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
	
	private LocalDate movieRentalDate;
	private LocalDate movieReturnDate;
	
	@ManyToOne
	@JoinColumn(name="inventory_number", referencedColumnName = "inventory_number")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name="member_card_number", referencedColumnName = "member_card_number")
	private Member member;
	

	
//	// Uzima inventarski broj
//	public Integer getInventarskiBroj() {
//		return movie != null ? movie.getInventoryNumber() : null;
//	}
//	
//	
////	Dodavanje iz drugih klasa
//	public String getNazivFilma() {
//		return movie != null ? movie.getTitle() : null;
//	}

	
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



	public MovieLoan(LocalDate movieRentalDate, LocalDate movieReturnDate) {
		super();
		this.movieRentalDate = movieRentalDate;
		this.movieReturnDate = movieReturnDate;
	}

	public MovieLoan() {

	}


	public LocalDate getMovieRentalDate() {
		return movieRentalDate;
	}


	public void setMovieRentalDate(LocalDate movieRentalDate) {
		this.movieRentalDate = movieRentalDate;
	}


	public LocalDate getMovieReturnDate() {
		return movieReturnDate;
	}


	public void setMovieReturnDate(LocalDate movieReturnDate) {
		this.movieReturnDate = movieReturnDate;
	}



	
}
