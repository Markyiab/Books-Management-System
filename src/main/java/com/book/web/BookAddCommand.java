package com.book.web;

import java.math.BigDecimal;
import java.util.Date;

public class BookAddCommand {

    private long bookId;

    private String name;
    private String author;
    private String publish;
    private String isbn;
    private String introduction;
    private String language;
    private BigDecimal price;
    private Date pubdate;
    private int classId;
    private int pressmark;
    private int state;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(final long bookId) {
        this.bookId = bookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPubdate(final Date pubdate) {
        this.pubdate = pubdate;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPressmark(int pressmark) {
        this.pressmark = pressmark;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getClassId() {
        return classId;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public String getAuthor() {
        return author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getPressmark() {
        return pressmark;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }

    public int getState() {
        return state;
    }

    public String getPublish() {
        return publish;
    }

}
