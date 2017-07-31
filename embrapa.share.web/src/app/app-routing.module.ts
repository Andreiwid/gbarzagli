import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
    {
        path: '', component: LoginComponent,
        children: [
            { path: 'login', component: LoginComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { enableTracing: true })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
