package cn.org.chtf.card.manage.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.org.chtf.card.manage.system.model.SysSmsTemplate;

/**
 * Service
 * @author lm
 */
public interface SysSmsTemplateService {

	/**
	 * 查询列表
	 */
	List<SysSmsTemplate> list(Map<String, Object> map);

	/**
	 * 通过sms_id查询单个
	 */
    SysSmsTemplate findById(Integer smsId);

	/**
	 * 通过map查询单个
	 */
	List<SysSmsTemplate> findByMap(Map<String, Object> map);

	/**
	 * 新增
	 */
	int save(SysSmsTemplate sysSmsTemplate);

	/**
	 * 修改
	 */
	int update(SysSmsTemplate sysSmsTemplate);

	/**
	 * 删除
	 */
	int deleteById(Integer smsId);
	
	int listcount(Map<String, Object> map);
	

    /**
     * 发送注册短信验证码
     * @param phone 接收人手机号
     * @param sessionId 届次id
     * @param session session
     * @return
     * @throws Exception 
     */
    public Boolean sendRegistValidateSMS(String phone,String companyName,Integer sessionId,HttpSession session) throws Exception;
    	
    /**
     * 发送注册成功短信
     * @param phone 接收人手机号码
     * @param companyName 注册公司名称
     * @param userName 用户名
     * @param password 密码
     * @param sessionId 届次id
     * @param memberType 会员类型
     * @return
     * @throws Exception
     */
    public Boolean sendRegistSucessSMS(String phone,String companyName,String userName,String password,Integer sessionId, Integer memberType) throws Exception;
    /**
     * @Description: 发送展位分配短信
     * @date: 2020年8月11日 下午3:17:37
     * @param phone 接收人手机号码
     * @param companyName 公司名称
     * @param boothNO 展位号
     * @param sessionId  届次id
     * @param type  发送短信类型（“零散参展商”，“交易团”，“记者”）
     * @throws Exception:
     * @return: Boolean
     */
    public Boolean sendBoothConfirmSMS(String phone,String companyName,String boothNO,Integer sessionId,String type) throws Exception;
    /**
     * 发送取证报表短信
     * @param phone 接收人手机号码
     * @param companyName 注册公司名称
     * @param userName 用户名
     * @param password 密码
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    public Boolean sendTakeReportSMS(String phone,String companyName,Integer sessionId) throws Exception;
    /**
     * 发送代办员开通短信
     * @param phone 接收人手机号码
     * @param companyName 注册公司名称
     * @param userName 用户名
     * @param password 密码
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    public Boolean sendOpenAgentSMS(String phone,String agentName,String userName,String password,Integer sessionId, Integer iType) throws Exception;
    /**
     * 发送零散转交易团短信通知
     * @param phone 接收人手机号
     * @param companyName 公司名称
     * @param traddingGroupName 交易团名称
     * @param sessionId 届次id
     * @return
     * @throws Exception
     */
    public Boolean sendExhibitionToTraddingGroupSMS(String phone,String companyName,String traddingGroupName,String tradingGroupContactPerson,String tradingGroupContactPhone,Integer sessionId) throws Exception;

	/**
	 * @param number 接收号码
	 * @param smsContent 发送内容
	 * @param receiver 接收人
	 * @param type 发送短信类型
	 * @return
	 */
    public Boolean sendMessage(String number, String smsContent, String receiver, String type) throws Exception;

	/**
	 * 发送后台验证码
	 * @param safeCode 验证码
	 * @param phone 接收人手机号
	 * @param traddingGroupName 交易团名称
	 * @param sessionId 届次id
	 * @return
	 * @throws Exception
	 */
	Boolean sendConsoleValidateSMS(String safeCode, String phone, Integer sessionId) throws Exception;

	/**
	 * 发送后台验证码
	 * @param safeCode 验证码
	 * @param phone 接收人手机号
	 * @param traddingGroupName 交易团名称
	 * @param sessionId 届次id
	 * @return
	 * @throws Exception
	 */
	Boolean sendFindPasswordSMS(String companyName, String passwordCode, String phone, Integer sessionId)
			throws Exception;

	boolean sendRegistValidateSMS(String randomString, String phone,Integer sessionId) throws Exception;

	boolean sendMessageForOnline(String phone, String name, Integer sessionId) throws Exception;
}
