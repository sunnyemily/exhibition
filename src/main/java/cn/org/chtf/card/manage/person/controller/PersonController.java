package cn.org.chtf.card.manage.person.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.person.pojo.PersonParameter;
import cn.org.chtf.card.manage.person.pojo.PersonWithBLOBs;
import cn.org.chtf.card.manage.person.service.PersonService;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class PersonController {

	@Resource(name="PersonServiceImpl")
	private PersonService personService;
	/**
	 * 更新人员
	 * @param person 人员实体，根据主键是否为0，判断是插入还是更新
	 * @return
	 */
	@RequestMapping(value="/manage/person/updatePerson")
	public ResultModel updatePerson(PersonWithBLOBs person) {
		return personService.updatePerson(person);
	}
	/**
	 * 批量删除
	 * @param personIdList主键列表
	 * @return
	 */
	@RequestMapping(value="/manage/person/deletePersons")
	public ResultModel deletePersons(@RequestParam(value = "personIdList[]") Integer[] personIdList) {
		return personService.deletePersons(personIdList);
	}
	/**
	 * 获取人员列表，分页
	 * @param page 分页及搜索实体
	 * @return
	 */
	@RequestMapping(value="/manage/person/getPersons/{menuId}")
	public ResultModel getPersons(@PathVariable Integer menuId,PersonParameter person) {
		person.setMenuId(menuId);
		return personService.getPersons(person);
	}
	/**
	 * 获取人员实体
	 * @param articleId 文章主键
	 * @return
	 */
	@RequestMapping(value="/manage/person/getPerson")
	public ResultModel getPerson(Integer personId) {
		return personService.getPerson(personId);
	}
	/**
	 * 更改信息所属菜单
	 * @param alterMenu 参数实体
	 * @return
	 */
	@RequestMapping(value="/manage/person/alterMenu")
	public ResultModel alterMenu(Integer menuId,@RequestParam(value="idList[]") List<Integer> idList){
		AlterMenuParameter alterMenu = new AlterMenuParameter();
		alterMenu.setMenuId(menuId);
		alterMenu.setIdList(idList);		
		return personService.alterMenu(alterMenu);
	}
}
