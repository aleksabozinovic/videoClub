package com.videoclub.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.movieloan.MovieLoan;

@Repository
@Configurable
public interface MovieLoanRepository extends JpaRepository<MovieLoan, Integer>{
    List<MovieLoan> findByMovieInventarskiBrojAndMemberBrojClanskeKarte(Integer inventarskiBroj, Integer brojClanskeKarte);
    List<MovieLoan> findByDatumVracanjaFilmaIsNotNull();
    List<MovieLoan> findByDatumVracanjaFilma(LocalDate datumVracanja);
    List<MovieLoan> findByDatumIznajmljivanjaFilma(LocalDate datumPozajmice);
    List<MovieLoan> findByMemberIme(String ime);
    List<MovieLoan> findByMemberPrezime(String prezime);
    List<MovieLoan> findByMovieNaslov(String naslov);
    List<MovieLoan> findByMovieInventarskiBroj(Integer inventarskiBroj);
    int countByMemberBrojClanskeKarteAndDatumVracanjaFilmaIsNull(Integer brojClanskeKarte);


}

