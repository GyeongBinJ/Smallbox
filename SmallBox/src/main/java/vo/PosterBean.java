package vo;

import java.sql.Date;

public class PosterBean {
	private String movie_real_picture;
	private int comment_idx;
	private String member_id;
	private int comment_star;
	private String comment_content;
	private Date comment_date;
	
	public String getMovie_real_picture() {
		return movie_real_picture;
	}
	public void setMovie_real_picture(String movie_real_picture) {
		this.movie_real_picture = movie_real_picture;
	}
	public int getComment_idx() {
		return comment_idx;
	}
	public void setComment_idx(int comment_idx) {
		this.comment_idx = comment_idx;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getComment_star() {
		return comment_star;
	}
	public void setComment_star(int comment_star) {
		this.comment_star = comment_star;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	
	@Override
	public String toString() {
		return "PosterBean [movie_real_picture=" + movie_real_picture + ", comment_idx=" + comment_idx + ", member_id="
				+ member_id + ", comment_star=" + comment_star + ", comment_content=" + comment_content
				+ ", comment_date=" + comment_date + "]";
	}
	
	
}
