<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/headerFo.jsp"%>
<%@ include file="../common/sideFo.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<!-- row -->
		      <div class="row">
		        <div class="col-lg-12">
		          <div class="card py-1 border-0 mb-8">
           			 <div class="mb-8">
			         <!-- heading -->
				         <h2 class="mb-2">판매 현황</h2>
				         <p><a href="#">${member.m_id } 님의 판매 목록입니다.</a></p>
				               총 상품 개수 : ${totalSellCnt }		
			      	</div>
		          <div>
		            <!-- table -->
		            <div class="table-responsive">
		              <table class="table text-nowrap table-with-checkbox">
		              
		                <thead class="table-light">
		                  <tr>
		                    <th></th>
		                    <th>제목</th>
		                    <th>등급</th>
		                    <th>매입가</th>
		                    <th>판매 예상가</th>
		                    <th>등록일자</th>
		                  </tr>
		                </thead>
		                <tbody>
		                <c:forEach var="oldbook" items="${oldBookSellList }">
		                  <tr>
		                    <td class="align-middle">
		                      <a href="newbookDetail?nb_num=${oldbook.nb_num }"><img src="${oldbook.nb_image }"
		                          class="icon-shape icon-xxl" alt=""></a>
		                    </td>
		                    <td class="align-middle">
		                      <div>
		                        <h5 class="fs-6 mb-0"><a href="newbookDetail?nb_num=${oldbook.nb_num }" class="text-inherit">${oldbook.nb_title }</a></h5>
		                         <small>${oldbook.nb_writer }</small><br>
		                         <small>${oldbook.nb_publisher }</small><br>
		                      </div>
		                    </td>
		                    <td class="align-middle">${oldbook.ob_grade }등급</td>
		                    <td class="align-middle"><fmt:formatNumber value="${oldbook.ob_pur_price }" pattern="#,###" /> 원</td>
		                    <td class="align-middle"><fmt:formatNumber value="${oldbook.ob_sell_price }" pattern="#,###" /> 원</td>
		                    <td class="align-middle"><fmt:formatDate value="${oldbook.ob_write_date }" pattern="yyyy년MM월dd일"/></td>
		                  </tr>
		                  </c:forEach>
		                </tbody>
		               
		              </table>
		            </div>
		
		          </div>
		        </div>
		
		      </div>
		
		
		
		    </div>

<nav aria-label="Page navigation example">
	  <ul class="pagination justify-content-center">
		    <li class="page-item disabled">
		      <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
		    </li>
		    <li class="page-item"><a class="page-link" href="#">1</a></li>
		    <li class="page-item"><a class="page-link" href="#">2</a></li>
		    <li class="page-item"><a class="page-link" href="#">3</a></li>
		    <li class="page-item">
	     	 <a class="page-link" href="#">Next</a>
	    </li>
	  </ul>
	</nav>


<%@ include file="../common/footerFo.jsp"%>

</body>
</html>