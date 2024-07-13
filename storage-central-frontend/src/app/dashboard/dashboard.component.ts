import { CommonModule } from '@angular/common';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { Subscription, finalize, Subject, takeUntil } from 'rxjs';
import { MaterialModule } from '../shared/material.module';
import { MediaService } from '../shared/media.service';
import { RegisterUser } from '../shared/models/register-user';
import { StorageService } from '../shared/services/storage.service';
import { StorageCentralAPIService } from '../shared/storage-central-api.service';
import { encode } from 'base64-arraybuffer';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource } from '@angular/material/table';
import { FileDataJson } from '../shared/models/file-data';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [RouterOutlet, MaterialModule, CommonModule, FormsModule, ReactiveFormsModule],
    providers: [MediaService, HttpClient],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss'
})

export class DashboardComponent implements OnInit {
    @Input() requiredFileType: string;
    fileName = '';
    uploadProgress: number;
    uploadSub: Subscription;
    encodedFileContent: any = "";
    uploadedMedia: Array<any> = [];

    loggedInUser: RegisterUser;

    displayedColumns: string[] = ['fileName', 'uploadedDate', 'fileType', 'fileSize', 'fileLastModifiedDate'];
    dataSource = new MatTableDataSource();

    constructor(private mediaService: MediaService, private storageService: StorageService, private apiService: StorageCentralAPIService,
        private router: Router, private toastr: ToastrService) { }

    ngOnInit(): void {
        this.loggedInUser = this.storageService.getUser()[0];

        this.apiService.getAllFiles().subscribe((fileData: FileDataJson[]) => {
            console.log(fileData);
            this.dataSource.data = fileData.filter((data: FileDataJson) => data.userId == this.loggedInUser.id);
        });
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    logout() {
        this.router.navigate(['home']);
        this.storageService.clean();
    }

    onFileSelected(event: any) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            this.encodedFileContent = reader.result;
            const base64Encoded = encode(this.encodedFileContent);
            console.log(base64Encoded);

            this.apiService.storeFile(this.loggedInUser.id, file, this.encodedFileContent).subscribe(() => {
                console.log("File Uploaded!");
                this.toastr.success('File Uploaded', 'Success', {
                    timeOut: 3000,
                    closeButton: true,
                });
            });
        };

        //const file: File = event.target.files[0];
        // if (file) {
        //     this.fileName = file.name;
        //     const formData = new FormData();
        //     formData.append("thumbnail", file);

        //     const upload$ = this.http.post(`http://yourapiurl`, formData, {
        //         reportProgress: true,
        //         observe: 'events'
        //     })
        //         .pipe(
        //             finalize(() => this.reset())
        //         );

        //     this.uploadSub = upload$.subscribe(event => {
        //         if (event.type == HttpEventType.UploadProgress) {
        //             this.uploadProgress = Math.round(100 * (event.loaded / event.total));
        //         }
        //     })
        // }
    }

    cancelUpload() {
        this.uploadSub.unsubscribe();
        this.reset();
    }

    reset() {
        this.uploadProgress = null;
        this.uploadSub = null;
    }

    onFileBrowse(event: Event) {
        const target = event.target as HTMLInputElement;
        this.processFiles(target.files);
    }

    processFiles(files: any) {
        for (const file of files) {
            var reader = new FileReader();
            reader.readAsDataURL(file); // read file as data url
            reader.onload = (event: any) => {
                // called once readAsDataURL is completed

                this.uploadedMedia.push({
                    FileName: file.name,
                    FileSize:
                        this.mediaService.getFileSize(file.size) +
                        ' ' +
                        this.mediaService.getFileSizeUnit(file.size),
                    FileType: file.type,
                    FileUrl: event.target.result,
                    FileProgessSize: 0,
                    FileProgress: 0,
                    ngUnsubscribe: new Subject<any>(),
                });

                this.startProgress(file, this.uploadedMedia.length - 1);
            };
        }
    }

    async startProgress(file: any, index: number) {
        let filteredFile = this.uploadedMedia
            .filter((u, index) => index === index)
            .pop();

        if (filteredFile != null) {
            let fileSize = this.mediaService.getFileSize(file.size);
            let fileSizeInWords = this.mediaService.getFileSizeUnit(file.size);
            if (this.mediaService.isApiSetup) {
                let formData = new FormData();
                formData.append('File', file);

                this.mediaService
                    .uploadMedia(formData)
                    .pipe(takeUntil(file.ngUnsubscribe))
                    .subscribe(
                        (res: any) => {
                            if (res.status === 'progress') {
                                let completedPercentage = parseFloat(res.message);
                                filteredFile.FileProgessSize = `${(
                                    (fileSize * completedPercentage) /
                                    100
                                ).toFixed(2)} ${fileSizeInWords}`;
                                filteredFile.FileProgress = completedPercentage;
                            } else if (res.status === 'completed') {
                                filteredFile.Id = res.Id;

                                filteredFile.FileProgessSize = fileSize + ' ' + fileSizeInWords;
                                filteredFile.FileProgress = 100;
                            }
                        },
                        (error: any) => {
                            console.log('file upload error');
                            console.log(error);
                        }
                    );
            } else {
                for (
                    var f = 0;
                    f < fileSize + fileSize * 0.0001;
                    f += fileSize * 0.01
                ) {
                    filteredFile.FileProgessSize = f.toFixed(2) + ' ' + fileSizeInWords;
                    var percentUploaded = Math.round((f / fileSize) * 100);
                    filteredFile.FileProgress = percentUploaded;
                    await this.fakeWaiter(Math.floor(Math.random() * 35) + 1);
                }
            }
        }
    }

    fakeWaiter(ms: number) {
        return new Promise((resolve) => {
            setTimeout(resolve, ms);
        });
    }

    removeImage(idx: number) {
        this.uploadedMedia = this.uploadedMedia.filter((u, index) => index !== idx);
    }
}
