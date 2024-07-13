import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MaterialModule } from './shared/material.module';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, MaterialModule, CommonModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})

export class AppComponent implements OnInit {

    constructor() { }

    ngOnInit(): void { }

}