package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.UserOrder;
import cn.mxlog.sscloud.controller.params.UserOrderCom;
import cn.mxlog.sscloud.service.UserOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created on 18 十月 2017
 *
 * @author F.Du
 */
@RestController
@RequestMapping("/api/userorder")
public class UserOrderController {
	private BaseResult result;

	@Autowired
	private UserOrderService userorderService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody UserOrder userorder) {
        result = new BaseResult();
        if (userorder == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.userorderService.save(userorder);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody UserOrder userorder) {
        userorder = this.userorderService.findById(userorder);

        result = new BaseResult();
        result.setData(userorder);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.userorderService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody UserOrderCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.userorderService.list(com.getUserOrder(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody UserOrder userorder) {
        result = new BaseResult();
        if (userorder == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.userorderService.delete(userorder);
        }

        return result;
    }


    @PostMapping("new.json")
    public BaseResult newOrder(@RequestBody UserOrderCom com){
        BaseResult result = new BaseResult();

        // 后台生成订单时, 默认状态为已支付
        if(com.getUserOrder() != null) {
            com.getUserOrder().setStatus(10);
        }

        this.userorderService.newOrder(com);
        return result;
    }

}
