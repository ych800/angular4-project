package cn.mxlog.sscloud.controller;

import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.User;
import cn.mxlog.sscloud.service.UserOrderService;
import cn.mxlog.sscloud.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by F.Du on 2017/10/17.
 */
@RestController
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderService orderService;

    @PostMapping("login.json")
    public BaseResult userLogin(@RequestBody User user, HttpSession session) {
        BaseResult result = this.userService.login(user);

        if (result.getStatus() == 0) {
            session.setAttribute("userinfo", result.getData());
        }


        return result;
    }


    @PostMapping("register.json")
    public BaseResult userRegister(@RequestBody User user, HttpSession session) {
        BaseResult result = new BaseResult();
        if (StringUtils.isEmpty(user.getEmail()) ||
                StringUtils.isEmpty(user.getPortPwd())) {
            result.setStatus(-1);
            result.setMessage("参数错误");
            return result;
        }

        Iterable<User> list=this.userService.list(user, new Pager());
        if(list != null && list.iterator().hasNext()){
            result.setStatus(-1000);
            result.setMessage("用户名或邮箱已经存在");
            return result;
        }

        int ret = this.userService.save(user);

        result.setStatus(ret);
        result.setData(user);
        if (ret == 0) {
            user.setPortPwd(null);
            user.setPassword(null);
            session.setAttribute("userinfo", user);
        }
        return result;
    }


    @PostMapping("/loginfo.json")
    public BaseResult userLoginInfo(HttpSession session) {
        BaseResult result = new BaseResult();
        User user = (User)session.getAttribute("userinfo");

        if (user == null) {
            result.setStatus(-1);
            result.setMessage("User not logged");
            return result;
        }

        result.setData(user);
        return result;
    }
}
