package cn.org.chtf.card.manage.online.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.friendlink.pojo.Friendlink;
import cn.org.chtf.card.manage.online.model.ExhibitionInfoModel;
import cn.org.chtf.card.manage.online.model.ExhibitorModel;
import cn.org.chtf.card.manage.online.model.NewsParams;
import cn.org.chtf.card.manage.online.model.OnlineNegotiate;
import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;
import cn.org.chtf.card.manage.online.model.ProductModel;
import cn.org.chtf.card.manage.online.model.ReservationModel;
import cn.org.chtf.card.manage.product.model.WebProduct;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;


/**
 * 线上预约Service
 * @author lm
 */
public interface OnlineService {

	/**
	 * 预约开放时间
	 * @param map
	 * @return
	 */
	List<ReservationModel> GetAvailableDates(Map<String, Object> map);

	/**
	 * 展区信息
	 * @param map
	 * @return
	 */
	List<ExhibitionInfoModel> GetExhibitionInfo(Map<String, Object> map);

	/**
	 * 天气信息
	 * @param exhibitionInfo
	 * @return
	 * @throws IOException
	 */
	Map<String, Object> getWeatherInfo(Map<String, Object> exhibitionInfo) throws IOException;

	/**
	 * 首页banner
	 * @param menuid
	 * @return
	 */
	Map<String, Object> GetBacicInfo(int menuid);

	List<Map<String, Object>> GetFriendLink(int menuid);

	int isExistsByCardNumber(Map<String, Object> map);

	List<Map<String, Object>> GetReceipt(Map<String, Object> param);

	Map<String, Object> GetTicketManage(String type);

	void insertTicketInfo(Map<String, Object> result);

	List<Map<String, Object>> GetTopTradingGroups(String type, String itop);

	ResultModel GetAppointmentInformation(NewsParams news);

	String GetTicketNumber(String pid);

	List<Map<String, Object>> GetCompanyList(Map<String, Object> map);

	List<Map<String, Object>> GetProductList(Map<String, Object> map);

	void updateZT(String hao, String mi);

	List<Map<String, Object>> GetHaoMa();

	List<ExhibitorModel> getExhibitiorList(PageModel pageModel);

	int getExhibitiorListCount(PageModel pageModel);

	int getTodayCountByCardNumber(OnlineReservationDetails ord);

	void updateWechatUser(Map<String, Object> par);

	List<ProductModel> getProductList(PageModel pageModel);

	int getProductListCount(PageModel pageModel);

	List<Map<String, Object>> GetProductMenus(Map<String, Object> exhibitionInfo);

	void addFavorites(Map<String, Object> map);

	void delFavorites(Map<String, Object> map);

	int GetSCCount(Map<String, Object> map);

	void addAwesome(Map<String, Object> map);

	void delAwesome(Map<String, Object> map);

	int GetDZCount(Map<String, Object> map);

	void updateCompany(Map<String, Object> map);

	void updateProduct(Map<String, Object> map);

	List<Map<String, Object>> GetOnlineInquiry(Map<String, Object> map);

	List<Map<String, Object>> GettopLikes(Map<String, Object> mapx);

	List<Map<String, Object>> GetMyFavorites(PageModel page);

	int GetMyFavoritesCount(PageModel page);

	void UnFavorite(Integer fid);

	ResultModel login(String phone, String password, HttpSession session);

	ResultModel CheckReg(String phone);

	ResultModel sendConsolePhoneCode(String phone, HttpSession session, String sessionId);

	ResultModel reg(String phone, String password, String vCode, HttpSession session);

	void updateInfo(Map<String, Object> map);

	List<Map<String, Object>> GetCompanyLink(int i);

	List<Map<String, Object>> getActivityList(Map<String, Object> exhibitionInfo);

	List<Map<String, Object>> GetActivityTime(Map<String, Object> exhibitionInfo);

	List<Map<String, Object>> GetActivityDetails(Map<String, Object> map);

	List<Map<String, Object>> MyReceived(PageModel page);

	int MyReceivedCount(PageModel page);

	List<Map<String, Object>> MyPostInfo(PageModel page);

	int MyPostInfoCount(PageModel page);

	Map<String,Object> GetZhiBoQingKuang(Integer companyid);

	List<Map<String, Object>> GetTopActivity(Map<String, Object> exhibitionInfo);

	boolean UserActivation(Map<String, Object> map);

	void SendSMSAndMessage(OnlineNegotiate oi, WechatUser userx) throws Exception;

	int getWechatUser(Map<String, Object> map);

	List<Map<String,Object>> GetGongCaiList(PageModel pageModel);

	int GetGongCaiListCount(PageModel pageModel);

	List<Map<String, Object>> MeetingList(PageModel page);

	int MeetingListCount(PageModel page);

	String GetMeetingPass(Integer id);

	List<ExhibitionInfoModel> GetDicForExhibition();

	Boolean VerifyPassword(Map<String, Object> map);

	Integer GetHostUid(Map<String, Object> map);

	ResultModel ChangePass(String oldpass, String pass, String confirmpass,int uid,String phone);

	
}
