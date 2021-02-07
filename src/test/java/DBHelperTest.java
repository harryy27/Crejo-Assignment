import enums.ReviewError;
import enums.UserRank;
import exceptions.InvalidReviewException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class DBHelperTest {

    private static DBHelper dbHelper;

    @BeforeClass
    public static void setup() {
        dbHelper = new DBHelper();

        dbHelper.addMovie("Don", 2006, new HashSet<>(Arrays.asList("Action", "Comedy")));
        dbHelper.addMovie("Tiger", 2008, new HashSet<>(Collections.singletonList("Drama")));
        dbHelper.addMovie("Padmaavat", 2006, new HashSet<>(Collections.singletonList("Comedy")));
        dbHelper.addMovie("Lunchbox", 2022, new HashSet<>(Collections.singletonList("Drama")));
        dbHelper.addMovie("Guru", 2006, new HashSet<>(Collections.singletonList("Drama")));
        dbHelper.addMovie("Metro", 2006, new HashSet<>(Collections.singletonList("Romance")));

        dbHelper.addUser("SRK");
        dbHelper.addUser("Salman");
        dbHelper.addUser("Deepika");

    }

    @Test
    public void test1() {
        //All the successful insertions are covered in this test
        Assert.assertTrue(dbHelper.addReview("SRK", "Don", 2));
        Assert.assertTrue(dbHelper.addReview("SRK", "Padmaavat", 8));
        Assert.assertTrue(dbHelper.addReview("Salman", "Don", 5));
        Assert.assertTrue(dbHelper.addReview("Deepika", "Don", 9));
        Assert.assertTrue(dbHelper.addReview("Deepika", "Guru", 6));
    }

    @Test
    public void test2() {
        //Multiple reviews of a movie by the same user is not allowed
        try {
            dbHelper.addReview("SRK","Don", 10);
        } catch (InvalidReviewException e) {
            Assert.assertEquals(ReviewError.REVIEW_ALREADY_EXISTS, e.getCode());
        }
    }

    @Test
    public void test3() {
        //Review of unreleased movie is not allowed
        try {
            dbHelper.addReview("Deepika", "Lunchbox", 5);
        } catch (InvalidReviewException e) {
            Assert.assertEquals(ReviewError.MOVIE_NOT_RELEASED, e.getCode());
        }
    }

    @Test
    public void test4() {
        //SRK should be promoted to Critic from Viewer because it's his 3rd review

        Assert.assertEquals(UserRank.VIEWER, dbHelper.getUser("SRK").getRank());
        dbHelper.addReview("SRK", "Tiger", 5);
        Assert.assertEquals(UserRank.CRITIC, dbHelper.getUser("SRK").getRank());
    }

    @Test
    public void test5() {
        //Since SRK is a critic now, his rating should be counted 2X

        double initialRating = dbHelper.getMovie("Metro").getTotalRatings();
        dbHelper.addReview("SRK", "Metro", 7);
        double newRating = dbHelper.getMovie("Metro").getTotalRatings();
        Assert.assertEquals(14, newRating - initialRating, 0);
    }

    @Test
    public void test6() {
        //Additional tests
        Assert.assertEquals("Tiger", dbHelper.getTopCriticsMoviesInGenre(1, "Drama").get(0).getMovieName());
        Assert.assertEquals(8.33, dbHelper.getAverageReviewForYear(2006), 0.1);
        Assert.assertEquals(5.33, dbHelper.getAverageReviewForMovie("Don"), 0.1);
    }
}
