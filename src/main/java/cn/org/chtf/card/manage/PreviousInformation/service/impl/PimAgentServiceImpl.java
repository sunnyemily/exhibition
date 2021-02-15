package cn.org.chtf.card.manage.PreviousInformation.service.impl;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsTradinggroupMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgenttype;
import cn.org.chtf.card.manage.PreviousInformation.service.PimAgentService;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.member.dao.MemberDAO;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.manage.membersession.service.MemberSessionService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 历届信息管理-代办员信息ServiceImpl
 * 
 * @author ggwudivs
 */
@Service
public class PimAgentServiceImpl implements PimAgentService {

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private PimAgentMapper pimAgentDao;

	@Autowired
	private MemberSessionService memberSessionService;

	@Autowired
	private SysSessionService sysSessionService;

	@Autowired
	private EbsCompanyinfoMapper ebsCompanyinfoDao;

	/**
	 * 查询历届信息管理-代办员信息列表
	 */
	@Override
	public List<PimAgent> list(Map<String, Object> map) {
		return pimAgentDao.list(map);
	}

	/**
	 * 数量历届信息管理-代办员信息
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return pimAgentDao.listcount(map);
	}

	/**
	 * 通过id查询单个历届信息管理-代办员信息
	 */
	@Override
	public PimAgent findById(Integer id, String session) {
		Map<String, Object> par = new HashMap<String, Object>();
		par.put("id", id);
		par.put("session", session);
		return pimAgentDao.findById(id, session);
	}

	/**
	 * 通过map查询单个历届信息管理-代办员信息
	 */
	@Override
	public List<PimAgent> findByMap(Map<String, Object> map) {
		return pimAgentDao.findByMap(map);
	}

	/**
	 * 新增历届信息管理-代办员信息
	 */
	@Override
	public int save(PimAgent pimAgent) {
		return pimAgentDao.save(pimAgent);
	}

	/**
	 * 修改历届信息管理-代办员信息
	 */
	@Override
	public int update(PimAgent pimAgent) {
		return pimAgentDao.update(pimAgent);
	}

	/**
	 * 删除历届信息管理-代办员信息
	 */
	@Override
	public int deleteById(Integer id) {
		return pimAgentDao.deleteById(id);
	}

	@Override
	public int saveAgentType(List<PimAgenttype> list) {
		return pimAgentDao.saveAgentType(list);
	}

	@Override
	public void deleteTypeByAgentId(Integer id) {
		pimAgentDao.deleteTypeByAgentId(id);
	}

	@Override
	public void addMember(Member hy) {
		memberDAO.insert(hy);
	}

	@Override
	public void updateMemberByLoginname(Member hy) {

		memberDAO.updateMemberByLoginname(hy);
	}

	@Autowired
	private EbsTradinggroupMapper ebsTradinggroupDao;
	@Autowired
	private CmCertificateTypeMapper cmCertificateTypeDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void UsePimAgent(Map<String, Object> map) {
		try {
			String isStr = String.valueOf(map.get("isStr"));
			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String id : ids) {
					if (id != null && !id.equals("")) {
						PimAgent pa = pimAgentDao.findById(Integer.valueOf(id),
								String.valueOf(map.get("strSessionid")));
						if (pa != null) {
							Map<String, Object> zjlx = new HashMap<String, Object>();
							zjlx.put("session",
									String.valueOf(map.get("strSessionid")));
							zjlx.put("loginname", pa.getLoginname());
							zjlx.put("name", pa.getName());
							List<PimAgent> list = findByMap(zjlx);
							int dbyid = 0;
							if (list.size() == 0) {
								// 交易团ID不为0，验证交易团是否存在，不存在则提取
								if (pa.getTradinggroupid() > 0) {
									EbsTradinggroup tg = ebsTradinggroupDao
											.findById(pa.getTradinggroupid());
									if (tg != null) {
										Map<String, Object> jyt = new HashMap<String, Object>();
										jyt.put("session", String.valueOf(map
												.get("strSessionid")));
										jyt.put("name", tg.getName());
										jyt.put("type", tg.getType());
										EbsTradinggroup Mtg = ebsTradinggroupDao
												.findByMap(jyt);
										int jytid = 0;
										if (Mtg == null) {
											tg.setId(null);
											tg.setAddtime(null);
											tg.setSession(String.valueOf(map
													.get("strSessionid")));
											ebsTradinggroupDao.save(tg);
											jytid = tg.getId();
										} else {
											jytid = Mtg.getId();
										}
										pa.setTradinggroupid(jytid);
									}
								}
								pa.setId(null);
								pa.setAddtime(null);
								pa.setSession(String.valueOf(map
										.get("strSessionid")));
								save(pa);
								dbyid = pa.getId();
							} else {
								dbyid = list.get(0).getId();
							}

							List<PimAgenttype> lit = pimAgentDao
									.getPimAgentTypeByAgentID(Integer
											.parseInt(id));
							if (lit.size() > 0) {
								for (int k = 0; k < lit.size(); k++) {
									PimAgenttype pat = lit.get(k);
									pat.setAgentid(dbyid);

									CmCertificateType ct = cmCertificateTypeDao
											.findById(pat.getCardtype());
									if (ct != null) {
										Map<String, Object> zjlx1 = new HashMap<String, Object>();
										zjlx1.put("session", String.valueOf(map
												.get("strSessionid")));
										zjlx1.put("chinesename",
												ct.getChinesename());
										zjlx1.put("type", ct.getType());
										zjlx1.put("isexhibitor",
												ct.getIsexhibitor());
										CmCertificateType Mct = cmCertificateTypeDao
												.findByMap(zjlx1);
										int cardTypeid = 0;
										if (Mct == null) {
											ct.setId(null);
											ct.setAddtime(null);
											ct.setSession(String.valueOf(map
													.get("strSessionid")));
											cmCertificateTypeDao.save(ct);
											cardTypeid = ct.getId();
										} else {
											cardTypeid = Mct.getId();
										}
										pat.setCardtype(cardTypeid);
									}
									List<PimAgenttype> listType = new ArrayList<PimAgenttype>();
									listType.add(pat);
									pimAgentDao.saveAgentType(listType);
								}
							}

						}
					}
				}
			}
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Override
	public PimAgent getAgentByMemberIdAndSessionId(Integer memberId,
			Integer sessionId) {
		return pimAgentDao.getAgentByMemberIdAndSessionId(memberId, sessionId);
	}

	@Override
	public List<PimAgenttype> getAgenttypeByMemberIdAndSessionId(
			Integer memberId, Integer sessionId) {
		PimAgent agent = pimAgentDao.getAgentByMemberIdAndSessionId(memberId,
				sessionId);
		return pimAgentDao.getPimAgentTypeByAgentID(agent.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R UseAgent(Map<String, Object> map) {
		try {
			String isStr = String.valueOf(map.get("isStr"));
			String newSession = String.valueOf(map.get("newSession"));
			String oldSession = String.valueOf(map.get("oldsession"));
			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String idAndMemberid : ids) {
					if (idAndMemberid != null && !idAndMemberid.equals("")) {
						String[] strArgs = idAndMemberid.split("\\|");
						String dbyid = strArgs[0];
						String memberid = strArgs[1];
						// 验证交易团
						PimAgent paa = pimAgentDao.findById(
								Integer.valueOf(dbyid), oldSession);
						int jytid = paa.getTradinggroupid();
						if (jytid > 0) {
							Map<String, Object> obj = new HashMap<String, Object>();
							obj.put("tradinggroupid", jytid);
							obj.put("session", newSession);
							List<PimAgent> litpim = pimAgentDao.findByMap(obj);
							if (litpim.size() > 0) {
								return R.error()
										.put("code", -200)
										.put("msg",
												"当前开放的届次已存在代办员与所选交易团的关联关系，无法提取数据。");
							}
						}

						MemberSession ms = new MemberSession();
						ms.setId(Integer.valueOf(newSession));
						ms.setMemberId(Integer.valueOf(memberid));
						ms.setOrgnizationId(Integer.valueOf(dbyid));
						PimAgent pat = new PimAgent();
						pat.setId(Integer.valueOf(dbyid));
						pat.setIsopen(0);
						pimAgentDao.update(pat);
						Map<String, Object> para = new HashMap<String, Object>();
						para.put("session", newSession);
						para.put("companyid", dbyid);
						para.put("memberid", memberid);
						// 验证此代办员在本届是否已存在
						List<MemberSession> lits = memberSessionService
								.findByMap(para);
						if (lits.size() == 0) {
							memberSessionService.insert(ms);
						}

						// 提取代办员企业信息
						// 取代办员关联的交易团
						PimAgent pa = pimAgentDao.findById(
								Integer.valueOf(dbyid), oldSession);
						Map<String, Object> par = new HashMap<String, Object>();
						par.put("tradinggroupid", pa.getTradinggroupid());
						par.put("session", oldSession);
						List<EbsCompanyinfo> listCompany = ebsCompanyinfoDao
								.findByMap(par);
						if (listCompany.size() > 0) {
							for (int j = 0; j < listCompany.size(); j++) {
								EbsCompanyinfo eci = listCompany.get(j);
								eci.setSession(newSession);
								// 验证企业在当前届次是否已存在
								Map<String, Object> paras = new HashMap<String, Object>();
								paras.put("chinesename", eci.getChinesename());
								paras.put("session", newSession);
								List<EbsCompanyinfo> lit = ebsCompanyinfoDao
										.findByMap(paras);
								if (lit.size() == 0) {
									ebsCompanyinfoDao.save(eci);
								}
							}
						}
					}
				}
			}
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

	@Override
	public void deleteTypeByAgentIdAndSession(Map<String, Object> para) {
		pimAgentDao.deleteTypeByAgentIdAndSession(para);
	}

	@Override
	public List<PimAgent> Previouslist(Map<String, Object> map) {		
		return pimAgentDao.Previouslist(map);
	}

	@Override
	public int PreviouslistCount(Map<String, Object> map) {
		return pimAgentDao.PreviouslistCount(map);
	}

}
