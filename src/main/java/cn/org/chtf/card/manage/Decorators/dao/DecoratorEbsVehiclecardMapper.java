package cn.org.chtf.card.manage.Decorators.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-车辆证件审核DAO
 * @author lm
 */
public interface DecoratorEbsVehiclecardMapper {

    /**
     * 通过id查询单个参展商管理-车辆证件审核
     */
    EbsVehiclecard findById(Integer id);

    /**
     * 通过map查询单个参展商管理-车辆证件审核
     */
    List<EbsVehiclecard> findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-车辆证件审核列表
     */
    List<EbsVehiclecard> list(Map<String, Object> map);

    /**
     * 新增参展商管理-车辆证件审核
     */
    int save(EbsVehiclecard ebsVehiclecard);

    /**
     * 修改参展商管理-车辆证件审核
     */
    int update(EbsVehiclecard ebsVehiclecard);

    /**
     * 删除参展商管理-车辆证件审核
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个参展商管理-车辆证件审核
     */
    int listcount(Map<String, Object> map);
    /**
     * 批量删除指定代办员添加的指定车辆证件
     * @param idList
     * @param memberId
     * @return
     */
	int delete(List<Integer> idList, Integer memberId);

	/**
	 * @author wushixing
	 * @apiNote车辆取证报表
	 * @param memberId 代办员
	 * @param makecardtime 制证时间，需考虑取证报表延时
	 * @param sessionId 届次id
	 * @param awayStatus 取证状态
	 * @return
	 */
	List<Map<String,Object>> takeAwayReport(@Param("memberId") Integer memberId, @Param("makecardtime") String makecardtime, @Param("sessionId") Integer sessionId, @Param("awayStatus") Integer awayStatus, @Param("cardType") Integer cardType);
	/**
	 * @author wushixing
	 * @apiNote 更新打印码
	 * @param memberId 代办员
	 * @param makecardtime 制证时间，需考虑取证报表延时
	 * @param sessionId 届次id
	 * @param awayStatus 取证状态
	 * @return
	 */
	Integer updatePrintNumber(@Param("printNumber") String printNumber, @Param("memberId") Integer memberId, @Param("makecardtime") String makecardtime, @Param("sessionId") Integer sessionId, @Param("awayStatus") Integer awayStatus, @Param("cardType") Integer cardType);

	/**打印取证报表时，更新证件打印编号
	 * @author ggwudivs
	 * @param map
	 * @return
	 */
	int updatePrintNumberByMap(Map<String, Object> map);

	void deleteByIdAndMemberId(@Param("id") int id, @Param("memberId") Integer memberId);

	void deleteByMap(Map<String, Object> map);

	List<Map<String,Object>> getHistoryCarCard(Map<String, Object> map);

	int getHistoryCarCardCount(Map<String, Object> map);

	List<Map<String, Object>> VehicleIDMarkList(Map<String, Object> map);

	int VehicleIDMarkListCount(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);
}
