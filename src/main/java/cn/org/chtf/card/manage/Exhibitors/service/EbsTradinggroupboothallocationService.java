package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroupboothallocation;
import cn.org.chtf.card.support.util.ResultModel;

/**
 * 参展商管理-交易团展位分配Service
 * @author ggwudivs
 */
public interface EbsTradinggroupboothallocationService {

	/**
	 * 查询参展商管理-交易团展位分配列表
	 */
	List<EbsTradinggroupboothallocation> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-交易团展位分配
	 */
    EbsTradinggroupboothallocation findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-交易团展位分配
	 */
	List<EbsTradinggroupboothallocation> findByMap(Map<String, Object> map);

	/**
	 * 新增参展商管理-交易团展位分配
	 */
	int save(EbsTradinggroupboothallocation ebsTradinggroupboothallocation);

	/**
	 * 修改参展商管理-交易团展位分配
	 */
	int update(EbsTradinggroupboothallocation ebsTradinggroupboothallocation);

	/**
	 * 删除参展商管理-交易团展位分配
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);
    
    /**
     * 返回本届次零散参展商申请展位所需信息
     * @param sessionid
     * @return
     */
	ResultModel selectAreaAndShowroomType(Integer sessionid);
    
    /**
     * @author wushixing
         * 返回本届次指定交易团可以分配给指定企业的展区、展厅类型和展位
     * @param sessionid
     * @return
     */
	public ResultModel selectAreaAndShowroomTypeAndBooth(Integer memberId,Integer companyId,Integer sessionid);

	List<Map<String, Object>> getShowRoomList1(Map<String, Object> map);

	List<Map<String, Object>> getShowRoomList2(Map<String, Object> map);

	int submitAllocation(Map<String, Object> map);

	List<Map<String, Object>> getBoothAllocationInfoList(Map<String, Object> map);

	int getBoothAllocationInfoListCount(Map<String, Object> map);

	List<Map<String, Object>> getRemainingBoothInfoList(Map<String, Object> map);
	
	int getRemainingBoothInfoListCount(Map<String, Object> map);
}
