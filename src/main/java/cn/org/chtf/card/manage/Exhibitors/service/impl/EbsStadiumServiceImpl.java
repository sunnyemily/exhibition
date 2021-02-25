package cn.org.chtf.card.manage.Exhibitors.service.impl;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsStadiumMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsStadium;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsStadiumService;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 搭建商管理-报馆管理ServiceImpl
 *
 * @author lm
 */
@Service
public class EbsStadiumServiceImpl implements EbsStadiumService {

    @Autowired
    private EbsStadiumMapper ebsStadiumDao;

    /**
     * 查询搭建商管理-报馆申请页面
     *
     * @return 分页搭建商管理-报馆申请数据
     */
    @Override
    public PageInfo<EbsStadium> page(RequestParamsUtil requestParamsUtil) {
        PageHelper.startPage(requestParamsUtil.getPageNo(), requestParamsUtil.getPageSize());
        return new PageInfo<>(ebsStadiumDao.list(requestParamsUtil.getParameters()));
    }

    /**
     * 查询搭建商管理-报馆申请列表
     */
    @Override
    public List<EbsStadium> list(Map<String, Object> map) {
        return ebsStadiumDao.list(map);
    }

    /**
     * 数量搭建商管理-报馆申请
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsStadiumDao.listcount(map);
    }


    /**
     * 通过id查询单个搭建商管理-报馆申请
     */
    @Override
    public EbsStadium findById(Integer id) {
        return ebsStadiumDao.findById(id);
    }

    /**
     * 通过map查询单个搭建商管理-报馆申请
     */
    @Override
    public List<EbsStadium> findByMap(Map<String, Object> map) {
        return ebsStadiumDao.findByMap(map);
    }

    /**
     * 新增搭建商管理-报馆申请
     */
    @Override
    public int save(EbsStadium ebsStadium) {
        return ebsStadiumDao.save(ebsStadium);
    }

    /**
     * 修改搭建商管理-报馆申请
     */
    @Override
    public int update(EbsStadium ebsStadium) {
        return ebsStadiumDao.update(ebsStadium);
    }

    /**
     * 删除搭建商管理-报馆申请
     */
    @Override
    public int deleteById(Integer id) {
        return ebsStadiumDao.deleteById(id);
    }

    @Override
    public ResultModel addOrUpdate(EbsStadium ebsStadium) {
        ResultModel result = null;
        try {
            int c = 0;
            if (ebsStadium.getId() == null || ebsStadium.getId().equals(0)) {
                c = save(ebsStadium);
            } else {
                c = update(ebsStadium);
            }
            if (c > 0) {
                result = new ResultModel(WConst.SUCCESS, WConst.SAVED, null);
            } else {
                result = new ResultModel(WConst.ERROR, "保存失败", "没有影响到任何行");
            }
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, "保存失败", e);
        }
        return result;
    }

    @Override
    public ResultModel getStadiums(EbsStadium ebsStadium, PageModel page) {
        ResultModel result = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", page.getKeywords());
        map.put("phone", page.getPhone());
        map.put("companyname", ebsStadium.getCompanyname());
        map.put("relationcompanyname", ebsStadium.getRelationcompanyname());
        map.put("index", page.getStart());
        map.put("size", page.getLimit());
        map.put("agent", ebsStadium.getAgent());
        map.put("session", ebsStadium.getSession());
        map.put("paystatus", ebsStadium.getPaystatus());
        switch (ebsStadium.getStatus()) {
            case 1:
                map.put("isforensics", "1");
                break;
            case 2:
                map.put("printstatus", "2");
                break;
            case 3:
                map.put("status", "1");
                break;
            case 4:
                map.put("status", "0");
                break;
            case 5:
                map.put("status", "-1");
                break;
        }

        try {
            result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, null);
            result.setResult(ebsStadiumDao.list(map));
            ;
            result.setCount(ebsStadiumDao.listcount(map));
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD, e.getMessage());
        }

        return result;
    }

    @Override
    public ResultModel delete(Integer[] ids, Integer memberId) {
        ResultModel result = null;
        if (ids.length == 0) {//验证传入数组的长度
            result = new ResultModel(WConst.ERROR, WConst.DELETEDERROR, "没有传入要删除的信息");
            return result;
        }
        List<Integer> idList = Arrays.asList(ids);
        try {
            for (int id : ids) {
                ebsStadiumDao.deleteByIdAndMemberId(id, memberId);
            }
            int row = ids.length;//ebsVehiclecardDao.delete(idList,memberId);
            result = new ResultModel(WConst.SUCCESS, WConst.DELETED, row);
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, WConst.DELETEDERROR, e.getMessage());
        }

        return result;
    }

    @Override
    public void deleteByMap(Map<String, Object> map) {
        ebsStadiumDao.deleteByMap(map);
    }

    @Override
    public Map<String, Object> loadCount(Map<String, Object> map) {
        return ebsStadiumDao.loadCount(map);
    }

}
