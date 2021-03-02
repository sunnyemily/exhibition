package cn.org.chtf.card.manage.Decorators.dao;

import java.util.List;
import java.util.Map;

/**
 * 企业信息DAO
 * @author ggwudivs
 */
public interface DecoratorEbsDecoratorManageMapper {

    /**
     * 查询企业信息列表
     */
    List<Map<String, Object>> list(Map<String, Object> map);

	/**
     * 通过map查询单个企业信息
     */
    int listcount(Map<String, Object> map);

	/**
	 * @Description: 更新企业信息
	 * @date: 2020年6月24日 下午7:56:52
	 * @author: ggwudivs
	 * @param map
	 * @return:
	 * @return: int
	 */
	int updateCompanyInfo(Map<String, Object> map);
    
	/**查询单个企业信息
	 * @param map
	 * @return
	 */
	Map<String, Object> selectCompanyInfo(Map<String, Object> map);

	List<Map<String, Object>> Previouslist(Map<String, Object> map);

	int Previouslistcount(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);

	List<Map<String, Object>> GetDownLoadInfo(Map<String, Object> map);
}
