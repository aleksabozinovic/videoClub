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
	public Member editMember(Integer memberCardNumber) {
		Optional<Member> result= repo.findById(memberCardNumber);
		return result.get();	
	}
	
	
	
	public void deleteMember(Integer memberCardNumber) {
		repo.deleteById(memberCardNumber);
	}


	public List<Member> findByCriteria(String name, String lastName, String phoneNumber, Integer memberCardNumber) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> member = cq.from(Member.class);

		List<Predicate> predicates = new ArrayList<>();

		if (name != null && !name.isEmpty()) {
			predicates.add(cb.like(member.get("name"), name + "%"));
		}
		if (lastName != null && !lastName.isEmpty()) {
			predicates.add(cb.like(member.get("lastName"), lastName + "%"));
		}
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			predicates.add(cb.like(member.get("phoneNumber"), phoneNumber + "%"));
		}
		if (memberCardNumber != null) {
			predicates.add(cb.equal(member.get("memberCardNumber"), memberCardNumber));
		}

		cq.select(member).where(cb.and(predicates.toArray(new Predicate[0])));

		return entityManager.createQuery(cq).getResultList();
	}

}
