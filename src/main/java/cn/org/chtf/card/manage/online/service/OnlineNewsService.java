package cn.org.chtf.card.manage.online.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.online.model.NewsParams;
import cn.org.chtf.card.support.util.ResultModel;



/**
 * 线上预约Service
 * @author lm
 */
public interface OnlineNewsService {

	List<Map> GetTopNews(int itop) throws UnsupportedEncodingException;

	Map<String, Object> GetNewsInfo(String newsid);

	ResultModel GetNewsList(NewsParams news) throws Exception;


	Map<String, Object> GetTopNewsForList();

	
	
}
