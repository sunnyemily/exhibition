package cn.org.chtf.card.manage.Exhibitors.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsStadium;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 搭建商管理-报馆申请DAO
 *
 * @author lm
 */
public interface EbsStadiumMapper {

    /**
     * 通过id查询单个搭建商管理-报馆申请
     */
    EbsStadium findById(Integer id);

    /**
     * 通过map查询单个搭建商管理-报馆申请
     */
    List<EbsStadium> findByMap(Map<String, Object> map);

    /**
     * 查询搭建商管理-报馆申请列表
     */
    List<EbsStadium> list(Map<String, Object> map);

    /**
     * 新增搭建商管理-报馆申请
     */
    int save(EbsStadium ebsStadium);

    /**
     * 修改搭建商管理-报馆申请
     */
    int update(EbsStadium ebsStadium);

    /**
     * 删除搭建商管理-报馆申请
     */
    int deleteById(Integer id);

    /**
     * 通过map查询单个搭建商管理-报馆申请
     */
    int listcount(Map<String, Object> map);

    /**
     * 批量删除指定代办员添加的指定车辆证件
     *
     * @param idList
     * @param memberId
     * @return
     */
    int delete(List<Integer> idList, Integer memberId);

    void deleteByIdAndMemberId(@Param("id") int id, @Param("memberId") Integer memberId);

    void deleteByMap(Map<String, Object> map);

    Map<String, Object> loadCount(Map<String, Object> map);
}
