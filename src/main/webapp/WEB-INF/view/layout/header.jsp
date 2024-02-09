<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>MyBank</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="/assets/img/favicon.png" rel="icon">
  <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="/assets/vendor/aos/aos.css" rel="stylesheet">
  <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="/assets/css/style.css" rel="stylesheet">
  <!-- 외부 스타일 시트 가져오기 -->
  <link rel="stylesheet" href="/css/styles.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="fixed-top  header-transparent ">
    <div class="container d-flex align-items-center justify-content-between">

      <div class="logo">
        <h1><a href="/index" style="font-weight:bold">MyBank</a></h1>
        <!-- Uncomment below if you prefer to use an image logo -->
        <!-- <a href="index.html"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->
      </div>

      <nav id="navbar" class="navbar">
        <ul>
          <li><a class="nav-link scrollto active" href="/">Home</a></li>
          <li class="dropdown"><a href="#"><span>뱅킹 서비스</span> <i class="bi bi-chevron-down"></i></a>
            <ul>
              <li class="dropdown"><a href="/account/list"><span>마이페이지</span> <i class="bi bi-chevron-right"></i></a>
                <ul>
                  <li><a href="/account/save">계좌 생성</a></li>
                  <li><a href="/account/list">나의 계좌 목록</a></li>
                
                </ul>
              </li>
         		<li><a href="/account/withdraw">출금</a></li>
                <li><a href="/account/deposit">입금</a></li>
                <li><a href="/account/transfer">이체</a></li>
            </ul>
          </li>
         <c:choose>
	    	<c:when test="${principal != null}">
         		<li><a class="getstarted scrollto" href="/user/logout">로그아웃</a></li>
          	</c:when>
		    <c:otherwise>
          		<li><a class="getstarted scrollto" href="/user/sign-in">로그인</a></li>
          		<li><a class="getstarted scrollto" href="/user/sign-up">회원가입</a></li>
          </c:otherwise>
		 </c:choose>
        </ul>
        <i class="bi bi-list mobile-nav-toggle"></i>
      </nav><!-- .navbar -->
    </div>
  </header><!-- End Header -->