package cn.org.chtf.card.manage.dictionaries.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.dictionaries.pojo.Dictionaries;
import cn.org.chtf.card.manage.dictionaries.service.DictionariesService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class DictionariesController {
	@Resource(name = "DictionariesServiceImpl")
	DictionariesService dictionariesService;
	/**
	 * 获取机构列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/manage/system/getUnits")
	public ResultModel getUnits(PageModel page) {		
		return dictionariesService.getUnits(page);
	}
	/**
	 * 获取机构列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/manage/system/getProjectType")
	public ResultModel getProjectType(PageModel page) {		
		return dictionariesService.getProjectType(page);
	}
	/**
	 * 获取研究领域列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/manage/system/getResearchType")
	public ResultModel getResearchType(PageModel page) {		
		return dictionariesService.getResearchType(page);
	}
	/**
	 * 更新结构
	 * @param page
	 * @return
	 */
	@RequestMapping(value= {"/manage/system/updateUnit","/manage/system/updateProjectType","/manage/system/updateResearchType"})
	public ResultModel updateDictionaries(Dictionaries dictionaries) {
		return dictionariesService.updateDictionaries(dictionaries);
	}
	/**
	 * 删除结构
	 * @param page
	 * @return
	 */
	@RequestMapping(value= {"/manage/system/deleteUnits","/manage/system/deleteProjectType","/manage/system/deleteResearchtType"})
	public ResultModel deleteDictionaries(@RequestParam(value = "dicidList[]") Integer[] dicidList) {		
		return dictionariesService.deleteDictionaries(dicidList);
	}
}
