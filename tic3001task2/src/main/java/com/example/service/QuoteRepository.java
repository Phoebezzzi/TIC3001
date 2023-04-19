package com.example.service;

import com.example.entity.Quote;

import java.util.List;

public interface QuoteRepository {
    List<Quote> findAll();

    Quote findById(long id);

    Quote save(Quote quote);

    void deleteById(Long id);
}
