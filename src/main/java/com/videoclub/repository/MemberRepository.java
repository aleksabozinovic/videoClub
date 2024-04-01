package com.videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.videoclub.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	List<Member> findByName(String name);
	List<Member> findByLastName(String lastName);
	List<Member> findByMemberCardNumber(Integer memberCardNumber);
	List<Member> findByMemberCardNumberAndNameAndLastName(Integer memberCardNumber, String name, String lastName);

}
