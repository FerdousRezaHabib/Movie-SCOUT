package application;

public class Movie {
    private final int id;
    private final String title;
    private final String genre;
    private final int year;
    private final String duration;
    private final double rating;
    private final String plot;
    private final String posterUrl;
    private final String cast;

    public Movie(int id, String title, String genre, int year, String duration, 
                double rating, String plot, String posterUrl, String cast) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.duration = duration;
        this.rating = rating;
        this.plot = plot;
        this.posterUrl = posterUrl;
        this.cast = cast;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public String getDuration() { return duration; }
    public double getRating() { return rating; }
    public String getPlot() { return plot; }
    public String getPosterUrl() { return posterUrl; }
    public String getCast() { return cast; }
}