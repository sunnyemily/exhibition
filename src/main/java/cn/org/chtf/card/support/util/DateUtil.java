package cn.org.chtf.card.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {
	  
    /** 
     * title:DateUtil.java 
     * description:日期对象转字符串 
     * time:2017年1月16日 下午10:40:49 
     * author:debug-steadyjack 
     * @param date 
     * @param format 
     * @return String 
     */  
    public static String formatDate(Date date,String format){  
        String result=null;  
        try {  
            SimpleDateFormat sdf=new SimpleDateFormat(format);  
            if(date!=null){  
                result=sdf.format(date);  
            }  
        } catch (Exception e) {  
            System.out.println("日期转字符串发生异常: "+e.getMessage());  
        }  
        return result;  
    }  
      
    /** 
     * title:DateUtil.java 
     * description:字符串转日期对象 
     * time:2017年1月16日 下午10:43:14 
     * author:debug-steadyjack 
     * @param str 
     * @param format 
     * @throws Exception 
     */  
    public static Date formatString(String str,String format){  
        Date date=null;  
        try {  
            if(StringUtil.isNotEmpty(str)){  
                SimpleDateFormat sdf=new SimpleDateFormat(format);  
                date=sdf.parse(str);  
            }  
        } catch (Exception e) {  
            System.out.println("字符串转日期出错: "+e.getMessage());  
        }  
        return date;  
    }  
      
    /** 
     * title:DateUtil.java 
     * description: 获取当前年月日时分秒组成的串：可用于命名图片、文件 
     * time:2017年1月16日 下午10:48:48 
     * author:debug-steadyjack 
     * @return String 
     * @throws Exception 
     */  
    public static String getCurrentDateStr()throws Exception{  
        Date date=new Date();  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");  
        return sdf.format(date);  
    }
    /**
     * 获取当前日期
     * @return Date
     */
    public static Date now() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
    	return Date.from(zdt.toInstant());
    }
    /**
     * @apiNote 计算两个时间的天数差
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDifferenceTotalDays(Object startDate,Object endDate) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	try {
	        long from = 0;
	        long to = 0;
	        if(startDate==null) {
	        	from = (new Date()).getTime();
	        }
	        else if(startDate.getClass().equals(String.class)) {//字符串类型的话
	        	String strStartDate = startDate.toString();
	        	if(strStartDate.length()>10) {
					from = timeFormat.parse(strStartDate).getTime();
	        	}
	        	else {
					from = dateFormat.parse(strStartDate).getTime();
	        	}
	        }
	        else if(startDate.getClass().equals(Long.class)) {
	        	from = (Long)startDate;
	        }
	        else if(startDate.getClass().equals(Date.class)) {
	        	from = ((Date)startDate).getTime();
	        }
	        if(endDate==null) {
	        	to = (new Date()).getTime();
	        }
	        else if(endDate.getClass().equals(String.class)) {
	        	String strEndDate = endDate.toString();
	        	if(strEndDate.length()>10) {
	        		to = timeFormat.parse(strEndDate).getTime();
	        	}
	        	else {
	        		to = dateFormat.parse(strEndDate).getTime();
	        	}
	        }
	        else if(endDate.getClass().equals(Long.class)) {
	        	to = (Long)endDate;
	        }
	        else if(endDate.getClass().equals(Date.class)) {
	        	to = ((Date)endDate).getTime();
	        }
	        int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
	        if(((to - from) % (1000 * 60 * 60 * 24))!=0) {
	        	days = days + 1;
	        }
	        return days;
		} catch (ParseException e) {
	    	return 0;
		}
    }
}
