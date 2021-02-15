package cn.org.chtf.card.manage.Exhibitors.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroom;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomtypeService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsShowroomMapper;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.RequestParamsUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参展商管理-展厅管理ServiceImpl
 * 
 * @author lm
 */
@Service
public class EbsShowroomServiceImpl implements EbsShowroomService {

	@Autowired
	private EbsShowroomMapper ebsShowroomDao;

	@Autowired
	private EbsShowroomtypeService ebsShowroomtypeService;

	@Autowired
	private EbsBoothService ebsBoothService;

	/**
	 * 查询参展商管理-展厅管理页面
	 * 
	 * @return 分页参展商管理-展厅管理数据
	 */
	@Override
	public PageInfo<EbsShowroom> page(RequestParamsUtil requestParamsUtil) {
		PageHelper.startPage(requestParamsUtil.getPageNo(),
				requestParamsUtil.getPageSize());
		return new PageInfo<>(ebsShowroomDao.list(requestParamsUtil
				.getParameters()));
	}

	/**
	 * 查询参展商管理-展厅管理列表
	 */
	@Override
	public List<EbsShowroom> list(Map<String, Object> map) {
		return ebsShowroomDao.list(map);
	}

	/**
	 * 数量参展商管理-展厅管理
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return ebsShowroomDao.listcount(map);
	}

	/**
	 * 通过id查询单个参展商管理-展厅管理
	 */
	@Override
	public EbsShowroom findById(Integer id) {
		return ebsShowroomDao.findById(id);
	}

	/**
	 * 通过map查询单个参展商管理-展厅管理
	 */
	@Override
	public EbsShowroom findByMap(Map<String, Object> map) {
		return ebsShowroomDao.findByMap(map);
	}

	/**
	 * 新增参展商管理-展厅管理
	 */
	@Override
	public int save(EbsShowroom ebsShowroom) {
		return ebsShowroomDao.save(ebsShowroom);
	}

	/**
	 * 修改参展商管理-展厅管理
	 */
	@Override
	public int update(EbsShowroom ebsShowroom) {
		return ebsShowroomDao.update(ebsShowroom);
	}

	/**
	 * 删除参展商管理-展厅管理
	 */
	@Override
	public int deleteById(Integer id) {
		return ebsShowroomDao.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void UseShowRoom(Map<String, Object> map) {
		try {
			String isStr = String.valueOf(map.get("isStr"));
			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String id : ids) {
					if (id != null && !id.equals("")) {
						// 获取展厅信息
						EbsShowroom sr = findById(Integer.valueOf(id));
						if (sr != null) {
							// 获取展厅类型
							int showroomtypeid = sr.getType();
							EbsShowroomtype st = ebsShowroomtypeService
									.findById(showroomtypeid);
							// 验证展厅类型是否已存在
							Map<String, Object> roomtype = new HashMap<String, Object>();
							roomtype.put("session",
									String.valueOf(map.get("strSessionid")));
							roomtype.put("name", st.getName());
							roomtype.put("tollmehod", st.getTollmehod());
							int newshowroomtypeid = 0;
							EbsShowroomtype ebsShowroomtype = ebsShowroomtypeService
									.findByMap(roomtype);
							if (ebsShowroomtype == null) {
								st.setSession(String.valueOf(map
										.get("strSessionid")));
								st.setId(null);
								st.setAddtime(null);
								ebsShowroomtypeService.save(st);
								newshowroomtypeid = st.getId();
							} else {
								newshowroomtypeid = ebsShowroomtype.getId();
							}
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("session",
									String.valueOf(map.get("strSessionid")));
							params.put("code", sr.getCode());
							params.put("name", sr.getName());
							params.put("type", newshowroomtypeid);
							// 验证展厅是否已存在
							int showroomid = 0;
							EbsShowroom newsr = findByMap(params);
							if (newsr == null) {
								sr.setSession(String.valueOf(map
										.get("strSessionid")));
								sr.setType(newshowroomtypeid);
								sr.setId(null);
								sr.setAddtime(null);
								save(sr);
								showroomid = sr.getId();
							} else {
								showroomid = newsr.getId();
							}
							if (String.valueOf(map.get("leixing")).equals("1")) {
								// 拉取展位
								List<EbsBooth> list = ebsBoothService
										.getBoothList(id);
								if (list.size() > 0) {
									for (int j = 0; j < list.size(); j++) {
										Map<String, Object> booth = new HashMap<String, Object>();
										booth.put("session", String.valueOf(map
												.get("strSessionid")));
										booth.put("name", list.get(j).getName());
										booth.put("showRoomId", showroomid);
										EbsBooth bt = ebsBoothService
												.findByMap(booth);
										if (bt == null) {
											EbsBooth Mbooth = list.get(j);
											Mbooth.setSession(String.valueOf(map
													.get("strSessionid")));
											Mbooth.setShowRoomId(showroomid);
											Mbooth.setId(null);
											Mbooth.setAddtime(null);
											ebsBoothService.save(Mbooth);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}

	/**
	 * @author wushixing
	 */
	@Override
	public List<EbsShowroom> groupRooms(Integer memberId, Integer sessionId) {

		return ebsShowroomDao.groupRooms(memberId, sessionId);
	}

}
