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
				.filter(loan -> loan.getDatumVracanjaFilma() == null)
				.map(MovieLoan::getMovie)
				.collect(Collectors.toList());	
		
		return  allMovies.stream()
				.filter(movie ->! borrowedMovies.contains(movie))
				.collect(Collectors.toList());	
	}



	public void saveManyToMany(Integer inventarskiBroj, Integer brojClanskeKarte, String ime, String prezime) throws Exception {
		
		List<Movie> movieList = movieRepo.findByInventarskiBroj(inventarskiBroj);
		List<Member> memberList = memberRepo.findByBrojClanskeKarteAndImeAndPrezime(brojClanskeKarte, ime, prezime);
		
		
	    int brojAktivnihPozajmica = movieLoanRepo.countByMemberBrojClanskeKarteAndDatumVracanjaFilmaIsNull(brojClanskeKarte);
	    if(brojAktivnihPozajmica > 4){
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
		LocalDate danasnjiDatum = LocalDate.now();
		movieLoan.setDatumIznajmljivanjaFilma(danasnjiDatum);

		int currentYear = danasnjiDatum.getYear();
		// Provera godina
		if (currentYear -  member.getGodinaRodjenja() < movie.getMpaRating()) {
			throw new Exception("Član nema dovoljno godina za iznajmljivanje ovog filma.");
        }
		
		movieLoanRepo.save(movieLoan);
		
	}
	
	// Vracanje Filma
	public void vracanjeFilmova(Integer inventarskiBroj, Integer brojClanskeKarte, String ime, String prezime) throws Exception {
		

	    List<MovieLoan> movieLoans = movieLoanRepo.findByMovieInventarskiBrojAndMemberBrojClanskeKarte(inventarskiBroj, brojClanskeKarte);		
	    
		// Provera da li je film pronadjen
	    if (!movieLoans.isEmpty()) {
	        LocalDate danasnjiDatum = LocalDate.now();
	        boolean filmVracen = false;
	        
	        for (MovieLoan movieLoan : movieLoans) {
	            // Provera da li se ime i prezime poklapaju sa članom koji vraća film
	            if (movieLoan.getMember().getIme().equals(ime) && movieLoan.getMember().getPrezime().equals(prezime)) {
	                // Ažuriranje podataka o datumu vraćanja filma
	                movieLoan.setDatumVracanjaFilma(danasnjiDatum);
	                // Čuvanje ažuriranih podataka
	                movieLoanRepo.save(movieLoan);
	                filmVracen = true;
	            }
	        }

	        // izuzetk ako član sa datim imenom i prezimenom nije pronađen
	        if (!filmVracen) {
	            throw new Exception("Član sa datim imenom i prezimenom nije pronađen.");
	        }

	    } else {
	        throw new Exception("Film nije pronađen.");
	    }
	}
	



	
	public List<MovieLoan> searchByIme(String ime) {
	    return movieLoanRepo.findByMember_ImeStartingWith(ime);
	}

	public List<MovieLoan> searchByPrezime(String prezime) {
	    return movieLoanRepo.findByMember_PrezimeStartingWith(prezime);
	}

	public List<MovieLoan> searchByNaslov(String naslov) {
	    return movieLoanRepo.findByMovie_NaslovStartingWith(naslov);
	}

	public List<MovieLoan> searchByInventarskiBroj(String inventarskiBroj) {
	    return movieLoanRepo.findByInventarskiBrojAsString( inventarskiBroj.toString());
	}
	public List<MovieLoan> searchByDatumPozajmice(LocalDate datumPozajmice) {
	    return movieLoanRepo.findByDatumIznajmljivanjaFilma(datumPozajmice);
	}

	public List<MovieLoan> searchByDatumVracanja(LocalDate datumVracanja) {
	    return movieLoanRepo.findByDatumVracanjaFilma(datumVracanja);
	}
	
	public List<MovieLoan> findByCriteria(String ime, String prezime, String naslov, Integer inventarskiBroj, LocalDate datumPozajmice, LocalDate datumVracanja) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<MovieLoan> cq = cb.createQuery(MovieLoan.class);
	    Root<MovieLoan> movieLoan = cq.from(MovieLoan.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (ime != null && !ime.isEmpty()) {
	        predicates.add(cb.like(movieLoan.get("member").get("ime"), ime + "%"));
	    }
	    if (prezime != null && !prezime.isEmpty()) {
	        predicates.add(cb.like(movieLoan.get("member").get("prezime"), prezime + "%"));
	    }
	    if (naslov != null && !naslov.isEmpty()) {
	        predicates.add(cb.like(movieLoan.get("movie").get("naslov"), naslov + "%"));
	    }
	    if (inventarskiBroj != null) {
		    predicates.add(cb.like(cb.function("str", String.class, movieLoan.get("movie").get("inventarskiBroj")), "%" + inventarskiBroj + "%"));
	    }
	    if (datumPozajmice != null) {
	        predicates.add(cb.equal(movieLoan.get("datumIznajmljivanjaFilma"), datumPozajmice));
	    }
	    if (datumVracanja != null) {
	        predicates.add(cb.equal(movieLoan.get("datumVracanjaFilma"), datumVracanja));
	    }

	    cq.select(movieLoan).where(cb.and(predicates.toArray(new Predicate[0])));

	    return entityManager.createQuery(cq).getResultList();
	}

}
