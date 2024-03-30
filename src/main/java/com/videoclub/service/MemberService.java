package com.videoclub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;
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
	@Autowired private EntityManager entityManager;
	
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


	public List<Member> findByCriteria(String ime, String prezime, String brojTelefona, Integer brojClanskeKarte) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> member = cq.from(Member.class);

		List<Predicate> predicates = new ArrayList<>();

		if (ime != null && !ime.isEmpty()) {
			predicates.add(cb.like(member.get("ime"), ime + "%"));
		}
		if (prezime != null && !prezime.isEmpty()) {
			predicates.add(cb.like(member.get("prezime"), prezime + "%"));
		}
		if (brojTelefona != null && !brojTelefona.isEmpty()) {
			predicates.add(cb.like(member.get("brojTelefona"), brojTelefona + "%"));
		}
		if (brojClanskeKarte != null) {
			predicates.add(cb.equal(member.get("brojClanskeKarte"), brojClanskeKarte));
		}

		cq.select(member).where(cb.and(predicates.toArray(new Predicate[0])));

		return entityManager.createQuery(cq).getResultList();
	}

	
//	public List<Member> findByPrezime(String prezime){
//		CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
//		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
//		Root<Member> member = cq.from(Member.class);
//		cq.select(member).where(cb.like(member.get("prezime"), prezime  + "%"));
//		return entitiyManager.createQuery(cq).getResultList();
//	}
//
//	public List<Member> findByBrojTelefona(String brojTelefona){
//		CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
//		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
//		Root<Member> member = cq.from(Member.class);
//		cq.select(member).where(cb.like(member.get("brojTelefona"), brojTelefona  + "%"));
//		return entitiyManager.createQuery(cq).getResultList();
//	}
//
//	public List<Member> findByBrojClanskeKarte(Integer brojClanskeKarte){
//	    String brojClanskeKarteString = String.valueOf(brojClanskeKarte);
//	    return repo.findByBrojClanskeKarteLike(brojClanskeKarteString);
//	}
}
