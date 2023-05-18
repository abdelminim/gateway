package com.app.services.filters;

import brave.Tracer;
import com.app.services.security.basic.BasicTokenUtil;
import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PreFilter extends ZuulFilter {

    @Autowired
    private Tracer tracer;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private BasicTokenUtil basicTokenUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String user = "";
        if (request.getHeader(tokenHeader) != null && !request.getHeader(tokenHeader).isEmpty()) {
            user = request.getHeader(tokenHeader).substring(6);
            ctx.addZuulRequestHeader("username", basicTokenUtil.getUsernameFromToken(user));
        }

        ctx.addZuulRequestHeader("traceId", tracer.currentSpan().context().traceIdString());
        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
        return null;
    }
}
