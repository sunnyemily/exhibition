package cn.org.chtf.card.manage.person.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.person.dao.PersonDAO;
import cn.org.chtf.card.manage.person.pojo.PersonParameter;
import cn.org.chtf.card.manage.person.pojo.PersonWithBLOBs;
import cn.org.chtf.card.manage.person.service.PersonService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;
@Service("PersonServiceImpl")
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDAO personDAO;
	public ResultModel updatePerson(PersonWithBLOBs person) {
		ResultModel result = null;
		try {
			if(person.getPersonId()==0) {
				personDAO.insert(person);
			}
			else {
				personDAO.update(person);
			}
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		return result;
	}
	public ResultModel deletePersons(Integer[] personIdList) {
		ResultModel result = null;
		if(personIdList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的人员");
			return result;
		}
		List<Integer> idList = Arrays.asList(personIdList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);
			personDAO.deletes(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getPersons(PersonParameter parameter) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(personDAO.list(parameter));;
			result.setCount(personDAO.total(parameter));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getPerson(Integer personId) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(personDAO.select(personId));;
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	
    /**
     * 更改信息所属栏目
     * @param alterMenu
     * @return
     */
	public ResultModel alterMenu(AlterMenuParameter alterMenu) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(personDAO.alterMenu(alterMenu));;
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel searchPersons(String personName,String language,String personPinyin,Integer start,Integer end) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(personDAO.searchList(personName, language, personPinyin,start, end));
			result.setCount(personDAO.searchTotal(personName, language, personPinyin,start, end));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
}
