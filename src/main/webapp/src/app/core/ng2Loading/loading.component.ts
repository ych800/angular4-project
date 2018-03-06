import { Component,OnInit ,Input} from '@angular/core';
import { LoadingService,LoadingAttrType,LoadingAttr } from './loading.service';

@Component({
  selector:'ng2-loading',
  templateUrl:'./loading.component.html',
  styleUrls:['./loading.component.css']
})

export class LoadingComponent implements OnInit{
  @Input() size:number = 36;
  @Input() color:string = 'rgba(103, 240, 62, 0.69)';
  @Input() show:boolean = true;
  private spinnerSize = Math.floor(this.size/4);

  constructor(public service:LoadingService){

  }

  ngOnInit():any{
    this.service.attrsObserve.subscribe((attr: LoadingAttr)=>{
      if (attr.type === LoadingAttrType.SHOW  &&  _.isEmpty(attr.value)) {
        this.show = attr.value;
      } else if (attr.type === LoadingAttrType.COLOR) {
        this.color = attr.value;
      } else if (attr.type === LoadingAttrType.SIZE) {
        this.size = attr.value;
      }
    });
  }


}
