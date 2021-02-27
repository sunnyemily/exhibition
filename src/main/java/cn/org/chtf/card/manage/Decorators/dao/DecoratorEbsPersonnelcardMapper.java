package cn.org.chtf.card.manage.Decorators.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 参展商管理-人员证件管理DAO
 * @author ggwudivs
 */
public interface DecoratorEbsPersonnelcardMapper {

    /**
     * 通过id查询单个参展商管理-人员证件管理
     */
    EbsPersonnelcard findById(Integer id);

    /**
     * 通过map查询单个参展商管理-人员证件管理
     */
    List<EbsPersonnelcard> findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-人员证件管理列表
     */
    List<EbsPersonnelcard> list(Map<String, Object> map);

    /**
     * 新增参展商管理-人员证件管理
     */
    int save(EbsPersonnelcard ebsPersonnelcard);

    /**
     * 修改参展商管理-人员证件管理
     */
    int update(EbsPersonnelcard ebsPersonnelcard);

    /**
     * 删除参展商管理-人员证件管理
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个参展商管理-人员证件管理
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> GetDownLoadInfo(Map<String, Object> map);
	/**
	 *
	 * 批量删除指定会员的证件信息
	 * @author wushixing
	 * @param idList
	 * @param memberId
	 */
	int delete(@Param("idList") List<Integer> idList, @Param("memberId") Integer memberId);

	List<EbsPersonnelcard> ExhibitorBadgeProductionlist(Map<String, Object> map);

	int ExhibitorBadgeProductionlistcount(Map<String, Object> map);

	/**
	 * @author wushixing
	 * 获取历届人员数据
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getHistoryPersonCard(Map<String, Object> map);
	/**
	 * @author wushixing
	 * 获取历届人员数量
	 * @param map
	 * @return
	 */
	int getHistoryPersonCardCount(Map<String, Object> map);
	/**
	 * @author wushixing
	 * @apiNote人员取证报表
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

	List<Map<String, Object>> ExportForeignGuestsList(Map<String, Object> map);

	int ExportForeignGuestsListCount(Map<String, Object> map);

	void deleteByMap(Map<String, Object> par);

	List<EbsPersonnelcard> Greenlist(Map<String, Object> map);

	int Greenlistcount(Map<String, Object> map);
	/**
	 * 计算已添加的数量
	 * @param cardtype 证件类型
	 * @param session 届次
	 * @param agent 代办员
	 * @param id 证件id
	 * @return
	 */
	int addCount(@Param("cardtype") Integer cardtype, @Param("session") Integer session, @Param("agent") Integer agent, @Param("id") Integer id);

	List<Map<String,Object>> PersonnelCardQuerylist(Map<String, Object> map);

	int PersonnelCardQuerylistcount(Map<String, Object> map);

	List<Map<String, Object>> PersonnelCertificateCollectionList(
            Map<String, Object> map);

	int PersonnelCertificateCollectionListCount(Map<String, Object> map);
	/**
	 * @author wushixing
	 * @apiNote 小程序观众认证
	 * @param cardnumber 身份证号码
	 * @param name 姓名
	 * @param sessionId 届次id
	 * @return
	 */
	cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo getRouterPersonRole(@Param("cardnumber") String cardnumber, @Param("name") String name, @Param("sessionId") Integer sessionId);

	List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map);

	int getPersonnelCardListCount(Map<String, Object> map);
	
	int resetStatusByCompanyId(Map<String, Object> map);

	List<EbsPersonnelcard> findByMapForReport(Map<String, Object> map);
}
