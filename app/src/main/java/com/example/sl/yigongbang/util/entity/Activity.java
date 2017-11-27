package com.example.sl.yigongbang.util.entity;
import java.sql.Date;

public class Activity {

	private int id;
	private int adminId;
	private String actName;
	private String actTime;
	private String actLocation;
	private String actIntroduction;
	private String image;
	private Integer maxPeople;
	private Integer curPeople;
	private Boolean isValid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getActTime() {
		return actTime;
	}
	public void setActTime(String actTime) {
		this.actTime = actTime;
	}
	public String getActLocation() {
		return actLocation;
	}
	public void setActLocation(String actLocation) {
		this.actLocation = actLocation;
	}
	public String getActIntroduction() {
		return actIntroduction;
	}
	public void setActIntroduction(String actIntroduction) {
		this.actIntroduction = actIntroduction;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getMaxPeople() {
		return maxPeople;
	}
	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}
	public Integer getCurPeople() {
		return curPeople;
	}
	public void setCurPeople(Integer curPeople) {
		this.curPeople = curPeople;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", adminId=" + adminId + ", actName=" + actName + ", actTime=" + actTime
				+ ", actLocation=" + actLocation + ", actIntroduction=" + actIntroduction + ", image=" + image
				+ ", maxPeople=" + maxPeople + ", curPeople=" + curPeople + ", isValid=" + isValid + "]";
	}
}