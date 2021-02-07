import beans.MovieBean;
import beans.UserBean;
import enums.ReviewError;
import enums.UserRank;
import exceptions.InvalidReviewException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class DBHelper {

    private final Map<String, UserBean> userBeanMap;
    private final Map<String, MovieBean> movieBeanMap;

    public DBHelper() {
        this.userBeanMap = new HashMap<>();
        this.movieBeanMap = new HashMap<>();
    }

    public void addUser(String userName) {
        userBeanMap.put(userName, new UserBean());
    }

    public UserBean getUser(String userName) {
        return userBeanMap.get(userName);
    }

    public void addMovie(String movieName, int releaseYear, Set<String> genre) {
        movieBeanMap.put(movieName, new MovieBean(movieName, releaseYear, genre));
    }

    public MovieBean getMovie(String movieName) {
        return movieBeanMap.get(movieName);
    }

    public boolean addReview(String userName, String movieName, float rating) {
        if(movieBeanMap.get(movieName).getReleaseYear() > LocalDateTime.now().getYear())
            throw new InvalidReviewException(ReviewError.MOVIE_NOT_RELEASED, ReviewError.MOVIE_NOT_RELEASED.getMessage());

        if(userBeanMap.get(userName).getReview().containsKey(movieName))
            throw new InvalidReviewException(ReviewError.REVIEW_ALREADY_EXISTS, ReviewError.REVIEW_ALREADY_EXISTS.getMessage());

        userBeanMap.get(userName).setReview(movieName, rating);

        if(UserRank.CRITIC.equals(userBeanMap.get(userName).getRank()))
            rating *= 2;

        movieBeanMap.get(movieName).addRating(userBeanMap.get(userName).getRank(), rating);

        return true;
    }

    public List<MovieBean> getTopCriticsMoviesInGenre(int n, String genre) {
        return movieBeanMap.values().stream().filter(movie -> movie.getGenre().contains(genre)).sorted(
                Comparator.comparingDouble(MovieBean::getTotalCriticsRating).reversed()).collect(Collectors.toList()).subList(0, n);
    }

    public double getAverageReviewForYear(int year) {
        AtomicReference<Double> totalRating = new AtomicReference<>((double) 0);
        AtomicInteger ratingCount = new AtomicInteger();
        movieBeanMap.values().stream().filter(movie -> year == movie.getReleaseYear()).map(
                movie -> {
                totalRating.updateAndGet(v -> (v + movie.getTotalRatings() / movie.getNumberOfRatings()));
                ratingCount.getAndIncrement();
                    return null;
                }).collect(Collectors.toList());

        return totalRating.get()/ratingCount.get();
    }

    public double getAverageReviewForMovie(String movieName) {
        return movieBeanMap.get(movieName).getTotalRatings()/movieBeanMap.get(movieName).getNumberOfRatings();
    }

}
