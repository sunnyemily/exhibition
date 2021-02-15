package cn.org.chtf.card.manage.person.service;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.person.pojo.PersonParameter;
import cn.org.chtf.card.manage.person.pojo.PersonWithBLOBs;
import cn.org.chtf.card.support.util.ResultModel;

public interface PersonService {

	/**
	 * 更新、新增人员
	 * @param person
	 * @return
	 */
	public ResultModel updatePerson(PersonWithBLOBs person);
	/**
	 * 删除人员
	 * @param personIdList
	 * @return
	 */
	public ResultModel deletePersons(Integer[] personIdList);
	/**
	 * 获取人员列表
	 * @param parameter
	 * @return
	 */
	public ResultModel getPersons(PersonParameter parameter);
	/**
	 * 获取人员（单个）
	 * @param personId
	 * @return
	 */
	public ResultModel getPerson(Integer personId);
	/**
	 * 更改信息所属菜单
	 * @param alterMenu
	 * @return
	 */
	public ResultModel alterMenu(AlterMenuParameter alterMenu);
	/**
	 * 搜索人员
	 * @param personName 人员姓名
	 * @param language 网站语言
	 * @param start 开始位置
	 * @param end 结束位置
	 * @return
	 */
	public ResultModel searchPersons(String personName,String language,String personPinyin,Integer start,Integer end);
}
