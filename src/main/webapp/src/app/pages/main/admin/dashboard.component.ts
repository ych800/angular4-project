import { Component,OnInit } from '@angular/core';
import {_HttpClient} from '../../../core/httpClient.module';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector:'admin-dashboard',
  templateUrl:'./dashboard.component.html',
  styles:[`
    .echarts-item{height:380px;}
  `],
})

export class DashboardComponent implements OnInit{
  private timeData: Array<string> = [];
  chartInstance :any = null;
  private serverList :any = [];

  constructor(private http:_HttpClient,
              private toastr:ToastrService){

  }

 //获取服务器数据;
  private getServiceData (){
    this.http.post('api/server/list.json',{
      server:{},
      pager:{pageNumber:1, pageSize:10}
    }).then((res)=>{
      if(res.status == 0){
        this.serverList = res.map.content;
        this.loadData();
      }
    });
  }

  onChartInit(ec) {
    this.chartInstance = ec;
  }

  private loadData(){
    let $this = this;
    let dateItem = new Date(new Date().getTime() - 24 * 60 * 60 * 1000).getTime();

    this.http.post('api/serverlog/list.json',{gradeDate:dateItem,pager:{pageSize:1000,pageNumber:1}}).then((res)=>{
      if(res.status == 0 && res.map && res.map.content){
        let data = res.map.content.reverse();
        let o: any = [];
        let seriesObj: any = [];

        if(data && data.length){
          o = _.groupBy(data,'server');
        }

        if(this.serverList.length){
          this.chartOption.xAxis.data = _.pluck(o[this.serverList[0].ip],'createdate').map(function (str) {
            let D = new Date(str);
            let y = D.getFullYear();
            let m = D.getMonth() + 1;
            let d = D.getDate();
            let hours = D.getHours() < 10 ? '0'+D.getHours() :D.getHours();
            let minute = D.getMinutes() < 10 ? '0'+D.getMinutes() :D.getMinutes();
            let second = D.getSeconds()< 10 ? '0'+D.getSeconds() :D.getSeconds();

            return m + '/' + d + ' '+ hours + ':' + minute ;
          });

          this.chartOption.legend.data = _.pluck(this.serverList,'name');

          for(let i=0;i<this.serverList.length;i++){
            seriesObj.push({
              name: this.serverList[i].name,
              type:'line',
              data: _.map(_.pluck(o[this.serverList[i].ip],'traffic'),function (item) {
                return (item / 1000000).toFixed(0);
              })
            });
          }

          this.chartOption.series = seriesObj;
          this.chartInstance.setOption(this.chartOption);
        }

      }
    });

    /*//5s reload data;
    setTimeout(function () {
      $this.loadData();
    },5000)*/

  }

  ngAfterViewInit(){
    this.getServiceData();
  }

  ngOnInit(){
  }

  //echarts option
  public chartOption = {
    title: {
      text: '服务器流量实时监控图',
      left:'10',
      textStyle:{
        color:'#fff'
      },
    },
    tooltip: {
      trigger: 'axis',
      formatter:function(params){
        var str = (params[0].axisValue || '') + '<br/>';
        if(params.length){
          for(var i=0;i<params.length;i++){
            str += '<span style="display:inline-block;height: 10px;width:10px;border-radius: 50%;background-color: '+params[i].color +'"></span>  '+ params[i].seriesName + ' : ' + params[i].value+' (MB)<br/>'
          }
          return str ;
        }
      }
    },
    legend: {
      show:true,
      right:'center',
      orient:'horizontal',
      textStyle:{
        color:'#fff'
      },
      data:[]
    },
    color:['#8fb800','#1f9d90','#ff9800','#2cabd0','#e75555','#9c27b0'],
    grid: {
      left: '3%',
      right: '8%',
      bottom: '70',
      top:'70',
      containLabel: true
    },
    toolbox: {
      show:false
    },
    dataZoom: [
      {
        show: true,
        type: 'slider',
        realtime: true,
        start: 0,
        textStyle:{
          color:'#fff'
        }
      }
    ],
    xAxis: {
      type: 'category',
      name:'时间',
      nameLocation:'end',
      boundaryGap: false,
      nameTextStyle:{
        color:'#fff'
      },
      axisLine:{
        lineStyle:{color:"#fff"}
      },
      data: []
    },
    yAxis: {
      type: 'value',
      name:'流量(MB)',
      nameLocation:'end',
      nameTextStyle:{
        color:'#fff'
      },
      axisLine:{lineStyle:{color:"#fff"},},
      splitLine:{show:false}
    },
    series: []
  }




}
