<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기 아래 부분 부터 main 영역으로 사용 예정  -->
<main id="main" style="margin-top: 200px;">
    <section id="features" class="features">
        <div class="container">
            <div class="row justify-content-center align-items-center">
                <!-- Aside -->
                <div class="col-md-3">
                    <%@ include file="/WEB-INF/view/layout/aside.jsp" %>
                </div>
                <!-- Account List -->
        		<div class="col-md-9">
        		<div class="section-title">
				<h2>계좌 생성 페이지</h2>
				<form action="/account/save" method="post" class=" bg-light"  style=" padding:50px"> 
				  <div class="form-group">
				    <label for="number">계좌번호 </label>
				    <input type="text" name="number" class="form-control" placeholder="Enter number" id="number" value="5555">
				  </div>
				  <div class="form-group">
				    <label for="pwd">계좌 비밀번호</label>
				    <input type="password" name="password" class="form-control" placeholder="Enter password" id="password" value ="1234">
				  </div>
				  <div class="form-group">
				    <label for="balance">입금 금액</label>
				    <input type="text" name="balance" class="form-control" placeholder="Enter balance" id="balance" value="2000">
				  </div>
				  <button type="submit" class="btn btn-primary">계좌 생성</button>
				</form>
			</div>
		</div>
	</div>
		</div>
 </section>
</main>

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>