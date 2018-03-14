import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class GameInfo {
    private ArrayList<Player> players;
    private boolean isReplay;

    GameInfo(Region region) {
        this.players = new ArrayList<>();

        JsonObject gameJson = Util.getJsonByURL(Constant.BASE_URL_OF_CLIENT_GAME_API).getAsJsonObject();
        this.isReplay = gameJson.get("isReplay").getAsBoolean();
        JsonArray players = gameJson.getAsJsonArray("players");

        players.forEach(player -> {
            JsonObject playerJson = player.getAsJsonObject();
            int playerId = playerJson.get("id").getAsInt();
            String name = playerJson.get("name").getAsString();
            UserType userType = UserType.getByApiTeamTypeStr(playerJson.get("type").getAsString());

            Race race = Race.getByApiRaceStr(playerJson.get("race").getAsString());

            System.out.println("[" + playerId + "] " + name);

            this.players.add(new Player(region, playerId, name, userType, race));
        });

        Player ownPlayer = this.getPlayer(Main.g.getPlayerName());
        ownPlayer.getAdditionalProfile(-1);
        int rating = ownPlayer.getRating();

        this.players.forEach(player -> {
            player.getAdditionalProfile(rating);
        });
    }

    ArrayList<Player> getPlayers() {
        return this.players;
    }

    boolean isReplay() {
        return this.isReplay;
    }

    Player getPlayer(String name) {
        for (Player player : this.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    // NOTE: only supporting 1v1
    Player getOpponent(String ownName) {
        for (Player player : this.players) {
            if (!player.getName().equals(ownName)) {
                return player;
            }
        }
        return null;
    }
}

