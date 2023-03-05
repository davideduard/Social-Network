package com.example.socialnetworkui.domain;

import kotlin.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Request extends Entity<Pair<String, String>> {
    private LocalDate sentDate;
    private int status;
    public Request(Pair<String, String> stringStringPair, LocalDate sentDate, int status) {
        super(stringStringPair);
        this.sentDate = sentDate;
        this.status = status;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return status == request.status && Objects.equals(sentDate, request.sentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentDate, status);
    }
}
