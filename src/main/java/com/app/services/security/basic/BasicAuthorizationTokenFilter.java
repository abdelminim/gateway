package com.app.services.security.basic;

import com.app.services.constants.GlobalConstant;
import com.app.services.constants.ResponseCodes;
import com.app.services.entity.Response;
import com.app.services.util.BufferedServletRequestWrapper;
import com.app.services.util.Utils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
public class BasicAuthorizationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final BasicTokenUtil basicTokenUtil;
    private final String tokenHeader;

    public BasicAuthorizationTokenFilter(UserDetailsService userDetailsService, BasicTokenUtil basicTokenUti1, @Value("${jwt.header}") String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.basicTokenUtil = basicTokenUti1;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String contextPath = request.getServletPath().toLowerCase();
        GrantedAuthority grantedAuthority;
        HttpServletRequest wrappedRequest = new BufferedServletRequestWrapper(request);

        try {
            final String requestHeader = wrappedRequest.getHeader(this.tokenHeader);

            String username = null;
            String authToken = null;
            if (requestHeader != null && requestHeader.startsWith("Basic ")) {
                authToken = requestHeader.substring(6);
                username = basicTokenUtil.getUsernameFromToken(authToken);

            }

            //checking authentication for user 
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // It is not compelling necessary to load the use details from the database. You could also store the information
                // in the token and read it from it. It's up to you ;)
                BasicUser userDetails = (BasicUser) this.userDetailsService.loadUserByUsername(username);

                // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
                // the database compellingly. Again it's up to you ;)
                if (basicTokenUtil.getPasswordFromToken(authToken).equals(userDetails.getPassword())) {

                    //in case G2G we extract orginating channel and re-validate the object without password
                    if (GlobalConstant.g2gUser.equals(username)) {

                        String serviceUsername = Utils.extractOriginatingChannel(wrappedRequest.getInputStream());
                        if (StringUtils.isEmpty(serviceUsername) || serviceUsername.equals("0")) {
                            throw new UsernameNotFoundException("Orgintaing Channell is empty");
                        }
//                        userDetails = (BasicUser) this.userDetailsService.loadUserByUsername(serviceUsername);
                        userDetails = (BasicUser) this.userDetailsService.loadUserByUsername(String.valueOf(24));
                    }

                    if (!userDetails.istServiceConsumer()) {
                        contextPath = contextPath.substring(0, contextPath.indexOf("/", 1) + 1);
                    }

                    grantedAuthority = new SimpleGrantedAuthority(contextPath);

                    if (userDetails.getAuthorities().contains(grantedAuthority)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(wrappedRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {

                        forbiddenResponse(response, HttpServletResponse.SC_FORBIDDEN, ResponseCodes.notEnoughPrivileges);

                        printContextPathAndRequest(contextPath, wrappedRequest);

                        return;
                    }

                }
            }
        } catch (Exception e) {

            if (e.getMessage().contains("User is disabled")) {
                forbiddenResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseCodes.disabledCredentials);
            } else if (e.getMessage().contains("Orgintaing Channell is empty")) {
                forbiddenResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseCodes.originatingChannelIsNull);
            } else {
                forbiddenResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseCodes.invalidToken);
            }

            printContextPathAndRequest(contextPath, wrappedRequest);

            return;
        }
        chain.doFilter(wrappedRequest, response);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void forbiddenResponse(HttpServletResponse response, Integer responseStatus, Integer responseCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8;");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseStatus);
        ServletOutputStream out = response.getOutputStream();
        out.write(new Response(responseCode).toJson().getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    public void printContextPathAndRequest(String contextPath, HttpServletRequest wrappedRequest) {

        JsonNode jsonNode = null;
        try {
            System.out.println("contextPath :::: #########################################");
            System.out.println(contextPath);
            jsonNode = Utils.mapJson(new String(IOUtils
                    .toByteArray(wrappedRequest.getInputStream()), StandardCharsets.UTF_8));
            System.out.println("jsonNode :::: #########################################");
            System.out.println(jsonNode.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
