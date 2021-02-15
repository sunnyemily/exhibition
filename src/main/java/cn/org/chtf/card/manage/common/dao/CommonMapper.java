package cn.org.chtf.card.manage.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



/**
 * 届次信息DAO
 * @author lm
 */
public interface CommonMapper {

	List<Map<String, Object>> GetIndustry(Map<String, Object> map);

	List<Map<String, Object>> GetCountry(Map<String, Object> map);

	List<Map<String, Object>> GetCardType(Map<String, Object> map);

	List<Map<String, Object>> GetDaiBanYuanZhengJianLeiXing(
			Map<String, Object> map);

	List<Map<String, Object>> GetTradingGroup(Map<String, Object> map);

	List<Map<String, Object>> findByMap(Map<String, Object> map);

	List<Map<String, Object>> GetAgent(Map<String, Object> map);

	List<Map<String, Object>> getDBYName(Map<String, Object> map);

	List<Map<String, Object>> getCompanyTrading(Map<String, Object> map);

	List<Map<String, Object>> GetPreviousTradingGroup(Map<String, Object> map);

	List<Map<String, Object>> GetUserRights(Map<String, Object> map);

	void addUserRights(List<Map<String, Object>> list);

	void delUserRights(Map<String, Object> map);

	List<Map<String, Object>> GetUser(Map<String, Object> map);

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
	String GetTicketNumber(String type);

	void updatereceiptcode(String piaohao);

	List<Map<String, Object>> GetCardTypesForPrint(Map<String, Object> map);

	List<Map<String, Object>> GetCompanyList(Map<String, Object> map);

	int GetTencentFileCount(Map<String, Object> para);

	void AddTencentFile(Map<String, Object> para);

	Map<String, Object> GetWaitingUploadTencent();

	void updateZhuangTai(Map<String, Object> para);

	Map<String, Object> GetCanShu(Map<String, Object> para);

	void updateVideo(Map<String, Object> map);

	void addMarkLog(Map<String, Object> para);

	Map<String, Object> GetWaitingToPush(Map<String, Object> par);

	void updateCardPushZhuangTai(Map<String, Object> para);

	int GetSMSInfo(@Param("iagent") int iagent, @Param("sessionId") String sessionId);

	Map<String, Object> GetMemberType(@Param("iagent") int iagent, @Param("sessionId") String sessionId);

	void Addsys_forensic_sms_log(@Param("iagent") int iagent, @Param("sessionId") String sessionId);

	void CarriedOut();

	List<Map<String, Object>> ExcuteCommand(String command);

	List<Map<String, Object>> GetPersonList();

	List<Map<String, Object>> GetPersonPicForTest(Map<String,Object> map);

	int AddCardPushInfo(Map<String, Object> obj);

	void updatePersonStatusALL(Map<String, Object> map);

	List<Map<String, Object>> GetWaitingToPushALL();

	void updateCardStatus();

	List<Map<String, Object>> zhajihao(Map<String, Object> map);

	List<Map<String, Object>> zhajidata(Map<String, Object> map);

	List<Map<String, Object>> zhajishijiandata(Map<String, Object> map);

	List<Map<String, Object>> GetCardTypeData(Map<String, Object> map);

	List<Map<String, Object>> getcompanybooth();

	

}
