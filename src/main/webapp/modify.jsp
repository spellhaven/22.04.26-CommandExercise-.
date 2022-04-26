<%@page import="com.gyojincompany.ex.MemberDto"%>
<%@page import="com.gyojincompany.ex.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberDao dao = new MemberDao();
	String id = (String) session.getAttribute("id");
	MemberDto dto = dao.getMemberInfo(id);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
</head>
<body>
	<form action="modifyOk.jsp" method="post">
		아이디 : <%= dto.getId() %> <br><br>
		비밀번호 : <input type=password name="pw"><br><br>
		이름 : <%= dto.getName() %> <br><br>
		이메일 : <input type="text" name="email" value="<%= dto.getEmail() %>"><br><br>
		주소 : <input type="text" name="address" size="50" value="<%= dto.getAddress() %>"><br><br>
		<input type="submit" value="수정완료">&nbsp;&nbsp;&nbsp;
		<input type="button" value="취소" onclick="javascript:window.location='main.jsp'">	
	
	</form>
</body>
</html>