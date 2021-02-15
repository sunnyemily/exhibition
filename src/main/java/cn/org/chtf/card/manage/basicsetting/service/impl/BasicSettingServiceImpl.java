package cn.org.chtf.card.manage.basicsetting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.basicsetting.dao.BasicSettingDAO;
import cn.org.chtf.card.manage.basicsetting.pojo.BasicSetting;
import cn.org.chtf.card.manage.basicsetting.service.BasicSettingService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("BasicSettingServiceImpl")
public class BasicSettingServiceImpl implements BasicSettingService {
	@Autowired
	private BasicSettingDAO basicSettingDAO;

	public ResultModel getBasicSetting(int bsid) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(basicSettingDAO.select(bsid));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e);
		}
		
		return result;
	}
	public ResultModel updateBasicSetting(BasicSetting basic) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			int opertionCount = basicSettingDAO.update(basic);
			if(opertionCount==0) {
				opertionCount = basicSettingDAO.insert(basic);
			}
			result.setResult(opertionCount);

		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e);
		}
		
		return result;
		
	}
}
