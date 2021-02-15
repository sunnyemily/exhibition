package cn.org.chtf.card.manage.function.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.function.dao.FunctionMapper;
import cn.org.chtf.card.manage.function.pojo.Function;
import cn.org.chtf.card.manage.function.service.FunctionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("FunctionServiceImpl")

public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionMapper functionDAO;
	public ResultModel selectAllForTree(int functionId){
		List<Function> Functions = functionDAO.selectAll();
		ResultModel result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,Functions);
		return result;
	}
	public ResultModel deleteFunction(int functionId){
		ResultModel result;
		try {
			int childNodeCount = (functionDAO.selectChilds(functionId)).size();
			if(childNodeCount>0) {
				result = new ResultModel(WConst.ERROR,WConst.HAVECHILDERROR,null);

			}
			else
			{
				int deleteCount = functionDAO.deleteByPrimaryKey(functionId);
				result = new ResultModel(WConst.SUCCESS,WConst.DELETED,deleteCount);
			}
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.DELETEDERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel updateFunction(Function function) {
		ResultModel result;
		try {
			if(function.getFunctionId()==0) {
				functionDAO.insertSelective(function);
			}
			else {
				functionDAO.updateByPrimaryKeySelective(function);
			}
		
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e);
		}
		return result;
	}
	public ResultModel getAuthorizedFunctions(HttpSession session){
		ResultModel result;
		try {
			if(session.getAttribute("user")!=null) {
				String username = ((User)session.getAttribute("user")).getUsername();
				List<Function> functionList = functionDAO.getAuthorizedFunctions(username);
				result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,functionList);
			}
			else {
				result = new ResultModel(WConst.LOGINOVERTIME,WConst.LOGINOVERTIMEMSG,null);
			}
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e);
		}
		return result;
	}


}
