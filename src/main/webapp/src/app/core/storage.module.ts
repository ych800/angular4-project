import {Injectable} from '@angular/core';

@Injectable()
export class SessionStorage{
  storage:any={};

  constructor(){
    if(!sessionStorage){
      console.error("Current browser does not support Session Storage")
    }

    this.storage = sessionStorage;

  }

  public set(key:string, value:string):void {
    this.storage[key] = value;
  }

  public get(key:string):string {
    return this.storage[key] || false;
  }

  public setObject(key:string, value:any):void {
    this.storage[key] = JSON.stringify(value);
  }

  public getObject(key:string):any {
    return JSON.parse(this.storage[key] || '{}');
  }

  public remove(key:string):any {
    this.storage.removeItem(key);
  }
}

@Injectable()
export class LocalStorage{
  storage:any={};

  constructor(){
    if(!localStorage){
      console.error("Current browser does not support Local Storage")
    }

    this.storage = localStorage;

  }

  public set(key:string, value:string):void {
    this.storage[key] = value;
  }

  public get(key:string):string {
    return this.storage[key] || false;
  }

  public setObject(key:string, value:any):void {
    this.storage[key] = JSON.stringify(value);
  }

  public getObject(key:string):any {
    return JSON.parse(this.storage[key] || '{}');
  }

  public remove(key:string):any {
    this.storage.removeItem(key);
  }
}

