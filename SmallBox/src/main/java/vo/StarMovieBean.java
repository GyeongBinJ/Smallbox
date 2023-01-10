package vo;

import java.sql.Date;

public class StarMovieBean {
	private int movie_idx;
	private String movie_title;
	private String movie_real_picture;
	private Date movie_open_date;
	private Double comment_star;
	private int movie_viewer;
	
	public int getMovie_idx() {
		return movie_idx;
	}
	public void setMovie_idx(int movie_idx) {
		this.movie_idx = movie_idx;
	}
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public String getMovie_real_picture() {
		return movie_real_picture;
	}
	public void setMovie_real_picture(String movie_real_picture) {
		this.movie_real_picture = movie_real_picture;
	}
	public Date getMovie_open_date() {
		return movie_open_date;
	}
	public void setMovie_open_date(Date movie_open_date) {
		this.movie_open_date = movie_open_date;
	}
	public Double getComment_star() {
		return comment_star;
	}
	public void setComment_star(Double comment_star) {
		this.comment_star = comment_star;
	}
	public int getMovie_viewer() {
		return movie_viewer;
	}
	public void setMovie_viewer(int movie_viewer) {
		this.movie_viewer = movie_viewer;
	}
	
	@Override
	public String toString() {
		return "StarMovieBean [movie_idx=" + movie_idx + ", movie_title=" + movie_title + ", movie_real_picture="
				+ movie_real_picture + ", movie_open_date=" + movie_open_date + ", comment_star=" + comment_star
				+ ", movie_viewer=" + movie_viewer + "]";
	}
	
	

}
