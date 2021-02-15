package cn.org.chtf.card.manage.menu.pojo;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private Integer menuId;

    private String menuName;
    
    private String menuOtherName;

    private String menuPicture;

    private String menuPicture2;

    private Integer menuOrder;

    private Integer menuParentId;

    private String menuLanguage;

    private Integer menuType;
    
    private String menuIntro;
    
    public String getMenuPicture2() {
		return menuPicture2;
	}
	public void setMenuPicture2(String menuPicture2) {
		this.menuPicture2 = menuPicture2;
	}
	public String getMenuIntro() {
		return menuIntro;
	}
	public void setMenuIntro(String menuIntro) {
		this.menuIntro = menuIntro;
	}
	private List<Menu> childMenu;
    public Menu() {
    	this.childMenu=new ArrayList<Menu>();
    }
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuPicture() {
        return menuPicture;
    }

    public void setMenuPicture(String menuPicture) {
        this.menuPicture = menuPicture == null ? null : menuPicture.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(Integer menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getMenuLanguage() {
        return menuLanguage;
    }

    public void setMenuLanguage(String menuLanguage) {
        this.menuLanguage = menuLanguage == null ? null : menuLanguage.trim();
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

	public List<Menu> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}

	public void addChildMenu(Menu childMenu) {
		this.childMenu.add(childMenu);
	}
	public String getMenuOtherName() {
		return menuOtherName;
	}
	public void setMenuOtherName(String menuOtherName) {
		this.menuOtherName = menuOtherName;
	}
}