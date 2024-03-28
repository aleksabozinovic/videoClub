package com.videoclub.movieloan;

import java.time.LocalDate;

import com.videoclub.member.Member;
import com.videoclub.movie.Movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;




@Entity
@Table(name= "movieLoan")
public class MovieLoan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	private LocalDate datumIznajmljivanjaFilma;
	private LocalDate datumVracanjaFilma;
	
	@ManyToOne
	@JoinColumn(name="inventarski_broj", referencedColumnName = "inventarski_broj")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name="broj_clanske_karte", referencedColumnName = "broj_clanske_karte")
	private Member member;
	

	
	// Uzima inventarski broj
	public Integer getInventarskiBroj() {
		return movie != null ? movie.getInventarskiBroj() : null;
	}
	
	
//	Dodavanje iz drugih klasa
	public String getNazivFilma() {
		return movie != null ? movie.getNaslov() : null;
	}

	
	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}



	public MovieLoan(LocalDate datumIznajmljivanjaFilma, LocalDate datumVracanjaFilma) {
		super();
		this.datumIznajmljivanjaFilma = datumIznajmljivanjaFilma;
		this.datumVracanjaFilma = datumVracanjaFilma;
	}

	public MovieLoan() {

	}

	
	public LocalDate getDatumIznajmljivanjaFilma() {
		return datumIznajmljivanjaFilma;
	}

	public void setDatumIznajmljivanjaFilma(LocalDate datumIznajmljivanjaFilma) {
		this.datumIznajmljivanjaFilma = datumIznajmljivanjaFilma;
	}

	public LocalDate getDatumVracanjaFilma() {
		return datumVracanjaFilma;
	}

	public void setDatumVracanjaFilma(LocalDate datumVracanjaFilma) {
		this.datumVracanjaFilma = datumVracanjaFilma;
	}


	
}
