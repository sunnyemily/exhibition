package cn.org.chtf.card.manage.Exhibitors.service.impl;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsPersonnelcardService;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.common.dao.CommonMapper;
import cn.org.chtf.card.manage.member.dao.MemberDAO;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.system.model.SystemDictionaries;
import cn.org.chtf.card.manage.system.service.SystemDictionariesService;
import cn.org.chtf.card.manage.wechatuser.dao.WechatUserMapper;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-人员证件管理ServiceImpl
 * @author ggwudivs
 */
@Service
public class EbsPersonnelcardServiceImpl implements EbsPersonnelcardService {

    @Autowired
    private EbsPersonnelcardMapper ebsPersonnelcardDao;
    
    @Autowired
	private EbsCompanyinfoMapper ebsCompanyinfoDao;

	@Autowired
	private MemberDAO memberDAO;
    
    @Autowired
    private SystemDictionariesService systemDictionariesService;
    
    @Autowired
    private CmCertificateTypeMapper cardTypeService;

    @Autowired
    private EbsCompanyinfoMapper companyService;
    
	private List<EbsPersonnelcard> findByMap;
	
	@Autowired
    private CommonMapper commonDao;

	@Autowired
	private WechatUserMapper wechatUserDAO;

    /**
     * 查询参展商管理-人员证件管理列表
     */
    @Override
    public List<EbsPersonnelcard> list(Map<String, Object> map) {
    	String newsession=String.valueOf(map.get("session"));
    	List<EbsPersonnelcard> list = ebsPersonnelcardDao.list(map);
    	List<Map<String,Object>> dbys = commonDao.getDBYName(map);
    	List<Map<String,Object>> companys = commonDao.getCompanyTrading(map);
    	for(int j=0;j<list.size();j++){   	
    		int iagentid = list.get(j).getAgent();
    		int iisback = list.get(j).getIsback();
    		List<Map<String,Object>> rsh = dbys.stream().filter(item ->String.valueOf(item.get("uid")).equals(String.valueOf(iagentid)) && String.valueOf(item.get("isback")).equals(String.valueOf(iisback))).collect(Collectors.toList());
    		if(rsh.size()>0){
    			list.get(j).setAgentname(String.valueOf(rsh.get(0).get("uname")));
    		}
    		String qymc = list.get(j).getCompanyname();
    		List<Map<String,Object>> qys = companys.stream().filter(item ->String.valueOf(item.get("companyname")).equals(qymc) && String.valueOf(item.get("session")).equals(newsession)).collect(Collectors.toList());
    		if(qys.size()>0){
    			list.get(j).setTjleixing(String.valueOf(qys.get(0).get("tjleixing")));
    			list.get(j).setJiaoyituan(String.valueOf(qys.get(0).get("jiaoyituan")));
    		}
    	}   
    	return list;
    }
    
    /**
     * 数量参展商管理-人员证件管理
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return ebsPersonnelcardDao.listcount(map);
    }


    /**
     * 通过id查询单个参展商管理-人员证件管理
     */
    @Override
    public EbsPersonnelcard findById(Integer id) {
    	EbsPersonnelcard epc =  ebsPersonnelcardDao.findById(id);
    	epc.setIccodeaes(CryptographyUtil.encrypt(epc.getIccode(), Charset.forName("utf8"), "lbh@MaoC"));
    	
    	Map<String,Object> par = new HashMap<String,Object>();
		par.put("companyid", epc.getCompanyid());
		par.put("session", epc.getSession());
		List<Map<String,Object>> lit = ebsCompanyinfoDao.GetCompanyBooth(par);
		String result = StringUtil.GetZhanWeiPinJie(lit);
		epc.setBoothcode(result);
    	
    	return epc;
    }

    /**
     * 通过map查询单个参展商管理-人员证件管理
     */
    @Override
    public List<EbsPersonnelcard> findByMap(Map<String, Object> map) {
        return ebsPersonnelcardDao.findByMap(map);
    }

    /**
     * 新增参展商管理-人员证件管理
     */
    @Override
    public int save(EbsPersonnelcard ebsPersonnelcard) {
        return ebsPersonnelcardDao.save(ebsPersonnelcard);
    }

    /**
     * 修改参展商管理-人员证件管理
     */
    @Override
    public int update(EbsPersonnelcard ebsPersonnelcard) {
        return ebsPersonnelcardDao.update(ebsPersonnelcard);
    }

    /**
     * 删除参展商管理-人员证件管理
     */
    @Override
    public int deleteById(Integer id) {
        return ebsPersonnelcardDao.deleteById(id);
    }
    

    /**
     * 查询参展商管理-人员证件管理列表
     */
    @Override
    public List<EbsPersonnelcard> listFront(Map<String, Object> map) {
    	Integer statuCode = (Integer) map.get("status");
    	
    	Map<String, Object> filter = new HashMap<String,Object>();
    	switch(statuCode) {
    	case 1:
    		filter.put("isforensics", "1");
    		break;
    	case 2:
    		filter.put("printstatus", "2");
    		break;
    	case 3:
    		filter.put("status", "1");
    		break;
    	case 4:
    		filter.put("status", "0");
    		break;
    	case 5:
    		filter.put("status", "-1");
    		break;
    	}
    	filter.put("agent", map.get("memeberId"));
        return ebsPersonnelcardDao.list(filter);
    }
    /**
     **数量参展商管理-人员证件管理
     */
    @Override
    public int listFrontCount(Map<String, Object> map) {Integer statuCode = (Integer) map.get("status");
	
		Map<String, Object> filter = new HashMap<String,Object>();
		switch(statuCode) {
		case 1:
			filter.put("isforensics", "1");
			break;
		case 2:
			filter.put("printstatus", "2");
			break;
		case 3:
			filter.put("status", "1");
			break;
		case 4:
			filter.put("status", "0");
			break;
		case 5:
			filter.put("status", "-1");
			break;
		}
		filter.put("agent", map.get("memeberId"));
        return ebsPersonnelcardDao.listcount(filter);
    }

	@Override
	public List<Map<String, Object>> GetDownLoadInfo(Map<String, Object> map) {
		List<Map<String, Object>> list = ebsPersonnelcardDao.GetDownLoadInfo(map);
		Map<String,Object> params = new HashMap<String,Object>();
		List<SystemDictionaries> lit = systemDictionariesService.findByMap(params);
		for(int j=0;j<list.size();j++){
			String chmd = list.get(j).get("purpose").toString();
			if(!chmd.equals("")){
				String chmdname="";
				String[] Args = chmd.split(",");
				for(int k=0;k<Args.length;k++){
					for(int d=0;d<lit.size();d++){
						if(lit.get(d).getDicid().toString().equals(Args[k])){
							chmdname+=lit.get(d).getDicCnName()+" ";
							break;
						}
					}
				}
				list.get(j).put("chmdname", chmdname);
			}
			
			String rhzdzh=list.get(j).get("knowexhibition").toString();
			if(!rhzdzh.equals("")){
				String rhzdzhname="";
				String[] Args = rhzdzh.split(",");
				for(int k=0;k<Args.length;k++){
					for(int d=0;d<lit.size();d++){
						if(lit.get(d).getDicid().toString().equals(Args[k])){
							rhzdzhname+=lit.get(d).getDicCnName()+" ";
							break;
						}
					}
				}
				list.get(j).put("rhzdzhname", rhzdzhname);
			}
			
			String ywxz=list.get(j).get("businessnature").toString();
			if(!ywxz.equals("")){
				String ywxzname="";
				String[] Args = ywxz.split(",");
				for(int k=0;k<Args.length;k++){
					for(int d=0;d<lit.size();d++){
						if(lit.get(d).getDicid().toString().equals(Args[k])){
							ywxzname+=lit.get(d).getDicCnName()+" ";
							break;
						}
					}
				}
				list.get(j).put("ywxzname", ywxzname);
			}
			
		}
		return list;
	}
	
	/**
	 * 更新人员证件信息
	 * @author wushixing
	 * @param ebsPersonnelcard
	 * @return
	 */
	public ResultModel addOrUpdate(EbsPersonnelcard ebsPersonnelcard,Member member,Map<String, Object> exhibitionInfo) {
		ResultModel result = null;
		try {
			CmCertificateType card = cardTypeService.findById(ebsPersonnelcard.getCardtype());			
			if(isExceedLimit(card,ebsPersonnelcard,member,exhibitionInfo)) {//超出限定值
				result = new ResultModel(WConst.ERROR,"保存失败，所办证件已达到限定上限",null); 
				return result;
			}
			int c = 0;
			if(ebsPersonnelcard.getId()==null||ebsPersonnelcard.getId().equals(0)) {
				c = save(ebsPersonnelcard);
			}
			else {
				c = update(ebsPersonnelcard);
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
	public ResultModel getPersonCards(EbsPersonnelcard personCard,PageModel page) {
		ResultModel result = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", page.getKeywords());
		map.put("index",page.getStart());
		map.put("size", page.getLimit());
		map.put("agent", personCard.getAgent());
		map.put("cardtype",personCard.getCardtype());
		map.put("session",personCard.getSession());
		map.put("companyname",personCard.getCompanyname());
		if(personCard.getIsout()!=null) {
			map.put("isout",personCard.getIsout());
		}
		switch(personCard.getStatus()) {
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
			result.setResult(ebsPersonnelcardDao.list(map));;
			result.setCount(ebsPersonnelcardDao.listcount(map));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	public ResultModel delete(Integer[] ids,Integer memberId) {
		ResultModel result = null;
		if(ids.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的信息");
			return result;
		}
		List<Integer> idList = Arrays.asList(ids);
		try {

			int row = ebsPersonnelcardDao.delete(idList,memberId);
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,row);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}

	@Override
	public List<EbsPersonnelcard> ExhibitorBadgeProductionlist(
			Map<String, Object> map) {
		List<EbsPersonnelcard> list = ebsPersonnelcardDao.ExhibitorBadgeProductionlist(map);	
		/*
    	List<Map<String,Object>> dbys = commonDao.getDBYName(map);
    	for(int j=0;j<list.size();j++){   	
    		int iagentid = list.get(j).getAgent();
    		int iisback = list.get(j).getIsback();
    		List<Map<String,Object>> rsh = dbys.stream().filter(item ->item.get("uid").equals(iagentid) && item.get("isback").equals(iisback)).collect(Collectors.toList());
    		if(rsh.size()>0){
    			list.get(j).setAgentname(String.valueOf(rsh.get(0).get("uname")));
    		}    		
    	}  */
    	return list;
	}

	@Override
	public int ExhibitorBadgeProductionlistcount(Map<String, Object> map) {
		return ebsPersonnelcardDao.ExhibitorBadgeProductionlistcount(map);
	}

	@Override
	public ResultModel getHistoryPersonCard(Integer memberId, Integer sessionId,String cardname, PageModel page) {
		ResultModel result = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start",page.getStart());
		map.put("limit", page.getLimit());
		map.put("memberId", memberId);
		map.put("cardname",cardname);
		map.put("sessionId",sessionId);
		map.put("name", page.getKeywords());
		map.put("companyname", page.getCompanyname());
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(ebsPersonnelcardDao.getHistoryPersonCard(map));;
			result.setCount(ebsPersonnelcardDao.getHistoryPersonCardCount(map));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultModel rejoin(Integer[] idList, Integer repeat,Map<String, Object> exhibitionInfo, Member member) {
		ResultModel result = null;
		Integer id = 0;
		try {
			for(int i = 0;i<idList.length;i++) {
				id = idList[i];
				//获取以前届次的证件
				EbsPersonnelcard personCard = ebsPersonnelcardDao.findById(id);
				//获取当前届次的证件类型
				CmCertificateType cardType = cardTypeService.findById(personCard.getCardtype());
				//当前届次是否已存在该企业的
//				if(member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)&&cardType.getIsexhibitor().equals(1)) {//是交易团且是参展证，则验证企业是否存在
//					Integer companyId = personCard.getCompanyid();
//				}
				Map<String,Object> cardTypeFilter = new HashMap<String,Object>();
				cardTypeFilter.put("session", exhibitionInfo.get("sessionId"));
				cardTypeFilter.put("chinesename", cardType.getChinesename());
				CmCertificateType nowCardType = cardTypeService.findByMap(cardTypeFilter);
				//当前届次是否已存在该证件
				Map<String,Object> filter = new HashMap<String,Object>();
				filter.put("session", exhibitionInfo.get("sessionId"));
				filter.put("cardnumber", personCard.getCardnumber());
				List<EbsPersonnelcard> cards = ebsPersonnelcardDao.findByMap(filter);
				if(cards.size()>0&&repeat.equals(0)) {
					throw new Exception(personCard.getName()+"的证件在本届已存在。");
				}
				else {
					personCard.setSession(String.valueOf(exhibitionInfo.get("sessionId")));
					personCard.setStatus(0);
					personCard.setAgent(member.getMemberId());
					personCard.setRemark("");
					personCard.setPrintstatus(0);
					personCard.setPrinttype(-1);
					personCard.setVerificationstatus(0);
					personCard.setIccode("");
					personCard.setVipcode("");
					personCard.setCardnature(0);
					personCard.setIsback(1);
					personCard.setIsforensics(0);
					personCard.setAddtime(new java.sql.Timestamp(new Date().getTime()));
					personCard.setCardtype(nowCardType.getId());
					personCard.setCardtypename(nowCardType.getChinesename());
					if(nowCardType.getIsexhibitor().equals(1)) {//是参展证的话获取同名本届企业的信息，并设置证件的公司id
						filter.put("chinesename", personCard.getCompanyname());
						List<EbsCompanyinfo> companys = companyService.findByMap(filter);
						if(companys.size()>0) {
							EbsCompanyinfo company = companys.get(0);
							personCard.setCompanyid(company.getId());
						}
					}
					
					if(isExceedLimit(nowCardType,personCard,member,exhibitionInfo)) {//超出限定值
						throw new Exception("激活成功，所办证件已达到限定上限");
					}
					ebsPersonnelcardDao.save(personCard);
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
	/**
	 * 所要新增或更新的证件是否已超过限定值
	 * @param card 证件类型
	 * @param ebsPersonnelcard 证件
	 * @param member 会员
	 * @param exhibitionInfo 届次
	 * @return 返回boolean
	 */
	public boolean isExceedLimit(CmCertificateType card,EbsPersonnelcard ebsPersonnelcard,Member member,Map<String, Object> exhibitionInfo) {
		boolean result = false;
		Integer sessionId = Integer.parseInt(exhibitionInfo.get("sessionId").toString());
		int maxCardCount = 0;
		if(member.getMemberType().equals(Member.MEMBER_TYPE_TRADE_CODE)||
				member.getMemberType().equals(Member.MEMBER_TYPE_REPORT_CODE)) {//代办员
			Integer tempMaxLimit;
			if(card.getIsexhibitor().equals(0)) {//非参展证
				tempMaxLimit = cardTypeService.getAgentCardLimit(member.getMemberId(), sessionId, ebsPersonnelcard.getCardtype());
			}
			else {//参展证
				tempMaxLimit = memberDAO.getTraddingGroupExhibitionCount(member.getMemberId(), sessionId);
			}
			if(tempMaxLimit!=null) {
				maxCardCount = tempMaxLimit;
			}
		}
		else{//非代办员只限制零散类型的参展证数量、和采购商证
			Integer tempMaxLimit;
			if(card.getIsexhibitor().equals(1)) {//参展证跟展位分配有关
				Map<String,Object> cardResult = memberDAO.getExhibitorCertificationCount(member.getMemberId(), sessionId);
					if(cardResult==null || cardResult.get("exhibitionCount")==null) {
						tempMaxLimit = 0;
					}
					else {
						tempMaxLimit = Integer.parseInt(cardResult.get("exhibitionCount").toString());
					}
			}
			else if(card.getChinesename().equals("采购商证")) {//采购商证和届次设定有关
				tempMaxLimit = Integer.parseInt(exhibitionInfo.get("purchaserCardLimit").toString());
			}
			else {//其他证件不限制
				tempMaxLimit = Integer.MAX_VALUE;
			}
			if(tempMaxLimit!=null) {
				maxCardCount = tempMaxLimit;
			}
		}
		
		//3.获取已填报此类证件的总数
		int addCount = ebsPersonnelcardDao.addCount(card.getId(), sessionId, member.getMemberId(), ebsPersonnelcard.getId());
		
		if(addCount+1 > maxCardCount) {//超出限定值
			result = true;
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
	        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date now = c.getTime();
			
			String makecardtime = dateFormat.format(now);
			//2.处理打印编码
			dateFormat=new SimpleDateFormat("yyyyMMdd");
			String printNumber = memberId + "-"+dateFormat.format(now)+String.format("%04d", cardType);
			if(awayStatus==0) {
				ebsPersonnelcardDao.updatePrintNumber(printNumber, memberId, makecardtime, sessionId, awayStatus, cardType);
			}
			List<Map<String, Object>> list = ebsPersonnelcardDao.takeAwayReport(memberId, makecardtime, sessionId, awayStatus, cardType);
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,list);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,e.getMessage(),e);
		}
		
		return result;
	}

	@Override
	public void deleteByMap(Map<String, Object> par) {
		ebsPersonnelcardDao.deleteByMap(par);
	}

	@Override
	public List<EbsPersonnelcard> Greenlist(Map<String, Object> map) {
		return ebsPersonnelcardDao.Greenlist(map);
	}

	@Override
	public int Greenlistcount(Map<String, Object> map) {
		return ebsPersonnelcardDao.Greenlistcount(map);
	}

	@Override
	public List<Map<String,Object>> PersonnelCardQuerylist(Map<String, Object> map) {
		List<Map<String,Object>> list = ebsPersonnelcardDao.PersonnelCardQuerylist(map);
		List<Map<String,Object>> dbys = commonDao.getDBYName(map);
    	for(int j=0;j<list.size();j++){   	
    		int iagentid = Integer.valueOf(list.get(j).get("agent").toString());
    		int iisback = Integer.valueOf(list.get(j).get("isback").toString());
    		List<Map<String,Object>> rsh = dbys.stream().filter(item ->String.valueOf(item.get("uid")).equals(String.valueOf(iagentid)) && String.valueOf(item.get("isback")).equals(String.valueOf(iisback))).collect(Collectors.toList());
    		if(rsh.size()>0){
    			list.get(j).put("agentname",String.valueOf(rsh.get(0).get("uname")));
    		}    		
    	}   
		return list;
	}

	@Override
	public int PersonnelCardQuerylistcount(Map<String, Object> map) {
		return ebsPersonnelcardDao.PersonnelCardQuerylistcount(map);
	}

	@Override
	public List<Map<String, Object>> PersonnelCertificateCollectionList(Map<String, Object> map) {		
		List<Map<String, Object>> list = ebsPersonnelcardDao.PersonnelCertificateCollectionList(map);    	
    	return list;
	}

	@Override
	public int PersonnelCertificateCollectionListCount(Map<String, Object> map) {
		return ebsPersonnelcardDao.PersonnelCertificateCollectionListCount(map);
	}

	@Override
	public ResultModel updateRouterPersonRole(String cardnumber, String name, Integer sessionId, HttpSession session) {
		try {
			//1.验证登录状态
			Object openId = session.getAttribute("openId");
			if(openId==null) {
				return WConst.RELOGINJSON;
			}
			//2.验证用户身份是否已认证过
			WechatUser user = wechatUserDAO.selectByRoutineOpenid(openId.toString());
			if(user.getUserTypeId()>1) {
				return new ResultModel(WConst.ERROR,"身份认证失败","已完成过认证，无需再次认证。");
			}
			//3.获取添加证件的人的所属公司类型
			EbsCompanyinfo company = ebsPersonnelcardDao.getRouterPersonRole(cardnumber, name, sessionId);
			if(company==null||
					company.getComanyTypeId()==null||
					(!company.getComanyTypeId().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)&&
							!company.getComanyTypeId().equals(Member.MEMBER_TYPE_PURCHASER_CODE))) {
				return new ResultModel(WConst.ERROR,"身份认证失败","请您仔细检查输入的姓名、身份证是否有误。");
			}
			//4.根据公司类型存储用户类型
			if(company.getComanyTypeId().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)) {
				user.setUserTypeId(2);
				user.setUserType("参展商");
			}
			if(company.getComanyTypeId().equals(Member.MEMBER_TYPE_PURCHASER_CODE)) {
				user.setUserTypeId(3);
				user.setUserType("采购商");
			}
			//5.存储证件信息
			user.setCardnumber(cardnumber);
			user.setName(name);
			int iCount = wechatUserDAO.isExists(company.getId());
			if(iCount==0)//不存在管理员，赋值
				user.setCompanyid(company.getId());
			
			wechatUserDAO.updateByRoutineOpenidSelective(user);
			return new ResultModel(WConst.SUCCESS,"身份认证成功："+user.getUserType(),"您已完成身份认证");
		
		}
		catch(Exception e) {
			return new ResultModel(WConst.ERROR,"身份认证失败",e.getLocalizedMessage());
		}
	}
	
	
	@Override
	public ResultModel getRouterPersonRole(Integer sessionId, HttpSession session) {
		try {
			//1.验证登录状态
			Object openId = session.getAttribute("openId");
			if(openId==null) {
				return WConst.RELOGINJSON;
			}
			//2.验证用户身份是否已认证过
			Map<String,Object> res = new HashMap<String,Object>();
			WechatUser user = wechatUserDAO.selectByRoutineOpenid(openId.toString());
			if(user.getUserTypeId().equals(0)) {
				String title = "您的身份：访客";
				return new ResultModel(WConst.SUCCESS,title,"如果您是参展商/采购商，请联系贵司的展会管理员在后台添加人员。");
			}
			else if(user.getUserTypeId().equals(1)) {
				String title = "您的身份：观众";
				return new ResultModel(WConst.SUCCESS,title,"如果您是参展商/采购商，请联系贵司的展会管理员在后台添加人员。");
			}
			else if(user.getUserTypeId().equals(2)) {
				String title = "您已通过身份验证：参展商";
				EbsCompanyinfo company = ebsPersonnelcardDao.getRouterPersonRole(user.getCardnumber(), user.getName(), sessionId);
				res.put("companyName", company.getChinesename());
				res.put("mainBusiness", company.getBusscope());
				res.put("address", company.getChineseaddress());
				res.put("memberType", Member.MEMBER_TYPE_EXHIBITOR_CODE);
				if(user.getMemberId()!=null&&user.getMemberId()>0) {
					res.put("isAdmin", true);
				}
				else {
					res.put("isAdmin", false);
				}
				if(user.getMemberId()!=0) {
					title += "管理员";
				}
				return new ResultModel(WConst.SUCCESS,title,res);
			}
			else if(user.getUserTypeId().equals(3)) {
				EbsCompanyinfo company = ebsPersonnelcardDao.getRouterPersonRole(user.getCardnumber(), user.getName(), sessionId);
				String title = "您已通过身份验证：采购商";
				res.put("companyName", company.getChinesename());
				res.put("mainBusiness", company.getBusscope());
				res.put("address", company.getChineseaddress());
				res.put("memberType", Member.MEMBER_TYPE_PURCHASER_CODE);
				if(user.getMemberId()!=null&&user.getMemberId()>0) {
					res.put("isAdmin", true);
				}
				else {
					res.put("isAdmin", false);
				}
				if(user.getMemberId()!=0) {
					title += "管理员";
				}
				return new ResultModel(WConst.SUCCESS,title,res);
			}
			return new ResultModel(WConst.ERROR,"未知身份",user);

		
		}
		catch(Exception e) {
			return new ResultModel(WConst.ERROR,"身份认证成功失败",e.getLocalizedMessage());
		}
	}

	@Override
	public List<Map<String, Object>> getPersonnelCardList(
			Map<String, Object> map) {
		return ebsPersonnelcardDao.getPersonnelCardList(map);
	}

	@Override
	public int getPersonnelCardListCount(Map<String, Object> map) {
		return ebsPersonnelcardDao.getPersonnelCardListCount(map);
	}

	@Override
	public int resetStatusByCompanyId(Map<String, Object> map) {
		return ebsPersonnelcardDao.resetStatusByCompanyId(map);
	}

	@Override
	public ResultModel updateWebPersonRole(String cardnumber, String name,
			int sessionId, HttpSession session) {
		try {
			//1.验证登录状态
			WechatUser user = (WechatUser) session.getAttribute("wechatUser");
			if(user==null){
				return WConst.RELOGINJSON;
			}
			//2.验证用户身份是否已认证过
			//WechatUser user = wechatUserDAO.selectByRoutineOpenid(openId.toString());
			if(user.getUserTypeId()>1) {
				return new ResultModel(WConst.ERROR,"身份认证失败","已完成过认证，无需再次认证。");
			}
			//3.获取添加证件的人的所属公司类型
			EbsCompanyinfo company = ebsPersonnelcardDao.getRouterPersonRole(cardnumber, name, sessionId);
			if(company==null||
					company.getComanyTypeId()==null||
					(!company.getComanyTypeId().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)&&
							!company.getComanyTypeId().equals(Member.MEMBER_TYPE_PURCHASER_CODE))) {
				return new ResultModel(WConst.ERROR,"身份认证失败","请您仔细检查输入的姓名、身份证是否有误。");
			}
			//4.根据公司类型存储用户类型
			if(company.getComanyTypeId().equals(Member.MEMBER_TYPE_EXHIBITOR_CODE)) {
				user.setUserTypeId(2);
				user.setUserType("参展商");
			}
			if(company.getComanyTypeId().equals(Member.MEMBER_TYPE_PURCHASER_CODE)) {
				user.setUserTypeId(3);
				user.setUserType("采购商");
			}
			//5.存储证件信息
			user.setCardnumber(cardnumber);
			user.setName(name);
			int iCount = wechatUserDAO.isExists(company.getId());
			if(iCount==0)//不存在管理员，赋值
				user.setCompanyid(company.getId());
			
			wechatUserDAO.updateByPrimaryKeySelective(user);
			WechatUser user1 = wechatUserDAO.selectByPrimaryKey(user.getUid());
			session.setAttribute("wechatUser",user1);
			return new ResultModel(WConst.SUCCESS,"身份认证成功："+user.getUserType(),"您已完成身份认证");
		
		}
		catch(Exception e) {
			return new ResultModel(WConst.ERROR,"身份认证失败",e.getLocalizedMessage());
		}
	}

	
}
