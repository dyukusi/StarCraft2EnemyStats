public class League {
    private int regionId;
    private int seasonId;
    private int queueId;
    private Leagues league;
    private int tier;
    private int minRating;
    private int maxRating;

    public League(int regionId, int seasonId, int queueId, int leagueId, int tier, int minRating, int maxRating) {
        this.regionId = regionId;
        this.seasonId = seasonId;
        this.queueId = queueId;
        this.league = Leagues.getLeagueById(leagueId);
        this.tier = tier;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public int getMinRating() {
        return this.minRating;
    }

    public Leagues getLeague() {
        return this.league;
    }

    public int getTier() {
        return this.tier;
    }
}
