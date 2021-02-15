package cn.org.chtf.card.manage.menu.service;

import java.util.List;

import cn.org.chtf.card.manage.menu.pojo.Menu;
import cn.org.chtf.card.support.util.ResultModel;

public interface MenuService {

	/**
	 * 为tree加载所有功能节点
	 * @param menuId
	 * @return
	 */
	public ResultModel selectMenuForTree(int menuId);

	/**
	 * 根据主键删除对应记录
	 * @param menuId
	 * @return
	 */
	public ResultModel deleteMenu(int menuId);
	/**
	 * 根据是否为0，添加或更新记录
	 * @param menu
	 * @return
	 */
	public ResultModel updateMenu(Menu menu);
	/**
	 * 根据菜单名关联菜单ID
	 * @param menuName
	 * @return
	 */
    public ResultModel getMenuByName(String menuName);
    /**
     * 获取所有日文栏目
     * @return
     */
    public List<Menu> getJPMenu();
    /**
     * 获取所有中文栏目
     * @return
     */
    public List<Menu> getCNMenu();
    /**
     * 获取所有英文栏目
     * @return
     */
    public List<Menu> getENMenu();
    /**
     * 根据主键获取menu
     * @param menuId
     * @return
     */
    public Menu getMenuByMenuId(Integer menuId);
    /**
     * 获取子菜单
     * @param menuId
     * @return
     */
    public List<Menu>selectChilds(Integer menuId);
    /**
     * 获取兄弟菜单
     * @param menuId
     * @return
     */
    public ResultModel selectBrotherMenu(Integer menuId);
}
