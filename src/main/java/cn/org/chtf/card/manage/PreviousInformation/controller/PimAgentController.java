package cn.org.chtf.card.manage.PreviousInformation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsVehiclecardService;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgenttype;
import cn.org.chtf.card.manage.PreviousInformation.service.PimAgentService;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.manage.membersession.service.MemberSessionService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.WConst;

import com.alibaba.fastjson.JSONObject;

/**
 * 历届信息管理-代办员信息Controller
 * 
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/PreviousInformation/pimAgent")
public class PimAgentController {

	@Autowired
	private PimAgentService pimAgentService;

	@Autowired
	private SysSessionService sysSessionService;

	@Autowired
	private MemberSessionService memberSessionService;

	@Autowired
	private SysSmsTemplateService sysSmsTemplateService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private EbsPersonnelcardService ebsPersonnelcardService;

	@Autowired
	private EbsVehiclecardService ebsVehiclecardService;

	@Autowired
	private SysOperationLogService sysOperationLogService;

	@Autowired
	private EbsTradinggroupService ebsTradinggroupService;

	@Autowired
	private CmCertificateTypeService cmCertificateTypeService;
	
	@Autowired
    private CommonService commonService;

	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<PimAgent> list = pimAgentService.list(map);
			int count = pimAgentService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	@RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			List<PimAgent> list = new ArrayList<PimAgent>();
			int count = 0;
			if(map.get("leixing")!=null && map.get("leixing").toString().equals("dbytq")){
				String strSessionid = sysSessionService.getSessionID(request);
				map.put("session",strSessionid);
				map = ResultVOUtil.TiaoZhengFenYe(map);
				list = pimAgentService.Previouslist(map);
				count = pimAgentService.PreviouslistCount(map);
			}
			else{
				map = ResultVOUtil.TiaoZhengFenYe(map);
				list = pimAgentService.list(map);
				count = pimAgentService.listcount(map);
			}			
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	/**
	 * 通过id查询单个历届信息管理-代办员信息
	 */
	@GetMapping("/findByMap")
	public R findByMap(@RequestParam Map<String, Object> map) {
		List<PimAgent> pimAgent = pimAgentService.findByMap(map);
		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("data", pimAgent);
	}

	/**
	 * 通过id查询单个历届信息管理-代办员信息
	 */
	@GetMapping("/findById")
	public R findById(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "session") String session,
			HttpServletRequest request) {
		if (session == null || session.equals("")) {
			session = sysSessionService.getSessionID(request);
		}
		PimAgent pimAgent = pimAgentService.findById(id, session);
		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("data", pimAgent);
	}

	/**
	 * 添加历届信息管理-代办员信息
	 */
	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public R save(@RequestBody PimAgent pimAgent, HttpServletRequest request,
			HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			Map<String, Object> map = new HashMap<String, Object>();
			pimAgent.setLoginname(pimAgent.getLoginname().trim());
			map.put("loginname", pimAgent.getLoginname());
			// List<PimAgent> lit = pimAgentService.findByMap(map);
			List<Member> lit = memberService.findByMap(map);
			if (lit.size() > 0) {
				return R.error().put("code", -200)
						.put("msg", "输入的登录名已被其他会员占用，请变更后重试！");
			}

			int ijytid = (pimAgent.getTradinggroupid().equals("") || pimAgent.getTradinggroupid()==null) ? 0 : pimAgent.getTradinggroupid();
			if (ijytid > 0) {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("tradinggroupid", ijytid);
				obj.put("session", strSessionid);
				List<PimAgent> litpim = pimAgentService.findByMap(obj);
				if (litpim.size() > 0) {
					return R.error().put("code", -200)
							.put("msg", "当前届次已存在代办员与所选交易团的关联关系，请确认后重试。");
				}
			}

			String mima = pimAgent.getLoginpass();
			User user = (User) session.getAttribute("user");
			String pass = "";
			if(!mima.equals("")){
				pass = CryptographyUtil.md5(pimAgent.getLoginpass(),
					pimAgent.getLoginname());
				pimAgent.setLoginpass(pass);
			}
			else{
				pimAgent.setLoginpass(pass);
			}
			
			// String strSessionid = sysSessionService.getSessionID(request);
			// pimAgent.setSession(strSessionid);
			pimAgent.setCreateuser(user.getId());
			pimAgentService.save(pimAgent);
			int daibanyuanid = pimAgent.getId();

			String Types = pimAgent.getAgenttype();
			String[] strArgs = Types.split("\\@");
			List<PimAgenttype> list = new ArrayList<PimAgenttype>();
			int membertype = 0;
			if (!Types.equals("")) {
				for (String key : strArgs) {
					PimAgenttype pat = new PimAgenttype();
					pat.setAgentid(daibanyuanid);
					pat.setRounds(strSessionid);
					String[] Args = key.split("\\|");
					pat.setCardtype(Integer.valueOf(Args[0]));
					if (Args[2].equals("记者证")) {
						membertype = 1;
					}
					if (Args[1] != null && !Args[1].equals("")) {
						pat.setQuantity(Integer.valueOf(Args[1]));
					} else {
						pat.setQuantity(0);
					}
					list.add(pat);
				}
			}
			if (list.size() > 0) {
				pimAgentService.saveAgentType(list);
			}

			Member hy = new Member();
			hy.setMemberUsername(pimAgent.getLoginname());
			hy.setMemberPassword(pass);
			hy.setMemberCompany(pimAgent.getName());
			hy.setMemberType(pimAgent.getType());
			hy.setMemberStatus(2);
			hy.setMemberRegistDate(new Date());
			pimAgentService.addMember(hy);
			int memberid = hy.getMemberId();

			MemberSession ms = new MemberSession();
			ms.setId(Integer.valueOf(strSessionid));
			ms.setMemberId(memberid);
			ms.setOrgnizationId(daibanyuanid);
			memberSessionService.insert(ms);

			sysOperationLogService.CreateEntity("添加代办员", strSessionid, 0,
					user.getId(), pimAgent.getId(),
					JSONObject.toJSONString(pimAgent));

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.SETEDERROR);
		}

		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
	}

	/**
	 * 修改历届信息管理-代办员信息
	 */
	@PostMapping("/update")
	@Transactional(rollbackFor = Exception.class)
	public R update(@RequestBody PimAgent pimAgent, HttpServletRequest request,
			HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			int ijytid = pimAgent.getTradinggroupid();
			if (ijytid > 0) {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("tradinggroupid", ijytid);
				obj.put("session", strSessionid);
				List<PimAgent> litpim = pimAgentService.findByMap(obj);
				for (int j = 0; j < litpim.size(); j++) {
					if (String.valueOf(litpim.get(j).getId()).equals(
							String.valueOf(pimAgent.getId()))) {
						litpim.remove(j);
					}
				}
				if (litpim.size() > 0) {
					return R.error().put("code", -200)
							.put("msg", "当前届次已存在代办员与所选交易团的关联关系，请确认后重试。");
				}
			}

			if (!pimAgent.getLoginpass().equals("")) {
				String pass = CryptographyUtil.md5(pimAgent.getLoginpass(),
						pimAgent.getLoginname());
				pimAgent.setLoginpass(pass);
			}
			pimAgentService.update(pimAgent);

			Member hy = new Member();
			hy.setMemberUsername(pimAgent.getLoginname());
			hy.setMemberPassword(pimAgent.getLoginpass());
			hy.setMemberType(pimAgent.getType());
			hy.setMemberCompany(pimAgent.getName());
			pimAgentService.updateMemberByLoginname(hy);

			int iRet = pimAgent.getId();
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("id", iRet);
			para.put("session", strSessionid);
			pimAgentService.deleteTypeByAgentIdAndSession(para);
			String Types = pimAgent.getAgenttype();
			String[] strArgs = Types.split("\\@");
			List<PimAgenttype> list = new ArrayList<PimAgenttype>();
			if (!Types.equals("")) {
				for (String key : strArgs) {
					PimAgenttype pat = new PimAgenttype();
					pat.setAgentid(iRet);
					pat.setRounds(strSessionid);
					String[] Args = key.split("\\|");
					pat.setCardtype(Integer.valueOf(Args[0]));
					if (Args.length == 2 && Args[1] != null
							&& !Args[1].equals("")) {
						pat.setQuantity(Integer.valueOf(Args[1]));
					} else {
						pat.setQuantity(0);
					}
					list.add(pat);
				}
			}
			if (list.size() > 0) {
				pimAgentService.saveAgentType(list);
			}

			sysOperationLogService.CreateEntity("更新代办员", strSessionid, 0,
					user.getId(), pimAgent.getId(),
					JSONObject.toJSONString(pimAgent));

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.SETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

	/**
	 * 删除历届信息管理-代办员信息
	 */
	@GetMapping("/deleteById")
	@Transactional(rollbackFor = Exception.class)
	public R deleteById(@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			User user = (User) session.getAttribute("user");
			String[] Args = id.split("\\|");
			int agentid = Integer.valueOf(Args[0]);
			int memberid = Integer.valueOf(Args[1]);
			PimAgent pimAgent = pimAgentService.findById(agentid, strSessionid);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("agentid", agentid);
			map.put("memberid", memberid);
			map.put("session", strSessionid);
			// 此代办员是否存在于其他届次
			List<Map<String, Object>> list = memberSessionService
					.getNonCurrentSession(map);
			if (list.size() > 0) {
				// 存在于其他届次则之删除当前届次的关联关系
				memberSessionService.delete(map);
			} else {
				// 不存在则全部删除
				pimAgentService.deleteTypeByAgentId(agentid);
				pimAgentService.deleteById(agentid);
				memberSessionService.delete(map);
				memberService.delete(memberid);
			}
			// 删除此代办员在本届的证件信息
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("memberid", memberid);
			par.put("session", strSessionid);
			ebsPersonnelcardService.deleteByMap(par);
			ebsVehiclecardService.deleteByMap(par);

			sysOperationLogService.CreateEntity("删除代办员", strSessionid, 0,
					user.getId(), pimAgent.getId(),
					JSONObject.toJSONString(pimAgent));
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return R.ok().put("code", WConst.ERROR)
					.put("msg", WConst.DELETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
	}

	/**
	 * 批量删除历届信息管理-代办员信息
	 */
	@GetMapping("/delAll")
	@Transactional(rollbackFor = Exception.class)
	public R delAll(@RequestParam(value = "isStr") String isStr,
			HttpServletRequest request, HttpSession session) {
		String strSessionid = sysSessionService.getSessionID(request);
		User user = (User) session.getAttribute("user");
		try {
			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String id : ids) {
					if (id != null && !id.equals("")) {
						String[] Args = id.split("\\|");
						int agentid = Integer.valueOf(Args[0]);
						int memberid = Integer.valueOf(Args[1]);
						PimAgent pimAgent = pimAgentService.findById(agentid,
								strSessionid);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("agentid", agentid);
						map.put("memberid", memberid);
						map.put("session", strSessionid);
						// 此代办员是否存在于其他届次
						List<Map<String, Object>> list = memberSessionService
								.getNonCurrentSession(map);
						if (list.size() > 0) {
							// 存在于其他届次则之删除当前届次的关联关系
							memberSessionService.delete(map);
						} else {
							// 不存在则全部删除
							pimAgentService.deleteTypeByAgentId(agentid);
							pimAgentService.deleteById(agentid);
							memberSessionService.delete(map);
							memberService.delete(memberid);
						}

						// 删除此代办员在本届的证件信息
						Map<String, Object> par = new HashMap<String, Object>();
						par.put("memberid", memberid);
						par.put("session", strSessionid);
						ebsPersonnelcardService.deleteByMap(par);
						ebsVehiclecardService.deleteByMap(par);

						// pimAgentService.deleteTypeByAgentId(Integer.valueOf(id));
						// pimAgentService.deleteById(Integer.valueOf(id));
						sysOperationLogService.CreateEntity("删除代办员",
								strSessionid, 0, user.getId(),
								pimAgent.getId(),
								JSONObject.toJSONString(pimAgent));
					}
				}
			}
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return R.ok().put("code", WConst.ERROR)
					.put("msg", WConst.DELETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
	}

	@RequestMapping("/setOpenOrClose")
	@Transactional(rollbackFor = Exception.class)
	public R setOpenOrClose(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpSession session) {
		String strSessionid = sysSessionService.getSessionID(request);
		User user = (User) session.getAttribute("user");
		try {
			PimAgent pa = new PimAgent();
			pa.setId(Integer.valueOf(String.valueOf(map.get("id"))));
			pa.setIsopen(Integer.valueOf(String.valueOf(map.get("isopen"))));
			if (pa.getIsopen() == 1) {
				// 开通代办员
				OpenAgent(map, request);
			} else {
				// 关闭代办员
				CloseAgent(map, request);
			}

			sysOperationLogService.CreateEntity("开通/关闭代办员", strSessionid, 0,
					user.getId(), pa.getId(), JSONObject.toJSONString(map));
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.SETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

	private void CloseAgent(Map<String, Object> map, HttpServletRequest request) {
		String strSessionid = sysSessionService.getSessionID(request);
		PimAgent pa = new PimAgent();
		pa.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		pa.setIsopen(Integer.valueOf(String.valueOf(map.get("isopen"))));
		List<PimAgent> list = new ArrayList<PimAgent>();
		Map<String, Object> par = new HashMap<String, Object>();
		par.put("isopen", "1");
		par.put("session", strSessionid);
		if (pa.getId() == 0 && pa.getIsopen() == 1) {
			list = pimAgentService.findByMap(par);
		} else {
			par.put("id", pa.getId());
			list = pimAgentService.findByMap(par);
		}

		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Integer pid = list.get(j).getId();
				pa.setId(pid);
				pimAgentService.update(pa);
				PimAgent pimAgent = pimAgentService.findById(pid, strSessionid);
				/*
				 * String pass = CryptographyUtil.md5(WConst.DEFAULT_PASSWORD,
				 * pimAgent.getLoginname()); PimAgent ps = new PimAgent();
				 * ps.setLoginpass(pass); ps.setId(pid);
				 * pimAgentService.update(ps);
				 */

				// 关闭会员信息
				Member hy = new Member();
				hy.setMemberUsername(pimAgent.getLoginname());
				// hy.setMemberPassword(pass);
				hy.setMemberStatus(2);
				pimAgentService.updateMemberByLoginname(hy);

				/*
				 * Map<String,Object> params = new HashMap<String,Object>();
				 * params.put("loginname", pimAgent.getLoginname());
				 * List<Member> lit = memberService.findByMap(params); int
				 * memberid = lit.get(0).getMemberId();
				 * 
				 * Map<String,Object> canshu = new HashMap<String,Object>();
				 * canshu.put("memberid", memberid); canshu.put("session",
				 * strSessionid); canshu.put("companyid", pid);
				 * List<MemberSession> sess =
				 * memberSessionService.findByMap(canshu); if(sess.size()==0){
				 * MemberSession ms = new MemberSession();
				 * ms.setId(Integer.valueOf(strSessionid));
				 * ms.setMemberId(memberid); ms.setOrgnizationId(pid);
				 * memberSessionService.insert(ms); } System.out.println("发短信");
				 * /* try {
				 * sysSmsTemplateService.sendOpenAgentSMS(pimAgent.getPhone
				 * (),pimAgent.getName(),
				 * pimAgent.getLoginname(),WConst.DEFAULT_PASSWORD
				 * ,Integer.valueOf(strSessionid)); } catch
				 * (NumberFormatException e) { e.printStackTrace(); } catch
				 * (Exception e) { e.printStackTrace(); }
				 */
			}
		}

	}

	private void OpenAgent(Map<String, Object> map, HttpServletRequest request) {
		String strSessionid = sysSessionService.getSessionID(request);
		PimAgent pa = new PimAgent();
		pa.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		pa.setIsopen(Integer.valueOf(String.valueOf(map.get("isopen"))));
		List<PimAgent> list = new ArrayList<PimAgent>();
		Map<String, Object> par = new HashMap<String, Object>();
		par.put("isopen", "0");
		par.put("session", strSessionid);
		int caozuoid = pa.getId();
		if (pa.getId() == 0) {
			list = pimAgentService.findByMap(par);
		} else {
			par.put("id", pa.getId());
			list = pimAgentService.findByMap(par);
		}

		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Integer pid = list.get(j).getId();
				PimAgent pimAgent = pimAgentService.findById(pid, strSessionid);
				pa.setId(pid);
				pimAgentService.update(pa);
				// 开通代办员设置初始密码
				String random = SMSUtil.getRandomString().toLowerCase();
				String pass = CryptographyUtil.md5(random,
						pimAgent.getLoginname());
				PimAgent ps = new PimAgent();
				ps.setLoginpass(pass);
				ps.setId(pid);
				pimAgentService.update(ps);

				// 修改web_member表密码及会员状态
				Member hy = new Member();
				hy.setMemberUsername(pimAgent.getLoginname());
				hy.setMemberPassword(pass);
				hy.setMemberStatus(0);
				pimAgentService.updateMemberByLoginname(hy);

				// 激活代办员时，web_member_session中没有则增加
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("loginname", pimAgent.getLoginname());
				List<Member> lit = memberService.findByMap(params);
				int memberid = lit.get(0).getMemberId();
				Map<String, Object> canshu = new HashMap<String, Object>();
				canshu.put("memberid", memberid);
				canshu.put("session", strSessionid);
				canshu.put("companyid", pid);
				List<MemberSession> sess = memberSessionService
						.findByMap(canshu);
				if (sess.size() == 0) {
					MemberSession ms = new MemberSession();
					ms.setId(Integer.valueOf(strSessionid));
					ms.setMemberId(memberid);
					ms.setOrgnizationId(pid);
					memberSessionService.insert(ms);
				}

				if (caozuoid > 0) {
					// System.out.println("*********************************************发短信");
					if (!pimAgent.getPhone().equals("")) {
						try {
							sysSmsTemplateService.sendOpenAgentSMS(
									pimAgent.getPhone(), pimAgent.getName(),
									pimAgent.getLoginname(),
									random,
									Integer.valueOf(strSessionid),
									pimAgent.getType());
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (!pimAgent.getAreaphone().equals("")) {
						try {
							sysSmsTemplateService.sendOpenAgentSMS(
									pimAgent.getAreaphone(),
									pimAgent.getName(),
									pimAgent.getLoginname(),
									random,
									Integer.valueOf(strSessionid),
									pimAgent.getType());
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		}

	}

	// 代办员历届数据提取（菜单名称）
	@RequestMapping("/UsePimAgent")
	public R UsePimAgent(@RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		String strSessionid = sysSessionService.getSessionID(request);
		map.put("strSessionid", strSessionid);
		pimAgentService.UsePimAgent(map);

		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS);
	}

	// 历届代办员提取（菜单名称）
	@PostMapping("/UseAgent")
	@Transactional(rollbackFor = Exception.class)
	public R UseAgent(@RequestBody PimAgent pimAgent,
			HttpServletRequest request, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			int ijytid = pimAgent.getTradinggroupid();
			int iMemberId = pimAgent.getMemberid();
			if (ijytid > 0) {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("tradinggroupid", ijytid);
				obj.put("session", strSessionid);
				List<PimAgent> litpim = pimAgentService.findByMap(obj);
				for (int j = 0; j < litpim.size(); j++) {
					if (String.valueOf(litpim.get(j).getId()).equals(
							String.valueOf(pimAgent.getId()))) {
						litpim.remove(j);
					}
				}
				if (litpim.size() > 0) {
					return R.error().put("code", -200)
							.put("msg", "当前届次已存在代办员与所选交易团的关联关系，请确认后重试。");
				}
			}
			pimAgent.setCreateuser(user.getId());
			pimAgent.setIsopen(0);
			// 更新代办员信息
			pimAgentService.update(pimAgent);
			int iRet = pimAgent.getId();
			Member hy = new Member();
			hy.setMemberUsername(pimAgent.getLoginname());
			hy.setMemberCompany(pimAgent.getName());
			pimAgentService.updateMemberByLoginname(hy);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("session", strSessionid);
			param.put("companyid", iRet);
			param.put("memberid", iMemberId);
			// 验证此代办员在本届是否已存在
			List<MemberSession> lits = memberSessionService.findByMap(param);
			if (lits.size() == 0) {
				MemberSession ms = new MemberSession();
				ms.setId(Integer.valueOf(strSessionid));
				ms.setMemberId(iMemberId);
				ms.setOrgnizationId(iRet);
				memberSessionService.insert(ms);
			}

			// 处理证件类型
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("id", iRet);
			para.put("session", strSessionid);
			pimAgentService.deleteTypeByAgentIdAndSession(para);
			String Types = pimAgent.getAgenttype();
			String[] strArgs = Types.split("\\@");
			List<PimAgenttype> list = new ArrayList<PimAgenttype>();
			if (!Types.equals("")) {
				for (String key : strArgs) {
					PimAgenttype pat = new PimAgenttype();
					pat.setAgentid(iRet);
					pat.setRounds(strSessionid);
					String[] Args = key.split("\\|");

					String[] Array = Args[0].split("#");
					Map<String, Object> res = new HashMap<String, Object>();
					res.put("chinesename", Array[0]);
					res.put("session", strSessionid);
					CmCertificateType cct = cmCertificateTypeService
							.findByMap(res);
					if (cct != null) {
						pat.setCardtype(cct.getId());
						if (Args.length == 2 && Args[1] != null
								&& !Args[1].equals("")) {
							pat.setQuantity(Integer.valueOf(Args[1]));
						} else {
							pat.setQuantity(0);
						}
						list.add(pat);
					}
				}
			}
			if (list.size() > 0) {
				pimAgentService.saveAgentType(list);
			}

			// 处理交易团信息
			// 交易团为0，跳过
			if (ijytid != 0) {
				EbsTradinggroup etg = ebsTradinggroupService.findById(ijytid);
				if (!etg.getSession().equals(strSessionid)) {
					// 指定交易团的届次不等于当前届次则更新为当前届次
					EbsTradinggroup etgu = new EbsTradinggroup();
					etgu.setId(ijytid);
					etgu.setSession(strSessionid);
					ebsTradinggroupService.update(etgu);
					commonService.UpdateUserRights(strSessionid,0,2);
				}
			}

			sysOperationLogService.CreateEntity("提取代办员", strSessionid, 0,
					user.getId(), pimAgent.getId(),
					JSONObject.toJSONString(pimAgent));

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.SETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

}