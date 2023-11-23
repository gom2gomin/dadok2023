<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="content">
		<c:if test="${result == 1}">
			<script type="text/javascript">
				alert("결제 완료~~!! ");
				location.href="memberMyOrder?m_num=${member.m_num}";
			</script>
		</c:if>
		<c:if test="${result == 2}">
			<script type="text/javascript">
				alert("결제 실패... ");
				location.href="memberMyOrder?m_num=${member.m_num}";
			</script>
		</c:if>
		<c:if test="${result == 3}">
			<script type="text/javascript">
				alert("결제 취소완료..!! ");
				location.href="memberMyOrder?m_num=${member.m_num}";
			</script>
		</c:if>
	</div>
</body>
</html>