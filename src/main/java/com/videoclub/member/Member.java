package com.videoclub.member;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {
	
	
	private String name;
	private String lastName;
	private Integer birthYear;
	private String phoneNumber;
	private String email;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_card_number")
	private Integer memberCardNumber;
	

	public Member(String name, String lastName, Integer birthYear, String phoneNumber, String email,
			Integer brojClanskeKarte) {
		this.name = name;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.memberCardNumber = brojClanskeKarte;
	}	
	public Member(String name, String lastName, Integer birthYear, String phoneNumber, String email) {
		this.name = name;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}


	public Member() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getMemberCardNumber() {
		return memberCardNumber;
	}
	public void setMemberCardNumber(Integer memberCardNumber) {
		this.memberCardNumber = memberCardNumber;
	}
	



}
