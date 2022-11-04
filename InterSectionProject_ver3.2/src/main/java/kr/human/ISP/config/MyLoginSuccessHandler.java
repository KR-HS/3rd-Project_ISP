package kr.human.ISP.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setDefaultTargetUrl("/");
        log.info("*".repeat(50) + "MyLoginSuccessHandler : {}", (authentication.getPrincipal()));
        request.getSession().setAttribute("userVO", (UserVO)authentication.getPrincipal());
       // String prevPage=(String)request.getSession().getAttribute("prevPage");
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("로그인 성공!!! 이전페이지로 가기 : ");
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }else{
            // 기본 url로 가도록 함함
           redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }
    }
    
    
}





