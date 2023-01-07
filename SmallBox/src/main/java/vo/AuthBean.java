package vo;

public class AuthBean {
	private String auth_id;
	private String auth_authCode;
	
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	public String getAuth_authCode() {
		return auth_authCode;
	}
	public void setAuth_authCode(String auth_authCode) {
		this.auth_authCode = auth_authCode;
	}
	@Override
	public String toString() {
		return "AuthBean [auth_id=" + auth_id + ", auth_authCode=" + auth_authCode + "]";
	}
	
	
}
