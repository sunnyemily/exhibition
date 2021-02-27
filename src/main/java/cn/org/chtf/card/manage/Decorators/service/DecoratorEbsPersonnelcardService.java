package cn.org.chtf.card.manage.Decorators.service;

import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 参展商管理-人员证件管理Service
 * @author ggwudivs
 */
public interface DecoratorEbsPersonnelcardService {

	/**
	 * 查询参展商管理-人员证件管理列表
	 */
	List<EbsPersonnelcard> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-人员证件管理
	 */
    EbsPersonnelcard findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-人员证件管理
	 */
	List<EbsPersonnelcard> findByMap(Map<String, Object> map);

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

	int listcount(Map<String, Object> map);
	List<EbsPersonnelcard> listFront(Map<String, Object> map);
	int listFrontCount(Map<String, Object> map);

	List<Map<String, Object>> GetDownLoadInfo(Map<String, Object> map);

	/**
	 * 更新人员证件信息
	 * @author wushixing
	 * @param ebsPersonnelcard
	 * @param member
	 * @param exhibitionInfo
	 * @return
	 */
	ResultModel addOrUpdate(EbsPersonnelcard ebsPersonnelcard, Member member, Map<String, Object> exhibitionInfo);
	/**
	 * @author wushixing
	 * @param page
	 * @return
	 */
	public ResultModel getPersonCards(EbsPersonnelcard ebsPersonnelcard, PageModel page);
	/**
	 * @author wushixing
	 * @param idList
	 * @param memberId
	 * @return
	 */
	public ResultModel delete(Integer[] idList, Integer memberId);

	List<EbsPersonnelcard> ExhibitorBadgeProductionlist(Map<String, Object> map);

	int ExhibitorBadgeProductionlistcount(Map<String, Object> map);
	ResultModel getHistoryPersonCard(Integer memberId, Integer sessionId, String cardname, PageModel page);
	/**
	 * 激活证件
	 * @param idList
	 * @param repeat 是否可重复，0时不允许重复，出现重复将不允许激活
	 * @param exhibitionInfo
	 * @param member
	 * @return
	 */
	ResultModel rejoin(Integer[] idList, Integer repeat, Map<String, Object> exhibitionInfo, Member member);
	/**
	 * @author wushixing
	 * @apiNote人员取证报表
	 * @param memberId 代办员
	 * @param makecardtime 制证时间，需考虑取证报表延时
	 * @param sessionId 届次id
	 * @param awayStatus 取证状态
	 * @return
	 */
	ResultModel takeAwayReport(Integer delay, Integer memberId, Integer sessionId, Integer awayStatus, Integer cardType);

	void deleteByMap(Map<String, Object> par);

	List<EbsPersonnelcard> Greenlist(Map<String, Object> map);

	int Greenlistcount(Map<String, Object> map);

	boolean isExceedLimit(CmCertificateType card, EbsPersonnelcard ebsPersonnelcard, Member member, Map<String, Object> exhibitionInfo);

	List<Map<String,Object>> PersonnelCardQuerylist(Map<String, Object> map);

	int PersonnelCardQuerylistcount(Map<String, Object> map);

	List<Map<String, Object>> PersonnelCertificateCollectionList(Map<String, Object> map);

	int PersonnelCertificateCollectionListCount(Map<String, Object> map);
	/**
	 * @author wushixing
	 * @apiNote 小程序观众认证
	 * @param cardnumber 身份证号码
	 * @param name 姓名
	 * @param sessionId 届次id
	 * @return
	 */
	ResultModel updateRouterPersonRole(String cardnumber, String name, Integer sessionId, HttpSession session);
	/**
	 * 获取用户身份
	 * @param sessionId
	 * @param session
	 * @return
	 */
	ResultModel getRouterPersonRole(Integer sessionId, HttpSession session);

	List<Map<String, Object>> getPersonnelCardList(Map<String, Object> map);

	int getPersonnelCardListCount(Map<String, Object> map);

	int resetStatusByCompanyId(Map<String, Object> map);

	ResultModel updateWebPersonRole(String cardnumber, String name,
                                    int parseInt, HttpSession session);
}
