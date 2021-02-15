package cn.org.chtf.card.manage.role.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.role.dao.RoleMapper;
import cn.org.chtf.card.manage.role.pojo.Role;
import cn.org.chtf.card.manage.role.service.RoleService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

@Service("RoleServiceImpl")

public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleDAO;
	
	public ResultModel updateRole(Role role) {
		ResultModel result = null;
		try {
			if(!StringUtil.isEmpty(role.getFunctions())) {
				List<Integer> functionList = new ArrayList<Integer>();

				for(String functionId : role.getFunctions().split(",")) {
					functionList.add(Integer.parseInt(functionId));
				}
				role.setFunctionList(functionList);
			}
			if(role.getRoleId()==0) {
				roleDAO.insert(role);
			}
			else {
				roleDAO.updateByPrimaryKey(role);
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel deleteRole(Integer[] roleIdList) {
		ResultModel result = null;
		if(roleIdList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的角色");
			return result;
		}
		List<Integer> idList = Arrays.asList(roleIdList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);

			roleDAO.deleteByPrimaryKey(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getRoles(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(roleDAO.getRoles(page));;
			result.setCount(roleDAO.getTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	
}
