package cn.org.chtf.card.manage.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.system.dao.SysSessionMapper;
import cn.org.chtf.card.manage.system.dao.SysSmsTemplateMapper;
import cn.org.chtf.card.manage.system.model.SysSession;
import cn.org.chtf.card.manage.system.model.SysSmsTemplate;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.CryptographyUtil;

/**
 * 届次信息ServiceImpl
 * 
 * @author lm
 */
@Service
public class SysSessionServiceImpl implements SysSessionService {

	@Autowired
	private SysSessionMapper sysSessionDao;

	@Autowired
	private SysSmsTemplateMapper sysSmsTemplateDao;

	/**
	 * 查询届次信息页面
	 * 
	 * @return 分页届次信息数据
	 */
	@Override
	public PageInfo<SysSession> page(RequestParamsUtil requestParamsUtil) {
		PageHelper.startPage(requestParamsUtil.getPageNo(),
				requestParamsUtil.getPageSize());
		return new PageInfo<>(sysSessionDao.list(requestParamsUtil
				.getParameters()));
	}

	/**
	 * 查询届次信息列表
	 */
	@Override
	public List<SysSession> list(Map<String, Object> map) {
		return sysSessionDao.list(map);
	}

	/**
	 * 数量届次信息
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return sysSessionDao.listcount(map);
	}

	/**
	 * 通过id查询单个届次信息
	 */
	@Override
	public SysSession findById(Integer id) {
		return sysSessionDao.findById(id);
	}

	/**
	 * 通过map查询单个届次信息
	 */
	@Override
	public SysSession findByMap(Map<String, Object> map) {
		return sysSessionDao.findByMap(map);
	}

	/**
	 * 新增届次信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int save(SysSession sysSession) {
		int iRet = -1;
		try {
			// 新增届次，复制上一届次短信模板
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("exhibitionid", sysSession.getExhibitionid());
			SysSession ss = sysSessionDao.GetLastSessionInfoByExhibitionid(map);
			map.put("smsSessionId", ss.getId());
			List<SysSmsTemplate> list = sysSmsTemplateDao.findByMap(map);

			iRet = sysSessionDao.save(sysSession);
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					list.get(j).setSmsSessionId(sysSession.getId());
					SysSmsTemplate sst = new SysSmsTemplate();
					sst.setSmsSessionId(sysSession.getId());
					sst.setSmsTitle(list.get(j).getSmsTitle());
					sst.setSmsTemplate(list.get(j).getSmsTemplate());
					sysSmsTemplateDao.save(sst);
				}
			}
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();			
		}

		return iRet;
	}

	/**
	 * 修改届次信息
	 */
	@Override
	public int update(SysSession sysSession) {
		return sysSessionDao.update(sysSession);
	}

	/**
	 * 删除届次信息
	 */
	@Override
	public int deleteById(Integer id) {
		return sysSessionDao.deleteById(id);
	}

	@Override
	public String getSessionID(HttpServletRequest request) {
		String url = CryptographyUtil.GeCurrenttUrl(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		return sysSessionDao.getSessionID(map);
	}

	@Override
	public SysSession GetLastSessionInfoByExhibitionid(Map<String, Object> map) {
		return sysSessionDao.GetLastSessionInfoByExhibitionid(map);
	}

	@Override
	public List<Map<String, Object>> getHistorySession(Map<String, Object> map) {
		return sysSessionDao.getHistorySession(map);
	}

	@Override
	public List<Map<String, Object>> getHistoryCountryCount(
			Map<String, Object> map) {
		return sysSessionDao.getHistoryCountryCount(map);
	}

	@Override
	public List<Map<String, Object>> getHistoryCompanyCount(
			Map<String, Object> map) {
		return sysSessionDao.getHistoryCompanyCount(map);
	}

	@Override
	public Map<String, Object> getExhibitionInfo(HttpServletRequest request) {
		String url = CryptographyUtil.GeCurrenttUrl(request);
		return sysSessionDao.getExhibitionInfo(url);
	}

	@Override
	public List<Map<String, Object>> getAllSession(Map<String, Object> map) {
		return sysSessionDao.getAllSession(map);
	}

	@Override
	public void updateStatus(SysSession sys) {
		sysSessionDao.updateStatus(sys);
	}

}
