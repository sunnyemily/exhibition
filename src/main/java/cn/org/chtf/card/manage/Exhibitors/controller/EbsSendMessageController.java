package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageLogService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.support.util.WConst;
import lombok.extern.slf4j.Slf4j;

/**
 * 展位申请表Controller
 * @author ggwudivs
 */
@Slf4j
@RestController
@RequestMapping("/manage/Exhibitors/ebsSendMessage")
public class EbsSendMessageController {

    @Autowired
    private EbsSendMessageLogService ebsSendMessageLogService;
    
    @Autowired
    private SysSmsTemplateService sysSmsTemplateService;
    
    @RequestMapping("/sendMessage")
    public R sendMessage(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	String type = map.get("type").toString();
		try {
			if(sysSmsTemplateService.sendMessage(map.get("number").toString(), map.get("content").toString(), map.get("receiver").toString(), type)){
				return R.ok().put("code", WConst.SUCCESS);
			}else{
				return R.error().put("msg", "发送失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(map.toString());
			return R.error().put("msg", "发送异常："+e.getMessage());
		}
    }
    
}