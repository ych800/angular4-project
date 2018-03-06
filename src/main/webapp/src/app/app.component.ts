import { Component } from '@angular/core';
import {LoginModule} from "./core/login.module";
import {Router} from "@angular/router";

@Component({
  selector: 'mx-app',
  template: `
      <router-outlet></router-outlet>
  `,
  styleUrls: []
})
export class AppComponent {
  title = 'app';

  constructor(loginModule: LoginModule, router:Router){
    // if(!loginModule.isLogined){
    //   router.navigate(["/login"]);
    // }
  }
}
