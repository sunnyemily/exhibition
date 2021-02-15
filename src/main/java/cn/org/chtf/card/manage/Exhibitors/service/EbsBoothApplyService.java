package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApply;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.support.util.ResultModel;

/**
 * 展位申请表Service
 * @author ggwudivs
 */
public interface EbsBoothApplyService {

	/**
	 * 查询展位申请表列表
	 */
	List<EbsBoothApply> list(Map<String, Object> map);

	/**
	 * 通过apply_id查询单个展位申请表
	 */
    EbsBoothApply findById(Integer applyId);

	/**
	 * 通过map查询单个展位申请表
	 */
	List<EbsBoothApply> findByMap(Map<String, Object> map);

	/**
	 * 新增展位申请表
	 */
	int save(EbsBoothApply ebsBoothApply);

	/**
	 * 修改展位申请表
	 */
	int update(EbsBoothApply ebsBoothApply);

	/**
	 * 删除展位申请表
	 */
	int deleteById(Integer applyId);
	
	int listcount(Map<String, Object> map);
	/**
     * 更新展位申请信息
     */
	public ResultModel updateApply(String apply);
	/**
     * 更新交易团企业展位申请信息
     */
	public ResultModel updateEnterpriseApply(String apply,EbsCompanyinfo company);
	/**
	 * 获取指定会员指定届次的展位申请信息
	 * @param memberId
	 * @param sessionId
	 * @return
	 */
	public ResultModel getApply(Integer memberId,Integer sessionId);

	Map<String, Object> findByCompanyId(int companyId, Integer sessionId);
}
