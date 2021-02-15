package cn.org.chtf.card.manage.Exhibitors.service.impl;

import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApplyList;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyListService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothApplyListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 展位申请详细表ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsBoothApplyListServiceImpl implements EbsBoothApplyListService {

    @Autowired
    private EbsBoothApplyListMapper ebsBoothApplyListDao;

    /**
     * 查询展位申请详细表列表
     */
    @Override
    public List<EbsBoothApplyList> list(Map<String, Object> map) {
        return ebsBoothApplyListDao.list(map);
    }
    
    /**
     * 数量展位申请详细表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsBoothApplyListDao.listcount(map);
    }


    /**
     * 通过apply_id查询单个展位申请详细表
     */
    @Override
    public List<EbsBoothApplyList> findById(Integer applyId) {
        return ebsBoothApplyListDao.findById(applyId);
    }

    /**
     * 通过map查询单个展位申请详细表
     */
    @Override
    public List<EbsBoothApplyList> findByMap(Map<String, Object> map) {
        return ebsBoothApplyListDao.findByMap(map);
    }

    /**
     * 新增展位申请详细表
     */
    @Override
    public int save(EbsBoothApplyList ebsBoothApplyList) {
        return ebsBoothApplyListDao.save(ebsBoothApplyList);
    }

    /**
     * 修改展位申请详细表
     */
    @Override
    public int update(EbsBoothApplyList ebsBoothApplyList) {
        return ebsBoothApplyListDao.update(ebsBoothApplyList);
    }

    /**
     * 删除展位申请详细表
     */
    @Override
    public int deleteById(Integer applyId) {
        return ebsBoothApplyListDao.deleteById(applyId);
    }

}
