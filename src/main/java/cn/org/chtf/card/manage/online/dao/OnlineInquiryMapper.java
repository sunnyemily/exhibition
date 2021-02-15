package cn.org.chtf.card.manage.online.dao;

import cn.org.chtf.card.manage.online.model.OnlineInquiry;
import cn.org.chtf.card.support.util.PageModel;

import java.util.List;
import java.util.Map;


/**
 * 在线询盘DAO
 * @author lm
 */
public interface OnlineInquiryMapper {

    /**
     * 通过id查询单个在线询盘
     */
    OnlineInquiry findById(Integer id);

    /**
     * 通过map查询单个在线询盘
     */
    List<OnlineInquiry> findByMap(Map<String, Object> map);

    /**
     * 查询在线询盘列表
     */
    List<OnlineInquiry> list(Map<String, Object> map);

    /**
     * 新增在线询盘
     */
    int save(OnlineInquiry onlineInquiry);

    /**
     * 修改在线询盘
     */
    int update(OnlineInquiry onlineInquiry);

    /**
     * 删除在线询盘
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个在线询盘
     */
    int listcount(Map<String, Object> map);

	List<OnlineInquiry> GetInquiryList(PageModel page);

	int GetInquiryListCount(PageModel page);

}
