import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ClientAPIManager {
    static Scene getCurrentScene() {
        JsonObject uiJson = Util.getJsonByURL(Constant.BASE_URL_OF_CLIENT_UI_API).getAsJsonObject();
        JsonObject gameJson = Util.getJsonByURL(Constant.BASE_URL_OF_CLIENT_GAME_API).getAsJsonObject();

        JsonArray activeScreensJsonArray = uiJson.getAsJsonArray("activeScreens");

        // game
        if (activeScreensJsonArray.size() <= 0) {
            return Scene.GAME;
        }

        // loading
        if (activeScreensJsonArray.get(0).getAsString().contains("ScreenLoading")) {
            return Scene.LOADING;
        }

        // menu
        return Scene.MENU;
    }
}
