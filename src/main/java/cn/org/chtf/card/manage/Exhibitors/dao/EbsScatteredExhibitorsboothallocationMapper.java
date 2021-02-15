package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-交易团展位分配DAO
 * @author ggwudivs
 */
public interface EbsScatteredExhibitorsboothallocationMapper {

	List<Map<String, Object>> getScatteredExhibitorsList(Map<String, Object> map);

	int getScatteredExhibitorsListCount(Map<String, Object> map);

	List<Map<String, Object>> getApplyBoothInfo(Map<String, Object> map);

	int allocationBooth(Map<String, Object> map);

	int cancleAllocationBooth(Map<String, Object> map);

	List<Map<String, Object>> selectScatteredTraddingGroup(Map<String, Object> map);

	Map<String, Object> loadCount(Map<String, Object> map);

	List<Map<String, Object>> queryExportInfo(Map<String, Object> map);

}
