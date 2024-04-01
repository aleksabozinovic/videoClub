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
    int countByMemberMemberCardNumberAndMovieReturnDateIsNull(Integer memberCardNumber);


}

