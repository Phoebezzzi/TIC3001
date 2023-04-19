package com.example.service.imp;

import com.example.entity.Quote;
import com.example.service.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QuoteRepositoryImp implements QuoteRepository {

    private List<Quote> quotes = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong(1);//to generate unique IDs for quotes

    @Override
    public List<Quote> findAll() {
        return quotes;
    }

    @Override
    public Quote findById(long id) {
        Optional<Quote> quote = quotes.stream().filter(q -> q.getId().equals(id)).findFirst();
        return quote.orElse(null);
    }

    @Override
    public Quote save(Quote quote) {
        if (quote.getId() == null) {
            quote.setId(nextId.getAndIncrement());
            quotes.add(quote);
        } else {
            int index = quotes.indexOf(quote);
            if (index != -1) {
                quotes.set(index, quote);
            } else {
                quotes.add(quote);
            }
        }
        return quote;
    }

    @Override
    public void deleteById(Long id) {
        quotes.removeIf(quote -> quote.getId().equals(id));
    }
}
