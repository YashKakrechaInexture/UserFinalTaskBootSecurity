package com.inexture.UserFinalTaskBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

//import com.inexture.UserFinalTaskBoot.Filters.AdminFilter;
//import com.inexture.UserFinalTaskBoot.Filters.HomepageFilter;
//import com.inexture.UserFinalTaskBoot.Filters.LoginFilter;
//import com.inexture.UserFinalTaskBoot.Filters.UserFilter;

@Configuration
@Profile("profile1")
public class ViewConfigurationClass {	

//	private AdminFilter adminFilter;
//	
//	private HomepageFilter homepageFilter;
//	
//	private LoginFilter loginFilter;
//	
//	private UserFilter userFilter;
//	
//	@Autowired
//    public void adminFilterConfiguration(AdminFilter adminFilter) {
//        this.adminFilter = adminFilter;
//    }
//	
//	@Autowired
//    public void homepageFilterConfiguration(HomepageFilter homepageFilter) {
//        this.homepageFilter = homepageFilter;
//    }
//	
//	@Autowired
//    public void loginFilterConfiguration(LoginFilter loginFilter) {
//        this.loginFilter = loginFilter;
//    }
//	
//	@Autowired
//    public void userFilterConfiguration(UserFilter userFilter) {
//        this.userFilter = userFilter;
//    }
//	
//	@Bean
//    public FilterRegistrationBean<AdminFilter> adminFilterFunction() {
//
//        FilterRegistrationBean<AdminFilter> registration = new FilterRegistrationBean<AdminFilter>();
//        registration.setFilter(adminFilter);
//        registration.addUrlPatterns("/admin","/adminServlet","/deleteServlet");
//        return registration;
//    }
//	
//	@Bean
//    public FilterRegistrationBean<HomepageFilter> homepageFilterFunction() {
//
//        FilterRegistrationBean<HomepageFilter> registration = new FilterRegistrationBean<HomepageFilter>();
//        registration.setFilter(homepageFilter);
//        registration.addUrlPatterns("/homepage");
//        return registration;
//    }
//	
//	@Bean
//    public FilterRegistrationBean<LoginFilter> loginFilterFunction() {
//
//        FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<LoginFilter>();
//        registration.setFilter(loginFilter);
//        registration.addUrlPatterns("/index");
//        return registration;
//    }
//	
//	@Bean
//    public FilterRegistrationBean<UserFilter> userFilterFunction() {
//
//        FilterRegistrationBean<UserFilter> registration = new FilterRegistrationBean<UserFilter>();
//        registration.setFilter(userFilter);
//        registration.addUrlPatterns("/homepage","/editServlet","/updateServlet","/adminServlet");
//        return registration;
//    }
	
	@Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html");
        
        return viewResolver;
    }
	
	@Bean
    public ViewResolver getViewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();

        viewResolver.setCache(true);
        viewResolver.setSuffix(".ftl");
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean(name = "freemarkerConfig")
    public FreeMarkerConfigurer getFreemarkerConfig() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();

        config.setTemplateLoaderPath("classpath:/templates");
        return config;
    }
}
