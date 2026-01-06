package com.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;

@Entity
public class Currency {

    @Id
    String isoCode;

    @NonNull
    String name;

    double rate;

    @NonNull
    LocalDate updatedAt;

    public Currency() {

    }

    public Currency(String isoCode, String name, double rate, LocalDate updatedAt) {
        this.isoCode = isoCode;
        this.name = name;
        this.rate = rate;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
