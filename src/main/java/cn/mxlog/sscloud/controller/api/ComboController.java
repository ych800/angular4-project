package cn.mxlog.sscloud.controller.api;


import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.Combo;
import cn.mxlog.sscloud.controller.params.ComboCom;
import cn.mxlog.sscloud.service.ComboService;

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
@RequestMapping("/api/combo")
public class ComboController {
	private BaseResult result;

	@Autowired
	private ComboService comboService;

	
    @PostMapping("/save.json")
    public BaseResult save(@RequestBody Combo combo) {
        result = new BaseResult();
        if (combo == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.comboService.save(combo);
        }

        return result;
    }


    @PostMapping("/get.json")
    public BaseResult get(@RequestBody Combo combo) {
        combo = this.comboService.findById(combo);

        result = new BaseResult();
        result.setData(combo);

        return result;
    }

    @PostMapping("/all.json")
    public BaseResult list() {
        result = new BaseResult();
        result.setMap(this.comboService.list());
        return result;
    }

    @PostMapping("/list.json")
    public BaseResult list(@RequestBody ComboCom com) {
        result = new BaseResult();
        if (com.getPager() == null) {
            com.setPager(new Pager());
        }
        result.setMap(this.comboService.list(com.getCombo(), com.getPager()));
        return result;
    }

    @PostMapping("/delete.json")
    public BaseResult delete(@RequestBody Combo combo) {
        result = new BaseResult();
        if (combo == null) {
            result.setStatus(-1);
            result.setMessage("params is required");
        } else {
            this.comboService.delete(combo);
        }

        return result;
    }

}
