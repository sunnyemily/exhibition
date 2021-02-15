package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.manage.online.model.OnlineNegotiate;
import cn.org.chtf.card.manage.online.service.OnlineNegotiateService;
import cn.org.chtf.card.manage.online.dao.OnlineNegotiateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 预约洽谈ServiceImpl
 * @author lm
 */
@Service
public class OnlineNegotiateServiceImpl implements OnlineNegotiateService {

    @Autowired
    private OnlineNegotiateMapper onlineNegotiateDao;

    /**
     * 查询预约洽谈列表
     */
    @Override
    public List<OnlineNegotiate> list(Map<String, Object> map) {
        return onlineNegotiateDao.list(map);
    }
    
    /**
     * 数量预约洽谈
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineNegotiateDao.listcount(map);
    }


    /**
     * 通过id查询单个预约洽谈
     */
    @Override
    public OnlineNegotiate findById(Integer id) {
        return onlineNegotiateDao.findById(id);
    }

    /**
     * 通过map查询单个预约洽谈
     */
    @Override
    public List<OnlineNegotiate> findByMap(Map<String, Object> map) {
        return onlineNegotiateDao.findByMap(map);
    }

    /**
     * 新增预约洽谈
     */
    @Override
    public int save(OnlineNegotiate onlineNegotiate) {
        return onlineNegotiateDao.save(onlineNegotiate);
    }

    /**
     * 修改预约洽谈
     */
    @Override
    public int update(OnlineNegotiate onlineNegotiate) {
        return onlineNegotiateDao.update(onlineNegotiate);
    }

    /**
     * 删除预约洽谈
     */
    @Override
    public int deleteById(Integer id) {
        return onlineNegotiateDao.deleteById(id);
    }

}
