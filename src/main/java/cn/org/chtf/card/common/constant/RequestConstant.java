package cn.org.chtf.card.common.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RequestConstant {

    @Value("${exhibition.hljlbh.mapping.request}")
    private String hljlbhRequest;

    @Value("${exhibition.hljlbh.mapping.domain}")
    private String hljlbhDomain;

    @Value("${exhibition.chtf.mapping.request}")
    private String chtfRequest;

    @Value("${exhibition.chtf.mapping.domain}")
    private String chtfDomain;

    @Value("${exhibition.hljhzw.mapping.request}")
    private String hljhzwRequest;

    @Value("${exhibition.hljhzw.mapping.domain}")
    private String hljhzwDomain;

    /**
     * 搭建商资质审核截止时间
     */
    public static final Integer QUALIFICATION_REVIEW_END_DATE_TYPE = 1;
    public static final String QUALIFICATION_REVIEW_END_DATE_URL = "/decorator/api/qualification_review_end_date";

    /**
     * 搭建商办证截止时间
     */
    public static final Integer APPLY_CERTIFICATES_END_DATE_TYPE = 2;
    public static final String APPLY_CERTIFICATES_END_DATE_URL = "/decorator/api/apply_certificates_end_date";

    /**
     * 报馆申请时间
     */
    public static final Integer STADIUM_DECORATOR_END_DATE_TYPE = 3;
    public static final String STADIUM_DECORATOR_END_DATE_URL = "/decorator/api/stadium_decorator_end_date";

    /**
     * 展商列表
     */
    public static final Integer EXHIBITOR_LIST_TYPE = 4;
    public static final String EXHIBITOR_LIST_URL = "/decorator/api/exhibitor_list?index=1&size=10000";

    /**
     * 展商信息
     */
    public static final Integer EXHIBITOR_INFO_TYPE = 5;
    public static final String EXHIBITOR_INFO_URL = "/decorator/api/exhibitor_info";

    public static final Map<String, String> requestMap = new HashMap<>();
    public static final Map<Integer, String> urlMap = new HashMap<>();

    @PostConstruct
    public void init() {
        requestMap.put(hljlbhRequest, hljlbhDomain);
        requestMap.put(chtfRequest, chtfDomain);
        requestMap.put(hljhzwRequest, hljhzwDomain);

        urlMap.put(QUALIFICATION_REVIEW_END_DATE_TYPE, QUALIFICATION_REVIEW_END_DATE_URL);
        urlMap.put(APPLY_CERTIFICATES_END_DATE_TYPE, APPLY_CERTIFICATES_END_DATE_URL);
        urlMap.put(STADIUM_DECORATOR_END_DATE_TYPE, STADIUM_DECORATOR_END_DATE_URL);
        urlMap.put(EXHIBITOR_LIST_TYPE, EXHIBITOR_LIST_URL);
        urlMap.put(EXHIBITOR_INFO_TYPE, EXHIBITOR_INFO_URL);
    }

    public static String getUrl(String url, Integer businessType) {
        return requestMap.get(url) + urlMap.get(businessType);
    }
}
