package cn.org.chtf.card.manage.function.service;

import javax.servlet.http.HttpSession;

import cn.org.chtf.card.manage.function.pojo.Function;
import cn.org.chtf.card.support.util.ResultModel;

public interface FunctionService {
	/**
	 * 为tree加载所有功能节点
	 * @param functionId
	 * @return
	 */
	public ResultModel selectAllForTree(int functionId);

	/**
	 * 根据主键删除对应记录
	 * @param functionId
	 * @return
	 */
	public ResultModel deleteFunction(int functionId);
	/**
	 * 根据是否为0，添加或更新记录
	 * @param function
	 * @return
	 */
	public ResultModel updateFunction(Function function);

	 /**
	  * 获取经授权的功能列表
	  * @param session
	  * @return
	  */
	 public ResultModel getAuthorizedFunctions(HttpSession session);
}
