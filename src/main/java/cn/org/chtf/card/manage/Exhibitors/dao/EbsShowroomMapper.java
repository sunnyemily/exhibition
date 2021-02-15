package cn.org.chtf.card.manage.Exhibitors.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 参展商管理-展厅管理DAO
 * @author lm
 */
public interface EbsShowroomMapper {

    /**
     * 通过id查询单个参展商管理-展厅管理
     */
    EbsShowroom findById(Integer id);

    /**
     * 通过map查询单个参展商管理-展厅管理
     */
    EbsShowroom findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-展厅管理列表
     */
    List<EbsShowroom> list(Map<String, Object> map);

    /**
     * 新增参展商管理-展厅管理
     */
    int save(EbsShowroom ebsShowroom);

    /**
     * 修改参展商管理-展厅管理
     */
    int update(EbsShowroom ebsShowroom);

    /**
     * 删除参展商管理-展厅管理
     */
    int deleteById(Integer id);

	/**
     * 通过map查询单个参展商管理-展厅管理
     */
    int listcount(Map<String, Object> map);

    /**
     * 交易团所分配的展厅
     */
    List<EbsShowroom> groupRooms(@Param("memberId") Integer memberId,@Param("sessionId") Integer sessionId);

}
