package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.org.chtf.card.common.utils.RequestParamsUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsVehiclecardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsVehiclecardService;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-车辆证件审核ServiceImpl
 * @author lm
 */
@Service
public class EbsVehiclecardServiceImpl implements EbsVehiclecardService {

    @Autowired
    private EbsVehiclecardMapper ebsVehiclecardDao;
    
    @Autowired
    private CmCertificateTypeMapper cardTypeService;

    /**
     * 查询参展商管理-车辆证件审核页面
     * @return 分页参展商管理-车辆证件审核数据
     */
    @Override
    public PageInfo<EbsVehiclecard> page(RequestParamsUtil requestParamsUtil) {
        PageHelper.startPage(requestParamsUtil.getPageNo(), requestParamsUtil.getPageSize());
        return new PageInfo<>(ebsVehiclecardDao.list(requestParamsUtil.getParameters()));
    }

    /**
     * 查询参展商管理-车辆证件审核列表
     */
    @Override
    public List<EbsVehiclecard> list(Map<String, Object> map) {
        return ebsVehiclecardDao.list(map);
    }
    
    /**
     * 数量参展商管理-车辆证件审核
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsVehiclecardDao.listcount(map);
    }


    /**
     * 通过id查询单个参展商管理-车辆证件审核
     */
    @Override
    public EbsVehiclecard findById(Integer id) {
        return ebsVehiclecardDao.findById(id);
    }

    /**
     * 通过map查询单个参展商管理-车辆证件审核
     */
    @Override
    public List<EbsVehiclecard> findByMap(Map<String, Object> map) {
        return ebsVehiclecardDao.findByMap(map);
    }

    /**
     * 新增参展商管理-车辆证件审核
     */
    @Override
    public int save(EbsVehiclecard ebsVehiclecard) {
        return ebsVehiclecardDao.save(ebsVehiclecard);
    }

    /**
     * 修改参展商管理-车辆证件审核
     */
    @Override
    public int update(EbsVehiclecard ebsVehiclecard) {
        return ebsVehiclecardDao.update(ebsVehiclecard);
    }

    /**
     * 删除参展商管理-车辆证件审核
     */
    @Override
    public int deleteById(Integer id) {
        return ebsVehiclecardDao.deleteById(id);
    }

	@Override
	public ResultModel addOrUpdate(EbsVehiclecard vehiclecard) {
		ResultModel result = null;
		try {
			int c = 0;
			if(vehiclecard.getId()==null||vehiclecard.getId().equals(0)) {
				c = save(vehiclecard);
			}
			else {
				c = update(vehiclecard);
			}
			if(c>0) {
				result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null); 
			}
			else {
				result = new ResultModel(WConst.ERROR,"保存失败","没有影响到任何行"); 
			}
		}
		catch(Exception e){
			result = new ResultModel(WConst.ERROR,"保存失败",e); 
		}
		return result;
	}

	@Override
	public ResultModel getVehicleCards(EbsVehiclecard vehiclecard, PageModel page) {
		ResultModel result = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", page.getKeywords());
		map.put("phone", page.getPhone());
		map.put("platenumber", page.getPlatenumber());
		map.put("companyname", vehiclecard.getCompanyname());
		map.put("index",page.getStart());
		map.put("size", page.getLimit());
		map.put("agent", vehiclecard.getAgent());
		map.put("cardtype",vehiclecard.getCardtype());
		map.put("session", vehiclecard.getSession());
		switch(vehiclecard.getStatus()) {
			case 1:
				map.put("isforensics", "1");
				break;
			case 2:
				map.put("printstatus", "2");
				break;
			case 3:
				map.put("status", "1");
				break;
			case 4:
				map.put("status", "0");
				break;
			case 5:
				map.put("status", "-1");
				break;
		}
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(ebsVehiclecardDao.list(map));;
			result.setCount(ebsVehiclecardDao.listcount(map));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}

	@Override
	public ResultModel delete(Integer[] ids, Integer memberId) {
		ResultModel result = null;
		if(ids.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.DELETEDERROR,"没有传入要删除的信息");
			return result;
		}
		List<Integer> idList = Arrays.asList(ids);
		try {
			for(int id : ids){
				ebsVehiclecardDao.deleteByIdAndMemberId(id,memberId);
			}
			int row = ids.length;//ebsVehiclecardDao.delete(idList,memberId);
			result = new ResultModel(WConst.SUCCESS,WConst.DELETED,row);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.DELETEDERROR,e.getMessage());
		}
		
		return result;
	}

	@Override
	public ResultModel takeAwayReport(Integer delay,Integer memberId, Integer sessionId,Integer awayStatus, Integer cardType) {

		ResultModel result = null;
		try {
			//1.处理取证报表延迟
		    Calendar c = Calendar.getInstance();
		    c.add(Calendar.MINUTE, -delay);
	        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
			Date now = c.getTime();
			
			String makecardtime = dateFormat.format(now);
			//2.处理打印编码
			dateFormat=new SimpleDateFormat("yyyyMMdd");
			String printNumber = memberId + "-"+dateFormat.format(now)+String.format("%04d", cardType);
			if(awayStatus==0) {
				ebsVehiclecardDao.updatePrintNumber(printNumber, memberId, makecardtime, sessionId, awayStatus, cardType);
			}
			List<Map<String, Object>> list = ebsVehiclecardDao.takeAwayReport(memberId, makecardtime, sessionId, awayStatus, cardType);
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,list);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
		}
		
		return result;
	}

	@Override
	public int updatePrintNumberByMap(Map<String, Object> map) {
		return ebsVehiclecardDao.updatePrintNumberByMap(map);
	}

	@Override
	public void deleteByMap(Map<String, Object> map) {
		ebsVehiclecardDao.deleteByMap(map);
	}
	/**
	 * 获取历届车辆证件
	 * @param memberId
	 * @param sessionId
	 * @param cardname
	 * @param page
	 * @return
	 */
	@Override
	public ResultModel getHistoryCarCard(Integer memberId, Integer sessionId,String cardname, PageModel page) {
		ResultModel result = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start",page.getStart());
		map.put("limit", page.getLimit());
		map.put("memberId", memberId);
		map.put("cardname",cardname);
		map.put("sessionId",sessionId);
		map.put("platenumber", page.getKeywords());
		map.put("companyname", page.getCompanyname());
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(ebsVehiclecardDao.getHistoryCarCard(map));;
			result.setCount(ebsVehiclecardDao.getHistoryCarCardCount(map));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultModel rejoin(Integer[] idList, int exhibitionSessionId, int memberId) {
		ResultModel result = null;
		Integer id = 0;
		try {
			for(int i = 0;i<idList.length;i++) {
				id = idList[i];
				EbsVehiclecard carCard = ebsVehiclecardDao.findById(id);
				//获取当前届次的证件类型
				CmCertificateType cardType = cardTypeService.findById(carCard.getCardtype());
				Map<String,Object> cardTypeFilter = new HashMap<String,Object>();
				cardTypeFilter.put("session", exhibitionSessionId);
				cardTypeFilter.put("chinesename", cardType.getChinesename());
				CmCertificateType nowCardType = cardTypeService.findByMap(cardTypeFilter);
				
				Map<String,Object> filter = new HashMap<String,Object>();
				filter.put("session", exhibitionSessionId);
				filter.put("platenumber", carCard.getPlatenumber());
				List<EbsVehiclecard> cards = ebsVehiclecardDao.findByMap(filter);
				if(cards.size()>0) {
					throw new Exception(carCard.getPlatenumber()+"的证件在本届已存在。");
				}
				else {
					carCard.setSession(String.valueOf(exhibitionSessionId));
					carCard.setStatus(0);
					carCard.setAgent(memberId);
					carCard.setRemark("");
					carCard.setPrintstatus(0);
					carCard.setPrinttype(-1);
					carCard.setCardnature(0);
					carCard.setIsback(1);
					carCard.setIsforensics(0);
					carCard.setAddtime(new java.sql.Timestamp(new Date().getTime()));
					carCard.setCardtype(nowCardType.getId());
					carCard.setCardtypename(nowCardType.getChinesename());
					ebsVehiclecardDao.save(carCard);
				}
				
			}
			result = new ResultModel(WConst.SUCCESS,"激活成功",null);
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
		}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> VehicleIDMarkList(Map<String, Object> map) {
		return ebsVehiclecardDao.VehicleIDMarkList(map);
	}

	@Override
	public int VehicleIDMarkListCount(Map<String, Object> map) {
		return ebsVehiclecardDao.VehicleIDMarkListCount(map);
	}
	
	@Override
	public Map<String, Object> loadCount(Map<String, Object> map) {
		return ebsVehiclecardDao.loadCount(map);
	}

}
