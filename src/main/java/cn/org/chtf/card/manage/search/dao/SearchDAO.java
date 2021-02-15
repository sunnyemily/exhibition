package cn.org.chtf.card.manage.search.dao;

import java.util.List;

import cn.org.chtf.card.manage.search.pojo.Search;
import cn.org.chtf.card.manage.search.pojo.SearchParameter;
import cn.org.chtf.card.support.util.PageModel;

public interface SearchDAO {    
    List<Search> getList(SearchParameter page);    
    int getTotal(SearchParameter page);
}