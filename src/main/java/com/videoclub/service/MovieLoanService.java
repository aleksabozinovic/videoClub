package com.videoclub.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoclub.member.Member;
import com.videoclub.movie.Movie;
import com.videoclub.movieloan.MovieLoan;
import com.videoclub.repository.MemberRepository;
import com.videoclub.repository.MovieLoanRepository;
import com.videoclub.repository.MovieRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MovieLoanService {
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private MovieLoanRepository movieLoanRepo;
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired private EntityManager entityManager;

	// Prikazivanje Filmova
	public List<Movie> listMovies(){

		List<Movie> allMovies = movieRepo.findAll();
		List<Movie> borrowedMovies = movieLoanRepo.findAll().stream()
				.filter(loan -> loan.getMovieReturnDate() == null)
				.map(MovieLoan::getMovie)
				.collect(Collectors.toList());
		
		return  allMovies.stream()
				.filter(movie -> !borrowedMovies.contains(movie))
				.collect(Collectors.toList());	
	}



	public void saveManyToMany(Integer inventoryNumber, Integer memberCardNumber, String name, String lastName) throws Exception {
		
		List<Movie> movieList = movieRepo.findByInventoryNumber(inventoryNumber);
		List<Member> memberList = memberRepo.findByMemberCardNumberAndNameAndLastName(memberCardNumber, name, lastName);
		
		
	    int numberOfActiveLoans = movieLoanRepo.countByMemberMemberCardNumberAndMovieReturnDateIsNull(memberCardNumber);
	    if(numberOfActiveLoans > 4){
			 throw new Exception("Ne sme se pozajmiti vise od 5 filmova");
		 }
	    if (movieList.isEmpty() || memberList.isEmpty()) {
	        throw new Exception("Član ili film nije pronađen.");
	    }
	    
		Movie movie = movieList.get(0);
		Member member = memberList.get(0);	
		MovieLoan movieLoan = new MovieLoan();
		
		// Postavljanje filma i clana u movieLoan
		movieLoan.setMovie(movie);
		movieLoan.setMember(member);

		// Kreiranje datuma kada je member pozajmio film
		LocalDate todayDate = LocalDate.now();
		movieLoan.setMovieRentalDate(todayDate);

		int currentYear = todayDate.getYear();
		// Provera godina
		if (currentYear -  member.getBirthYear() < movie.getMpaRating()) {
			throw new Exception("Član nema dovoljno godina za iznajmljivanje ovog filma.");
        }
		
		movieLoanRepo.save(movieLoan);
		
	}
	
	// Vracanje Filma
	public void movieReturn(Integer inventoryNumber, Integer memberCardNumber, String name, String lastName) throws Exception {
		

	    List<MovieLoan> movieLoans = movieLoanRepo.findByMovieInventoryNumberAndMemberMemberCardNumber(inventoryNumber, memberCardNumber);		
	    
		// Provera da li je film pronadjen
	    if (!movieLoans.isEmpty()) {
	        LocalDate todayDate = LocalDate.now();
	        boolean isMovieReturned = false;
	        
	        for (MovieLoan movieLoan : movieLoans) {
	            // Provera da li se ime i prezime poklapaju sa članom koji vraća film
	            if (movieLoan.getMember().getName().equals(name) && movieLoan.getMember().getLastName().equals(lastName)) {
	                // Ažuriranje podataka o datumu vraćanja filma
	                movieLoan.setMovieReturnDate(todayDate);
	                // Čuvanje ažuriranih podataka
	                movieLoanRepo.save(movieLoan);
	                isMovieReturned = true;
	            }
	        }

	        // ako član sa datim imenom i prezimenom nije pronađen
	        if (!isMovieReturned) {
	            throw new Exception("Član sa datim imenom i prezimenom nije pronađen.");
	        }

	    } else {
	        throw new Exception("Film nije pronađen.");
	    }
	}
	



	

	
	public List<MovieLoan> findByCriteria(String name, String lastName, String title, Integer inventoryNumber, LocalDate movieRentalDate, LocalDate movieReturnDate) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<MovieLoan> cq = cb.createQuery(MovieLoan.class);
	    Root<MovieLoan> movieLoan = cq.from(MovieLoan.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (name != null && !name.isEmpty()) {
	        predicates.add(cb.like(movieLoan.get("member").get("name"), name + "%"));
	    }
	    if (lastName != null && !lastName.isEmpty()) {
	        predicates.add(cb.like(movieLoan.get("member").get("lastName"), lastName + "%"));
	    }
	    if (title != null && !title.isEmpty()) {
	        predicates.add(cb.like(movieLoan.get("movie").get("title"), title + "%"));
	    }
	    if (inventoryNumber != null) {
		    predicates.add(cb.like(cb.function("str", String.class, movieLoan.get("movie").get("inventoryNumber")), "%" + inventoryNumber + "%"));
	    }
	    if (movieRentalDate != null) {
	        predicates.add(cb.equal(movieLoan.get("movieRentalDate"), movieRentalDate));
	    }
	    if (movieReturnDate != null) {
	        predicates.add(cb.equal(movieLoan.get("movieReturnDate"), movieReturnDate));
	    }

	    cq.select(movieLoan).where(cb.and(predicates.toArray(new Predicate[0])));

	    return entityManager.createQuery(cq).getResultList();
	}

}
