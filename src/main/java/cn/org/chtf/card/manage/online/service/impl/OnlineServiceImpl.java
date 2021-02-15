package cn.org.chtf.card.manage.online.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.online.dao.OnlineMapper;
import cn.org.chtf.card.manage.online.model.ExhibitionInfoModel;
import cn.org.chtf.card.manage.online.model.ExhibitorModel;
import cn.org.chtf.card.manage.online.model.NewsParams;
import cn.org.chtf.card.manage.online.model.OnlineNegotiate;
import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;
import cn.org.chtf.card.manage.online.model.ProductModel;
import cn.org.chtf.card.manage.online.model.ReservationModel;
import cn.org.chtf.card.manage.online.model.WeatherInfo;
import cn.org.chtf.card.manage.online.service.OnlineService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.http.HttpUtil;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;
import cn.org.chtf.card.support.util.wechat.wechatApi;

/**
 * 线上预约ServiceImpl
 * @author lm
 */
@Service
public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private OnlineMapper onlineDao;
    
    @Autowired
	private wechatApi wechat;
    
    @Autowired
	private EbsCompanyinfoMapper ebsCompanyinfoDao;
    
    @Autowired
	private SysSmsTemplateService smsService;
    
    @Autowired
	private StringRedisTemplate redisTemplate;

    /**
	 * 预约开放时间
	 * @param map
	 * @return
	 */
    @Override
	public List<ReservationModel> GetAvailableDates(Map<String, Object> map) {
    	List<ReservationModel> list = onlineDao.GetAvailableDates(map);
    	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    	String nowtime = formater.format(new Date());
    	for(int j=0;j<list.size();j++){
    		String zhdate = list.get(j).getExhibitiondate();
    		//当天预约
    		if(nowtime.equals(zhdate)){
    			int dangtiantotal = list.get(j).getDangtiantotal();
    			int dangtiancount = list.get(j).getDangtiancount();
    			if(dangtiantotal-dangtiancount>0){
    				list.get(j).setUseable(1);
    			}
    		}
    		//提前预约
    		if(nowtime.compareTo(zhdate)<0){
    			int tiqiancount = list.get(j).getTiqiancount();
    			int onlinevotes = list.get(j).getOnlinevotes();
    			if(onlinevotes-tiqiancount>0){
    				list.get(j).setUseable(1);
    			}
    		}
    	}
    	return list;
	}

    /**
	 * 展区信息
	 * @param map
	 * @return
	 */
	@Override
	public List<ExhibitionInfoModel> GetExhibitionInfo(Map<String, Object> map) {
		return onlineDao.GetExhibitionInfo(map);
	}

	/**
	 * 获取天气信息
	 * @throws IOException 
	 */
	@Override
	public Map<String, Object> getWeatherInfo(Map<String, Object> exhibitionInfo) throws IOException {
		Map<String, Object> map =  new HashMap<String,Object>();
		map = onlineDao.getWeatherInfo(exhibitionInfo);
		if(map==null){
			WeatherInfo WeatherInfo = HttpUtil.Weather(exhibitionInfo.get("cityid").toString());
			onlineDao.insertWeatherInfo(WeatherInfo);	
			Map<String,Object> par = new HashMap<String,Object>();
			par.put("days",WeatherInfo.getDays());
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日"); //制定输出格式
			Date d = new Date();
			par.put("monthday", sdf.format(d));
			par.put("week",WeatherInfo.getWeek());
			par.put("citynm",WeatherInfo.getCitynm());
			par.put("cityid",WeatherInfo.getCityid());
			par.put("temperature",WeatherInfo.getTemperature());
			par.put("temperature_curr",WeatherInfo.getTemperature_curr());
			par.put("weather",WeatherInfo.getWeather());
			par.put("weather_curr",WeatherInfo.getWeather_curr());
			par.put("weather_icon",WeatherInfo.getWeather_icon());
			par.put("wind",WeatherInfo.getWind());
			par.put("winp",WeatherInfo.getWinp());
			map=par;
		}
		return map;
	}

	/**
	 * 首页banner
	 */
	@Override
	public Map<String, Object> GetBacicInfo(int menuid) {
		return onlineDao.GetBacicInfo(menuid);
	}

	/**
	 * 友情链接
	 */
	@Override
	public List<Map<String, Object>> GetFriendLink(int menuid) {
		return onlineDao.GetFriendLink(menuid);
	}

	@Override
	public int isExistsByCardNumber(Map<String, Object> map) {
		return onlineDao.isExistsByCardNumber(map);
	}

	@Override
	public List<Map<String, Object>> GetReceipt(Map<String, Object> param) {
		return onlineDao.GetReceipt(param);
	}

	@Override
	public Map<String, Object> GetTicketManage(String type) {
		return onlineDao.GetTicketManage(type);
	}

	@Override
	public void insertTicketInfo(Map<String, Object> result) {
		onlineDao.insertTicketInfo(result);
	}

	/**
	 * 获取线上交易团
	 * @param type
	 * @param itop
	 * @return
	 */
	@Override
	public List<Map<String, Object>> GetTopTradingGroups(String type, String itop) {
		return onlineDao.GetTopTradingGroups(type,itop);
	}

	/**
	 * 获取用户预约信息
	 */
	@Override
	public ResultModel GetAppointmentInformation(NewsParams news) {
		Map<String, Object> page = new HashMap<String,Object>();
		page.put("page", news.getPage());
		page.put("limit", news.getLimit());
		page.put("session", news.getSession());
		page.put("uid", news.getUid());
		page = ResultVOUtil.TiaoZhengFenYe(page);
		List<Map<String,Object>> list = onlineDao.GetAppointmentInformation(page);
		int iCount = onlineDao.GetAppointmentInformationCount(page);
		
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,list,iCount);
	}

	@Override
	public String GetTicketNumber(String pid) {
		return onlineDao.GetTicketNumber(pid);
	}

	/**
	 * 随机优质展商
	 */
	@Override
	public List<Map<String, Object>> GetCompanyList(Map<String, Object> map) {
		return onlineDao.GetCompanyList(map);
	}

	/**
	 * 随机优质展品
	 */
	@Override
	public List<Map<String, Object>> GetProductList(Map<String, Object> map) {
		return onlineDao.GetProductList(map);
	}

	
	@Override
	public void updateZT(String hao, String mi) {
		onlineDao.updateZT(hao,mi);
	}

	@Override
	public List<Map<String, Object>> GetHaoMa() {
		return onlineDao.GetHaoMa();
	}

	@Override
	public List<ExhibitorModel> getExhibitiorList(PageModel pageModel) {		
		List<ExhibitorModel> list = onlineDao.getExhibitiorList(pageModel);
		Map<String,Object> par = new HashMap<String,Object>();		
		par.put("session", pageModel.getSession());
		for(int j=0;j<list.size();j++){
			par.put("companyid", list.get(j).getCompanyid());
			List<Map<String,Object>> lit = ebsCompanyinfoDao.GetCompanyBooth(par);
			String result = StringUtil.GetZhanWeiPinJie(lit);
			list.get(j).setZhanweihao(result);
		}
		return list;
	}

	@Override
	public int getExhibitiorListCount(PageModel pageModel) {
		int iCount = onlineDao.getExhibitiorListCount(pageModel);		
		return iCount;
	}

	@Override
	public int getTodayCountByCardNumber(OnlineReservationDetails ord) {
		return onlineDao.getTodayCountByCardNumber(ord);
	}

	@Override
	public void updateWechatUser(Map<String, Object> par) {
		onlineDao.updateWechatUser(par);
	}

	@Override
	public List<ProductModel> getProductList(PageModel pageModel) {
		return onlineDao.getProductList(pageModel);
	}

	@Override
	public int getProductListCount(PageModel pageModel) {
		return onlineDao.getProductListCount(pageModel);
	}

	@Override
	public List<Map<String, Object>> GetProductMenus(
			Map<String, Object> exhibitionInfo) {
		return onlineDao.GetProductMenus(exhibitionInfo);
	}

	@Override
	public void addFavorites(Map<String, Object> map) {
		onlineDao.addFavorites(map);
	}

	@Override
	public void delFavorites(Map<String, Object> map) {
		onlineDao.delFavorites(map);
	}

	@Override
	public int GetSCCount(Map<String, Object> map) {
		return onlineDao.GetSCCount(map);
	}

	@Override
	public void addAwesome(Map<String, Object> map) {
		onlineDao.addAwesome(map);		
	}

	@Override
	public void delAwesome(Map<String, Object> map) {
		onlineDao.delAwesome(map);
	}

	@Override
	public int GetDZCount(Map<String, Object> map) {
		return onlineDao.GetDZCount(map);
	}

	@Override
	public void updateCompany(Map<String, Object> map) {
		onlineDao.updateCompany(map);
	}

	@Override
	public void updateProduct(Map<String, Object> map) {
		onlineDao.updateProduct(map);
	}

	@Override
	public List<Map<String, Object>> GetOnlineInquiry(Map<String, Object> map) {
		return onlineDao.GetOnlineInquiry(map);
	}

	@Override
	public List<Map<String, Object>> GettopLikes(Map<String, Object> map) {
		return onlineDao.GettopLikes(map);
	}

	@Override
	public List<Map<String, Object>> GetMyFavorites(PageModel page) {
		List<Map<String, Object>> list = onlineDao.GetMyFavorites(page);
		if(page.getAct()==0){
			Map<String,Object> par = new HashMap<String,Object>();
			par.put("session", page.getSession());
			for(int j=0;j<list.size();j++){
				par.put("companyid", list.get(j).get("companyId"));			
				List<Map<String,Object>> lit = ebsCompanyinfoDao.GetCompanyBooth(par);
				String result = StringUtil.GetZhanWeiPinJie(lit);
				list.get(j).put("num", result);
			}
		}
		return list;
	}

	@Override
	public int GetMyFavoritesCount(PageModel page) {
		return onlineDao.GetMyFavoritesCount(page);
	}

	@Override
	public void UnFavorite(Integer fid) {
		onlineDao.UnFavorite(fid);
	}

	@Override
	public ResultModel login(String phone, String password, HttpSession session) {
		Map<String,Object> token = new HashMap<String,Object>();
		 ResultModel result = new ResultModel();		
		//String sessionCode = (String) session.getAttribute(SMSUtil.CONSOLE_LOGIN_CODEKEY);
		/*   lm  注释  2020-05-19*/
		/*if(null==vCode||!sessionCode.equals(vCode)) {
			result = new ResultModel(WConst.ERROR,"验证码不正确",session.getAttribute(SMSUtil.CONSOLE_LOGIN_CODEKEY));
			return result;
		}*/
		 if(StringUtil.isEmpty(phone)|| StringUtil.isEmpty(password)) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.LOGINERROR);
			 return result;
		 }
		 token.put("phone", phone);
		 token.put("password",CryptographyUtil.md5(password, phone));
		 try{
			 WechatUser wechatUser = onlineDao.login(token);
			 if(wechatUser==null) {
				 result.setStatus(WConst.ERROR);
				 result.setMsg(WConst.LOGINERROR);			 
			 }
			 else {			
				 //添加用户权限和角色
				 //user.setPermissions(functionDAO.getAuthorizedFunctions(username));
				 result.setStatus(WConst.SUCCESS);
				 result.setMsg(WConst.CERTIFICATED);
				 result.setResult(wechatUser);
				 session.setAttribute("wechatUser", wechatUser);
				 //登陆成功，登陆失败次数清零
				 //if(user.getFailcount()>0)
				 //userDAO.clearFailCount(username);
			 }
			 return result;
		 }
		 catch(Exception ee){
			 result.setStatus(WConst.ERROR);
			 result.setMsg("账号异常");
			 return result;
		 }		 
	}

	@Override
	public ResultModel CheckReg(String phone) {
		ResultModel result = new ResultModel();	
		Map<String,Object> token = new HashMap<String,Object>();
		token.put("phone", phone);		 
		WechatUser wechatUser = onlineDao.login(token);
		if(wechatUser==null){
			 result.setStatus(WConst.SUCCESS);
			 result.setMsg("");
		}
		else{
			 result.setStatus(WConst.ERROR);
			 result.setMsg("输入的手机号已存在");
		}
		return result;
	}

	@Override
	public ResultModel sendConsolePhoneCode(String phone, HttpSession session, String sessionId) {
		Map<String,Object> token = new HashMap<String,Object>();
		 ResultModel result = new ResultModel();		
		 if(StringUtil.isEmpty(phone)) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg(WConst.LOGINERROR);
			 return result;
		 }
		 
		WechatUser wechatUser = onlineDao.login(token);
		if(wechatUser!=null){
			result.setStatus(WConst.ERROR);
			result.setMsg("输入的手机号已存在");
			return result;
		}
		 
		 String randomString = TencentSMSUtil.getRandomString();//SMSUtil.getRandomString();
		  //将生成的随机字符串保存到session中
		 session.removeAttribute(SMSUtil.ONLINE_REGIST_CODEKEY);
		 session.setAttribute(SMSUtil.ONLINE_REGIST_CODEKEY,randomString);
		 try {			 
			if(smsService.sendRegistValidateSMS(randomString, phone, Integer.valueOf(sessionId))) {
				 result.setStatus(WConst.SUCCESS);
				 result.setMsg("短信发送成功。");
			 }
			else {

				 result.setStatus(WConst.ERROR);
				 result.setMsg(WConst.LOGINERROR);
			}
		} catch (Exception e) {
			 result.setStatus(WConst.ERROR);
			 result.setMsg("短信发送失败");
			 result.setResult(e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultModel reg(String phone, String password, String vCode, HttpSession session) {
		ResultModel result = new ResultModel();	
		String sessionCode = (String) session.getAttribute(SMSUtil.ONLINE_REGIST_CODEKEY);
		if(null==vCode||!sessionCode.equals(vCode)) {
			result = new ResultModel(WConst.ERROR,"验证码不正确",session.getAttribute(SMSUtil.ONLINE_REGIST_CODEKEY));
			return result;
		}
		SetRedisKey(phone, password);
		result.setStatus(WConst.SUCCESS);
		result.setMsg("");
		return result;
	}
	
	public void SetRedisKey(String phone, String password) {
		password = CryptographyUtil.md5(password, phone);
	    //redisTemplate.opsForList().rightPush(phone, password);//存入Redis	
	    redisTemplate.opsForValue().set(phone, password);
	    //redisTemplate.opsForValue().get("name");
	}

	@Override
	public void updateInfo(Map<String, Object> map) {
		onlineDao.updateInfo(map);
	}

	@Override
	public List<Map<String, Object>> GetCompanyLink(int fid) {
		return onlineDao.GetCompanyLink(fid);
	}

	@Override
	public List<Map<String, Object>> getActivityList(
			Map<String, Object> exhibitionInfo) {
		return onlineDao.getActivityList(exhibitionInfo);
	}

	@Override
	public List<Map<String, Object>> GetActivityTime(
			Map<String, Object> exhibitionInfo) {
		return onlineDao.GetActivityTime(exhibitionInfo);
	}

	@Override
	public List<Map<String, Object>> GetActivityDetails(Map<String, Object> map) {
		return onlineDao.GetActivityDetails(map);
	}

	@Override
	public List<Map<String, Object>> MyReceived(PageModel page) {
		return onlineDao.MyReceived(page);
	}

	@Override
	public int MyReceivedCount(PageModel page) {
		return onlineDao.MyReceivedCount(page);
	}

	@Override
	public List<Map<String, Object>> MyPostInfo(PageModel page) {
		return onlineDao.MyPostInfo(page);
	}

	@Override
	public int MyPostInfoCount(PageModel page) {
		return onlineDao.MyPostInfoCount(page);
	}

	@Override
	public Map<String, Object> GetZhiBoQingKuang(Integer companyid) {
		return onlineDao.GetZhiBoQingKuang(companyid);
	}

	@Override
	public List<Map<String, Object>> GetTopActivity(
			Map<String, Object> exhibitionInfo) {
		return onlineDao.GetTopActivity(exhibitionInfo);
	}

	@Override
	public boolean UserActivation(Map<String, Object> map) {
		String mima = redisTemplate.opsForValue().get(map.get("phone").toString());
		if(mima==null || mima.equals("")){
			return false;
		}
		map.put("password", mima);
		onlineDao.updateWechatUserInfo(map);
		return true;
	}

	@Override
	public void SendSMSAndMessage(OnlineNegotiate ont, WechatUser user) throws Exception {
		if(user==null){//未认证
        	int companyid=ont.getCompanyid();
        	int sessionid = ont.getSession();
        	Map<String,Object> map =  new HashMap<String,Object>();
        	map.put("companyid", companyid);
        	map.put("sessionid", sessionid);
        	List<Map<String,Object>> list = onlineDao.GetCanZhanZhengRenYuan(map);
        	for(int j=0;j<list.size();j++){
        		String phone = list.get(j).get("phone").toString();
        		String name = list.get(j).get("name").toString();
        		try{
        			if(!phone.equals("")){
        				boolean b = smsService.sendMessageForOnline(phone,name,ont.getSession());
        			}
        		}
        		catch(Exception ee){}
        	}
        }
        else{//已认证
        	String phone = user.getPhone();
        	
        	try{
        		//发送短信
        		boolean b = smsService.sendMessageForOnline(phone,user.getName(),ont.getSession());
        	}
    		catch(Exception ee){}
        	
        	//发送微信消息
        	Map<String,Object> postData = new HashMap<String,Object>();

    		//20个以内字符，多了会发不出去
    		Map<String,Object> thing1Value = new HashMap<String,Object>();
    		thing1Value.put("value", user.getNickname());
    		postData.put("thing1", thing1Value);

    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    		
    		Map<String,Object> time2Value = new HashMap<String,Object>();
    		time2Value.put("value", df.format(new Date()));
    		postData.put("time2", time2Value);
    		
    		Map<String,Object> thing3Value = new HashMap<String,Object>();
    		//20个以内字符，多了会发不出去
    		String neirong = ont.getContent();
    		if(ont.getContent().length()>16){
    			neirong = neirong.substring(0,16)+"...";
    		}
    		thing3Value.put("value", neirong);
    		postData.put("thing3", thing3Value);
    		wechat.subscribeMessageSend(user.getRoutineOpenid(), wechatApi.NEW_MESSAGE_TEMPLATE_ID, "pages/user/message/message", postData);
        }
	}

	@Override
	public int getWechatUser(Map<String, Object> map) {
		return onlineDao.getWechatUserByphone(map);
	}

	@Override
	public List<Map<String,Object>> GetGongCaiList(PageModel pageModel) {
		return onlineDao.GetGongCaiList(pageModel);
	}

	@Override
	public int GetGongCaiListCount(PageModel pageModel) {
		return onlineDao.GetGongCaiListCount(pageModel);
	}

	@Override
	public List<Map<String, Object>> MeetingList(PageModel page) {
		return onlineDao.MeetingList(page);
	}

	@Override
	public int MeetingListCount(PageModel page) {
		return onlineDao.MeetingListCount(page);
	}

	@Override
	public String GetMeetingPass(Integer id) {
		return onlineDao.GetMeetingPass(id);
	}

	@Override
	public List<ExhibitionInfoModel> GetDicForExhibition() {
		return onlineDao.GetDicForExhibition();
	}

	@Override
	public Boolean VerifyPassword(Map<String, Object> map) {
		Boolean result = false;
		String Pass = onlineDao.GetPassByMeetingNumAndPhone(map);
		if(Pass!=null && Pass.equals(map.get("pass"))){
			result = true;
		}
		return result;
	}

	@Override
	public Integer GetHostUid(Map<String, Object> map) {
		Object obj = onlineDao.GetHostUid(map);
		if(obj==null){
			return 0;
		}
		return Integer.valueOf(obj.toString());
	}

	@Override
	public ResultModel ChangePass(String oldpass, String pass, String confirmpass,int uid, String phone) {
		if(!pass.equals(confirmpass)){
			return new ResultModel(WConst.ERROR,"新密码与确认密码必须一致",null);
		}
		String nowPass = onlineDao.GetNowPassword(uid,phone);
		if(!CryptographyUtil.md5(oldpass, phone).equals(nowPass)){
			return new ResultModel(WConst.ERROR,"旧密码错误，请重试",null);
		}
		onlineDao.UpdateWechatUserPass(uid,CryptographyUtil.md5(pass, phone));
		return new ResultModel(WConst.SUCCESS,"修改成功",null);
	}
    

}
