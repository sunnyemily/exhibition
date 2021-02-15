package cn.org.chtf.card.manage.member.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.pojo.RegistParam;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class MemberController {
	@Resource(name = "MemberServiceImpl")
	MemberService memberService;

	/**
	 * 会员登陆
	 * @param Member
	 * @return
	 */
	@RequestMapping(value="/mlogin")
	public ResultModel login(String username,String password,String validateCode,String sessionId,Integer memberType,HttpServletRequest request,HttpSession session) {	
		return memberService.login(username,password,validateCode,sessionId,memberType,request,session);
	}

	/**
	 * 会员登陆
	 * @param Member
	 * @return
	 */
	@RequestMapping(value="/getLoginMember")
	public ResultModel getLoginMember(HttpSession session) {
		return memberService.getLoginMember(session);
	}

	/**
	 * 更新会员资料，不允许更新登录名、密码
	 * @param Member
	 * @return
	 */
	@RequestMapping(value="/saveProfile")
	public ResultModel saveProfile(HttpSession session,Member member) {
		member.setMemberUsername(null);
		member.setMemberPassword(null);
		return memberService.update(session,member);
	}

	/**
	 * 更新会员密码
	 * @param Member
	 * @return
	 */
	@RequestMapping(value="/modifyPassword")
	public ResultModel modifyPassword(HttpSession session,String oldPassword,String newPassword) {
		return memberService.modifyPassword(session,oldPassword,newPassword);
	}

	/**
	 * 退出登陆
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mlogout")
	public ResultModel mlogout(HttpSession session) {
		return memberService.logout(session);
	}
	/**
	 * 获取会员列表，分页
	 * @param page 分页及搜索实体
	 * @return
	 */
	@RequestMapping(value="/manage/member/getMembers")
	public ResultModel getMembers(PageModel page) {
		return memberService.getMembers(page);
	}
	/**
	 * 后台更新、新增会员
	 * @param member 会员实体
	 * @return
	 */
	@RequestMapping(value="/manage/member/updateMember")
	public ResultModel update(Member member) {
		return memberService.update(member);
	}
	/**
	 * 删除会员
	 * @param member 会员实体
	 * @return
	 */
	@RequestMapping(value="/manage/member/delete")
	public ResultModel delete(@RequestParam(value = "idList[]") Integer[] idList) {
		return memberService.deletes(idList);
	}
	
}
