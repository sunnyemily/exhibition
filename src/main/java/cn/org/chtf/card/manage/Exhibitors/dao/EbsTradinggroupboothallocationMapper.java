package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroupboothallocation;


/**
 * 参展商管理-交易团展位分配DAO
 * @author ggwudivs
 */
public interface EbsTradinggroupboothallocationMapper {

    /**
     * 通过id查询单个参展商管理-交易团展位分配
     */
    EbsTradinggroupboothallocation findById(Integer id);

    /**
     * 通过map查询单个参展商管理-交易团展位分配
     */
    List<EbsTradinggroupboothallocation> findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-交易团展位分配列表
     */
    List<EbsTradinggroupboothallocation> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个参展商管理-交易团展位分配
     */
    int listcount(Map<String, Object> map);
    
    /**
     * 返回本届次零散参展商申请展位所需信息
     * @param sessionid
     * @return
     */
    List<Map<String,Object>> selectAreaAndShowroomType(Integer sessionid);
    
    /**
     * @author wushixing
         * 返回本届次指定交易团可以分配给指定企业的展区、展厅类型和展位
     * @param sessionid
     * @return
     */
    List<Map<String,Object>> selectAreaAndShowroomTypeAndBooth(@Param("memberId")Integer memberId,@Param("companyId")Integer companyId,@Param("sessionid")Integer sessionid);

	List<Map<String, Object>> getShowRoomList1(Map<String, Object> map);

	List<Map<String, Object>> getShowRoomList2(Map<String, Object> map);

	int submitAllocation(Map<String, Object> map);

	List<Map<String, Object>> getBoothAllocationInfoList(Map<String, Object> map);

	int getBoothAllocationInfoListCount(Map<String, Object> map);

	List<Map<String, Object>> getRemainingBoothInfoList(Map<String, Object> map);

	int getRemainingBoothInfoListCount(Map<String, Object> map);

}
