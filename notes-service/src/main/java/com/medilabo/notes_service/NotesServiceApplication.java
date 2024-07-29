package com.medilabo.notes_service;

import com.medilabo.notes_service.model.Note;
import com.medilabo.notes_service.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class NotesServiceApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(NotesServiceApplication.class);

	@Autowired
	NoteRepository noteRepository;

	public static void main(String[] args) {
		SpringApplication.run(NotesServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Note> notes = noteRepository.findAll();
		notes.forEach(note -> logger.info(note.toString()));
	}
}
