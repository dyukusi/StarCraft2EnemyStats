public class Constant {
    public static String OWN_NAME = "Dyukusi";

    public static final String BASE_URL_OF_SC2LOGS_API_SEARCH = "http://ec2-13-115-175-110.ap-northeast-1.compute.amazonaws.com:8080/api/profiles/%s/%s/%s";
    public static final String BASE_URL_OF_SC2LOGS_API_SEARCH_WITH_RATING = "http://ec2-13-115-175-110.ap-northeast-1.compute.amazonaws.com:8080/api/profiles/%s/%s/%s?rating=%d";
    public static final String BASE_URL_OF_SC2LOGS_API_UPDATE = "http://ec2-13-115-175-110.ap-northeast-1.compute.amazonaws.com:8080/api/profiles/update?region=%s&profileId=%d&race=%s";
    public static final String BASE_URL_OF_SC2LOGS_API_LEAGUE = "http://ec2-13-115-175-110.ap-northeast-1.compute.amazonaws.com:8080/api/leagues/%s";

    public static final String BASE_URL_OF_CLIENT_UI_API = "http://localhost:6119/ui";
    public static final String BASE_URL_OF_CLIENT_GAME_API = "http://localhost:6119/game";

    public static final String ROOT_PATH_OF_OWN_INFO = "./output/own/";
    public static final String ROOT_PATH_OF_OPPONENT_INFO = "./output/opponent/";
    public static final String PATH_OF_PLAYER_INFO_TEXT_DIR = "text/";
    public static final String PATH_OF_PLAYER_INFO_IMAGE_DIR = "image/";

    public static final String DEFAULT_CLAN_NAME = "Not belonging";

    public static final String MMR_CHART_IMAGE_PATH = "./output/own/mmrChart.png";
    public static final String PATH_OF_MMR_LOG = "./output/own/mmrLog.txt";
    public static final String PATH_OF_DUMMY_IMAGE = "./image/other/dummy.png";
    public static final String PATH_OF_UNKNOWN_IMAGE = "./image/other/unknown.png";
    public static final String DEFAULT_UNKNOWN_MARK = "?";
    public static final String RECENT_PLAYER_INFO_TEXT = "within 1hour";
    public static final String ONE_HOUR_BEFORE_PLAYER_INFO_TEXT = "1hour before";
    public static final String PREFIX_OF_HOURS_BEFORE_PLAYER_INFO_TEXT = "hours before";

    public static final int MMR_CHART_SIZE_X = 500;
    public static final int MMR_CHART_SIZE_Y = 300;

    public static final int[] LEAGUE_IDS = {
            0, // BRONZE
            1, // SILVER
            2, // GOLD
            3, // PLATINUM
            4, // DIAMOND
            5, // MASTER
            6  // GRANDMASTER
    };
}
