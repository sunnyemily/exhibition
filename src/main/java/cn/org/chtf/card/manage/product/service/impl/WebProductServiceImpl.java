package cn.org.chtf.card.manage.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.product.dao.WebProductMapper;
import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.manage.product.service.WebProductService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

/**
 * ServiceImpl
 * @author lm
 */
@Service
public class WebProductServiceImpl implements WebProductService {

    @Autowired
    private WebProductMapper webProductDao;

    /**
     * 查询列表
     */
    @Override
    public List<WebProduct> list(Map<String, Object> map) {
        return webProductDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return webProductDao.listcount(map);
    }


    /**
     * 通过product_id查询单个
     */
    @Override
    public WebProduct findById(Integer productId) {
        return webProductDao.findById(productId);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<WebProduct> findByMap(Map<String, Object> map) {
        return webProductDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(WebProduct webProduct) {
        return webProductDao.save(webProduct);
    }

    /**
     * 修改
     */
    @Override
    public int update(WebProduct webProduct) {
    	//保证每次编辑都要进行重新审核
    	webProduct.setProductStatus(0);
        return webProductDao.update(webProduct);
    }
    
    @Override
	public int updateBackstage(WebProduct webProduct) {
    	return webProductDao.update(webProduct);
	}

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer productId) {
        return webProductDao.deleteById(productId);
    }

	@Override
	public int auditByCompanyId(Map<String, Object> map) {
		return webProductDao.auditByCompanyId(map);
	}
	
	@Override
	public int resetStatusByCompanyId(Map<String, Object> map) {
		return webProductDao.resetStatusByCompanyId(map);
	}

    /**
     * @author wushixing
     * 前端产品查询列表
     */
    @Override
    public ResultModel webList(String sessionId,Integer memberId,Integer status,String productName,String companyName,PageModel page) {
    	ResultModel result = null;
    	try {
    		Map<String, Object> filter = new HashMap<String,Object>();
			filter.put("session",sessionId);
			filter.put("memberid",memberId);
			filter.put("productName",productName);
			filter.put("companyName",companyName);
			filter.put("page",page.getStart());
			filter.put("limit",page.getLimit());
			if(status!=null) {
				filter.put("productStatus",status);
			}
    		List<WebProduct> list = webProductDao.webList(filter);
    		int c = webProductDao.webListcount(filter);
    		result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,list);
    		result.setCount(c);
    	}
    	catch(Exception e) {
    		result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e);
    		throw e;
    	}
        return result;
    }

	@Override
	public List<WebProduct> getTopProduct(
			Map<String, Object> exhibitionInfo) {		
		return webProductDao.getTopProduct(exhibitionInfo);
	}


}
