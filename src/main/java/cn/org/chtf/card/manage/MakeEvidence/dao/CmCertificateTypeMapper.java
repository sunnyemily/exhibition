package cn.org.chtf.card.manage.MakeEvidence.dao;

import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 证件类型管理DAO
 * @author lm
 */
public interface CmCertificateTypeMapper {

    /**
     * 通过id查询单个证件类型管理
     */
    CmCertificateType findById(Integer id);

    /**
     * 通过map查询单个证件类型管理
     */
    CmCertificateType findByMap(Map<String, Object> map);

    /**
     * 查询证件类型管理列表
     */
    List<CmCertificateType> list(Map<String, Object> map);

    /**
     * 新增证件类型管理
     */
    int save(CmCertificateType cmCertificateType);

    /**
     * 修改证件类型管理
     */
    int update(CmCertificateType cmCertificateType);

    /**
     * 删除证件类型管理
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个证件类型管理
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> getList(Map<String, Object> map);


	/**
	 * 根据代办员的登录ID获取可办证件类型
	 * @author wushixing
	 * @param memberId 
	 * @param sessinId
	 * @return
	 */
    List<CmCertificateType> getCertificateTypesByMemberId(@Param("memberId")Integer memberId,@Param("sessionId")String sessionId);
    /**
	 * 根据会员类型ID获取可办证件类型
	 * @author wushixing
	 * @param memberId 
	 * @param sessinId
	 * @return
	 */
    List<CmCertificateType> getCertificateTypesByMemberType(@Param("memberType")Integer memberType,@Param("sessionId")String sessionId);

	List<Map<String, Object>> DailyIDSummaryTableList(Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableListRiQi(Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableListDataByRiQi(
			Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableListHeJi(Map<String, Object> map);

	List<Map<String, Object>> CertificateSummaryFormList(Map<String, Object> map);

	List<Map<String, Object>> CertificateSummaryFormDaiBanYuanZhengJianList(Map<String, Object> map);

    
   int getAgentCardLimit(@Param("memberId")Integer memberId,@Param("sessionId")Integer sessionId,@Param("cardId")Integer cardId);
}
