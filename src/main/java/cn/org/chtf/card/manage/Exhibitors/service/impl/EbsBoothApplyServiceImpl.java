package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothApplyListMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothApplyMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsBoothMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApply;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApplyList;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApplyParam;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.product.dao.WebProductMapper;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

/**
 * 展位申请表ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsBoothApplyServiceImpl implements EbsBoothApplyService {

    @Autowired
    private EbsCompanyinfoMapper ebsCompanyinfoDao;

    @Autowired
    private WebProductMapper productDAO;
    @Autowired
    private EbsBoothApplyMapper ebsBoothApplyDao;
    @Autowired
    private EbsBoothApplyListMapper ebsBoothApplyListDao;
    @Autowired
    private EbsCompanyinfoMapper companyDAO;
    @Autowired
    private EbsBoothMapper boothDAO;
    @Autowired
    private PimAgentMapper agentDAO;
    
    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;

    /**
     * 查询展位申请表列表
     */
    @Override
    public List<EbsBoothApply> list(Map<String, Object> map) {
        return ebsBoothApplyDao.list(map);
    }
    
    /**
     * 数量展位申请表
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsBoothApplyDao.listcount(map);
    }


    /**
     * 通过apply_id查询单个展位申请表
     */
    @Override
    public EbsBoothApply findById(Integer applyId) {
        return ebsBoothApplyDao.findById(applyId);
    }

    /**
     * 通过map查询单个展位申请表
     */
    @Override
    public List<EbsBoothApply> findByMap(Map<String, Object> map) {
        return ebsBoothApplyDao.findByMap(map);
    }

    /**
     * 新增展位申请表
     */
    @Override
    public int save(EbsBoothApply ebsBoothApply) {
        return ebsBoothApplyDao.save(ebsBoothApply);
    }

    /**
     * 修改展位申请表
     */
    @Override
    public int update(EbsBoothApply ebsBoothApply) {
        return ebsBoothApplyDao.update(ebsBoothApply);
    }

    /**
     * 删除展位申请表
     */
    @Override
    public int deleteById(Integer applyId) {
        return ebsBoothApplyDao.deleteById(applyId);
    }


    /**
     * 更新展位申请信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel updateApply(String apply) {
		try {
			EbsBoothApplyParam boothApply = JSON.parseObject(apply, EbsBoothApplyParam.class);
			Boolean isNew = true;
			if(boothApply.getApplyId() == null || boothApply.getApplyId().equals(0))
			{
				EbsCompanyinfo company = ebsCompanyinfoDao.getLastCompanyByMemberId(boothApply.getMemberId());
				boothApply.setCompanyId(company.getId());
				ebsBoothApplyDao.save(boothApply);
			}
			else {
				isNew = false;
				ebsBoothApplyDao.update(boothApply);
			}
			//更新需要将旧的全部删除
			if(!isNew)
			{
				ebsBoothApplyListDao.deleteById(boothApply.getApplyId());
			}
			for (EbsBoothApplyList applyList : boothApply.getList()){
				applyList.setApplyId(boothApply.getApplyId());
				ebsBoothApplyListDao.save(applyList);
			}
	        return new ResultModel(WConst.SUCCESS,"保存成功",null);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
	        return new ResultModel(WConst.ERROR,"保存失败",e);
		} 
    }

	@Override
	public ResultModel updateEnterpriseApply(String apply, EbsCompanyinfo company) {
		try {
			EbsBoothApplyParam boothApply = JSON.parseObject(apply, EbsBoothApplyParam.class);
			PimAgent agent = agentDAO.getAgentByMemberIdAndSessionId(boothApply.getMemberId(), boothApply.getSessionId());
			company.setTradinggroupid(agent.getTradinggroupid());
			Integer productCount = 0;
			if(company.getId().equals(0)||company.getId()==null) {

				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put("session", boothApply.getSessionId());
				filter.put("chinesename", company.getChinesename());
				List<EbsCompanyinfo> cards = ebsCompanyinfoDao
						.findByMap(filter);
				if(cards.size()==0) {
					ebsCompanyinfoService.save(company);
				}
				else {
			        return new ResultModel(WConst.ERROR,company.getChinesename() + "的企业在本届已存在。",null);

				}
			}
			else {
				Map<String,Object> filter = new HashMap<String,Object>();
				filter.put("companyId", company.getId());
				filter.put("session", boothApply.getSessionId());
				productCount = productDAO.listcount(filter);
				ebsCompanyinfoService.update(company);
			}
			if(boothApply.getCheckedBooths().length>0) {
				boothDAO.assignBooths(boothApply.getCheckedBooths(), company.getId());
			}
			Boolean isNew = true;
			if(boothApply.getApplyId() == null || boothApply.getApplyId().equals(0))
			{
				boothApply.setCompanyId(company.getId());
				ebsBoothApplyDao.save(boothApply);
			}
			else {
				isNew = false;
				if(boothApply.getUnCheckedBooths().length>0) {
					boothDAO.unAssignBooths(boothApply.getUnCheckedBooths());
				}
				ebsBoothApplyDao.update(boothApply);
			}
			//更新需要将旧的全部删除
			if(!isNew)
			{
				ebsBoothApplyListDao.deleteById(boothApply.getApplyId());
			}
			for (EbsBoothApplyList applyList : boothApply.getList()){
				applyList.setApplyId(boothApply.getApplyId());
				ebsBoothApplyListDao.save(applyList);
			}
			
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("companyId", company.getId());
			res.put("productCount",0);
			
	        return new ResultModel(WConst.SUCCESS,"保存成功",res);
		} catch (Exception e) {
			e.printStackTrace();
	        return new ResultModel(WConst.ERROR,"保存失败",e);
		} 
	}
    /**
	 * 获取指定会员指定届次的展位申请信息
	 * @param memberId
	 * @param sessionId
	 * @return
	 */
    @Override
	public ResultModel getApply(Integer memberId,Integer sessionId) {
    	try {
        	Map<String,Object> filter = new HashMap<String,Object>();
        	filter.put("memberId", memberId);
        	filter.put("sessionId",sessionId);
        	List<EbsBoothApply> applies = ebsBoothApplyDao.findByMap(filter);
        	if(applies.size()==0) {
                return new ResultModel(WConst.SUCCESS,"获取成功",null);
        	}
        	EbsBoothApply apply = applies.get(0);
        	EbsBoothApplyParam applied = new EbsBoothApplyParam();
        	BeanUtils.copyProperties(applied,apply);
        	applied.setList(ebsBoothApplyListDao.findById(applied.getApplyId()));
            return new ResultModel(WConst.SUCCESS,"获取成功",applied);
    	} catch (Exception e) {
			e.printStackTrace();
	        return new ResultModel(WConst.ERROR,"获取失败",e);
		} 
	}

	@Override
	public Map<String, Object> findByCompanyId(int companyId, Integer sessionId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("sessionId", sessionId);
		return ebsBoothApplyDao.findByCompanyId(map);
	}
}
