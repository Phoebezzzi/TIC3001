package com.example.controller;


import com.example.entity.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.QuoteRepository;

import java.util.List;
import java.util.Optional;

@RestController
//The @RequestMapping annotation is used to specify the base URL for the API ("/quotes").
@RequestMapping("/quotes")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;
//    The @Autowired annotation is used to inject an instance of the QuoteRepository interface, which is used to interact with the database.

    @GetMapping
//    The @GetMapping annotation is used to handle GET requests to retrieve all quotes or a single quote by its ID.
    public List<Quote> getAllQuotes() {

        return (List<Quote>) quoteRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable("id") long id) {
        Optional<Quote> quote = Optional.ofNullable(quoteRepository.findById(id));
        if (quote.isPresent()) {
            return ResponseEntity.ok(quote.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
//    The @PostMapping annotation is used to handle POST requests to create a new quote.
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
        if (quote.getAuthor() == null || quote.getText() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            Quote savedQuote = quoteRepository.save(quote);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuote);
        }
    }

    @PutMapping("/{id}")
//    The @PutMapping annotation is used to handle PUT requests to update an existing quote.
    public ResponseEntity<Quote> updateQuote(@PathVariable("id") long id, @RequestBody Quote quote) {
        Optional<Quote> existingQuote = Optional.ofNullable(quoteRepository.findById(id));
        if (existingQuote.isPresent()) {
            if (quote.getAuthor() == null || quote.getText() == null) {
                return ResponseEntity.badRequest().build();
            } else {
                Quote updatedQuote = existingQuote.get();
                updatedQuote.setAuthor(quote.getAuthor());
                updatedQuote.setText(quote.getText());
                quoteRepository.save(updatedQuote);
                return ResponseEntity.ok(updatedQuote);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
//    The @DeleteMapping annotation is used to handle DELETE requests to delete a quote by its ID.
    public ResponseEntity<?> deleteQuote(@PathVariable("id") long id) {
        Optional<Quote> quote = Optional.ofNullable(quoteRepository.findById(id));
        if (quote.isPresent()) {
            quoteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

