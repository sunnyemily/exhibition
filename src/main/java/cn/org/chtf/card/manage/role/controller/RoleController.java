package cn.org.chtf.card.manage.role.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.role.pojo.Role;
import cn.org.chtf.card.manage.role.service.RoleService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class RoleController {
	
	@Resource(name = "RoleServiceImpl")
	RoleService roleService;
	
	@RequestMapping(value="/manage/system/updateRole")
	public ResultModel updateRole(Role role) {
		
		return roleService.updateRole(role);
	}
	
	@RequestMapping(value="/manage/system/getRoles")
	public ResultModel getRoles(PageModel page) {
		
		return roleService.getRoles(page);
	}
	
	@RequestMapping(value="/manage/system/deleteRole")
	public ResultModel deleteRole(@RequestParam(value = "roleIdList[]") Integer[] roleIdList) {
		return roleService.deleteRole(roleIdList);
	}
}
