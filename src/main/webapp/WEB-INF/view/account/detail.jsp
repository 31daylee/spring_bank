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
				<h2>계좌 내역 조회</h2>

				<!-- 만약 accountList null or not null  -->
					<div class="bg-light text-center"  style=" padding-bottom:50px">
						<div style="padding:50px">
							<table class = table style="border:1px solid #dee2e6">
								<thead>
									<tr>
										<th>${principal.username}님의계좌</th>						
										<th>계좌번호 : ${account.number}</th>						
										<th>잔액 : ${account.formatBalance()}</th>						
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><a href="/account/detail/${account.id}">전체 조회</a></td>
										<td><a href="/account/detail/${account.id}?type=deposit">입금 조회</a></td>
										<td><a href="/account/detail/${account.id}?type=withdraw">출금 조회</a></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div style="padding-left:50px; padding-right:50px;">
				
							<table class = table>
								<thead>
									<tr>
										<th>날짜</th>						
										<th>보낸이</th>						
										<th>받은이</th>						
										<th>입출금 금액</th>						
										<th>계좌 잔액</th>						
									</tr>
								</thead>
								<tbody>
							        <c:choose>
							            <c:when test="${not empty historyList}">
							                <c:forEach var="history" items="${historyList}">
							                    <tr>
							                        <td>${history.formatCreatedAt()}</td>
							                        <td>${history.sender}</td>
							                        <td>${history.receiver}</td>
							                        <td>${history.formatAmount()}</td>
							                        <td>${history.formatBalance()}</td>
							                    </tr>
							                </c:forEach>
							            </c:when>
							            <c:otherwise>
							                <tr>
							                    <td colspan="5" >
							                        <p style = "margin-top:20px">조회된 결과가 없습니다.</p>
							                    </td>
							                </tr>
							            </c:otherwise>
							        </c:choose>
							    </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	 </section>
</main>

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>