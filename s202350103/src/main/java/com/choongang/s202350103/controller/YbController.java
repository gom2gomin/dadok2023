package com.choongang.s202350103.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choongang.s202350103.model.Community;
import com.choongang.s202350103.gbService.NewBookService;
import com.choongang.s202350103.model.Cart;
import com.choongang.s202350103.model.Member;
import com.choongang.s202350103.model.NewBook;
import com.choongang.s202350103.model.OldBook;
import com.choongang.s202350103.model.PointList;
import com.choongang.s202350103.model.WishList;
import com.choongang.s202350103.sjService.OldbookService;
import com.choongang.s202350103.ybService.MemberService;
import com.choongang.s202350103.ybService.Paging;
import com.choongang.s202350103.ybService.communityPaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
@Slf4j
public class YbController {
	
	private final MemberService ms;
	private final HttpSession session;
	private final JavaMailSender mailSender;
	final DefaultMessageService messageService; 
	private final NewBookService nbs;
	private final OldbookService obs;
	
	
	public YbController(HttpSession session, MemberService ms, JavaMailSender mailSender, NewBookService nbs, OldbookService obs ) {
		this.ms = ms;
		this.session = session;
		this.mailSender = mailSender;
		this.messageService = NurigoApp.INSTANCE.initialize("NCSI4UORH4AWJGTE", "ZYW9R5J88TDYQ2855DNUH8ZTJZNEENPR", "https://api.coolsms.co.kr");
		this.nbs = nbs;
		this.obs = obs;
	}
	

//	private Model getSession(Member member, Model model) {
//		Member member1 = new Member();
//		member = ms.login(member1);
//		member =(Member) session.getAttribute("member");
//		
//		model.addAttribute("member", member);
//		
//		return model;
//	}
	
	// Main Page
	@RequestMapping(value = "/")
	public String main(Member member, NewBook newbook,OldBook oldBook , HttpServletRequest request, Model model, Cart cart) {
		System.out.println("YbController main() start... ");
		member =(Member) session.getAttribute("member");
		if(member == null) {
			
			// 카테고리별 최대 조회수 도서 상품 리스트 구하기
			List<NewBook> hitList = nbs.selectHitNbNum();
			System.out.println("hitList -> "+hitList.size());
			
			// 다독 전체 최대 조회수 도서 상품 리스트
			NewBook hitBook1 = nbs.selectAllHitNbNum();
			System.out.println("hitList -> "+hitList.size());
			
			// oldBook 4개 랜덤
			   List<OldBook> ObNumRedomSel = obs.selectRendomObNum();
			   model.addAttribute("ObNumRedomSel", ObNumRedomSel);
			   System.out.println("ObNumRedomSel->"+ObNumRedomSel.size());
			   System.out.println("ObNumRedomSel->"+ObNumRedomSel);
			
			
			// 출간일 기준 5개의 도서 리스트
			List<NewBook> releaseNewbookList = nbs.selectReleaseNewbookListNum();
			System.out.println("releaseNewbookList -> "+releaseNewbookList.size());
			
			model.addAttribute("hitList", hitList);
			model.addAttribute("hitBook1", hitBook1);
			model.addAttribute("releaseNewbookList", releaseNewbookList);
			
			return "main";
		}
		
		// 카테고리별 최대 조회수 도서 상품 리스트 구하기
		List<NewBook> hitList = nbs.selectHitNbNum();
		System.out.println("hitList -> "+hitList.size());
		
		// 다독 전체 최대 조회수 도서 상품 리스트
		NewBook hitBook1 = nbs.selectAllHitNbNum();
		System.out.println("hitList -> "+hitList.size());
		
		// oldBook 4개 랜덤
		   List<OldBook> ObNumRedomSel = obs.selectRendomObNum();
		   model.addAttribute("ObNumRedomSel", ObNumRedomSel);
		   System.out.println("ObNumRedomSel->"+ObNumRedomSel.size());
		   System.out.println("ObNumRedomSel->"+ObNumRedomSel);
		
		// 출간일 기준 5개의 도서 리스트
		List<NewBook> releaseNewbookList = nbs.selectReleaseNewbookListNum();
		System.out.println("releaseNewbookList -> "+releaseNewbookList.size());
		
		model.addAttribute("hitList", hitList);
		model.addAttribute("hitBook1", hitBook1);
		model.addAttribute("releaseNewbookList", releaseNewbookList);
		
		int totalCart = ms.totalCart(member);
		System.out.println("YbController main() start... ");
		model.addAttribute("member", member);
		model.addAttribute("totalCart", totalCart);
		
		return "main";
	}
	
	// 로그인 창 이동
	@GetMapping(value = "loginForm")
	public String loginForm(Member member, Model model) {
		System.out.println("YbController login() start... ");
		member =(Member) session.getAttribute("member");
		if(member != null) {
			model.addAttribute("member", member);
			return "main";
		}
		
		return "yb/loginForm";
	}
	
	// 로그인 시 회원 체크 Ajax
		@ResponseBody
		@RequestMapping("/memberChk")
		public String memberLoginChk(Member member, String chk_Id, String chk_Pw) {
			System.out.println("YbController memberChk() start...");
			member = ms.memberChk(chk_Id);
			if(member != null ) {
				System.out.println("YbController memberLoginChk member.m_id -> " + member.getM_id());
				System.out.println("YbController memberLoginChk member.m_pw -> " + member.getM_pw());
				System.out.println("YbContorller memberLoginChk member.m_wd -> " + member.getM_wd());
				int m_wd = member.getM_wd();
				String m_id = member.getM_id();
				String m_pw = member.getM_pw();
				System.out.println("YbController memberLoginChk m_id -> " + m_id);
				System.out.println("YbController memberLoginChk m_pw -> " + m_pw);
				System.out.println("YbController memberLoginChk chk_id -> " + chk_Id);
				System.out.println("YbController memberLoginChk chk_Pw -> " + chk_Pw);
				int result = 0;

				if(chk_Id.equals(m_id) && chk_Pw.equals(m_pw) && m_wd == 0) {
					result = 1;
					session.setAttribute("member", member);
					System.out.println("YbController memberLoginChk member -> " + session.getId());
				} else if(chk_Id.equals(m_id) && chk_Pw.equals(m_pw) && m_wd == 1) {
					result = 2;		
				} else {
					result = 0;
				}
		
				System.out.println("YbController memberLoginChk result -> " + result);
		
				String strResult = Integer.toString(result);
				return strResult;
			
			}else {
				return "0";
			}		
		}
	
	// cookieSeession 이용 로그인
	
//	@RequestMapping(value = "memberLogin")
//	public String login(@Valid @ModelAttribute Member member, HttpServletResponse response, HttpServletRequest request, BindingResult bindingResult, Member member1) {
//		if(bindingResult.hasErrors()) {
//			return "yb/loginForm";
//		} 
//		member = ms.login(member1);
//		System.out.println("YbController login() member -> " + member);
//		
//		if(member == null) {
//			return "yb/loginForm";
//		}
//		sessionManager.createSession(member, response);
//	
//		System.out.println("YbController login getSessionId -> " + sessionManager.getSession(request));
//		return "main";
//	}
//	// 로그아웃
//	@GetMapping(value = "memberLogout")
//	public String logout(HttpServletRequest request) {
//		sessionManager.expire(request);
//
//		return "redirect:/";
//	}
	
	// 로그인
	@RequestMapping(value = "memberLogin")
	public String login(Member member1, HttpSession session, HttpServletRequest request, Model model) {
		log.info("Login page");

		System.out.println("YbController login() session -> " + session);
		Member member = ms.login(member1);
//		getSession(member, model);
		
		if (member != null) {
			session.setAttribute("member", member);
			System.out.println("YbController login() session -> " + session.getId());
			System.out.println("YbController login() member.getId -> " + member.getM_id());
			return "redirect:/";
		} else {
			 return "yb/loginForm";
		}
	
	}
	
	
	// 로그아웃
	@GetMapping(value = "memberLogout")
	   public String logout(HttpSession session, HttpServletRequest request) {
			System.out.println("YbController userLogout start... ");
	      try {
				
				  session = request.getSession(false); //세션이 있으면 기존 세션을 반환한다.
				  // 세션이 없으면 새로운  세션을 생성하지 않고, null을 반환
				  if (session != null) {
					  System.out.println("YbController logout() session null ");
					  session.removeAttribute("member"); 
					  session.invalidate(); // 세션 초기화
				  }
		  } catch (Exception e) {
	         System.out.println("logout Exception -> "+e.getMessage());
	      }
	      return "redirect:/";
	}

	// 비밀번호 찾기 페이지 이동
	@GetMapping(value = "memberFindPwForm")
	public String findMemberPw() {
		System.out.println("YbController memberFindPwForm() start...");
		return "yb/memberFindPwForm";
	}
	
	// 비밀번호 찾기 인증 화면 이동
	@RequestMapping("memberFindPw")
	public String memberPhFindId(@RequestParam("auth") String auth, Model model ) {
		
		if("ph".equals(auth)) {
			return "yb/memberFindPwPh";
		}else {
			return "yb/memberFindPwEmail";
		}
		
	}
	@RequestMapping("memberPwChange")
	public String memberPwChange(Model model) {
		
		return "yb/memberPwChange";
		
	}
	
	// 장바구니 페이지 이동
	@RequestMapping(value = "memberCartList")
	public String memberCartList(Cart cart, Model model, String currentPage, 
								 HttpSession session, Member member) {
		System.out.println("YbController memberCartList() start...");

		// 로그인한 멤버 값 불러오기
		member =(Member) session.getAttribute("member");
		
//		if(member == null) {
//			return "yb/loginForm";
//		} 
		
//		getSession(model, member);
	
		System.out.println("YbController memberCartList() member.m_id -> " + member.getM_id());
		// 회원별 장바구니 총 개수
		int totalCart = ms.totalCart(member);
		System.out.println("YbController memberCartList() totalCart -> " + totalCart);
		
		// 페이징 처리
		Paging page = new Paging(totalCart, currentPage);
		
		cart.setStart(page.getStart());
		cart.setEnd(page.getEnd());
		
		System.out.println("YbController memberCartList member.m_num -> " + member.getM_num());
		// 장바구니 리스트
		List<Cart> listCart = ms.listCart(cart, member);
		System.out.println("YbController memberCartList listCart.size() -> " + listCart.size());
		
		int totalPrice = ms.totalPrice(member);
		
		model.addAttribute("page", page);
		model.addAttribute("totalCart", totalCart);
		model.addAttribute("member", member);
		model.addAttribute("listCart", listCart);
		model.addAttribute("totalPrice", totalPrice);
		
		return "yb/memberCartList";
	}
	
	// 찜목록 페이지 이동
	@RequestMapping(value = "memberWishList")
	public String memberWishList(Member member, HttpServletRequest request, HttpSession session, Model model, 
								 WishList wishList, String currentPage) {
		
		System.out.println("YbController memberWishList() start...");
		
		// 로그인한 멤버 값 불러오기
		member =(Member) session.getAttribute("member");
//		getSession(member, model);
		
		if(member == null) {
			return "yb/loginForm";
		}
		
		// 회원별 찜 총 개수
		int totalWishList = ms.totalWishList(member);
		
		// 페이징 처리
		Paging page = new Paging(totalWishList, currentPage);
				
		wishList.setStart(page.getStart());
		wishList.setEnd(page.getEnd());
		
		System.out.println("YbController memberWishList() member.m_id -> " + member.getM_id());
	
		
		// 찜목록 리스트
		List<WishList> memberWishList = ms.memberWishList(wishList);
		System.out.println("YbController memberWishList memberWishList.size() -> " + memberWishList.size());
		
		model.addAttribute("page", page);
		model.addAttribute("member", member);
		model.addAttribute("memberWishList", memberWishList);
		model.addAttribute("totalWishList", totalWishList);
		model.addAttribute("wishList", wishList);
		return "yb/memberWishList";
	}
	
	// 포인트 페이지
	@GetMapping(value = "memberPointList") 
	public String memberPointList(Member member, Model model, PointList pointList, String currentPage) {
		System.out.println("YbController memberPointList() start...");
		// 로그인한 멤버 값 불러오기
		member =(Member) session.getAttribute("member");
		
		System.out.println("memberPointList member.getM_id -> " + member.getM_id());
		System.out.println("memberPointList session -> " + session.getAttribute("member"));
		
		System.out.println("YbController memberPointList() member.getM_point -> " + member.getM_point());
		
		// 포인트 적립갯수
		pointList.setM_num(member.getM_num());
		System.out.println("YbController memberPointList pointList.getM_num -> " + pointList.getM_num());
		int pointListCnt = ms.pointListCnt(pointList);
		System.out.println("YbController memberPointList pointListCnt ->" + pointListCnt);
		// 포인트 리스트
		
		
		// 페이징 처리
		Paging page = new Paging(pointListCnt, currentPage);
			
		pointList.setStart(page.getStart());
		pointList.setEnd(page.getEnd());
		List<PointList> memberPointList = ms.memberPointList(pointList);
		System.out.println("YbController memberPointList() memberPointList.size() -> " + memberPointList.size());
		
		model.addAttribute("page", page);
		model.addAttribute("memberPointList", memberPointList);
		model.addAttribute("member", member);
		return "yb/memberPointList";
		
	}
	// 중고책 판매 리스트
	@GetMapping(value = "memberSellList") 
	public String memberSellList(Member member, Model model, OldBook oldbook, String currentPage) {
		System.out.println("YbController memberSellList() start...");
		// 로그인한 멤버 값 불러오기
		member =(Member) session.getAttribute("member");
		if(member == null) {
			return "yb/loginForm";
		}
		// 총 판매 개수
		int totalSellCnt = ms.totalSellCnt(member);
		System.out.println("YbController memberSellList() totalSellCnt -> " + totalSellCnt);
		// 페이징 처리
		Paging page = new Paging(totalSellCnt, currentPage);
				
		oldbook.setStart(page.getStart());
		oldbook.setEnd(page.getEnd());
		// 중고책 리스트
		List<OldBook> oldBookSellList = ms.oldBookSellList(oldbook);
		
		System.out.println("YbController memberSellList() oldBookSellList.size -> " + oldBookSellList.size());
		
		model.addAttribute("page", page);
		model.addAttribute("oldBookSellList", oldBookSellList);
		model.addAttribute("totalSellCnt", totalSellCnt);
		model.addAttribute("member", member);
		
		return "yb/memberMySellList";
	}
	// 커뮤니티 리스트
	@GetMapping(value = "memberCommunity")
	public String memberCommunity(Community community, Model model, String currentPage, Member member) {
		System.out.println("YbController memberCommunity() start..");
		member =(Member) session.getAttribute("member");
		
		int comListTotalCnt = ms.comListTotalCnt(community);
		communityPaging page = new communityPaging(comListTotalCnt, currentPage);
		
		community.setStart(page.getStart());
		community.setEnd(page.getEnd());
		List<Community> communityList = ms.communityList(community);
		System.out.println("YbController memberCommunity() communityList.size() -> " +communityList.size());
		
		model.addAttribute("member", member);
		model.addAttribute("page", page);
		model.addAttribute("comListTotalCnt", comListTotalCnt);
		model.addAttribute("communityList", communityList);
		return "yb/memberCommunity";
	}
	
	// 내 커뮤니티 리스트
	@GetMapping(value = "memberMyCommunity")
	public String memberMyCommunity(Community community, Model model, String currentPage, Member member) {
		System.out.println("YbController memberMyCommunity() start..");
		member =(Member) session.getAttribute("member");
		
		int m_num = member.getM_num();
		int comMyListTotalCnt = ms.comMyListTotalCnt(m_num);
		communityPaging page = new communityPaging(comMyListTotalCnt, currentPage);
		
		community.setStart(page.getStart());
		community.setEnd(page.getEnd());
		community.setM_num(m_num);
		List<Community> communityMyList = ms.communityMyList(community);
		System.out.println("YbController memberCommunity() communityMyList.size() -> " +communityMyList.size());
		
		model.addAttribute("member", member);
		model.addAttribute("page", page);
		model.addAttribute("comMyListTotalCnt", comMyListTotalCnt);
		model.addAttribute("communityMyList", communityMyList);
		model.addAttribute("community", community);
		return "yb/memberMyCommunity";
	}
	
	
	// 회원 탈퇴 페이지 이동
	@GetMapping(value = "memberWithdrawForm")
	public String memberWithdrawForm(Member member, HttpSession session, Model model) {
		System.out.println("YbController memberWithdrowForm() start...");
		member =(Member) session.getAttribute("member");
		if(member == null) {
			return "yb/loginForm";
		}
		model.addAttribute("member", member);
		return "yb/memberWithdrawForm";
	}
	
	// 회원탈퇴 하기
	@PostMapping(value = "memberWithdraw") 
	public String memberWithdraw(Member member, Model model,
								 @RequestParam("m_pw") String m_pw) { 
		System.out.println("YbController memberWithdraw() start..."); 
		member =(Member) session.getAttribute("member");
		
		member = ms.memberWithdraw(member); 
		session.removeAttribute("member"); 
		session.invalidate(); // 세션 초기화
		return "main"; 

	}
	 

	// 회원탈퇴 비밀번호 Check Ajax
	@ResponseBody
    @RequestMapping("/memberChkPw")
    public String memberChkPw(Member member, HttpSession session) {
          System.out.println("YbController memberChkPw() start..");
          member =(Member) session.getAttribute("member");
         
          String memberPw = member.getM_pw();
          System.out.println("YbController memberChkPw() memberPw -> " + memberPw);
          return memberPw;
    }	
	
	
	// 인증 랜덤번호 발송 메서드
    private String certiNum() {
    	Random random = new Random();
    	int min = 100000;
    	int max = 999999;
    	
    	int certiNum = random.nextInt((max - min) + 1) + min;
    	
    	return String.valueOf(certiNum);
    }
    
    // 이메일 전송 내장 클래스
    @Data
	@AllArgsConstructor
    class sendMailData {
    	private String certiNum;
    	private String m_email;
    }
    
	// 이메일 전송
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/mailTransport")
	public sendMailData mailTransport(Model model, String memberMail) throws MessagingException {
		System.out.println("YbController mailTransport start...");
		System.out.println("YbController mail memberMail -> " + memberMail); 
		Member findEmail = ms.findEmail(memberMail);
		String certiNum = null;

		if(findEmail!=null) {
				String tomail = memberMail;	// 받는 사람 email
				System.out.println("YbController mailTransport memberMail -> " + tomail);
				String setfrom = "96dydqls@gmail.com";
				String title = "DADOK 인증번호 입니다.";	// 제목
				// Mime 전자우편 Internet 표준 Format
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(setfrom);		// 보내는 사람 생략하거나 하면 정상작동 안함
				messageHelper.setTo(tomail);		// 받는 사람 이메일
				messageHelper.setSubject(title); 	// 메일제목은 생략 가능
				certiNum = certiNum();
				messageHelper.setText("인증번호는 [ " + certiNum + " ]입니다." );	// 메일 내용
				System.out.println("인증번호입니다." + certiNum);
				mailSender.send(message);
			
				model.addAttribute("memberMail", memberMail);
					
				return new sendMailData(certiNum, memberMail);
		} else {

			return new sendMailData(certiNum, memberMail);
		}	
	}
	
	// 인증번호 체크
	@PostMapping(value = "certiNumChk") 
	public String certiNumChk(Member member, Model model,
											 @RequestParam("certiNum") String certiNum,
											 @RequestParam("inputNum") String inputNum,
											 @RequestParam("m_email")  String m_email) {
		System.out.println("YbController certiNumChk() start..");
		System.out.println("certiNum -> " + certiNum);
		System.out.println("inputNum -> " + inputNum);
		if(certiNum.equals(inputNum)) {
			
			System.out.println("YbController certiNumChk Success! Change your Password.");
			System.out.println("YbController certiNumChk m_email -> " + m_email);
			model.addAttribute("m_email", m_email);
			model.addAttribute("member", member);
			return "yb/memberPwChangeForm1";
		} else {
			System.out.println("YbController certiNumChk fail... try again!");
			model.addAttribute("msg", "인증번호를 다시 입력해주세요");
			return "yb/memberFindPwEmail";
		}
	}
	// 아이디 찾기 -> 인증 후 비밀번호 변경 페이지
	@GetMapping(value = "memberPwChangeForm")
	public String memberPwChangeForm(String m_num, Model model) {
		System.out.println("YbController memberPwChangeForm() start..");

		System.out.println("YbController memberPwChangeForm() member.m_num -> " + m_num);

		model.addAttribute("m_num", m_num);
		return "yb/memberPwChangeForm";
	}
	// 내장 클래스
	@Data
	@AllArgsConstructor
	class Result {
		private final String strResult;
	}
	
//	@RequestParam("m_email") String m_email
	// 인증번호 성공 후 비밀번호 변경 페이지
	@ResponseBody
	@RequestMapping(value = "changePwChk")
	public Result changePwChk(Member member, @RequestParam("m_pw")  String m_pw,
											 @RequestParam("m_pw2") String m_pw2
											 ) {
		System.out.println("YbController changePwChk() start..");
		int result = 0;
		
//		System.out.println("YbController changePwChk m_email -> " + m_email);
		if(m_pw.equals(m_pw2) && m_pw.matches((("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")))) {
			result = 1;
		} else if(m_pw.equals(m_pw2) && !m_pw.matches((("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")))){
			result = 2;
		} else {
			result = 0;
		}
		String strResult = Integer.toString(result);

		return new Result(strResult);
	}

	// 아이디 찾기 -> 인증 후 비밀번호 변경 
	@PostMapping(value = "memberPwChange")
	public String memberPwChange(String m_num, String m_pw, Member member) {
		System.out.println("YbController memberPwChange() start..");
		System.out.println("YbController memberPwChange() m_num -> " + m_num);
		System.out.println("YbController memberPwChange() m_pw -> " + m_pw);
		Member memberPwChange = ms.memberPwChange(m_num, m_pw);
		
		session.removeAttribute("member"); 
		session.invalidate(); // 세션 초기화
		return "main"; 
	}
	
	// 비밀번호 변경 -> email 인증 후 비밀번호 변경 
	@PostMapping(value = "memberPwChange1")
	public String memberPwChange1(Member member, @RequestParam("m_pw") String m_pw,
												 @RequestParam("m_email") String m_email) {
		System.out.println("YbController memberPwChange1() start..");
		System.out.println("YbController memberPwChange1() m_num -> " + m_email);
		System.out.println("YbController memberPwChange1() m_pw -> " + m_pw);
		Member memberPwChange1 = ms.memberPwChange1(m_email, m_pw);
		
		session.removeAttribute("member"); 
		session.invalidate(); // 세션 초기화
		return "main"; 
	}
	// 비밀번호 변경 -> ph 인증 후 변경
	@PostMapping(value = "memberPwChangeByPh")
	public String memberPwChangeByPh(Member member, @RequestParam("m_pw") String m_pw,
												 	@RequestParam("m_ph") String m_ph1) {
		System.out.println("YbController memberPwChangeByPh() start..");
		System.out.println("YbController memberPwChangeByPh() m_ph1 -> " + m_ph1);
		System.out.println("YbController memberPwChangeByPh() m_pw -> " + m_pw);
		String m_ph = phoneHyphen(m_ph1);
		System.out.println("YbController memberPwChangeByPh() m_ph1 -> " + m_ph1);
		Member memberPwChangeByPh = ms.memberPwChangeByPh(m_ph, m_pw);
		
		session.removeAttribute("member"); 
		session.invalidate(); // 세션 초기화
		return "main"; 
	}
	// 전화번호 하이픈 - 추가 	
	private String phoneHyphen(String m_ph) {
		
		StringBuilder phoneHyphen = new StringBuilder();
		
		int phLength = m_ph.length();
		for(int i = 0; i < phLength; i++) {
			// 전화번호의 마지막자리까지 인덱스 부여 
			char digit = m_ph.charAt(i);
			
			// 전화번호에 하이픈을 추가할 위치 
			if(i == 3 || i == 7) {
				phoneHyphen.append('-');
			}
			phoneHyphen.append(digit);
		}
		return phoneHyphen.toString();
	}
	
	// 내장 클래스
	@Data
	@AllArgsConstructor
	class sendData {
		private final String m_ph;
		private final String certiNum;
		private final Member memberFindPh;
	}
	
	// 문자 통한 비밀번호 찾기
	@ResponseBody
	@RequestMapping(value = "/textTransport")
	public sendData textTransport(String memberPh, Member member, Model model) {
		
		System.out.println("YbController textTransport() start..");
		System.out.println("YbController textTransport() m_ph -> " + memberPh);
		String phoneHyphen = phoneHyphen(memberPh);
		
		Member memberFindPh = ms.memberFindPh(phoneHyphen);
		System.out.println("YbController textTransport memberFindPh -> " + memberFindPh);
		String certiNum = null;

		if(memberFindPh != null) {
			Message message = new Message();
			certiNum = certiNum();
			System.out.println("YbController textTransport certiNum -> " + certiNum);
			
			message.setFrom("01024846106");
			message.setTo(memberPh);
			message.setText("[(주)DADOK] ["+certiNum+"] 인증번호를 정확히 입력해주세요.");
			  
			SingleMessageSentResponse response = 
					this.messageService.sendOne(new SingleMessageSendingRequest(message)); 
			System.out.println(response);
			model.addAttribute("memberPh", memberPh);
			
			return new sendData(memberPh, certiNum, memberFindPh);
		} else {
			return new sendData(memberPh, certiNum, memberFindPh);
		}
	}
	
	// 인증번호 체크
	@PostMapping(value = "phCertiNumChk") 
	public String phCertiNumChk(Member member, Model model,
											   @RequestParam("certiNum") String certiNum,
											   @RequestParam("inputNum") String inputNum,
											   @RequestParam("m_ph")  String m_ph) {
		System.out.println("YbController phCertiNumChk() start..");
		System.out.println("certiNum -> " + certiNum);
		System.out.println("inputNum -> " + inputNum);
		if(certiNum.equals(inputNum)) {
			
			System.out.println("YbController phCertiNumChk Success! Change your Password.");
			System.out.println("YbController phCertiNumChk m_ph -> " + m_ph);
			model.addAttribute("m_ph", m_ph);
			model.addAttribute("member", member);
			return "yb/memberPwChangeByPhForm";
		} else {
			System.out.println("YbController certiNumChk fail... try again!");
			model.addAttribute("msg", "인증번호를 다시 입력해주세요");
			return "yb/memberFindPwPh";
		}
	}
	
	@GetMapping(value = "writeForm")
	   public String writeForm(Member member, Model model) {
	      System.out.println("YbController writeForm() start..");
	      member =(Member) session.getAttribute("member");
	      model.addAttribute("member", member);
	      return "yb/writeForm";
	   }
	   
	   @GetMapping(value = "searchBook")
	   public String searchBook(NewBook newbook, Model model, String currentPage) {
	      System.out.println("YbController searchBook() start..");
	      
	      return "yb/searchBook";
	      
	   }
	   @GetMapping(value = "communityUpdate")
	   public String communityUpdate(NewBook newbook, Model model, Community community, int cm_num) {
	      System.out.println("YbController searchBook() start..");
	      
	      
	      community = ms.selectBookDetail(cm_num);
	      model.addAttribute("community", community);
	      return "yb/communityUpdate";
	      
	   }
	   @GetMapping(value = "postDetailForm")
	   public String postDetailForm(Community community, Model model, String currentPage, int cm_num, Member member) {
	      System.out.println("YbController postDetailForm() start..");
	      member =(Member) session.getAttribute("member");
	      
	      community = ms.selectBookDetail(cm_num);
	      community.setCm_num(cm_num);
	      
	      int nb_num = community.getNb_num();
	      List<Community> sameDetailList = ms.sameDetailList(nb_num);
	      
	      int readCntUp = ms.readCntUp(cm_num);
	      
	      
	      System.out.println("YbController postDetailForm() member->"+member);
	      
	      
	      model.addAttribute("member", member);
	      model.addAttribute("sameDatailList", sameDetailList);      
	      model.addAttribute("community", community);      
	      System.out.println("YbController postDetailForm() readCntUp->"+readCntUp);

	      
	      return "yb/postDetailForm";
	      
	   }
	   
	 //날짜별로 폴더생성
		 public String makeDir() {
		 	Date date=new Date();
		 	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		 	String now=sdf.format(date);

		 	String path=uploadpath + "\\" +now; //경로
		 	File file = new File(path);

		 	if(file.exists()==false) {//파일이 존재하면 true
		 		file.mkdir(); //폴더생성
		 	}

		 	return path;
		 }
		 

	   // 커뮤니티 글 등록
	   @PostMapping(value = "communityInsert")
	   public String communityInsert(Member member, Community community, Model model, NewBook newbook, 
			   						 MultipartHttpServletRequest files, @RequestParam("multiFile") List<MultipartFile> multiFileList, HttpServletRequest request) {
	      System.out.println("YbController communityInsert() start..");
	      	
	      	// 받아온것 출력 확인
		  System.out.println("multiFileList : " + multiFileList);
		  System.out.println("multiFileList.size : " + multiFileList.size());
		// path 가져오기
		  String path = request.getSession().getServletContext().getRealPath("upload/yb");
		  String root = path + "\\";
		  File fileCheck = new File(root);
		  System.out.println("path -> " + path);
		  System.out.println("root-> " + root);
		  System.out.println("fileCheck -> " + fileCheck);

		  if(!fileCheck.exists()) fileCheck.mkdirs();
		  List<Map<String, String>> fileList = new ArrayList<>();
		  Map<String, String> map = null;
		  if(multiFileList.size() > 1) {
			  for(int i = 0; i < multiFileList.size(); i++) {
				  String originFile = multiFileList.get(i).getOriginalFilename();
				  String ext = originFile.substring(originFile.lastIndexOf("."));
				  String changeFile = UUID.randomUUID().toString() + ext;
		
				  map = new HashMap<>();
				  map.put("originFile", originFile);
				  map.put("changeFile", changeFile);	
				  fileList.add(map);
			  }
		  }
		  System.out.println(fileList);
		  System.out.println(fileList.size());
		  
		  // 파일업로드
		  
		  try {
			  if(fileList.size() > 0) {
				  for(int i = 0; i < multiFileList.size(); i++) {
					  File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
					  multiFileList.get(i).transferTo(uploadFile);
				  }
			  }
			  System.out.println("다중 파일 업로드 성공!");
			
		  } catch (IllegalStateException | IOException e) {
			  System.out.println("다중 파일 업로드 실패 ㅠㅠ");
			  // 만약 업로드 실패하면 파일 삭제
			  for(int i = 0; i < multiFileList.size(); i++) {
				  new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
			  }
			  e.printStackTrace(); 
		  }
			  List<String> valueList = fileList.stream().filter(t -> t.containsKey("changeFile")).map(m -> m.get("changeFile").toString()).collect(Collectors.toList()); 
			  String cm_image1 = "";
			  String cm_image2 = "";
			  if(valueList.size() == 1) {
				  cm_image1 = valueList.get(0);				  
			  } else if(valueList.size() > 1){
				  cm_image1 = valueList.get(0);
				  cm_image2 = valueList.get(1);
			  }
		      
		      member =(Member) session.getAttribute("member");
		      community.setM_num(member.getM_num());
		      community.setCm_image1(cm_image1);
		      community.setCm_image2(cm_image2);
		      System.out.println("getM_num -> " + community.getM_num());
		      System.out.println("getM_num -> " + community.getCm_image1());
		      System.out.println("getM_num -> " + community.getCm_image2());
		      community.setNb_num(newbook.getNb_num());
		      System.out.println("getNB_num -> " + community.getNb_num());
		      int communityInsert = ms.communityInsert(community);
		      
		      System.out.println("YbController communityInsert result -> " + communityInsert );
		      model.addAttribute("member", member);
		      model.addAttribute("check", 1);
		      return "yb/writeForm";
		  } 
		  
		    

	     
	   // 커뮤니티 글 등록 시 책 선택
	   @GetMapping(value = "searchListBook")
	   public String searchListBook(NewBook newbook, Model model, String currentPage) {
	      System.out.println("YbController searchListBook() start..");
	      int searchBookCnt = ms.searchBookCnt(newbook);
	      Paging page = new Paging(searchBookCnt, currentPage);
	      
	      newbook.setStart(page.getStart());
	      newbook.setEnd(page.getEnd());
	      
	      List<NewBook> searchListBook = ms.searchListBook(newbook);
	      model.addAttribute("page", page);
	      model.addAttribute("newbook", newbook);
	      model.addAttribute("searchListBook", searchListBook);
	      return "yb/searchBook";
	      
	   }
	   // 게시글 수정
	   @PostMapping(value = "communityUpdateDo")
	   public String communityUpdateDo(Community community, Member member,
			   						   MultipartHttpServletRequest files, @RequestParam("multiFile") List<MultipartFile> multiFileList, HttpServletRequest request) {
		   System.out.println("YbController communityUpdateDo() start..");
		   member =(Member) session.getAttribute("member");
		   System.out.println("multiFileList : " + multiFileList);
			// path 가져오기
			  String path = request.getSession().getServletContext().getRealPath("upload/yb");
			  String root = path + "\\";
			  File fileCheck = new File(root);
			  System.out.println("path -> " + path);
			  System.out.println("root-> " + root);
			  System.out.println("fileCheck -> " + fileCheck);
			
		      if(!fileCheck.exists()) fileCheck.mkdirs();
			  List<Map<String, String>> fileList = new ArrayList<>();
			  Map<String, String> map = null;
			  
			  if(multiFileList.size() > 1) {
				  for(int i = 0; i < multiFileList.size(); i++) {
					  String originFile = multiFileList.get(i).getOriginalFilename();
					  String ext = originFile.substring(originFile.lastIndexOf("."));
					  String changeFile = UUID.randomUUID().toString() + ext;
			
					  map = new HashMap<>();
					  map.put("originFile", originFile);
					  map.put("changeFile", changeFile);	
					  fileList.add(map);
				  }
			  }
			  System.out.println(fileList);
			  // 파일업로드
			  try {
				  if(fileList.size() > 0) {
					  for(int i = 0; i < multiFileList.size(); i++) {
						  File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
						  multiFileList.get(i).transferTo(uploadFile);
					  }
				  }
				  System.out.println("다중 파일 업로드 성공!");
				
			  } catch (IllegalStateException | IOException e) {
				  System.out.println("다중 파일 업로드 실패 ㅠㅠ");
				  // 만약 업로드 실패하면 파일 삭제
				  for(int i = 0; i < multiFileList.size(); i++) {
					  new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
				  }
				  e.printStackTrace(); 
			  }
			  // map List String으로 변환
			  List<String> valueList = fileList.stream().filter(t -> t.containsKey("changeFile")).map(m -> m.get("changeFile").toString()).collect(Collectors.toList());
			  String cm_image1 = "";
			  String cm_image2 = "";
			  if(valueList.size() == 1) {
				  cm_image1 = valueList.get(0);				  
			  } else if(valueList.size() > 1){
				  cm_image1 = valueList.get(0);
				  cm_image2 = valueList.get(1);
			  }

		      System.out.println("cm_imag1 -> " + cm_image1);
		      System.out.println("cm_imag2 -> " + cm_image2);

		      community.setCm_image1(cm_image1);
		      community.setCm_image2(cm_image2);
 		   int communityUpdateDo = ms.communityUpdateDo(community);
		   
		   return "yb/postDetailForm";
	   }
	   
	   // 게시글 삭제
	   @GetMapping(value = "communityDelete")
	   public String communityDelete(Community community, Member member, int cm_num) {
		   System.out.println("YbController communityUpdateDo() start..");
		   member =(Member) session.getAttribute("member");
		  
		   int communityDelete = ms.communityDelete(cm_num);
   
		   return "redirect:/memberCommunity";
	   }
	   
	   @GetMapping(value = "communityHitPush")
	   public String communityHitPush(Member member, int cm_num, Model model, RedirectAttributes redirect) {
		   member =(Member) session.getAttribute("member");
		   
		   int communityHitPush = ms.communityHitPush(cm_num);
		   redirect.addAttribute("cm_num", cm_num);
		   model.addAttribute("communityHitPush", communityHitPush);
		   return "redirect:/postDetailForm";
	   }
	   
	   private String uploadpath;

	 
	
}

	   

