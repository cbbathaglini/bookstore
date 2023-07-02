package com.bookstore.bookstore.dto;

import com.bookstore.bookstore.model.Book;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class BookCreateRequestDTO {

    @NotBlank
    private String title;
    private int year;

    @NotBlank @Size(max = 400)
    private String synopsis;

    public BookCreateRequestDTO() {
    }

    public BookCreateRequestDTO(String title, int year, String synopsis) {
        this.title = title;
        this.year = year;
        this.synopsis = synopsis;
    }

    public Book convert() {
        return new Book(this.title, this.year, this.synopsis);
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
        return "BookCreateRequestDTO{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }
}
