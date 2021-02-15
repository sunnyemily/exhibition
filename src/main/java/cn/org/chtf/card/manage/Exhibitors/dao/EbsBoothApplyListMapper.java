package cn.org.chtf.card.manage.Exhibitors.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApplyList;

import java.util.List;
import java.util.Map;


/**
 * 展位申请详细表DAO
 * @author ggwudivs
 */
public interface EbsBoothApplyListMapper {

    /**
     * 通过apply_id查询单个展位申请详细表
     */
	List<EbsBoothApplyList> findById(Integer applyId);

    /**
     * 通过map查询单个展位申请详细表
     */
    List<EbsBoothApplyList> findByMap(Map<String, Object> map);

    /**
     * 查询展位申请详细表列表
     */
    List<EbsBoothApplyList> list(Map<String, Object> map);

    /**
     * 新增展位申请详细表
     */
    int save(EbsBoothApplyList ebsBoothApplyList);

    /**
     * 修改展位申请详细表
     */
    int update(EbsBoothApplyList ebsBoothApplyList);

    /**
     * 删除展位申请详细表
     */
    int deleteById(Integer apply_id);

	/**
     * 通过map查询单个展位申请详细表
     */
    int listcount(Map<String, Object> map);

}
