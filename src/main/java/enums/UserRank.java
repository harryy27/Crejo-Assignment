package enums;

public enum UserRank {
    VIEWER("Viewer"),
    CRITIC("Critic"),
    EXPERT("Expert"),
    ADMIN("Admin");

    private final String rank;

    UserRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return this.rank;
    }
}
