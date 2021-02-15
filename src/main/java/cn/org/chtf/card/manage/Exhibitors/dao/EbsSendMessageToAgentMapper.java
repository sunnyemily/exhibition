package cn.org.chtf.card.manage.Exhibitors.dao;

import java.util.List;
import java.util.Map;


/**
 * 短信通知参展商DAO
 * @author ggwudivs
 */
public interface EbsSendMessageToAgentMapper {

    List<Map<String, Object>> list(Map<String, Object> map);

    int listcount(Map<String, Object> map);

}
