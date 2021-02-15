package cn.org.chtf.card.manage.accessrecords.dao;

import cn.org.chtf.card.manage.accessrecords.pojo.AccessRecords;
import cn.org.chtf.card.manage.accessrecords.pojo.AccessRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccessRecordsMapper {
    long countByExample(AccessRecordsExample example);

    int deleteByExample(AccessRecordsExample example);

    int deleteByPrimaryKey(String id);

    int insert(AccessRecords record);

    int insertSelective(AccessRecords record);

    List<AccessRecords> selectByExample(AccessRecordsExample example);

    AccessRecords selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AccessRecords record, @Param("example") AccessRecordsExample example);

    int updateByExample(@Param("record") AccessRecords record, @Param("example") AccessRecordsExample example);

    int updateByPrimaryKeySelective(AccessRecords record);

    int updateByPrimaryKey(AccessRecords record);

	void updatePiaoStatus(AccessRecords accessRecords);
}