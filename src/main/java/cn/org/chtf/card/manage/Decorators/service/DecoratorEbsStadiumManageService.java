package cn.org.chtf.card.manage.Decorators.service;

import java.util.List;
import java.util.Map;

/**
 * 搭建商报馆信息Service
 *
 * @author ggwudivs
 */
public interface DecoratorEbsStadiumManageService {

    List<Map<String, Object>> list(Map<String, Object> map);

    int listcount(Map<String, Object> map);

    int updateStadiumInfo(Map<String, Object> map);

    Map<String, Object> selectStadiumInfo(Map<String, Object> map);

    String downloadAttachment(Map<String, Object> map);
}
