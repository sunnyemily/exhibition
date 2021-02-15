package cn.org.chtf.card.manage.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.basic.dao.BasicMapper;
import cn.org.chtf.card.manage.basic.pojo.Basic;
import cn.org.chtf.card.manage.basic.service.BasicService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("BasicServiceImpl")
public class BasicServiceImpl implements BasicService{
	@Autowired
	private BasicMapper basicDAO;
	/**
	 * 更新基本信息
	 */
	public ResultModel updateBasic(Basic basic) {
		ResultModel result;
		try {
			if(basic.isEditType()) {
				basicDAO.addBasic(basic);
			}
			else {
				basicDAO.updateBasicByMenuId(basic);
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e);
		}
		return result;
	}
	/**
	 * 获取基本信息
	 * @param menuId
	 * @return
	 */
	public ResultModel getBasic(int menuId) {
		ResultModel result;
		try {
			Basic basic = basicDAO.getBasicByMenuId(menuId);
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,basic);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e);
		}
		return result;
	}
}
