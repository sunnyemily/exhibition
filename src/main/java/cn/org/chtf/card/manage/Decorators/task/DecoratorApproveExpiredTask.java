package cn.org.chtf.card.manage.Decorators.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsDecoratorManageService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搭建商认证过期
 * 修改搭建商状态为未审核通过
 * 查询条件：认证结束时间小于当前时间 && 审核状态为审核通过或者未审核通过
 * 一天执行一次，每天1点执行
 */
@Component
@Slf4j
public class DecoratorApproveExpiredTask {

    @Autowired
    private DecoratorEbsDecoratorManageService decoratorEbsDecoratorManageService;

    @Autowired
    private SysOperationLogService sysOperationLogService;

    @Scheduled(cron =  "0 0 1 * * ?")
//    @Scheduled(cron =  "0 0/2 * * * ?")
    private  void configureTasks() throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("搭建商认证过期，开始时间:{}", DateUtil.now());
        List<Map<String, Object>> dataList = decoratorEbsDecoratorManageService.listApproveExpiredData();
        log.info("搭建商认证过期，条数:{}", dataList.size());
        if (CollectionUtil.isNotEmpty(dataList)) {
            for (Map<String, Object> decorator : dataList) {
                Object id = decorator.get("id");
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", id);
                dataMap.put("auditStatus", 1);
                decoratorEbsDecoratorManageService.updateCompanyInfo(dataMap);
                log.info("搭建商认证过期，修改审核状态数据:{}", JSONUtil.toJsonStr(dataMap));

                String act = "搭建商认证过期";
                sysOperationLogService.CreateEntity(act, null, 0, 0,
                        Integer.valueOf(id.toString()), JSONObject.toJSONString(dataMap));
                log.info("搭建商认证过期，修改审核状态记录日志成功，session为空，operator为0");
            }
        }
        log.info("搭建商认证过期，结束时间:{}，耗时:{}", DateUtil.now(), (System.currentTimeMillis() - startTime));
    }
}
