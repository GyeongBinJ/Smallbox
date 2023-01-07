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
import svc.isRightUserService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberFindPasswdProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		System.out.println("MemberFindPasswdProAction");
		
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String member_id = request.getParameter("member_id");
			String member_email = request.getParameter("member_email");
			
			MemberBean member = new MemberBean();
			member.setMember_id(member_id);
			member.setMember_email(member_email);
			
			isRightUserService service = new isRightUserService();
			boolean isExistUser = service.passwdFind(member);
			if(isExistUser) {
//			System.out.println(isExistUser);
			
			String[] ch = {
		            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
		            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
		            "0","1","2","3","4","5","6","7","8","9"
		         };
			Random random = new Random();
			String imsiPW = ""; 
			int codeLength = 10; 
			
			// 원하는 코드 길이만큼 for문을 사용하여 반복문으로 처리
			for(int i = 1; i <= codeLength; i++) {
				// 배열 크기를 난수의 범위로 지정하여 난수 생성
				int index = random.nextInt(ch.length);
				
				// 생성된 난수를 배열 인덱스로 활용하여 1개의 코드 가져와서 문자열 결합
				imsiPW += ch[index];
			}
			
			System.out.println("인증코드 : " + imsiPW);
			
			boolean updatePasswd = service.updatePasswd(imsiPW, member);
			if(updatePasswd) {
				System.out.println("비밀번호 변경완료!");
			}
			
			String content = "임시 비밀번호로 로그인 한 후, 회원정보 수정에서 비밀번호 변경하세요.";
			content += "<hr>";
			content += "<table>";
			content += "<tr>";
			content += "	<th>아이디</th>";
			content += "	<td>" + member_id + "</td>";
			content += "</tr>";
			content += "<tr>";
			content += "	<th>임시비밀번호</th>";
			content += "	<td>" + imsiPW + "</td>";
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
				// 수신자 정보 설정을 위한 InternetAddress 객체 생성
				Address resceiverAddress = new InternetAddress(member.getMember_email());
				
				// 서버 정보와 인증 정보를 포함하는 javax.mail.MimeMessage 객체 생성
				// => 파라미터 : javax.mail.Session 객체 전달
				// => 생성된 MimeMessage 객체를 통해 전송할 메일에 대한 정보 생성
				Message mailMessage = new MimeMessage(mailSession);
				
				// 수신자 정보 설정
				mailMessage.setRecipient(Message.RecipientType.TO, resceiverAddress);
				// 발신자 정보 설정
				mailMessage.setFrom(new InternetAddress("yongs1041@naver.com"));
				// 메일 제목 설정
				mailMessage.setSubject("[SmallBox] 아이디 / 비밀번호 입니다");
				// 메일 본문 설정
				mailMessage.setContent(content, "text/html; charset=UTF-8");
				// 메일 전송 날짜 및 시각 정보 설정
				//      => java.util.Date 객체 생성을 통해 시스템 시각 정보 사용
				mailMessage.setSentDate(new Date());
				
				// 메일 전송
				// javax.mail.Transport 클래스의 static 메서드 send() 호출
				// => 파라미터 : Message 객체
				Transport.send(mailMessage);
				
				forward = new ActionForward();
				forward.setPath("MemberLoginForm.sm");
				forward.setRedirect(false); 
				
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("<h3>SMTP 서버 설정 또는 서비스 문제 발생!</h3>");
			}
			
		}
		return forward;
	}
}