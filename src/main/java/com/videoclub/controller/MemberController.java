package com.videoclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.member.Member;
import com.videoclub.service.MemberService;


@Controller
public class MemberController {
	
	@Autowired private MemberService service;
	
	@GetMapping("/member")
	public String showMembers(Model model) {
		List<Member> memberList = service.listOfAllMembers();
		model.addAttribute("memberList" ,memberList);
		return "member";
	}
	
	@PostMapping("/member")
	public String saveNewMember(Member member) {
		service.save(member);
		return "redirect:/member";
	}
	
	@GetMapping("/member/addMember")
	public String addNewMember(Model model) {
		model.addAttribute("member" , new Member());
		return "addMember";
	}
	
	@GetMapping("/member/edit/{memberCardNumber}")
	public String editMember(@PathVariable Integer memberCardNumber, Model model) {
		Member member = service.editMember(memberCardNumber);
		model.addAttribute("member" , member);
		return "addMember";
	}
	
	@GetMapping("/member/delete/{memberCardNumber}")
	public String deleteMember(@PathVariable Integer memberCardNumber, Model model) {
		service.deleteMember(memberCardNumber);
		return "redirect:/member";
	}
	
	@GetMapping("/filteredMembers")
	public String filteredMembers(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "memberCardNumber", required = false) Integer memberCardNumber,
			Model model) {

		List<Member> filteredMembers = service.findByCriteria(name, lastName, phoneNumber, memberCardNumber);
		model.addAttribute("memberList", filteredMembers);
		return "member";
		
	}

}
