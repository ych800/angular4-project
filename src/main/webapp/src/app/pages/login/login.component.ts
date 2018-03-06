import {Component, OnInit,ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {LoginModule} from "../../core/login.module";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../../styles/sass/main.scss'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  user: any = {
    username: "",
    password: ""
  };

  constructor(private loginModule: LoginModule,
              private router: Router) {

  }

  ngOnInit() {

  }

  login() {
    this.loginModule.login(this.user).then(() => {
      let user = this.loginModule.user;
      if (user) {
        this.router.navigate(["/admin/dashboard"])
      }
    });
  }
}
