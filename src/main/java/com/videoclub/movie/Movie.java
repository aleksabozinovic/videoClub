package com.videoclub.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {

	@Column(nullable = false, name = "naslov")
	private String naslov;
	
	@Column(name = "zanr")
	private String zanr;
	
	@Column(name= "mpa_rating")
	private String mpaRating;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventarski_broj")
	private Integer inventarskiBroj;
	
	@Column(name= "opis")
	private String opis;
	
	@Column(name= "jezik")
	private String jezik;
	
	@Column(name= "godina_snimanja")
	private String godinaSnimanja;
	
	@Column(name= "datum_unosa")
	private String datumUnosa;
	
	
	public Movie(String naslov, String zanr, String mpaRating, Integer inventarskiBroj, String opis, String jezik,
			String godinaSnimanja, String datumUnosa) {
		super();
		this.naslov = naslov;
		this.zanr = zanr;
		this.mpaRating = mpaRating;
		this.inventarskiBroj = inventarskiBroj;
		this.opis = opis;
		this.jezik = jezik;
		this.godinaSnimanja = godinaSnimanja;
		this.datumUnosa = datumUnosa;
	}


	public Movie(String naslov, String zanr, String mpaRating, String opis, String jezik, String godinaSnimanja,
			String datumUnosa) {
		super();
		this.naslov = naslov;
		this.zanr = zanr;
		this.mpaRating = mpaRating;
		this.opis = opis;
		this.jezik = jezik;
		this.godinaSnimanja = godinaSnimanja;
		this.datumUnosa = datumUnosa;
	}


	public Movie() {
		// TODO Auto-generated constructor stub
	}

	public String getNaslov() {
		return naslov;
	}


	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}


	public String getZanr() {
		return zanr;
	}


	public void setZanr(String zanr) {
		this.zanr = zanr;
	}


	public String getMpaRating() {
		return mpaRating;
	}


	public void setMpaRating(String d) {
		this.mpaRating = d;
	}


	public Integer getInventarskiBroj() {
		return inventarskiBroj;
	}


	public void setInventarskiBroj(Integer inventarskiBroj) {
		this.inventarskiBroj = inventarskiBroj;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getJezik() {
		return jezik;
	}


	public void setJezik(String jezik) {
		this.jezik = jezik;
	}


	public String getGodinaSnimanja() {
		return godinaSnimanja;
	}


	public void setGodinaSnimanja(String godinaSnimanja) {
		this.godinaSnimanja = godinaSnimanja;
	}


	public String getDatumUnosa() {
		return datumUnosa;
	}


	public void setDatumUnosa(String datumUnosa) {
		this.datumUnosa = datumUnosa;
	}


	@Override
	public String toString() {
		return "Movie [naslov=" + naslov + ", zanr=" + zanr + ", mpaRating=" + mpaRating + ", inventarskiBroj="
				+ inventarskiBroj + ", opis=" + opis + ", jezik=" + jezik + ", godinaSnimanja=" + godinaSnimanja
				+ ", datumUnosa=" + datumUnosa + "]";
	}

	
	
	
}
