package cn.org.chtf.card.manage.Exhibitors.service;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroom;

import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 参展商管理-展厅管理Service
 * @author lm
 */
public interface EbsShowroomService {

	/**
	 * 查询参展商管理-展厅管理页面
	 * @return 分页参展商管理-展厅管理数据
	 */
	PageInfo<EbsShowroom> page(RequestParamsUtil requestParamsUtil);

	/**
	 * 查询参展商管理-展厅管理列表
	 */
	List<EbsShowroom> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-展厅管理
	 */
    EbsShowroom findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-展厅管理
	 */
	EbsShowroom findByMap(Map<String, Object> map);

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
	
	int listcount(Map<String, Object> map);

	void UseShowRoom(Map<String, Object> map);
	/**
	 * @apiNote 获取交易团所分配的展厅
	 * @param memberId
	 * @param sessionId
	 * @return
	 */
	List<EbsShowroom> groupRooms(@Param("memberId") Integer memberId,@Param("sessionId") Integer sessionId);
}
