package com.videoclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.videoclub.member.Member;
import com.videoclub.repository.MemberRepository;

@Service
@Configurable
public class MemberService {
	@Autowired private MemberRepository repo;
	
	public List<Member> listOfAllMembers(){
		return (List<Member>) repo.findAll();
		
	}
	
	public void save(Member member) {
		repo.save(member);
	}
	public Member editMember(Integer brojClanskeKarte) {
		Optional<Member> result= repo.findById(brojClanskeKarte);
		return result.get();	
	}
	
	
	
	public void deleteMember(Integer brojClanskeKarte) {
		repo.deleteById(brojClanskeKarte);
	}
	
	
	public List<Member> findByIme(String ime){
		return repo.findByIme(ime);
	}	
	
	public List<Member> findByPrezime(String prezime){
		return repo.findByPrezime(prezime);
	}
	
	public List<Member> findByBrojTelefona(String brojTelefona){
		return repo.findByBrojTelefona(brojTelefona);
	}	
	
	public List<Member> findByBrojClanskeKarte(Integer brojClanskeKarte){
		return repo.findByBrojClanskeKarte(brojClanskeKarte);
	}
}
