public class MatchLog {
    private long unixtime;
    private int rating;

    MatchLog(long unixtime, int rating) {
        this.unixtime = unixtime;
        this.rating = rating;
    }

    int getRating() {
        return this.rating;
    }
}
