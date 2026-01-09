package com.example.backend.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iso_code", nullable = false)
    String isoCode;

    String name;

    double rate;

    @Column(name = "recorded_at", nullable = false)
    LocalDate recordedAt;

    public Currency() {

    }

    public Currency(String isoCode, String name, double rate, LocalDate recordedAt) {
        this.isoCode = isoCode;
        this.name = name;
        this.rate = rate;
        this.recordedAt = recordedAt;
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

    public LocalDate getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDate recordedAt) {
        this.recordedAt = recordedAt;
    }
}
