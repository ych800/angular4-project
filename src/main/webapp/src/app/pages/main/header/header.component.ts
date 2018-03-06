import { Component, OnInit } from '@angular/core';
import {NgbDropdownConfig} from '@ng-bootstrap/ng-bootstrap';

import { LoginModule } from '../../../core/login.module';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styles:[`
    .signOutBtn:hover{color:#FFC107;}
  `],
  providers: [NgbDropdownConfig]
})
export class HeaderComponent implements OnInit {
  scrolled:boolean = true;

  constructor(profileDropdownConfig: NgbDropdownConfig,
              private loginSever: LoginModule,) {

    profileDropdownConfig.placement = 'bottom-right';
    profileDropdownConfig.autoClose = false;

  }

  ngOnInit() {

  }

  //退出登录
  public quit():void{
    this.loginSever.logout();
  }

}
