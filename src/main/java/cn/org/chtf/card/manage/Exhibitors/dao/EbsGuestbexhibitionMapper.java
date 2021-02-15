package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.Exhibitors.model.EbsGuestbexhibition;


/**
 * 参展商管理-嘉宾B-布撤展企业管理DAO
 * @author lm
 */
public interface EbsGuestbexhibitionMapper {

    /**
     * 通过id查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    EbsGuestbexhibition findById(Integer id);

    /**
     * 通过map查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    EbsGuestbexhibition findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-嘉宾B-布撤展企业管理列表
     */
    List<EbsGuestbexhibition> list(Map<String, Object> map);

    /**
     * 新增参展商管理-嘉宾B-布撤展企业管理
     */
    int save(EbsGuestbexhibition ebsGuestbexhibition);

    /**
     * 修改参展商管理-嘉宾B-布撤展企业管理
     */
    int update(EbsGuestbexhibition ebsGuestbexhibition);

    /**
     * 删除参展商管理-嘉宾B-布撤展企业管理
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    int listcount(Map<String, Object> map);

	void ResetPassword(EbsGuestbexhibition ebsGuestbexhibition);
}
