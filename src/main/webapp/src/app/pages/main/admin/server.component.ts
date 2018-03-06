import { Component,OnInit,ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ModalDirective } from 'ngx-bootstrap/modal';

import  { _HttpClient } from '../../../core/httpClient.module';
import  { LoginModule } from '../../../core/login.module';
import {TrafficTransform} from "../../../core/trafficTransform";

@Component({
  selector:'admin-server',
  templateUrl:'./server.component.html',
  styles:[``]
})

export class ServerComponent implements OnInit{
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
  dataUrl: any = "api/server/list.json";
  item:any = {};

  constructor(private http:_HttpClient,
              private login:LoginModule,
              private toastr:ToastrService,
              private traffic:TrafficTransform
              ){
    this.searchParams = {
      server:{

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
        if(data){
          let list = data.list || [];
          if(list.length){
            for(let i = 0;i<list.length;i++){
              data.list[i].traffic = $this.traffic.withUnit(list[i].traffic);
            }
          }
        }
      },
      columns:[
        /*{
          field:'id',
          title:'编号'
        },*/
        {
          field:'name',
          title:'名称'
        },
        {
          field:'region',
          title:'地域'
        },
        {
          field:'ip',
          title:'IP地址'
        },
        {
          field:'port',
          title:'管理端口'
        },
        {
          field:'user',
          title:'用户名'
        },
        {
          field:'userPwd',
          title:'密码'
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
          field:'traffic',
          title:'流量'
        },
        {
          field:'trafficName',
          title:'流量名称'
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

    this.item.traffic = this.traffic.withoutUnit(this.item.traffic);
    this.http.post('api/server/save.json',this.item).then(res=>{
      if(res.status == 0){
        this.toastr.success('保存成功!');
        this.updateModal.hide();
        this.table.reload();
      }
    })
  }

  //确认删除
  sureDelete(){
    this.http.post('api/server/delete.json',this.item).then((res)=>{
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



/**
 *
 * CREATE TABLE `node` (
   `id` int(11) NOT NULL '编号',
   `name` varchar(255) NULL COMMENT '名称',
   `regon` varchar(255) NULL  COMMENT '地域',
   `ip` varchar(255) NULL  COMMENT 'ip地址',
   `port` varchar(255) NULL COMMENT '管理端口',
   `user` varchar(255) NULL COMMENT '用户名',
   `user_pwd` varchar(255) NULL '密码',
   `traffic` bigint(64) NULL '流量',
   `traffic_name` bigint(64) NULL '流量名称',
   `createdate` datetime NULL,
   `createby` varchar(255) NULL,
   `modifydate` datetime NULL,
   `modifyby` varchar(255) NULL,
   `status` int(11) NULL,
   PRIMARY KEY (`id`)
 );
 *
 * **/
