package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApply;


/**
 * 展位申请表DAO
 * @author ggwudivs
 */
public interface EbsBoothApplyMapper {

    /**
     * 通过apply_id查询单个展位申请表
     */
    EbsBoothApply findById(Integer applyId);

    /**
     * 通过map查询单个展位申请表
     */
    List<EbsBoothApply> findByMap(Map<String, Object> map);

    /**
     * 查询展位申请表列表
     */
    List<EbsBoothApply> list(Map<String, Object> map);

    /**
     * 新增展位申请表
     */
    int save(EbsBoothApply ebsBoothApply);

    /**
     * 修改展位申请表
     */
    int update(EbsBoothApply ebsBoothApply);

    /**
     * 删除展位申请表
     */
    int deleteById(Integer apply_id);

	/**
     * 通过map查询单个展位申请表
     */
    int listcount(Map<String, Object> map);

    /**通过CompanyId和届次id，查询apply_id
     * @param memberId
     * @return
     */
    Map<String, Object> findByCompanyId(Map<String, Object> map);
}
