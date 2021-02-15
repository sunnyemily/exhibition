package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApply;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.support.util.WConst;

/**
 * 展位申请表Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsBoothApply")
public class EbsBoothApplyController {

    @Autowired
    private EbsBoothApplyService ebsBoothApplyService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsBoothApply> list = ebsBoothApplyService.list(map);			
			int count = ebsBoothApplyService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过apply_id查询单个展位申请表
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<EbsBoothApply> ebsBoothApply =ebsBoothApplyService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsBoothApply);
    } 
    

    /**
     * 通过apply_id查询单个展位申请表
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "apply_id") Integer apply_id) {
        EbsBoothApply ebsBoothApply =ebsBoothApplyService.findById(apply_id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsBoothApply);
    }   

    /**
     * 添加展位申请表
     */
    @PostMapping("/save")
    public R save(@RequestBody EbsBoothApply ebsBoothApply,HttpServletRequest request) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	ebsBoothApply.setSessionId(Integer.valueOf(strSessionid));
        ebsBoothApplyService.save(ebsBoothApply);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改展位申请表
     */
    @PostMapping("/update")
    public R update(@RequestBody EbsBoothApply ebsBoothApply) {
        ebsBoothApplyService.update(ebsBoothApply);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除展位申请表
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "apply_id") Integer apply_id) {
        ebsBoothApplyService.deleteById(apply_id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除展位申请表
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr){
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				ebsBoothApplyService.deleteById(Integer.valueOf(id));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}