package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.User;
import cn.mxlog.sscloud.controller.params.UserCom;
import cn.mxlog.sscloud.service.UserService;

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
@RequestMapping("/api/user")
public class UserController {
	private BaseResult result;

	@Autowired
	private UserService userService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody User user) {
        result = new BaseResult();
        if (user == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.userService.save(user);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody User user) {
        user = this.userService.findById(user);

        result = new BaseResult();
        result.setData(user);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.userService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody UserCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.userService.list(com.getUser(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody User user) {
        result = new BaseResult();
        if (user == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.userService.delete(user);
        }

        return result;
    }

}
