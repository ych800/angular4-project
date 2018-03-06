package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.Server;
import cn.mxlog.sscloud.controller.params.ServerCom;
import cn.mxlog.sscloud.service.ServerService;

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
@RequestMapping("/api/server")
public class ServerController {
	private BaseResult result;

	@Autowired
	private ServerService serverService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody Server server) {
        result = new BaseResult();
        if (server == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.serverService.save(server);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody Server server) {
        server = this.serverService.findById(server);

        result = new BaseResult();
        result.setData(server);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.serverService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody ServerCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.serverService.list(com.getServer(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody Server server) {
        result = new BaseResult();
        if (server == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.serverService.delete(server);
        }

        return result;
    }

}
