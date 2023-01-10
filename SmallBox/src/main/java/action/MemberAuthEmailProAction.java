package action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.GoogleMailAuthenticator;
import svc.AuthcodeInsertService;
import vo.ActionForward;
import vo.AuthBean;

public class MemberAuthEmailProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		System.out.println("MemberAuthEmailProAction");
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String id = request.getParameter("id");
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		
		// 난수 설정 
		Random random = new Random();
		String emailAuthCode = Integer.toString(random.nextInt(888888)+111111); 
		
		System.out.println("인증코드 : " + emailAuthCode);
		
		
		String content = "인증코드 입니다. 인증코드 입력란에 입력하세요.";
		content += "<hr>";
		content += "<table>";
		content += "<tr>";
		content += "	<th>인증코드</th>";
		content += "	<td>" + emailAuthCode + "</td>";
		content += "</tr>";
		content += "</table>";
		
		
		// --- 메일 전송 위한 설정 작업 
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port", "587"); 
		props.put("mail.smtp.starttls.enable", "true"); 
		props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
		
		Authenticator myAuth = new GoogleMailAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, myAuth);
		
		try {
			
			Address senderAddress = new InternetAddress("sdo7653@gamil.com", "Smallbox");
			// 수신자 정보 설정을 위한 InternetAddress 객체 생성
			Address resceiverAddress = new InternetAddress(email1 + "@" + email2);
			
			// 서버 정보와 인증 정보를 포함하는 javax.mail.MimeMessage 객체 생성
			// => 파라미터 : javax.mail.Session 객체 전달
			// => 생성된 MimeMessage 객체를 통해 전송할 메일에 대한 정보 생성
			Message mailMessage = new MimeMessage(mailSession);
			
			// 수신자 정보 설정
			mailMessage.setRecipient(Message.RecipientType.TO, resceiverAddress);
			// 발신자 정보 설정
			mailMessage.setFrom(senderAddress);
			// 메일 제목 설정
			mailMessage.setSubject("[SmallBox] 인증 코드입니다");
			// 메일 본문 설정
			mailMessage.setContent(content, "text/html; charset=UTF-8");
			//메일 전송 날짜 및 시각 정보 설정
			//      => java.util.Date 객체 생성을 통해 시스템 시각 정보 사용
			mailMessage.setSentDate(new Date());
			
			// 메일 전송
			// javax.mail.Transport 클래스의 static 메서드 send() 호출
			// => 파라미터 : Message 객체
			Transport.send(mailMessage);
			
			AuthBean auth = new AuthBean();
			auth.setAuth_id(id);
			auth.setAuth_authCode(emailAuthCode);
			
			AuthcodeInsertService service = new AuthcodeInsertService();
			boolean insertSuccess = service.authcodeInsert(auth);
			if(insertSuccess) {
				System.out.println("auth테이블 데이터 저장완료!");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("<h3>SMTP 서버 설정 또는 서비스 문제 발생!</h3>");
		}
		
		
		return forward;
	}

}
