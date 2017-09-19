import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    @Input() username: string;
    @Input() name: string;
    @Input() password: string;
    @Input() confirmPassword: string;

    constructor() { }

    ngOnInit() {
    }

    onConfirm() {
        if (this.password === this.confirmPassword) {

        }
    }
}
