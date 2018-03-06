package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.UserTraffic;
import cn.mxlog.sscloud.controller.params.UserTrafficCom;
import cn.mxlog.sscloud.service.UserTrafficService;

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
@RequestMapping("/api/usertraffic")
public class UserTrafficController {
	private BaseResult result;

	@Autowired
	private UserTrafficService usertrafficService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody UserTraffic usertraffic) {
        result = new BaseResult();
        if (usertraffic == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.usertrafficService.save(usertraffic);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody UserTraffic usertraffic) {
        usertraffic = this.usertrafficService.findById(usertraffic);

        result = new BaseResult();
        result.setData(usertraffic);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.usertrafficService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody UserTrafficCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.usertrafficService.list(com.getUserTraffic(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody UserTraffic usertraffic) {
        result = new BaseResult();
        if (usertraffic == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.usertrafficService.delete(usertraffic);
        }

        return result;
    }

}
