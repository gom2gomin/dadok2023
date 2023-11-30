<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/headerBo.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="assets/js/jquery.js"></script>
<script type="text/javascript">

	
	function mouse_click(){
		alert("포인트 유형\n 1 주문 적립\n 2 출석 이벤트\n 3 퀴즈 이벤트\n 4 회원가입\n 5 추천인 코드\n 6 주문 포인트 사용\n 7 관리자 조정\n 8 포인트 충전");
	}
	
	function boMemberPointUpdate(m_num) {
			var popupW = 800;
			var popupH = 350;
			var left = Math.ceil((window.screen.width - popupW)/2);
			var top = Math.ceil((window.screen.height - popupH)/2);
		
			var url = "boMemberPointUpdate?m_num="+m_num;
	        var name = "boMemberPointUpdate";
	        
	        window.open(url, name, 'width='+popupW+',height='+popupH+',left='+left+',top='+top+',scrollbars=yes,resizable=no,toolbar=no,titlebar=no,menubar=no,location=no')
	}


</script>
<style type="text/css">
	table{
		width: 80%;
	}
	
	h2{
		text-align: center;
	}	
	.center-text {
	  	text-align: center; /* 텍스트 가운데 정렬 */
	  	position: absolute;
	  	top: 30%;
	  	left: 60%;
	  	font-weight: bold;
	  	color:black;
	  	transform: translate(-50%, -50%); /* 가운데 정렬을 위한 변환 */
	}
</style>
</head>
<body>
<div class="mb-8">
   <!-- heading -->
   <h1 class="mb-1">포인트 목록</h1>
</div>
 <div class="mb-8">
  <!-- heading -->
   <p class="fs-2 mb-3"><strong>${member.m_name} 님 (${member.m_id })</strong></p>
</div>

<p class="fs-4 mb-5">총 포인트 : <mark >${sum}P</mark></p>

<c:if test="${empty memberPoint }">
			<div class="row">
				<div class="center-text mt-14">
					발생한 포인트 이력이 없습니다.
				</div>
			</div>
</c:if>
<c:if test="${not empty memberPoint }">	  
<input type="hidden" name="m_num" id="m_num" value="${m_num  }">       
<table class="table" style="text-align: center">
	<thead class="table-light">
		<tr>
			<th scope="col">번호</th>
			<th scope="col">포인트번호</th>
			<th scope="col">회원번호</th>
			<th scope="col">발행페이지 코드</th>
			<th scope="col">발행일시</th>
			<th scope="col">포인트유형	</th>
			<th scope="col">지급포인트</th>
		</tr>
		
	</thead>
	<tbody>
		
		<c:forEach var="memberPoint" items="${memberPoint }">
				
				<tr>
				<th scope="row">${memberPoint.rn }</th>
				<td>${memberPoint.p_num }</td>
				<td>${memberPoint.m_num }</td>
				<td>
				<c:choose>
					<c:when test="${memberPoint.o_order_num != 0}"> ${memberPoint.o_order_num}</c:when>
					<c:when test="${memberPoint.q_num != 0}">${memberPoint.q_num}</c:when>
					<c:when test="${memberPoint.a_num != 0}">${memberPoint.a_num}</c:when>
					<c:otherwise>00000</c:otherwise>
				</c:choose>
				</td>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd" value="${memberPoint.a_par_pdate }"/>
				</td>
				<td>
					<a onclick="mouse_click()">${memberPoint.p_list_type }</a>	
				</td>
				<td>${memberPoint.m_point }</td>
						
			</tr>
		</c:forEach>
	</tbody>
</table>

<!-- <div class="d-grid gap-2 d-md-flex justify-content-center"> -->
<!--   	<button class="btn btn-soft-primary" type="button" onclick="boMemberPointUpdate(m_num.value)">수정하기</button> -->
<!--   	<input type="button"  class="btn btn-secondary mb-2" id="cancleButton"  value="목록가기"> -->
<!-- </div>	 -->
<div class="d-grid gap-2 d-md-flex justify-content-center">
	<input type="button" class="btn btn-primary mb-2" value="수정하기" style="margin-right: 10px" onclick="boMemberPointUpdate(m_num.value)">
	<input type="button"  class="btn btn-secondary mb-2" id="cancleButton"  value="목록가기">
</div>
<script type="text/javascript">
	var cancleButton = document.getElementById("cancleButton");
	cancleButton.addEventListener('click',function(){
		location.href='adminMemberList';
	});
</script>				

<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
	 	<c:if test="${page.startPage > page.pageBlock }">
			 <li class="page-item justify-content-center">					
				<a class="page-link mx-1 text-body"  href="selectMemberPoint?currentPage=${page.startPge-page.pageBlocck }">이전</a>
			</li>
		</c:if>
			<c:forEach var="i" begin="${page.startPage }" end="${page.endPage}">
			 <li class="page-item justify-content-center">
					<a class="page-link mx-1 text-body"  href="selectMemberPoint?currentPage=${i }&m_num=${memberPoint.m_num }">${i}</a>
			</li>
		</c:forEach>
			
			<c:if test="${page.endPage < page.totalPage }">
			 <li class="page-item justify-content-center">		 
				<a class="page-link mx-1 text-body"href="selectMemberPoint?currentPage=${page.startPage+page.pageBlock }">다음</a>
			</li>
		</c:if>
	</ul>
</nav>
</c:if>
<br>
<hr>
<br>
	
</body>
</html>