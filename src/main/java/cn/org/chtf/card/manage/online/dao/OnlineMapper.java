package cn.org.chtf.card.manage.online.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.online.model.ExhibitionInfoModel;
import cn.org.chtf.card.manage.online.model.ExhibitorModel;
import cn.org.chtf.card.manage.online.model.OnlineReservationDetails;
import cn.org.chtf.card.manage.online.model.ProductModel;
import cn.org.chtf.card.manage.online.model.ReservationModel;
import cn.org.chtf.card.manage.online.model.WeatherInfo;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.manage.wechatuser.pojo.WechatUser;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 线上预约DAO
 * @author lm
 */
public interface OnlineMapper {

	List<ReservationModel> GetAvailableDates(Map<String, Object> map);

	List<ExhibitionInfoModel> GetExhibitionInfo(Map<String, Object> map);

	Map<String, Object> getWeatherInfo(Map<String, Object> exhibitionInfo);

	void insertWeatherInfo(WeatherInfo weatherInfo);

	Map<String, Object> GetBacicInfo(int menuid);

	List<Map<String, Object>> GetFriendLink(int menuid);

	int isExistsByCardNumber(Map<String, Object> map);

	List<Map<String, Object>> GetReceipt(Map<String, Object> param);

	Map<String, Object> GetTicketManage(String type);

	void insertTicketInfo(Map<String, Object> result);

	List<Map<String, Object>> GetTopTradingGroups(@Param("type") String type, @Param("itop") String itop);

	List<Map<String, Object>> GetAppointmentInformation(Map<String, Object> page);

	int GetAppointmentInformationCount(Map<String, Object> page);

	String GetTicketNumber(String pid);

	List<Map<String, Object>> GetCompanyList(Map<String, Object> map);

	List<Map<String, Object>> GetProductList(Map<String, Object> map);

	void updateZT(@Param("hao") String hao, @Param("mi") String mi);

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

	List<Map<String, Object>> GettopLikes(Map<String, Object> map);

	List<Map<String, Object>> GetMyFavorites(PageModel page);

	int GetMyFavoritesCount(PageModel page);

	void UnFavorite(@Param("fid") Integer fid);

	WechatUser login(Map<String, Object> token);

	WechatUser getWechatUser(Map<String, Object> token);

	void updateInfo(Map<String, Object> map);

	List<Map<String, Object>> GetCompanyLink(@Param("fid") Integer fid);

	List<Map<String, Object>> getActivityList(Map<String, Object> exhibitionInfo);

	List<Map<String, Object>> GetActivityTime(Map<String, Object> exhibitionInfo);

	List<Map<String, Object>> GetActivityDetails(Map<String, Object> map);

	List<Map<String, Object>> MyReceived(PageModel page);

	int MyReceivedCount(PageModel page);

	List<Map<String, Object>> MyPostInfo(PageModel page);

	int MyPostInfoCount(PageModel page);

	Map<String, Object> GetZhiBoQingKuang(@Param("companyid") Integer companyid);

	List<Map<String, Object>> GetTopActivity(Map<String, Object> exhibitionInfo);

	void updateWechatUserInfo(Map<String, Object> map);

	List<Map<String, Object>> GetCanZhanZhengRenYuan(Map<String, Object> map);

	int getWechatUserByphone(Map<String, Object> map);

	List<Map<String,Object>> GetGongCaiList(PageModel pageModel);

	int GetGongCaiListCount(PageModel pageModel);

	List<Map<String, Object>> MeetingList(PageModel page);

	int MeetingListCount(PageModel page);

	String GetMeetingPass(Integer id);

	List<ExhibitionInfoModel> GetDicForExhibition();

	String GetPassByMeetingNumAndPhone(Map<String, Object> map);

	Object GetHostUid(Map<String, Object> map);

	String GetNowPassword(@Param("uid") int uid, @Param("phone") String phone);

	void UpdateWechatUserPass(@Param("uid") int uid, @Param("pass")String pass);

}
