package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.Map;


/**
 * 参展商管理-展位DAO
 * @author ggwudivs
 */
public interface EbsBoothAuditMapper {

	/**删除企业展位审核信息
	 * @param map
	 * @return
	 */
	int deleteByCompany(Map<String, Object> map);
	
}
