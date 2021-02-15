package cn.org.chtf.card.manage.dictionaries.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.dictionaries.pojo.Dictionaries;
import cn.org.chtf.card.support.util.PageModel;

public interface DictionariesDAO {
    int insert(Dictionaries record);
    int delete(Integer dicId);
    int deletes(@Param("dicIdList") List<Integer> dicIdList);
    int update(Dictionaries record);
    Dictionaries select(Integer dicId);    
    List<Dictionaries> getUnits(PageModel page);    
    int getUnitsTotal(PageModel page);   
    List<Dictionaries> getProjectType(PageModel page);    
    int getProjectTypeTotal(PageModel page);  
    List<Dictionaries> getResearchType(PageModel page);    
    int getResearchTypeTotal(PageModel page);
}