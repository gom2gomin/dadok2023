<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/headerFo.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
		function changeChk(p_point){
			//alert("변경!");
			//alert("보유 포인트 : ${member.m_point }");
			//alert("입력 포인트 : "+p_point);
			
			if(p_point <= '${member.m_point }'){
				//alert("사용 가능합니다.");
				$("#pointMsg").html("사용 가능합니다.");
				
				// 결제정보의 할인/부가결제에 반영
				const p_point_result = Number(p_point).toLocaleString();
				//alert("p_point_result : "+p_point_result);
				$("#o_point_result").html(p_point_result);
				
				// 결제정보의 최종결제금액에 반영
				const p_deliv_price = Number(document.getElementById("o_deliv_price").value);
				//alert("p_deliv_price : "+p_deliv_price);
				const nb_price_num = Number('${newbook.nb_price}');
				const p_pay_price = (nb_price_num + p_deliv_price - p_point);
				//alert("p_pay_price : "+p_pay_price);
				const p_pay_price_result = p_pay_price.toLocaleString();
				//alert("p_pay_price_result : "+p_pay_price_result);
				$("#o_pay_price").html(p_pay_price_result);
				$("#o_pay_price").val(p_pay_price_result);
				
				// 결제정보의 적립혜택에 반영
				const p_point_save = (p_pay_price * 0.01);
				const p_point_save_result = p_point_save.toLocaleString();
				$("#o_point_save").html(p_point_save_result);
				
			} else {
				//alert("보유 포인트보다 많이 사용할 수 없습니다.");
				$("#pointMsg").html("보유 포인트보다 많이 사용할 수 없습니다.");
				$("#o_point").val("");
			}
		}
	</script>
</head>
<body>
<!-- 배송비 결정 -->
<c:choose>
	<c:when test="${newbook.nb_price > 50000}">
		<c:set var="o_deliv_price" value="0"/>
		<c:set var="o_deliv_price_express" value="무료 배송"/>
	</c:when>
	<c:otherwise>
		<c:set var="o_deliv_price" value="3000"/>
		<c:set var="o_deliv_price_express" value="3,000 원"/>
	</c:otherwise>
</c:choose>
<input type="hidden" id="o_deliv_price" name="o_deliv_price" value="${o_deliv_price }">


  <!-- section -->

  <section class="my-lg-14 my-8">
      <!-- container -->
    <div class="container">
      <div class="row">
          <!-- col -->
        <div class="offset-lg-2 col-lg-8 col-12">
          <div class="mb-8">
              <!-- heading -->
            <h3 style="align: center;">선물하기</h3><p>
          </div>
          <!-- form -->
          <form class="row" action="foGivingGiftAction">
            <h5 class="h5">보내는 사람</h5><p>
              <!-- input -->
            <div class="col-md-12 mb-3">
              <label class="form-label"> 이름</label>
              <h6 class="h6">${member.m_name }</h6>
            </div>
            <div class="col-md-12 mb-3">
              <!-- input -->
              <label class="form-label"> 이메일</label>
              <h6 class="h6">${member.m_email }</h6>
            </div>
            <div class="col-md-12 mb-3">
              <!-- input -->
              <label class="form-label"> 휴대전화</label>
              <h6 class="h6">${member.m_ph }</h6>
            </div>
            
            <p><p><hr><p><p>
            
            <h5 class="h5">받는 사람</h5><p>
              <!-- input -->
            <div class="col-md-12 mb-3">
              <label class="form-label" for="o_gift_name"> 이름<span class="text-danger">*</span></label>
              <input type="text" id="o_gift_name" class="form-control" name="o_gift_name" placeholder="홍길동" required>
            </div>
            <div class="col-md-12 mb-3">
              <label class="form-label" for="o_gift_mail"> 이메일<span class="text-danger">*</span></label>
              <input type="text" id="o_gift_mail" class="form-control" name="o_gift_mail" placeholder="hgd@dadok.com" required>
            </div>
            <div class="col-md-12 mb-3">
              <!-- input -->
              <label class="form-label" for="o_gift_ph"> 휴대전화<span class="text-danger">*</span></label>
              <input type="text" id="o_gift_ph" name="o_gift_name" class="form-control" placeholder="010-0000-0000" required>
            </div>
            
            <p><p><hr><p><p>
            
            <h5 class="h5">메시지 카드</h5><p>
            <div class="col-md-12 mb-3">
            	<input type="radio" name="o_gift_card" value="card1"><img alt="card1" src="../assets/images/gift/giftcard1.png"><br>
            	<input type="radio" name="o_gift_card" value="card2"><img alt="card2" src="../assets/images/gift/giftcard2.png"><br><p><p>
            	<textarea rows="3" name="o_gift_msg" class="form-control" placeholder="메시지를 입력해주세요."></textarea>
            </div>
            
            <p><p><hr><p><p>
            
            <h5 class="h5">주문 상품</h5><p>
				<div class="row">
				  <div class="col-12">
				     <div style="border: 1px solid #dfe2e1;
				     			 border-radius: 0.5rem;
				     			 padding: 0.55rem 1rem;">
				     <div>
				        <!-- card body -->
				        <div class="card-body">
				           <div class=" row align-items-center">
				              <!-- col -->
				              <div class="col-md-4 col-12">
				                 <div class="text-center position-relative ">
				                       <!-- img --><img src="${newbook.nb_image}" alt="${newbook.nb_title}" class="mb-3 img-fluid" style="height: 150px;">
				                 </div>
				              </div>
				              <div class="col-md-8 col-12 flex-grow-1">
				                 <!-- heading -->
				                 <h2 class="fs-6">${newbook.nb_title}</h2>
				                 <div class="text-small mb-1"><small><fmt:formatNumber value="${quantity}" groupingUsed="true"/>개</small></div>
				                 <div class=" mt-6">
				                    <!-- price -->
				                    <div><span class="text-dark"><fmt:formatNumber value="${newbook.nb_price}" groupingUsed="true"/>원</span></div>
				                 </div>
				              </div>
				           </div>
				        </div>
				     </div>
				     </div>
				  </div>
				</div>
				
            <p><p><hr><p><p>
            
            <h5 class="h5">할인/부가결제</h5><p>
              <!-- input -->
            <div class="col-md-12 mb-3">
              <label class="form-label" for="o_point"> 사용 포인트  (보유 : <fmt:formatNumber value="${member.m_point }" groupingUsed="true"/>원)</label>
              <span class="text-danger" id="pointMsg" ></span>
              <input type="text" id="o_point" class="form-control" name="o_point"
              		 onchange="changeChk(o_point.value)">
            </div>
				
            <p><p><hr><p><p>
            
            <h5 class="h5">결제 정보</h5><p>
              <!-- input -->
            <div class="col-md-12 mb-3">
            	<table style="width: 100%;">
            		<tr height="40px">
            			<td class="form-label" width="70%">상품금액</td>
            			<td class="h6" width="30%" align="right"><fmt:formatNumber value="${newbook.nb_price}" groupingUsed="true"/> 원</td>
            		</tr>
            		<tr height="40px">
            			<td class="form-label" width="70%">배송비</td>
            			<td class="h6" width="30%" align="right">${o_deliv_price_express }</td>
            		</tr>
            		<tr height="40px">
            			<td class="form-label" width="70%">할인/부가결제</td>
            			<td class="h6" width="30%" align="right"><span id="o_point_result">0</span> 원</td>
            		</tr>
            		<tr style="border-top: 1px solid #dfe2e1;
            				   border-bottom: 1px solid #dfe2e1;
            				   height: 60px">
            			<td class="text-danger" width="70%">최종 결제 금액</td>
            			<td class="text-danger" width="30%" align="right">
            				<span id="o_pay_price">
            					<fmt:formatNumber value="${newbook.nb_price + o_deliv_price}" groupingUsed="true"/>
            				</span> 원
            			</td>
            		</tr>
            		<tr height="40px">
            			<td class="form-label" width="70%">적립 혜택</td>
            			<td class="h6" width="30%" align="right">
            				<span id="o_point_save">
            					<fmt:formatNumber value="${(newbook.nb_price + o_deliv_price) * 0.01}" groupingUsed="true"/>
            				</span> 원
            			</td>
            		</tr>
            	</table>
            </div>
				
            <p><p><hr><p><p>
            
            <h5 class="h5">결제 수단</h5><p>
            	<div class="d-grid gap-2 col-6 mx-auto">
	            	<button type="button" class="btn btn-soft-secondary mb-2">카카오</button>
	            	<button type="button" class="btn btn-soft-secondary mb-2">토스</button>
            	</div>
            	
            <p><p><p><p><p><p>
            	
            <div class="d-grid gap-2">
	            <input class="btn btn-primary" type="submit" value="결제하기">
            </div>


          </form>

        </div>
      </div>
    </div>

  </section>

<!-- hidden value -->
<input type="hidden" name="m_num" value="${member.m_num }"> 
<input type="hidden" name="o_pay_price" value="" id="o_pay_price"> <!-- ajax 통해 삽입 -->

<%@ include file="../common/footerFo.jsp" %>
</body>
</html>