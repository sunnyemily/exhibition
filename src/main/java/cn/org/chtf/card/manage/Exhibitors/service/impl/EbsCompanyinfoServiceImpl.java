package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.hutool.core.util.StrUtil;
import cn.org.chtf.card.manage.Decorators.controller.DecoratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothApplyMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsVehiclecardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApply;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgenttype;
import cn.org.chtf.card.manage.file.service.FileService;
import cn.org.chtf.card.manage.member.dao.MemberDAO;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.membersession.dao.MemberSessionMapper;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.manage.membersession.service.MemberSessionService;
import cn.org.chtf.card.manage.system.dao.SysSessionMapper;
import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 企业信息ServiceImpl
 * 
 * @author ggwudivs
 */
@Service
public class EbsCompanyinfoServiceImpl implements EbsCompanyinfoService {

	@Autowired
	private EbsCompanyinfoMapper ebsCompanyinfoDao;

	@Autowired
	private MemberSessionService memberSessionService;

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private PimAgentMapper pimAgentDao;

	@Autowired
	private MemberSessionMapper memberSessionMapper;

	@Autowired
	private SysSessionMapper sysSessionDao;

	@Autowired
	private EbsPersonnelcardMapper personnelcardDao;
	@Autowired
	private EbsVehiclecardMapper carDao;
	@Autowired
	private EbsBoothApplyMapper applyDao;
	
	@Autowired
	private FileService fileService;

	@Resource
	private DecoratorUtil decoratorUtil;

	/**
	 * 查询企业信息列表
	 */
	@Override
	public List<EbsCompanyinfo> list(Map<String, Object> map) {
		return ebsCompanyinfoDao.list(map);
	}

	/**
	 * 数量企业信息
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return ebsCompanyinfoDao.listcount(map);
	}

	/**
	 * 通过id查询单个企业信息
	 */
	@Override
	public EbsCompanyinfo findById(Integer id) {
		EbsCompanyinfo eci = ebsCompanyinfoDao.findById(id);
		Map<String,Object> par = new HashMap<String,Object>();
		par.put("companyid", id);
		par.put("session", eci.getSession());
		List<Map<String,Object>> lit = ebsCompanyinfoDao.GetCompanyBooth(par);
		String result = StringUtil.GetZhanWeiPinJie(lit);
		eci.setZhanweihao(result);
		return eci;
	}

	/**
	 * 通过map查询单个企业信息
	 */
	@Override
	public List<EbsCompanyinfo> findByMap(Map<String, Object> map) {
		return ebsCompanyinfoDao.findByMap(map);
	}

	/**
	 * 新增企业信息
	 */
	@Override
	public int save(EbsCompanyinfo ebsCompanyinfo) {
		int Ret = ebsCompanyinfoDao.save(ebsCompanyinfo);
		fileService.uploadTencent(ebsCompanyinfo.getId(),"ebs_companyinfo","id",ebsCompanyinfo.getCompanyvideo());
		return Ret;
	}

	/**
	 * 修改企业信息
	 */
	@Override
	public int update(EbsCompanyinfo ebsCompanyinfo) {
		int iRet = ebsCompanyinfoDao.update(ebsCompanyinfo);
		fileService.uploadTencent(ebsCompanyinfo.getId(),"ebs_companyinfo","id",ebsCompanyinfo.getCompanyvideo());
		return iRet;
	}

	/**
	 * 删除企业信息
	 */
	@Override
	public int deleteById(Integer id) {
		return ebsCompanyinfoDao.deleteById(id);
	}

	/**
	 * 获取交易团添加的企业
	 * 
	 * @param roomId
	 * @param status
	 * @param sessionId
	 * @param page
	 * @param session
	 * @return
	 */
	public ResultModel getTraddingGroupCompanys(Integer roomId, Integer status,
			String sessionId, PageModel page, HttpSession session) {
		ResultModel result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qymc", page.getKeywords());
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		if (roomId != null) {
			map.put("showRoomId", roomId);
		}
		map.put("sessionId", Integer.parseInt(sessionId));
		switch (status) {
		case 1:
			map.put("exhibitionOfficeAuditStatus", 1);
			break;
		case 4:
			map.put("exhibitionOfficeAuditStatus", 0);
			break;
		case 5:
			map.put("exhibitionOfficeAuditStatus", 2);
			break;
		}
		map.put("sessionIds", Integer.parseInt(sessionId));
		Member member = (Member) session.getAttribute("member");
		map.put("memberId", member.getMemberId());
		try {
			result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, null);
			List<Map<String, Object>> traddingGroupCompanys = ebsCompanyinfoDao.getTraddingGroupCompanys(map);
			for(Map<String, Object> company:traddingGroupCompanys) {
				List<Map<String,Object>> boothList = new ArrayList<Map<String,Object>>();
				company.put("boothName", company.get("booths"));
				if(company.get("booths")!=null) {
					String booths = company.get("booths").toString();
					for(String booth : booths.split(",")) {
						Map<String,Object> boothMap = new HashMap<String,Object>();
						boothMap.put("boothName", booth);
						boothList.add(boothMap);
					}
				}
				String booths = StringUtil.GetZhanWeiPinJie(boothList);
				company.replace("booths", booths);
			}
			
			result.setResult(traddingGroupCompanys);
			result.setCount(ebsCompanyinfoDao.getTraddingGroupCompanysCount(map));
			
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD,
					e.getMessage());
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> EnterpriseWithdrawallist(
			Map<String, Object> map) {
		return ebsCompanyinfoDao.EnterpriseWithdrawallist(map);
	}

	@Override
	public int EnterpriseWithdrawallistcount(Map<String, Object> map) {
		return ebsCompanyinfoDao.EnterpriseWithdrawallistcount(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R UseCompany(Map<String, Object> map) {
		try {
			String isStr = String.valueOf(map.get("isStr"));
			String newsession = String.valueOf(map.get("Sessionid"));
			String Memberid = String.valueOf(map.get("memberid"));
			String oldsession = String.valueOf(map.get("oldsession"));
			String dbyid = String.valueOf(map.get("daibanyuan"));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberid", Memberid);
			params.put("oldsession", oldsession);

			MemberSession ms = new MemberSession();
			ms.setId(Integer.valueOf(newsession));
			ms.setMemberId(Integer.valueOf(Memberid));
			ms.setOrgnizationId(Integer.valueOf(dbyid));

			// 验证交易团
			PimAgent paa = pimAgentDao.findById(Integer.valueOf(dbyid),
					oldsession);
			int jytid = paa.getTradinggroupid();
			if (jytid > 0) {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("tradinggroupid", jytid);
				obj.put("session", newsession);
				List<PimAgent> litpim = pimAgentDao.findByMap(obj);
				if (litpim.size() > 0) {
					return R.error().put("code", -200)
							.put("msg", "当前开放的届次已存在代办员与所选交易团的关联关系，无法提取数据。");
				}
			}

			Map<String, Object> para = new HashMap<String, Object>();
			para.put("session", newsession);
			para.put("companyid", dbyid);
			para.put("memberid", Memberid);
			List<MemberSession> lits = memberSessionService.findByMap(para);
			if (lits.size() == 0) {
				memberSessionService.insert(ms);
			}
			PimAgent pa = new PimAgent();
			pa.setId(Integer.valueOf(dbyid));
			pa.setIsopen(0);
			pimAgentDao.update(pa);
			//处理代办员的证件类型
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("agentid", dbyid);
			param.put("oldsession", oldsession);
			param.put("newsession", newsession);
			List<Map<String,Object>> litzjlx = ebsCompanyinfoDao.GetAgentTypes(param);
			if(litzjlx.size()>0){
				pimAgentDao.deleteTypeByAgentIdAndSession(para);			
				List<PimAgenttype> listAgent = new ArrayList<PimAgenttype>();
				for(int r=0;r<litzjlx.size();r++){
					PimAgenttype pat = new PimAgenttype();
					pat.setAgentid(Integer.valueOf(dbyid));
					pat.setRounds(newsession);
					pat.setCardtype(Integer.valueOf(litzjlx.get(r).get("id").toString()));
					pat.setQuantity(Integer.valueOf(litzjlx.get(r).get("quantity").toString()));
					listAgent.add(pat);
				}
				if(listAgent.size()>0){
					pimAgentDao.saveAgentType(listAgent);
				}
			}
			

			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String id : ids) {
					if (id != null && !id.equals("")) {
						EbsCompanyinfo cm = ebsCompanyinfoDao.findById(Integer
								.valueOf(id));
						Map<String, Object> par = new HashMap<String, Object>();
						par.put("session", newsession);
						par.put("chinesename", cm.getChinesename());
						List<EbsCompanyinfo> list = ebsCompanyinfoDao
								.findByMap(par);
						if (list.size() == 0) {
							cm.setSession(newsession);
							ebsCompanyinfoDao.save(cm);
						}
					}
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.SETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

	/**
	 * @author wushixing
	 */
	@Override
	public ResultModel getMemberCompany(HttpSession session) {
		ResultModel result = null;
		try {

			Member member = (Member) session.getAttribute("member");
			EbsCompanyinfo company = ebsCompanyinfoDao
					.getCompanyByMemberIdAndSessionId(member.getMemberId(),
							member.getMemberSessionId());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("member", member);
			resultMap.put("company", company);
			result = new ResultModel(WConst.SUCCESS, "查询成功", resultMap);
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
		// TODO Auto-generated method stub
		return result;
	}

	/**
	 * @author wushixing
	 */
	@Override
	public ResultModel getMemberCompany(HttpSession session, HttpServletRequest request) {
		ResultModel result = null;
		try {

			Member member = (Member) session.getAttribute("member");
			EbsCompanyinfo company = ebsCompanyinfoDao
					.getCompanyByMemberIdAndSessionId(member.getMemberId(),
							member.getMemberSessionId());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("member", member);
			resultMap.put("company", company);
			String decoratorAuditStartTime = decoratorUtil.getDecoratorAuditStartTime(request);
			if (StrUtil.isNotEmpty(decoratorAuditStartTime)) {
				resultMap.put("decoratorAuditStartTime", decoratorUtil.getDateStr(decoratorAuditStartTime));
			}
			String decoratorAuditEndTime = decoratorUtil.getDecoratorAuditEndTime(request);
			if (StrUtil.isNotEmpty(decoratorAuditEndTime)) {
				resultMap.put("decoratorAuditEndTime", decoratorUtil.getDateStr(decoratorAuditEndTime));
			}
			result = new ResultModel(WConst.SUCCESS, "查询成功", resultMap);
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
		// TODO Auto-generated method stub
		return result;
	}

	/**
	 * @author wushixing
	 */
	@Override
	public ResultModel getMemberDecorator(HttpSession session, HttpServletRequest request) {
		ResultModel result = null;
		try {
			Member member = (Member) session.getAttribute("member");
			EbsCompanyinfo company = ebsCompanyinfoDao.getDecoratorByMemberId(member.getMemberId());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("member", member);
			resultMap.put("company", company);
			result = new ResultModel(WConst.SUCCESS, "查询成功", resultMap);
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public ResultModel updateMemberCompany(EbsCompanyinfo company,
			HttpSession session) {
		ResultModel result = null;
		try {

			Member member = (Member) session.getAttribute("member");
			EbsCompanyinfo hisCompany = ebsCompanyinfoDao
					.getLastCompanyByMemberId(member.getMemberId());
			if (hisCompany != null
					&& hisCompany.getId().equals(company.getId())) {
				int c = ebsCompanyinfoDao.update(company);
				result = new ResultModel(WConst.SUCCESS, WConst.SAVED, c);
			} else {
				result = new ResultModel(WConst.ERROR, "权限不足", "本公司不属于您的");
			}
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public EbsCompanyinfo findOneById(Integer id) {
		return ebsCompanyinfoDao.findOneById(id);
	}

	@Override
	public EbsCompanyinfo findFirstById(Integer id) {
		EbsCompanyinfo eci = ebsCompanyinfoDao.findOneById(id);
		int CompanyTypeId = eci.getComanyTypeId();
		String session = eci.getSession();
		Map<String, Object> obj = new HashMap<String, Object>();
		if (CompanyTypeId == 0) {
			obj.put("session", session);
			obj.put("tradinggroupid", eci.getTradinggroupid());
			String result = memberSessionMapper.GetJYTLoginName(obj);
			eci.setLoginname(result);
		}
		if (CompanyTypeId == 2) {
			obj.put("session", session);
			obj.put("orgid", id);
			String result = memberSessionMapper.GetLoginName(obj);
			eci.setLoginname(result);
		}
		return eci;
	}

	@Override
	public List<EbsCompanyinfo> Greenlist(Map<String, Object> map) {
		return ebsCompanyinfoDao.Greenlist(map);
	}

	@Override
	public int Greenlistcount(Map<String, Object> map) {
		return ebsCompanyinfoDao.Greenlistcount(map);
	}

	/**
	 * 验证企业是否存在
	 * 
	 * @param companyName
	 *            企业名称
	 * @param memberType
	 *            要注册的会员类型
	 * @param sessionId
	 *            届次id
	 * @return
	 */
	@Override
	public ResultModel validateCompany(String companyName, Integer memberType,
			Integer sessionId) {
		ResultModel result = null;
		try {
			// 1.查找本届同名的公司名称
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("chinesename", companyName);
			filter.put("session", sessionId);
			List<EbsCompanyinfo> companys = ebsCompanyinfoDao.findByMap(filter);
			// 1.1本届中已存在
			if (companys.size() > 0) {
				EbsCompanyinfo company = companys.get(0);
				// 获取本届中的已注册会员
				List<Member> members = memberDAO.getRegistedMembers(
						company.getId(), sessionId);
				for (Member member : members) {
					// 同类型会员
					if (member.getMemberType().equals(memberType)) {
						result = new ResultModel(WConst.ERROR, "本企业已注册", null);
						continue;
					}
					switch (member.getMemberType()) {
					case Member.MEMBER_TYPE_EXHIBITOR_CODE:
						result = new ResultModel(WConst.ERROR,
								"本企业已注册为零散参展商，请登录零散参展商账户填报证件。", null);
						break;
					case Member.MEMBER_TYPE_DECORATOR_CODE:
						result = new ResultModel(
								WConst.ERROR,
								"本企业已注册为布撤展商，如需更改该企业账户类型，请联系展会管理处：0451-82340100。",
								null);
						break;
					case Member.MEMBER_TYPE_FOREIGN_CODE:
						result = new ResultModel(
								WConst.ERROR,
								"本企业已注册为外宾，如需更改该企业账户类型，请联系展会管理处：0451-82340100。",
								null);
						break;
					case Member.MEMBER_TYPE_PURCHASER_CODE:
						result = new ResultModel(
								WConst.ERROR,
								"本企业已注册为采购商，如需更改该企业账户类型，请联系展会管理处：0451-82340100。",
								null);
						break;
					case Member.MEMBER_TYPE_EXHIBITOR_ONLINE_CODE:
						result = new ResultModel(
								WConst.ERROR,
								"本企业已注册为线上展商，请登录零散参展商账户填报信息。",
								null);
						break;
					}
				}
				if (members.size() == 0) {
					result = new ResultModel(WConst.ERROR,
							"数据异常，本届企业已存在，但用户不存在。请联系展会管理处：0451-82340100。", null);

				}

			}
			// 1.2本届不存在
			else {
				// 2.验证历届是否存在
				filter.put("session", null);
				companys = ebsCompanyinfoDao.findByMap(filter);
				// 2.1历届中存在
				if (companys.size() > 0) {
					result = new ResultModel(WConst.CONFIRM,
							"存在历届企业信息，需要用户验证才可提取，是否现在前去验证？", null);
				}
				// 2.2历届中不存在
				else {
					result = new ResultModel(WConst.SUCCESS, "恭喜您，公司名称可正常注册。",
							null);
				}
			}
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD,
					e.getMessage());
		}
		return result;
	}

	/**
	 * 验证企业是否存在
	 * 
	 * @param companyName
	 *            企业名称
	 * @param memberType
	 *            要注册的会员类型
	 * @param sessionId
	 *            届次id
	 * @return
	 */
	@Override
	public ResultModel validateHistoryCompany(EbsCompanyinfo company,
			HttpSession session) {
		ResultModel result = null;
		try {
			// 1.查找同名的公司名称
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("chinesename", company.getChinesename());
			filter.put("session", company.getSession());
			List<EbsCompanyinfo> companys = ebsCompanyinfoDao
					.findHistoryByMap(filter);

			// 2.找到了
			if (companys.size() > 0) {
				EbsCompanyinfo historyCompany = companys.get(0);
				// 获取本届中的已注册会员
				// 对信息进行验证
				if (company.getContactperson().equals(
						historyCompany.getContactperson())
						|| company.getPrincipal().equals(
								historyCompany.getPrincipal())) {
					// 获取注册信息
					List<Member> members = memberDAO.getHistoryRegistedMembers(
							historyCompany.getId(),
							Integer.parseInt(company.getSession()));
					Map<String, Object> res = new HashMap<String, Object>();
					if (members.size() > 0) {
						res.put("member", members.get(0));
					}
					res.put("company", companys.get(0));
					result = new ResultModel(WConst.SUCCESS, "信息验证成功。", res);
				} else {
					Object objectCount = session
							.getAttribute(Member.MEMBER_VALIDATE_ERROR_KEY);
					Integer count = 0;
					if (objectCount == null) {
						count = 1;
					} else {
						count = (Integer) objectCount + 1;
					}
					session.setAttribute(Member.MEMBER_VALIDATE_ERROR_KEY,
							count);
					String msg = "";
					if (count > 3) {
						msg = "该验证信息不正确，如您忘记验证信息，请联系展会管理处：0451-82340100。";
					} else {
						msg = "验证信息不正确，请重试。";
					}
					result = new ResultModel(WConst.ERROR, msg, null);
				}
			}
			// 2.2没找到
			else {
				result = new ResultModel(WConst.SUCCESS, "无历届企业信息，可继续注册。", null);
			}
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD, e);
		}
		return result;
	}

	@Override
	public List<EbsCompanyinfo> PreviousEnterpriseList(Map<String, Object> map) {
		List<EbsCompanyinfo> list = ebsCompanyinfoDao
				.PreviousEnterpriseList(map);
		List<EbsCompanyinfo> company = new ArrayList<EbsCompanyinfo>();
		for (int j = 0; j < list.size(); j++) {
			EbsCompanyinfo eci = ebsCompanyinfoDao
					.findById(list.get(j).getId());
			SysSession ss = sysSessionDao.findById(Integer.valueOf(eci
					.getSession()));
			eci.setSessionname(ss.getSessionid());
			company.add(eci);
		}
		return company;
	}

	@Override
	public int PreviousEnterpriseListCount(Map<String, Object> map) {
		return ebsCompanyinfoDao.PreviousEnterpriseListCount(map);
	}

	/**
	 * 获取历届交易团添加的企业
	 * 
	 * @param roomId
	 * @param status
	 * @param sessionId
	 * @param page
	 * @param session
	 * @return
	 */
	public ResultModel getTraddingGroupHistoryCompanys(Integer sessionId,
			Integer memberId, PageModel page) {
		ResultModel result = null;
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("memberId", memberId);
		filter.put("sessionId", sessionId);
		filter.put("chinesename", page.getKeywords());
		filter.put("start", page.getStart());
		filter.put("limit", page.getLimit());

		try {
			result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, null);
			result.setResult(ebsCompanyinfoDao
					.getTraddingGroupHistoryCompanys(filter));
			result.setCount(ebsCompanyinfoDao
					.getTraddingGroupHistoryCompanysCount(filter));
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD,
					e.getMessage());
		}

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultModel rejoin(Integer[] idList, int sessionId, int memberId) {
		ResultModel result = null;
		Integer id = 0;
		try {
			for (int i = 0; i < idList.length; i++) {
				id = idList[i];
				EbsCompanyinfo company = ebsCompanyinfoDao.findById(id);
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put("session", sessionId);
				filter.put("chinesename", company.getChinesename());
				List<EbsCompanyinfo> cards = ebsCompanyinfoDao
						.findByMap(filter);
				if (cards.size() > 0) {
					throw new Exception(company.getChinesename() + "的企业在本届已存在。");
				} else {
					company.setId(null);
					company.setSession(String.valueOf(sessionId));
					company.setAddtime(new java.sql.Timestamp(new Date()
							.getTime()));
					ebsCompanyinfoDao.save(company);
					EbsBoothApply apply = new EbsBoothApply();
					apply.setMemberId(memberId);
					apply.setSessionId(sessionId);
					apply.setCompanyId(company.getId());
					applyDao.save(apply);
				}

			}
			result = new ResultModel(WConst.SUCCESS, "激活成功", null);
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultModel delete(Integer[] ids, int memberId, int sessionId) {
		// 1.查询是否已添加证件

		ResultModel result = null;
		if (ids.length == 0) {// 验证传入数组的长度
			result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					"没有传入要删除的信息");
			return result;
		}
		// 2.清空
		List<Integer> idList = Arrays.asList(ids);
		try {

			int row = ebsCompanyinfoDao.delete(idList, memberId, sessionId);
			result = new ResultModel(WConst.SUCCESS, WConst.DELETED, row);
		} catch (Exception e) {
			result = new ResultModel(WConst.ERROR, WConst.DELETEDERROR,
					e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
		}

		return result;
	}

	@Override
	public Member GetMemberInfoByCompanyId(Map<String,Object> par) {		
		return ebsCompanyinfoDao.GetMemberInfoByCompanyId(par);
	}

	@Override
	public List<Map<String, Object>> GetTopComapnyInfo(
			Map<String, Object> exhibitionInfo) {		
		Map<String,Object> par = new HashMap<String,Object>();
		par.put("session", exhibitionInfo.get("session"));
		List<Map<String, Object>> list = ebsCompanyinfoDao.GetTopComapnyInfo(exhibitionInfo);
		for(int j=0;j<list.size();j++){
			String CompanyId = list.get(j).get("companyId").toString();
			par.put("companyid", CompanyId);
			List<Map<String,Object>> lit = ebsCompanyinfoDao.GetCompanyBooth(par);
			String result = StringUtil.GetZhanWeiPinJie(lit);
			list.get(j).put("num", result);
		}
		return list;
	}

}
