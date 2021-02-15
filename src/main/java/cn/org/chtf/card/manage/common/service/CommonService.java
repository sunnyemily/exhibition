package cn.org.chtf.card.manage.common.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.common.utils.R;

/**
 * 届次信息Service
 * @author lm
 */
public interface CommonService {

	List<Map<String, Object>> GetIndustry(Map<String, Object> map);

	List<Map<String, Object>> GetCountry(Map<String, Object> map);

	List<Map<String, Object>> GetCardType(Map<String, Object> map);

	List<Map<String, Object>> GetDaiBanYuanZhengJianLeiXing(
			Map<String, Object> map);

	List<Map<String, Object>> GetTradingGroup(Map<String, Object> map);

	List<Map<String, Object>> findByMap(Map<String, Object> map);

	List<Map<String, Object>> GetAgent(Map<String, Object> map);

	List<Map<String, Object>> GetPreviousTradingGroup(Map<String, Object> map);

	List<Map<String, Object>> GetUserRights(Map<String, Object> map);

	void addUserRights(List<Map<String, Object>> list);

	void delUserRights(Map<String, Object> map);

	List<Map<String, Object>> GetUser(Map<String, Object> map);

	void UpdateUserRights(String strSessionid, Integer id, int leixing);

	List<Map<String, Object>> GetExhibitionArea(Map<String, Object> map);

	int GetGreenPersonCardCount(Map<String, Object> map);

	List<Map<String, Object>> GetZhengJianZhiZuoGetTradingGroup(
			Map<String, Object> map);

	/**
     * 通过证件类型获取类型下证件所属企业信息及待审核数量（证件审核）
     * @param map
     * @param request
     * @param session
     * @return
     */
	List<Map<String, Object>> GetPendingDocuments(Map<String, Object> map);

	/**
     * 通过证件类型获取类型下证件所属企业信息及待打印数量（证件制作）
     * @param map
     * @param request
     * @return
     */
	List<Map<String, Object>> GetDocumentsToBePrinted(Map<String, Object> map);

	/**
     * 获取待审核证件代办员及数量（证件审核）
     * @param map
     * @param request
     * @return
     */
	List<Map<String, Object>> GetAgentCertificateReview(Map<String, Object> map);

	List<Map<String, Object>> GetTradingGroupForGreen(Map<String, Object> map);

	/**
     * 获取票号
	 * @param string 1线上预约  2人员证件
     * @return
     */
	String GetTicketNumber(String string);

	List<Map<String, Object>> GetCardTypesForPrint(Map<String, Object> map);

	String GetTicketNumber(Map<String,Object> exhibitionInfo);

	List<Map<String, Object>> GetCompanyList(Map<String, Object> map);

	void ChuLiWanChengDeShiPin(Map<String, Object> para);

	void addMarkLog(Map<String, Object> para);

	String GetMeetingNumber();

	void SendSMS(Integer id, String act, String sessionId);

	R ExcuteCommand(String pass, String command);

	List<Map<String, Object>> GetPersonPicForTest(Map<String,Object> type);

	int AddCardPushInfo(Map<String, Object> obj);

	void updatePersonStatusALL(Map<String, Object> map);

	List<Map<String, Object>> zhajihao(Map<String, Object> map);

	List<Map<String, Object>> zhajidata(Map<String, Object> map);

	List<Map<String, Object>> zhajishijiandata(Map<String, Object> map);

	List<Map<String, Object>> GetCardTypeData(Map<String, Object> map);

	List<Map<String, Object>> getcompanybooth();

	


}
