import java.io.FileNotFoundException;

public class EnemyStats implements Runnable{
    private Scene previousScene;
    private GameInfo gameInfo;

    EnemyStats() {
        this.previousScene = Scene.BOOT;
    }

    @Override
    public void run() {
        while(true) {
            Scene currentScene = ClientAPIManager.getCurrentScene();

            // EnemyStats
            if (!this.previousScene.equals(currentScene) && currentScene.equals(Scene.GAME)) {
                System.out.println("Game started");

                // NOTE: only supporting 1v1 atm
                makeTextAndMediaFilesFor1v1();
            }

            // MMRLogger
            if (this.previousScene.equals(Scene.GAME) && !currentScene.equals(Scene.GAME) && !this.gameInfo.isReplay()) {
                this.logResult();

                try {
                    new MatchLogManager(Constant.PATH_OF_MMR_LOG).generateRatingChart(Constant.MMR_CHART_SIZE_X, Constant.MMR_CHART_SIZE_Y);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.previousScene = currentScene;
        }
    }

    private void logResult() {
        Player own = this.gameInfo.getPlayer(Main.g.getPlayerName());

        try {
            System.out.println("detecting new rating...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // request update info
        Util.httpGet(String.format(
                Constant.BASE_URL_OF_SC2LOGS_API_UPDATE,
                own.getRegion().getId(),
                own.getProfileId(),
                own.getRace().getId()
        ));

        Player updatedOwn = new Player(
                own.getRegion(),
                own.getPlayerId(),
                own.getName(),
                own.getUserType(),
                own.getRace()
        );

        System.out.println("newMMR: " + updatedOwn.getRating());

        long unixtime = System.currentTimeMillis() / 1000L;
        String outputText = String.format("%d,%d", unixtime, updatedOwn.getRating());
        Util.writeText(Constant.PATH_OF_MMR_LOG, outputText, false);
    }

    // NOTE: only supporting 1v1 atm
    private void makeTextAndMediaFilesFor1v1() {
        String ownName = Main.g.getPlayerName();
        this.gameInfo = new GameInfo(Main.g.getRegion());
        Player own = this.gameInfo.getPlayer(ownName);
        Player opponent = this.gameInfo.getOpponent(ownName);

        own.makeTextAndMediaFiles(Constant.ROOT_PATH_OF_OWN_INFO);
        opponent.makeTextAndMediaFiles(Constant.ROOT_PATH_OF_OPPONENT_INFO);
    }
}
