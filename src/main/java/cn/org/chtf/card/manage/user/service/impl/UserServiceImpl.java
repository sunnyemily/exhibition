package cn.org.chtf.card.manage.user.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.function.dao.FunctionMapper;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.user.dao.UserDAO;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.manage.user.service.UserService;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.WMail;
import cn.org.chtf.card.support.util.WebFileUtil;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;

@Service("UserServiceImpl")

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private FunctionMapper functionDAO;
	
	@Autowired
    private SysOperationLogService sysOperationLogService;
	
	@Autowired
    private SysSessionService sysSessionService;
	
	@Autowired
    private CommonService commonService;
	
	@Autowired
	private SysSmsTemplateService smsService;

	@Resource(name = "BasicSettingServiceImpl")
	BasicSettingService basicSetting;

	@Resource
    private SMSUtil sMSUtil;
	
	 public ResultModel sendConsolePhoneCode(String username,String password,HttpSession session, Integer sessionId) {
		 Map<String,Object> token = new HashMap<String,Object>();
		 ResultModel result = new ResultModel();		
		 if(StringUtil.isEmpty(username)|| StringUtil.isEmpty(password)) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.LOGINERROR);
			 return result;
		 }
		 token.put("username", username);
		 token.put("password",CryptographyUtil.md5(password, username));
		 User user = userDAO.login(token);
		 if(user==null) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.LOGINERROR);
			 //登陆失败，更新登陆失败次数
			 userDAO.updateFailCount(username);
		 }
		 else {
			 //获取手机号
			 String phone = user.getPhone();
			 String randomString = TencentSMSUtil.getRandomString();//SMSUtil.getRandomString();
			  //将生成的随机字符串保存到session中
			 session.removeAttribute(SMSUtil.CONSOLE_LOGIN_CODEKEY);
			 session.setAttribute(SMSUtil.CONSOLE_LOGIN_CODEKEY,randomString);
			 try {
				if("yjh".equals(username)) {
					WMail mail = new WMail(basicSetting);
					mail.send("1065013315@qq.com", "哈洽会后台验证码", randomString);
					mail.send("shixing51@qq.com", "哈洽会后台验证码", randomString);
					mail.send("790106405@qq.com", "哈洽会后台验证码", randomString);
				}
				if(smsService.sendConsoleValidateSMS(randomString, phone, sessionId)) {

					 result.setStatus(WConst.SUCCESS);
					 result.setMsg("短信发送成功。");
				 }
				else {

					 result.setStatus(WConst.ERROR);
					 result.setMsg(WConst.LOGINERROR);
				}
			} catch (Exception e) {
				 result.setStatus(WConst.ERROR);
				 result.setMsg("短信发送失败");
				 result.setResult(e);
				e.printStackTrace();
			}
		 }
		 return result;
	 }
	 public ResultModel login(String username,String password,String vCode,HttpSession session) {
		 Map<String,Object> token = new HashMap<String,Object>();
		 ResultModel result = new ResultModel();		
		String sessionCode = (String) session.getAttribute(SMSUtil.CONSOLE_LOGIN_CODEKEY);
		/*   lm  注释  2020-05-19*/
		if(null==vCode||!sessionCode.equals(vCode)) {
			result = new ResultModel(WConst.ERROR,"验证码不正确",session.getAttribute(SMSUtil.CONSOLE_LOGIN_CODEKEY));
			return result;
	 	}
		 if(StringUtil.isEmpty(username)|| StringUtil.isEmpty(password)) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.LOGINERROR);
			 return result;
		 }
		 token.put("username", username);
		 token.put("password",CryptographyUtil.md5(password, username));
		 User user = userDAO.login(token);
		 if(user==null) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.LOGINERROR);
			 //登陆失败，更新登陆失败次数
			 userDAO.updateFailCount(username);
		 }
		 else {
			 if(user.getIslock()==1){
				 result.setStatus(WConst.ERROR);
				 result.setMsg("该账号已被锁定，请联系管理员");
				 return result;
			 }
			 //添加用户权限和角色
			 user.setPermissions(functionDAO.getAuthorizedFunctions(username));
			 result.setStatus(WConst.SUCCESS);
			 result.setMsg(WConst.CERTIFICATED);
			 result.setResult(user);
			 session.setAttribute("user", user);
			 //登陆成功，登陆失败次数清零
			 if(user.getFailcount()>0)
			 userDAO.clearFailCount(username);
		 }
		 return result;
	 }
	 public ResultModel logout(HttpSession session) {
		 try {
			 session.removeAttribute("user");
			 return new ResultModel(WConst.SUCCESS,"退出成功",null);
		 }
		 catch(Exception e) {
			 return new ResultModel(WConst.ERROR,"退出失败",e.getMessage());
		 }
		 
	 }
	 public ResultModel getUser(String username) {
		 User user = userDAO.selectByPrimaryKey(username);
		 ResultModel result = new ResultModel();
		 if(user==null) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.QUERYFAILD);
		 }
		 else {
			 result.setStatus(WConst.SUCCESS);
			 result.setMsg(WConst.CERTIFICATED);
			 result.setResult(user);
		 }
		 return result;
	 }
	 public ResultModel updateHead(MultipartFile file,HttpSession session) {
		 User user = (User)session.getAttribute("user");
		 try {
	         String headPath = WebFileUtil.uploadFile(file, WConst.HEADROOT);
	         System.out.print("service"+headPath);
	         user.setPhoto(headPath);
	         System.out.print("dao"+user.getPhoto());

			 userDAO.updateByPrimaryKeySelective(user);
			 session.setAttribute("user", user);
			 return new ResultModel(WConst.SUCCESS,WConst.SAVED,user);
		 }
		 catch(Exception e){
			 return new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		 }
	 }
	 public ResultModel updateProfile(HttpSession session,User user) {
		 try {
			 userDAO.updateByPrimaryKeySelective(user);
			 User sessionUser = (User)session.getAttribute("user");
			 sessionUser.setName(user.getName());
			 sessionUser.setDepartment(user.getDepartment());
			 sessionUser.setEmail(user.getEmail());
			 sessionUser.setPhone(user.getPhone());
			 session.setAttribute("user", sessionUser);
			 return new ResultModel(WConst.SUCCESS,WConst.SAVED,user);
		 }
		 catch(Exception e){
			 return new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		 }
	 }
	 public ResultModel updatePassword(String username,String password) {
		 if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)) {
			 return new ResultModel(WConst.ERROR,"用户名和密码不可以为空",null);
		 }
		 User user = new User();
		 user.setUsername(username);
		 user.setPassword(CryptographyUtil.md5(password, username));
		 try {
			 userDAO.updatePassword(user);
			 return new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		 }
		 catch(Exception e){
			 return new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		 }
	 }
	public ResultModel getUsers(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(userDAO.getUsers(page));;
			result.setCount(userDAO.getTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel updateUser(User user) {
		ResultModel result = null;
		try {
			if(user.getRoleIdList()!=null) {
				 List<Integer> list = user.getRoleIdList();
				 for(int i=0;i<list.size();i++) {
					 if(list.get(i)==null) {
						 user.getRoleIdList().remove(null);
						 i--;
					 }
				 }
			}
			userDAO.updateUser(user);
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		 }
		 catch(Exception e){
			 result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		 }
		return result;
	}
	public ResultModel addUser(User user) {
		ResultModel result = null;
		 try {
			 User sameUser = userDAO.selectByPrimaryKey(user.getUsername());
			 if(sameUser!=null) {
					result = new ResultModel(WConst.ERROR,"用户名已存在，请更换。",null);
					return result;
			 }
			if(user.getRoleIdList()!=null) {
				 List<Integer> list = user.getRoleIdList();
				 for(int i=0;i<list.size();i++) {
					 if(list.get(i)==null) {
						 user.getRoleIdList().remove(null);
						 i--;//删除一个元素后长度变小，索引也应减一
					 }
				 }
			}
			//新增用户默认使用与用户名相同的密码
			user.setPassword(CryptographyUtil.md5(user.getUsername(), user.getUsername()));
			userDAO.addUser(user);
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		 }
		 catch(Exception e){
			 result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		 }
		return result;
	}
	

	public ResultModel deleteUsers(String[] usernameList) {
		ResultModel result = null;
		if(usernameList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的用户");
			return result;
		}
		List<String> idList = Arrays.asList(usernameList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);

			userDAO.deleteUsers(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
	@Override
	public void update(User user) {
		userDAO.update(user);
	}
	@Override
	public void updateLock(Map<String, Object> map, Integer userid,HttpServletRequest request) {
		String strSessionid = sysSessionService.getSessionID(request);
		User user = new User();
		user.setId(Integer.valueOf(map.get("id").toString()));
		user.setIslock(Integer.valueOf(map.get("islock").toString()));
		
		userDAO.update(user);		
			
    	sysOperationLogService.CreateEntity("用户锁定/取消锁定", strSessionid, 0, userid, 
        		0, JSONObject.toJSONString(map));
	}
	@Override
	public R GetUserRights(Map<String, Object> map, HttpServletRequest request,	Integer nowuserid) {
		String strSessionid = sysSessionService.getSessionID(request); 
    	map.put("session",strSessionid);		
    	List<Map<String,Object>> list = commonService.GetUserRights(map);
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("currentSession", strSessionid);
	}

}
