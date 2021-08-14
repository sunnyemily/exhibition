package cn.org.chtf.card.manage.system.dao;

import cn.org.chtf.card.manage.system.model.SysSmsTemplate;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * DAO
 * @author lm
 */
public interface SysSmsTemplateMapper {

    /**
     * 通过sms_id查询单个
     */
    SysSmsTemplate findById(Integer smsId);

    /**
     * 通过map查询单个
     */
    List<SysSmsTemplate> findByMap(Map<String, Object> map);

    /**
     * 查询列表
     */
    List<SysSmsTemplate> list(Map<String, Object> map);

    /**
     * 新增
     */
    int save(SysSmsTemplate sysSmsTemplate);

    /**
     * 修改
     */
    int update(SysSmsTemplate sysSmsTemplate);

    /**
     * 删除
     */
    int deleteById(Integer sms_id);

	/**
     * 通过map查询单个
     */
    int listcount(Map<String, Object> map);

	void addSmsTemplete(List<SysSmsTemplate> list);
	/**
	 * 获取本届次的展会模板
	 * @param title
	 * @param sessionId
	 * @return
	 */
	SysSmsTemplate findByTitleAndSessionId(@Param("title") String  title,@Param("sessionId") Integer sessionId);


    /**
     * 获取指定标定的短信模板
     * @param title
     * @return
     */
    SysSmsTemplate findByTitle(@Param("title") String  title);
	

}
