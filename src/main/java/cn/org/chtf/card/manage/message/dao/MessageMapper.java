package cn.org.chtf.card.manage.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.message.pojo.Message;
import cn.org.chtf.card.support.util.PageModel;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);
    List<Message> getMessages(PageModel page);
    
    int getTotal(PageModel page);
    
    int deleteMessages(@Param("messageIdList") List<Integer> messageIdList);
}