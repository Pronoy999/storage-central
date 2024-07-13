import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterUser } from './models/register-user';

@Injectable({
    providedIn: 'root'
})
export class StorageCentralAPIService {
    private baseUrl = 'http://localhost:3000/';

    constructor(private http: HttpClient) { }

    registerUser(data: Partial<RegisterUser>): Observable<any> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(data);
        return this.http.post(this.baseUrl + 'users/', body, { 'headers': headers });
    }

    getAllUsers() {
        return this.http.get(this.baseUrl + 'users');
    }

    storeFile(userId: string, file: any, fileData: string) {
        const headers = { 'content-type': 'application/json' }
        //const body = JSON.stringify(userId+file);
        const body = {
            'userId': userId,
            'fileLastModified': file.lastModified,
            'fileLastModifiedDate': file.lastModifiedDate,
            'fileName': file.name,
            'fileSize': file.size,
            'fileType': file.type,
            'encodedData': fileData,
            'uploadedDate': new Date()
        };
        return this.http.post(this.baseUrl + 'files/', body, { 'headers': headers });
    }

    getAllFiles() {
        return this.http.get(this.baseUrl + 'files');
    }
}