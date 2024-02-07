<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기 아래 부분 부터 main 영역으로 사용 예정  -->
			<div class="col-sm-8">
				<h2>로그인</h2>
				<h5>어서오세요! 환영합니다.</h5>
				<form action="/user/sign-in" method="post"> <!-- 로그인은 post로 해야, 히스토리가 안남음 -->
				  <div class="form-group">
				    <label for="username">이름 :</label>
				    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username" value="길동">
				  </div>
				  <div class="form-group">
				    <label for="pwd">비밀번호 :</label>
				    <input type="password" name="password" class="form-control" placeholder="Enter password" id="pwd" value="1234">
				  </div>
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
					  <button type="submit" class="btn btn-primary btn-login" >로그인</button>
				  </div>
				</form>
			</div>
		</div>
	</div>

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>