package cn.org.chtf.card.manage.user.pojo;

import java.util.Date;
import java.util.List;

import cn.org.chtf.card.manage.function.pojo.Function;

public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2687676929822622763L;
	private Integer id;
	
	//锁定 
	private Integer islock;
	
	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	/**
	 * 是否全部交易团
	 */
	private Integer isall;
	public Integer getIsall() {
		return isall;
	}

	public void setIsall(Integer isall) {
		this.isall = isall;
	}

	/**
	 * 用户名
	 */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 头像
     */
    private String photo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机
     */
    private String phone;
    
    /**
     * 部门
     */
    private String department;
    
    /**
     * 最后登陆日期
     */
    private Date lastlogin;
    
    /**
     * 登陆失败次数
     */
    private Short failcount;
    
    /**
     * 角色名
     */
    private String rolename;
    
    /**
     * 角色列表
     */
    private String roleIds;
    
    /**
     * 授权的url
     */
    private List<Function> permissions;
    /**
     * 角色列表，数组类型
     */
    private List<Integer> roleIdList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhoto() {
    	return photo;
    }
    
    public void setPhoto(String photo) {
    	this.photo = photo == null ? null : photo.trim();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Short getFailcount() {
        return failcount;
    }

    public void setFailcount(Short failcount) {
        this.failcount = failcount;
    }
    
    public String getRolename() {
    	return this.rolename;
    }
    
    public void setRolename(String rolename) {
    	this.rolename = rolename == null ? null : rolename.trim();
    }

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
    	this.roleIds = roleIds == null ? null : roleIds.trim();
	}

	public List<Integer> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public List<Function> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Function> permissions) {
    	this.permissions = permissions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}