public enum League {
    BRONZE      (0, "./image/league/bronze.png"),
    SILVER      (1, "./image/league/silver.png"),
    GOLD        (2, "./image/league/gold.png"),
    PLATINUM    (3, "./image/league/platinum.png"),
    DIAMOND     (4, "./image/league/diamond.png"),
    MASTER      (5, "./image/league/master.png"),
    GRANDMASTER (6, "./image/league/grandmaster.png");

    private int id;
    private String iconPath;
    League(int id, String iconPath) {
        this.id = id;
        this.iconPath = iconPath;
    }

    int getId() {
        return this.id;
    }

    String getIconPath() {
        return this.iconPath;
    }

    static League getLeagueById(int id) {
        for (League league : League.values()) {
            if (league.getId() == id) return league;
        }
        return null;
    }
}


//            0, // BRONZE
//                    1, // SILVER
//                    2, // GOLD
//                    3, // PLATINUM
//                    4, // DIAMOND
//                    5, // MASTER
//                    6  // GRANDMASTER
