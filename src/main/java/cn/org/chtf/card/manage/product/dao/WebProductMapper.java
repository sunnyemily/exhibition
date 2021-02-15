package cn.org.chtf.card.manage.product.dao;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.product.model.WebProduct;


/**
 * DAO
 * @author lm
 */
public interface WebProductMapper {

    /**
     * 通过product_id查询单个
     */
    WebProduct findById(Integer productId);

    /**
     * 通过map查询单个
     */
    List<WebProduct> findByMap(Map<String, Object> map);

    /**
     * 查询列表
     */
    List<WebProduct> list(Map<String, Object> map);

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
    int deleteById(Integer product_id);

	/**
     * 通过map查询单个
     */
    int listcount(Map<String, Object> map);

	int auditByCompanyId(Map<String, Object> map);
	
	int resetStatusByCompanyId(Map<String, Object> map);

    /**
     * 前台查询列表
     */
    List<WebProduct> webList(Map<String, Object> map);

	/**
     * 前台查询数量列表
     */
    int webListcount(Map<String, Object> map);

	List<WebProduct> getTopProduct(Map<String, Object> exhibitionInfo);
}
