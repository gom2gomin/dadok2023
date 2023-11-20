<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/headerFo.jsp" %>
<%@ include file="../common/sideFo.jsp" %>
   <!-- 정산하기 --> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="writeFormObTrans" method="post">
<p class="text-center fs-1 ">중고 도서 정산 안내 </p>
	<div class="text-center  position-relative ">
	<img src="assets/images/png/calcul.png" alt="판매신청"
                      class="mb-3 img-fluid">
	
	</div>
					<input type="hidden" name="m_id" value="${oldBook.m_id}">
					<input type="hidden" name="m_name" value="${oldBook.m_name}">
					<input type="hidden" name="m_num" value="${oldBook.m_num }">
					<input type="hidden" name="nb_num" value="${oldBook.nb_num }">	
					<input type="hidden" name="nb_price" value="${oldBook.nb_price }">
<br>
<hr>
	<p class="text-center fs-1 ">정산 예정 금액  </p>
			<p class="text-end fs-5 ">원본 책 가격 	<fmt:formatNumber type="number" pattern="###,###,###,###,###,###" value="${oldBook.nb_price}"/>원    </p> 
			<p class="text-end fs-5 ">A등급 		<fmt:formatNumber type="number" pattern="###,###,###,###,###,###" value="${oldBook.nb_price *0.6}"/>원    </p>
			<p class="text-end fs-5 ">B등급 		<fmt:formatNumber type="number" pattern="###,###,###,###,###,###" value="${oldBook.nb_price *0.5}"/>원   </p>
			<p class="text-end fs-5 ">C등급 		<fmt:formatNumber type="number" pattern="###,###,###,###,###,###" value="${oldBook.nb_price *0.4}"/>원   </p>
			<p class="text-end fs-5 ">D등급		<fmt:formatNumber type="number" pattern="###,###,###,###,###,###" value="${oldBook.nb_price *0.3}"/>원   </p>
			
	  <br>
  <hr>
		  <p class="text-start fs-4 ">정산 계좌    </p>
		 <div class="input-group mb-3">
				<select name="ob_acc_name" >
						<option value="1">기업은행</option>
						<option value="2">우리은행</option>
						<option value="3">하나은행</option>
						<option value="4">제일은행</option>
						<option value="5">농      협</option>
						<option value="6">신한은행</option>
						<option value="7">국민은행</option>
						<option value="8">카카오뱅크</option>
						<option value="9">토스    뱅크</option>
				</select> 
 

		<input type="hidden" name="ob_status" value="1">
	    <input type="text"   name="ob_acc_num"  maxlength="12" pattern="[0-9]+" class="form-control" placeholder="계좌 번호를 입력해주세요" title="숫자만 입력해주세요"
	    	 required/>
			                          
			   
                       
			 </div> 
			 
			 <br>
			 <hr>
			 <div class="d-grid gap-2">
				    <button class="btn btn-success mb-2" type="submit">판매 계속</button>
	    	 </div>
 </form>
</body>
<%@ include file="../common/footerFo.jsp" %>
</html>