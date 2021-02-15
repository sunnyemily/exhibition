package cn.org.chtf.card.manage.member.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface MemberService {
	/**
	 * 注册会员
	 * @param member
	 * @return
	 */
	public ResultModel regist(Integer isActive,Member member,EbsCompanyinfo company,HttpSession session);
	/**
	 * 会员登陆
	 * @param username 用户名
	 * @param password 密码
	 * @param request 请求
	 * @param session
	 * @return
	 */
	public ResultModel login(String username,String password,String validateCode,String sessionId,Integer memberType,HttpServletRequest request,HttpSession session);
	/**
	 * 获取已登陆会员信息
	 * @param session
	 * @return
	 */
	public ResultModel getLoginMember(HttpSession session);
	/**
	 * 更新会员信息
	 * @param session
	 * @param member
	 * @return
	 */
	public ResultModel update(HttpSession session,Member member);
	/**
	 * 更改密码
	 * @param session
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public ResultModel modifyPassword(HttpSession session,String oldPassword,String newPassword);
	/**
	 * 退出登陆
	 * @param session
	 * @return
	 */
	public ResultModel logout(HttpSession session);
	/**
	 * 忘记密码，仅适用于非代办员用户
	 * @param member
	 * @return
	 */
	public ResultModel forgot(String companyName, String email, String phone,String resetType, String sessionId);
	/**
	 * 获取分页列表
	 * @param page
	 * @return
	 */
	public ResultModel getMembers(PageModel page);
	/**
	 * 更新会员信息，包括新增和修改。其中如果密码设置为32位，则密码不更改
	 * @param member
	 * @return
	 */
	public ResultModel update(Member member);
	/**
	 * 删除会员
	 * @param memberIdList
	 * @return
	 */
	public ResultModel deletes(Integer[] memberIdList);
	/**
	 * 发送注册验证码
	 * @param phone
	 * @param sessionId
	 * @param session
	 * @return
	 */
	public ResultModel sendPhoneCode(String phone,String companyName,String sessionId,HttpSession session);
	public ResultModel sendEmailCode(String email,String companyName,String sessionId,HttpSession session);
	/**
	 * 激活历史账户
	 * 此激活只用于激活将关联信息存放到companyInfo中的账户，如果后期存放到其他表，需修改此函数
	 * @param companyId
	 * @param memberId 
	 * @param sessionId 届次id
	 * @return
	 */
	public ResultModel activateHistoryAccount(Integer companyId,Integer memberId,String sessionId);

	/**
	 * @author wushixing
	 *   获取会员可办证件类型
	 * @param member
	 * @param sessionId
	 * @return
	 */
	public List<CmCertificateType> getMemberCertificateType(Member member,String sessionId);

	
	public void delete(int memberid);

    Map<String,Object> getBoothProcess(Integer memberId,Integer sessionId);
    List<Map<String, Object>> getCardProcess(HttpSession session, Integer sessionId);
	public Member findById(Integer memberId);
	public List<Member> findByMap(Map<String, Object> map);
	/**
	 * 获取零散参展商参展证办理权限
	 * @param memberId
	 * @param sessionId
	 * @return.get("canOutCertification") 室外参展证办理权限
	 * @return.get("canInCertification") 室内参展证办理权限
	 */
	Map<String, Object> getExihibitorTypePermission(Integer memberId, Integer sessionId);
	/**
	 * 获取零散参展商申请详情
	 * @param session session
	 * @param sessionId 届次id
	 * @return applyBooth 是否已申请展位
	 * @return hasLogo 是否已设置logo
	 * @return hasPicture 是否已上传封面
	 * @return productCount 已添加产品数量
	 * @return boothCount 已分配的展位数量
	 */
	Map<String, Object> getExhibitorApplyInfo(HttpSession session, Integer sessionId);
	/**
	 * 获取指定人添加的审核失败的证件
	 * @param session
	 * @return
	 */
	Map<String, Object> getFaildCards(HttpSession session);
	/**
	 * 小程序管理人员认证
	 * @param username
	 * @param password
	 * @param sessionId
	 * @param memberType
	 * @param request
	 * @param session
	 * @return
	 */
	ResultModel identificationMember(String username, String password, String sessionId, Integer memberType,
			HttpServletRequest request, HttpSession session);
	ResultModel routerRegist(Integer isActive, Member member, EbsCompanyinfo company, HttpSession session);
}
