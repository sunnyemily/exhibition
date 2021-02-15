package cn.org.chtf.card.manage.MakeEvidence.service;

import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;

import java.util.List;
import java.util.Map;

/**
 * 证件类型管理Service
 * @author lm
 */
public interface CmCertificateTypeService {

	/**
	 * 查询证件类型管理页面
	 * @return 分页证件类型管理数据
	 */
	PageInfo<CmCertificateType> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询证件类型管理列表
	 */
	List<CmCertificateType> list(Map<String, Object> map);

	/**
	 * 通过id查询单个证件类型管理
	 */
    CmCertificateType findById(Integer id);

	/**
	 * 通过map查询单个证件类型管理
	 */
	CmCertificateType findByMap(Map<String, Object> map);

	/**
	 * 新增证件类型管理
	 */
	int save(CmCertificateType cmCertificateType);

	/**
	 * 修改证件类型管理
	 */
	int update(CmCertificateType cmCertificateType);

	/**
	 * 删除证件类型管理
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> getList(Map<String, Object> map);

	void UseCardType(Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableList(Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableListRiQi(Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableListDataByRiQi(Map<String, Object> map);

	List<Map<String, Object>> DailyIDSummaryTableListHeJi(Map<String, Object> map);

	List<Map<String, Object>> CertificateSummaryFormList(Map<String, Object> map);

	List<Map<String, Object>> CertificateSummaryFormDaiBanYuanZhengJianList(Map<String, Object> map);
}
