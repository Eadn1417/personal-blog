package com.en.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenGlobalConfig implements WebMvcConfigurer {

    /**
     * 注册Sa-Token拦截器 - 开启注解鉴权功能
     * 作用：启用 @SaCheckLogin、@SaCheckRole、@SaCheckPermission 等注解功能
     * 注意：使用无参构造，避免上下文未初始化错误
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                    "/api/auth/login",     // 放行登录接口
                    "/api/auth/register"   // 放行注册接口
                );
    }

    /**
     * 跨域配置 - 前后端分离必须配置
     * 作用：允许前端应用从不同域名访问后端API
     * 影响：确保浏览器不会阻止跨域请求，支持Token认证
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有路径
                .allowedOriginPatterns("*") // 允许所有来源（生产环境应指定具体域名）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*") // 允许所有请求头
                .exposedHeaders(
                    "Authorization", 
                    "Content-Disposition",
                    "X-Total-Count"
                ) // 暴露给前端的响应头
                .allowCredentials(true) // 允许携带认证信息（Cookie、Authorization头等）
                .maxAge(3600); // 预检请求缓存时间（秒）
    }
}