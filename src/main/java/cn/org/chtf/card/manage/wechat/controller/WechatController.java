package cn.org.chtf.card.manage.wechat.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.http.HttpUtil;
import cn.org.chtf.card.support.util.wechat.Account;

@RestController
public class WechatController {
	@Resource
    private HttpUtil httpAPIService;
	@Resource
    private cn.org.chtf.card.support.util.wechat.wechatApi wechatApi;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Resource Account account;

    @RequestMapping("/getMenus")
    public ResultModel getMenus() throws Exception {
    	ResultModel result = new ResultModel();
    	result.setStatus(WConst.SUCCESS);
    	result.setMsg("查询成功");
    	result.setResult(wechatApi.queryMenus());
        return result;
    }

    @RequestMapping("/createMenus")
    public String createMenus() throws Exception {
        boolean result = wechatApi.createMenu("{\r\n" + 
        		"     \"button\":[\r\n" + 
        		"     {    \r\n" + 
        		"          \"type\":\"click\",\r\n" + 
        		"          \"name\":\"今日歌曲\",\r\n" + 
        		"          \"key\":\"V1001_TODAY_MUSIC\"\r\n" + 
        		"      },\r\n" + 
        		"      {\r\n" + 
        		"           \"name\":\"菜单\",\r\n" + 
        		"           \"sub_button\":[\r\n" + 
        		"           {    \r\n" + 
        		"               \"type\":\"view\",\r\n" + 
        		"               \"name\":\"搜索\",\r\n" + 
        		"               \"url\":\"http://www.soso.com/\"\r\n" + 
        		"            },\r\n" + 
        		"            {\r\n" + 
        		"               \"type\":\"click\",\r\n" + 
        		"               \"name\":\"赞一下我们\",\r\n" + 
        		"               \"key\":\"V1001_GOOD\"\r\n" + 
        		"            }]\r\n" + 
        		"       }]\r\n" + 
        		" }");
        if(result) {
        	return "OK";
        }
        return "false";
    }
}
