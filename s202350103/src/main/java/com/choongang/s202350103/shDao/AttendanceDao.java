package com.choongang.s202350103.shDao;

import java.util.List;

import com.choongang.s202350103.model.Attendance;

public interface AttendanceDao {
	
	// 출석 이벤트 갯수
	int 			 totalAtt();
	//EventList.이벤트 번호 받아오기 위함 메소드
	List<Attendance> listEvent(Attendance attendance);
	int 			 divideAttNum(int eNum);
	Attendance 		 attendance(int eNum);

}