package cn.org.chtf.card.manage.Decorators.service;

import cn.org.chtf.card.common.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 企业信息Service
 * @author ggwudivs
 */
public interface DecoratorEbsDecoratorManageService {

	/**
	 * 查询企业信息列表
	 */
	List<Map<String, Object>> list(Map<String, Object> map);

	int listcount(Map<String, Object> map);

	int updateCompanyInfo(Map<String, Object> map);

	R UseScatteredExhibitors(Map<String, Object> map);
	
	Map<String, Object> selectCompanyInfo(Map<String, Object> map);

	List<Map<String, Object>> Previouslist(Map<String, Object> map);

	int Previouslistcount(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);

	List<Map<String, Object>> GetDownLoadInfo(Map<String, Object> map);
	
}
