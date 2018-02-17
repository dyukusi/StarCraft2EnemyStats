public class EnemyStats {
    Scene previousScene;

    EnemyStats() {
        this.previousScene = Scene.BOOT;
    }

    public void run() {
        while(true) {
            Scene currentScene = ClientAPIManager.getCurrentScene();

            // execute following process one time when other scenes => game scene
            if (!this.previousScene.equals(currentScene) && currentScene.equals(Scene.GAME)) {
                System.out.println("Game started");

                // NOTE: only supporting 1v1 atm
                makeTextAndMediaFilesFor1v1();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.previousScene = currentScene;
        }

    }

    // NOTE: only supporting 1v1 atm
    private void makeTextAndMediaFilesFor1v1() {
        GameInfo gameInfo = new GameInfo(Constant.OWN_NAME);
        Player own = gameInfo.getOwn();
        Player opponent = gameInfo.getOpponent();
        own.makeTextAndMediaFiles(Constant.ROOT_PATH_OF_OWN_INFO);
        opponent.makeTextAndMediaFiles(Constant.ROOT_PATH_OF_OPPONENT_INFO);
    }
}
