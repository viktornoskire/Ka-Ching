package com.example.backend.repository;

import com.example.backend.entity.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CurrencyRepository extends ListCrudRepository<Currency, String> {

    @Query("""
    select c from Currency c where c.isoCode = :isoCode order by c.id desc limit 1
    """)
    Currency getCurrencyByIsoCode(String isoCode);

    @Query("""
        select c
        from Currency c
        where c.recordedAt = (
            select max(c2.recordedAt)
            from Currency c2
            where c2.isoCode = c.isoCode
        )
        order by c.isoCode
    """)
    List<Currency> getAllLatestCurrencies();}
