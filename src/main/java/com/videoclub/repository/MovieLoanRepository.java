package com.videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.movieloan.MovieLoan;

@Repository
public interface MovieLoanRepository extends JpaRepository<MovieLoan, Integer>{
    MovieLoan findByMovieInventarskiBrojAndClanoviBrojClanskeKarte(Integer inventarskiBroj, Integer brojClanskeKarte);
    List<MovieLoan> findByDatumVracanjaFilmaIsNotNull();
}
