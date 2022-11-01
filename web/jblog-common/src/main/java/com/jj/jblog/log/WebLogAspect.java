package com.jj.jblog.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.jj.jblog.domain.WebLog;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一日志处理切面
 *
 * @author 任人子
 * @date 2022/3/25  - {TIME}
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.jj.jblog.controller.*.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint join) throws Exception {
    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Exception {

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        // 封装日志对象
        WebLog webLog = new WebLog();
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        long endTime =  System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();

        webLog.setBasePath(StrUtil.removePrefix(urlStr, URLUtil.url(urlStr).getPath()))
                .setIp(request.getRemoteUser())
                .setMethod(request.getMethod())
                .setSpendTime((int) (endTime - startTime))
                .setStartTime(startTime)
                .setResult(result)
                .setUri(request.getRequestURI())
                .setUrl(request.getRequestURL().toString())
                .setParameter(getParameter(method, joinPoint.getArgs()));

        Map<String, Object> initMap = new HashMap<>();
        initMap.put("url", webLog.getUrl());
        initMap.put("parameter", webLog.getParameter());
        initMap.put("method", webLog.getMethod());
        initMap.put("spendTime", webLog.getSpendTime());

        LOGGER.info(Markers.appendEntries(initMap), JSONUtil.parse(webLog).toString());
        return result;
    }
    public Object getParameter(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        List<Object> argList = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if(requestBody != null){
                argList.add(args[i]);
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if(requestParam != null){
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if(!StringUtils.isEmpty(requestParam.value())){
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if(argList.size() == 0){
            return null;
        }else if(argList.size() == 1){
            return argList.get(0);
        }else{
            return argList;
        }
    }

}
