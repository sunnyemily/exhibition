package cn.org.chtf.card;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 3600, redisNamespace = "exhibition")
@EnableTransactionManagement
@MapperScan("cn.org.chtf.card.manage.*.dao")
public class ExhibitionApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExhibitionApplication.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED,
					"/error/error.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND,
					"/error/error.html");
			ErrorPage error500Page = new ErrorPage(
					HttpStatus.INTERNAL_SERVER_ERROR, "/error/error.html");

			container.addErrorPages(error401Page, error404Page, error500Page);
		});
	}
	
	@Bean
	 PageHelper pageHelper(){
	    //分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
	     properties.setProperty("reasonable", "true");
	     properties.setProperty("supportMethodsArguments", "true");
	     properties.setProperty("returnPageInfo", "check");
	     properties.setProperty("params", "count=countSql");
	     pageHelper.setProperties(properties);

   //添加插件
	     new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
	     return pageHelper;
	 }
	
}
