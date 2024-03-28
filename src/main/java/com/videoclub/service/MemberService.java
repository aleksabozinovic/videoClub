package com.videoclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.videoclub.member.Member;
import com.videoclub.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
@Configurable
public class MemberService {
	
	@Autowired private MemberRepository repo;
	@Autowired private EntityManager entitiyManager;
	
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
	
		CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> member = cq.from(Member.class);
		cq.select(member).where(cb.like(member.get("ime"), ime  + "%"));
		return entitiyManager.createQuery(cq).getResultList();
		
	}	
	
	public List<Member> findByPrezime(String prezime){
		CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> member = cq.from(Member.class);
		cq.select(member).where(cb.like(member.get("prezime"), prezime  + "%"));
		return entitiyManager.createQuery(cq).getResultList();
	}
	
	public List<Member> findByBrojTelefona(String brojTelefona){
		CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> member = cq.from(Member.class);
		cq.select(member).where(cb.like(member.get("brojTelefona"), brojTelefona  + "%"));
		return entitiyManager.createQuery(cq).getResultList();
	}	
	
	public List<Member> findByBrojClanskeKarte(Integer brojClanskeKarte){
	    String brojClanskeKarteString = String.valueOf(brojClanskeKarte);
	    return repo.findByBrojClanskeKarteLike(brojClanskeKarteString);
	}
}
