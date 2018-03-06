package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.controller.params.UserLogCom;
import cn.mxlog.sscloud.model.UserLog;
import cn.mxlog.sscloud.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * Created on 18 十月 2017
 *
 * @author F.Du
 */
@RestController
@RequestMapping("/api/userlog")
public class UserLogController {
	private BaseResult result;

	@Autowired
	private UserLogService userlogService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody UserLog userlog) {
        result = new BaseResult();
        if (userlog == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.userlogService.save(userlog);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody UserLog userlog) {
        userlog = this.userlogService.findById(userlog);

        result = new BaseResult();
        result.setData(userlog);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.userlogService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody UserLogCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.userlogService.list(com.getUserLog(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody UserLog userlog) {
        result = new BaseResult();
        if (userlog == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.userlogService.delete(userlog);
        }

        return result;
    }
}
