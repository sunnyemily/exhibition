package cn.org.chtf.card.support.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WPermissionConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链

        // addPathPatterns 用于添加拦截规则,排除所有用户都具有的权限

        // excludePathPatterns 用户排除拦截
		
		WPermission wPermission = new WPermission();

        registry.addInterceptor(wPermission).addPathPatterns("/manage/**").excludePathPatterns("/manage/logout");
        super.addInterceptors(registry);

    }
}
