import { Component,OnInit,ViewChild } from '@angular/core';
//import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
//import { ModalDirective } from 'ngx-bootstrap/modal';

import  { _HttpClient } from '../../../core/httpClient.module';
import  { LoginModule } from '../../../core/login.module';
import {TrafficTransform} from "../../../core/trafficTransform";

@Component({
  selector:'admin-order',
  templateUrl:'./order.component.html'
})

export class OrderComponent implements OnInit{
  @ViewChild('datagrid') table :any;
  @ViewChild('optTpl') optTpl :any;
  @ViewChild('statusTpl') statusTpl :any;
  //@ViewChild('updateModal') updateModal: ModalDirective;
  //@ViewChild('deleteModal') deleteModal: ModalDirective;

  /*public modalConfig ={
    animate:true,
    keyboard:true,
    backdrop: true,
    ignoreBackdropClick: false
  }*/
  searchParams: any = {};
  options: any = {};
  dataUrl: any = "api/userorder/list.json";
  //item:any = {};

  constructor(private http:_HttpClient,
              private login:LoginModule,
              private toastr:ToastrService,
              private traffic:TrafficTransform
  ){
    this.searchParams = {
      order:{

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
              data.list[i].userId = list[i].user.id;
              data.list[i].comboName = list[i].combo.name;
              data.list[i].paymethod = data.list[i].paymethod == '0' ? '线下支付' : '线上支付';
              data.list[i].traffic = $this.traffic.withUnit(list[i].traffic);
            }
          }
        }
      },
      columns:[
        {
          field:'orderNo',
          title:'订单编号'
        },
        {
          field:'userId',
          title:'用户ID'
        },
        {
          field:'comboName',
          title:'套餐'
        },
        {
          field:'traffic',
          title:'购买流量'
        },
        {
          field:'quantity',
          title:'数量'
        },
        {
          field:'realPrice',
          title:'金额'
        },
        {
          field:'paymethod',
          title:'支付方式'
        },
        {
          field:'payDate',
          dataType:'date',
          title:'支付时间'
        },
        {
          field:'orderDate',
          dataType:'date',
          title:'订单时间'
        },
        {
          field:'description',
          title:'订单备注'
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
        }/*,
        {
          field:'',
          title:'操作',
          template:this.optTpl
        },*/
      ]
    }
  }

  /*modaltype :string = 'add';
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
  }*/

  /*public modalHandler(formInfo:NgForm,type,e){
    if(type == 'onHide'||'onHidden'){
      if(formInfo){
        formInfo.onReset();
      }
      this.item = {};
    }
  }*/


  /*saveItem(formInfo:NgForm) {
    console.log(formInfo);
    console.log(JSON.stringify(this.item));

    if(!formInfo.form.valid){
      this.toastr.error('请将信息填写完整!');
      return;
    }

    this.http.post('api/order/save.json',this.item).then(res=>{
      if(res.status == 0){
        this.toastr.success('保存成功!');
        this.updateModal.hide();
        this.table.reload();
      }
    })
  }*/

  //确认删除
  /*sureDelete(){
    this.http.post('api/order/delete.json',this.item).then((res)=>{
      if(res.status == 403){
        this.toastr.error('您没有权限删除该数据!');
      } else if(res.status == 0){
        this.toastr.success('删除成功!');
        this.deleteModal.hide();
        this.table.reload();
      }
    })
  }*/
}
/************************  order Databases(key)  ***************************/
/*
 *`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
 `order_no` VARCHAR ( 255 ) NULL COMMENT '订单编号',
 `user_id` INT ( 11 ) NULL,
 `combo_id` INT ( 11 ) NULL COMMENT '套餐',
 `traffic` BIGINT ( 64 ) NULL COMMENT '购买流量',
 `quantity` INT ( 11 ) NULL COMMENT '数量',
 `order_date` datetime NULL COMMENT '订单时间',
 `price` DECIMAL ( 10, 2 ) NULL,
 `paymethod` INT ( 11 ) NULL COMMENT '0. 线下支付, 1. PayPal',
 `pay_date` datetime NULL COMMENT '支付时间',
 `description` VARCHAR ( 500 ) NULL COMMENT '订单备注',
 `createdate` datetime NULL,
 `createby` VARCHAR ( 255 ) NULL,
 `modifydate` datetime NULL,
 `modifyby` VARCHAR ( 255 ) NULL,
 `status` INT ( 11 ) NULL,
 * */
