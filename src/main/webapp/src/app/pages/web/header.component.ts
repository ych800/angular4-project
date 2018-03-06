import { Component, OnInit,TemplateRef } from '@angular/core';
import {NgbDropdownConfig} from '@ng-bootstrap/ng-bootstrap';
import { BsModalService ,BsModalRef} from 'ngx-bootstrap/modal';
import {_HttpClient} from '../../core/httpClient.module';

@Component({
  selector: 'web-header',
  templateUrl: './header.component.html',
  providers: [NgbDropdownConfig]
})
export class WebHeaderComponent implements OnInit {
  scrolled:boolean = true;

  item:any = {};

  modalRef: BsModalRef;

  constructor(profileDropdownConfig: NgbDropdownConfig,
              private modalService: BsModalService,
              private http:_HttpClient ) {

    profileDropdownConfig.placement = 'bottom-right';
    profileDropdownConfig.autoClose = false;

  }
  public openRegister(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  ngOnInit() {

  }

  public register(){
    this.http.post("register.json",this.item).then(res => {
        console.log(res);
    });
  }

}
