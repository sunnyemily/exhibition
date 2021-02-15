package cn.org.chtf.card.manage.Exhibitors.service;

import java.util.List;
import java.util.Map;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;

/**
 * 参展商管理-展位Service
 * @author ggwudivs
 */
public interface EbsBoothService {

	/**
	 * 查询参展商管理-展位列表
	 */
	List<EbsBooth> list(Map<String, Object> map);

	/**
	 * 通过id查询单个参展商管理-展位
	 */
    EbsBooth findById(Integer id);

	/**
	 * 通过map查询单个参展商管理-展位
	 */
	EbsBooth findByMap(Map<String, Object> map);

	/**
	 * 新增参展商管理-展位
	 */
	int save(EbsBooth ebsBooth);

	/**
	 * 修改参展商管理-展位
	 */
	int update(EbsBooth ebsBooth);

	/**
	 * 删除参展商管理-展位
	 */
	int deleteById(Integer id);
	
	int listcount(Map<String, Object> map);

	List<Map<String, Object>> GetBoothByMap(Map<String, Object> map);

	List<String> queryAllBooth(Map<String, Object> map);

	List<String> queryAllShowRoom(Map<String, Object> map);

	List<String> queryAllCompany(Map<String, Object> map);

	int releaseById(Integer id);

	List<EbsBooth> getBoothList(String id);

	int releaseCompanyBooth(int companyId, int sessionId);

	List<EbsBooth> GetBoothListByTradingGrounIdAndSession(
			Map<String, Object> map);
}
