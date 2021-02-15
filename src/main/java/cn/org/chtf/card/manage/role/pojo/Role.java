package cn.org.chtf.card.manage.role.pojo;

import java.util.List;

public class Role {
	/**
	 * 主键
	 */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 以逗号分隔的功能清单，主要用于返回给客户端
     */
    private String functions;
    /**
     * 数组形式的功能清单，主要用于更新和新增
     */
    private List<Integer> functionList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getFunctions() {
        return this.functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions == null ? null : functions.trim();
    }

	public List<Integer> getFunctionList() {
		return this.functionList;
	}

	public void setFunctionList(List<Integer> functionList) {
		this.functionList = functionList;
	}
}