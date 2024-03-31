package com.videoclub.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.videoclub.movieloan.MovieLoan;
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

	
	  @GetMapping("/recordReview")
	    public String showRecordReview(Model model,
	                                        @RequestParam(value = "name", required = false) String name,
	                                        @RequestParam(value = "lastName", required = false) String lastName,
	                                        @RequestParam(value = "title", required = false) String title,
	                                        @RequestParam(value = "inventoryNumber", required = false) Integer inventoryNumber,
	                                        @RequestParam(value = "movieRentalDate", required = false) LocalDate movieRentalDate,
	                                        @RequestParam(value = "movieReturnDate", required = false) LocalDate movieReturnDate) {
		  
		    
		  List<MovieLoan> results = new ArrayList<>();
		    if (name != null || lastName != null || title != null || inventoryNumber != null || movieRentalDate != null || movieReturnDate != null) {
		        results = movieLoanService.findByCriteria(name, lastName, title, inventoryNumber, movieRentalDate, movieReturnDate);
		    }				  
		    model.addAttribute("results", results);

	        return "recordReviews";
	    }

	
	@GetMapping("/movieLoan")
	public String showLoanedMovies(Model model) {
		List<Movie> movieLists = movieLoanService.listMovies();
		

		model.addAttribute("movieLists", movieLists);
		return "movieLoan";
		}
	
	@GetMapping("/movieLoan/loan/{inventoryNumber}")
	public String borrowMovies(
			@PathVariable String inventoryNumber, Model model) {
		
		List<Movie> movies = movieService.findMoviesByInventoryNumber(Integer.parseInt(inventoryNumber));
		model.addAttribute("movieLists",movies);
		return "loanForm";
	}
	
	@PostMapping("/movieLoan/loan/{inventoryNumber}")
	public ResponseEntity<String> saveBorrowedMovies(
			@RequestParam String name,
			@RequestParam String lastName,
			@RequestParam Integer memberCardNumber,
			@PathVariable Integer inventoryNumber
			){
		

		
		// napraviti proveru da da li se poklapaju ime, prezime i inventarski broj pre nego sto se sacuva
		try {
			movieLoanService.saveManyToMany(inventoryNumber, memberCardNumber,name , lastName);
			String htmlResponse = "<html><body><h1>Podaci su uspešno sačuvani.</h1>"
					+ "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";

			return ResponseEntity.ok().body(htmlResponse);
		} catch (Exception e) {
			String htmlResponse = "<html><body><h1>" + e.getMessage() + "</h1>"
					+ "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
			return ResponseEntity.badRequest().body(htmlResponse);
		}

	}

	@GetMapping("/movieReturn")
	public String showReturnedMovies() {
		return "movieReturn";
		}
	@PostMapping("/movieReturn")
	public ResponseEntity<String> movieReturn(
			@RequestParam String name,
			@RequestParam String lastName,
			@RequestParam Integer memberCardNumber,
			@RequestParam Integer inventoryNumber	
			) {
		List<Member> member = memberRepo.findByMemberCardNumber(memberCardNumber);
		List<Member> memberIme = memberRepo.findByName(name);
		List<Member> memberPrezime = memberRepo.findByLastName(lastName);
		List<Movie> movie = movieRepo.findByInventoryNumber(memberCardNumber);

		// napraviti proveru da da li se poklapaju ime, prezime i inventarski broj pre nego sto se sacuva
		try {
		    if(!member.isEmpty() && !movie.isEmpty() && !memberIme.isEmpty() && !memberPrezime.isEmpty()) {
		        movieLoanService.movieReturn(inventoryNumber, memberCardNumber, name , lastName);
		        String htmlResponse = "<html><body><h1>Uspesno ste vratili film.</h1>"
		                + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";

		        return ResponseEntity.ok().body(htmlResponse);
		    } else {
		        String htmlResponse = "<html><body><h1>Podaci o članu ili filmu nisu tačni.</h1>"
		                + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
		        return ResponseEntity.badRequest().body(htmlResponse);
		    }
		} catch (Exception e) {
		    String htmlResponse = "<html><body><h1>Došlo je do greške: " + e.getMessage() + "</h1>"
		            + "<a href=\"/\">Povratak na početnu stranicu</a></body></html>";
		    return ResponseEntity.internalServerError().body(htmlResponse);
		}

		
	}

}
