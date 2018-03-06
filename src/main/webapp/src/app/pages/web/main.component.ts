import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {NgbDropdownConfig} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'web-main',
  templateUrl: './main.component.html',
  providers: [NgbDropdownConfig],
  styleUrls:["../../../styles/style.scss"],
  encapsulation: ViewEncapsulation.None
})
export class MainComponent implements OnInit {
  scrolled:boolean = true;

  constructor(profileDropdownConfig: NgbDropdownConfig) {

    profileDropdownConfig.placement = 'bottom-right';
    profileDropdownConfig.autoClose = false;

  }

  ngOnInit() {

  }

}
