import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class GameInfo {
    private String ownName;
    private ArrayList<Player> players;

    GameInfo(String ownName) {
        this.ownName = ownName;
        this.players = new ArrayList<>();

        JsonObject gameJson = Util.getJsonByURL(Constant.BASE_URL_OF_CLIENT_GAME_API).getAsJsonObject();
        JsonArray players = gameJson.getAsJsonArray("players");

        players.forEach(player -> {
            JsonObject playerJson = player.getAsJsonObject();
            int playerId = playerJson.get("id").getAsInt();
            String name = playerJson.get("name").getAsString();
            UserType userType = UserType.getByApiTeamTypeStr(playerJson.get("type").getAsString());

            Race race = Race.getByApiRaceStr(playerJson.get("race").getAsString());

            System.out.println("[" + playerId + "] " + name);

            this.players.add(new Player(playerId, name, userType, race));
        });
    }

    public String getOwnName() {
        return ownName;
    }

    ArrayList<Player> getPlayers() {
        return this.players;
    }

    Player getOwn() {
        for (Player player : this.players) {
            if (player.getName().equals(this.ownName)) {
                return player;
            }
        }
        return null;
    }

    // NOTE: only supporting 1v1
    Player getOpponent() {
        for (Player player : this.players) {
            if (!player.getName().equals(this.ownName)) {
                return player;
            }
        }
        return null;
    }
}

