/**
 * Http 请求封装类
 *
 * 请在环境变量中配置 SERVER_URL
 * 改类提供 get,post,delete 方式请求数据
 * 请求失败时返回  {status: -9999, message: "请求失败"}
 */
import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {environment} from '../../environments/environment';

import { LoadingService } from './ng2Loading/loading.service';

/**
 * 封装HttpClient，主要解决：
 * + 优化HttpClient在参数上便利性
 * + 统一实现 loading
 * + 统一JWT
 */
@Injectable()
export class _HttpClient {
  constructor(private http: Http,private loading:LoadingService) {
  }

  private jwt: any = null;
  private _loading: boolean = false;

  /** 判断是否正在加载中 */
  get isLoading(): boolean {
    return this._loading;
  }

  public setJwt(jwt) {
    this.jwt = jwt;
  }

  private begin() {
    //console.time('http');
    //this._loading = true;
    this.loading.start();
  }

  private end() {
    //console.timeEnd();
    //this._loading = false;
    this.loading.stop();
  }

  /**
   * GET请求
   *
   * @param {string} url URL地址
   * @param {*} [params] 请求参数
   */
  get(url: string, params?: any): any {
    url = environment.server_url + url;
    this.begin();
    return this.http
      .get(url, {
        params: params
      }).toPromise().then((res) => {
        this.end();

        if (!res.ok) {
          return {status: -9999, message: "请求失败"};
        }

        return res.json();
      })
  }

  /**
   * POST请求
   *
   * @param {string} url URL地址
   * @param {*} [params] 请求参数
   */
  post(url: string, params?: any): Promise<any> {
    url = environment.server_url + url;
    let options: any = {
      withCredentials: true
    };
    if (this.jwt) {
      options.headers = {
        "Authorization": "Bearer " + this.jwt
      }
    }
    this.begin();
    return this.http
      .post(url, params, options).toPromise().then((res) => {
        this.end();
        if (!res.ok) {
          return {status: -9999, message: "请求失败"};
        }

        return res.json();
      })
  }

  /**
   * DELETE请求
   *
   * @param {string} url URL地址
   * @param {*} [params] 请求参数
   */
  delete(url: string, params?: any): any {
    url = environment.server_url + url;
    this.begin();
    return this.http
      .delete(url, {
        params: params
      }).toPromise().then((res) => {
        this.end();
        if (!res.ok) {
          return {status: -9999, message: "请求失败"};
        }

        return res.json();
      })
  }
}
