package cn.org.bachelor.iam;

import cn.org.bachelor.iam.acm.interceptor.UserAccessControlInterceptor;
import cn.org.bachelor.iam.idm.interceptor.UserIdentifyInterceptor;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @描述
 * @创建时间 2018/11/5
 * @author liuzhuo
 */
@Configuration
public class IamWebConfig implements WebMvcConfigurer {

    @Autowired
    private IamConfig config;

    /**
     * @return 访问控制拦截器
     */
    @Bean
    public UserAccessControlInterceptor authInterceptor() {
        return new UserAccessControlInterceptor();
    }

    /**
     * @return 用户识别拦截器
     */
    @Bean
    public UserIdentifyInterceptor userInterceptor() {
        return new UserIdentifyInterceptor();
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/idm/as/accesstoken",//与idmascontroller相关
            "/idm/as/logout"//与idmascontroller相关
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(config.isEnableUserIdentify()) {
            registry.addInterceptor(userInterceptor()).order(-2)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**/*");
        }
        if(config.isEnableUserAccessControl()) {
            InterceptorRegistration ir = registry.addInterceptor(authInterceptor()).order(-1)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**");
            if (config.getExcludePathPatterns() != null && config.getExcludePathPatterns().length > 0)
                ir.excludePathPatterns(config.getExcludePathPatterns());
        }
    }
}