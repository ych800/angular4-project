import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {ModalDirective} from 'ngx-bootstrap/modal';

import  {_HttpClient} from '../../../core/httpClient.module';
import { TrafficTransform } from '../../../core/trafficTransform';

@Component({
  selector: 'admin-user',
  templateUrl: './user.component.html',
  styles: [`
    .btn-xs{padding: 5px 5px !important;}
    .pagination > li > a.page-link,.pagination > li > span{background: #4caf50 !important;}//待修改分页样式...
  `]
})

export class UserComponent implements OnInit {
  @ViewChild('datagrid') table: any;
  @ViewChild('tabledetail') tableDetail: any;
  @ViewChild('optTpl') optTpl: any;
  @ViewChild('statusTpl') statusTpl: any;
  @ViewChild('updateModal') updateModal: ModalDirective;
  @ViewChild('orderModal') orderModal: ModalDirective;
  @ViewChild('deleteModal') deleteModal: ModalDirective;
  @ViewChild('detailModal') detailModal: ModalDirective;

  public modalConfig = {
    animate: true,
    keyboard: true,
    backdrop: true,
    ignoreBackdropClick: false
  }
  searchParams: any = {};
  options: any = {};
  dataUrl: any = "api/user/list.json";
  item: any = {};
  detailOptions :any = {};
  detailSearchParams: any = {};
  detailSearchUrl:string = 'api/usertraffic/list.json';
  item_userName:string = '';

  constructor(private http: _HttpClient,
              private traffic :TrafficTransform,
              private toastr: ToastrService,) {
    this.searchParams = {
      user: {username:''},
      pager: {
        pageNumber: 1,
        pageSize: 10
      }
    }
    this.detailSearchParams = {
      userTraffic:{},
      pager: {
        pageNumber: 1,
        pageSize: 10
      }
    }
  }

  comboData: any = [];
  serviceData: any = [];

  ngOnInit() {
    //获取套餐数据
    this.http.post('api/combo/list.json', {'combo': {}, 'pager': {'pageSize': 10000, 'pageNumber': 1}}).then((res) => {
      if (res.status == 0) {
        this.comboData = res.map.content;
      }
    })
    //获取服务器数据;
    this.http.post('api/server/list.json', {'server': {}, 'pager': {'pageSize': 10000, 'pageNumber': 1}}).then((res) => {
      if (res.status == 0) {
        this.serviceData = res.map.content;
      }
    })
  }

  ngAfterViewInit() {
    let $this = this;
    this.options = {
      pagination: true,
      onLoadSuccess: function (data) {
        if(data){
          let list = data.list || [];
          if(list.length){
            for(let i = 0;i<list.length;i++){
              if(list[i].userTraffic){
                let diff = list[i].userTraffic.endDate - (new Date()).getTime();
                if(diff<=0){
                  data.list[i].restDate = '已过期';
                }else{
                  data.list[i].restDate = (diff / 1000 / 60 / 60 / 24).toFixed(1) + '(天)';
                }

                data.list[i].restTraffic = $this.traffic.withUnit(list[i].userTraffic.traffic)+'/'+$this.traffic.withUnit(list[i].userTraffic.trafficUsed);
              }
            }
          }
        }
      },
      columns: [
        {
          field: 'username',
          title: '用户名'
        },
        {
          field: 'email',
          title: '邮箱'
        },
        {
          field: 'phone',
          title: '电话'
        },
        {
          field: 'port',
          title: '代理端口'
        },
        {
          field: 'portPwd', //add  modify ==> 不显示;
          title: '代理密码'
        },
        {
          field: 'server',
          title: '服务器'
        },
        {
          field: 'restDate',
          title: '剩余天数'
        },
        {
          field: 'restTraffic',
          title: '流量(订购/已使用)'
        },
        {
          field: '',
          title: '操作',
          template: this.optTpl
        },
      ]
    }

    //详细:
    this.detailOptions = {
      pagination: true,
      autoload:false,
      onLoadSuccess: function (data) {
        if(data){
          let list = data.list || [];
          if(list.length){
            for(let i = 0;i<list.length;i++){
              data.list[i].userName = $this.item_userName;
              data.list[i].trafficUnit = $this.traffic.withUnit(data.list[i].traffic);
              data.list[i].useTrafficUnit = $this.traffic.withUnit(data.list[i].trafficUsed);
            }
          }
        }
      },
      columns: [
        {
          field: 'userName',
          title: '用户名'
        },
        {
          field: 'startDate',
          dataType:'date',
          title: '开始日期'
        },
        {
          field: 'endDate',
          dataType:'date',
          title: '截止日期'
        },
        {
          field: 'trafficUnit',
          title: '套餐流量'
        },
        {
          field: 'useTrafficUnit',
          title: '已用流量'
        },
        {
          field: 'status',
          title: '状态'
        }
      ]
    }


  }

  order: any = {};
  modaltype: string = 'add';

  public showModal(type, row) {
    this.item = {};
    if (row) {
      this.item = _.clone(row)
    }
    if (type == 'delete') {
      this.deleteModal.show();
    } else if (type == 'order') {
      this.order.userId = row.id;
      this.order.comboId = '-1';
      this.order.serviceId = '-1';
      this.order.price =this.order.realPrice = 0;
      this.order.quantity = 1;
      this.orderModal.show();
    }else if(type == 'detail'){
      this.detailModal.show();
      this.item_userName = this.item.username;
      this.detailSearchParams.userTraffic.userId = this.item.id;

      if(!this.item.userTraffic){
        this.tableDetail.empty();
        return;
      }

      this.tableDetail.reload();
    } else {
      this.updateModal.show();
      if (type == 'add') {
        this.modaltype = 'add';
        this.item.server = '-1';
      } else {
        this.modaltype = 'modify';
        this.item.password = null;
        if(!this.item.server){
          this.item.server = '-1';
        }
      }
    }
  }

  public modalHandler(formItem: NgForm, type, e) {
    if (type == 'onHide' || 'onHidden') {
      if (formItem) {
        formItem.onReset();
      }
      this.item = {};
      this.order = {};
      this.detailSearchParams.userTraffic.userId = '';
      this.tableDetail.empty();
    }
  }

  //保存用户
  saveItem(formInfo: NgForm) {
    if (!formInfo.form.valid) {
      this.toastr.error('请将信息填写完整!');
      return;
    }

    this.http.post('api/user/save.json', this.item).then(res => {
      if (res.status == 0) {
        this.toastr.success('保存成功!');
        this.updateModal.hide();
        this.table.reload();
      }
    })
  }

  //保存新订单
  private orderItem: any = {
    userOrder: {user: {id: null}},
    combo: {id: '-1'},
    server: {id: '-1'}
  }

  saveOrder(formOrder: NgForm) {
    //{"comboId":"3","userId":"3","serviceId":"1","quantity":"12","price":"121","description":"测试测试.."}
    this.orderItem = {
      userOrder: {
        user: {id: this.order.userId},
        quantity: this.order.quantity,
        price: this.order.price,
        realPrice:this.order.realPrice,
        description: this.order.description
      },
      combo: {id: this.order.comboId},
      server: {id: this.order.serviceId}
    }
    //简单验证
    if(!formOrder.form.valid){
      this.toastr.error('请将信息填写完整1');
      return;
    }

    if (this.orderItem.combo.id == '-1') {
      this.toastr.error('请选择套餐!');
      return;
    }

    if (this.orderItem.server.id == '-1') {
      this.toastr.error('请选择服务器!')
      return;
    }

    //数量验证
    if(isNaN(this.orderItem.userOrder.quantity)
      || this.orderItem.userOrder.quantity <= 0
      || this.orderItem.userOrder.quantity.toString().indexOf('.')>0){
      this.toastr.error('数量必须为大于0整数!');
      return;
    }
    if(this.orderItem.userOrder.quantity > 12){
      this.toastr.error('数量最大值不能超过12!');
      return;
    }
    if(isNaN(this.order.realPrice) || this.order.realPrice < 0){
      this.toastr.error('实付价格必须为大于0的数字!');
      return;
    }
    this.orderItem.userOrder.paymethod = 0;//支付方式; 0:线下支付,1:线上支付
    this.http.post('api/userorder/new.json', this.orderItem).then(res => {
      if (res.status == 0) {
        this.toastr.success('保存成功!');
        this.orderModal.hide();
        this.table.reload();
      }
    })
  }

  //确认删除
  sureDelete() {
    this.http.post('api/user/delete.json', this.item).then((res) => {
      if (res.status == 403) {
        this.toastr.error('您没有权限删除该数据!');
      } else if (res.status == 0) {
        this.toastr.success('删除成功!');
        this.deleteModal.hide();
        this.table.reload();
      }
    })
  }

  //套餐价格查询:
  private comboPrice:number = 0;
  searchPrice(comboId) {
    let item = _.find(this.comboData,{"id":comboId * 1});
    if(item){
      this.order.realPrice = this.order.price = this.comboPrice = item.price;
      if(isNaN(this.order.quantity) || this.order.quantity <= 0 ||this.order.quantity.toString().indexOf('.')>0){
        this.order.realPrice = this.order.price = 0;
        return ;
      }
      this.order.price = item.price * this.order.quantity;
    }else{
      this.order.realPrice =this.order.price = 0;
    }
  }

  //change realPrice
  realPriceChange(price){
    if(isNaN(price) || price < 0){
      this.toastr.error('实付价格必须为大于0的数字!');
    }
  }

  //计算总价格
  calTotalPrice(quantity){
    if(isNaN(quantity) || quantity<=0 || quantity.toString().indexOf('.')>0){
      this.toastr.error('数量必须为大于0整数!');
      this.order.price = this.order.realPrice = 0;
      return;
    }
    if(quantity > 12){
      this.toastr.error('数量最大值不能超过12!');
    }
    this.order.price = this.order.realPrice = this.comboPrice * quantity;
  }

}




/*
 * DROP DATABASE if EXISTS `ss_cloud`;

 CREATE DATABASE `ss_cloud` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

 USE `ss_cloud`;


 CREATE TABLE `user` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(255) NULL,
 `username` varchar(255) NULL,
 `password` varchar(255) NULL,
 `email` varchar(255) NULL,
 `phone` varchar(255) NULL,
 `level_id` int(255) NULL COMMENT '用户等级',
 `expire` varchar(255) NULL COMMENT '过期时间',
 `traffic` int(32) NULL COMMENT '流量, 保存KB',
 `serverName` varchar(255) NULL,
 `server` varchar(255) NULL,
 `port` varchar(255) NULL,
 `port_pwd` varchar(255) NULL COMMENT '代理使用密码',
 `createdate` datetime NULL,
 `createby` varchar(255) NULL,
 `modifydate` datetime NULL,
 `modifyby` varchar(255) NULL,
 `status` int(11) null,
 PRIMARY KEY (`id`)
 )
 COMMENT = '用户表';

 CREATE TABLE `level` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(255) NULL,
 `traffic` varchar(255) NULL COMMENT '流量',
 `traffic_name` varchar(255) NULL COMMENT '流量名称, eg:30G',
 `default_node` varchar(255) NULL COMMENT '默认节点',
 `default_port` varchar(255) NULL COMMENT '默认端口',
 `default_port_pwd` varchar(255) NULL COMMENT '默认密码',
 `price` decimal(10,2) NULL,
 `sale_price` decimal(10,2) NULL,
 `createdate` datetime NULL,
 `createby` varchar(255) NULL,
 `modifydate` datetime NULL,
 `modifyby` varchar(255) NULL,
 `status` int(11) null,
 PRIMARY KEY (`id`)
 );

 CREATE TABLE `server` (
 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
 `name` varchar(255) NULL COMMENT '名称',
 `regon` varchar(255) NULL COMMENT '地域',
 `ip` varchar(255) NULL COMMENT 'ip地址',
 `port` varchar(255) NULL COMMENT '管理端口',
 `user` varchar(255) NULL COMMENT '用户名',
 `user_pwd` varchar(255) NULL COMMENT '密码',
 `traffic` bigint(64) NULL  COMMENT '流量',
 `traffic_name` varchar(64) NULL  COMMENT '流量名称',
 `createdate` datetime NULL,
 `createby` varchar(255) NULL,
 `modifydate` datetime NULL,
 `modifyby` varchar(255) NULL,
 `status` int(11) NULL,
 PRIMARY KEY (`id`)
 );

 CREATE TABLE `port` (
 `id` int(11) NOT  NULL AUTO_INCREMENT,
 `user_id` int(11) NULL,
 `name` varchar(255) NULL,
 `server_id` int(11) NULL,
 `port` varchar(255) NULL,
 `createdate` datetime NULL,
 `createby` varchar(255) NULL,
 `modifydate` datetime NULL,
 `modifyby` varchar(255) NULL,
 `status` int(11) NULL,
 PRIMARY KEY (`id`)
 );


 ALTER TABLE `user` ADD CONSTRAINT `fk_user` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`);
 ALTER TABLE `port` ADD CONSTRAINT `fk_port` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
 ALTER TABLE `port` ADD CONSTRAINT `fk_port_1` FOREIGN KEY (`server_id`) REFERENCES `server` (`id`);
 *
 *
 *
 * */


/**  动态多级表单创建和使用 **/
/*
 * 1.app.module.ts
 * import activeFormModule form @angular/forms ===> 添加到import中
 *
 * 2. 组件中使用:
 * import FormGroup,FormBuilder form @angular/forms
 *
 * export class orderFormComponent{
 *   orderForm:FormGroup;
 *
 *   constructor(private fb:FormBuilder){
 *     this.createOrderForm();
 *   }
 *
 *   createOrderForm(){
 *     this.orderForm = {
 *       combo:this.fb.group({id:''}),
 *       server:this.fb.grouo({id:''}),
 *       userOrder:this.fb.group({
 *         price:'',
 *         quantity:'',
 *         description:'',
 *         userId:''
 *       })
 *     }
 *   }
 * }
 *
 * html====>
 *
 * <form [formGroup]='orderForm' novalidate>
 *
 *     <!-- combo -->
 *      <div formGroupName="combo">
 *       <div class="form-group">
 <label class="center-block">套餐:
 <input class="form-control" formControlName="id">
 </label>
 </div>
 *      </div>
 *
 *      <!-- server -->
 *      <div formGroupName="server">
 *       <div formGroupName="combo">
 *       <div class="form-group">
 <label class="center-block">服务器:
 <input class="form-control" formControlName="id">
 </label>
 </div>
 *      </div>
 *      </div>
 *      <!-- userOrder -->
 *      <div formGroupName="userOrder">
 *         <div class="form-group">
 <label class="center-block">价格:
 <input class="form-control" formControlName="price">
 </label>
 </div>
 *      </div>
 * </form>
 *
 *
 *
 * */
