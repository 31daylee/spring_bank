<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>


<!-- 메인페이지 -->
<main id="main" style="margin-top: 200px;">
    <section id="features" class="features">
		<div class="container">
			<div class="row justify-content-center align-items-center">
		    	<div class="col-md-6">
		        	<div class="section-title">
				        <h2>출금 페이지</h2>
				        <form action="/account/withdraw" method="post" class=" bg-light"  style=" padding:50px">
				            <div class="form-group">
				                <label for="amount">출금 금액 </label> 
				                <input type="text"    name="amount" class="form-control" placeholder="출금할 금액을 입력해주세요" id="amount" >
				            </div>
				             </br>
				            <div class="form-group">
				                <label for="wAccountNumber">출금 계좌번호</label> 
				                <input type="text"    name="wAccountNumber" class="form-control" placeholder="계좌번호를 입력해주세요" id="wAccountNumber" >
				            </div>
				             </br>
				            <div class="form-group">
				                <label for="wAccountPassword">출금 계좌 비밀번호</label> 
				                <input type="password" name="wAccountPassword" class="form-control" placeholder="출금 계좌 비밀번호 입력해주세요" id="wAccountPassword">
				            </div>
				             </br>
				            <button type="submit" class="btn btn-primary">출금</button>
				        </form>
		   			 </div>
				</div>
			</div>	
		</div>
	</section>
</main>
<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>