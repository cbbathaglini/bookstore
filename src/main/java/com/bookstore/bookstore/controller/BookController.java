package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.BookCreateRequestDTO;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private BookRepository bookRepository;


    @Transactional
    @PostMapping("/create")
    public ResponseEntity createBook(@RequestBody BookCreateRequestDTO bookCreateDTO){
        Book book = bookCreateDTO.convert();
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/listAll")
    public ResponseEntity listAll(){
        List<Book> allBooksList = bookRepository.findAll();
        return ResponseEntity.ok().body(allBooksList);
    }


    @GetMapping("/getBook/{id}")
    public ResponseEntity getBook(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(book);
    }

}
