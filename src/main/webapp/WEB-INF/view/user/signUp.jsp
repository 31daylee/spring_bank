<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기 아래 부분 부터 main 영역으로 사용 예정  -->
<main id="main" style="margin-top: 200px;">
    <section id="features" class="features">
      <div class="container">
	     	<div class="row justify-content-center align-items-center">
	        	<div class="col-md-6">
	        		<div class="section-title">
					<h2>회원 가입</h2>
					<h5 class="greeting">어서오세요! 환영합니다</h5>
					<form action="/user/sign-up" method="post" class=" bg-light"  style=" padding:50px">
					  <div class="form-group">
					    <label for="username">아이디</label>
					    <input type="text" name="username" class="form-control" placeholder="아이디를 입력해주세요" id="username">
					  </div>
					  	    </br>
					  <div class="form-group">
					    <label for="pwd">비밀번호</label>
					    <input type="password" name="password" class="form-control" placeholder="비밀번호를 입력해주세요" id="pwd">
					  </div>
					  	    </br>
					  <div class="form-group">
					    <label for="fullname">성함</label>
					    <input type="text" name="fullname" class="form-control" placeholder="성함을 입력해주세요" id="fullname">
					  </div>
					  <!-- TODO: 이벤트 전파 속성- 버블링/캡처링이란? -->
					  <div class="login-container">
						  <a class="login-button" href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=72919ee7c8ec0f967c858cc03998bbc3&redirect_uri=http://localhost:80/user/kakao-callback">
						  	<img alt="" src="/images/kakaotalk_sharing_btn_small.png" style="border: 1px solid gray;" >
						  </a> 
						  <a href="#" class="login-button" style="border: 1px solid gray; border-radius:6px;" >
						  	<img alt="" src="/images/naver_logo.png" >
						  </a>
						  <a href="https://accounts.google.com/o/oauth2/v2/auth?client_id=78534889326-bd77l69smot5kpjddheeu5nsjrcbf8ea.apps.googleusercontent.com&redirect_uri=http://localhost:80/user/google-callback&response_type=code&scope=profile&email" class="login-button">
						  	<img alt="" src="/images/google_logo.png" >
						  </a>
						  <button type="submit" class="btn btn-primary  btn-signup">회원가입</button>
					  </div>
					</form>
				</div>
			</div>
		</div>
		</div>
    </section><!-- End App Features Section -->
  </main><!-- End #main -->

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>