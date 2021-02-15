package cn.org.chtf.card.manage.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.function.dao.FunctionMapper;
import cn.org.chtf.card.manage.function.pojo.Function;
import cn.org.chtf.card.manage.menu.dao.MenuMapper;
import cn.org.chtf.card.manage.menu.pojo.Menu;
import cn.org.chtf.card.manage.menu.service.MenuService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("MenuServiceImpl")

public class MenuServiceImpl implements MenuService {


	@Autowired
	private MenuMapper menuDAO;
	@Autowired
	private FunctionMapper functionDAO;
	
	public ResultModel selectMenuForTree(int menuId){
		List<Menu> Functions = menuDAO.selectAll();
		ResultModel result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,Functions);
		return result;
	}
	public ResultModel deleteMenu(int menuId){
		ResultModel result;
		try {
			int childNodeCount = (menuDAO.selectChilds(menuId)).size();
			if(childNodeCount>0) {
				result = new ResultModel(WConst.ERROR,WConst.HAVECHILDERROR,null);

			}
			else
			{
				int deleteCount = menuDAO.deleteByPrimaryKey(menuId);
				result = new ResultModel(WConst.SUCCESS,WConst.DELETED,deleteCount);
			}
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.DELETEDERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel updateMenu(Menu menu) {
		ResultModel result;
		try {
			if(menu.getMenuId()==0) {
				//1.插入并返回插入的菜单主键
				menuDAO.insertSelective(menu);
				int menuId = menu.getMenuId();
				//2.获取插入菜单的父菜单的主键
				Integer parentId = menu.getMenuParentId();
				//3.获取功能的父节点
				Function parentFunction = functionDAO.getFunctionByMenuId(parentId);
				//4.设置菜单的关联功能
				Function relatedFunction = new Function();
				relatedFunction.setFunctionIsInterface(false);
				relatedFunction.setFunctionMenuId(menuId);
				relatedFunction.setFunctionName(menu.getMenuName());				
				relatedFunction.setFunctionParentid(parentFunction.getFunctionId());
				relatedFunction.setLanguage(menu.getMenuLanguage());
				relatedFunction.setFunctionOrder(menu.getMenuOrder());
				//4.1根据菜单类型设置相关信息
				List<Function> interfaceFunction = new ArrayList<Function>();
				switch(menu.getMenuType()) {
				case 0:
					//4.1.1查看功能设置
					Function readFunction = new Function();
					readFunction.setFunctionIsInterface(true);
					readFunction.setFunctionMenuId(menuId);
					readFunction.setFunctionName("查看");
					readFunction.setFunctionOrder(1);
					readFunction.setFunctionParentid(parentFunction.getFunctionId());
					readFunction.setLanguage(menu.getMenuLanguage());
					readFunction.setFunctionUrl("basic/getBasic/"+menuId);
					interfaceFunction.add(readFunction);
					//4.1.2更新功能
					Function updateFunction = new Function();
					updateFunction.setFunctionIsInterface(true);
					updateFunction.setFunctionMenuId(menuId);
					updateFunction.setFunctionName("更新");
					updateFunction.setFunctionOrder(2);
					updateFunction.setFunctionParentid(parentFunction.getFunctionId());
					updateFunction.setLanguage(menu.getMenuLanguage());
					updateFunction.setFunctionUrl("basic/updateBasic/"+menuId);
					interfaceFunction.add(updateFunction);
					
					relatedFunction.setFunctionUrl("basic/basic.html");
					break;
				case 1:
				case 2:
				case 3:
				case 7:
				case 8:
					//4.1.1查看功能设置
					Function readFunction1 = new Function();
					readFunction1.setFunctionIsInterface(true);
					readFunction1.setFunctionMenuId(menuId);
					readFunction1.setFunctionName("查看");
					readFunction1.setFunctionOrder(1);
					readFunction1.setFunctionParentid(parentFunction.getFunctionId());
					readFunction1.setLanguage(menu.getMenuLanguage());
					readFunction1.setFunctionUrl("article/getArticles/"+menuId);
					interfaceFunction.add(readFunction1);
					//4.1.2更新功能
					Function updateFunction1 = new Function();
					updateFunction1.setFunctionIsInterface(true);
					updateFunction1.setFunctionMenuId(menuId);
					updateFunction1.setFunctionName("编辑");
					updateFunction1.setFunctionOrder(2);
					updateFunction1.setFunctionParentid(parentFunction.getFunctionId());
					updateFunction1.setLanguage(menu.getMenuLanguage());
					updateFunction1.setFunctionUrl("article/updateArticle");
					interfaceFunction.add(updateFunction1);
					//4.1.3删除功能
					Function deleteFunction = new Function();
					deleteFunction.setFunctionIsInterface(true);
					deleteFunction.setFunctionMenuId(menuId);
					deleteFunction.setFunctionName("删除");
					deleteFunction.setFunctionOrder(3);
					deleteFunction.setFunctionParentid(parentFunction.getFunctionId());
					deleteFunction.setLanguage(menu.getMenuLanguage());
					deleteFunction.setFunctionUrl("article/deleteArticles");
					interfaceFunction.add(deleteFunction);
					//4.1.4详情功能
					Function infoFunction = new Function();
					infoFunction.setFunctionIsInterface(true);
					infoFunction.setFunctionMenuId(menuId);
					infoFunction.setFunctionName("单页查询");
					infoFunction.setFunctionOrder(4);
					infoFunction.setFunctionParentid(parentFunction.getFunctionId());
					infoFunction.setLanguage(menu.getMenuLanguage());
					infoFunction.setFunctionUrl("article/getArticle");
					interfaceFunction.add(infoFunction);
					String functionUrl = "article/article.html";
					if(null!=menu.getMenuType()) {
						if(menu.getMenuType().equals(2)) {
							functionUrl = "article/video.html";
						}
						else if(menu.getMenuType().equals(3)) {
							functionUrl = "article/research.html";
						}
						else if(menu.getMenuType().equals(7)) {
							functionUrl = "article/teacher.html";
						}
						else if(menu.getMenuType().equals(8)) {
							functionUrl = "article/leader.html";
						}
					}
					relatedFunction.setFunctionUrl(functionUrl);
					break;
				case 4:
				case 5:
					//4.1.1查看功能设置
					Function readFunction2 = new Function();
					readFunction2.setFunctionIsInterface(true);
					readFunction2.setFunctionMenuId(menuId);
					readFunction2.setFunctionName("查看");
					readFunction2.setFunctionOrder(1);
					readFunction2.setFunctionParentid(parentFunction.getFunctionId());
					readFunction2.setLanguage(menu.getMenuLanguage());
					readFunction2.setFunctionUrl("friendlink/getFriendlinks/"+menuId);
					interfaceFunction.add(readFunction2);
					//4.1.2更新功能
					Function updateFunction2 = new Function();
					updateFunction2.setFunctionIsInterface(true);
					updateFunction2.setFunctionMenuId(menuId);
					updateFunction2.setFunctionName("编辑");
					updateFunction2.setFunctionOrder(2);
					updateFunction2.setFunctionParentid(parentFunction.getFunctionId());
					updateFunction2.setLanguage(menu.getMenuLanguage());
					updateFunction2.setFunctionUrl("friendlink/updateFriendlink");
					interfaceFunction.add(updateFunction2);
					//4.1.3删除功能
					Function deleteFunction1 = new Function();
					deleteFunction1.setFunctionIsInterface(true);
					deleteFunction1.setFunctionMenuId(menuId);
					deleteFunction1.setFunctionName("删除");
					deleteFunction1.setFunctionOrder(3);
					deleteFunction1.setFunctionParentid(parentFunction.getFunctionId());
					deleteFunction1.setLanguage(menu.getMenuLanguage());
					deleteFunction1.setFunctionUrl("friendlink/deleteFriendlinks");
					interfaceFunction.add(deleteFunction1);
					
					relatedFunction.setFunctionUrl("friendlink/friendlink.html");
					break;
				}
				//5.添加菜单关联功能
				functionDAO.insertSelective(relatedFunction);
				//6.添加功能的接口
				for(Function function : interfaceFunction) {
					function.setFunctionParentid(relatedFunction.getFunctionId());
					functionDAO.insertSelective(function);
				}
			}
			else {
				menuDAO.updateByPrimaryKeySelective(menu);
			}
		
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e);
		}
		return result;
	}
    public ResultModel getMenuByName(String menuName) {
		ResultModel result;

    	try {
    		Menu menu = menuDAO.getMenuByName(menuName);
    		result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,menu);

    	}
    	catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
    	}
    	return result;
    }
    public List<Menu> getJPMenu(){
    	List<Menu> allMenu = menuDAO.selectLanguageMenu("JP");
    	return formatMenu(allMenu,3);
    }
    public List<Menu> getCNMenu(){
    	List<Menu> allMenu = menuDAO.selectLanguageMenu("CN");
    	return formatMenu(allMenu,1);
    }
    public List<Menu> getENMenu(){
    	List<Menu> allMenu = menuDAO.selectLanguageMenu("EN");
    	return formatMenu(allMenu,2);
    }
    /**
     * 格式化menu，加入子menu
     */
    private List<Menu> formatMenu(List<Menu> allMenu,Integer topMenuId){
    	List<Menu> topMenu = new ArrayList<Menu>();
    	for(Menu menu : allMenu) {
    		if(menu.getMenuParentId().equals(topMenuId)) {
    			topMenu.add(addChildMenu(allMenu,menu));
    		}
    	}
    	return topMenu;
    }
    /**
     * 递归取子菜单
     * @param allMenu
     * @param currentMenu
     */
    private Menu addChildMenu(List<Menu> allMenu,Menu currentMenu) {
    	for(Menu menu : allMenu) {
    		if(menu.getMenuParentId().equals(currentMenu.getMenuId())) {//找到子菜单
    			currentMenu.addChildMenu(addChildMenu(allMenu,menu));			
    		}    		
    	}
    	return currentMenu;
    	
    }
    public Menu getMenuByMenuId(Integer menuId) {
    	return menuDAO.selectByPrimaryKey(menuId);
    }
    public List<Menu>selectChilds(Integer menuId){
    	return menuDAO.selectChilds(menuId);
    }
    public ResultModel selectBrotherMenu(Integer menuId){
		ResultModel result;

    	try {
    		result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,menuDAO.selectBrotherMenu(menuId));

    	}
    	catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
    	}
    	return result;
    }

}
