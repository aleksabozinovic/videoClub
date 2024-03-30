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
	
	@GetMapping("/member/edit/{brojClanskeKarte}")
	public String editMember(@PathVariable Integer brojClanskeKarte, Model model) {
		Member member = service.editMember(brojClanskeKarte);
		model.addAttribute("member" , member);
		return "addMember";
	}
	
	@RequestMapping("/member/delete/{brojClanskeKarte}")
	public String deleteMember(@PathVariable Integer brojClanskeKarte, Model model) {
		service.deleteMember(brojClanskeKarte);
		return "redirect:/member";
	}
	
	@GetMapping("/filtriraniMemberi")
	public String filtriranjeMembera(
			@RequestParam(value = "ime", required = false) String ime,
			@RequestParam(value = "prezime", required = false) String prezime,
			@RequestParam(value = "brojTelefona", required = false) String brojTelefona,
				@RequestParam(value = "brojClanskeKarte", required = false) Integer brojClanskeKarte,
			Model model) {

		List<Member> filteredMembers = service.findByCriteria(ime, prezime, brojTelefona, brojClanskeKarte);
		model.addAttribute("memberList", filteredMembers);
		return "member";
		
	}

}
