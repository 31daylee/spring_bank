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
				    <label for="username">username:</label>
				    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username" value="길동">
				  </div>
				  <div class="form-group">
				    <label for="pwd">password:</label>
				    <input type="password" name="password" class="form-control" placeholder="Enter password" id="pwd" value="1234">
				  </div>
				  <button type="submit" class="btn btn-primary">로그인</button>
				</form>
			</div>
		</div>
	</div>

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>