<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기 아래 부분 부터 main 영역으로 사용 예정  -->
			<div class="col-sm-8">
				<h2>회원 가입</h2>
				<h5>어서오세요! 환영합니다.</h5>
				<form action="/user/sign-up" method="post">
				  <div class="form-group">
				    <label for="username">이름 :</label>
				    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
				  </div>
				  <div class="form-group">
				    <label for="pwd">비밀번호 :</label>
				    <input type="password" name="password" class="form-control" placeholder="Enter password" id="pwd">
				  </div>
				  <div class="form-group">
				    <label for="fullname">성 :</label>
				    <input type="text" name="fullname" class="form-control" placeholder="Enter fullname" id="fullname">
				  </div>
				  <!-- TODO: 이벤트 전파 속성- 버블링/캡처링이란? -->
				  <button type="submit" class="btn btn-primary">회원가입</button>
				</form>
			</div>
		</div>
	</div>

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>