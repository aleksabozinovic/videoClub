package com.videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.videoclub.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	List<Member> findByIme(String ime);
	List<Member> findByPrezime(String prezime);
	List<Member> findByBrojTelefona(String brojTelefona);
	List<Member> findByBrojClanskeKarte(Integer brojClanskeKarte);
	List<Member> findByBrojClanskeKarteAndImeAndPrezime(Integer brojClanskeKarte, String ime, String prezime);
	@Query("SELECT m FROM Member m WHERE CAST(m.brojClanskeKarte AS string) LIKE CONCAT(:brojClanskeKarte, '%')")
	List<Member> findByBrojClanskeKarteLike(@Param("brojClanskeKarte") String brojClanskeKarte);
}
