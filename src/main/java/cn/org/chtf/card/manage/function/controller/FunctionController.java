package cn.org.chtf.card.manage.function.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.function.pojo.Function;
import cn.org.chtf.card.manage.function.service.FunctionService;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class FunctionController {
	@Resource(name = "FunctionServiceImpl")
	FunctionService functionService;
	
	@RequestMapping(value="/manage/system/getAllForTree")
	public ResultModel getAllForTree(int functionId) {
		return  functionService.selectAllForTree(functionId);
	}
	@RequestMapping(value="/manage/system/deleteFunction")
	public ResultModel deleteFunction(int functionId) {
		return  functionService.deleteFunction(functionId);
	}
	@RequestMapping(value="/manage/system/updateFunction")
	public ResultModel updateFunction(HttpServletRequest request,Function function) {
		return  functionService.updateFunction(function);
	}
	@RequestMapping(value="/manage/system/getAuthorizedFunctions")
	public ResultModel getAuthorizedFunctions(HttpSession session) {
		return  functionService.getAuthorizedFunctions(session);
	}
}
