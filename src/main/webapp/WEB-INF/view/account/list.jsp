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
				<h2>나의 계좌 목록</h2>
				<!-- 만약 accountList null or not null  -->
				<div class="bg-light">
					<c:choose>
						<c:when test="${accountList != null}">
							<table class = table>
								<thead>
									<tr>
										<th>계좌 번호</th>						
										<th>잔액</th>						
									</tr>
								</thead>
								<tbody>
									<c:forEach var="account" items="${accountList}">
										<tr>
											<td><a href="/account/detail/${account.id}">${account.number}</a></td>
											<td>${account.formatBalance()}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<p>아직 생성된 계좌가 없습니다</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	</div>
</section>
</main>

<!-- footer -->
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>