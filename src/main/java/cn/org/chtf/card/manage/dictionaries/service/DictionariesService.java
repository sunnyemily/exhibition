package cn.org.chtf.card.manage.dictionaries.service;

import cn.org.chtf.card.manage.dictionaries.pojo.Dictionaries;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface DictionariesService {
	public ResultModel getUnits(PageModel page);
	public ResultModel getProjectType(PageModel page);
	public ResultModel updateDictionaries(Dictionaries dictionaries);
	public ResultModel deleteDictionaries(Integer[] dicidList);
	public ResultModel getResearchType(PageModel page);
}
