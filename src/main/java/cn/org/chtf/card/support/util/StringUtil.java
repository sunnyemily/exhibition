package cn.org.chtf.card.support.util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;  
public class StringUtil {
	/** 
     * title:StringUtil.java 
     * description:判断是否是空 
     * time:2017年1月16日 下午10:44:21 
     * author:debug-steadyjack 
     * @param str 
     * @return boolean 
     */  
    public static boolean isEmpty(String str){  
        if(str==null || "".equals(str.trim())){  
            return true;  
        }else{  
            return false;  
        }  
    }  
      
    /** 
     * title:StringUtil.java 
     * description:判断是否不是空 
     * time:2017年1月16日 下午10:44:44 
     * author:debug-steadyjack 
     * @param str 
     * @return boolean 
     */  
    public static boolean isNotEmpty(String str){  
        if((str!=null)&&!"".equals(str.trim())){  
            return true;  
        }else{  
            return false;  
        }  
    }  
      
    /** 
     * title:StringUtil.java 
     * description: 格式化模糊查询 
     * time:2017年1月16日 下午10:45:09 
     * author:debug-steadyjack 
     * @param str 
     * @return String 
     */  
    public static String formatLike(String str){  
        if(isNotEmpty(str)){  
            return "%"+str+"%";  
        }else{  
            return null;  
        }  
    }  
      
    /** 
     * title:StringUtil.java 
     * description:过滤掉集合里的空格 
     * time:2017年1月16日 下午10:45:34 
     * author:debug-steadyjack 
     * @param list 
     * @return List<String> 
     */  
    public static List<String> filterWhite(List<String> list){  
        List<String> resultList=new ArrayList<String>();  
        for(String str:list){  
            if(isNotEmpty(str)){  
                resultList.add(str);  
            }  
        }  
        return resultList;  
    } 
    
    private static Pattern linePattern = Pattern.compile("_(\\w)");  
    /**下划线转驼峰*/  
    public static String lineToHump(String str){  
        str = str.toLowerCase();  
        Matcher matcher = linePattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }  
    private static Pattern humpPattern = Pattern.compile("[A-Z]");  
    /**驼峰转下划线,效率比上面高*/  
    public static String humpToLine(String str){  
        Matcher matcher = humpPattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }
    
    /**
     * @Description: 计算展位区间,传进来的展位列示例【booths：N1021,N1022,N1023,N1025,N1028,N1029】
     * @date: 2020年9月29日 下午7:25:23
     * @author: ggwudivs
     * @param list
     * @return: String
     */
    public static List<Map<String, Object>> calculateBoothRegion(List<Map<String, Object>> list) {
    	for (Map<String, Object> map : list) {
			String booths = map.get("booths")==null?"":map.get("booths").toString();
			if(booths.length() > 0){
				String boothRegionStr = "";
				int total=0;
				String[] boothsStrSplit = booths.split(",");
				for(int j=0;j<boothsStrSplit.length;j++){
					if(j==0){
						boothRegionStr=boothRegionStr+boothsStrSplit[j];
					}
					else{
						String qlw = boothsStrSplit[j].substring(0,2);
						String preqlw = boothsStrSplit[j-1].substring(0,2); 
						Integer first = Integer.valueOf(boothsStrSplit[j].substring(2));
						Integer prefirst = Integer.valueOf(boothsStrSplit[j-1].substring(2));
						if(qlw.equals(preqlw)){
							if(first==(prefirst+1)){	
								total++;
								if(j==boothsStrSplit.length-1){
									boothRegionStr=boothRegionStr+"-"+boothsStrSplit[j];
								}
							}
							else{
								if(total>=1){
									boothRegionStr=boothRegionStr+"-"+boothsStrSplit[j-1]+","+boothsStrSplit[j];
									total=0;
								}
								else{
									boothRegionStr=boothRegionStr+","+boothsStrSplit[j];
								}
							}
						}
						else{
							if(total>1){
								boothRegionStr=boothRegionStr+"-"+boothsStrSplit[j-1]+","+boothsStrSplit[j];
							}
							else{
								boothRegionStr=boothRegionStr+","+boothsStrSplit[j];
							}
							total=0;
						}				
					}
				}
				map.put("booths", boothRegionStr);
			}
		}
    	return list;
	}
    
    /**
     * 统一展位号
     * @param list
     * @return
     */
    public static String GetZhanWeiPinJie(List<Map<String, Object>> list) {  
		String result = "";
		int total=0;
		for(int j=0;j<list.size();j++){
			if(j==0){
				result=result+list.get(j).get("boothName");
			}
			else{
				String qlw = String.valueOf(list.get(j).get("boothName")).substring(0,2);
				String preqlw = String.valueOf(list.get(j-1).get("boothName")).substring(0,2); 
				Integer first = Integer.valueOf(String.valueOf(list.get(j).get("boothName")).substring(2));
				Integer prefirst = Integer.valueOf(String.valueOf(list.get(j-1).get("boothName")).substring(2));
				if(qlw.equals(preqlw)){
					if(first==(prefirst+1)){	
						total++;
						if(j==list.size()-1){
							result=result+"-"+list.get(j).get("boothName");
						}
					}
					else{
						if(total>=1){
							result=result+"-"+list.get(j-1).get("boothName")+","+list.get(j).get("boothName");
							total=0;
						}
						else{
							result=result+","+list.get(j).get("boothName");
						}
					}
				}
				else{
					if(total>1){
						result=result+"-"+list.get(j-1).get("boothName")+","+list.get(j).get("boothName");
					}
					else{
						result=result+","+list.get(j).get("boothName");
					}
					total=0;
				}				
			}
		}
		return result;
	}
    
    /**
     * 去除html标签
     */
    public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
    
    /**
     * url解码
     */
    public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return "";
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}
    
    /**
     * 取当前年份后两位
     * @param strDate
     * @return
     */
    public static String getYear() {
    	Date strDate = new Date();
        String date = null;
        if (strDate!= null) {
            Calendar startTime = Calendar.getInstance();
            int year = startTime.get(Calendar.YEAR) - 20;
            // 这里初始化时间，然后设置年份。只以年份为基准，不看时间
            startTime.clear();
            startTime.set(Calendar.YEAR, year);
            SimpleDateFormat formatter = new SimpleDateFormat("yy");
            formatter.setLenient(false);
            formatter.set2DigitYearStart(startTime.getTime());
            try {
                date = formatter.format(strDate);
            }
            catch (Exception e) {
            }
        }
        return date;
    }
    
    //获取  天，小时，分，秒
    public static String GetTimeDifference(String time) {
    	String str = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();//当前时间
            Date date = df.parse(time);//过去
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if(day>0){
            	str +=  day + "天";
            }
            if(hour>0){
            	str+=hour + "小时";
            }
            
            str +=  min + "分";// + s + "秒");
        } catch (Exception e) {

        }
        return str;
    }
    
}
