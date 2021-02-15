package cn.org.chtf.card.manage.role.service;

import java.util.List;

import cn.org.chtf.card.manage.role.pojo.Role;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface RoleService {
	/**
	 * 新增和更新角色
	 * @param role
	 * @return
	 */
	public ResultModel updateRole(Role role);
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	public ResultModel deleteRole(Integer[] roleIdList);
	/**
	 * 获取角色列表
	 * @param page
	 * @return
	 */
	public ResultModel getRoles(PageModel page);
}
