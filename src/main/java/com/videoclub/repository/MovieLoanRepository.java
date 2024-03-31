package com.videoclub.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.movieloan.MovieLoan;

@Repository
@Configurable
public interface MovieLoanRepository extends JpaRepository<MovieLoan, Integer>{
    List<MovieLoan> findByMovieInventoryNumberAndMemberMemberCardNumber(Integer inventarskiBroj, Integer memberCardNumber);
    List<MovieLoan> findByMovieReturnDateIsNotNull();
//    List<MovieLoan> findByDatumVracanjaFilma(LocalDate datumVracanja);
//    List<MovieLoan> findByDatumIznajmljivanjaFilma(LocalDate datumPozajmice);
    
    List<MovieLoan> findByMemberName(String name);
    List<MovieLoan> findByMemberLastName(String lastName);
    List<MovieLoan> findByMovieTitle(String title);
    List<MovieLoan> findByMovieInventoryNumber(Integer inventoryNumber);
    int countByMemberMemberCardNumberAndMovieReturnDateIsNull(Integer memberCardNumber);
//    
//    @Query("SELECT ml FROM MovieLoan ml WHERE CAST(ml.movie.inventarskiBroj AS string) LIKE CONCAT(:prefix, '%')")
//    List<MovieLoan> findByInventarskiBrojAsString(@Param("prefix") String prefix);

}

