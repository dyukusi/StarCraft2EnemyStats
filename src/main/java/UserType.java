public enum UserType {
    User("user"),
    Computer("computer");

    private String apiTeamTypeStr;
    UserType(String apiTeamTypeStr) {
        this.apiTeamTypeStr = apiTeamTypeStr;
    }

    String getApiTeamTypeStr() {
        return this.apiTeamTypeStr;
    }

    static UserType getByApiTeamTypeStr(String apiTeamTypeStr) {
        for (UserType ut : UserType.values()) {
            if (ut.getApiTeamTypeStr().equals(apiTeamTypeStr)) {
                return ut;
            }
        }
        return null;
    }
}
