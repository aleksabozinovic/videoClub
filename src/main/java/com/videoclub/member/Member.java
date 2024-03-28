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
	
	
	private String ime;
	private String prezime;
	private Integer godinaRodjenja;
	private String brojTelefona;
	private String mail;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "broj_clanske_karte")
	private Integer brojClanskeKarte;
	

	public Member(String ime, String prezime, Integer godinaRodjenja, String brojTelefona, String mail,
			Integer brojClanskeKarte) {
		this.ime = ime;
		this.prezime = prezime;
		this.godinaRodjenja = godinaRodjenja;
		this.brojTelefona = brojTelefona;
		this.mail = mail;
		this.brojClanskeKarte = brojClanskeKarte;
	}	
	public Member(String ime, String prezime, Integer godinaRodjenja, String brojTelefona, String mail) {
		this.ime = ime;
		this.prezime = prezime;
		this.godinaRodjenja = godinaRodjenja;
		this.brojTelefona = brojTelefona;
		this.mail = mail;
	}


	public Member() {
		
	}
	
	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public Integer getGodinaRodjenja() {
		return godinaRodjenja;
	}


	public void setGodinaRodjenja(Integer godinaRodjenja) {
		this.godinaRodjenja = godinaRodjenja;
	}


	public String getBrojTelefona() {
		return brojTelefona;
	}


	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public Integer getBrojClanskeKarte() {
		return brojClanskeKarte;
	}


	public void setBrojClanskeKarte(Integer brojClanskeKarte) {
		this.brojClanskeKarte = brojClanskeKarte;
	}


}
