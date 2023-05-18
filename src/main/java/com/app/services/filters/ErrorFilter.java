package com.app.services.filters;

import com.app.services.constants.ResponseCodes;
import com.app.services.entity.Response;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
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
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            if (ctx.getResponseStatusCode() != 200) {
                System.out.println("contextPath :::: #########################################");
                System.out.println(ctx.getRequest().getContextPath().toLowerCase());
            }

        } catch (Exception ex) {
            Logger.getLogger(PostFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
