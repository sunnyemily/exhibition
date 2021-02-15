package cn.org.chtf.card.manage.AuditRecord.service.impl;

import cn.org.chtf.card.manage.AuditRecord.model.LogDocumentaudit;
import cn.org.chtf.card.manage.AuditRecord.service.LogDocumentauditService;
import cn.org.chtf.card.manage.AuditRecord.dao.LogDocumentauditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 证件审核记录ServiceImpl
 * @author ggwudivs
 */
@Service
public class LogDocumentauditServiceImpl implements LogDocumentauditService {

    @Autowired
    private LogDocumentauditMapper logDocumentauditDao;

    /**
     * 查询证件审核记录列表
     */
    @Override
    public List<LogDocumentaudit> list(Map<String, Object> map) {
        return logDocumentauditDao.list(map);
    }
    
    /**
     * 数量证件审核记录
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return logDocumentauditDao.listcount(map);
    }


    /**
     * 通过id查询单个证件审核记录
     */
    @Override
    public LogDocumentaudit findById(Integer id) {
        return logDocumentauditDao.findById(id);
    }

    /**
     * 通过map查询单个证件审核记录
     */
    @Override
    public List<LogDocumentaudit> findByMap(Map<String, Object> map) {
        return logDocumentauditDao.findByMap(map);
    }

    /**
     * 新增证件审核记录
     */
    @Override
    public int save(LogDocumentaudit logDocumentaudit) {
        return logDocumentauditDao.save(logDocumentaudit);
    }

    /**
     * 修改证件审核记录
     */
    @Override
    public int update(LogDocumentaudit logDocumentaudit) {
        return logDocumentauditDao.update(logDocumentaudit);
    }

    /**
     * 删除证件审核记录
     */
    @Override
    public int deleteById(Integer id) {
        return logDocumentauditDao.deleteById(id);
    }

}
