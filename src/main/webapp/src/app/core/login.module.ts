import {Injectable} from '@angular/core';
import {_HttpClient} from './httpClient.module';
import { Router } from '@angular/router';
import  { ToastrService } from 'ngx-toastr';

import 'rxjs/add/operator/toPromise';
import {SessionStorage} from "./storage.module";

@Injectable()
export class LoginModule {
  user: any = {};
  jwt: any = null;
  expire: any = 0;
  isLogined: boolean = false;
  timer: any = null;

  constructor(private http: _HttpClient,
              private toastr: ToastrService,
              private router: Router,
              private storage: SessionStorage) {
    // 从session storage 中获取登录状态
    this.isLogined = storage.get("current_login") == "true";
    this.user = storage.getObject("current_user");
    this.jwt = storage.get("current_jwt");
    if (this.jwt) {
      http.setJwt(this.jwt);
    }
    this.expire = parseInt(storage.get("current_expire"));

    if (this.isLogined) {
      this.startTimer();
    }
  }

  // 登录
  login(user): Promise<any> {
    return this.http.post("login.json", user).then(res => {
      if (res.status == 0) {
        if (res.data) {

          // this.jwt = res.data.jwt || null;
          // this.expire = res.data.expire || null;
          // this.user = res.data.user || null;
          //
          // this.http.setJwt(this.jwt);
          //
          // this.storage.set("current_login", this.isLogined + "");
          // this.storage.set("current_jwt", this.jwt);
          // this.storage.setObject("current_user", this.user);
          // this.storage.setObject("current_expire", this.expire);

          this.user = res.data || null;
          if (this.user.isAdmin == 1) {
            this.isLogined = true;
          }

          //this.startTimer()
        }
      }
    });
  }

  loginfo(): Promise<any> {
    return this.http.post("loginfo.json", {}).then(res => {
      this.isLogined = false;
      if (res.status == 0) {
        if (res.data) {
          this.user = res.data || null;
          if (this.user.isAdmin == 1) {
            this.isLogined = true;
          }
        }
      }

    })
  }

  //退出登录
  logout():void {
    this.http.post('logout.json',{}).then((res) => {
      if(res.status == 0){
        this.isLogined = false;
        this.user = {};

        this.toastr.success('退出成功!');
        this.router.navigate(["/login"]);
      }
    })
  }

  // 每10分钟检测token过期,刷新token
  startTimer() {
    if (this.timer) {
      clearInterval(this.timer);
    }
    this.timer = setInterval(() => {
      if (this.expire - new Date().getTime() < 10 * 60 * 1000) {
        this.login({"refresh_token": this.user.refresh_token});
      }
    }, 10 * 60 * 1000); // 每10分钟检测一次
  }
}
