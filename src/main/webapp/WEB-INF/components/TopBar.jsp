<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.oracle.projectGo.type.UsersRoleType" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="com.oracle.projectGo.service.UsersService" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2023-12-05
  Time: 오후 4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="topbar" class="app-navbar fixed-top" >

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(UsersRoleType.ANONYMOUS.getLabel());
        System.out.println(auth.getAuthorities()+ auth.getName());
        boolean isAuthenticated = !role.contains(UsersRoleType.ANONYMOUS.getLabel());
        boolean isAdmin = role.contains(UsersRoleType.ADMIN.getLabel());
    %>
    <div class="container">
        <nav class="navbar  navbar-expand-md navbar-light  justify-content-between ">
            <div class="col-3">
                <a class="navbar-brand" href="/">
                    <img src="/asset/logo.png" height="50" alt="Logo">
                </a>
            </div>
            <div class=" col-7 d-flex justify-content-end ">
                <div>
                    <button class="navbar-toggler" type="button"
                            data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse justify-content-end " id="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link app-nav-link" href="/admin/board/sitemap">이용안내</a></li>
                        <li class="nav-item"><a class="nav-link app-nav-link" href="/subscribe/subscribeView">구독서비스</a></li>
                        <li class="nav-item"><a class="nav-link app-nav-link" href="/learning/signUpLearningGroup">학습서비스</a></li>
                        <li class="nav-item"><a class="nav-link app-nav-link" href="/group/listLearningContent">교육자마당</a></li>
                        <li class="nav-item"><a class="nav-link app-nav-link" href="/lookup/board/noticeBoardList">조회마당</a></li>

                        <% if (isAdmin) {
                        %>
                            <li class="nav-item"><a class="nav-link app-nav-link" href="/game/gameContentSelect">운영마당</a></li>
                        <% } %>
                    </ul>
                </div>
            </div>
            <div class=" col-2 d-flex justify-content-end">


                <% if (isAuthenticated) {
                %>

                <a href="/userUpdateForm1" style="margin-right: 10px;">정보수정</a>
                <a href="/logout">로그아웃</a>
                <% } else { %>
                <a href="/login">로그인</a>
                <% } %>

            </div>
        </nav>
    </div>

</header>
