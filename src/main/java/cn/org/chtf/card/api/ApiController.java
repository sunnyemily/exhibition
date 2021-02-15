package cn.org.chtf.card.api;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.accessrecords.dao.AccessRecordsMapper;
import cn.org.chtf.card.manage.accessrecords.pojo.AccessRecords;
import cn.org.chtf.card.manage.article.service.ArticleService;
import cn.org.chtf.card.manage.basic.service.BasicService;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.menu.service.MenuService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.WebFileUtil;
import cn.org.chtf.card.support.util.http.HttpResult;
import cn.org.chtf.card.support.util.http.HttpUtil;
import cn.org.chtf.card.support.util.tencentyun.TLSSigAPIv2;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;
import cn.org.chtf.card.support.util.tencentyun.Util;

@RestController
@RequestMapping(value= {"/api"})
/**
 * 第三方对接的全都放在这个控制器里
 * @author wushixing
 *
 */
public class ApiController {
	Map<String,Object> exhibitionInfo;

	ApiController(){
	}	
	@Resource(name = "MenuServiceImpl")
	MenuService menuService;	
	@Resource(name = "MemberServiceImpl")
	MemberService memberService;

	@Autowired
	SysSessionService sessionService;
	
	@Resource(name="ArticleServiceImpl")
	private ArticleService articleService;
	@Resource(name="BasicServiceImpl")
	private BasicService basicService;
	@Resource(name="BasicSettingServiceImpl")
	private BasicSettingService basicSettingService;
	@Resource
	private CmCertificateTypeService certificateType;
    @Autowired
    private AccessRecordsMapper accessDAO;
    
    @Resource
    private SMSUtil sMSUtil;
	
	@Autowired
    private HttpServletRequest request;
	@Resource
    private HttpUtil httpAPIService;
    
    /**
	 * 首页接口
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value= {"/access/records"})
	@ResponseBody
	public ResultModel accessRecords(AccessRecords accessRecords,HttpSession session,HttpServletRequest request) throws IOException {
		setCommonContent();
		ResultModel result = new ResultModel();
		result.setStatus(WConst.SUCCESS);
		result.setMsg("接收成功");
		AccessRecords existRecords = accessDAO.selectByPrimaryKey(accessRecords.getId());
		if(existRecords==null) {
			if(accessRecords != null&&accessRecords.getId()!=null) {
				accessDAO.insertSelective(accessRecords);
				accessDAO.updatePiaoStatus(accessRecords);
			}
			else {
				result.setStatus(WConst.ERROR);
				result.setMsg("识别号不可为空");

			}
			result.setResult(accessRecords);
		}
		else {
			result.setStatus(0);//0代表已存在
			result.setMsg("该数据已接收过。");
			result.setResult(accessRecords);
		}
		return result;
	}

	/**
	 * 首页接口
	 * @param language
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value= {"/im/user/sign"})
	public ResultModel  getUserSign(String userid) {
		Integer appid = Util.IM_APP_ID;
		String sign = Util.IM_APP_KEY;
		TLSSigAPIv2 tlss = new TLSSigAPIv2(appid,sign);
		Integer EXPIRETIME = 7 * 24 * 60 * 60;
		String sig = tlss.genUserSig(userid, EXPIRETIME);
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,sig);
	}
	@RequestMapping(value= {"/im/user/login"})
	public ResultModel  imLogin(HttpSession session) {
		Object userObject = session.getAttribute("wechatUser");

		if (userObject == null) {
			return WConst.RELOGINJSON;
		}
		Integer appid = Util.IM_APP_ID;
		String sign = Util.IM_APP_KEY;
		TLSSigAPIv2 tlss = new TLSSigAPIv2(appid,sign);
		Integer EXPIRETIME = 7 * 24 * 60 * 60;
		WechatUser user = (WechatUser) userObject;
		String sig = tlss.genUserSig(user.getUid().toString(), EXPIRETIME);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("sig", sig);
		res.put("uid", user.getUid().toString());
		res.put("nick", user.getNickname());
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,res);
	}

	/**
	 * 证件推送测试接口
	 */
	@RequestMapping(value= {"/push/card"})
	public ResultModel  cardPush() {
		String url = "http://i345b15646.wicp.vip:46550/api/identityTicket/add";
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		try {
			Map<String,Object> mapJson = new HashMap<String,Object>();
			mapJson.put("ticketNum", "20010000003");
			mapJson.put("name", "罗华");
			mapJson.put("idno", "232603197810014000");
			mapJson.put("isLimit", 1);
			mapJson.put("limit", 1);
			mapJson.put("isVip", 0);
			mapJson.put("startDate", "20201021");
			mapJson.put("endDate", "20201021");
			strJson = mapper.writeValueAsString(mapJson);
			System.out.println("准备推送票证信息：");
			System.out.println(strJson);
			HttpResult result = httpAPIService.postJson(url, strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			String returnStrJson = mapper.writeValueAsString(strResult);
			System.out.println("接收到的返回信息：");
			System.out.println(returnStrJson);
			
           FileOutputStream fileOutputStream = null;
           File file = new File("./log/card/232603197810014000-zj.txt");
           if(file.exists()){
               //判断文件是否存在，如果不存在就新建一个txt
               file.createNewFile();
           }
           fileOutputStream = new FileOutputStream(file);
           fileOutputStream.write(("准备推送证件信息："+strJson+"接收到的返回信息："+returnStrJson).getBytes());
           fileOutputStream.flush();
           fileOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
	}

	/**
	 * 证件推送测试接口
	 */
	@RequestMapping(value= {"/push/photo"})
	public ResultModel  cardPhoto() {
		String url = "http://i345b15646.wicp.vip:46550/api/identityTicket/upload-photo";
		ObjectMapper mapper = new ObjectMapper();
		String strJson;
		try {
			Map<String,Object> mapJson = new HashMap<String,Object>();
			mapJson.put("idno", "232603197810014000");
			String strBase = "data:image/jpg;base64,"+WebFileUtil.ImageToBase64("./static/test.jpg");
			mapJson.put("photo", strBase);
			strJson = mapper.writeValueAsString(mapJson);
			System.out.println("准备推送照片信息：");
			System.out.println(strJson);
			HttpResult result = httpAPIService.postJson(url, strJson);
			Map strResult = mapper.readValue(result.getBody(), Map.class);
			String returnStrJson = mapper.writeValueAsString(strResult);
			System.out.println("接收到的返回信息：");
			System.out.println(returnStrJson);

		       try {
		           FileOutputStream fileOutputStream = null;
		           File file = new File("./log/card/232603197810014000-zp.txt");
		           if(file.exists()){
		               //判断文件是否存在，如果不存在就新建一个txt
		               file.createNewFile();
		           }
		           fileOutputStream = new FileOutputStream(file);
		           fileOutputStream.write(("准备推送照片信息："+strJson+"接收到的返回信息："+returnStrJson).getBytes());
		           fileOutputStream.flush();
		           fileOutputStream.close();
		       }
		       catch(Exception e) {
		    	   e.printStackTrace();
		       }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
	}


	/**
	 * BASE64转码接口
	 */
	@RequestMapping(value= {"/turn/jpg"})
	public ResultModel  testTurnJPG(String imagePath) {
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,WebFileUtil.turnToJPGBASE64(imagePath));
	}
	public void setCommonContent() {
		exhibitionInfo = sessionService.getExhibitionInfo(request);
	}
}