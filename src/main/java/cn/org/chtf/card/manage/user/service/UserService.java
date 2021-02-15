package cn.org.chtf.card.manage.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface UserService {
	public ResultModel sendConsolePhoneCode(String username,String password,HttpSession session, Integer sessionId);
	/**
	 * 验证登陆
	 * @param 用户名
	 * @param 密码
	 * @return 返回实体
	 */
	 public ResultModel login(String username,String password,String vCode,HttpSession session);
	 /**
	  * 根据用户名查询用户
	  * @param 用户名
	  * @return 返回实体
	  */
	 public ResultModel getUser(String username);
	 /**
	  * 更新头像
	  * @param file 头像文件
	  * @param user 
	  * @param session
	  * @return
	  */
	 public ResultModel updateHead(MultipartFile file,HttpSession session);
	 /**
	  * 更新用户资料
	  * @param session
	  * @param user
	  * @return
	  */
	 public ResultModel updateProfile(HttpSession session,User user);
	 /**
	  * 修改密码
	  * @param username 用户名
	  * @param password 密码
	  * @return
	  */
	 public ResultModel updatePassword(String username,String password);
	 /**
	  * 退出登陆
	  * @param session
	  * @return
	  */
	 public ResultModel logout(HttpSession session);
	 /**
	  * 获取用户分页列表
	  * @param page
	  * @return
	  */
	 public ResultModel getUsers(PageModel page);
	 /**
	  * 更新用户信息及角色
	  * @param user
	  * @return
	  */
	 public ResultModel updateUser(User user);
	 /**
	  * 新增用户
	  * @param user
	  * @return
	  */
	 public ResultModel addUser(User user);
	 /**
	  * 批量删除用户
	  * @param usernameList
	  * @return
	  */
	 public ResultModel deleteUsers(String[] usernameList);
	public void update(User user);
	public void updateLock(Map<String, Object> map, Integer id,HttpServletRequest request);
	public R GetUserRights(Map<String, Object> map, HttpServletRequest request,
			Integer id);
}
