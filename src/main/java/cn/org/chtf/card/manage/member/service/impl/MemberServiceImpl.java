package cn.org.chtf.card.manage.member.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.StringUtil;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothApplyMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsVehiclecardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgenttype;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.manage.member.dao.MemberDAO;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.memberlog.dao.MemberLogDAO;
import cn.org.chtf.card.manage.memberlog.pojo.MemberLog;
import cn.org.chtf.card.manage.membersession.dao.MemberSessionMapper;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.manage.product.dao.WebProductMapper;
import cn.org.chtf.card.manage.system.dao.SysSessionMapper;
import cn.org.chtf.card.manage.system.dao.SysSmsTemplateMapper;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.wechatuser.dao.WechatUserMapper;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.DateUtil;
import cn.org.chtf.card.support.util.IpUtils;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.RandomValidateCodeUtil;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.WMail;

@Service("MemberServiceImpl")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private MemberLogDAO memberLogDAO;

	@Autowired
	private WechatUserMapper wechatUserDAO;
	
	@Autowired
	private EbsCompanyinfoMapper companyDAO;
	@Autowired
	private EbsBoothApplyMapper applyDAO;
	@Autowired
	private EbsPersonnelcardMapper personDAO;
	@Autowired
	private EbsVehiclecardMapper carDAO;

	@Autowired
	private MemberSessionMapper memberSessionDAO;
	
	@Autowired
	private CmCertificateTypeMapper certificateTypeDAO;
	
	@Autowired
	private SysSmsTemplateService smsService;
	
	@Autowired
	private PimAgentMapper agentDAO;
	@Autowired
	private WebProductMapper productDAO;
	
	@Autowired
    private SysOperationLogService sysOperationLogService;

    @Autowired
    private SysSmsTemplateMapper sysSmsTemplateDao;
	@Autowired
	private EbsBoothMapper boothDAO;
	@Resource(name = "BasicSettingServiceImpl")
	BasicSettingService basicSetting;

	@Autowired
	private SysSessionMapper sysSessionDao;
	
	@Resource
    private SMSUtil sMSUtil;
	@Transactional(rollbackFor = Exception.class)
	public ResultModel regist(Integer isActive,Member member,EbsCompanyinfo company,HttpSession session) {
		ResultModel result = null;
		//记录原始密码
		String password = member.getMemberPassword();
		try {
			member.setMemberCompany(company.getChinesename());
			//首先验证验证码是否正确
			String sessionCode = (String) session.getAttribute(SMSUtil.REGIST_CODEKEY);
			if(member.getMemberType()==member.MEMBER_TYPE_PURCHASER_CODE||member.getMemberType()==member.MEMBER_TYPE_EXHIBITOR_CODE)
			{
				if(null==member.getMemberActivationCode()||!member.getMemberActivationCode().equals(sessionCode)) {

					throw new Exception("验证码不正确");
				}
				
			}
			//1.验证当前届次企业是否存在
			Map<String,Object> companyFilter = new HashMap<String,Object>();
			companyFilter.put("chinesename", company.getChinesename());
			companyFilter.put("session", company.getSession());
			List<EbsCompanyinfo> companys = companyDAO.findByMap(companyFilter);
			//当前届次已存在直接返回
			if(companys.size()>0) {

				throw new Exception("该企业在本届已注册");
			}
			//3.验证手机号
			companyFilter.clear();
			companyFilter.put("session", company.getSession());
			companyFilter.put("phone", company.getPhone());
			companys = companyDAO.findByMap(companyFilter);
			if(member.getMemberType()==member.MEMBER_TYPE_PURCHASER_CODE||member.getMemberType()==member.MEMBER_TYPE_EXHIBITOR_CODE)
			{
				if(companys.size()>0){

					throw new Exception("手机号已存在");
				}
			}
			//4.新增企业
			company.setComanyTypeId(member.getMemberType());
			companyDAO.save(company);
			//5.会员信息
			//新注册的默认为激活状态
			member.setMemberPassword(CryptographyUtil.md5(member.getMemberPassword(), member.getMemberUsername()));
			if(member.getMemberId().equals(0)) {
				

				//2.验证用户名
				if(memberDAO.exist(member.getMemberUsername())>0){
					throw new Exception("用户名已存在");
				}
				
				member.setMemberStatus(0);
				member.setMemberRegistDate(DateUtil.now());
				//新增会员
				memberDAO.insert(member);
			}
			else {
				memberDAO.update(member);
			}
			//6.增加关联关系，关联关系暂不需要验证唯一性，已在前面步骤中验证过了。
			MemberSession memberSession = new MemberSession();
			memberSession.setMemberId(member.getMemberId());
			memberSession.setOrgnizationId(company.getId());
			memberSession.setId(Integer.valueOf(company.getSession()));
			memberSessionDAO.insert(memberSession);
			//发送注册成功短信
			try {
				smsService.sendRegistSucessSMS(company.getPhone(), company.getChinesename(), member.getMemberUsername(), password,Integer.parseInt(company.getSession()),member.getMemberType());
				result = new ResultModel(WConst.SUCCESS,"注册成功，注册信息已发送到您填写的手机号码上，请注意查收。",null);

			}
			catch(Exception e) {
				result = new ResultModel(WConst.SUCCESS,"恭喜您，注册成功。",e);
			}
//			BasicSetting setting = (BasicSetting)basicSetting.getBasicSetting(1).getResult();
//			WMail mail = new WMail(basicSetting);
//			String activeUrl = "http://"+setting.getBsDomain()+"/active-"+member.getMemberEmail()+"-"+activeCode+".html";
//			mail.send(member.getMemberEmail(), "注册激活邮件", "尊敬的用户"+member.getMemberName()+",<br />您好，恭喜您注册成功。注册信息30分钟有效，请您<a href=\""+activeUrl+"\">点击</a>激活。如无法打开，请复制“"+activeUrl+"”到浏览器地址栏。");
		
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultModel routerRegist(Integer isActive,Member member,EbsCompanyinfo company,HttpSession session) {
		ResultModel result = null;
		//记录原始密码
		String password = member.getMemberPassword();
		try {
			member.setMemberCompany(company.getChinesename());
			//首先验证验证码是否正确
			String sessionCode = (String) session.getAttribute(SMSUtil.REGIST_CODEKEY);
			if(member.getMemberType()==member.MEMBER_TYPE_PURCHASER_CODE||member.getMemberType()==member.MEMBER_TYPE_EXHIBITOR_CODE)
			{
				if(null==member.getMemberActivationCode()||!member.getMemberActivationCode().equals(sessionCode)) {

					throw new Exception("验证码不正确");
				}
				
			}
			//1.验证当前届次企业是否存在
			Map<String,Object> companyFilter = new HashMap<String,Object>();
			companyFilter.put("chinesename", company.getChinesename());
			companyFilter.put("session", company.getSession());
			List<EbsCompanyinfo> companys = companyDAO.findByMap(companyFilter);
			//当前届次已存在直接返回
			if(companys.size()>0) {

				throw new Exception("该企业在本届已注册");
			}
			//3.验证手机号
			companyFilter.clear();
			companyFilter.put("session", company.getSession());
			companyFilter.put("phone", company.getPhone());
			companys = companyDAO.findByMap(companyFilter);
			if(member.getMemberType()==member.MEMBER_TYPE_PURCHASER_CODE||member.getMemberType()==member.MEMBER_TYPE_EXHIBITOR_CODE)
			{
				if(companys.size()>0){

					throw new Exception("手机号已存在");
				}
			}
			//4.新增企业
			company.setComanyTypeId(member.getMemberType());
			companyDAO.save(company);
			//5.会员信息
			//新注册的默认为激活状态
			member.setMemberPassword(CryptographyUtil.md5(member.getMemberPassword(), member.getMemberUsername()));
			if(member.getMemberId().equals(0)) {
				

				//2.验证用户名
				if(memberDAO.exist(member.getMemberUsername())>0){
					throw new Exception("用户名已存在");
				}
				
				member.setMemberStatus(0);
				member.setMemberRegistDate(DateUtil.now());
				//新增会员
				memberDAO.insert(member);
			}
			else {
				memberDAO.update(member);
			}
			//6.增加关联关系，关联关系暂不需要验证唯一性，已在前面步骤中验证过了。
			MemberSession memberSession = new MemberSession();
			memberSession.setMemberId(member.getMemberId());
			memberSession.setOrgnizationId(company.getId());
			memberSession.setId(Integer.valueOf(company.getSession()));
			memberSessionDAO.insert(memberSession);
			//7.将微信用户直接绑定，并升级为管理员
			String openId = (String) session.getAttribute("openId");
			WechatUser user = wechatUserDAO.selectByRoutineOpenid(openId);
			user.setMemberId(member.getMemberId());
			user.setUserTypeId(WechatUser.USER_TYPE_EXHIBITOR_CODE);
			user.setUserType("参展商");
			wechatUserDAO.updateByPrimaryKey(user);
			//发送注册成功短信
			try {
				smsService.sendRegistSucessSMS(company.getPhone(), company.getChinesename(), member.getMemberUsername(), password, Integer.parseInt(company.getSession()), member.getMemberType());
				result = new ResultModel(WConst.SUCCESS,"注册成功，注册信息已发送到您填写的手机号码上，请注意查收。请您通过PC版报名管理系统完成展位申请和证件填报。PC版网址：https://card.hljlbh.org.cn/cn/exhibitor-login.html",null);

			}
			catch(Exception e) {
				result = new ResultModel(WConst.SUCCESS,"恭喜您，注册成功。请您通过PC版报名管理系统完成展位申请和证件填报。PC版网址：https://card.hljlbh.org.cn/cn/exhibitor-login.html",e);
			}
		
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}
	public ResultModel deletes(Integer[] memberIdList) {
		ResultModel result = null;
		if(memberIdList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.DELETEDERROR,"没有传入要删除的研究者");
			return result;
		}
		List<Integer> idList = Arrays.asList(memberIdList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.DELETED,null);
			if(idList.size()>1) {
				memberDAO.deletes(idList);
			}
			else {
				memberDAO.delete(idList.get(0));
			}
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.DELETEDERROR,e.getMessage());
		}
		
		return result;
	}
	public ResultModel login(String username,String password,String validateCode,String sessionId,Integer memberType,HttpServletRequest request,HttpSession session) {
		ResultModel result = null;
		try {
			//验证验证码，小程序不需要验证码，会传过来超级验证码
			if(!validateCode.equals("Maocuh@abcd")) {
				String sessionCode = ((String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY)).toLowerCase();
				if(null==validateCode||!sessionCode.equals(validateCode.toLowerCase())) {
					result = new ResultModel(WConst.ERROR,"验证码不正确",null);
					return result;
				}
			}
			//验证届次
			if(!NumberUtils.isDigits(sessionId)) {
				result = new ResultModel(WConst.ERROR,"届次参数不正确",null);
				return result;
			}
			//1.登陆
			Member member = new Member();
			member.setMemberUsername(username);
			member.setMemberType(memberType);
			member.setMemberSessionId(Integer.parseInt(sessionId));
			member = memberDAO.login(member);
			if(memberType.equals(2)&&member==null) {
				member = new Member();
				member.setMemberUsername(username);
				member.setMemberSessionId(Integer.parseInt(sessionId));
				member.setMemberType(Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE);
				member = memberDAO.login(member);
			}
			//1.1 当前届次没有有账户则验证往届是否有账户
			if(member==null) {
				Member historyMember = memberDAO.historyAccount(member);
				if(historyMember==null) {
					result = new ResultModel(WConst.ERROR,WConst.LOGINERROR,null);
				}
				else {
					historyMember.setMemberSessionId(Integer.parseInt(sessionId));
					result = new ResultModel(WConst.CONFIRM,"你本届未注册！是否激活往届登录用户信息？",member);
				}
				return result;
			}
			//1.2是否密码正确
			String inputPassword = CryptographyUtil.md5(password, member.getMemberUsername());
			if(!inputPassword.equals(member.getMemberPassword()))
			{
				result = new ResultModel(WConst.ERROR,WConst.LOGINERROR,null);
				return result;
			}
			member.setMemberPassword(null);
			if(member.getMemberStatus()!=0) {
				result = new ResultModel(WConst.ERROR,"账户异常，请联系管理员。",null);
				return result;
			}
			//2.记录日志
			MemberLog memberLog = new MemberLog();
			memberLog.setMemberId(member.getMemberId());
			memberLog.setMlogDatetime(DateUtil.now());
			memberLog.setMlogIp(IpUtils.getIpAddr(request));
			memberLogDAO.insert(memberLog);
			
			//小程序传过来的，则生成token返回给前台
			if(validateCode.equals("Maocuh@abcd")) {
	            String token = session.getId();
	            member.setMemberPassword(token);
			}
			
			//3.存入session

			session.setAttribute("member", member);
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,member);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.LOGINERROR,e);
		}
		return result;
	}
	@Override
	public ResultModel identificationMember(String username,String password,String sessionId,Integer memberType,HttpServletRequest request,HttpSession session) {
		ResultModel result = null;
		try {
			//验证届次
			if(!NumberUtils.isDigits(sessionId)) {
				result = new ResultModel(WConst.ERROR,"届次参数不正确",null);
				return result;
			}
			//1.登陆
			Member member = new Member();
			member.setMemberUsername(username);
			member.setMemberType(memberType);
			member.setMemberSessionId(Integer.parseInt(sessionId));
			member = memberDAO.login(member);
			if(memberType.equals(2)&&member==null) {
				member = new Member();
				member.setMemberUsername(username);
				member.setMemberSessionId(Integer.parseInt(sessionId));
				member.setMemberType(Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE);
				member = memberDAO.login(member);
			}
			//1.1 当前届次没有
			if(member==null) {
				result = new ResultModel(WConst.ERROR,"激活失败，请确认账户信息是否正确。",member);
				return result;
			}
			//1.2是否密码正确
			String inputPassword = CryptographyUtil.md5(password, member.getMemberUsername());
			if(!inputPassword.equals(member.getMemberPassword()))
			{
				result = new ResultModel(WConst.ERROR,"激活失败，请确认账户信息是否正确。",member);
				return result;
			}
			member.setMemberPassword(null);
			if(member.getMemberStatus()!=0) {
				result = new ResultModel(WConst.ERROR,"激活失败，请确认账户信息是否正确。",member);
				return result;
			}
			//3.更新用户的关联会员信息
			String token = session.getAttribute("openId").toString();
			WechatUser user = wechatUserDAO.selectByRoutineOpenid(token);
			user.setMemberId(member.getMemberId());
			wechatUserDAO.updateByPrimaryKeySelective(user);
			//3.存入session
			session.setAttribute("member", member);
			result = new ResultModel(WConst.SUCCESS,"认证成功",member);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,"激活失败，请确认账户信息是否正确。",e);
		}
		return result;
	}
	public ResultModel getLoginMember(HttpSession session) {
		ResultModel result = null;
		if(null!=session.getAttribute("member")) {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,session.getAttribute("member"));
		}
		else {
			result = WConst.RELOGINJSON;
		}
		return result;
	}
	public ResultModel update(HttpSession session,Member member) {
		ResultModel result = null;
		if(null==session.getAttribute("member")) {
			result = WConst.RELOGINJSON;
			return result;
		}
		System.out.println(session.getAttribute("member"));
		Member cacheMember = (Member)(session.getAttribute("member"));
		
//		if(cacheMember.getMemberId()!=member.getMemberId()) {
//			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"异常操作");
//			return result;
//		}
		member.setMemberId(cacheMember.getMemberId());
		try {
			if(null!=member.getMemberPassword()) {
				member.setMemberPassword(CryptographyUtil.md5(member.getMemberPassword(), cacheMember.getMemberUsername()));
			}
			memberDAO.update(member);
			session.setAttribute("member", memberDAO.select(member.getMemberId()));
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.LOGINERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel update(Member member) {
		ResultModel result = null;
		try {
			if(null!=member.getMemberPassword()) {
				if(member.getMemberPassword().length()!=32) {
					member.setMemberPassword(CryptographyUtil.md5(member.getMemberPassword(), member.getMemberUsername()));
				}
			}
			if(null==member.getMemberId()||member.getMemberId()==0) {
				if(memberDAO.exist(member.getMemberUsername())>0){//用户名重复，提前返回
					result = new ResultModel(WConst.ERROR,"用户名已存在",null);
					return result;
				}
				memberDAO.insert(member);
			}
			else {
				memberDAO.update(member);
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e);
		}
		return result;
	}
	public ResultModel modifyPassword(HttpSession session,String oldPassword,String newPassword) {
		ResultModel result = null;
		Member cacheMember = (Member)(session.getAttribute("member"));
		cacheMember = memberDAO.select(cacheMember.getMemberId());
		if(cacheMember.getMemberPassword().equals(CryptographyUtil.md5(oldPassword, cacheMember.getMemberUsername()))) {
			cacheMember.setMemberPassword(CryptographyUtil.md5(newPassword, cacheMember.getMemberUsername()));
			memberDAO.update(cacheMember);
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
			session.removeAttribute("member");
		}
		else {
			result = new ResultModel(WConst.ERROR,"旧密码不正确","异常操作");
			return result;
		}
		return result;
	}
	public ResultModel logout(HttpSession session) {
		ResultModel result = null;
		session.removeAttribute("member");
		result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		return result;
	}
	public ResultModel forgot(String companyName, String email, String phone,String resetType, String sessionId) {
		ResultModel result = null;
		try {
			if(resetType.equals("phone")) {
				email = null;
			}
			else {
				phone = null;
			}
			Member forgotMember = memberDAO.getForgotMember(companyName, email,phone, sessionId);
			if(null!=forgotMember) {
				WMail mail = new WMail(basicSetting);
				int tempPassword = (int)((Math.random()*9+1)*100000);
				forgotMember.setMemberPassword(CryptographyUtil.md5(String.valueOf(tempPassword), forgotMember.getMemberUsername()));
				memberDAO.update(forgotMember);

				String msgContent = forgotMember.getMemberCompany()+"您好，您的新密码为："+tempPassword+"，请您尽快登陆修改密码。";
				String noticeContent = "并尽快登陆修改密码。";
				String[] phones = {phone};
				if(resetType.equals("phone")) {
					noticeContent = "重置密码已发送到您的注册手机上，请查收短信，" + noticeContent;
					smsService.sendFindPasswordSMS(companyName, String.valueOf(tempPassword), phone, Integer.parseInt(sessionId));
				}
				else {
					noticeContent = "重置密码已发送到您的注册邮箱，请查收邮件，" + noticeContent;
					mail.send(email, "密码修改通知", msgContent);
				}
				sysOperationLogService.CreateEntity("密码找回", sessionId, 1, 0, forgotMember.getMemberId(), null);
				result = new ResultModel(WConst.SUCCESS,noticeContent,null);
			}
			else {
				result = new ResultModel(WConst.ERROR,"信息不匹配",null);
			}
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
		}
		return result;
	}
	public ResultModel getMembers(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,memberDAO.getMembers(page));
			result.setCount(memberDAO.getTotal(page));
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
		}
		return result;
	}
	/**
	 * 发送手机验证码
	 * @param phone
	 * @param sessionId
	 * @param session
	 * @return
	 */
	public ResultModel sendPhoneCode(String phone,String companyName,String sessionId,HttpSession session) {
		//1.查询当前届次是否存在此手机号
		Map<String, Object> companyFilter = new HashMap<String,Object>();
		companyFilter.put("session", sessionId);
		companyFilter.put("phone", phone);
		List<EbsCompanyinfo> companys = companyDAO.findByMap(companyFilter);
		if(companys.size()>0){
			ResultModel result = new ResultModel(WConst.ERROR,"手机号已存在",null);
			return result;
		}
		//2.发送验证码
		try {
			boolean sendResult = smsService.sendRegistValidateSMS(phone, companyName, Integer.parseInt(sessionId), session);
			if(sendResult) {
				ResultModel result = new ResultModel(WConst.SUCCESS,"短信发送成功，请查收。",null);
				return result;
			}
			else {
				ResultModel result = new ResultModel(WConst.ERROR,"短信发送失败，请稍后重试。",null);
				return result;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			ResultModel result = new ResultModel(WConst.ERROR,"短信验证码发送失败，请稍后重试。",e);
			return result;
		}
		 
	}
	/**
	 * 发送邮箱验证码
	 * @param email
	 * @param sessionId
	 * @param session
	 * @return
	 */
	public ResultModel sendEmailCode(String email,String companyName,String sessionId,HttpSession session) {
		//1.查询当前届次是否存在此手机号
		Map<String, Object> companyFilter = new HashMap<String,Object>();
		companyFilter.put("session", sessionId);
		companyFilter.put("email", email);
		List<EbsCompanyinfo> companys = companyDAO.findByMap(companyFilter);
		if(companys.size()>0){
			ResultModel result = new ResultModel(WConst.ERROR,"邮箱已存在",null);
			return result;
		}
		//2.发送验证码
		try {
	    	String smsContent = sysSmsTemplateDao.findByTitleAndSessionId(SMSUtil.PHONE_VALIDATE_CODE, Integer.parseInt(sessionId)).getSmsTemplate();

			Map<String,Object> param = new HashMap<String,Object>();
	    	//1.设置验证码
	    	String validateCode = SMSUtil.getRandomString();
	    	param.put("phoneValidateCode", validateCode);
	    	//1.1将验证码存入session
		    session.removeAttribute(SMSUtil.REGIST_CODEKEY);
		    session.setAttribute(SMSUtil.REGIST_CODEKEY,validateCode);
	    	//2.设置接收通知的公司名字
	    	param.put("companyName", companyName);
	    	//3.处理参数
	    	smsContent = SMSUtil.processParameter(smsContent, param);
			WMail mail = new WMail(basicSetting);
			mail.send(email, "注册验证码", smsContent);
			
			ResultModel result = new ResultModel(WConst.SUCCESS,"邮件发送成功，请查收。",null);
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
			ResultModel result = new ResultModel(WConst.ERROR,"短信验证码发送失败，请稍后重试。",e);
			return result;
		}
		 
	}
	/**
	 * 激活历史账户
	 * 此激活只用于激活将关联信息存放到companyInfo中的账户，如果后期存放到其他表，需修改此函数
	 * @Deprecated
	 * @param companyId
	 * @param memberId 
	 * @param sessionId 届次id
	 * @return
	 */
	public ResultModel activateHistoryAccount(Integer companyId,Integer memberId,String sessionId) {
		try {
			// 1.获取历史企业
			EbsCompanyinfo company;
			if (companyId == null) {
				company = companyDAO.getLastCompanyByMemberId(memberId);
			} else {
				company = companyDAO.findById(companyId);
				memberId = memberSessionDAO.getMemberIdByOrgnizationId(companyId);
			}
			company.setId(null);
			// 2.修改企业届次
			company.setSession(sessionId);
			// 3.新增企业
			companyDAO.save(company);
			companyId = company.getId();
			MemberSession memberSession = new MemberSession();
			memberSession.setOrgnizationId(companyId);
			memberSession.setId(Integer.parseInt(sessionId));
			memberSession.setMemberId(memberId);
			// 4.添加企业、会员关系数据
			memberSessionDAO.insert(memberSession);
			//5.更新本届密码
			Member member = memberDAO.select(memberId);
			//5.1生成随机密码
			String password = sMSUtil.getRandomString();
			member.setMemberPassword(CryptographyUtil.md5(password, member.getMemberUsername()));
			memberDAO.update(member);
			//6.发送会员激活成功短信
			smsService.sendRegistSucessSMS(company.getPhone(), company.getChinesename(), member.getMemberUsername(), password, Integer.parseInt(sessionId),member.getMemberType());
			return new ResultModel(WConst.SUCCESS, "激活成功", null);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, "激活失败", e);
		}

	}
	/**
	 * @author wushixing
	 * @param member
	 * @param sessionId
	 * @return
	 */
	public List<CmCertificateType> getMemberCertificateType(Member member,String sessionId) {
		try {
			//1.会员类为交易团和记者用户时，证件类型在交易团表中取
			if(member.getMemberType().equals(0)||member.getMemberType().equals(1)) {
				return certificateTypeDAO.getCertificateTypesByMemberId(member.getMemberId(), sessionId);
			}
			//2.其他会员类型在会员证件类型表中取
			else
			{
				return certificateTypeDAO.getCertificateTypesByMemberType(member.getMemberType(), sessionId);
			}
		} catch (Exception e) {
			return new ArrayList<CmCertificateType>();
		}
	}
	@Override
	public void delete(int memberid) {
		memberDAO.delete(memberid);
	}
	@Override
	public Map<String,Object> getExihibitorTypePermission(Integer memberId, Integer sessionId){
		Map<String,Object> permission = new HashMap<String,Object>();
		
		Map<String,Object> auditProcess = memberDAO.getBoothProcess(memberId, sessionId);
		Integer companyId = (Integer) auditProcess.get("companyId");
		Integer exhibitionAuditStatus = (Integer) auditProcess.get("exhibitionAuditStatus");
		//已申请展位且展览处已审核通过
		Boolean canOutCertification = false;
		Boolean canInCertification = false;
		if(exhibitionAuditStatus!=null && exhibitionAuditStatus.equals(1)) {
			//根据公司id关联所有已分配的展位所属展厅类型，获取室内外属性
			List<EbsShowroomtype> list = boothDAO.getCertificationPermission(sessionId, companyId);
			for(EbsShowroomtype roomType : list){
				if(roomType!=null&&roomType.getIsoutdoor()!=null&&roomType.getIsoutdoor().equals(1)) {
					canOutCertification = true;					
				}
				else if(roomType!=null&&roomType.getIsoutdoor()!=null&&roomType.getIsoutdoor().equals(0)){
					canInCertification = true;
				}
			}
		}
		permission.put("canOutCertification", canOutCertification);
		permission.put("canInCertification", canInCertification);
		return permission;
	}
	@Override
	public Map<String, Object> getBoothProcess(Integer memberId, Integer sessionId) {
		return memberDAO.getBoothProcess(memberId, sessionId);
	}
	/**
	 * 获取展商申请详情
	 * @param session
	 * @param sessionId
	 * @return
	 */
	@Override
	public Map<String, Object> getExhibitorApplyInfo(HttpSession session, Integer sessionId) {
		Member member = (Member)(session.getAttribute("member"));
		EbsCompanyinfo company = companyDAO.getCompanyByMemberIdAndSessionId(member.getMemberId(),member.getMemberSessionId());
		Map<String, Object> applyInfo = new HashMap<String,Object>();
		//1.获取是否已申请展位
		Map<String,Object> applyFilter = new HashMap<String,Object>();
		applyFilter.put("companyId", company.getId());
		applyFilter.put("sessionId", member.getMemberSessionId());
		if(applyDAO.listcount(applyFilter)<=0) {
			applyInfo.put("applyBooth", false);
		}else {
			applyInfo.put("applyBooth", true);
		}
		//2.获取是否已上传企业封面和logo
		if(StringUtil.isNotEmpty(company.getCompanylogo())&&!company.getCompanylogo().trim().equals("")) {
			applyInfo.put("hasLogo", true);
		}
		else {
			applyInfo.put("hasLogo", false);
		}
		if(StringUtil.isNotEmpty(company.getCompanypicture())&&!company.getCompanypicture().trim().equals("")) {
			applyInfo.put("hasPicture", true);
		}
		else {
			applyInfo.put("hasPicture", false);
		}
		if(StringUtil.isNotEmpty(company.getLegalpersonname())&&!company.getLegalpersonname().trim().equals("")) {
			applyInfo.put("hasCertification", true);
		}
		else {
			applyInfo.put("hasCertification", false);
		}
		//3.获取产品信息
		Map<String,Object> productFilter = new HashMap<String,Object>();
		productFilter.put("companyId", company.getId());
		productFilter.put("session", member.getMemberSessionId());
		applyInfo.put("productCount", productDAO.listcount(productFilter));
		//4.获取展位号分配情况
		Map<String,Object> boothFilter = new HashMap<String,Object>();
		boothFilter.put("session", member.getMemberSessionId());
		boothFilter.put("companyId", company.getId());
		applyInfo.put("boothCount", boothDAO.listcount(boothFilter));
		return applyInfo;
	}
	@Override
	public List<Map<String, Object>> getCardProcess(HttpSession session, Integer sessionId) {
			Member member = (Member)(session.getAttribute("member"));
			//1.获取可办证件类型
			List<CmCertificateType> certificateTypes = getMemberCertificateType(member,sessionId.toString());
			List<Integer> ids = new ArrayList<Integer>();
			for(CmCertificateType agentType : certificateTypes) {
				ids.add(agentType.getId());
			}
			//2.获取各类型已申办数量
			List<Map<String,Object>> appliedCount = memberDAO.getCardProcess(ids,sessionId,member.getMemberId(), null,null,null);
			//3.获取审核失败数量
			List<Map<String,Object>> auditFailedCount = memberDAO.getCardProcess(ids,sessionId,member.getMemberId(), -1,null,null);
			//4.获取审核成功数量
			List<Map<String,Object>> auditSuccessCount = memberDAO.getCardProcess(ids,sessionId,member.getMemberId(), 1,null,null);
			//5.已制证数量
			List<Map<String,Object>> printCount = memberDAO.getCardProcess(ids,sessionId,member.getMemberId(), 1,2,1);
			List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
			//6.可办证件类型数量
			List<Map<String,Object>> canInputCount = new ArrayList<Map<String,Object>>();
			//零散参展商
			if(member.getMemberType().equals(member.MEMBER_TYPE_EXHIBITOR_CODE)) {
				//获取零散参展商可办证件数量：参展证和布撤展证
				Map<String,Object> cardCount = memberDAO.getExhibitorCertificationCount(member.getMemberId(), sessionId);
				for(int i = 0;i<appliedCount.size();i++) {
					Map<String,Object> card = appliedCount.get(i);
						if(card.get("chinesename").equals("参展证")) {
							if(cardCount==null || cardCount.get("exhibitionCount")==null) {
								card.put("inputCount", 0);
							}
							else {
								card.put("inputCount", Long.parseLong(cardCount.get("exhibitionCount").toString()) - (Long)card.get("cardCount"));
							}
						}
				}
			}
			//交易团和记者证
			else if(member.getMemberType().equals(member.MEMBER_TYPE_TRADE_CODE)||
					member.getMemberType().equals(member.MEMBER_TYPE_REPORT_CODE)) {
				PimAgent agent = agentDAO.getAgentByMemberIdAndSessionId(member.getMemberId(), sessionId);
				List<PimAgenttype> agentTypes = agentDAO.getPimAgentTypeByAgentID(agent.getId());
				for(int i = 0;i<appliedCount.size();i++) {
					Map<String,Object> card = appliedCount.get(i);
					for(PimAgenttype agentType : agentTypes) {
						Long inputCount = 0L;
						if(agentType.getCardtype().equals(card.get("cardTypeId"))) {
							//是参展证，直接根据展位分配计算可办参展证数量
							if(card.get("isexhibitor").equals(1)){
								Integer exhibitionCount = memberDAO.getTraddingGroupExhibitionCount(member.getMemberId(),sessionId);
								if(exhibitionCount!=null) {
									inputCount = Long.parseLong(exhibitionCount.toString()) - (Long)card.get("cardCount");
								}
							}
							//否则，按照分配的数量直接取值
							else {
								inputCount = agentType.getQuantity() - (Long)card.get("cardCount");
							}
							if(inputCount<0) {
								inputCount = 0L;
							}
							card.put("inputCount", inputCount);
						}
					}
				}
			}
			//采购商类型的会员要去系统届次表里去拿
			else if(member.getMemberType().equals(member.MEMBER_TYPE_PURCHASER_CODE)) {
				Integer count = sysSessionDao.findById(sessionId).getPurchaserCardLimit();
				for(int i = 0;i<appliedCount.size();i++) {
					Map<String,Object> card = appliedCount.get(i);
					if(card.get("chinesename").equals("采购商证")) {
						card.put("inputCount", count - (Long)card.get("cardCount"));
					}
				}
			}

			//7.将数据进行组合
			for(int i = 0;i<appliedCount.size();i++) {
				Map<String,Object> card = appliedCount.get(i);
				if(ids.contains(card.get("cardTypeId"))) {
					card.put("auditFailedCount", auditFailedCount.get(i).get("cardCount"));
					card.put("auditSuccessCount", auditSuccessCount.get(i).get("cardCount"));
					card.put("printCount", printCount.get(i).get("cardCount"));
					resultMap.add(card);
				}
			}
			return resultMap;
	}
	@Override
	public Member findById(Integer memberId) {
		return memberDAO.select(memberId);
	}
	@Override
	public List<Member> findByMap(Map<String, Object> map) {
		return memberDAO.findByMap(map);
	}
	/**
	 * 获取指定人添加的审核失败的证件
	 * @param session
	 * @return
	 */
	@Override
	public Map<String, Object> getFaildCards(HttpSession session) {
		Member member = (Member)(session.getAttribute("member"));
		List<EbsPersonnelcard> persons = new ArrayList<EbsPersonnelcard>();
		List<EbsVehiclecard> cars = new ArrayList<EbsVehiclecard>();
		List<CmCertificateType> certificateTypes = getMemberCertificateType(member,member.getMemberSessionId().toString());
		List<Integer> ids = new ArrayList<Integer>();
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("session", member.getMemberSessionId());
		filter.put("agent", member.getMemberId());
		filter.put("status", -1);
		filter.put("index", 0);
		filter.put("size", 100);
		for(CmCertificateType agentType : certificateTypes) {
			filter.put("cardtype", agentType.getId());
			if(agentType.getType().equals(0)) {
				filter.put("size", 100);
				List<EbsPersonnelcard> list = personDAO.list(filter);
				persons.addAll(list);
			}
			else {
				List<EbsVehiclecard> list = carDAO.list(filter);
				cars.addAll(list);
			}
			ids.add(agentType.getId());
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("car", cars);
		result.put("person", persons);
		return result;
	}
}
