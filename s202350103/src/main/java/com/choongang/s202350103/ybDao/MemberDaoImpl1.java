package com.choongang.s202350103.ybDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.choongang.s202350103.model.Cart;
import com.choongang.s202350103.model.Member;
import com.choongang.s202350103.model.OldBook;
import com.choongang.s202350103.model.PointList;
import com.choongang.s202350103.model.WishList;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl1 implements MemberDao {
	private final SqlSession session;
	private final HttpSession https; 
	// 로그인
	@Override
	public Member login(Member member1) {
		System.out.println("MemberDaoImpl login Start...");
		Member member = null;
		 
		try {
			member = session.selectOne("ybUserLogin", member1);
			System.out.println("MemberDaoImpl login member1 -> " + member.getM_id());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(member == null) {
			return null;
		} else {
			return member;			
		}
	}
	// 장바구니 총 개수
	@Override
	public int totalCart(Member member) {
		System.out.println("MemberDaoImpl1 totalCart start...");
			int totalCart = 0;
			System.out.println("MemberDaoImpl1 totalCart member -> " + member.getM_id());
		try {
			totalCart = session.selectOne("ybTotalCart", member);	
			System.out.println("MemberDaoImpl1 totalCart() totalCart -> " + totalCart);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 totalCart() Exception -> " + e.getMessage());
		}
		
		return totalCart;
	}
	// 판매 총 개수
	@Override
	public int totalSellCnt(Member member) {
		System.out.println("MemberDaoImpl1 totalSellCnt() start...");
		int totalSellCnt = 0;
		System.out.println("MemberDaoImpl1 totalSellCnt member -> " + member.getM_num());
		try {
			totalSellCnt = session.selectOne("ybTotalSellCnt", member);
			System.out.println("MemberDaoImpl1 totalSellCnt() totalCart -> " + totalSellCnt);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 totalSellCnt() Exception -> " + e.getMessage());
		}
		return totalSellCnt;
	}
	
	// 장바구니 총 가격
	@Override
	public int totalPrice(Member member) {
		System.out.println("MemberDaoImpl1 totalPrice() start...");
		int totalPrice = 0;
		try {
			totalPrice = session.selectOne("ybTotalPrice", member);
			System.out.println("MemberDaoImpl1 totalPrice totalPrice -> " + totalPrice);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 totalPrice Exception -> " + e.getMessage());
		}
		return totalPrice;
	}
	// 찜목록
	@Override
	public int totalWishList(Member member) {
		System.out.println("MemberDaoImpl1 totalWishList start...");
		int totalWishList = 0;
		System.out.println("MemberDaoImpl1 totalWishList member -> " + member.getM_id());
		try {
			totalWishList = session.selectOne("ybTotalWishList", member);	
			System.out.println("MemberDaoImpl1 totalWishList() totalWishList -> " + totalWishList);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 totalWishList() Exception -> " + e.getMessage());
		}
	
	return totalWishList;
	}
	
	// 장바구니 리스트
	@Override
	public List<Cart> listCart(Cart cart, Member member) {
		
		member =(Member) https.getAttribute("member");
		List<Cart> listCart = new ArrayList<Cart>();
		System.out.println("MemberDaoImpl1 listCart() start...");
		cart.setM_num(member.getM_num());
		System.out.println("MemberDaoImpl1 listCart() cart.m_num -> " +cart.getM_num());
		System.out.println("MemberDaoImpl1 listCart() member.m_num -> " + member.getM_num());
		try {
			listCart = session.selectList("ybListCart", cart);
			
			System.out.println("MemberDaoImpl1 listCart.size() -> " + listCart.size());
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 listCart Exception -> " + e.getMessage());
		}
		
		return listCart;
	}
	// 찜 목록 리스트
	@Override
	public List<WishList> memberWishList(WishList wishList) {
		Member member =(Member) https.getAttribute("member");
		List<WishList> memberWishList = new ArrayList<WishList>();
		System.out.println("MemberDaoImpl1 memberWishList() start...");
		wishList.setM_num(member.getM_num());
		System.out.println("MemberDaoImpl1 memberWishList() wishList.m_num -> " +wishList.getM_num());
		System.out.println("MemberDaoImpl1 memberWishList() member.m_num -> " + member.getM_num());
		try {
			memberWishList = session.selectList("ybWishList", wishList);
			
			System.out.println("MemberDaoImpl1 memberWishList.size() -> " + memberWishList.size());
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberWishList Exception -> " + e.getMessage());
		}
		return memberWishList;
	}
	// 포인트리스트
	@Override
	public List<PointList> memberPointList(PointList pointList) {
		Member member =(Member) https.getAttribute("member");
		List<PointList> memberPointList = new ArrayList<PointList>();
		pointList.setM_num(member.getM_num());
		
		try {
			memberPointList = session.selectList("ybMemberQuizList", pointList);
			System.out.println("MemberDaoImpl1 memberPointList.size() -> " + memberPointList.size());
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberPointList Exception -> " + e.getMessage());
		}
		return memberPointList;
	}
	
	// 회원 탈퇴
	@Override
	public Member memberWithdraw(Member member) {
		System.out.println("MemberDaoImpl1 memberWithdraw() start...");
		member =(Member) https.getAttribute("member");
		System.out.println("MemberDaoImpl1 memberWithdraw() member.m_num -> " + member.getM_num());
		try {
			member = session.selectOne("ybMemberWithdraw", member);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberWithdraw Exception -> " + e.getMessage());
		}
		return member;
	}
	
	// 회원 로그인 체크
	@Override
	public Member memberChk(String chk_Id) {
		System.out.println("MemberDaoImpl1 memberChk() start...");
		Member member = new Member();
		try {
			member = session.selectOne("ybMemberChk", chk_Id);	
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberChk() Exception -> " + e.getMessage());
		}
		if(chk_Id == null) {
			return null;	
		} else {
			return member;
		}
		
	}
	// 중고책 판매 리스트
	@Override
	public List<OldBook> oldBookSellList(OldBook oldbook) {
		System.out.println("MemberDaoImpl1 oldBookSellList() start...");
		Member member =(Member) https.getAttribute("member");
		List<OldBook> oldBookSellList = new ArrayList<OldBook>();
		try {
			oldbook.setM_num(member.getM_num());
			System.out.println("MemberServiceImpl1 oldBookSellList member.getNum -> " + oldbook.getM_num());
			oldBookSellList = session.selectList("ybOldBookSellList", oldbook); 
			System.out.println("MemberServiceImpl1 oldBookSellList oldBookSellList -> " + oldBookSellList.size());
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 oldBookSellList() Exception -> " + e.getMessage());
		}
		return oldBookSellList;
	}
	// 회원 이메일 
	@Override
	public Member findEmail(String memberMail) {
		System.out.println("MemberDaoImpl1 findEmail() start...");
		Member member = new Member();
		try {
			member = session.selectOne("ybFindEmail", memberMail);
			System.out.println("MemberDaoImpl1 findMail memberMail -> " + memberMail);
			System.out.println("에러에러에ㅓ레러레렝어네");
			
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 findEmail() Exception -> " + e.getMessage());
		}
		if(memberMail == null) {
			return null;	
		} else {
			return member;
		}
	}
	//  
	@Override
	public int memberPwUpdate(String m_pw, Member member) {
		System.out.println("MemberDaoImpl1 memberPwUpdate() start...");
		System.out.println("MemberDaoImpl1 memberPwUpdate member.getM_id -> " + member.getM_id());
		System.out.println("MemberDaoImpl1 memberPwUpdate m_pw -> " +m_pw);
		int memberPwUpdate = 0;
		try {
			memberPwUpdate = session.selectOne("ybMemberPwUpdate", m_pw);
			System.out.println("MemberDaoImpl1 memberPwUpdate memberPwUpdate -> " + memberPwUpdate);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberPwUpdate() Exception -> " + e.getMessage());
		}
		return memberPwUpdate;
	}
	@Override
	public Member memberPwChange(String m_num, String m_pw) {
		System.out.println("MemberDaoImpl1 memberPwChange() start...");
		Member memberPwChange = null;
		try {
			System.out.println("MemberDaoImpl1 memberPwChange() m_num -> " + m_num);
			System.out.println("MemberDaoImpl1 memberPwChange() m_pw -> " + m_pw);
			HashMap<String, Object> mapUpdate = new HashMap<>();
			mapUpdate.put("m_num", m_num);
			mapUpdate.put("m_pw", m_pw);
			
			memberPwChange = session.selectOne("ybMemberPwUpdate1", mapUpdate);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberPwChange() Exception -> " + e.getMessage());
		}
		return memberPwChange;
	}

	@Override
	public int memCount() {
		System.out.println("MemberDaoImpl1 memCount() start...");
		int memCount = 0;
		try {
			memCount = session.selectOne("ybMemCount");
			System.out.println("MemberDaoImpl1 memCount() memCount -> " + memCount);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memCount() Exception -> " +e.getMessage());
		}
		return memCount;
	}
	@Override
	public Member memberPwChange1(String m_email, String m_pw) {
		System.out.println("MemberDaoImpl1 memberPwChange1() start...");
		Member memberPwChange1 = null;
		try {
			System.out.println("MemberDaoImpl1 memberPwChange1() m_num -> " + m_email);
			System.out.println("MemberDaoImpl1 memberPwChange1() m_pw -> " + m_pw);
			HashMap<String, Object> mapUpdate = new HashMap<>();
			mapUpdate.put("m_email", m_email);
			mapUpdate.put("m_pw", m_pw);
			
			memberPwChange1 = session.selectOne("ybMemberPwUpdate2", mapUpdate);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberPwChange1() Exception -> " + e.getMessage());
		}
		return memberPwChange1;
	}
	@Override
	public Member memberFindPh(String phoneHyphen) {
		System.out.println("MemberDaoImpl1 memberFindPh() start...");
		Member memberFindPh = null;
		try {
			System.out.println("MemberDaoImpl1 memberFindPh() phoneHyphen -> " + phoneHyphen);
			memberFindPh = session.selectOne("ybMemberFindPh", phoneHyphen);
			System.out.println("MemberDaoImpl1 memberFindPh() memberFindPh -> " + memberFindPh);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberFindPh() Exception -> " + e.getMessage());
		}
		return memberFindPh;
	}
	@Override
	public Member memberPwChangeByPh(String m_ph, String m_pw) {
		System.out.println("MemberDaoImpl1 memberPwChangeByPh() start...");
		Member memberPwChangeByPh = null;
		try {
			System.out.println("MemberDaoImpl1 memberPwChangeByPh() m_ph -> " + m_ph);
			System.out.println("MemberDaoImpl1 memberPwChangeByPh() m_pw -> " + m_pw);
			HashMap<String, Object> mapUpdate = new HashMap<>();
			mapUpdate.put("m_ph", m_ph);
			mapUpdate.put("m_pw", m_pw);
			
			memberPwChangeByPh = session.selectOne("ybMemberPwChangeByPh", mapUpdate);
		} catch (Exception e) {
			System.out.println("MemberDaoImpl1 memberPwChangeByPh() Exception -> " + e.getMessage());
		}
		return memberPwChangeByPh;
	}

}
	

	

	
	


