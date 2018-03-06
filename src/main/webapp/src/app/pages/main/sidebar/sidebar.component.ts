import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
  showHoverElem: any = {top: -150, height: 0, };
  menuItems : any = [
    {
      page: "/admin/dashboard",
      name: "首页",
      icon: "fa fa-home"
    },{
      page: "/admin/user",
      name: "用户",
      icon: "fa fa-user-circle",
    },{
      page: "/admin/server",
      name: "服务器",
      icon: "fa fa-cloud-download",
    },{
      page: "/admin/combo",
      name: "套餐",
      icon: "fa fa-credit-card",
    },
    {
      page:'/admin/order',
      name:'订单',
      icon:'fa fa-shopping-cart'
    }
  ]
  constructor() {
    this.menuItems
  }

  ngOnInit() {
  }

  hoverItem (event, item){
    this.showHoverElem = {
      top: $(event.target).offset().top,
      height: event.target.clientHeight
    };
    event.stopPropagation();
  }

  // 切换菜单
  activeMenu(event, item){
    _.each(this.menuItems, function(item){
      item.active = false;
      _.each(item.children, function(obj){
        obj.active = false;
      })
    })

    item.active = true;
  }
}
