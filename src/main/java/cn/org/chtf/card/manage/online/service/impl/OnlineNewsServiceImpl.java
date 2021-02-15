package cn.org.chtf.card.manage.online.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.online.model.NewsParams;
import cn.org.chtf.card.manage.online.service.OnlineNewsService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.http.HttpUtil;

import com.alibaba.fastjson.JSONObject;


/**
 * 线上预约ServiceImpl
 * @author lm
 */
@Service
public class OnlineNewsServiceImpl implements OnlineNewsService {
	 
	 private static String strURL = "http://card.hljlbh.org.cn/NEWS";
	 //private static String strURL = "http://localhost";
	
	@Override
	public List<Map> GetTopNews(int itop) throws UnsupportedEncodingException {
		String url = strURL + "/Api.php/News/GetTopNews?top="+itop;
		String value =  StringUtil.decode(HttpUtil.SendByGet(url));
		return JSONObject.parseArray(value, Map.class);
	}

	@Override
	public Map<String, Object> GetNewsInfo(String newsid) {
		String url = strURL + "/Api.php/News/GetNewsInfo?newsid="+newsid;
		String value =  StringUtil.decode(HttpUtil.SendByGet(url));
		return JSONObject.parseObject(value, Map.class);
	}	
	
	@Override
	public ResultModel GetNewsList(NewsParams news) throws Exception {
		Map<String, Object> page = new HashMap<String,Object>();
		page.put("page", news.getPage());
		page.put("limit", news.getLimit());
		page.put("xwlx", news.getNewstype());
		page.put("biaot", news.getKeywords());
		page = ResultVOUtil.TiaoZhengFenYe(page);
		String url1 = strURL + "/Api.php/News/GetNewsList?index="+page.get("index")+"&size="+page.get("size");
		String url2 = strURL + "/Api.php/News/GetNewsListCount?index="+page.get("index")+"&size="+page.get("size");
		if(page.get("xwlx")==null || page.get("xwlx").equals("")){
			url1+="&xwlx=";
			url2+="&xwlx=";
		}
		else{
			url1+="&xwlx="+URLEncoder.encode(page.get("xwlx").toString(), "UTF-8");
			url2+="&xwlx="+URLEncoder.encode(page.get("xwlx").toString(), "UTF-8");
		}
		if(page.get("biaot")==null || page.get("biaot").equals("")){
			url1+="&biaot=";
			url2+="&biaot=";
		}
		else{
			url1+="&biaot="+URLEncoder.encode(page.get("biaot").toString(), "UTF-8");
			url2+="&biaot="+URLEncoder.encode(page.get("biaot").toString(), "UTF-8");
		}
		System.out.println(URLEncoder.encode(url1, "UTF-8"));	
				//+"&xwlx="+page.get("xwlx")==null ? "" : page.get("xwlx").toString() + "&biaot="+page.get("biaoti")==null ? "" : page.get("biaot").toString();		
		String value =  StringUtil.decode(HttpUtil.SendByGet(url1));		
		
		List<Map> list = JSONObject.parseArray(value, Map.class);//onlineNewsMapper.GetNewsList(page);
		for(int j=0;j<list.size();j++){
			String intro = "";
			String Content = list.get(j).get("newsContent").toString().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "")
					.replace(" ", "").replace("　", "").replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", "");
			if(Content.length()>60){
				intro = Content.substring(0,60);
			}
			else{
				intro = Content;
			}
			list.get(j).put("intro", intro);
		}
		String strCount =  HttpUtil.SendByGet(url2).replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", "");		
		return new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,list,Integer.valueOf(strCount));
	}
	
	@Override
	public Map<String, Object> GetTopNewsForList() {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String url = strURL + "/Api.php/News/GetTopPictureNews?top=5";
		String value =  StringUtil.decode(HttpUtil.SendByGet(url));		
		List<Map> list = JSONObject.parseArray(value, Map.class);//onlineNewsMapper.GetNewsList(page);
		resMap.put("news", list);
		String urlType =  strURL + "/Api.php/News/GetNewsType";
		String valueType =  StringUtil.decode(HttpUtil.SendByGet(urlType));		
		List<Map> newsType = JSONObject.parseArray(valueType, Map.class);	
		resMap.put("newsCate", newsType);
		return resMap;
	}

    
    

}
