package cn.org.chtf.card.support.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.org.chtf.card.manage.function.pojo.Function;
import cn.org.chtf.card.manage.user.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WPermission implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	User user = (User)request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();  

    	//1.未登陆，跳转到登陆页面
    	if(null==user) {
    		response.setCharacterEncoding("UTF-8");  
    	    response.setContentType("application/json; charset=utf-8");
    	    PrintWriter out = null;  
            out = response.getWriter();  
            out.append(mapper.writeValueAsString(WConst.RELOGINJSON));
    		System.out.println("登陆认证失败");
            return false;
    	}
    	//2.进行授权认证
    	String uri = request.getRequestURI().replace("/manage/", "");
    	if("/getUser/updateProfile/updatePassword/metrics/info/health".contains(uri)) {
    		return true;
    	}
    	System.out.println(uri);
    	for(Function f : user.getPermissions()) {

    		if(uri.equals(f.getFunctionUrl())) {
    			return true;
    		}
    	}
    	//没有提前返回的都是未授权访问，返回未授权信息
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");
	    PrintWriter out = null;  
        out = response.getWriter();  
        out.append(mapper.writeValueAsString(WConst.NOPERMISSIONJSON));
		System.out.println("权限认证失败");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        System.out.println(request.getRequestURI());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        System.out.println("MyInterceptor1.afterCompletion()在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行(主要是用于进行资源清理工作)");

    }
	
}
