package cn.org.chtf.card.manage.product.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

/**
 * Service
 * @author lm
 */
public interface WebProductService {

	/**
	 * 查询列表
	 */
	List<WebProduct> list(Map<String, Object> map);

	/**
	 * 通过product_id查询单个
	 */
    WebProduct findById(Integer productId);

	/**
	 * 通过map查询单个
	 */
	List<WebProduct> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(WebProduct webProduct);

	/**
	 * 修改
	 */
	int update(WebProduct webProduct);

	/**
	 * 删除
	 */
	int deleteById(Integer productId);
	
	int listcount(Map<String, Object> map);

	int updateBackstage(WebProduct webProduct);

	int auditByCompanyId(Map<String, Object> map);
	
	int resetStatusByCompanyId(Map<String, Object> map);

	/**
	 * @author wushixing
	 * 前端产品查询列表
	 */
	ResultModel webList(String sessionId, Integer memberId, Integer status,String productName,String companyName,PageModel page);

	List<WebProduct> getTopProduct(Map<String, Object> exhibitionInfo);
}
