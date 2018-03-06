import {forwardRef, Injectable, NgModule} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';
/*import {clearInterval} from "timers";*/

export enum LoadingAttrType {
  SIZE,
  COLOR,
  SHOW
}

export class LoadingAttr {
  constructor(public type:LoadingAttrType,public value:any){}
}

//export modules
/*@NgModule({providers: [forwardRef(() => LoadingService)]})
export class LoadingModule {
}*/

@Injectable()

export class LoadingService {
  private _size:number = 36;
  private _color:string = 'rgba(103, 240, 62, 0.69)';
  private _show:boolean = false;

  /*private _intervalCounterId:any = 0;
  public interval:number = 100; // in milliseconds*/

  private attrSource:Subject<LoadingAttr> = new Subject<LoadingAttr>();
  public attrsObserve: Observable<LoadingAttr> = this.attrSource.asObservable();

  constructor() {}

  set color(value:string){
    if(!_.isEmpty(value)){
      this._color = value;
      this.emitAttr(new LoadingAttr(LoadingAttrType.COLOR,this._color));
    }
  }
  get color():string {
    return this._color;
  }

  set size(value:number){
    if(!_.isEmpty(value)){
      this._size = value;
      this.emitAttr(new LoadingAttr(LoadingAttrType.SIZE,this._size));
    }
  }
  get size():number {
    return this._size;
  }

  /*set show(value:boolean){
    if(!_.isEmpty(value)){
      this._show = value;
      this.emitAttr(new LoadingAttr(LoadingAttrType.SHOW,this._show));
    }
  }*/
  get show():boolean{
    return this._show;
  }


  private emitAttr(attr: LoadingAttr) {
    if (this.attrSource) {
      this.attrSource.next(attr);
    }
  }


  public start() {
    this._show = true;
    this.emitAttr(new LoadingAttr(LoadingAttrType.SHOW,this._show));
  }

  public stop(){
    setTimeout(()=>{
      this._show = false;
      this.emitAttr(new LoadingAttr(LoadingAttrType.SHOW,this._show));
    },100);

  }

  reset(){

  }

  complete(){

  }







}
