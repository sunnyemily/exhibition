package cn.org.chtf.card.manage.MakeEvidence.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
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
 * 证件类型管理ServiceImpl
 * 
 * @author lm
 */
@Service
public class CmCertificateTypeServiceImpl implements CmCertificateTypeService {

	@Autowired
	private CmCertificateTypeMapper cmCertificateTypeDao;

	/**
	 * 查询证件类型管理页面
	 * 
	 * @return 分页证件类型管理数据
	 */
	@Override
	public PageInfo<CmCertificateType> page(RequestParamsUtil requestParamsUtil) {
		PageHelper.startPage(requestParamsUtil.getPageNo(),
				requestParamsUtil.getPageSize());
		return new PageInfo<>(cmCertificateTypeDao.list(requestParamsUtil
				.getParameters()));
	}

	/**
	 * 查询证件类型管理列表
	 */
	@Override
	public List<CmCertificateType> list(Map<String, Object> map) {
		return cmCertificateTypeDao.list(map);
	}

	/**
	 * 数量证件类型管理
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return cmCertificateTypeDao.listcount(map);
	}

	/**
	 * 通过id查询单个证件类型管理
	 */
	@Override
	public CmCertificateType findById(Integer id) {
		return cmCertificateTypeDao.findById(id);
	}

	/**
	 * 通过map查询单个证件类型管理
	 */
	@Override
	public CmCertificateType findByMap(Map<String, Object> map) {
		return cmCertificateTypeDao.findByMap(map);
	}

	/**
	 * 新增证件类型管理
	 */
	@Override
	public int save(CmCertificateType cmCertificateType) {
		return cmCertificateTypeDao.save(cmCertificateType);
	}

	/**
	 * 修改证件类型管理
	 */
	@Override
	public int update(CmCertificateType cmCertificateType) {
		return cmCertificateTypeDao.update(cmCertificateType);
	}

	/**
	 * 删除证件类型管理
	 */
	@Override
	public int deleteById(Integer id) {
		return cmCertificateTypeDao.deleteById(id);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map) {
		return cmCertificateTypeDao.getList(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void UseCardType(Map<String, Object> map) {
		try {
			String isStr = String.valueOf(map.get("isStr"));
			if (isStr != null && !isStr.equals("")) {
				String[] ids = isStr.split(",");
				for (String id : ids) {
					if (id != null && !id.equals("")) {
						CmCertificateType ct = cmCertificateTypeDao
								.findById(Integer.valueOf(id));
						if (ct != null) {
							Map<String, Object> zjlx = new HashMap<String, Object>();
							zjlx.put("session",
									String.valueOf(map.get("strSessionid")));
							zjlx.put("chinesename", ct.getChinesename());
							zjlx.put("type", ct.getType());
							zjlx.put("isexhibitor", ct.getIsexhibitor());
							CmCertificateType Mct = findByMap(zjlx);
							if (Mct == null) {
								ct.setId(null);
								ct.setAddtime(null);
								ct.setSession(String.valueOf(map
										.get("strSessionid")));
								save(ct);
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

	@Override
	public List<Map<String, Object>> DailyIDSummaryTableList(
			Map<String, Object> map) {
		return cmCertificateTypeDao.DailyIDSummaryTableList(map);
	}

	@Override
	public List<Map<String, Object>> DailyIDSummaryTableListRiQi(
			Map<String, Object> map) {
		return cmCertificateTypeDao.DailyIDSummaryTableListRiQi(map);
	}

	@Override
	public List<Map<String, Object>> DailyIDSummaryTableListDataByRiQi(
			Map<String, Object> map) {
		return cmCertificateTypeDao.DailyIDSummaryTableListDataByRiQi(map);
	}

	@Override
	public List<Map<String, Object>> DailyIDSummaryTableListHeJi(
			Map<String, Object> map) {
		return cmCertificateTypeDao.DailyIDSummaryTableListHeJi(map);
	}

	@Override
	public List<Map<String, Object>> CertificateSummaryFormList(
			Map<String, Object> map) {
		return cmCertificateTypeDao.CertificateSummaryFormList(map);
	}

	@Override
	public List<Map<String, Object>> CertificateSummaryFormDaiBanYuanZhengJianList(
			Map<String, Object> map) {
		return cmCertificateTypeDao
				.CertificateSummaryFormDaiBanYuanZhengJianList(map);
	}

}
