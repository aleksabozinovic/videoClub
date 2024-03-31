package com.videoclub.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {

	@Column(nullable = false, name = "title")
	private String title;
	
	@Column(name = "genre")
	private String genre;

	@Column(name= "mpa_rating")
	private Integer mpaRating;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_number")
	private Integer inventoryNumber;
	
	@Column(name= "description")
	private String description;
	
	@Column(name= "language")
	private String language;
	
	@Column(name= "year_of_recording")
	private String yearOfRecording;
	
	@Column(name= "date_of_entry")
	private String dateOfEntry;
	
	
	public Movie(String title, String genre, Integer mpaRating, Integer inventoryNumber, String description, String language,
			String yearOfRecording, String dateOfEntry) {
		super();
		this.title = title;
		this.genre = genre;
		this.mpaRating = mpaRating;
		this.inventoryNumber = inventoryNumber;
		this.description = description;
		this.language = language;
		this.yearOfRecording = yearOfRecording;
		this.dateOfEntry = dateOfEntry;
	}


	public Movie(String title, String genre, Integer mpaRating, String description, String language, String yearOfRecording,
                 String dateOfEntry) {
		super();
		this.title = title;
		this.genre = genre;

        this.mpaRating = mpaRating;
		this.description = description;
		this.language = language;
		this.yearOfRecording = yearOfRecording;
		this.dateOfEntry = dateOfEntry;
	}


	public Movie() {
		// TODO Auto-generated constructor stub
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public Integer getMpaRating() {
		return mpaRating;
	}


	public void setMpaRating(Integer mpaRating) {
		this.mpaRating = mpaRating;
	}


	public Integer getInventoryNumber() {
		return inventoryNumber;
	}


	public void setInventoryNumber(Integer inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getYearOfRecording() {
		return yearOfRecording;
	}


	public void setYearOfRecording(String yearOfRecording) {
		this.yearOfRecording = yearOfRecording;
	}


	public String getDateOfEntry() {
		return dateOfEntry;
	}


	public void setDateOfEntry(String dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}




}
