package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.repository.entity.User;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 1. HandlerIntercetor 구현하기 
@Component // IoC 대상
public class AuthInterceptor implements HandlerInterceptor {

	// preHandle :컨트롤러 들어오기 전에 동작  
	// true -> controller 안으로 들어감
	// false -> 안들어 감
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("---------Interceptor 동작 확인-------");
		// 인증 검사 
		HttpSession session =  request.getSession();
		User principal = (User)session.getAttribute(Define.PRINCIPAL); // 세션에서 값 꺼내기
		if(principal == null) {
			//response.sendRedirect("/user/sign-in"); // 인증이 안되면 로그인페이지로 
			throw new UnAuthorizedException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
		}
		
		return true;
	}

	// postHandle : 뷰가 랜더링 되기 전에 호출 되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	// afterCompletion : 요청 처리가 완료된 후, 뷰가 랜더링이 완료된 후 호출 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
