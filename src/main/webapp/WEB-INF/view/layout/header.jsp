<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<script>


</script>
<html lang="en">
<head>
  <title>my bank</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <!-- 외부 스타일 시트 가져오기 -->
  <link rel="stylesheet" href="/css/styles.css">

</head>
<body>

	<div class=" text-center banner--img" >
	  <img class="header-logo"src="/images/logo_white.png" alt="MYBANK 로고">
	</div>
	
	<nav class="navbar navbar-expand-sm bg-light navbar-dark">
	  <a class="navbar-brand text-dark" href="#">Menu</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <div class="collapse navbar-collapse" id="collapsibleNavbar">
	    <ul class="navbar-nav text-dark">
		    <c:choose>
		    	<c:when test="${principal != null}">
				     <li class="nav-item">
				        <a class="nav-link text-dark" href="/user/logout">로그아웃</a>
				     </li>  
		    	</c:when>
		    	<c:otherwise>
	    		      <li class="nav-item">
				        <a class="nav-link text-dark" href="/user/sign-in">로그인</a>
				      </li>
				      <li class="nav-item">
				        <a class="nav-link text-dark" href="/user/sign-up">회원가입</a>
				      </li>
		    	</c:otherwise>
		    </c:choose>
	    </ul>
	  </div>  
	</nav>
<div class="container" >
    <div class="row">
        <!-- Sidebar 영역 -->
        <aside class="col-md-3">
            <div class="d-flex flex-column p-3 bg-light">
                <a href="/user/sign-in" class="mb-3 me-md-auto link-dark text-decoration-none">
                    <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
                    <span style="font-size: 21px; font-weight:bold;">뱅킹 서비스</span>
                </a>
                <ul class="nav nav-pills flex-column mb-auto">
                    <li class="nav-item">
                        <a href="/account/save" class="nav-link active" aria-current="page">
                            <svg class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>
                            계좌 생성
                        </a>
                    </li>
                    <li>
                        <a href="/account/list" class="nav-link link-dark">
                            <svg class="bi me-2" width="16" height="16"><use xlink:href="#speedometer2"></use></svg>
                            계좌 목록
                        </a>
                    </li>
                    <li>
                        <a href="/account/withdraw" class="nav-link link-dark">
                            <svg class="bi me-2" width="16" height="16"><use xlink:href="#table"></use></svg>
                            출금
                        </a>
                    </li>
                    <li>
                        <a href="/account/deposit" class="nav-link link-dark">
                            <svg class="bi me-2" width="16" height="16"><use xlink:href="#grid"></use></svg>
                            입금
                        </a>
                    </li>
                    <li>
                        <a href="/account/transfer" class="nav-link link-dark">
                            <svg class="bi me-2" width="16" height="16"><use xlink:href="#people-circle"></use></svg>
                            이체
                        </a>
                    </li>
                </ul>
                <hr>
                <div class="dropdown">
                    <a href="#" class="d-flex align-items-center link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="/images/profile.jpg" alt="" width="32" height="32" class="rounded-circle me-2">
                        <strong style="margin-left:5px">이현정</strong>
                    </a>
                    <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
                        <li><a class="dropdown-item" href="#">New project...</a></li>
                        <li><a class="dropdown-item" href="#">Settings</a></li>
                        <li><a class="dropdown-item" href="#">Profile</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Sign out</a></li>
                    </ul>
                </div>
            </div>
        </aside>


	
	