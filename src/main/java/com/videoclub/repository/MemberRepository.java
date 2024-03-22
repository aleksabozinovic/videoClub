package com.videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	List<Member> findByIme(String ime);
	List<Member> findByPrezime(String prezime);
	List<Member> findByBrojTelefona(String brojTelefona);
	List<Member> findByBrojClanskeKarte(Integer brojClanskeKarte);
}
