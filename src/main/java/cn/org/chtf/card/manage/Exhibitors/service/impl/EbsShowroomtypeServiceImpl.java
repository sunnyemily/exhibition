package cn.org.chtf.card.manage.Exhibitors.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;
import cn.org.chtf.card.manage.Exhibitors.service.EbsShowroomtypeService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsShowroomtypeMapper;
import cn.org.chtf.card.common.utils.RequestParamsUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 参展商管理-展厅类型ServiceImpl
 * @author lm
 */
@Service
public class EbsShowroomtypeServiceImpl implements EbsShowroomtypeService {

    @Autowired
    private EbsShowroomtypeMapper ebsShowroomtypeDao;

    /**
     * 查询参展商管理-展厅类型页面
     * @return 分页参展商管理-展厅类型数据
     */
    @Override
    public PageInfo<EbsShowroomtype> page(RequestParamsUtil requestParamsUtil) {
        PageHelper.startPage(requestParamsUtil.getPageNo(), requestParamsUtil.getPageSize());
        return new PageInfo<>(ebsShowroomtypeDao.list(requestParamsUtil.getParameters()));
    }

    /**
     * 查询参展商管理-展厅类型列表
     */
    @Override
    public List<EbsShowroomtype> list(Map<String, Object> map) {
        return ebsShowroomtypeDao.list(map);
    }
    
    /**
     * 数量参展商管理-展厅类型
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsShowroomtypeDao.listcount(map);
    }


    /**
     * 通过id查询单个参展商管理-展厅类型
     */
    @Override
    public EbsShowroomtype findById(Integer id) {
        return ebsShowroomtypeDao.findById(id);
    }

    /**
     * 通过map查询单个参展商管理-展厅类型
     */
    @Override
    public EbsShowroomtype findByMap(Map<String, Object> map) {
        return ebsShowroomtypeDao.findByMap(map);
    }

    /**
     * 新增参展商管理-展厅类型
     */
    @Override
    public int save(EbsShowroomtype ebsShowroomtype) {
        return ebsShowroomtypeDao.save(ebsShowroomtype);
    }

    /**
     * 修改参展商管理-展厅类型
     */
    @Override
    public int update(EbsShowroomtype ebsShowroomtype) {
        return ebsShowroomtypeDao.update(ebsShowroomtype);
    }

    /**
     * 删除参展商管理-展厅类型
     */
    @Override
    public int deleteById(Integer id) {
        return ebsShowroomtypeDao.deleteById(id);
    }

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map) {		
		return ebsShowroomtypeDao.getList(map);
	}

}
