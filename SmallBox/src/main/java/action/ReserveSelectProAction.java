package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ReserveSelectProService;
import vo.ActionForward;
import vo.TheaterBean;

public class ReserveSelectProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReserveSelectProAction");
		
		ActionForward forward = null;
		
		try {
			ReserveSelectProService service = new ReserveSelectProService();
			
			String movie_title = request.getParameter("movie_title");
			String reserve_date = request.getParameter("reserve_date");
			
			System.out.println("영화 제목: " + movie_title);
			System.out.println("예약 날짜: " + reserve_date);
			
			List<TheaterBean> theaterList = service.getTheaterList(movie_title, reserve_date);
			
//		request.setAttribute("theaterList", theaterList);
			System.out.println(theaterList);
			List<Time> timeList = theaterList.stream().map(TheaterBean::getTheater_time).collect(Collectors.toList());
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
//			out.println("<script>");
			out.println("<h3>" + movie_title + "</h3>");
			out.println("<input type='hidden' name='movie_title' value='" + movie_title + "'>");
			out.println("<h3>" + reserve_date + "</h3>");
			out.println("<input type='hidden' name='reserved_date' value='" + reserve_date + "'>");
			for(int i = 0; i < timeList.size(); i++) {
				
					out.println("<input type='button' class='timeButton' value='" + timeList.get(i)+ "'onclick='change_btn(event)'><br><br>");
					
			}
			out.println(" <input type='hidden' name='selected_time' value=''>");
//			out.println("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		forward = new ActionForward();
//		forward.setPath("reserve/reserve_view_Backup.jsp");
//		forward.setRedirect(false);
		
		
		return forward;
	}

}
