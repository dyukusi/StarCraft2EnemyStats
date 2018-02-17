public enum Race {
    Terran ("Terr",   "./image/race/terran.png"),
    Zerg   ("Zerg",   "./image/race/zerg.png"),
    Protoss("Prot",   "./image/race/protoss.png"),
    Random ("random", "./image/race/random.png");

    private String apiRaceStr;
    private String iconPath;
    Race(String apiRaceStr, String iconPath) {
        this.apiRaceStr = apiRaceStr;
        this.iconPath = iconPath;
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
