package cn.org.chtf.card.manage.person.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.common.pojo.AlterMenuParameter;
import cn.org.chtf.card.manage.person.pojo.PersonParameter;
import cn.org.chtf.card.manage.person.pojo.PersonWithBLOBs;

public interface PersonDAO {
    int insert(PersonWithBLOBs record);
    int delete(Integer personId);    
    int deletes(@Param("idList") List<Integer> idList);
    int update(PersonWithBLOBs record);
    PersonWithBLOBs select(Integer personId);
    List<PersonWithBLOBs> list(PersonParameter page);
    int total(PersonParameter page);    
    int alterMenu(AlterMenuParameter alterMenu);
    List<PersonWithBLOBs> searchList(@Param("personName") String personName,@Param("language") String language,
    		@Param("personPinyin") String personPinyin,@Param("start") Integer start,@Param("end") Integer end);
    int searchTotal(@Param("personName") String personName,@Param("language") String language,
    		@Param("personPinyin") String personPinyin,@Param("start") Integer start,@Param("end") Integer end);    
    List<String> groupByCountry(Map<String,Object> map);
    List<String> groupForeignCountry(Map<String,Object> map);
    List<String> groupForeignerCardCountry(Map<String,Object> map);
    List<String> groupForeignExhibitorsCountry(Map<String,Object> map);
    Map<String,Object> groupCertificateNumber(Map<String,Object> map);
}