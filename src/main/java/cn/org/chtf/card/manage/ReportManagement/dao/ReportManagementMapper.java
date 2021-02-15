package cn.org.chtf.card.manage.ReportManagement.dao;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.ReportManagement.model.AudienceSurvey;
import cn.org.chtf.card.manage.ReportManagement.model.PurchaseIntentionStatistics;
import cn.org.chtf.card.manage.ReportManagement.model.ReportManagement;



/**
 * 报表管理DAO
 * @author lm
 */
public interface ReportManagementMapper {

	List<Map<String, Object>> NationalDocumentStatisticsList(Map<String, Object> map);

	int NationalDocumentStatisticsListCount(Map<String, Object> map);

	List<ReportManagement> NationalDocumentStatisticsCardList(Map<String, Object> map);

	int NationalDocumentStatisticsCardListCount(Map<String, Object> map);

	List<AudienceSurvey> AudienceSurveyList(Map<String, Object> map);

	List<Map<String, Object>> AudienceSurveyViewList(Map<String, Object> map);

	int AudienceSurveyViewListCount(Map<String, Object> map);

	List<PurchaseIntentionStatistics> PurchaseIntentionStatisticsList(Map<String, Object> map);

	int PurchaseIntentionStatisticsListCount(Map<String, Object> map);

	List<Map<String, Object>> PlaceofAttributionList(Map<String, Object> map);

	int PlaceofAttributionListCount(Map<String, Object> map);

	List<Map<String, Object>> ExhibitorInformationStatisticsList(Map<String, Object> map);
	
	int ExhibitorInformationStatisticsListCount(Map<String, Object> map);

	List<Map<String, Object>> QuestionnaireManagementList(Map<String, Object> map);

	int QuestionnaireManagementListCount(Map<String, Object> map);

	List<Map<String, Object>> HeadofTradingGroupList(Map<String, Object> map);

	int HeadofTradingGroupListCount(Map<String, Object> map);

	List<Map<String, Object>> PurchaseIntentionStatisticsListForDownLoad(Map<String, Object> map);

	List<Map<String, Object>> getEnterpriseList(Map<String, Object> map);

	List<Map<String, Object>> showRoomsList(Map<String, Object> map);

	List<Map<String, Object>> getShowRoomList(Map<String, Object> map);
}
