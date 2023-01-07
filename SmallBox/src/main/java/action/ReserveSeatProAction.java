package action;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.CouponListService;
import svc.ReserveGetTheaterIdxService;
import svc.ReserveShowSelectedSeatService;
import vo.ActionForward;
import vo.CouponBean;
import vo.ReserveBean;
import vo.TheaterBean;

public class ReserveSeatProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReserveSeatProAction");
		ActionForward forward = null;
		
		String session_id = request.getParameter("session_id");
		String theater_title = request.getParameter("movie_title");
		String theater_date = request.getParameter("reserved_date");
		String theater_time = request.getParameter("selected_time");
		System.out.println("reserve seeat Pro action의 session id : " + session_id);
		System.out.println("reserve seeat Pro action의 theater_title : " + theater_title);
		System.out.println("reserve seeat Pro action의 theater_date : " + theater_date);
		System.out.println("reserve seeat Pro action의 theater_time : " + theater_time);
		
		//theater_idx 조회하기
		
		TheaterBean theater = new TheaterBean();
		theater.setTheater_title(theater_title);
		theater.setTheater_date(Date.valueOf(theater_date));
		theater.setTheater_time(Time.valueOf(theater_time));
		
		ReserveGetTheaterIdxService service = new ReserveGetTheaterIdxService();
		int theater_idx = service.getTheaterIdx(theater);
//		System.out.println("pro action : return 받은 theater_idx : " + theater_idx);

		//리턴 받은 theater_idx사용해서 이미 예약 된 좌석번호 조회
		
		ReserveBean reserve = new ReserveBean();
		reserve.setTheater_idx(theater_idx);
		reserve.setRes_date(Date.valueOf(theater_date));
		reserve.setRes_time(Time.valueOf(theater_time));
		reserve.setTheater_title(theater_title);
		
		ReserveShowSelectedSeatService sService = new ReserveShowSelectedSeatService();
		List<String> reservedList = sService.getReservedSeatList(reserve);
				
		System.out.println("reserve seat pro action의 reserved list : " + reservedList);
//		request.setAttribute("reservedList", reservedList);
		
		request.setAttribute("theater_idx", theater_idx);
			
		String selectedSeatList = String.join(",", reservedList);
		System.out.println(selectedSeatList);
		request.setAttribute("selectedSeatList", selectedSeatList);
		
		forward = new ActionForward();
		forward.setPath("reserve/reserve_seat.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
