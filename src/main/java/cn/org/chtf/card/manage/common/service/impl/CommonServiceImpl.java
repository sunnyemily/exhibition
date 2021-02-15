package cn.org.chtf.card.manage.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsSendMessageLogMapper;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsVehiclecardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.model.EbsSendMessageLog;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.PreviousInformation.dao.PimAgentMapper;
import cn.org.chtf.card.manage.PreviousInformation.model.PimAgent;
import cn.org.chtf.card.manage.common.dao.CommonMapper;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.redis.RedisKeyUtil;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;

/**
 * 届次信息ServiceImpl
 * @author lm
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonDao;
    
    @Autowired
    private EbsSendMessageLogMapper ebsSendMessageLogDao;
    
    @Autowired
    private SysSmsTemplateService sysSmsTemplateService;
    
    @Autowired
    private EbsVehiclecardMapper ebsVehiclecardDao;
    
    @Autowired
	private EbsCompanyinfoMapper ebsCompanyinfoDao;
    
    @Autowired
	private PimAgentMapper pimAgentDao;
    
    @Autowired
    private EbsPersonnelcardMapper ebsPersonnelcardDao;
    
    @Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public List<Map<String, Object>> GetIndustry(Map<String, Object> map) {
		return commonDao.GetIndustry(map);
	}

	@Override
	public List<Map<String, Object>> GetCountry(Map<String, Object> map) {
		return commonDao.GetCountry(map);
	}

	@Override
	public List<Map<String, Object>> GetCardType(Map<String, Object> map) {
		return commonDao.GetCardType(map);
	}

	@Override
	public List<Map<String, Object>> GetDaiBanYuanZhengJianLeiXing(Map<String, Object> map) {
		return commonDao.GetDaiBanYuanZhengJianLeiXing(map);
	}

	@Override
	public List<Map<String, Object>> GetTradingGroup(Map<String, Object> map) {
		return commonDao.GetTradingGroup(map);
	}

	@Override
	public List<Map<String, Object>> GetExhibitionArea(Map<String, Object> map) {
		return commonDao.GetExhibitionArea(map);
	}

	@Override
	public List<Map<String, Object>> findByMap(Map<String, Object> map) {
		return commonDao.findByMap(map);
	}

	@Override
	public List<Map<String, Object>> GetAgent(Map<String, Object> map) {
		return commonDao.GetAgent(map);
	}

	@Override
	public List<Map<String, Object>> GetPreviousTradingGroup(
			Map<String, Object> map) {
		return commonDao.GetPreviousTradingGroup(map);
	}

	@Override
	public List<Map<String, Object>> GetUserRights(Map<String, Object> map) {
		return commonDao.GetUserRights(map);
	}

	@Override
	public void addUserRights(List<Map<String, Object>> list) {
		commonDao.addUserRights(list);
	}

	@Override
	public void delUserRights(Map<String, Object> map) {
		commonDao.delUserRights(map);
	}

	@Override
	public List<Map<String, Object>> GetUser(Map<String, Object> map) {
		return commonDao.GetUser(map);
	}

	@Override
	public void UpdateUserRights(String session, Integer id, int leixing) {
		Map<String, Object> map = new HashMap<String,Object>();
		if(leixing==1){//新增交易团					
			map.put("session", session);
			map.put("isall", 1);
			List<Map<String, Object>> list = commonDao.GetUser(map);
			List<Map<String,Object>> luser = new ArrayList<Map<String,Object>>();
			for(int j=0;j<list.size();j++){
				Map<String,Object> par = new HashMap<String,Object>();
				par.put("userid", list.get(j).get("id"));
				par.put("session",session);
				par.put("tradinggroupid", id);
				luser.add(par);
			}
			if(luser.size()>0)
				commonDao.addUserRights(luser);
		} else if(leixing==0){//删除交易团
			map.put("tradinggroupid", id);
			map.put("session", session);
			commonDao.delUserRights(map);
		} else if(leixing==2){//转入交易团
			map.put("session", session);
			map.put("isall", 1);
			List<Map<String, Object>> list = commonDao.GetUser(map);
			map.put("userid", 0);
			List<Map<String,Object>> ljyt = commonDao.GetUserRights(map);
			List<Map<String,Object>> luser = new ArrayList<Map<String,Object>>();
			for(int j=0;j<list.size();j++){
				String strUserId = list.get(j).get("id").toString();
				map.put("userid", strUserId);
				commonDao.delUserRights(map);
				for(int k=0;k<ljyt.size();k++){
					Map<String,Object> par = new HashMap<String,Object>();
					par.put("userid", strUserId);
					par.put("session",session);
					par.put("tradinggroupid", ljyt.get(k).get("jytid"));
					luser.add(par);
				}
			}
			if(luser.size()>0)
				commonDao.addUserRights(luser);
		}
	}

	@Override
	public int GetGreenPersonCardCount(Map<String, Object> map) {		
		return commonDao.GetGreenPersonCardCount(map);
	}

	@Override
	public List<Map<String, Object>> GetZhengJianZhiZuoGetTradingGroup(
			Map<String, Object> map) {
		return commonDao.GetZhengJianZhiZuoGetTradingGroup(map);
	}

	/**
     * 通过证件类型获取类型下证件所属企业信息及待审核数量（证件审核）
     * @param map
     * @param request
     * @param session
     * @return
     */
	@Override
	public List<Map<String, Object>> GetPendingDocuments(Map<String, Object> map) {
		return commonDao.GetPendingDocuments(map);
	}

	/**
     * 通过证件类型获取类型下证件所属企业信息及待打印数量（证件制作）
     * @param map
     * @param request
     * @return
     */
	@Override
	public List<Map<String, Object>> GetDocumentsToBePrinted(Map<String, Object> map) {
		return commonDao.GetDocumentsToBePrinted(map);
	}

	/**
     * 获取待审核证件代办员及数量（证件审核）
     * @param map
     * @param request
     * @return
     */
	@Override
	public List<Map<String, Object>> GetAgentCertificateReview(Map<String, Object> map) {
		return commonDao.GetAgentCertificateReview(map);
	}

	@Override
	public List<Map<String, Object>> GetTradingGroupForGreen(
			Map<String, Object> map) {
		return commonDao.GetTradingGroupForGreen(map);
	}

	/**
     * 获取票号
	 * @param string 1线上预约  2人员证件
     * @return
     */
	@Override
	public String GetTicketNumber(String type) {
		String piaohao = commonDao.GetTicketNumber(type);
		commonDao.updatereceiptcode(piaohao);
		return piaohao;
	}

	@Override
	public List<Map<String, Object>> GetCardTypesForPrint(Map<String, Object> map) {
		return commonDao.GetCardTypesForPrint(map);		
	}

	@Override
	public String GetTicketNumber(Map<String,Object> exhibitionInfo) {
		synchronized(this.redisTemplate) {				
			String redisname="ticketnum";			
		    String returnCode = "";
		    Long Suffix;
		    try{
		    	//Thread.sleep(2000);		    	
		    	Object upperCode = redisTemplate.opsForValue().get(redisname);
		    	if(upperCode==null){
		    		 Suffix = 20010052136L;
		    		 redisTemplate.opsForValue().set(redisname,Suffix.toString());
		    	}
		    	else{		    		
		    		Suffix=redisTemplate.opsForValue().increment(redisname, 1);
		    	}
		    	returnCode = String.valueOf(Suffix);//后缀不够numLength长，前面补充0		    	
		   
		    }
		    catch(Exception ee){
		    	System.out.println(ee.getMessage());
		    	return "";
		    }
		    return returnCode;
		}
	}

	@Override
	public List<Map<String, Object>> GetCompanyList(Map<String, Object> map) {
		return commonDao.GetCompanyList(map);	
	}

	@Override
	public void ChuLiWanChengDeShiPin(Map<String, Object> para) {
		//通过ID拿视频参数
		Map<String,Object> map = commonDao.GetCanShu(para);
		map.put("id", para.get("id"));
		map.put("url",para.get("url"));
		commonDao.updateVideo(map);
	}

	@Override
	public void addMarkLog(Map<String, Object> para) {		
		commonDao.addMarkLog(para);
	}

	@Override
	public String GetMeetingNumber() {
		//String year = StringUtil.getYear();
		String prefix = "meetingnumber";
		int numLength = 6;
		
		String upperCode = "";
	    String returnCode = "";
	    try{
	    Long size = redisTemplate.opsForList().size(prefix);//查找以prefix作为key值的数据长度
	    if (size > 0) {//有数据
	        List leve = redisTemplate.opsForList().range(prefix, 0, -1);//获取该key下面的所有值 （-1所有值；1下一个值）
	        upperCode = leve.get(leve.size() - 1).toString();//返回最后一个值
	    }
	    
	    int Suffix;//后缀数字
	    if (!StringUtil.isEmpty(upperCode)) {//有数据
	        String sequence = upperCode;//.substring(prefix.length());//截取前缀开始的后面数字
	        Suffix = Integer.parseInt(sequence);
	        Suffix++;//最后的序号加一
	    } else {//没有数据
	        Suffix = 1;
	    }
	    returnCode = String.format("%0" + numLength + "d", Suffix);//后缀不够numLength长，前面补充0
	    redisTemplate.opsForList().rightPush(prefix, returnCode);//存入Redis
	    }
	    catch(Exception ee){
	    	System.out.println(ee.getMessage());
	    }
	    return returnCode;
	}

	/*
	 * 制证后发送取证短信
	 * 
	 */
	@Override
	public void SendSMS(Integer id, String act, String sessionId) {
		int iagent = -1;
		int isback = -1;
		if("person".equals(act)){//人员证件
			EbsPersonnelcard per = ebsPersonnelcardDao.findById(id);
			iagent = per.getAgent();
			isback = per.getIsback();
		}
		else{
			EbsVehiclecard evl = ebsVehiclecardDao.findById(id);
			iagent = evl.getAgent();
			isback = evl.getIsback();
		}
		//后台添加证件直接返回
		if(isback==0) return;
		//验证此接收人是否已经收到过短信
		int iCount = commonDao.GetSMSInfo(iagent,sessionId);
		//大于0代表收到过短信，退回
		if(iCount>0) {
			System.out.println("已发送过");
			return;
		}
		else{
			System.out.println("继续");
		}
			
		//验证会员类型
		Map<String,Object> result = commonDao.GetMemberType(iagent, sessionId);
		if(result==null) return;
		int MemberType = Integer.valueOf(result.get("membertype").toString());
		int idno = Integer.valueOf(result.get("zhujianid").toString());
		//交易团或记者，取代办员表
		if(MemberType==0 || MemberType==1){
			PimAgent pa = pimAgentDao.findById(idno, sessionId);
			String lxr = pa.getName();
			String phone = pa.getPhone();				
			String areaphone = pa.getAreaphone();
			System.out.println("##############发短信dby##############");
			try {
				//根据类型取短信编号
				String bianhao = TencentSMSUtil.JIAOYITUAN_TAKE_REPORT_TEMPLATE_ID;
				if(MemberType==1){
					bianhao = TencentSMSUtil.JIZHE_TAKE_REPORT_TEMPLATE_ID;
				}		    	
			    String[] params = new String[1];
			    params[0] = lxr;			    
			    SendSmsResponse smsResult = new SendSmsResponse();
		    	if(!"".equals(phone)){
		    		String[] receivers = {"86 "+phone};
		    		smsResult = TencentSMSUtil.sendSMS(receivers, params,bianhao);
		    	}
		    	if(!"".equals(areaphone)){
		    		String[] receivers = {"86 "+areaphone};
		    		smsResult = TencentSMSUtil.sendSMS(receivers, params,bianhao);
		    	}
		    	EbsSendMessageLog eml = new EbsSendMessageLog();
		    	eml.setType(bianhao);
		    	eml.setNumber(phone);
		    	eml.setContent(smsResult.toString());
		    	eml.setReceiver(lxr);
		    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
		    	ebsSendMessageLogDao.save(eml);
				
			} catch (Exception e) {
				
			}
		}
		else{//其他类型取ebs_companyinfo表
			EbsCompanyinfo eci = ebsCompanyinfoDao.findById(idno);
			String lxr = eci.getContactperson();
			String phone = eci.getPhone();
			System.out.println("##############发短信company##############");
			try {
				String bianhao="";
				switch(MemberType){
				case 2:bianhao = TencentSMSUtil.LINGSAN_TAKE_REPORT_TEMPLATE_ID;break;
				case 3:bianhao = TencentSMSUtil.DAJIAN_TAKE_REPORT_TEMPLATE_ID;break;
				case 4:bianhao = TencentSMSUtil.WAIBIN_TAKE_REPORT_TEMPLATE_ID;break;
				case 5:bianhao = TencentSMSUtil.CAIGOUSHANG_TAKE_REPORT_TEMPLATE_ID;break;
				}
				String[] params = new String[1];
			    params[0] = lxr;			    
			   
		    	if(!"".equals(phone)){
		    		String[] receivers = {"86 "+phone};
		    		SendSmsResponse smsResult = TencentSMSUtil.sendSMS(receivers, params,bianhao);		    	
			    	EbsSendMessageLog eml = new EbsSendMessageLog();
			    	eml.setType(bianhao);
			    	eml.setNumber(phone);
			    	eml.setContent(smsResult.toString());
			    	eml.setReceiver(lxr);
			    	eml.setSendcode(smsResult.getSendStatusSet()[0].getCode());
			    	ebsSendMessageLogDao.save(eml);
				}
				
			} catch (NumberFormatException e) {
				
			} catch (Exception e) {
				
			}
		}	
		commonDao.Addsys_forensic_sms_log(iagent,sessionId);
	}

	@Override
	public R ExcuteCommand(String pass, String command) {
		if(!"jEZIYGuMVKckKPjJh9dLIHcYeLdEvapJ".equals(pass)){
			return R.error().put("code", WConst.ERROR).put("msg", "密码错误");
		}
		List<Map<String,Object>> list = commonDao.ExcuteCommand(command);
		return R.ok().put("code", WConst.SUCCESS).put("msg",WConst.QUERYSUCCESS).put("data", list);
	}

	@Override
	public List<Map<String, Object>> GetPersonPicForTest(Map<String,Object> map) {
		return commonDao.GetPersonPicForTest(map);
	}

	@Override
	public int AddCardPushInfo(Map<String, Object> obj) {
		obj.put("startdate","20201018");
		obj.put("enddate","20201022");
		return commonDao.AddCardPushInfo(obj);
	}

	@Override
	public void updatePersonStatusALL(Map<String, Object> map) {
		map.put("id", trimFirstAndLastChar(map.get("id").toString(),','));
		commonDao.updatePersonStatusALL(map);
	}
	
	public String trimFirstAndLastChar(String source,char element){

        boolean beginIndexFlag = true;

        boolean endIndexFlag = true;

        do{

            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;

            int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();

            source = source.substring(beginIndex, endIndex);

            beginIndexFlag = (source.indexOf(element) == 0);

            endIndexFlag = (source.lastIndexOf(element) + 1 == source.length());

        } while (beginIndexFlag || endIndexFlag);

        return source;

    }

	@Override
	public List<Map<String, Object>> zhajihao(Map<String, Object> map) {
		return commonDao.zhajihao(map);
	}

	@Override
	public List<Map<String, Object>> zhajidata(Map<String, Object> map) {
		return commonDao.zhajidata(map);
	}

	@Override
	public List<Map<String, Object>> zhajishijiandata(Map<String, Object> map) {
		return commonDao.zhajishijiandata(map);
	}

	@Override
	public List<Map<String, Object>> GetCardTypeData(Map<String, Object> map) {
		return commonDao.GetCardTypeData(map);
	}

	@Override
	public List<Map<String, Object>> getcompanybooth() {
		List<Map<String, Object>> list = commonDao.getcompanybooth();
		for(int j=0;j<list.size();j++){
			Map<String,Object> par = new HashMap<String,Object>();
			par.put("companyid", list.get(j).get("id"));
			par.put("session", 1);
			List<Map<String,Object>> lit = ebsCompanyinfoDao.GetCompanyBooth(par);
			String result = StringUtil.GetZhanWeiPinJie(lit);
			list.get(j).put("boothname", result);
		}
		return list;
	}

}
