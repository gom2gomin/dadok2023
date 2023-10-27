package com.choongang.s202350103.model;

import java.util.Date;
import lombok.Data;

@Data
public class OldBook {				//중고상품
	private int		ob_num;			//중고상품번호
	private int		nb_num;			//새상품번호
	private int		m_num;			//회원번호
	private int		ob_acc_name;	//정산 은행명
	private String	ob_acc_num;		//정산계좌번호
	private int 	ob_grade;		//중고등급
	private int		ob_pur_price;	//중고 매입가
	private int 	ob_status;		//검수매입상태
	private int 	ob_sell_price;	//중고 판매가
	private Date	ob_report_date; //신청일자
	private int 	ob_trans_com;	//판매 택배회사
	private String	ob_trans_num;	//판매 운송장 번호
	private int 	ob_ripped;		//찢긴상태
	private int 	ob_scribble;	//낙서상태
	private int 	ob_smeary;		//오염상태
	private Date	ob_write_date;	//중고 등록일자
	private int 	ob_readcnt;		//중고 조회수
	
	// 조회용
}
