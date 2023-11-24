package com.choongang.s202350103.htDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.choongang.s202350103.domain.KakaoPayApprovalVO;
import com.choongang.s202350103.model.Cart;
import com.choongang.s202350103.model.Member;
import com.choongang.s202350103.model.NewBook;
import com.choongang.s202350103.model.Orderr;
import com.choongang.s202350103.model.Review;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderrDaoImpl implements OrderrDao {
	private final SqlSession session;
	private final HttpSession https; 
	
	private final PlatformTransactionManager transactionManager;
	
	@Override
	public int orderTotal() {
		int result = 0;
		System.out.println("OrderDaoImpl orderTotal() Start...");
		try {
			result = session.selectOne("htOrderTotal");
			
			System.out.println("OrderDaoImpl orderTotal() result--> " + result);
		}catch (Exception e) {
			System.out.println("OrderDaoImpl orderTotal Exception--> "+ e.getMessage());
		}
		
		return result;
	}

	@Override
	public List<NewBook> orderOne(NewBook newBook) {
		System.out.println("OrderDaoImpl orderOne() Start...");
		
		List<NewBook> orderOne = null;
		try {
			orderOne = session.selectList("htOrderOne", newBook);
			System.out.println("OrderrDaoImpl orderOne--> "+ orderOne);
		}catch (Exception e) {
			System.out.println("OrderrDaoImpl orderOne Exception -> " + e.getMessage());
		}
		return orderOne;
	}

	@Override
	public List<Cart> orderList(Cart cart, Member member) {
		System.out.println("OrderDaoImpl orderList() Start...");
		member =(Member) https.getAttribute("member");
		cart.setM_num(member.getM_num());
		List<Cart> orderList = null;
		try {
			orderList = session.selectList("htOrderList", cart);
			System.out.println("orderList--> "+ orderList);
		}catch (Exception e) {
			System.out.println("OrderrDaoImpl orderList Exception -> " + e.getMessage());
		}
		return orderList;
	}

	@Override
	public void orderInsert(Orderr orderr, List<Cart> list) {// 프로시저를 사용하기 때문에 void이어야 한다. 
		System.out.println("OrderDaoImpl orderList() Start...");
		
		// https://zorba91.tistory.com/189 --> list문을 insert하는 법
		
		//Transaction 관리
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			// order insert (여기서 프로시저를 통해서 o_order_num을 orderr객체에 담아서 가지고 나온다.) 
			session.selectOne("htOrderInsert", orderr); //프로시저를 사용하므로 return값이 없어도 된다. orderr DTO에 값을 가지고 나온다. 파라미터 out이 있기 때문이다.
			System.out.println("Dao orderInsert orderr-->"+orderr);
		
			Map<String, Object> params = new HashMap<String, Object>();
		    params.put("orderr", orderr);
		    params.put("list", list);
		    
			// orderDetail insert
			int od_result = session.insert("htOrderDetailInsert", params);
			System.out.println("Dao htOrderInsert od_result--->" + od_result);
			System.out.println("orderr.getO_pay_price()--> " + orderr.getO_pay_price());
			System.out.println("orderr.getM_num()--> " + orderr.getM_num());
			
			// member 포인트 update
			int update_result = session.update("htMemberPointUpdate", orderr);
			System.out.println("Dao htOrderInsert update_result--->" + update_result);
			
			// 포인트 이력 insert(구매시)
			int pay_point_insert_result = session.insert("htPayPointInsert", orderr);
			System.out.println("Dao htOrderInsert pay_point_insert_result--->" + pay_point_insert_result);
			
			// 포인트 이력 insert(사용시)
			int use_point_insert_result = session.insert("htUsePointInsert", orderr);
			System.out.println("Dao htOrderInsert use_point_insert_result--->" + use_point_insert_result);
			
			// 장바구니 삭제(장바구니 결제일 경우)
			if(orderr.getPaymentType() == 2) {
				int cart_delete_result = session.delete("htCartDelete",orderr);
				System.out.println("Dao htOrderInsert cart_delete_result--->" + cart_delete_result);
			}
			
			//commit
			transactionManager.commit(txStatus);
		}catch (Exception e) {
			System.out.println("OrderrDaoImpl orderInsert Exception -> " + e.getMessage());
			//rollback
			transactionManager.rollback(txStatus);
		}
		return ;
	}

	// 카카오페이 결제 데이터
	@Override
	public Orderr orderPayment(Orderr orderr) {
		System.out.println("OrderDaoImpl orderPayment() Start...");
		Orderr orderr2 = null;
		try {
			orderr2 = session.selectOne("htOrderPayment", orderr);
			System.out.println("OrderrDaoImpl orderPayment orderr--> "+ orderr2);
		}catch (Exception e) {
			System.out.println("OrderrDaoImpl orderPayment Exception -> " + e.getMessage());
		}
		return orderr2;
	}

	@Override
	public int paySuccess(KakaoPayApprovalVO ka) {
		System.out.println("OrderDaoImpl PaySuccess() Start...");
		int result = 0;
		try {
			System.out.println("OrderrDaoImpl PaySuccess ka--> "+ ka);
			result = session.update("htOrderUpdate", ka);
			System.out.println("OrderrDaoImpl PaySuccess result--> "+ result);
		}catch (Exception e) {
			System.out.println("OrderrDaoImpl PaySuccess Exception -> " + e.getMessage());
		}
		return result;
	}


}
