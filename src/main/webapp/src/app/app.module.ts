import {BrowserModule} from '@angular/platform-browser';

import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpModule} from '@angular/http';
import { NgStyle } from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ToastrModule} from 'ngx-toastr';
import {SlimLoadingBarModule} from 'ng2-slim-loading-bar';
import {SelectModule} from 'ng2-select';
import { ModalModule } from 'ngx-bootstrap'

import {AppRoutingModule} from './core/app-routing.module';
import  {KeepHtmlPipe} from './core/keepHtml.pipe';
import  {_HttpClient} from './core/httpClient.module';
import  {LoginModule} from './core/login.module';
import { LoadingComponent } from  './core/ng2Loading/loading.component';
import { LoadingService } from './core/ng2Loading/loading.service';

import {AppComponent} from './app.component';
import {LoginComponent} from './pages/login/login.component';
import {HeaderComponent} from './pages/main/header/header.component';
import {SidebarComponent} from './pages/main/sidebar/sidebar.component';
import {DatagridComponent} from './core/datagrid/datagrid.component';
import {LocalStorage, SessionStorage} from "./core/storage.module";
import { TrafficTransform } from './core/trafficTransform';
import  { EchartsDirective } from './core/echarts.directive';

import { AdminComponent } from './pages/main/admin.component';
import  { UserComponent } from './pages/main/admin/user.component';
import  { OrderComponent } from './pages/main/admin/order.component';
import  { ComboComponent } from './pages/main/admin/combo.component';
import  { ServerComponent } from './pages/main/admin/server.component';
import  { DashboardComponent } from './pages/main/admin/dashboard.component';

import {WebHeaderComponent} from "./pages/web/header.component";
import {MainComponent} from "./pages/web/main.component";
import {IndexComponent} from "./pages/web/index.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminComponent,
    HeaderComponent,
    SidebarComponent,
    DatagridComponent,
    KeepHtmlPipe,

    UserComponent,
    OrderComponent,
    ComboComponent,
    ServerComponent,
    DashboardComponent,

    // web
    MainComponent,
    IndexComponent,
    WebHeaderComponent,
    EchartsDirective,
    LoadingComponent
  ],
  entryComponents: [],
  imports: [
    BrowserModule,
    HttpModule,
    BrowserAnimationsModule,
    SelectModule,
    NgbModule.forRoot(),
    ModalModule.forRoot(),
    SlimLoadingBarModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-center',
      preventDuplicates: true,
      closeButton: true
    }),
    FormsModule,
    AppRoutingModule,
  ],
  providers: [LoginModule, _HttpClient, SessionStorage, LocalStorage,TrafficTransform, NgStyle,LoadingService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
