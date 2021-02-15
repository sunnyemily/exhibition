package cn.org.chtf.card.support.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.tomcat.util.http.MimeHeaders;

public class WRouterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException { // 修改请求头
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        Map<String, String> map = new HashMap<>();
        map.put("Cookie", "SESSION=1234356");
        modifyHeaders(map, httpRequest);

        // 修改cookie
        ModifyHttpServletRequestWrapper requestWrapper = new ModifyHttpServletRequestWrapper(httpRequest);
        String token = httpRequest.getHeader("token");
        if (token != null && !"".equals(token)) {
            requestWrapper.putCookie("SESSION", token);
        }
        else {
        	token = request.getParameter("token");
            if (token != null && !"".equals(token)) {
                requestWrapper.putCookie("SESSION", token);
            }
        }

        // finish
        chain.doFilter(requestWrapper, response);		
	}


    /**
     * 修改请求头信息
     * @param headerses
     * @param request
     */
    private void modifyHeaders(Map<String, String> headerses, HttpServletRequest request) {
        if (headerses == null || headerses.isEmpty()) {
            return;
        }
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            for (Map.Entry<String, String> entry : headerses.entrySet()) {
                o2.removeHeader(entry.getKey());
                o2.addValue(entry.getKey()).setString(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改cookie信息
     */
    class ModifyHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, String> mapCookies;
		private Map<String, String[]> parameterMap; // 所有参数的Map集合

        ModifyHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            this.mapCookies = new HashMap<>();
            this.parameterMap = new HashMap<>();
        }
        public void putCookie(String name, String value) {
            this.mapCookies.put(name, value);
        }
		@Override
        public Cookie[] getCookies() {
            HttpServletRequest request = (HttpServletRequest) getRequest();
            Cookie[] cookies = request.getCookies();
            if (mapCookies == null || mapCookies.isEmpty()) {
                return cookies;
            }
            if (cookies == null || cookies.length == 0) {
                List<Cookie> cookieList = new LinkedList<>();
                for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
                    String key = entry.getKey();
                    if (key != null && !"".equals(key)) {
                        cookieList.add(new Cookie(key, entry.getValue()));
                    }
                }
                if (cookieList.isEmpty()) {
                    return cookies;
                }
                return cookieList.toArray(new Cookie[cookieList.size()]);
            } else {
                List<Cookie> cookieList = new ArrayList<>(Arrays.asList(cookies));
                for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
                    String key = entry.getKey();
                    if (key != null && !"".equals(key)) {
                        for (int i = 0; i < cookieList.size(); i++) {
                            if(cookieList.get(i).getName().equals(key)){
                                cookieList.remove(i);
                            }
                        }
                        cookieList.add(new Cookie(key, entry.getValue()));
                    }
                }
                return cookieList.toArray(new Cookie[cookieList.size()]);
            }
        }
		// 重写几个HttpServletRequestWrapper中的方法
		/**
		 * 获取所有参数名
		 * 
		 * @return 返回所有参数名
		 */
		@Override
		public Enumeration<String> getParameterNames() {
			Vector<String> vector = new Vector<String>(parameterMap.keySet());
			return vector.elements();
		}

		/**
		 * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
		 * 
		 * @param name
		 *            指定参数名
		 * @return 指定参数名的值
		 */
		@Override
		public String getParameter(String name) {
			String[] results = parameterMap.get(name);
			if (results == null || results.length <= 0)
				return null;
			else {
				System.out.println("修改之前： " + results[0]);
				return modify(results[0]);
			}
		}

		/**
		 * 获取指定参数名的所有值的数组，如：checkbox的所有数据 
		 * 接收数组变量 ，如checkobx类型
		 */
		@Override
		public String[] getParameterValues(String name) {
			String[] results = parameterMap.get(name);
			if (results == null || results.length <= 0)
				return null;
			else {
				int length = results.length;
				for (int i = 0; i < length; i++) {
					System.out.println("修改之前2： " + results[i]);
					results[i] = modify(results[i]);
				}
				return results;
			}
		}

		/**
		 * 自定义的一个简单修改原参数的方法，即：给原来的参数值前面添加了一个修改标志的字符串
		 * 
		 * @param string
		 *            原参数值
		 * @return 修改之后的值
		 */
		private String modify(String string) {
			return "Modified: " + string;
		}
    }
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
