package cn.org.chtf.card.manage.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.menu.dao.MenuMapper;
import cn.org.chtf.card.manage.menu.pojo.Menu;
import cn.org.chtf.card.manage.search.dao.SearchDAO;
import cn.org.chtf.card.manage.search.pojo.Search;
import cn.org.chtf.card.manage.search.pojo.SearchParameter;
import cn.org.chtf.card.manage.search.service.SearchService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@Service("SearchServiceImpl")
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDAO searchDAO;
	@Autowired
	private MenuMapper menuDAO;
	public ResultModel search(String keyword,Integer pageSize,Integer pageIndex,String language) {
		ResultModel result = new ResultModel();
		SearchParameter page = new SearchParameter();
		page.setKeywords(keyword);
		page.setLimit(pageSize);
		page.setPage(pageIndex);
		page.setField("title");
		page.setOrder("ASC");
		page.setLanguage(language);
		result.setCount(searchDAO.getTotal(page));
		
		List<Search> list = searchDAO.getList(page);
		if(list!=null && list.size()>0) {
			List<Menu> menus = menuDAO.selectAll();
			for(Search search : list) {
				search.setTopmenuid(getTopMenu(menus,search.getMenuid()).getMenuId());
				search.setLanguage(getTopMenu(menus,search.getMenuid()).getMenuLanguage());
			}
		}
		result.setResult(list);
		return result;
		
	}
	private Menu getTopMenu(List<Menu> menus,Integer menuId) {
		Menu tMenu = null;
		for(Menu menu : menus) {
			if(menu.getMenuId().equals(menuId)) {
				Integer parentId = menu.getMenuParentId();
				if(parentId<5) {
					tMenu = menu;
					break;
				}
				else {
					tMenu = getTopMenu(menus,parentId);
				}
			}
		}
		return tMenu;
	}
}
