import { Component, OnInit,ViewEncapsulation } from '@angular/core';
import {NgbDropdownConfig} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'web-index',
  templateUrl: './index.component.html',
  providers: [NgbDropdownConfig],
  styleUrls:["../../../styles/style.scss"],
  encapsulation: ViewEncapsulation.None
})
export class IndexComponent implements OnInit {
  scrolled:boolean = true;

  constructor() {


  }

  ngOnInit() {

  }

}
