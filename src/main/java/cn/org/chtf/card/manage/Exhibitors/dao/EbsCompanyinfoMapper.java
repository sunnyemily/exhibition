package cn.org.chtf.card.manage.Exhibitors.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.member.pojo.Member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 企业信息DAO
 * @author ggwudivs
 */
public interface EbsCompanyinfoMapper {

    /**
     * 通过id查询单个企业信息
     */
    EbsCompanyinfo findById(Integer id);

    /**
     * 通过map查询单个企业信息
     */
    List<EbsCompanyinfo> findByMap(Map<String, Object> map);

    /**
     * 通过map查询单个企业信息
     */
    List<EbsCompanyinfo> findHistoryByMap(Map<String, Object> map);

    /**
     * 查询企业信息列表
     */
    List<EbsCompanyinfo> list(Map<String, Object> map);

    /**
     * 新增企业信息
     */
    int save(EbsCompanyinfo ebsCompanyinfo);

    /**
     * 修改企业信息
     */
    int update(EbsCompanyinfo ebsCompanyinfo);

    /**
     * 删除企业信息
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个企业信息
     */
    int listcount(Map<String, Object> map);
    
    /**
     * 根据会员id和届次ID获取公司信息
     * @author wushixing
     * @param memberId 会员id
     * @return
     */
    EbsCompanyinfo getCompanyByMemberIdAndSessionId(@Param("memberId") Integer memberId,@Param("sessionId") Integer sessionId);

    /**
     * 根据会员id获取搭建商公司信息
     * @author wushixing
     * @param memberId 会员id
     * @return
     */
    EbsCompanyinfo getDecoratorByMemberId(@Param("memberId") Integer memberId);
    
    /**
     * 根据会员id获取最新一条企业
     * @author wushixing
     * @param memberId 会员id
     * @return
     */
    EbsCompanyinfo getLastCompanyByMemberId(Integer memberId);

    /**
     * @author wushixing
          * 查询交易团直接届次添加的企业列表
     */
    List<Map<String,Object>> getTraddingGroupCompanys(Map<String, Object> map);

	/**
	 * @author wushixing
          * 查询交易团以前届次添加的企业总数
     */
    int getTraddingGroupHistoryCompanysCount(Map<String, Object> map);

    /**
     * @author wushixing
          * 查询交易团以前届次添加的企业列表
     */
    List<EbsCompanyinfo> getTraddingGroupHistoryCompanys(Map<String, Object> map);

	/**
	 * @author wushixing
          * 查询交易团直接届次添加的企业总数
     */
    int getTraddingGroupCompanysCount(Map<String, Object> map);

	List<Map<String, Object>> EnterpriseWithdrawallist(Map<String, Object> map);

	int EnterpriseWithdrawallistcount(Map<String, Object> map);

	EbsCompanyinfo findOneById(Integer id);

	List<EbsCompanyinfo> Greenlist(Map<String, Object> map);

	int Greenlistcount(Map<String, Object> map);

	List<EbsCompanyinfo> PreviousEnterpriseList(Map<String, Object> map);

	int PreviousEnterpriseListCount(Map<String, Object> map);
	
	int delete(@Param("idList") List<Integer> idList, @Param("memberId") Integer memberId, @Param("sessionId") Integer sessionId);

	List<Map<String, Object>> GetAgentTypes(Map<String, Object> param);

	Member GetMemberInfoByCompanyId(Map<String,Object> par);

	List<Map<String, Object>> GetTopComapnyInfo(Map<String, Object> exhibitionInfo);

	List<Map<String,Object>> GetCompanyBooth(Map<String, Object> par);

    /**
     * @author wushixing
     * 查询搭建商报馆申请关联的企业列表
     */
    List<Map<String,Object>> getStadiumCompanys(Map<String, Object> map);

    /**
     * @author wushixing
     * 查询搭建商报馆申请关联的企业总数
     */
    int getStadiumCompanysCount(Map<String, Object> map);

}
