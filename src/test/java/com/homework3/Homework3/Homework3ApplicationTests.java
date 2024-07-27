package com.homework3.Homework3;

import com.homework3.Homework3.entities.BookEntity;
import com.homework3.Homework3.repositories.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class Homework3ApplicationTests {
	@Autowired
	private BookRepo bookRepo;


    @Test
	void contextLoads() {
	}

	@Test
	void getTitle(){
		List<BookEntity> entity = bookRepo.findByTitle("The Kite Runner");
		System.out.println(entity);
	}

}
