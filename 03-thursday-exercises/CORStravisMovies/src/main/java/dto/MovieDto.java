/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Movie;

/**
 *
 * @author Rasmus2
 */
public class MovieDto {

    private String name;
    private String director;
    private String genre;
    private int year;
    private double length;
    private Long productionPrice;

    public MovieDto(Movie movie) {
        name = movie.getName();
        director = movie.getDirector();
        genre = movie.getGenre();
        year = movie.getYear();
        length = movie.getLength();
        productionPrice = movie.getProductionPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Long getProductionPrice() {
        return productionPrice;
    }

    public void setProductionPrice(Long productionPrice) {
        this.productionPrice = productionPrice;
    }

}
