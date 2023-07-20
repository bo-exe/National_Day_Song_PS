package sg.edu.rp.c346.id22020749.l08ps;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Song implements Serializable {

    private int id, year, stars;
    private String title, singers;

    public Song(int id, String title, String singers, int year, int stars) {
        this.id = id;
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSingers() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
    }

    public void setTitle(String title) { this.title = title; }
    public void setSingers(String singers) { this.singers = singers; }
    public void setYear(int year) { this.year = year; }
    public void setStars(int stars) { this.stars = stars; }

    @NonNull
    @Override
    public String toString() {
        return id + ".\n"
                + "Title: " + title + "\n"
                + "Singers: " + singers + "\n"
                + "Year Released: " + year + "\n"
                + "Rating: " + stars + " stars";
    }

    public String getStarRating(int stars) {
        String starRating = "";

        for (int i = 0; i < stars; i++) {
            starRating += "* ";
        }

        return starRating;
    }

}