package com.videoclub.movieloan;


import java.util.HashSet;
import java.util.Set;

import com.videoclub.member.Member;
import com.videoclub.movie.Movie;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;




@Entity
@Table(name= "movieLoan")
public class MovieLoan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private String datumIznajmljivanjaFilma;
	private String datumVracanjaFilma;
	
	@ManyToOne
	@JoinColumn(name="inventarski_broj", referencedColumnName = "inventarski_broj")
	private Movie movie;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "movie_loan_member",
			joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "inventarski_broj"),
			inverseJoinColumns = 
					@JoinColumn(name = "member_id")
				
			
			)
	
	private Set<Member> clanovi = new HashSet<>();
	
	// Uzima inventarski broj
	public Integer getInventarskiBroj() {
		return movie != null ? movie.getInventarskiBroj() : null;
	}
	
	
	
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Set<Member> getClanovi() {
		return clanovi;
	}

	public void setClanovi(Set<Member> clanovi) {
		this.clanovi = clanovi;
	}

	public MovieLoan(String datumIznajmljivanjaFilma, String datumVracanjaFilma) {
		super();
		this.datumIznajmljivanjaFilma = datumIznajmljivanjaFilma;
		this.datumVracanjaFilma = datumVracanjaFilma;
	}

	public MovieLoan() {
	
	}

	
	public String getDatumIznajmljivanjaFilma() {
		return datumIznajmljivanjaFilma;
	}

	public void setDatumIznajmljivanjaFilma(String datumIznajmljivanjaFilma) {
		this.datumIznajmljivanjaFilma = datumIznajmljivanjaFilma;
	}

	public String getDatumVracanjaFilma() {
		return datumVracanjaFilma;
	}

	public void setDatumVracanjaFilma(String datumVracanjaFilma) {
		this.datumVracanjaFilma = datumVracanjaFilma;
	}
	
	
	
}
