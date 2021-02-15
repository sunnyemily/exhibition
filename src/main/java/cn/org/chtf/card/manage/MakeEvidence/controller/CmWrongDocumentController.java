package cn.org.chtf.card.manage.MakeEvidence.controller;

import cn.org.chtf.card.manage.MakeEvidence.model.CmWrongDocument;
import cn.org.chtf.card.manage.MakeEvidence.service.CmWrongDocumentService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.List;

/**
 * Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/MakeEvidence/cmWrongDocument")
public class CmWrongDocumentController {

    @Autowired
    private CmWrongDocumentService cmWrongDocumentService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<CmWrongDocument> list = cmWrongDocumentService.list(map);			
			int count = cmWrongDocumentService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
	/**
     * 通过id查询单个
     */
    @GetMapping("/findByMap")
    public R findByMap(@RequestParam Map<String,Object> map) {
        List<CmWrongDocument> cmWrongDocument =cmWrongDocumentService.findByMap(map);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", cmWrongDocument);
    } 
    

    /**
     * 通过id查询单个
     */
    @GetMapping("/findById")
    public R findById(@RequestParam(value = "id") Integer id) {
        CmWrongDocument cmWrongDocument =cmWrongDocumentService.findById(id);
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", cmWrongDocument);
    }   

    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody CmWrongDocument cmWrongDocument,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	cmWrongDocument.setSession(strSessionid);
        cmWrongDocumentService.save(cmWrongDocument);
        sysOperationLogService.CreateEntity("添加错误证件", strSessionid, 0, user.getId(), 
        		cmWrongDocument.getId(), JSONObject.toJSONString(cmWrongDocument));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CmWrongDocument cmWrongDocument,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
        cmWrongDocumentService.update(cmWrongDocument);
        sysOperationLogService.CreateEntity("更新错误证件", strSessionid, 0, user.getId(), 
        		cmWrongDocument.getId(), JSONObject.toJSONString(cmWrongDocument));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public R deleteById(@RequestParam(value = "id") Integer id,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	CmWrongDocument cwd = cmWrongDocumentService.findById(id);
        cmWrongDocumentService.deleteById(id);
        sysOperationLogService.CreateEntity("删除错误证件", strSessionid, 0, user.getId(), 
        		cwd.getId(), JSONObject.toJSONString(cwd));
        return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

    }
    
    /**
     * 批量删除
     */
    @GetMapping("/delAll")
    public R delAll(@RequestParam(value = "isStr") String isStr,HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	if (isStr!=null &&!isStr.equals("")) {
    		String[] ids = isStr.split(",");
    		for (String id : ids) {
    			if(id!=null &&!id.equals("")){
    				CmWrongDocument cwd = cmWrongDocumentService.findById(Integer.valueOf(id));
    				cmWrongDocumentService.deleteById(Integer.valueOf(id));
    				sysOperationLogService.CreateEntity("删除错误证件", strSessionid, 0, user.getId(), 
    		        		cwd.getId(), JSONObject.toJSONString(cwd));
    			}
    		}
    	}
    	return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    }

}