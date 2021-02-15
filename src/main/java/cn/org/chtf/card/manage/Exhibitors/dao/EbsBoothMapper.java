package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;


/**
 * 参展商管理-展位DAO
 * @author ggwudivs
 */
public interface EbsBoothMapper {

    /**
     * 通过id查询单个参展商管理-展位
     */
    EbsBooth findById(Integer id);

    /**
     * 通过map查询单个参展商管理-展位
     */
    EbsBooth findByMap(Map<String, Object> map);

    /**
     * 查询参展商管理-展位列表
     */
    List<EbsBooth> list(Map<String, Object> map);

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

	/**
     * 通过map查询单个参展商管理-展位
     */
    int listcount(Map<String, Object> map);

	List<Map<String, Object>> GetBoothByMap(Map<String, Object> map);

	List<String> queryAllBooth(Map<String, Object> map);

	List<String> queryAllShowRoom(Map<String, Object> map);

	List<String> queryAllCompany(Map<String, Object> map);

	int releaseById(Integer id);

	List<EbsBooth> getBoothList(String id);
	/**
	 * 分配展位
	 * @param booths
	 * @return
	 */
	int assignBooths(@Param("booths") Integer[] booths,@Param("companyId")Integer companyId);
	/**
	 * 取消分配展位
	 * @param booths
	 * @return
	 */
	int unAssignBooths(@Param("booths") Integer[] booths);

	/**释放企业展位
	 * @param map
	 * @return
	 */
	int releaseCompanyBooth(Map<String, Object> map);
	/**
	 * 获取零散参展商所办参展证的类型
	 * @param sessionId
	 * @param companyId
	 * @return
	 */
	List<EbsShowroomtype> getCertificationPermission(@Param("sessionId") Integer sessionId,@Param("companyId")Integer companyId);

	List<EbsBooth> GetBoothListByTradingGrounIdAndSession(
			Map<String, Object> map);
}
