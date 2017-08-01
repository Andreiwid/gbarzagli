import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    title = 'Embrapa Share';

    links = [
        { name: 'Login', url: 'login' }
    ];
}
