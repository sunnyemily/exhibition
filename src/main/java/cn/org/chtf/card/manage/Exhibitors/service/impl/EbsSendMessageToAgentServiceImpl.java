package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsSendMessageToAgentMapper;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageToAgentService;

/**
 * Map<String, Object>ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsSendMessageToAgentServiceImpl implements EbsSendMessageToAgentService {

    @Autowired
    private EbsSendMessageToAgentMapper ebsSendMessageToAgentDao;

    /**
     * 查询零散参展商列表
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return ebsSendMessageToAgentDao.list(map);
    }
    
    /**
     * 查询零散参展商数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsSendMessageToAgentDao.listcount(map);
    }

}
