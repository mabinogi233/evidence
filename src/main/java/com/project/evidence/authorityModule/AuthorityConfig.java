package com.project.evidence.authorityModule;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//配置拦截器
@Configuration
public class AuthorityConfig extends WebMvcConfigurationSupport {

    @Autowired
    private AuthorityHandle authorityHandle;

    /**
     * 拦截全部路径的请求
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authorityHandle).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}

