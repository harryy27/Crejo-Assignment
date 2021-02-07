package beans;

import enums.UserRank;

import java.util.HashMap;
import java.util.Map;

public class UserBean {

    private UserRank rank;

    private final Map<String, Float> review;

    public Map<String, Float> getReview() {
        return review;
    }

    public void setReview(String movieName, float rating) {
        this.review.put(movieName, rating);

        if(review.size() == 3)
            this.rank = UserRank.CRITIC;

        if(review.size() == 10)
            this.rank = UserRank.EXPERT;

        if(review.size() == 20)
            this.rank = UserRank.ADMIN;
    }

    public UserBean() {
        this.rank = UserRank.VIEWER;
        this.review = new HashMap<>();
    }

    public UserRank getRank() {
        return rank;
    }

}
