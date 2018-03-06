import { Component,OnInit,ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ModalDirective } from 'ngx-bootstrap/modal';

import  { _HttpClient } from '../../../core/httpClient.module';
import  { LoginModule } from '../../../core/login.module';
import {TrafficTransform} from "../../../core/trafficTransform";

@Component({
  selector:'admin-combo',
  templateUrl:'./combo.component.html'
})

export class ComboComponent implements OnInit{
  @ViewChild('datagrid') table :any;
  @ViewChild('optTpl') optTpl :any;
  @ViewChild('statusTpl') statusTpl :any;
  @ViewChild('updateModal') updateModal: ModalDirective;
  @ViewChild('deleteModal') deleteModal: ModalDirective;

  public modalConfig ={
    animate:true,
    keyboard:true,
    backdrop: true,
    ignoreBackdropClick: false
  }
  searchParams: any = {};
  options: any = {};
  dataUrl: any = "api/combo/list.json";
  item:any = {};

  constructor(private http:_HttpClient,
              private login:LoginModule,
              private toastr:ToastrService,
              private traffic:TrafficTransform
  ){
    this.searchParams = {
      combo:{

      },
      pager:{
        pageNumber:1,
        pageSize:10
      }
    }
  }

  ngOnInit(){

  }

  ngAfterViewInit(){
    let $this = this;
    this.options={
      pagination:true,
      onLoadSuccess:function (data) {
        if(data) {
          let list = data.list || [];
          if (list.length) {
            for (let i = 0; i < list.length; i++) {
              data.list[i].traffic = $this.traffic.withUnit(list[i].traffic);
            }
          }
        }
      },
      columns:[
        {
         field:'name',
         title:'名称'
         },
        {
          field:'traffic',
          title:'流量'
        },
        {
          field:'trafficName',
          title:'流量名称'
        },
        {
          field:'period',
          title:'结算周期'
        },
        {
          field:'price',
          title:'价格'
        },
        {
          field:'salePrice',
          title:'销售价格'
        },
        {
          field:'modifydate',
          dataType:'date',
          title:'修改时间'
        },
        {
          field:'status',
          title:'状态',
          template:this.statusTpl
        },
        {
          field:'',
          title:'操作',
          template:this.optTpl
        },
      ]
    }
  }

  modaltype :string = 'add';
  public showModal(type,row){
    this.item = {};
    if(row){
      this.item = _.clone(row);
    }
    if(type == 'delete'){
      this.deleteModal.show();
    }else{
      this.updateModal.show();
      if(type == 'add'){
        this.modaltype = 'add';
      }else{
        this.modaltype = 'modify';
      }
    }
  }

  public modalHandler(formInfo:NgForm,type,e){
    if(type == 'onHide'||'onHidden'){
      if(formInfo){
        formInfo.onReset();
      }
      this.item = {};
    }
  }


  saveItem(formInfo:NgForm) {
    if(!formInfo.form.valid){
      this.toastr.error('请将信息填写完整!');
      return;
    }
    //流量转化:
    this.item.traffic = this.traffic.withoutUnit(this.item.traffic);
    this.http.post('api/combo/save.json',this.item).then(res=>{
      if(res.status == 0){
        this.toastr.success('保存成功!');
        this.updateModal.hide();
        this.table.reload();
      }
    })
  }

  //确认删除
  sureDelete(){
    this.http.post('api/combo/delete.json',this.item).then((res)=>{
      if(res.status == 403){
        this.toastr.error('您没有权限删除该数据!');
      } else if(res.status == 0){
        this.toastr.success('删除成功!');
        this.deleteModal.hide();
        this.table.reload();
      }
    })
  }
}

/************************ combo Databases(key)  ***************************/
/*
* CREATE TABLE `combo` (
 `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
 `name` VARCHAR ( 255 ) NULL,
 `traffic` VARCHAR ( 255 ) NULL COMMENT '流量',
 `traffic_name` VARCHAR ( 255 ) NULL COMMENT '流量名称, eg:30G',
 `period` VARCHAR ( 255 ) NULL COMMENT '结算周期',
 `price` DECIMAL ( 10, 2 ) NULL comment '价格',
 `sale_price` DECIMAL ( 10, 2 ) NULL comment '销售',
 `createdate` datetime NULL,
 `createby` VARCHAR ( 255 ) NULL,
 `modifydate` datetime NULL,
 `modifyby` VARCHAR ( 255 ) NULL,
 `status` INT ( 11 ) NULL,
 PRIMARY KEY ( `id` )
 );
* */
