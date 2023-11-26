<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/headerBo.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="assets/js/jquery.js"></script>
<script type="text/javascript">
	function checkDate(){
		//alert("테스트");
		var curDate = new Date();
		var curDate1 = curDate.getFullYear()+"-"+(curDate.getMonth()+1)+"-"+curDate.getDate();
		//alert(curDate1);
		var sdate = $('input[name=q_sdate]').val();
		var edate = $('input[name=q_edate]').val();
		//alert("sdate->"+sdate);
		//alert("edate->"+edate);
		if(sdate>edate){
			alert("이벤트 기간 설정을 잘 못 되었습니다.");
			return false;
/* 		} else if(curDate1>=sdate&&curDate1<=edate){
			alert("이벤트 기간을 도중으로 생성할 수 없습니다.");
			return false; */
		} else if(curDate1 >= edate){
			alert("기간이 종료된 이벤트는 생성하실 수 없습니다.");
			return false;
		} else if(curDate1 < edate){
			document.forms["createQuizForm"].action;
			return true;
		} else {
			alert("오류 발생");
			return false;
		}
	}
	
	function convertToDate(dateStr) {
		var parts = dateStr.split('-');
		return new Date(parts[0], parts[1] - 1, parts[2]);
		}

</script>
<style>
	form {
		width:80%;
	}
	
	th{
		width: 20%;	
		vertical-align: middle;
	}
	
	.title{
		font-size: 25px; 
		font-weight: bold;
	}
	
	.s_title{
		text-align: center;
	}
	
	textarea {
	 width: 80%;
	 height: 150px;
}
	
</style>
<body>
<form id="createQuizForm" action="createQuiz" onsubmit="return checkDate()" enctype="multipart/form-data" method="post">
	<table class="table">
		<tr>
			<th class="title" colspan="2">퀴즈 설정</th>
			</tr>
			<tr>
			<th class="table-active">이벤트 이름</th>
			<td><input type="text" name="q_title" style="width: 300px;" required="required"></td>	
		</tr>
		<tr>
			<th class="table-active">이벤트 기간</th>
			<td><input type="date" name="q_sdate" required="required"> ~ <input type="date" name="q_edate" required="required"></td>
		</tr>
		<tr>
			<th class="table-active">사진 등록</th>
			<td><input type="file" name="file1" required="required"></td>
		</tr>
		<tr>
			<th class="table-active">출석 혜택지급</th>
			<td>
				<table>
				<tr>
					<th class="s_title">포인트 </th>
					<td><input type="number" name="q_point" min="1" maxlength="3" style="margin-left: 10px; width: 80px;" required="required"> point</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<th class="table-active">질문</th>
			<td><textarea name="q_question" required="required"></textarea></td>
		</tr>
		<tr>
			<th class="table-active">선택</th>
			<td>
				<table>
					<tr>
						<th class="s_title">1 </th><td><input type="text" name="q_select1" style="width: 300px;" required="required"></td>
					</tr>
					<tr>
						<th class="s_title">2 </th><td><input type="text" name="q_select2" style="width: 300px;" required="required"></td>
					</tr>
					<tr>
						<th class="s_title">3 </th><td><input type="text" name="q_select3" style="width: 300px;" required="required"></td>
					</tr>
					<tr>
						<th class="s_title">4 </th><td><input type="text" name="q_select4" style="width: 300px;" required="required"></td>
					</tr>
				</table>
			</td>						
		</tr>
		<tr>
			<th scope="row" class="table-active">정답</th>
			<td>
				<select name="q_answer" required="required" style="width: 50px; height: 25px; text-align: center;">
					<option id="select" value="1" selected="selected">1
					<option id="select" value="2">2
					<option id="select" value="3">3
					<option id="select" value="4">4
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td style="float: right; margin-right: 40px; margin-top: 10px;">
				<input type="submit" class="btn btn-soft-primary mb-2" value="생성" id="button"> 
				&nbsp;
				<input type="button" class="btn btn-soft-danger mb-2"  value="취소" id="button">
			</td>
		</tr>
	</table>
</form>
</body>
</html>