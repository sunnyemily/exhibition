package cn.org.chtf.card.manage.common.pojo;

import java.util.List;

public class AlterMenuParameter {
	/**
	 * 要迁移的信息主键列表
	 */
	private List<Integer> idList;
	/**
	 * 迁移的菜单主键
	 */
	private Integer menuId;
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public List<Integer> getIdList() {
		return idList;
	}
	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
}
