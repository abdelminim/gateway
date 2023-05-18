package com.app.services.filters;

import com.app.services.constants.ResponseCodes;
import com.app.services.entity.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.StreamUtils;

public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
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
                Response response;
                if (ctx.getResponseStatusCode() == 404) {
                    response = new Response(ResponseCodes.pathNotFound);
                } else {
                    InputStream stream = ctx.getResponseDataStream();
                    String body = StreamUtils.copyToString(stream, Charset.forName(StandardCharsets.UTF_8.toString()));
                    response = new Response(ResponseCodes.unHandeledException, body);
                }
                ctx.getResponse().setContentType("application/json;charset=UTF-8");
                ctx.setResponseBody(new Gson().toJson(response));
            }
;
        } catch (IOException ex) {
            Logger.getLogger(PostFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
