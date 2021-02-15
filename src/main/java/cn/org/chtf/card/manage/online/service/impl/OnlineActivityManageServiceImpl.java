package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.manage.online.model.OnlineActivityManage;
import cn.org.chtf.card.manage.online.service.OnlineActivityManageService;
import cn.org.chtf.card.manage.online.dao.OnlineActivityManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * @author lm
 */
@Service
public class OnlineActivityManageServiceImpl implements OnlineActivityManageService {

    @Autowired
    private OnlineActivityManageMapper onlineActivityManageDao;

    /**
     * 查询列表
     */
    @Override
    public List<OnlineActivityManage> list(Map<String, Object> map) {
        return onlineActivityManageDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineActivityManageDao.listcount(map);
    }


    /**
     * 通过id查询单个
     */
    @Override
    public OnlineActivityManage findById(Integer id) {
        return onlineActivityManageDao.findById(id);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<OnlineActivityManage> findByMap(Map<String, Object> map) {
        return onlineActivityManageDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(OnlineActivityManage onlineActivityManage) {
        return onlineActivityManageDao.save(onlineActivityManage);
    }

    /**
     * 修改
     */
    @Override
    public int update(OnlineActivityManage onlineActivityManage) {
        return onlineActivityManageDao.update(onlineActivityManage);
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id) {
        return onlineActivityManageDao.deleteById(id);
    }

}
