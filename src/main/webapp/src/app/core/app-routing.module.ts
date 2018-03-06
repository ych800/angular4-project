/**
 * APP 路由配置文件
 */
import {NgModule, OnInit} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


import { LoginComponent } from '../pages/login/login.component';
import { AdminComponent } from '../pages/main/admin.component';

import { UserComponent } from '../pages/main/admin/user.component';
import { ComboComponent } from '../pages/main/admin/combo.component';
import { OrderComponent } from '../pages/main/admin/order.component';
import { ServerComponent } from '../pages/main/admin/server.component';
import { DashboardComponent } from '../pages/main/admin/dashboard.component';
import {MainComponent} from "../pages/web/main.component";
import {IndexComponent} from "../pages/web/index.component";



const routes: Routes = [
  {path: '', redirectTo: '/index', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {
    path: 'admin',
    component: AdminComponent,
    children:[
      {path: "dashboard", component: DashboardComponent},
      {path: "user", component: UserComponent},
      {path: "server", component: ServerComponent},
      {path: "combo", component: ComboComponent},
      {path: 'order', component: OrderComponent}
    ]
  },
  {
    path: "index",
    component: IndexComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
