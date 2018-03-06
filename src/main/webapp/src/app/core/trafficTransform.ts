import { Injectable } from '@angular/core'

Injectable();

export class TrafficTransform {
  constructor(){

  }

  //with unit
  public withUnit(num:number){
    if (num < 1) {
      return num.toFixed(1) + ' B';
    } else if (num < 1000) {
      return num.toFixed(0) + ' B';
    } else if (num < 1000000) {
      return (num / 1000).toFixed(0) + ' KB';
    } else{
      return (num / 1000000000).toFixed(2) + ' GB';
    }
  }

  //without unit
  public withoutUnit(str:any){
    if(str.indexOf('KB') > 0 || str.indexOf('K') >0 ||str.indexOf('kb') >0){
      return str.replace(/[a-zA-Z]/g,'') * 1000;
    }else if(str.indexOf('MB') > 0 || str.indexOf('M') > 0 || str.indexOf('mb') > 0){
      return str.replace(/[a-zA-Z]/g,'') * 1000000;
    }else if(str.indexOf('GB') > 0 || str.indexOf('G') > 0 || str.indexOf('gb') > 0){
      return str.replace(/[a-zA-Z]/g,'') * 1000000000;
    }else if(str.indexOf('B') > 0 || str.indexOf('b') > 0){
      return str.replace(/[a-zA-Z]/g,'') * 1;
    }else{
      return str;
    }
  }
}
