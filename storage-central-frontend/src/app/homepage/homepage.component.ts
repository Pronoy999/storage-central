import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators, FormBuilder, AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MaterialModule } from '../shared/material.module';
import { RegisterUser } from '../shared/models/register-user';
import { StorageService } from '../shared/services/storage.service';
import { StorageCentralAPIService } from '../shared/storage-central-api.service';

@Component({
    selector: 'app-homepage',
    standalone: true,
    imports: [RouterOutlet, MaterialModule, CommonModule, FormsModule, ReactiveFormsModule],
    templateUrl: './homepage.component.html',
    styleUrl: './homepage.component.scss'
})

export class HomepageComponent implements OnInit {

    @ViewChild('registerFormSideNav') registerFormSideNav: any;
    hide: boolean = true;
    showSignIn: boolean = true;
    emailValidationPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$";
    specialCharactersPattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;

    designOption1: boolean = true;

    public loginForm = this.fb.group({
        loginName: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]),
        rememberMe: new FormControl('')
    });

    public registerForm = this.fb.group({
        firstName: new FormControl('', [Validators.required]),
        lastName: new FormControl('', [Validators.required]),
        registerEmail: new FormControl('', [Validators.required]),
        registerPassword: new FormControl('', [Validators.required])
    });

    constructor(private fb: FormBuilder, private apiService: StorageCentralAPIService, private toastr: ToastrService,
        private router: Router, private storageService: StorageService) { }

    ngOnInit(): void {

    }

    public passwordPatternValidator(regex: RegExp) {
        return (control: AbstractControl): { [key: string]: any } => {
            if (!control.value) {
                return { 'passwordPatternValidator': false }
            }
            const valid = regex.test(control.value);
            return valid ? { 'passwordPatternValidator': false } : { 'passwordPatternValidator': true };
        };
    }

    signIn() {
        this.showSignIn = true;
        if (this.loginForm.valid) {
            console.log(this.loginForm.value);
            this.apiService.getAllUsers().subscribe((userData: RegisterUser[]) => {
                console.log(userData);
                var AuthUser = userData.filter((data: RegisterUser) => data.registerEmail == this.loginForm.value.loginName);
                var AuthPass = userData.filter((data: RegisterUser) => data.registerPassword == this.loginForm.value.password);

                if (AuthPass.length !== 0 && AuthUser.length !== 0) {
                    this.toastr.success('Welcome back', 'Login successful!', {
                        timeOut: 3000,
                        closeButton: true,
                    });
                    this.storageService.saveUser(AuthUser);
                    this.router.navigate(['dashboard/'], { queryParams: { userId: AuthUser[0].id } });
                } else {
                    this.toastr.error('Please check your credentials', 'Login unsuccessful!', {
                        timeOut: 3000,
                        closeButton: true,
                    });
                }
            });
        }
    }

    register() {
        this.showSignIn = false;
        if (this.registerForm.valid) {
            console.log(this.registerForm.value);
            this.apiService.registerUser(this.registerForm.value).subscribe(() => {
                this.toastr.success('User is registered', 'Success', {
                    timeOut: 3000,
                    closeButton: true,
                });
                this.registerFormSideNav.close();
            });
        }
    }

    signup() {
        this.registerFormSideNav.open();
    }

    goBackToLogin() {
        this.registerFormSideNav.close();
    }
}