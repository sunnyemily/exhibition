package cn.org.chtf.card.manage.Exhibitors.service;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 企业信息Service
 * @author ggwudivs
 */
public interface EbsCompanyinfoService {

	/**
	 * 查询企业信息列表
	 */
	List<EbsCompanyinfo> list(Map<String, Object> map);

	/**
	 * 通过id查询单个企业信息
	 */
    EbsCompanyinfo findById(Integer id);

	/**
	 * 通过map查询单个企业信息
	 */
	List<EbsCompanyinfo> findByMap(Map<String, Object> map);

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
	
	int listcount(Map<String, Object> map);
	/**
	 * @author wushixing
	 * @apiNote 获取交易团添加企业列表
	 * @param roomId
	 * @param status
	 * @param exhibitionSessionId
	 * @param page
	 * @param session
	 * @return
	 */
	ResultModel getTraddingGroupCompanys(Integer roomId, Integer status, String exhibitionSessionId, PageModel page,
			HttpSession session);
	/**
	 * @author wushixing
	 * @apiNote 获取会员关联企业信息，适用于交易团外的其他企业
	 * @param session
	 * @return
	 */
	ResultModel getMemberCompany(HttpSession session);
	/**
	 * @author wushixing
	 * @apiNote 获取会员关联企业信息，适用于交易团外的其他企业
	 * @param session
	 * @return
	 */
	ResultModel getMemberCompany(HttpSession session, HttpServletRequest request);
	/**
	 * @author wushixing
	 * @apiNote 获取会员关联企业信息，适用于交易团外的其他企业
	 * @param session
	 * @return
	 */
	ResultModel updateMemberCompany(EbsCompanyinfo company,HttpSession session);

	List<Map<String, Object>> EnterpriseWithdrawallist(Map<String, Object> map);

	int EnterpriseWithdrawallistcount(Map<String, Object> map);

	R UseCompany(Map<String, Object> map);

	EbsCompanyinfo findOneById(Integer id);

	List<EbsCompanyinfo> Greenlist(Map<String, Object> map);

	int Greenlistcount(Map<String, Object> map);

	/**
	 * 验证企业是否存在
	 * @param companyName 企业名称
	 * @param memberType 要注册的会员类型
	 * @param sessionId 届次id
	 * @return
	 */
	ResultModel validateCompany(String companyName, Integer memberType, Integer sessionId);

	/**
	 * 验证企业是否存在
	 * @param session 
	 * @param companyName 企业名称
	 * @param memberType 要注册的会员类型
	 * @param sessionId 届次id
	 * @return
	 */
	ResultModel validateHistoryCompany(EbsCompanyinfo company, HttpSession session);

	List<EbsCompanyinfo> PreviousEnterpriseList(Map<String, Object> map);

	int PreviousEnterpriseListCount(Map<String, Object> map);

	EbsCompanyinfo findFirstById(Integer id);
    /**
     * 获取历届交易团添加的企业
     * @param roomId
     * @param status
     * @param sessionId
     * @param page
     * @param session
     * @return
     */
	public ResultModel getTraddingGroupHistoryCompanys(Integer sessionId,Integer memberId,PageModel page);
	/**
	 * 激活历届企业
	 * @param idList
	 * @param parseInt
	 * @param memberId
	 * @return
	 */
	ResultModel rejoin(Integer[] idList, int sessionId, int memberId);

	ResultModel delete(Integer[] idList, int memberId, int sessionId);

	Member GetMemberInfoByCompanyId(Map<String,Object> par);

	List<Map<String, Object>> GetTopComapnyInfo(Map<String, Object> exhibitionInfo);

}
