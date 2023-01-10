package action;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.ReserveDeleteCouponService;
import svc.ReservePaymentProService;
import svc.ReserveSubtractSeatService;
import vo.ActionForward;
import vo.ReserveBean;

public class ReservePaymentProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("action : ReservePaymentProAction");
		ActionForward forward = null;
		
		//가져온 값을 변수에 저장
		int theater_idx = Integer.parseInt(request.getParameter("theater_idx"));
		String theater_title = request.getParameter("theater_title");
		String member_id = request.getParameter("member_id");
		Date res_date = Date.valueOf(request.getParameter("res_date"));
		Time res_time = Time.valueOf(request.getParameter("res_time"));
		int res_price = Integer.parseInt(request.getParameter("res_price"));
		int coupon_idx = Integer.parseInt(request.getParameter("coupon_idx"));
		
		//좌석 뺼 때 사람 수
		int people = 0;
		
		//예매 번호 만들기
	//=========================================
		//너무 길어서 그런가 string으로 계속 뜸
//		String datestr = request.getParameter("res_date").replace("-", "");
//		System.out.println("pro action의 datestr = " + datestr);
		
//		String frontRNum = datestr + request.getParameter("theater_idx");
//		임시로 극장번호 01 넣음
//		String frontRNum = datestr + "10";
//		System.out.println("예약번호 앞에 번호 : " + frontRNum);
	//=========================================
		
		//		좌석은 갯수 때문에 따로 함
		System.out.println("pro action's theater_idx : " + theater_idx);
		System.out.println("pro action's coupon_idx : " + coupon_idx);
//		System.out.println("theater_title : " + theater_title);
//		System.out.println("member_id : " + member_id);
//		System.out.println("res_date : " + res_date);
//		System.out.println("res_time : " + res_time);
//		System.out.println("res_price : " + res_price);
		
		String strSeat = request.getParameter("res_seat");
		System.out.println("strSeat : " + strSeat);
		String [] arrSeat = strSeat.split(",");
		 
		for(int i =0; i<arrSeat.length; i++){
		System.out.println(arrSeat[i]);
		}
//		최대 4놈까지만 예약 가능, 1번째 예약자
		System.out.println("arrSeat.length : " + arrSeat.length);
		
		ReserveBean reserve = new ReserveBean();
		reserve.setTheater_idx(theater_idx);
		reserve.setTheater_title(theater_title);
		reserve.setMember_id(member_id);
		reserve.setRes_date(res_date);
		reserve.setRes_time(res_time);
		reserve.setRes_price(res_price);
		
		String res_seat = arrSeat[0];
		reserve.setRes_seat(res_seat);

		int res_num = Integer.parseInt(theater_idx+res_seat);
//		System.out.println("reserve num : " + res_num);
		reserve.setRes_num(res_num);
		
		System.out.println("reserve payment Pro action의 reserve :  " + reserve);
		
		ReservePaymentProService service = new ReservePaymentProService();
		boolean isReserveSuccess = service.reserveAndPayment(reserve);
		System.out.println("pro action의 isReserveSuccess: " + isReserveSuccess);
		
		people = 1;
		
		if(arrSeat.length == 2) {
			reserve = new ReserveBean();
			reserve.setTheater_idx(theater_idx);
			reserve.setTheater_title(theater_title);
			reserve.setMember_id(member_id);
			reserve.setRes_date(res_date);
			reserve.setRes_time(res_time);
			reserve.setRes_price(res_price);
			
			res_seat = arrSeat[1];
			reserve.setRes_seat(res_seat);
		
			res_num = Integer.parseInt(theater_idx+res_seat);
//			System.out.println("reserve num : " + res_num);
			reserve.setRes_num(res_num);
			
			System.out.println("reserve payment Pro action의 reserve :  " + reserve);
			
			service = new ReservePaymentProService();
			boolean isReserveSuccess2 = service.reserveAndPayment(reserve);
			System.out.println("pro action의 isReserveSuccess2: " + isReserveSuccess2);
			people = 2;
			System.out.println("2 ? " + people);
			
		} else if(arrSeat.length == 3) {
			for(int i = 1; i <3; i++) {
				reserve = new ReserveBean();
				reserve.setTheater_idx(theater_idx);
				reserve.setTheater_title(theater_title);
				reserve.setMember_id(member_id);
				reserve.setRes_date(res_date);
				reserve.setRes_time(res_time);
				reserve.setRes_price(res_price);
				
				res_seat = arrSeat[i];
				reserve.setRes_seat(res_seat);
			
				res_num = Integer.parseInt(theater_idx+res_seat);
//				System.out.println("reserve num : " + res_num);
				reserve.setRes_num(res_num);
				
				System.out.println("reserve payment Pro action의 reserve :  " + reserve);
				
				service = new ReservePaymentProService();
				if(i==1) {
					Boolean isReserveSuccess2 = service.reserveAndPayment(reserve);
					System.out.println("pro action의 isReserveSuccess2: " + isReserveSuccess2);
				} else if(i==2) {
					Boolean isReserveSuccess3 = service.reserveAndPayment(reserve);
					System.out.println("pro action의 isReserveSuccess3: " + isReserveSuccess3);
					people = 3;
					System.out.println("3 ? " + people);
				}
			} //3일 떄 for end
			
		} else if(arrSeat.length == 4) {
			for(int i = 1; i <4; i++) {
				reserve = new ReserveBean();
				reserve.setTheater_idx(theater_idx);
				reserve.setTheater_title(theater_title);
				reserve.setMember_id(member_id);
				reserve.setRes_date(res_date);
				reserve.setRes_time(res_time);
				reserve.setRes_price(res_price);
				
				res_seat = arrSeat[i];
				reserve.setRes_seat(res_seat);
			
				res_num = Integer.parseInt(theater_idx+res_seat);
//				System.out.println("reserve num : " + res_num);
				reserve.setRes_num(res_num);
				
				System.out.println("reserve payment Pro action의 reserve :  " + reserve);
				
				service = new ReservePaymentProService();
				if(i==1) {
					Boolean isReserveSuccess2 = service.reserveAndPayment(reserve);
					System.out.println("pro action의 isReserveSuccess2: " + isReserveSuccess2);
				} else if(i==2) {
					Boolean isReserveSuccess3 = service.reserveAndPayment(reserve);
					System.out.println("pro action의 isReserveSuccess3: " + isReserveSuccess3);
					
				} else if(i==3) {
					Boolean isReserveSuccess4 = service.reserveAndPayment(reserve);
					System.out.println("pro action의 isReserveSuccess3: " + isReserveSuccess4);
					people = 4;
					System.out.println("4 ? " + people);
				}
			} //3일 떄 for end
		}
	
//		delete coupon, 극장 번호 조회 후 최대인원 -1;
		ReserveSubtractSeatService ssservice = new ReserveSubtractSeatService();
		Boolean isSubtractSeatSuccess = ssservice.substractSeat(theater_idx, people);
		
		ReserveDeleteCouponService dcpService = new ReserveDeleteCouponService();
		boolean isDelCpSuccess = dcpService.deleteCoupon(coupon_idx);
		
		HttpSession session = request.getSession();
		session.setAttribute("res_num", res_num);
		
		forward = new ActionForward();
		forward.setPath("ReservePaymentApi.mv");
		forward.setRedirect(false);
		
		return forward;
	}

}
