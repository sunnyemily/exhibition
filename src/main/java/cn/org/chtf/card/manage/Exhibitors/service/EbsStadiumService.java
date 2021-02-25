package cn.org.chtf.card.manage.Exhibitors.service;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsStadium;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 搭建商管理-报馆申请Service
 *
 * @author lm
 */
public interface EbsStadiumService {

    /**
     * 查询搭建商管理-报馆申请页面
     *
     * @return 分页搭建商管理-报馆申请数据
     */
    PageInfo<EbsStadium> page(RequestParamsUtil requestParamsUtil);

    /**
     * 查询搭建商管理-报馆申请列表
     */
    List<EbsStadium> list(Map<String, Object> map);

    /**
     * 通过id查询单个搭建商管理-报馆申请
     */
    EbsStadium findById(Integer id);

    /**
     * 通过map查询单个搭建商管理-报馆申请
     */
    List<EbsStadium> findByMap(Map<String, Object> map);

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

    int listcount(Map<String, Object> map);

    /**
     * 更新报馆申请信息
     *
     * @param ebsStadium
     * @return
     * @author wushixing
     */
    ResultModel addOrUpdate(EbsStadium ebsStadium);

    /**
     * @param page
     * @return
     * @author wushixing
     */
    public ResultModel getStadiums(EbsStadium ebsStadium, PageModel page);

    /**
     * @param idList
     * @param memberId
     * @return
     * @author wushixing
     */
    public ResultModel delete(Integer[] idList, Integer memberId);

    void deleteByMap(Map<String, Object> par);

    Map<String, Object> loadCount(Map<String, Object> map);
}
