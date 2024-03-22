package com.videoclub.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoclub.member.Member;
import com.videoclub.movie.Movie;
import com.videoclub.movieloan.MovieLoan;
import com.videoclub.repository.MemberRepository;
import com.videoclub.repository.MovieLoanRepository;
import com.videoclub.repository.MovieRepository;

@Service
public class MovieLoanService {
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private MovieLoanRepository movieLoanRepo;
	
	@Autowired
	private MovieRepository movieRepo;
	
	


	
	
	// Prikazivanje Filmova
	public List<Movie> listMovies(){

		List<Movie> allMovies = movieRepo.findAll();
		List<Movie> borrowedMovies = movieLoanRepo.findAll().stream()
				.filter(loan -> loan.getDatumVracanjaFilma() == null)
				.map(MovieLoan::getMovie)
				.collect(Collectors.toList());	
		
		return  allMovies.stream()
				.filter(movie ->! borrowedMovies.contains(movie))
				.collect(Collectors.toList());
		
	}



	public void saveManyToMany(Integer inventarskiBroj, Integer brojClanskeKarte) {
		
		List<Movie> movieList = movieRepo.findByInventarskiBroj(inventarskiBroj);
		List<Member> memberList = memberRepo.findByBrojClanskeKarte(brojClanskeKarte);
		
		Movie movie = movieList.get(0);
		Member member = memberList.get(0);		
		
		MovieLoan movieLoan = new MovieLoan();
		
		// Postavljanje filma i clana u movieLoan
		movieLoan.setMovie(movie);
//		Set<Member> clanovi = new HashSet<>();
//		clanovi.add(member);
		movieLoan.getClanovi().add(member);
		
		// Kreiranje datuma kada je member pozajmio film
		LocalDate danasnjiDatum = LocalDate.now();
		String stringDanasnjiDatum = danasnjiDatum.toString();		
		movieLoan.setDatumIznajmljivanjaFilma(stringDanasnjiDatum);
		
		movieLoanRepo.save(movieLoan);
		
	}
	
	// Vracanje Filma
	public void vracanjeFilmova(Integer inventarskiBroj, Integer brojClanskeKarte, String ime, String prezime) {
		
		
		MovieLoan movieLoan = movieLoanRepo.findByMovieInventarskiBrojAndClanoviBrojClanskeKarte(inventarskiBroj, brojClanskeKarte);
		
		// Provera da li je film pronadjen
		if(movieLoan != null) {
			// Azuriranje podataka o datumu vracanja filma
			LocalDate danasnjiDatum = LocalDate.now();
			movieLoan.setDatumVracanjaFilma(danasnjiDatum.toString());
			
			// Uklanjanje clana iz liste clanova koji su pozajmili film
			movieLoan.getClanovi().removeIf(member -> member.getBrojClanskeKarte().equals(brojClanskeKarte));
						
			// Cuvanje azuriranih podataka
			movieLoanRepo.save(movieLoan);
			
			
			
		}else {
			System.out.println("Film nije pronadjen");
		}	
	}
}
