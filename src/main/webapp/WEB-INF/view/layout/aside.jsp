<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
        <aside>
            <div class="d-flex flex-column p-3 bg-light">
                <a href="/user/sign-in" class="mb-3 me-md-auto link-dark text-decoration-none">
                    <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
                    <span style="font-size: 21px; font-weight:bold;">마이페이지</span>
                </a>
                <ul class="nav nav-pills flex-column mb-auto">
                    <li class="nav-item">
        				<a href="/account/save" class="nav-link" aria-current="page">
                            계좌 생성
                        </a>
                    </li>
                    <li>
                        <a href="/account/list" class="nav-link" >
                            계좌 목록
                        </a>
                    </li>
                </ul>
                <hr>
                <div class="dropdown">
                    <a href="#" class="d-flex align-items-center link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="${principal.setupUserImage()}" alt="" width="32" height="32" class="rounded-circle me-2">
                        <strong style="margin-left:5px">${principal.fullname}</strong>
                    </a>
                    <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
                        <li><a class="dropdown-item" href="/user/profile">계정</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="/user/logout">로그아웃</a></li>
                    </ul>
                </div>
            </div>
        </aside>
        
<script>
	//현재 페이지의 URI 가져오기
	var currentUri = window.location.pathname;
	
	// 모든 링크 요소 가져오기
	var links = document.querySelectorAll(".nav-link");
	
	// 각 링크 요소에 대해 현재 페이지 URI와 비교하여 active 클래스를 설정
	links.forEach(function(link) {
	    if (link.getAttribute("href") === currentUri) {
	        link.classList.add("active");
	    }
	});
</script>