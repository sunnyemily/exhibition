package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsTradinggroupboothallocationMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBooth;
import cn.org.chtf.card.manage.Exhibitors.model.EbsShowroomtype;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroupboothallocation;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupboothallocationService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-交易团展位分配ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsTradinggroupboothallocationServiceImpl implements EbsTradinggroupboothallocationService {

    @Autowired
    private EbsTradinggroupboothallocationMapper ebsTradinggroupboothallocationDao;

    /**
     * 查询参展商管理-交易团展位分配列表
     */
    @Override
    public List<EbsTradinggroupboothallocation> list(Map<String, Object> map) {
        return ebsTradinggroupboothallocationDao.list(map);
    }
    
    /**
     * 数量参展商管理-交易团展位分配
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsTradinggroupboothallocationDao.listcount(map);
    }


    /**
     * 通过id查询单个参展商管理-交易团展位分配
     */
    @Override
    public EbsTradinggroupboothallocation findById(Integer id) {
        return ebsTradinggroupboothallocationDao.findById(id);
    }

    /**
     * 通过map查询单个参展商管理-交易团展位分配
     */
    @Override
    public List<EbsTradinggroupboothallocation> findByMap(Map<String, Object> map) {
        return ebsTradinggroupboothallocationDao.findByMap(map);
    }

    /**
     * 新增参展商管理-交易团展位分配
     */
    @Override
    public int save(EbsTradinggroupboothallocation ebsTradinggroupboothallocation) {
        return ebsTradinggroupboothallocationDao.save(ebsTradinggroupboothallocation);
    }

    /**
     * 修改参展商管理-交易团展位分配
     */
    @Override
    public int update(EbsTradinggroupboothallocation ebsTradinggroupboothallocation) {
        return ebsTradinggroupboothallocationDao.update(ebsTradinggroupboothallocation);
    }

    /**
     * 删除参展商管理-交易团展位分配
     */
    @Override
    public int deleteById(Integer id) {
        return ebsTradinggroupboothallocationDao.deleteById(id);
    }

    /**
     * 返回本届次零散参展商申请展位所需信息
     * @author wushixing
     * @param sessionid
     * @return
     */
	public ResultModel selectAreaAndShowroomType(Integer sessionid){
		ResultModel model;
		try {
			List<Map<String,Object>> list = ebsTradinggroupboothallocationDao.selectAreaAndShowroomType(sessionid);
			List<Map<String,Object>> areas = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map : list) {

				EbsShowroomtype applyType = new EbsShowroomtype();
				applyType.setAddtime((Date) map.get("addTime"));
				applyType.setArea((BigDecimal) map.get("area"));
				applyType.setBoothlimit((BigDecimal) map.get("boothlimit"));
				applyType.setExhibitorsnumber((Integer) map.get("exhibitornumber"));
				applyType.setExplanation((String) map.get("explanation"));
				applyType.setId((Integer) map.get("id"));
				applyType.setName((String) map.get("name"));
				applyType.setPrice1((BigDecimal) map.get("price1"));
				applyType.setPrice2((BigDecimal) map.get("price2"));
				applyType.setPrice3((BigDecimal) map.get("price3"));
				applyType.setPriceunit((String) map.get("priceunit"));
				applyType.setRemark((String) map.get("remark"));
				applyType.setSession((String) map.get("session"));
				applyType.setTollmehod((String) map.get("tollmehod"));
				
				Map<String,Object> newMap;
				if(areas.size()>0) {
					newMap = areas.get(areas.size()-1);
					if(!newMap.get("tradingGroupId").equals(map.get("tradinggroupid"))) {
						newMap = new HashMap<String,Object>();
						newMap.put("tradingGroupId", map.get("tradinggroupid"));
						newMap.put("tradingGroupName",map.get("tradinggroupname"));
						List<EbsShowroomtype> applyTypes = new ArrayList<EbsShowroomtype>();
						applyTypes.add(applyType);
						newMap.put("applyTypes",applyTypes);
						areas.add(newMap);
					}
					else {
						List<EbsShowroomtype> applyTypes = (List<EbsShowroomtype>) newMap.get("applyTypes");
						applyTypes.add(applyType);
					}
				}
				else {
					newMap = new HashMap<String,Object>();
					newMap.put("tradingGroupId", map.get("tradinggroupid"));
					newMap.put("tradingGroupName",map.get("tradinggroupname"));
					List<EbsShowroomtype> applyTypes = new ArrayList<EbsShowroomtype>();
					applyTypes.add(applyType);
					newMap.put("applyTypes",applyTypes);
					areas.add(newMap);
				}
			}
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("areas", areas);
			result.put("inputInfo", "");
			model = new ResultModel(WConst.SUCCESS,"获取成功",result);
		}
		catch(Exception e) {
			model = new ResultModel(WConst.ERROR,"信息获取错误",e);
		}
		return model;
	}

    /**
     * 返回本届次指定交易团、指定企业可申请的展区、展厅和展位信息
     * @author wushixing
     * @param sessionid
     * @return
     */
	public ResultModel selectAreaAndShowroomTypeAndBooth(Integer memberId,Integer companyId,Integer sessionid){
		ResultModel model;
		try {
			List<Map<String,Object>> list = ebsTradinggroupboothallocationDao.selectAreaAndShowroomTypeAndBooth(memberId,companyId,sessionid);
			List<Map<String,Object>> areas = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> applyTypes = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map : list) {
				//增加新的展位并赋值
				Map<String, Object> newBoothMap = new HashMap<String,Object>();
				newBoothMap.put("boothId", map.get("boothId"));
				newBoothMap.put("boothName", map.get("boothName"));
				
				Map<String,Object> newRoomMap;
				//2.已有展厅
				if(applyTypes.size()>0) {
					//2.1取最后一个展厅
					newRoomMap = applyTypes.get(applyTypes.size()-1);
					//2.2当前与上一个展厅是一个展厅
					if(newRoomMap.get("id").equals(map.get("id"))) {
						//2.3获取最后展厅的展位列表，并讲新展位加入
						List<Map<String,Object>> booths = (List<Map<String,Object>>) newRoomMap.get("booths");
						booths.add(newBoothMap);
						continue;
					}
				}
				//1.1增加新的展厅类型并赋值
				newRoomMap = new HashMap<String,Object>();
				newRoomMap.put("addTime", map.get("addTime"));
				newRoomMap.put("area", map.get("area"));
				newRoomMap.put("boothlimit", map.get("boothlimit"));
				newRoomMap.put("exhibitornumber", map.get("exhibitornumber"));
				newRoomMap.put("explanation", map.get("explanation"));
				newRoomMap.put("id", map.get("id"));
				newRoomMap.put("name", map.get("name"));
				newRoomMap.put("price1", map.get("price1"));
				newRoomMap.put("price2", map.get("price2"));
				newRoomMap.put("price3", map.get("price3"));
				newRoomMap.put("priceunit", map.get("priceunit"));
				newRoomMap.put("remark", map.get("remark"));
				newRoomMap.put("session", map.get("session"));
				newRoomMap.put("tollmehod", map.get("tollmehod"));
				newRoomMap.put("tollmehod", map.get("tollmehod"));
				//1.3声明展位列表
				List<Map<String,Object>> booths = new ArrayList<Map<String,Object>>();
				//1.4将新展位加入展位列表
				booths.add(newBoothMap);
				//1.5将展位列表与展厅进行关联
				newRoomMap.put("booths", booths);
				//1.5将展厅进行加入展厅列表				
				applyTypes.add(newRoomMap);
			}
			if(list.size()==0) {
				model = new ResultModel(WConst.ERROR,"尚未分配展位，请稍后再试。",null);
				return model;
			}
			Map<String,Object> area = new HashMap<String,Object>();
			Map<String,Object> firstArea = list.get(0);
			area.put("tradingGroupId", firstArea.get("tradinggroupid"));
			area.put("tradingGroupName", firstArea.get("tradinggroupname"));
			area.put("applyTypes", applyTypes);
			areas.add(area);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("areas", areas);
			result.put("inputInfo", "");
			model = new ResultModel(WConst.SUCCESS,"获取成功",result);
		}
		catch(Exception e) {
			model = new ResultModel(WConst.ERROR,"信息获取错误",e);
		}
		return model;
	}

	@Override
	public List<Map<String, Object>> getShowRoomList1(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.getShowRoomList1(map);
	}

	@Override
	public List<Map<String, Object>> getShowRoomList2(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.getShowRoomList2(map);
	}

	@Override
	public int submitAllocation(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.submitAllocation(map);
	}

	@Override
	public List<Map<String, Object>> getBoothAllocationInfoList(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.getBoothAllocationInfoList(map);
	}

	@Override
	public int getBoothAllocationInfoListCount(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.getBoothAllocationInfoListCount(map);
	}

	@Override
	public List<Map<String, Object>> getRemainingBoothInfoList(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.getRemainingBoothInfoList(map);
	}

	@Override
	public int getRemainingBoothInfoListCount(Map<String, Object> map) {
		return ebsTradinggroupboothallocationDao.getRemainingBoothInfoListCount(map);
	}
}
