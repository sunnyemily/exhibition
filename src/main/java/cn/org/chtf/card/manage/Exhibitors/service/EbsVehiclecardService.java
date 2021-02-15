package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

/**
 * 参展商管理-车辆证件审核Service
 * @author lm
 */
public interface EbsVehiclecardService {

	/**
	 * 查询参展商管理-车辆证件审核页面
	 * @return 分页参展商管理-车辆证件审核数据
	 */
	PageInfo<EbsVehiclecard> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询参展商管理-车辆证件审核列表
	 */
	List<EbsVehiclecard> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-车辆证件审核
	 */
    EbsVehiclecard findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-车辆证件审核
	 */
	List<EbsVehiclecard> findByMap(Map<String, Object> map);

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
	
	int listcount(Map<String, Object> map);
	
	/**
	 * 更新人员证件信息
	 * @author wushixing
	 * @param ebsPersonnelcard
	 * @return
	 */
	ResultModel addOrUpdate(EbsVehiclecard vehiclecard);
	/**
	 * @author wushixing
	 * @param page
	 * @return
	 */
	public ResultModel getVehicleCards(EbsVehiclecard vehiclecard,PageModel page);
	/**
	 * @author wushixing
	 * @param idList
	 * @param memberId
	 * @return
	 */
	public ResultModel delete(Integer[] idList,Integer memberId);
	/**
	 * @author wushixing
	 * @apiNote车辆取证报表
	 * @param memberId 代办员
	 * @param makecardtime 制证时间，需考虑取证报表延时
	 * @param sessionId 届次id
	 * @param awayStatus 取证状态
	 * @return
	 */
	ResultModel takeAwayReport(Integer delay,Integer memberId,Integer sessionId,Integer awayStatus, Integer cardType);

	int updatePrintNumberByMap(Map<String, Object> map);

	void deleteByMap(Map<String, Object> par);

	/**
	 * 获取历届车辆证件
	 * @param memberId
	 * @param sessionId
	 * @param cardname
	 * @param page
	 * @return
	 */
	ResultModel getHistoryCarCard(Integer memberId, Integer sessionId, String cardname, PageModel page);

	ResultModel rejoin(Integer[] idList, int exhibitionSessionId, int memberId);

	List<Map<String, Object>> VehicleIDMarkList(Map<String, Object> map);

	int VehicleIDMarkListCount(Map<String, Object> map);
	
	Map<String, Object> loadCount(Map<String, Object> map);
}
