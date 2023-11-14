package com.choongang.s202350103.htDao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.choongang.s202350103.model.Cart;
import com.choongang.s202350103.model.Member;
import com.choongang.s202350103.model.NewBook;
import com.choongang.s202350103.model.Review;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderrDaoImpl implements OrderrDao {
	private final SqlSession session;
	private final HttpSession https; 
	
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

}
