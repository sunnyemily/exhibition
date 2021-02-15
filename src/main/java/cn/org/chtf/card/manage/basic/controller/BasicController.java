package cn.org.chtf.card.manage.basic.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.basic.pojo.Basic;
import cn.org.chtf.card.manage.basic.service.BasicService;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class BasicController {
	@Resource(name="BasicServiceImpl")
	private BasicService basicService;
	@RequestMapping(value="/manage/basic/updateBasic/{cid}")
	public ResultModel updateBasic(Basic basic,@PathVariable int cid) {
		basic.setMenuId(cid);
		return basicService.updateBasic(basic);
	}
	@RequestMapping(value="/manage/basic/getBasic/{cid}")
	public ResultModel getBasic(@PathVariable int cid) {
		return basicService.getBasic(cid);
	}
}
