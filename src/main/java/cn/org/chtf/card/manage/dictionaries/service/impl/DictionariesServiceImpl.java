package cn.org.chtf.card.manage.dictionaries.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.dictionaries.dao.DictionariesDAO;
import cn.org.chtf.card.manage.dictionaries.pojo.Dictionaries;
import cn.org.chtf.card.manage.dictionaries.service.DictionariesService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("DictionariesServiceImpl")

public class DictionariesServiceImpl implements DictionariesService {


	@Autowired
	private DictionariesDAO dictionariesDAO;
	public ResultModel getUnits(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(dictionariesDAO.getUnits(page));;
			result.setCount(dictionariesDAO.getUnitsTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getProjectType(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(dictionariesDAO.getProjectType(page));;
			result.setCount(dictionariesDAO.getProjectTypeTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getResearchType(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(dictionariesDAO.getResearchType(page));;
			result.setCount(dictionariesDAO.getResearchTypeTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel updateDictionaries(Dictionaries dictionaries) {
		ResultModel result = null;
		try {
			if(dictionaries.getDicId()==0) {
				dictionariesDAO.insert(dictionaries);
			}
			else {
				dictionariesDAO.update(dictionaries);
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		return result;
	}
	

	public ResultModel deleteDictionaries(Integer[] dicidList) {
		ResultModel result = null;
		if(dicidList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的字典");
			return result;
		}
		List<Integer> idList = Arrays.asList(dicidList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);

			dictionariesDAO.deletes(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
}
