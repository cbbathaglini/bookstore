package com.bookstore.bookstore.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private int year;

    @NotBlank @Size(max = 400)
    private String synopsis;

    public Book() {

    }

    public Book(@NotBlank String title, int year, @NotBlank @Size(max = 400) String synopsis) {
        this.title = title;
        this.year = year;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }
}

