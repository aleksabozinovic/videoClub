package com.videoclub.member;

import java.util.HashSet;
import java.util.Set;

import com.videoclub.movieloan.MovieLoan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {
	
	
	private String ime;
	private String prezime;
	private String godinaRodjenja;
	private String brojTelefona;
	private String mail;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "broj_clanske_karte")
	private Integer brojClanskeKarte;
	
	
	@ManyToMany(mappedBy = "clanovi")
	private Set<MovieLoan> pozajmljivanje = new HashSet<>();
	
	
	
	public Set<MovieLoan> getPozajmljivanje() {
		return pozajmljivanje;
	}
	public void setPozajmljivanje(Set<MovieLoan> pozajmljivanje) {
		this.pozajmljivanje = pozajmljivanje;
	}
	public Member(String ime, String prezime, String godinaRodjenja, String brojTelefona, String mail,
			Integer brojClanskeKarte) {
		this.ime = ime;
		this.prezime = prezime;
		this.godinaRodjenja = godinaRodjenja;
		this.brojTelefona = brojTelefona;
		this.mail = mail;
		this.brojClanskeKarte = brojClanskeKarte;
	}	
	public Member(String ime, String prezime, String godinaRodjenja, String brojTelefona, String mail) {
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


	public String getGodinaRodjenja() {
		return godinaRodjenja;
	}


	public void setGodinaRodjenja(String godinaRodjenja) {
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
	@Override
	public String toString() {
		return "Member [ime=" + ime + ", prezime=" + prezime + ", godinaRodjenja=" + godinaRodjenja + ", brojTelefona="
				+ brojTelefona + ", mail=" + mail + ", brojClanskeKarte=" + brojClanskeKarte + ", pozajmljivanje="
				+ pozajmljivanje + "]";
	}

	

}
