<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="/css/styles.css" rel="stylesheet" integrity="sha256-2XFplPlrFClt0bIdPgpz8H7ojnk10H69xRqd9+uTShA=" crossorigin="anonymous"/>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
	<div class="container"style="margin-top:100px">
	<div class="row">
			<div class="col-12">
				<!-- Page title -->
				<div class="my-5" >
					<h3>계정</h3>
					<hr>
				</div>
				<!-- Form START -->
				<form class="file-upload" action="/user/profile" method="post">
					<div class="row mb-5 gx-5">
						<!-- Contact detail -->
						<div class="col-xxl-8 mb-5 mb-xxl-0">
							<div class="bg-light px-4 py-5 rounded">
								<div class="row g-3">
									<h4 class="mb-4 mt-0">나의 정보</h4>
									<!-- First Name -->
									<div class="col-md-6">
										<label class="form-label">이메일 *</label>
										<input type="text" class="form-control" placeholder="" aria-label="First name" value="Scaralet">
									</div>
									<!-- Last name -->
									<div class="col-md-6">
										<label class="form-label">이름 *</label>
										<input type="text" class="form-control" placeholder="" aria-label="Last name" value="Doe">
									</div>
									<!-- Phone number -->
									<div class="col-md-6">
										<label class="form-label">휴대폰 번호</label>
										<input type="text" class="form-control" placeholder="" aria-label="Phone number" value="(333) 000 555">
									</div>
								</div> <!-- Row END -->
							</div>
						</div>
						<!-- Upload profile -->
						
						<div class="col-xxl-4">
							<div class="bg-light px-3 py-2 rounded">
								<div class="row g-3"  style="margin-top:20px">
									<h4 class="mb-4 mt-0">프로필</h4>
									<div class="text-center">
										<!-- Image upload -->
										<div class="square position-relative display-2 mb-3">
											<i class="fas fa-fw fa-user position-absolute top-50 start-50 translate-middle text-secondary"></i>
										</div>
										<!-- Button -->
										<input type="file" id="customFile" name="file" hidden="" class="custom-file-input">
										<label class="btn btn-success-soft btn-block " for="customFile">등록</label>
										<button type="button" class="btn btn-danger-soft">삭제</button>
										<!-- Content -->
										<p class="text-muted mt-3 mb-0"><span class="me-1">Note:</span>최대 300px x 300px</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="gap-3 d-md-flex justify-content-md-end text-center">
						<button type="button" class="btn btn-outline-primary btn-lg">저장</button>
					</div>
					</form> <!-- Row END -->
					<hr>
					<!-- change password -->
					<form action="/user/password" method="post">
					<div class="col-xxl-6">
						<div class="bg-light px-4 py-5 rounded">
							<div class="row g-3">
								<h4 class="my-4">비밀번호 변경</h4>
								<!-- Old password -->
								<div class="col-md-6">
									<label for="exampleInputPassword1" class="form-label">현재 비밀번호 *</label>
									<input type="password" class="form-control" id="exampleInputPassword1">
								</div>
								<!-- New password -->
								<div class="col-md-6">
									<label for="exampleInputPassword2" class="form-label">새 비밀번호 *</label>
									<input type="password" class="form-control" id="exampleInputPassword2">
								</div>
								<!-- Confirm password -->
								<div class="col-md-12">
									<label for="exampleInputPassword3" class="form-label">새 비밀번호 확인 *</label>
									<input type="password" class="form-control" id="exampleInputPassword3">
								</div>
							</div>
						</div>
					</div>
					<!-- button -->
				<div class="gap-3 d-md-flex justify-content-md-end text-center">
					<button type="button" class="btn btn-outline-danger btn-lg">탈퇴</button>
					<button type="button" class="btn btn-outline-primary btn-lg">변경</button>
				</div>
			</form> <!-- Form END -->
			</div> <!-- Row END -->
		</div>
	</div>
<script>
// Add the following code if you want the name of the file appear on select
$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>