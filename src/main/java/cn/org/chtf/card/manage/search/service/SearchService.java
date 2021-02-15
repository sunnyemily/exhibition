package cn.org.chtf.card.manage.search.service;

import cn.org.chtf.card.support.util.ResultModel;

public interface SearchService {
	public ResultModel search(String keyword,Integer pageSize,Integer pageIndex,String language);
}
