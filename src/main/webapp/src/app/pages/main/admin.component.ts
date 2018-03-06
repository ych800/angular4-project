import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router, NavigationStart, NavigationEnd} from '@angular/router';
import {SlimLoadingBarService} from 'ng2-slim-loading-bar';
import {LoginModule} from "../../core/login.module";


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['../../../styles/sass/main.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AdminComponent implements OnInit {


  constructor(private router: Router,
              private loadingBar: SlimLoadingBarService,
              private login: LoginModule) {
  }


  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.loadingBar.start();
      }
      if (event instanceof NavigationEnd) {
        this.loadingBar.complete();
      }
    });

    this.login.loginfo().then(res => {
      if(!this.login.isLogined){
        this.router.navigate(["/login"]);
      }
    })
  }
}
