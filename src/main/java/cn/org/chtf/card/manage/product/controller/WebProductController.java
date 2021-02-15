package cn.org.chtf.card.manage.product.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.manage.product.service.WebProductService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

/**
 * Controller
 * @author lm
 */
@RestController
@RequestMapping("/manage/Product/webProduct")
public class WebProductController {
	
	@Autowired
	private SysOperationLogService sysOperationLogService;

    @Autowired
    private WebProductService webProductService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<WebProduct> list = webProductService.list(map);			
			int count = webProductService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过product_id查询单个
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<WebProduct> webProduct =webProductService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", webProduct);
    } 
    

    /**
     * 通过product_id查询单个
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "product_id") Integer product_id) {
        WebProduct webProduct =webProductService.findById(product_id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", webProduct);
    }  
    
    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody WebProduct webProduct,HttpServletRequest request) {
    	//String strSessionid = sysSessionService.getSessionID(request);
    	
        webProductService.save(webProduct);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody WebProduct webProduct) {
        webProductService.update(webProduct);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }
    
    /**根据companyId审核产品
     * @param map
     * @return
     */
    @PostMapping("/auditByCompanyId")
    public R auditByCompanyId(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
		map.put("session",strSessionid);
    	webProductService.auditByCompanyId(map);
    	User user = (User) session.getAttribute("user");
    	sysOperationLogService.CreateEntity("审核单个公司所有产品", strSessionid, 0, user.getId(), 
        		0, JSONObject.toJSONString(map));
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }
    
    /**
     * 后台审核修改
     */
    @PostMapping(value= {"/updateBackstage","/againAudit"})
    public R updateBackstage(@RequestBody WebProduct webProduct) {
        webProductService.updateBackstage(webProduct);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "product_id") Integer product_id) {
        webProductService.deleteById(product_id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				webProductService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }
}