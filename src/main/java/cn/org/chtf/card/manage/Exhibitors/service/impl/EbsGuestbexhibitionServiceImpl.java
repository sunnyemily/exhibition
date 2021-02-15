package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsGuestbexhibitionMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsGuestbexhibition;
import cn.org.chtf.card.manage.Exhibitors.service.EbsGuestbexhibitionService;

/**
 * 参展商管理-嘉宾B-布撤展企业管理ServiceImpl
 * @author lm
 */
@Service
public class EbsGuestbexhibitionServiceImpl implements EbsGuestbexhibitionService {

    @Autowired
    private EbsGuestbexhibitionMapper ebsGuestbexhibitionDao;

    /**
     * 查询参展商管理-嘉宾B-布撤展企业管理页面
     * @return 分页参展商管理-嘉宾B-布撤展企业管理数据
     */
    @Override
    public PageInfo<EbsGuestbexhibition> page(RequestParamsUtil requestParamsUtil) {
        PageHelper.startPage(requestParamsUtil.getPageNo(), requestParamsUtil.getPageSize());
        return new PageInfo<>(ebsGuestbexhibitionDao.list(requestParamsUtil.getParameters()));
    }

    /**
     * 查询参展商管理-嘉宾B-布撤展企业管理列表
     */
    @Override
    public List<EbsGuestbexhibition> list(Map<String, Object> map) {
        return ebsGuestbexhibitionDao.list(map);
    }
    
    /**
     * 数量参展商管理-嘉宾B-布撤展企业管理
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsGuestbexhibitionDao.listcount(map);
    }


    /**
     * 通过id查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    @Override
    public EbsGuestbexhibition findById(Integer id) {
        return ebsGuestbexhibitionDao.findById(id);
    }

    /**
     * 通过map查询单个参展商管理-嘉宾B-布撤展企业管理
     */
    @Override
    public EbsGuestbexhibition findByMap(Map<String, Object> map) {
        return ebsGuestbexhibitionDao.findByMap(map);
    }

    /**
     * 新增参展商管理-嘉宾B-布撤展企业管理
     */
    @Override
    public int save(EbsGuestbexhibition ebsGuestbexhibition) {
        return ebsGuestbexhibitionDao.save(ebsGuestbexhibition);
    }

    /**
     * 修改参展商管理-嘉宾B-布撤展企业管理
     */
    @Override
    public int update(EbsGuestbexhibition ebsGuestbexhibition) {
        return ebsGuestbexhibitionDao.update(ebsGuestbexhibition);
    }

    /**
     * 删除参展商管理-嘉宾B-布撤展企业管理
     */
    @Override
    public int deleteById(Integer id) {
        return ebsGuestbexhibitionDao.deleteById(id);
    }

	@Override
	public void ResetPassword(EbsGuestbexhibition ebsGuestbexhibition) {
		ebsGuestbexhibitionDao.ResetPassword(ebsGuestbexhibition);		
	}
}
