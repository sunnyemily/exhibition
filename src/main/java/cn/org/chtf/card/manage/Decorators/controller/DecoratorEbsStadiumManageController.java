package cn.org.chtf.card.manage.Decorators.controller;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsStadiumManageService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 搭建商报馆信息Controller
 *
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Decorators/ebsStadiumManage")
public class DecoratorEbsStadiumManageController {

    @Autowired
    private DecoratorEbsStadiumManageService decoratorEbsStadiumManageService;

    @Autowired
    private SysSessionService sysSessionService;

    @Autowired
    private SysOperationLogService sysOperationLogService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            User user = (User) session.getAttribute("user");
            map.put("userId", user.getId());
            map = ResultVOUtil.TiaoZhengFenYe(map);
            List<Map<String, Object>> list = decoratorEbsStadiumManageService.list(map);
            int count = decoratorEbsStadiumManageService.listcount(map);
            return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

    @RequestMapping(value = {"/updateStadiumInfo", "/agreeStadiumInfo", "/rejectStadiumInfo"})
    public R updateStadiumInfo(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            User user = (User) session.getAttribute("user");
            if (map.get("auditType") != null
                    && ("auditAgree".equals(map.get("auditType")) || "auditReject".equals(map.get("auditType")))) {
                map.put("audittime", new java.sql.Timestamp(System.currentTimeMillis()));
                map.put("auditername", user.getUsername());
            }
            //修改企业信息
            decoratorEbsStadiumManageService.updateStadiumInfo(map);

            sysOperationLogService.CreateEntity("修改报馆信息", strSessionid, 0, user.getId(),
                    Integer.valueOf(map.get("id").toString()), JSONObject.toJSONString(map));

            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
        }
    }

    @GetMapping("/selectStadiumInfo")
    public R selectStadiumInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        try {
            String strSessionid = sysSessionService.getSessionID(request);
            map.put("session", strSessionid);
            Map<String, Object> stadiumInfo = decoratorEbsStadiumManageService.selectStadiumInfo(map);
            return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", stadiumInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
        }
    }

}