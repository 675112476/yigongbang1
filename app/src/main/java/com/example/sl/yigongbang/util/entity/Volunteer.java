package com.example.sl.yigongbang.util.entity;


public class Volunteer {

	private int id;
	private String phone;
	private String password;
	private String realName;
	private Integer gender;
	private Integer age;
	private String address;
	private String image;
	private Integer credit;
	private String nickName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "Volunteer{" +
				"id=" + id +
				", phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", realName='" + realName + '\'' +
				", gender=" + gender +
				", age=" + age +
				", address='" + address + '\'' +
				", image='" + image + '\'' +
				", credit=" + credit +
				", nickName='" + nickName + '\'' +
				'}';
	}
}
