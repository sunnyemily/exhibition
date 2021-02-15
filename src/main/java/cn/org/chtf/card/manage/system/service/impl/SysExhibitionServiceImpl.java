package cn.org.chtf.card.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.system.model.SysExhibition;
import cn.org.chtf.card.manage.system.service.SysExhibitionService;
import cn.org.chtf.card.manage.system.dao.SysExhibitionMapper;
import cn.org.chtf.card.common.utils.RequestParamsUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 展会信息ServiceImpl
 * @author lm
 */
@Service
public class SysExhibitionServiceImpl implements SysExhibitionService {

    @Autowired
    private SysExhibitionMapper sysExhibitionDao;

    /**
     * 查询展会信息页面
     * @return 分页展会信息数据
     */
    @Override
    public PageInfo<SysExhibition> page(RequestParamsUtil requestParamsUtil) {
        PageHelper.startPage(requestParamsUtil.getPageNo(), requestParamsUtil.getPageSize());
        return new PageInfo<>(sysExhibitionDao.list(requestParamsUtil.getParameters()));
    }

    /**
     * 查询展会信息列表
     */
    @Override
    public List<SysExhibition> list(Map<String, Object> map) {
        return sysExhibitionDao.list(map);
    }
    
    /**
     * 数量展会信息
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysExhibitionDao.listcount(map);
    }


    /**
     * 通过id查询单个展会信息
     */
    @Override
    public SysExhibition findById(Integer id) {
        return sysExhibitionDao.findById(id);
    }

    /**
     * 通过map查询单个展会信息
     */
    @Override
    public SysExhibition findByMap(Map<String, Object> map) {
        return sysExhibitionDao.findByMap(map);
    }

    /**
     * 新增展会信息
     */
    @Override
    public int save(SysExhibition sysExhibition) {
        return sysExhibitionDao.save(sysExhibition);
    }

    /**
     * 修改展会信息
     */
    @Override
    public int update(SysExhibition sysExhibition) {
        return sysExhibitionDao.update(sysExhibition);
    }

    /**
     * 删除展会信息
     */
    @Override
    public int deleteById(Integer id) {
        return sysExhibitionDao.deleteById(id);
    }

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map) {
		return sysExhibitionDao.getList(map);
	}

}
