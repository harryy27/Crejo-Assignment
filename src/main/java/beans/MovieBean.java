package beans;

import enums.UserRank;

import java.util.Set;

public class MovieBean {

    private final String movieName;

    private final int releaseYear;

    private final Set<String> genre;

    private double totalCriticsRating;

    private long numberOfRatings;

    private double totalRatings;

    public MovieBean(String movieName, int releaseYear, Set<String> genre) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Set<String> getGenre() {
        return genre;
    }

    public double getTotalCriticsRating() {
        return totalCriticsRating;
    }

    public long getNumberOfRatings() {
        return numberOfRatings;
    }

    public double getTotalRatings() {
        return totalRatings;
    }

    public void addRating(UserRank rank, double rating) {
        if(UserRank.CRITIC.equals(rank))
            this.totalCriticsRating += rating;

        this.numberOfRatings++;
        this.totalRatings += rating;
    }
}
