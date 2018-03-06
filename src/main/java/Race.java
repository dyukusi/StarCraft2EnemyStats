public enum Race {
    Terran (1, "Terr",   "./image/race/terran.png"),
    Zerg   (2, "Zerg",   "./image/race/zerg.png"),
    Protoss(3, "Prot",   "./image/race/protoss.png"),
    Random (4, "random", "./image/race/random.png"),
    Null   (99, "", "");

    private int id;
    private String apiRaceStr;
    private String iconPath;
    Race(int id, String apiRaceStr, String iconPath) {
        this.id = id;
        this.apiRaceStr = apiRaceStr;
        this.iconPath = iconPath;
    }

    int getId() {
        return this.id = id;
    }

    String getApiRaceStr() {
        return this.apiRaceStr;
    }

    String getIconPath() {
        return this.iconPath;
    }

    static Race getByApiRaceStr(String apiRaceStr) {
        for (Race race : Race.values()) {
            if (race.getApiRaceStr().equals(apiRaceStr)) {
                return race;
            }
        }
        return null;
    }
}
