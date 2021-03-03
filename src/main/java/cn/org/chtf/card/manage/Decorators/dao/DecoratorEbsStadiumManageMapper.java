package cn.org.chtf.card.manage.Decorators.dao;

import java.util.List;
import java.util.Map;

/**
 * 搭建商报馆信息DAO
 * @author ggwudivs
 */
public interface DecoratorEbsStadiumManageMapper {

    /**
     * 查询搭建商报馆信息列表
     */
    List<Map<String, Object>> list(Map<String, Object> map);

	/**
     * 通过map查询单个搭建商报馆信息
     */
    int listcount(Map<String, Object> map);

	/**
	 * @Description: 更新搭建商报馆信息
	 * @date: 2020年6月24日 下午7:56:52
	 * @author: ggwudivs
	 * @param map
	 * @return:
	 * @return: int
	 */
	int updateStadiumInfo(Map<String, Object> map);
    
	/**查询单个搭建商报馆信息
	 * @param map
	 * @return
	 */
	Map<String, Object> selectStadiumInfo(Map<String, Object> map);
}
