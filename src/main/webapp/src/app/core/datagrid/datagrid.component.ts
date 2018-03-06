/**
 * DataGrid插件
 *
 * options.columns: 定义数据表格列 {field:"数据Key", title: "列名称", template: "数据显示模板"}
 * options.pagination: 是否分页
 * options.onLoadSuccess: 数据加载完成后执行.
 * options.dataFilter: 数据过滤及数据处理, 加载数据后先执行filter,在填充到表格
 */

import {
  Component, OnInit, Input, Output, HostListener, SimpleChanges, ViewChild,
  ElementRef,
  EventEmitter
} from '@angular/core';

import {EventManager} from '@angular/platform-browser';


import {_HttpClient} from "../httpClient.module";
import { LoadingService } from '../ng2Loading/loading.service';

@Component({
  selector: 'datagrid',
  templateUrl: './datagrid.component.html'
})
export class DatagridComponent implements OnInit {

  isLoading: any;

  @Output('onLoadSuccess') onLoadSuccess: EventEmitter<any> = new EventEmitter<any>();
  @Input() options: any = {};
  @Input() data: any;
  @Input() dataUrl: any;
  @Input() searchParams: any = {pager: {pageNumber: 1, pageSize: 10}};


  constructor(private http: _HttpClient, private eventManager: EventManager,private loading :LoadingService) {
  }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this.searchParams) {
      this.searchParams = {pager: {pageNumber: 1, pageSize: 10}}
    }

    if (!this.data) {
      this.data = {list: [], total: 0};
    }

    // options第一次改变时,加载数据
    if (!_.isEmpty(this.options)) {
      // 不分页时, 将分页数传100000
      if (!this.options.pagination) {
        this.searchParams.pager.pageSize = 100000;
      }

    }
  }

  ngAfterViewInit() {

    if (this.options.autoload !== false && this.dataUrl) {
      this.reload(1);
    }
  }

  loadData() {
    //this.isLoading = true;
    this.loading.start();
    this.isLoading = this.http.post(this.dataUrl, this.searchParams).then(res => {
      this.loading.stop();
      if (res.status == 0) {
        if(this.options.dataFilter){
          this.data.list = this.options.dataFilter (res.map.content);
        }else{
          this.data.list = res.map.content;
        }

        this.data.total = res.map.totalElements;
      }
      //this.isLoading = false;
      this.loadedData();
    });
  }

  loadedData() {
    this.onLoadSuccess.emit(this.data);
    if (this.options.onLoadSuccess) {
      this.options.onLoadSuccess(this.data);
    }
  }

  @HostListener("reload")
  reload(pageNumber) {
    if (this.searchParams && this.searchParams.pager) {
      this.searchParams.pager.pageNumber = pageNumber || 1;
    }
    this.loadData();
  }

  empty(){
    this.data = {list: [], total: 0};
  }

  @ViewChild('table')
  table: ElementRef;

}
