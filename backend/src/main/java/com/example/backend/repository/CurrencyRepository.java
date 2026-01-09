package com.example.backend.repository;

import com.example.backend.entity.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CurrencyRepository extends ListCrudRepository<Currency, String> {

    @Query("""
    select c from Currency c where c.isoCode = :isoCode order by c.id limit 1
    """)
    Currency getCurrencyByIsoCode(String isoCode);

}
