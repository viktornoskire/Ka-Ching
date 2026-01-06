package com.example.backend.repository;

import com.example.backend.entity.Currency;
import org.springframework.data.repository.ListCrudRepository;

public interface CurrencyRepository extends ListCrudRepository<Currency, String> {
}
