package com.api.agroguard.aspect;

import com.api.agroguard.service.GeoLocationService;
import com.api.agroguard.service.VisitorInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private VisitorInfoService visitorInfoService;

    @Pointcut("within(com.api.agroguard.controller..*)")
    public void controllerPackagePointcut() {
        // Pointcut for all controller methods
    }

    @Before("controllerPackagePointcut()")
    public void logActivity(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String methodName = joinPoint.getSignature().getName();

        // Here you could save the log to a database or external logging service
        System.out.println("Activity: User " + username + " accessed " + methodName + " from IP " + ip);
    }

    @Before("execution(* com.api.agroguard.controller.*.*(..))")
    public void logRequestInfo(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String geoLocation = geoLocationService.getGeoLocation(ip);
        System.out.println("IP: " + ip + " GeoLocation: " + geoLocation);
        // 这里可以进一步实现计数和持久化逻辑
        // 更新访问信息
        visitorInfoService.updateVisitorInfo(ip, geoLocation);
    }
}
