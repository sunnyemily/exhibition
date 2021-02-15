package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.manage.system.model.SysOperationLog;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.dao.SysOperationLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * @author lm
 */
@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {

    @Autowired
    private SysOperationLogMapper sysOperationLogDao;

    /**
     * 查询列表
     */
    @Override
    public List<SysOperationLog> list(Map<String, Object> map) {
        return sysOperationLogDao.list(map);
    }
    
    /**
     * 数量
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return sysOperationLogDao.listcount(map);
    }


    /**
     * 通过id查询单个
     */
    @Override
    public SysOperationLog findById(Integer id) {
        return sysOperationLogDao.findById(id);
    }

    /**
     * 通过map查询单个
     */
    @Override
    public List<SysOperationLog> findByMap(Map<String, Object> map) {
        return sysOperationLogDao.findByMap(map);
    }

    /**
     * 新增
     */
    @Override
    public int save(SysOperationLog sysOperationLog) {
        return sysOperationLogDao.save(sysOperationLog);
    }

    /**
     * 修改
     */
    @Override
    public int update(SysOperationLog sysOperationLog) {
        return sysOperationLogDao.update(sysOperationLog);
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id) {
        return sysOperationLogDao.deleteById(id);
    }

    /**
     * 创建操作日志实体保存 
     * 【 act 操作类型	】
     * 【session  当前届次	】
     * 【isback 0后台 1前台	】
     * 【operator 操作人id	】
     * 【primarykey 操作数据的主键	】
     * 【remark   备注】
     */
	@Override
	public void CreateEntity(String act, String session, int isback,
			int operator, int primarykey, String remark) {
		SysOperationLog sol = new SysOperationLog();
		sol.setAct(act);
		sol.setSession(session);
		sol.setIsback(isback);
		sol.setOperator(operator);
		sol.setPrimarykey(primarykey);
		sol.setRemark(remark);		
		sysOperationLogDao.save(sol);
	}

}
