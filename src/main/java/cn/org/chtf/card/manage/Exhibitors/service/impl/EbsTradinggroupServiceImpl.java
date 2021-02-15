package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsTradinggroupMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroom;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroupboothallocation;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomtypeService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupboothallocationService;

/**
 * 参展商管理-交易团ServiceImpl
 * 
 * @author lm
 */
@Service
public class EbsTradinggroupServiceImpl implements EbsTradinggroupService {

	@Autowired
	private EbsTradinggroupMapper ebsTradinggroupDao;

	@Autowired
	private EbsShowroomService ebsShowroomService;
	@Autowired
	private EbsShowroomtypeService ebsShowroomtypeService;
	@Autowired
	private EbsBoothService ebsBoothService;
	
	@Autowired
    private EbsCompanyinfoMapper ebsCompanyinfoDao;

	@Autowired
	private EbsTradinggroupboothallocationService ebsTradinggroupboothallocationService;

	/**
	 * 查询参展商管理-交易团页面
	 * 
	 * @return 分页参展商管理-交易团数据
	 */
	@Override
	public PageInfo<EbsTradinggroup> page(RequestParamsUtil requestParamsUtil) {
		PageHelper.startPage(requestParamsUtil.getPageNo(),
				requestParamsUtil.getPageSize());
		return new PageInfo<>(ebsTradinggroupDao.list(requestParamsUtil
				.getParameters()));
	}

	/**
	 * 查询参展商管理-交易团列表
	 */
	@Override
	public List<EbsTradinggroup> list(Map<String, Object> map) {
		return ebsTradinggroupDao.list(map);
	}

	/**
	 * 数量参展商管理-交易团
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return ebsTradinggroupDao.listcount(map);
	}

	/**
	 * 通过id查询单个参展商管理-交易团
	 */
	@Override
	public EbsTradinggroup findById(Integer id) {
		return ebsTradinggroupDao.findById(id);
	}

	/**
	 * 通过map查询单个参展商管理-交易团
	 */
	@Override
	public EbsTradinggroup findByMap(Map<String, Object> map) {
		return ebsTradinggroupDao.findByMap(map);
	}

	/**
	 * 新增参展商管理-交易团
	 */
	@Override
	public int save(EbsTradinggroup ebsTradinggroup) {
		return ebsTradinggroupDao.save(ebsTradinggroup);
	}

	/**
	 * 修改参展商管理-交易团
	 */
	@Override
	public int update(EbsTradinggroup ebsTradinggroup) {
		return ebsTradinggroupDao.update(ebsTradinggroup);
	}

	/**
	 * 删除参展商管理-交易团
	 */
	@Override
	public int deleteById(Integer id) {
		return ebsTradinggroupDao.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int UseTradingGroup(Map<String, Object> map) {
		try {
			String isStr = String.valueOf(map.get("isStr"));
			String oldsession = map.get("oldsession").toString();
			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String id : ids) {
					if (id != null && !id.equals("")) {
						//UPDATE逻辑
						EbsTradinggroup tg = new EbsTradinggroup();
						tg.setId(Integer.valueOf(id));
						tg.setSession(String.valueOf(map.get("strSessionid")));
						update(tg);
						
						//提取交易团关联的企业
						Map<String,Object> par = new HashMap<String,Object>();
						par.put("tradinggroupid", id);
						par.put("session", oldsession);
						List<EbsCompanyinfo> listCompany = ebsCompanyinfoDao.findByMap(par);
						if(listCompany.size()>0){
							for(int j=0;j<listCompany.size();j++){
								EbsCompanyinfo eci = listCompany.get(j);
								eci.setSession(String.valueOf(map.get("strSessionid")));
								Map<String,Object> para = new HashMap<String,Object>();
								para.put("chinesename", eci.getChinesename());
								para.put("session", String.valueOf(map.get("strSessionid")));
								List<EbsCompanyinfo> lit = ebsCompanyinfoDao.findByMap(para);
								if(lit.size()==0){
									ebsCompanyinfoDao.save(eci);
								}
							}
						}
						
						
						/*INSERT逻辑
						EbsTradinggroup tg = ebsTradinggroupDao.findById(Integer.valueOf(id));
						if (tg != null) {
							Map<String, Object> jyt = new HashMap<String, Object>();
							jyt.put("session",String.valueOf(map.get("strSessionid")));
							jyt.put("name", tg.getName());
							jyt.put("type", tg.getType());
							EbsTradinggroup Mtg = findByMap(jyt);
							int jytid = 0;
							if (Mtg == null) {
								tg.setId(null);
								tg.setAddtime(null);
								tg.setSession(String.valueOf(map
										.get("strSessionid")));
								save(tg);
								jytid = tg.getId();
							} else {
								jytid = Mtg.getId();
							}
							
							if (String.valueOf(map.get("leixing")).equals("1")) {
								// 拉取展位分配
								Map<String, Object> zwfp = new HashMap<String, Object>();
								zwfp.put("tradinggroupid", id);
								List<EbsTradinggroupboothallocation> list = ebsTradinggroupboothallocationService
										.findByMap(zwfp);
								if (list.size() > 0) {
									for (int j = 0; j < list.size(); j++) {
										EbsTradinggroupboothallocation fp = list
												.get(j);
										int oldshowroomid = fp.getShowroomid();
										int oldboothid = fp.getBoothid();
										EbsTradinggroupboothallocation newtl = LaQuZhanTingZhanWei(
												oldshowroomid, oldboothid,
												String.valueOf(map
														.get("strSessionid")));
										newtl.setTradinggroupid(jytid);
										newtl.setSession(String.valueOf(map
												.get("strSessionid")));
										Map<String, Object> obj = new HashMap<String, Object>();
										obj.put("session", String.valueOf(map
												.get("strSessionid")));
										obj.put("tradinggroupid",
												newtl.getTradinggroupid());
										obj.put("showroomid",
												newtl.getShowroomid());
										obj.put("boothid", newtl.getBoothid());
										List<EbsTradinggroupboothallocation> lit = ebsTradinggroupboothallocationService
												.findByMap(obj);
										if (lit.size() == 0) {
											ebsTradinggroupboothallocationService
													.save(newtl);
										}
									}
								}
							}
						}*/
					}
				}
			}
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	private EbsTradinggroupboothallocation LaQuZhanTingZhanWei(
			int oldshowroomid, int oldboothid, String strSessionid) {
		EbsTradinggroupboothallocation tl = new EbsTradinggroupboothallocation();
		// 获取展厅信息
		EbsShowroom sr = ebsShowroomService.findById(oldshowroomid);
		if (sr != null) {
			// 获取展厅类型
			int showroomtypeid = sr.getType();
			EbsShowroomtype st = ebsShowroomtypeService
					.findById(showroomtypeid);
			// 验证展厅类型是否已存在
			Map<String, Object> roomtype = new HashMap<String, Object>();
			roomtype.put("session", strSessionid);
			roomtype.put("name", st.getName());
			roomtype.put("tollmehod", st.getTollmehod());
			int newshowroomtypeid = 0;
			EbsShowroomtype ebsShowroomtype = ebsShowroomtypeService
					.findByMap(roomtype);
			if (ebsShowroomtype == null) {
				st.setSession(strSessionid);
				st.setId(null);
				st.setAddtime(null);
				ebsShowroomtypeService.save(st);
				newshowroomtypeid = st.getId();
			} else {
				newshowroomtypeid = ebsShowroomtype.getId();
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("session", strSessionid);
			params.put("code", sr.getCode());
			params.put("name", sr.getName());
			params.put("type", newshowroomtypeid);
			// 验证展厅是否已存在
			int showroomid = 0;
			EbsShowroom newsr = ebsShowroomService.findByMap(params);
			if (newsr == null) {
				sr.setSession(strSessionid);
				sr.setType(newshowroomtypeid);
				sr.setId(null);
				sr.setAddtime(null);
				ebsShowroomService.save(sr);
				showroomid = sr.getId();
			} else {
				showroomid = newsr.getId();
			}
			tl.setShowroomid(showroomid);
			if (oldboothid > 0) {
				// 拉取展位

				// 获取旧展位信息
				EbsBooth oldbt = ebsBoothService.findById(oldboothid);
				List<EbsBooth> list = ebsBoothService.getBoothList(String
						.valueOf(oldshowroomid));
				if (list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						Map<String, Object> booth = new HashMap<String, Object>();
						booth.put("session", strSessionid);
						booth.put("name", list.get(j).getName());
						booth.put("showRoomId", showroomid);
						EbsBooth bt = ebsBoothService.findByMap(booth);
						if (bt == null) {
							EbsBooth Mbooth = list.get(j);
							Mbooth.setSession(strSessionid);
							Mbooth.setShowRoomId(showroomid);
							Mbooth.setId(null);
							Mbooth.setAddtime(null);
							ebsBoothService.save(Mbooth);
							if (oldbt.getName().equals(Mbooth.getName())) {
								tl.setBoothid(Mbooth.getId());
							}
						} else {
							if (oldbt.getName().equals(bt.getName())) {
								tl.setBoothid(bt.getId());
							}
						}
					}
				}
			} else {
				tl.setBoothid(0);
			}
		}
		return tl;
	}

	@Override
	public List<Map<String, Object>> GetBoothByTradingGroupId(
			Map<String, Object> map) {
		return ebsTradinggroupDao.GetBoothByTradingGroupId(map);
	}
	
	@Override
	public List<Map<String, Object>> selectByType(Map<String, Object> map) {
		return ebsTradinggroupDao.selectByType(map);
	}

}
