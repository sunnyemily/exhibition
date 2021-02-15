package cn.org.chtf.card.manage.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 * @author lm
 */
@Data
public class WebProduct implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer productId;

    /**
     * 所属公司id
     */
    private Integer companyId;

    /**
     * 所属公司名称
     */
    private String companyName;

    /**
     * 所属栏目
     */
    private Integer productMenuId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品价格
     */
    private Double productPrice;

    /**
     * 封面图
     */
    private String productPicture;

    /**
     * 轮播图
     */
    private String productPictures;

    /**
     * 产品视频
     */
    private String productVideo;

    /**
     * 款号
     */
    private String productNum;

    /**
     * 排序
     */
    private Integer productOrder;

    /**
     * 产品细节
     */
    private String productDescription;

    /**
     * 更新时间
     */
    private Date productUpdatetime;

    private Integer productStatus;
    
    private Integer session;
    
    private String menuname;
    
    private Integer memberid;
    
    private String remark;
    
    /**
     * 品牌
     */
    private String productBrand;
    
    private String productUnit;
    private String unitname;

    /**
     * 产品价格单位
     */
    private Integer productPriceunit;
    private String priceunitname;

    /**
     * 供应数量
     */
    private String supplyquantity;

    /**
     * 展品虚拟展厅
     */
    private String url;

    /**
     * 国家
     */
    private Integer country;
    private String countryname;

    /**
     * 省份
     */
    private Integer province;
    private String provincename;
    /**
     * 城市
     */
    private Integer city;
    private String cityname;
    
    //点击量
    private Integer clickrates;
    
}
