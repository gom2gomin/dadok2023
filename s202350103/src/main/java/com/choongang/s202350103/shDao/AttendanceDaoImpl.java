package com.choongang.s202350103.shDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.choongang.s202350103.model.Attendance;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AttendanceDaoImpl implements AttendanceDao {

	private final SqlSession session;
	
	@Override
	public int totalAtt() {
		System.out.println("AttendanceDaoImpl totalAtt() Start..");
		int totalAtt = 0;
		try {
			totalAtt = session.selectOne("shTotalAtt");
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl totalAtt() Exception->"+e.getMessage());
		}
		return totalAtt;
	}

	@Override
	public List<Attendance> listEvent(Attendance attendance) {
		System.out.println("AttendanceDaoImpl listEvent Start..");
		List<Attendance> eventList = null;
		try {
			eventList = session.selectList("shEventList",attendance);
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl listEvent Exception->"+e.getMessage());
		}
		return eventList;
	}

	@Override
	public int divideAttNum(int eNum) {
		System.out.println("AttendanceDaoImpl divideAttNum() Start..");
		int num = 0;
		try {
			num = session.selectOne("shDivideAttNum", eNum);
			System.out.println("AttendanceDaoImpl divideAttNum() eNum->"+num);
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl divideAttNum() Exception->"+e.getMessage());
		}
		return num;
	}

	
	@Override
	public Attendance attendance(int eNum) {
		System.out.println("AttendanceDaoImpl listAttendance() Start..");
		Attendance attendance = null;
		System.out.println("eNum"+eNum);
		try {
			attendance = session.selectOne("shAttendance",eNum);
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl listAttendance() Exception ->"+e.getMessage());
		}
		return attendance;
	}

	@Override
	public int startMonth(int eNum) {
		int sMonth = 0;
		try {
			sMonth = session.selectOne("shStartMonth",eNum);
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl startMonth() Exception ->"+e.getMessage());
		}
		return sMonth;
	}

	@Override
	public int startYear(int eNum) {
		int sYear = 0;
		try {
			sYear = session.selectOne("shStartYear",eNum);
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl startYear() Exception ->"+e.getMessage());
		}
		return sYear;
	}

	@Override
	public int createAtt(Attendance attendance) {
		int result = 0;
		try {
			result = session.insert("shCreateAtt",attendance);
		} catch (Exception e) {
			System.out.println("AttendanceDaoImpl createAtt() Exception ->"+e.getMessage());
		}
		return result;
	}

}
