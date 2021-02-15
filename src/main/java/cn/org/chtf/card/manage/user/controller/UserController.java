package cn.org.chtf.card.manage.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.manage.user.service.UserService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@RestController
public class UserController {
	@Resource(name = "UserServiceImpl")
	
	UserService userService;
	
	@Autowired
	SysSessionService sessionService;
	@Autowired
    private HttpServletRequest request;
	@RequestMapping(value="/login")
	public ResultModel Login(String username,String password,String vCode ,HttpSession session) {
		ResultModel result = userService.login(username, password,vCode,session);
		return result;
	}
	@RequestMapping(value="/sendConsolePhoneCode")
	public ResultModel sendConsolePhoneCode(String username,String password,HttpSession session) {

		Map<String,Object> exhibitionInfo = sessionService.getExhibitionInfo(request);
		Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId").toString());
		ResultModel result = userService.sendConsolePhoneCode(username, password,session,sessionId);
		return result;
	}
	@RequestMapping(value="/manage/logout")
	public ResultModel Logout(HttpSession session) {
		ResultModel result = userService.logout(session);
		return result;
	}
	/**
	 * 先把函数写在这里，后面应该在登陆的时候，将用户信息缓存到本地，减少服务器通讯
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/manage/getUser")
	public ResultModel getUser(HttpSession session) {
		ResultModel result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,(User)session.getAttribute("user"));
		return result;
	}
	/**
	 * 更新个人资料
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/manage/updateProfile")
	public ResultModel updateProfile(HttpSession session,User user) {
		ResultModel result = userService.updateProfile(session, user);
		return result;
	}
	/**
	 * 修改密码
	 * @param session
	 * @return
	 */
	@RequestMapping(value= {"/manage/updatePassword","/manage/system/updatePassword"})
	public ResultModel updatePassword(String username,String password) {
		ResultModel result = userService.updatePassword(username, password);
		return result;
	}
	/**
	 * 上传头像
	 * @param file 头像文件
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
    public ResultModel uploadImg(@RequestParam("file") MultipartFile file,HttpSession session) {
		ResultModel result = userService.updateHead(file,session);
        return result;
    }
	/**
	 * 获取用户列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/manage/system/getUsers")
	public ResultModel getUsers(PageModel page) {		
		return userService.getUsers(page);
	}
	
	/**
	  * 更新用户信息及角色
	  * @param user
	  * @return
	  */
	@RequestMapping(value="/manage/system/updateUser")
	 public ResultModel updateUser(User user) {
		 return userService.updateUser(user);
	 }
	 /**
	  * 新增用户
	  * @param user
	  * @return
	  */
	@RequestMapping(value="/manage/system/addUser")
	 public ResultModel addUser(User user) {
		return userService.addUser(user); 
	 }
	/**
	 * 批量删除用户
	 * @param usrnameList
	 * @return
	 */
	@RequestMapping(value="/manage/system/deleteUsers")
	public ResultModel deleteUsers(@RequestParam(value = "usernameList[]") String[] usernameList) {
		return userService.deleteUsers(usernameList);
	}
	/*
	@RequestMapping("/ChangeLock")    
	public R ChangeLock(@RequestBody Map<String,Object> map,HttpServletRequest request,HttpSession session){		
		User userx = (User)session.getAttribute("user");	
		userService.updateLock(map,userx.getId(),request);
    
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    }*/
	
	@RequestMapping(value= {"/manage/system/ChangeLock"})
	public R ChangeLock(String id,String islock, HttpServletRequest request,HttpSession session) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("islock", islock);
		User userx = (User)session.getAttribute("user");	
		userService.updateLock(map,userx.getId(),request);
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}
	
	@RequestMapping(value= {"/manage/system/GetTradingGroupForUser"})
	public R GetTradingGroupForUser(String userid, HttpServletRequest request,HttpSession session) {
		User userx = (User)session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userid",userid);		
    	R r = userService.GetUserRights(map,request,userx.getId());
		return r;
	}
	
	
}
