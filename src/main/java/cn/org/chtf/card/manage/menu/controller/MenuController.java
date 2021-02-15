package cn.org.chtf.card.manage.menu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.menu.pojo.Menu;
import cn.org.chtf.card.manage.menu.service.MenuService;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class MenuController {

	@Resource(name = "MenuServiceImpl")
	MenuService menuService;
	
	@RequestMapping(value="/manage/system/getMenuForTree")
	public ResultModel getAllForTree(int menuId) {
		return  menuService.selectMenuForTree(menuId);
	}
	@RequestMapping(value="/manage/system/deleteMenu")
	public ResultModel deleteMenu(int menuId) {
		return  menuService.deleteMenu(menuId);
	}
	@RequestMapping(value="/manage/system/updateMenu")
	public ResultModel updateMenu(Menu menu) {
		return  menuService.updateMenu(menu);
	}
	@RequestMapping(value="/manage/system/getMenuByName")
    public ResultModel getMenuByName(String menuName) {
		return  menuService.getMenuByName(menuName);
    }
	@RequestMapping(value="/Common/getBrotherMenu")
	public ResultModel getBrotherMenu(Integer menuId) {
		return  menuService.selectBrotherMenu(menuId);
	}
}
