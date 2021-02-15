package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.manage.online.model.OnlineActivityDetails;
import cn.org.chtf.card.manage.online.service.OnlineActivityDetailsService;
import cn.org.chtf.card.manage.online.dao.OnlineActivityDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * @author lm
 */
@Service
public class OnlineActivityDetailsServiceImpl implements OnlineActivityDetailsService {

    @Autowired
    private OnlineActivityDetailsMapper onlineActivityDetailsDao;

    /**
     * 查询列表
     */
    @Override
    public List<OnlineActivityDetails> list(Map<String, Object> map) {
        return onlineActivityDetailsDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineActivityDetailsDao.listcount(map);
    }


    /**
     * 通过id查询单个
     */
    @Override
    public OnlineActivityDetails findById(Integer id) {
        return onlineActivityDetailsDao.findById(id);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<OnlineActivityDetails> findByMap(Map<String, Object> map) {
        return onlineActivityDetailsDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(OnlineActivityDetails onlineActivityDetails) {
        return onlineActivityDetailsDao.save(onlineActivityDetails);
    }

    /**
     * 修改
     */
    @Override
    public int update(OnlineActivityDetails onlineActivityDetails) {
        return onlineActivityDetailsDao.update(onlineActivityDetails);
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id) {
        return onlineActivityDetailsDao.deleteById(id);
    }

}
