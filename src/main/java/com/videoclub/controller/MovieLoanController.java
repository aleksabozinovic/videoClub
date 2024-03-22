package com.videoclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.member.Member;
import com.videoclub.movie.Movie;
import com.videoclub.repository.MemberRepository;
import com.videoclub.repository.MovieRepository;
import com.videoclub.service.MovieLoanService;
import com.videoclub.service.MovieService;

@Controller
public class MovieLoanController {
	
	
	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private MovieLoanService movieLoanService;
	
	@Autowired
	private MovieService movieService;
	
	
	
	@GetMapping("/pregledEvidencije")
	public String showPregledEvidencije() {
	
		return "pregledEvidencije";
		}

	
	@GetMapping("/movieLoan")
	public String showPozajmljivanjeFilmova(Model model) {
		List<Movie> movieLists = movieLoanService.listMovies();
		
//		System.out.println("svi filmovi"  + movieLists);
		
		model.addAttribute("movieLists", movieLists);
		return "movieLoan";
		}
	
	@GetMapping("/movieLoan/pozajmljivanje/{inventarskiBroj}")
	public String pozajmljivanjeFilmova(
			@PathVariable String inventarskiBroj, Model model) {
		
		List<Movie> movies = movieService.findMoviesByInventarskomBroju(Integer.parseInt(inventarskiBroj));
		model.addAttribute("movieLists",movies);
		return "loanForm";
	}
	
	@PostMapping("/movieLoan/pozajmljivanje/{inventarskiBroj}")
	public ResponseEntity<String> sacuvajPozajmljivanje(
			@RequestParam String ime,
			@RequestParam String prezime,
			@RequestParam Integer brojClanskeKarte,
			@PathVariable Integer inventarskiBroj
			){
		
		List<Member> member = memberRepo.findByBrojClanskeKarte(brojClanskeKarte);
		List<Member> memberIme = memberRepo.findByIme(ime);
		List<Member> memberPrezime = memberRepo.findByPrezime(prezime);
		List<Movie> movie = movieRepo.findByInventarskiBroj(inventarskiBroj);
		
		// napraviti proveru da da li se poklapaju ime, prezime i inventarski broj pre nego sto se sacuva
		if(!member.isEmpty() && !movie.isEmpty() && !memberIme.isEmpty() && !memberPrezime.isEmpty()) {
			movieLoanService.saveManyToMany(inventarskiBroj, brojClanskeKarte);
			String htmlResponse = "<html><body><h1>Podaci su uspesno sacuvani.</h1>"
                    + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
			
			return ResponseEntity.ok().body(htmlResponse);
		}else {
			String htmlResponse = "<html><body><h1>Podaci o članu ili filmu nisu tačni.</h1>"
                    + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
			return ResponseEntity.badRequest().body(htmlResponse);
		}

	}

	@GetMapping("/movieReturn")
	public String showVracanjeFilmova() {
		return "movieReturn";
		}
	
	@PostMapping("/movieReturn")
	public ResponseEntity<String> vracanjeFilmova(
			@RequestParam String ime,
			@RequestParam String prezime,
			@RequestParam Integer brojClanskeKarte,
			@RequestParam Integer inventarskiBroj	
			) {
		
		List<Member> member = memberRepo.findByBrojClanskeKarte(brojClanskeKarte);
		List<Member> memberIme = memberRepo.findByIme(ime);
		List<Member> memberPrezime = memberRepo.findByPrezime(prezime);
		List<Movie> movie = movieRepo.findByInventarskiBroj(inventarskiBroj);
		
		// napraviti proveru da da li se poklapaju ime, prezime i inventarski broj pre nego sto se sacuva
		if(!member.isEmpty() && !movie.isEmpty() && !memberIme.isEmpty() && !memberPrezime.isEmpty()) {
			movieLoanService.vracanjeFilmova(inventarskiBroj, brojClanskeKarte, ime , prezime);
			String htmlResponse = "<html><body><h1>Uspesno ste vratili film.</h1>"
                    + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
			
			return ResponseEntity.ok().body(htmlResponse);
		}else {
			String htmlResponse = "<html><body><h1>Podaci o članu ili filmu nisu tačni.</h1>"
                    + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
			return ResponseEntity.badRequest().body(htmlResponse);
		}
		
	}
}