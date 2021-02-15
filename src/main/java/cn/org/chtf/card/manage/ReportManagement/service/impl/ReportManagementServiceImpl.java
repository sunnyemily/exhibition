package cn.org.chtf.card.manage.ReportManagement.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.MakeEvidence.dao.CmCertificateTypeMapper;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.ReportManagement.dao.ReportManagementMapper;
import cn.org.chtf.card.manage.ReportManagement.model.AudienceSurvey;
import cn.org.chtf.card.manage.ReportManagement.model.PurchaseIntentionStatistics;
import cn.org.chtf.card.manage.ReportManagement.model.ReportManagement;
import cn.org.chtf.card.manage.ReportManagement.service.ReportManagementService;
import cn.org.chtf.card.manage.common.dao.CommonMapper;
import cn.org.chtf.card.manage.system.dao.SystemDictionariesMapper;
import cn.org.chtf.card.manage.system.model.SystemDictionaries;

/**
 * 报表管理
 * @author lm
 */
@Service
public class ReportManagementServiceImpl implements ReportManagementService {

	@Autowired
    private SystemDictionariesMapper systemDictionariesDao;
	
	@Autowired
    private CmCertificateTypeMapper cmCertificateTypeDao;
	
	@Autowired
    private CommonMapper commonDao;
	
    @Autowired
    private ReportManagementMapper reportManagementDao;
    
    @Autowired
    private EbsPersonnelcardMapper ebsPersonnelcardDao;

	@Override
	public List<Map<String,Object>> NationalDocumentStatisticsList(Map<String, Object> map) {
		return reportManagementDao.NationalDocumentStatisticsList(map);
	}

	@Override
	public int NationalDocumentStatisticsListCount(Map<String, Object> map) {
		return reportManagementDao.NationalDocumentStatisticsListCount(map);
	}

	@Override
	public List<ReportManagement> NationalDocumentStatisticsCardList(Map<String, Object> map) {
		return reportManagementDao.NationalDocumentStatisticsCardList(map);
	}

	@Override
	public int NationalDocumentStatisticsCardListCount(Map<String, Object> map) {
		return reportManagementDao.NationalDocumentStatisticsCardListCount(map);
	}

	@Override
	public List<AudienceSurvey> AudienceSurveyList(Map<String, Object> map) {
		List<AudienceSurvey> list = new ArrayList<AudienceSurvey>();
		List<AudienceSurvey> listchjs = new ArrayList<AudienceSurvey>();	
		List<AudienceSurvey> listchmd = new ArrayList<AudienceSurvey>();
		List<AudienceSurvey> listzhanqu = new ArrayList<AudienceSurvey>();
		List<AudienceSurvey> listzhanhui = new ArrayList<AudienceSurvey>();
		List<AudienceSurvey> listywxz = new ArrayList<AudienceSurvey>();
		List<AudienceSurvey> listywly = new ArrayList<AudienceSurvey>();
		List<EbsPersonnelcard> lit = ebsPersonnelcardDao.findByMapForReport(map);
		String leixing = String.valueOf(map.get("leixing"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("useable", 1);
		List<SystemDictionaries> listZD = new ArrayList<SystemDictionaries>();	
		if(leixing.equals("") || leixing.equals("3")){
			params.put("dicParentid", 3);			
			listZD = systemDictionariesDao.findByMap(params);
			int itotal=0;
			for(int j=0;j<listZD.size();j++){
				String typename="参会角色";
				String answername = listZD.get(j).getDicCnName();
				int answerid = Integer.valueOf(listZD.get(j).getDicid().toString());				
				List<EbsPersonnelcard> rsh = lit.stream().filter(item ->item.getParticipants() == answerid).collect(Collectors.toList());
				AudienceSurvey as = new AudienceSurvey();
				as.setTypename(typename);
				as.setAnswername(answername);
				as.setType("participants");
				as.setTypeid(answerid);
				as.setTotal(rsh.size());
				as.setPercent("0");
				itotal=itotal+rsh.size();
				listchjs.add(as);				
			}
			if(!leixing.equals("")){
				//计算占比
				for(int k=0;k<listchjs.size();k++){
					int total = listchjs.get(k).getTotal();
					if(total>0){						
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String num = df.format(((float)total/itotal)*100);//返回的是String类型 
						listchjs.get(k).setPercent(num);
					}
				}
			}
		}
		if(leixing.equals("") || leixing.equals("4")){
			params.put("dicParentid", 4);			
			listZD = systemDictionariesDao.findByMap(params);
			int itotal=0;
			for(int j=0;j<listZD.size();j++){
				String typename="参会目的";
				String answername = listZD.get(j).getDicCnName();
				String answerid = listZD.get(j).getDicid().toString();
				int count=0;
				for(int k=0;k<lit.size();k++){
					String chmd = lit.get(k).getPurpose();
					if(!chmd.equals("")){
						String[] ids = chmd.split(",");
						for (String id : ids) {
							if(id.equals(answerid)){
								count++;
							}
						}
					}					
				}
				AudienceSurvey as = new AudienceSurvey();
				as.setTypename(typename);
				as.setAnswername(answername);
				as.setType("purpose");
				as.setTypeid(Integer.valueOf(answerid));
				as.setTotal(count);
				as.setPercent("0");
				itotal=itotal+count;
				listchmd.add(as);		
			}
			if(!leixing.equals("")){
				//计算占比
				for(int k=0;k<listchmd.size();k++){
					int total = listchmd.get(k).getTotal();
					if(total>0){
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String num = df.format(((float)total/itotal)*100);//返回的是String类型 
						listchmd.get(k).setPercent(num);
					}
				}
			}
		}
		if(leixing.equals("") || leixing.equals("20")){
			params.put("dicParentid", 20);			
			listZD = systemDictionariesDao.findByMap(params);
			int itotal=0;
			for(int j=0;j<listZD.size();j++){
				String typename="您想参观的展区";
				String answername = listZD.get(j).getDicCnName();
				int answerid = Integer.valueOf(listZD.get(j).getDicid().toString());
				List<EbsPersonnelcard> rsh = lit.stream().filter(item ->item.getVisitexhibition() == answerid).collect(Collectors.toList());
				AudienceSurvey as = new AudienceSurvey();
				as.setTypename(typename);
				as.setAnswername(answername);
				as.setType("visitexhibition");
				as.setTypeid(answerid);
				as.setTotal(rsh.size());
				as.setPercent("0");
				itotal=itotal+rsh.size();
				listzhanqu.add(as);				
			}
			if(!leixing.equals("")){
				//计算占比
				for(int k=0;k<listzhanqu.size();k++){
					int total = listzhanqu.get(k).getTotal();
					if(total>0){
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String num = df.format(((float)total/itotal)*100);//返回的是String类型 
						listzhanqu.get(k).setPercent(num);
					}
				}
			}
		}
		if(leixing.equals("") || leixing.equals("6")){
			params.put("dicParentid", 6);			
			listZD = systemDictionariesDao.findByMap(params);
			int itotal=0;
			for(int j=0;j<listZD.size();j++){
				String typename="您如何知道展会";
				String answername = listZD.get(j).getDicCnName();
				String answerid = listZD.get(j).getDicid().toString();
				int count=0;
				for(int k=0;k<lit.size();k++){
					String chmd = lit.get(k).getKnowexhibition();
					if(!chmd.equals("")){
						String[] ids = chmd.split(",");
						for (String id : ids) {
							if(id.equals(answerid)){
								count++;
							}
						}
					}					
				}
				AudienceSurvey as = new AudienceSurvey();
				as.setTypename(typename);
				as.setAnswername(answername);
				as.setType("knowexhibition");
				as.setTypeid(Integer.valueOf(answerid));
				as.setTotal(count);
				as.setPercent("0");
				itotal=itotal+count;
				listzhanhui.add(as);		
			}
			if(!leixing.equals("")){
				//计算占比
				for(int k=0;k<listzhanhui.size();k++){
					int total = listzhanhui.get(k).getTotal();
					if(total>0){
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String num = df.format(((float)total/itotal)*100);//返回的是String类型 
						listzhanhui.get(k).setPercent(num);
					}
				}
			}
		}
		if(leixing.equals("") || leixing.equals("8")){
			params.put("dicParentid", 8);			
			listZD = systemDictionariesDao.findByMap(params);
			int itotal=0;
			for(int j=0;j<listZD.size();j++){
				String typename="业务性质";
				String answername = listZD.get(j).getDicCnName();
				String answerid = listZD.get(j).getDicid().toString();
				int count=0;
				for(int k=0;k<lit.size();k++){
					String chmd = lit.get(k).getBusinessnature();
					if(!chmd.equals("")){
						String[] ids = chmd.split(",");
						for (String id : ids) {
							if(id.equals(answerid)){
								count++;
							}
						}
					}					
				}
				AudienceSurvey as = new AudienceSurvey();
				as.setTypename(typename);
				as.setAnswername(answername);
				as.setType("businessnature");
				as.setTypeid(Integer.valueOf(answerid));
				as.setTotal(count);
				as.setPercent("0");
				itotal=itotal+count;
				listywxz.add(as);		
			}
			if(!leixing.equals("")){
				//计算占比
				for(int k=0;k<listywxz.size();k++){
					int total = listywxz.get(k).getTotal();
					if(total>0){
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String num = df.format(((float)total/itotal)*100);//返回的是String类型 
						listywxz.get(k).setPercent(num);
					}
				}
			}
		}
		if(leixing.equals("") || leixing.equals("1000")){
			List<Map<String,Object>> litLY = commonDao.GetIndustry(map);
			int itotal =0;
			for(int j=0;j<litLY.size();j++){
				String typename="业务领域";
				String answername = litLY.get(j).get("name").toString();
				int answerid = Integer.valueOf(litLY.get(j).get("id").toString());
				List<EbsPersonnelcard> rsh = lit.stream().filter(item ->item.getBussinessarea() == answerid).collect(Collectors.toList());
				AudienceSurvey as = new AudienceSurvey();
				as.setTypename(typename);
				as.setAnswername(answername);
				as.setType("bussinessarea");
				as.setTypeid(answerid);
				as.setTotal(rsh.size());
				as.setPercent("0");
				itotal=itotal+rsh.size();
				listywly.add(as);				
			}
			if(!leixing.equals("")){
				//计算占比
				for(int k=0;k<listywly.size();k++){
					int total = listywly.get(k).getTotal();
					if(total>0){
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String num = df.format(((float)total/itotal)*100);//返回的是String类型 
						listywly.get(k).setPercent(num);
					}
				}
			}
		}
		list.addAll(listchjs);	
		list.addAll(listchmd);
		list.addAll(listzhanqu);
		list.addAll(listzhanhui);
		list.addAll(listywxz);
		list.addAll(listywly);
		return list;
	}

	@Override
	public List<Map<String, Object>> AudienceSurveyViewList(Map<String, Object> map) {
		List<Map<String, Object>> list = reportManagementDao.AudienceSurveyViewList(map);
		String newsession=String.valueOf(map.get("session"));    	
    	List<Map<String,Object>> dbys = commonDao.getDBYName(map);
    	List<Map<String,Object>> companys = commonDao.getCompanyTrading(map);
    	for(int j=0;j<list.size();j++){   	
    		int iagentid = Integer.valueOf(list.get(j).get("agent").toString());
    		int iisback = Integer.valueOf(list.get(j).get("isback").toString());
    		List<Map<String,Object>> rsh = dbys.stream().filter(item ->String.valueOf(item.get("uid")).equals(String.valueOf(iagentid)) && String.valueOf(item.get("isback")).equals(String.valueOf(iisback))).collect(Collectors.toList());
    		if(rsh.size()>0){
    			list.get(j).put("agentname",rsh.get(0).get("uname"));
    		}
    		String qymc = list.get(j).get("companyname").toString();
    		List<Map<String,Object>> qys = companys.stream().filter(item ->String.valueOf(item.get("companyname")).equals(qymc) && String.valueOf(item.get("session")).equals(newsession)).collect(Collectors.toList());
    		if(qys.size()>0){
    			list.get(j).put("tjleixing",String.valueOf(qys.get(0).get("tjleixing")));
    			//list.get(j).setJiaoyituan(String.valueOf(qys.get(0).get("jiaoyituan")));
    		}
    	}   
    	return list;
		
		
	}

	@Override
	public int AudienceSurveyViewListCount(Map<String, Object> map) {
		return reportManagementDao.AudienceSurveyViewListCount(map);
	}

	@Override
	public List<Map<String, Object>> RegistrationCertificateList(Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> par = new HashMap<String,Object>();
		//所有人员信息
		List<EbsPersonnelcard> lit = ebsPersonnelcardDao.findByMapForReport(map);
		//参展证
		map.put("isexhibitor", 1);
		CmCertificateType ct = cmCertificateTypeDao.findByMap(map);
		int cardtype=ct.getId();
		List<EbsPersonnelcard> rsh = lit.stream().filter(item ->item.getCardtype() == cardtype).collect(Collectors.toList());
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("zongshu", rsh.size());
		//参展证中国
		List<EbsPersonnelcard> rsh0 = lit.stream().filter(item ->item.getCardtype() == cardtype && item.getCountry().equals("900000")).collect(Collectors.toList());		
		params.put("zhongguo", rsh0.size());		
		//参展证港澳台
		List<EbsPersonnelcard> rsh1 = lit.stream().filter(item ->item.getCardtype() == cardtype && item.getCountry().equals("900000") && (item.getCity().equals("710000") || item.getCity().equals("810000") || item.getCity().equals("820000"))).collect(Collectors.toList());		
		params.put("gangaotai", rsh1.size());		
		//参展证国外
		List<EbsPersonnelcard> rsh2 = lit.stream().filter(item ->item.getCardtype() == cardtype && !item.getCountry().equals("900000")).collect(Collectors.toList());		
		params.put("guowai", rsh2.size());
		par.put("canzhanzheng", params);
		
		Map<String,Object> params1 = new HashMap<String,Object>();
		map.put("isexhibitor", "");
		map.put("chinesename", "参展证B");
		ct = cmCertificateTypeDao.findByMap(map);
		int cardtype1=ct.getId();
		List<EbsPersonnelcard> brsh = lit.stream().filter(item ->item.getCardtype() == cardtype1).collect(Collectors.toList());		
		params1.put("zongshu", brsh.size());
		//参展B证中国
		List<EbsPersonnelcard> brsh0 = lit.stream().filter(item ->item.getCardtype() == cardtype1 && item.getCountry().equals("900000")).collect(Collectors.toList());		
		params1.put("zhongguo", brsh0.size());		
		//参展证B港澳台
		List<EbsPersonnelcard> brsh1 = lit.stream().filter(item ->item.getCardtype() == cardtype1 && item.getCountry().equals("900000") && (item.getCity().equals("710000") || item.getCity().equals("810000") || item.getCity().equals("820000"))).collect(Collectors.toList());		
		params1.put("gangaotai", brsh1.size());		
		//参展证B国外
		List<EbsPersonnelcard> brsh2 = lit.stream().filter(item ->item.getCardtype() == cardtype1 && !item.getCountry().equals("900000")).collect(Collectors.toList());		
		params1.put("guowai", brsh2.size());
		par.put("canzhanzhengb", params1);
		
		Map<String,Object> params2 = new HashMap<String,Object>();
		map.put("chinesename", "嘉宾证A");
		ct = cmCertificateTypeDao.findByMap(map);
		int cardtype2=ct.getId();
		List<EbsPersonnelcard> jarsh = lit.stream().filter(item ->item.getCardtype() == cardtype2).collect(Collectors.toList());		
		params2.put("zongshu", jarsh.size());
		//嘉宾证A中国
		List<EbsPersonnelcard> jarsh0 = lit.stream().filter(item ->item.getCardtype() == cardtype2 && item.getCountry().equals("900000")).collect(Collectors.toList());		
		params2.put("zhongguo", jarsh0.size());
		
		//嘉宾证A港澳台
		List<EbsPersonnelcard> jarsh1 = lit.stream().filter(item ->item.getCardtype() == cardtype2 && item.getCountry().equals("900000") && (item.getCity().equals("710000") || item.getCity().equals("810000") || item.getCity().equals("820000"))).collect(Collectors.toList());		
		params2.put("gangaotai", jarsh1.size());
		
		//嘉宾证A国外
		List<EbsPersonnelcard> jarsh2 = lit.stream().filter(item ->item.getCardtype() == cardtype2 && !item.getCountry().equals("900000")).collect(Collectors.toList());		
		params2.put("guowai", jarsh2.size());
		par.put("jiabinzhenga", params2);
		
		Map<String,Object> params3 = new HashMap<String,Object>();
		map.put("chinesename", "采购商证");
		ct = cmCertificateTypeDao.findByMap(map);
		int cardtype3=ct.getId();
		List<EbsPersonnelcard> cgsrsh = lit.stream().filter(item ->item.getCardtype() == cardtype3).collect(Collectors.toList());		
		params3.put("zongshu", cgsrsh.size());
		//采购商证中国
		List<EbsPersonnelcard> cgsrsh0 = lit.stream().filter(item ->item.getCardtype() == cardtype3 && item.getCountry().equals("900000")).collect(Collectors.toList());		
		params3.put("zhongguo", cgsrsh0.size());
		
		//采购商证港澳台
		List<EbsPersonnelcard> cgsrsh1 = lit.stream().filter(item ->item.getCardtype() == cardtype3 && item.getCountry().equals("900000") && (item.getCity().equals("710000") || item.getCity().equals("810000") || item.getCity().equals("820000"))).collect(Collectors.toList());		
		params3.put("gangaotai", cgsrsh1.size());
		
		//采购商证国外
		List<EbsPersonnelcard> cgsrsh2 = lit.stream().filter(item ->item.getCardtype() == cardtype3 && !item.getCountry().equals("900000")).collect(Collectors.toList());		
		params3.put("guowai", cgsrsh2.size());
		par.put("caigoushang", params3);
		
		Map<String,Object> params4 = new HashMap<String,Object>();
		map.put("chinesename", "贵宾证");
		ct = cmCertificateTypeDao.findByMap(map);
		int cardtype4=ct.getId();
		List<EbsPersonnelcard> gbzrsh = lit.stream().filter(item ->item.getCardtype() == cardtype4).collect(Collectors.toList());		
		params4.put("zongshu", gbzrsh.size());
		//贵宾证中国
		List<EbsPersonnelcard> gbzrsh0 = lit.stream().filter(item ->item.getCardtype() == cardtype4 && item.getCountry().equals("900000")).collect(Collectors.toList());		
		params4.put("zhongguo", gbzrsh0.size());
		
		//贵宾证港澳台
		List<EbsPersonnelcard> gbzrsh1 = lit.stream().filter(item ->item.getCardtype() == cardtype4 && item.getCountry().equals("900000") && (item.getCity().equals("710000") || item.getCity().equals("810000") || item.getCity().equals("820000"))).collect(Collectors.toList());		
		params4.put("gangaotai", gbzrsh1.size());
		
		//贵宾证国外
		List<EbsPersonnelcard> gbzrsh2 = lit.stream().filter(item ->item.getCardtype() == cardtype4 && !item.getCountry().equals("900000")).collect(Collectors.toList());		
		params4.put("guowai", gbzrsh2.size());
		par.put("guibinzheng", params4);
		
		list.add(par);
		return list;
	}

	@Override
	public List<PurchaseIntentionStatistics> PurchaseIntentionStatisticsList(Map<String, Object> map) {
		List<PurchaseIntentionStatistics> list =  reportManagementDao.PurchaseIntentionStatisticsList(map);
		int itotal=0;
		for(int j=0;j<list.size();j++){
			itotal=itotal+list.get(j).getShuliang();
		}
		for(int k=0;k<list.size();k++){
			int total = list.get(k).getShuliang();
			if(total>0){
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
				String num = df.format(((float)total/itotal)*100);//返回的是String类型 
				list.get(k).setPercent(num);
			}
		}
		return list;
	}

	@Override
	public int PurchaseIntentionStatisticsListCount(Map<String, Object> map) {
		return reportManagementDao.PurchaseIntentionStatisticsListCount(map);
	}

	@Override
	public List<Map<String, Object>> PlaceofAttributionList(Map<String, Object> map) {
		return reportManagementDao.PlaceofAttributionList(map);
	}

	@Override
	public int PlaceofAttributionListCount(Map<String, Object> map) {
		return reportManagementDao.PlaceofAttributionListCount(map);
	}

	@Override
	public List<Map<String, Object>> ExhibitorInformationStatisticsList(Map<String, Object> map) {		
		List<Map<String, Object>> list = reportManagementDao.ExhibitorInformationStatisticsList(map);
		for(int j=0;j<list.size();j++){
			String booths = String.valueOf(list.get(j).get("booths"));
			if(!booths.equals("")){
				String[] Args = booths.split(",");
				int count=Args.length;
				list.get(j).put("mianji",count);
			}
		}
		return list;
	}

	@Override
	public int ExhibitorInformationStatisticsListCount(Map<String, Object> map) {
		return reportManagementDao.ExhibitorInformationStatisticsListCount(map);
	}

	@Override
	public List<Map<String, Object>> QuestionnaireManagementList(Map<String, Object> map) {
		return reportManagementDao.QuestionnaireManagementList(map);
	}

	@Override
	public int QuestionnaireManagementListCount(Map<String, Object> map) {
		return reportManagementDao.QuestionnaireManagementListCount(map);
	}

	@Override
	public List<Map<String, Object>> HeadofTradingGroupList(Map<String, Object> map) {
		return reportManagementDao.HeadofTradingGroupList(map);
	}

	@Override
	public int HeadofTradingGroupListCount(Map<String, Object> map) {
		return reportManagementDao.HeadofTradingGroupListCount(map);
	}

	@Override
	public List<Map<String, Object>> PurchaseIntentionStatisticsListForDownLoad(Map<String, Object> map) {
		List<Map<String, Object>> list = reportManagementDao.PurchaseIntentionStatisticsListForDownLoad(map);
		int itotal=0;
		for(int j=0;j<list.size();j++){
			itotal=itotal+Integer.valueOf(String.valueOf(list.get(j).get("shuliang")));
		}
		for(int k=0;k<list.size();k++){
			int total = Integer.valueOf(String.valueOf(list.get(k).get("shuliang")));
			if(total>0){
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
				String num = df.format(((float)total/itotal)*100);//返回的是String类型 
				list.get(k).put("percent", num.equals("0") ? "0" : num+"%");
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> ExportForeignGuestsList(Map<String, Object> map) {
		return ebsPersonnelcardDao.ExportForeignGuestsList(map);
	}

	@Override
	public int ExportForeignGuestsListCount(Map<String, Object> map) {
		return ebsPersonnelcardDao.ExportForeignGuestsListCount(map);
	}

	@Override
	public List<Map<String, Object>> getEnterpriseList(Map<String, Object> map) {
		return reportManagementDao.getEnterpriseList(map);
	}

	@Override
	public List<Map<String, Object>> getShowRoomList(Map<String, Object> map) {
		return reportManagementDao.getShowRoomList(map);
	}
}
