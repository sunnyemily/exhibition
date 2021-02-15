package cn.org.chtf.card.manage.Exhibitors.service.impl;

import cn.org.chtf.card.manage.Exhibitors.model.EbsSendMessageLog;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageLogService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsSendMessageLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 发送短信日志ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsSendMessageLogServiceImpl implements EbsSendMessageLogService {

    @Autowired
    private EbsSendMessageLogMapper ebsSendMessageLogDao;

    /**
     * 查询发送短信日志列表
     */
    @Override
    public List<EbsSendMessageLog> list(Map<String, Object> map) {
        return ebsSendMessageLogDao.list(map);
    }
    
    /**
     * 数量发送短信日志
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsSendMessageLogDao.listcount(map);
    }


    /**
     * 通过id查询单个发送短信日志
     */
    @Override
    public EbsSendMessageLog findById(Integer id) {
        return ebsSendMessageLogDao.findById(id);
    }

    /**
     * 通过map查询单个发送短信日志
     */
    @Override
    public EbsSendMessageLog findByMap(Map<String, Object> map) {
        return ebsSendMessageLogDao.findByMap(map);
    }

    /**
     * 新增发送短信日志
     */
    @Override
    public int save(EbsSendMessageLog ebsSendMessageLog) {
        return ebsSendMessageLogDao.save(ebsSendMessageLog);
    }

    /**
     * 修改发送短信日志
     */
    @Override
    public int update(EbsSendMessageLog ebsSendMessageLog) {
        return ebsSendMessageLogDao.update(ebsSendMessageLog);
    }

    /**
     * 删除发送短信日志
     */
    @Override
    public int deleteById(Integer id) {
        return ebsSendMessageLogDao.deleteById(id);
    }

}
