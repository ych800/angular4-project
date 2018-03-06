package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.controller.params.ServerLogCom;
import cn.mxlog.sscloud.model.ServerLog;
import cn.mxlog.sscloud.service.ServerLogService;
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
@RequestMapping("/api/serverlog")
public class ServerLogController {
	private BaseResult result;

	@Autowired
	private ServerLogService serverlogService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody ServerLog serverlog) {
        result = new BaseResult();
        if (serverlog == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.serverlogService.save(serverlog);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody ServerLog serverlog) {
        serverlog = this.serverlogService.findById(serverlog);

        result = new BaseResult();
        result.setData(serverlog);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.serverlogService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody ServerLogCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.serverlogService.list(com.getServerLog(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody ServerLog serverlog) {
        result = new BaseResult();
        if (serverlog == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.serverlogService.delete(serverlog);
        }

        return result;
    }


}
