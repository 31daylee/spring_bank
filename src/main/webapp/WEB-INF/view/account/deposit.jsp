<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
			<!-- 메인페이지 -->
<main id="main" style="margin-top: 200px;">
    <section id="features" class="features">
      <div class="container">
     	<div class="row justify-content-center align-items-center">
        	<div class="col-md-6">
        		<div class="section-title">
			        <h2>입금 페이지</h2>
			        <form action="/account/deposit" method="post" class=" bg-light"  style=" padding:50px">
			            <div class="form-group">
			                <label for="amount">입금 금액 </label> 
			                <input type="text"    name="amount" class="form-control" placeholder="입금할 금액을 입력해주세요" id="amount" >
			            </div>
			 			</br>
			            <div class="form-group">
			                <label for="dAccountNumber">입금 계좌번호 </label> 
			                <input type="text"    name="dAccountNumber" class="form-control" placeholder="입금 계좌번호를 입력해주세요" id="dAccountNumber" >
			            </div>
						</br>
			            <div class="form-group">
			                <label for="dAccountPassword">입금 계좌 비밀번호</label> 
			                <input type="password" name="dAccountPassword" class="form-control" placeholder="입금 계좌 비밀번호를 입력해주세요" id="dAccountPassword">
			            </div>
						 </br>
			            <button type="submit" class="btn btn-primary">입금</button>
			        </form>
				</div>
			</div>
		</div>
	</div>
 </section>
</main>
<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>