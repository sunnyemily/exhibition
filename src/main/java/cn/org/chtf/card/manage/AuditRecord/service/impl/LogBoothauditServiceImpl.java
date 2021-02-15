package cn.org.chtf.card.manage.AuditRecord.service.impl;

import cn.org.chtf.card.manage.AuditRecord.model.LogBoothaudit;
import cn.org.chtf.card.manage.AuditRecord.service.LogBoothauditService;
import cn.org.chtf.card.manage.AuditRecord.dao.LogBoothauditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 展位审核记录ServiceImpl
 * @author ggwudivs
 */
@Service
public class LogBoothauditServiceImpl implements LogBoothauditService {

    @Autowired
    private LogBoothauditMapper logBoothauditDao;

    /**
     * 查询展位审核记录列表
     */
    @Override
    public List<LogBoothaudit> list(Map<String, Object> map) {
        return logBoothauditDao.list(map);
    }
    
    /**
     * 数量展位审核记录
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return logBoothauditDao.listcount(map);
    }


    /**
     * 通过id查询单个展位审核记录
     */
    @Override
    public LogBoothaudit findById(Integer id) {
        return logBoothauditDao.findById(id);
    }

    /**
     * 通过map查询单个展位审核记录
     */
    @Override
    public List<LogBoothaudit> findByMap(Map<String, Object> map) {
        return logBoothauditDao.findByMap(map);
    }

    /**
     * 新增展位审核记录
     */
    @Override
    public int save(LogBoothaudit logBoothaudit) {
        return logBoothauditDao.save(logBoothaudit);
    }

    /**
     * 修改展位审核记录
     */
    @Override
    public int update(LogBoothaudit logBoothaudit) {
        return logBoothauditDao.update(logBoothaudit);
    }

    /**
     * 删除展位审核记录
     */
    @Override
    public int deleteById(Integer id) {
        return logBoothauditDao.deleteById(id);
    }

}
