import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Player {
    private Region region;
    private int playerId;
    private String name;
    private UserType userType;
    private Race race;
    private boolean hasAdditionalProfile;

    // from sc2logs api. only ladder player available
    private String battleTag;
    private int profileId;
    private int playedCount;
    private int rating;
    private int leagueId;
    private int ladderId;
    private int points;
    private int wins;
    private int losses;
    private int ties;
    private int longestWinStreak;
    private int currentWinStreak;
    private int currentRank;
    private int highestRank;
    private int previousRank;
    private int clanId;
    private String clanTag;
    private String clanName;
    private String clanIconURL;
    private String clanDecalURL;
    private long lastPlayedAt;
    private long createdAt;

    Player(Region region, int playerId, String name, UserType type, Race race) {
        this.region = region;
        this.playerId = playerId;
        this.name = name;
        this.userType = type;
        this.race = race;
        this.hasAdditionalProfile = false;
    }

    void getAdditionalProfile(int rating) {
        if (!this.userType.equals(UserType.User)) {
            System.out.println("Can not to get additional profile for CPU. just skipped. PlayerName: " + this.name);
            return;
        }

        try {
            // fetch profile by sc2logs api if user
            JsonArray profileJsonArray = Util.getJsonByURL(String.format(Constant.BASE_URL_OF_SC2LOGS_API_SEARCH, this.region.name(), name, this.race.name(), rating)).getAsJsonArray();
            if (profileJsonArray.size() <= 0) return;

            // regard first element as correct one. this depends on how is API implemented
            JsonObject profileJson = profileJsonArray.get(0).getAsJsonObject();
            this.profileId = profileJson.get("id").getAsInt();
            this.battleTag = profileJson.get("battle_tag").getAsString();
            this.playedCount = profileJson.get("played_count").getAsInt();
            this.leagueId = profileJson.get("league_id").getAsInt();
            this.ladderId = profileJson.get("ladder_id").getAsInt();
            this.rating = profileJson.get("rating").getAsInt();
            this.points = profileJson.get("points").getAsInt();
            this.wins = profileJson.get("wins").getAsInt();
            this.losses = profileJson.get("losses").getAsInt();
            this.ties = profileJson.get("ties").getAsInt();
            this.longestWinStreak = profileJson.get("longest_win_streak").getAsInt();
            this.currentWinStreak = profileJson.get("current_win_streak").getAsInt();
            this.currentRank = profileJson.get("current_rank").getAsInt();
            this.highestRank = profileJson.get("highest_rank").getAsInt();
            this.previousRank = profileJson.get("previous_rank").getAsInt();
            this.clanId = profileJson.get("clan_id").getAsInt();

            if (!profileJson.get("clan_tag").isJsonNull()) {
                this.clanTag = profileJson.get("clan_tag").getAsString();
                this.clanName = profileJson.get("clan_name").getAsString();

                if (!profileJson.get("clan_icon_url").isJsonNull()) {
                    this.clanIconURL = profileJson.get("clan_icon_url").getAsString();
                }

                if (!profileJson.get("clan_decal_url").isJsonNull()) {
                    this.clanDecalURL = profileJson.get("clan_decal_url").getAsString();
                }
            }

            this.lastPlayedAt = profileJson.get("last_played_at").getAsLong();
            this.createdAt = profileJson.get("created_at").getAsLong();

            this.hasAdditionalProfile = true;
        } catch (Exception e) {
            System.err.println("error occured on detail profile process.");
            e.printStackTrace();
        }
    }

    boolean isUser() {
        return this.userType.equals(UserType.User);
    }

    public String getName() {
        return this.name;
    }

    int calcWinRate() {
        double rate = ((double)this.wins/(this.wins + this.losses)) * 100.0;
        return (int)rate;
    }

    long calcHoursBeforeInfo() {
        return Util.calcHoursBefore(this.createdAt);
    }

    boolean hasClanTag() {
        return this.clanTag != null;
    }

    boolean hasClanIcon() {
        return this.clanIconURL != null;
    }

    boolean hasClanDecal() {
        return this.clanDecalURL != null;
    }

    String getClanIconURL() {
        return this.clanIconURL;
    }

    String getClanDecalURL() {
        return this.clanDecalURL;
    }

    Race getRace() {
        return this.race;
    }

    Region getRegion() {
        return this.region;
    }

    int getProfileId() {
        return this.profileId;
    }

    UserType getUserType() {
        return this.userType;
    }

    int getRating() {
        return this.rating;
    }

    int getPlayerId() {
        return this.playerId;
    }

    Leagues getLeague() {
        if (!this.hasAdditionalProfile) return null;
        return Leagues.getLeagueById(this.leagueId);
    }

    boolean hasAdditionalProfile() {
        return this.hasAdditionalProfile;
    }

    // ---------------- methods for output text ---------------

    // with ClanTag
    public String makeNameText() {
        if (!this.hasClanTag()) return this.name;
        return String.format("<%s>", this.clanTag) + this.name;
    }

    public String makeRatingText() {
        if (this.rating == 0) return Constant.DEFAULT_UNKNOWN_MARK;
        return String.valueOf(this.rating);
    }

    public String makeWinRateText() {
        if (!this.hasAdditionalProfile) return Constant.DEFAULT_UNKNOWN_MARK;
        int winRate = this.calcWinRate();
        return String.valueOf(winRate);
    }

    String makeClanNameText() {
        if (!this.hasAdditionalProfile) return Constant.DEFAULT_UNKNOWN_MARK;
        return this.clanName == null ? Constant.DEFAULT_CLAN_NAME : this.clanName;
    }

    String makePlayedCountText() {
        if (!this.hasAdditionalProfile) return Constant.DEFAULT_UNKNOWN_MARK;
        return String.valueOf(this.playedCount);
    }

    String makeHoursBeforeInfoText() {
        if (!this.hasAdditionalProfile) return Constant.DEFAULT_UNKNOWN_MARK;
        long hoursBefore = this.calcHoursBeforeInfo();

        if (hoursBefore == 0) return Constant.RECENT_PLAYER_INFO_TEXT;
        if (hoursBefore == 1) return Constant.ONE_HOUR_BEFORE_PLAYER_INFO_TEXT;
        return String.valueOf(this.calcHoursBeforeInfo()) + Constant.PREFIX_OF_HOURS_BEFORE_PLAYER_INFO_TEXT;
    }

    // --------------------------------------------------------
    public void makeTextAndMediaFiles(String rootPath) {
        // ----------------- text files -----------------
        String textDirPath = rootPath + Constant.PATH_OF_PLAYER_INFO_TEXT_DIR;
        Util.writeText(textDirPath + "name.txt", this.makeNameText(), true);
        Util.writeText(textDirPath + "rating.txt", this.makeRatingText(), true);
        Util.writeText(textDirPath + "winRate.txt", this.makeWinRateText(), true);
        Util.writeText(textDirPath + "clanName.txt", this.makeClanNameText(), true);
        Util.writeText(textDirPath + "playCount.txt", this.makePlayedCountText(), true);
        Util.writeText(textDirPath + "hoursBeforeInfo.txt", this.makeHoursBeforeInfoText(), true);

        // ----------------- image files -----------------
        String imageDirPath = rootPath + Constant.PATH_OF_PLAYER_INFO_IMAGE_DIR;
        Util.copyImage(this.getRace().getIconPath(), imageDirPath + "raceIcon.png");

        String leagueIconImagePath = imageDirPath + "leagueIcon.png";
        if (this.hasAdditionalProfile) Util.copyImage(this.getLeague().getIconPath(), leagueIconImagePath);
        else Util.copyImage(Constant.PATH_OF_UNKNOWN_IMAGE, leagueIconImagePath);

        String clanIconPath = imageDirPath + "clanIcon.png";
        if (this.hasClanIcon()) Util.downloadImage(this.getClanIconURL(), clanIconPath);
        else Util.copyImage(Constant.PATH_OF_DUMMY_IMAGE, clanIconPath);

        String clanDecalPath = imageDirPath + "clanDecal.png";
        if (this.hasClanDecal()) Util.downloadImage(this.getClanDecalURL(), clanDecalPath);
        else Util.copyImage(Constant.PATH_OF_DUMMY_IMAGE, clanDecalPath);

        System.out.println("finished");
    }
}
