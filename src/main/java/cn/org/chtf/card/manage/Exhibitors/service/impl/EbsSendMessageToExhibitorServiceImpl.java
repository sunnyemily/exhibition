package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsSendMessageToExhibitorMapper;
import cn.org.chtf.card.manage.Exhibitors.service.EbsSendMessageToExhibitorService;

/**
 * Map<String, Object>ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsSendMessageToExhibitorServiceImpl implements EbsSendMessageToExhibitorService {

    @Autowired
    private EbsSendMessageToExhibitorMapper ebsSendMessageToExhibitorDao;

    /**
     * 查询零散参展商列表
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return ebsSendMessageToExhibitorDao.list(map);
    }
    
    /**
     * 查询零散参展商数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsSendMessageToExhibitorDao.listcount(map);
    }

}
