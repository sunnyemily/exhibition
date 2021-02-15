package cn.org.chtf.card.manage.online.service.impl;

import cn.org.chtf.card.manage.online.model.OnlineInquiry;
import cn.org.chtf.card.manage.online.service.OnlineInquiryService;
import cn.org.chtf.card.manage.online.dao.OnlineInquiryMapper;
import cn.org.chtf.card.support.util.PageModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 在线询盘ServiceImpl
 * @author lm
 */
@Service
public class OnlineInquiryServiceImpl implements OnlineInquiryService {

    @Autowired
    private OnlineInquiryMapper onlineInquiryDao;

    /**
     * 查询在线询盘列表
     */
    @Override
    public List<OnlineInquiry> list(Map<String, Object> map) {
        return onlineInquiryDao.list(map);
    }
    
    /**
     * 数量在线询盘
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return onlineInquiryDao.listcount(map);
    }


    /**
     * 通过id查询单个在线询盘
     */
    @Override
    public OnlineInquiry findById(Integer id) {
        return onlineInquiryDao.findById(id);
    }

    /**
     * 通过map查询单个在线询盘
     */
    @Override
    public List<OnlineInquiry> findByMap(Map<String, Object> map) {
        return onlineInquiryDao.findByMap(map);
    }

    /**
     * 新增在线询盘
     */
    @Override
    public int save(OnlineInquiry onlineInquiry) {
        return onlineInquiryDao.save(onlineInquiry);
    }

    /**
     * 修改在线询盘
     */
    @Override
    public int update(OnlineInquiry onlineInquiry) {
        return onlineInquiryDao.update(onlineInquiry);
    }

    /**
     * 删除在线询盘
     */
    @Override
    public int deleteById(Integer id) {
        return onlineInquiryDao.deleteById(id);
    }

	@Override
	public List<OnlineInquiry> GetInquiryList(PageModel page) {
		return onlineInquiryDao.GetInquiryList(page);
	}

	@Override
	public int GetInquiryListCount(PageModel page) {
		return onlineInquiryDao.GetInquiryListCount(page);
	}

}
