package game;

public class Loop implements Runnable {
    private Game game;

    private boolean running;
    private final double updateRate = 1.0d / 60.0d;

    private long nextStatTime;
    private int fps, ups;

    public Loop(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        // game.Game game.Loop
        while (running) {
            currentTime = System.currentTimeMillis();

            // Time in seconds
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            // Accumulating the time
            accumulator += lastRenderTimeInSeconds;
            // Updating the lastUpdate
            lastUpdate = currentTime;

            while (accumulator >= updateRate) {
                update();
                accumulator -= updateRate;
            }
            render();
            printStats();
        }
    }

    // Printing the stats
    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.printf("FPS: %d, UPS: %d\n", fps, ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void render() {
        game.render();
        fps++;
    }

    private void update() {
        game.update();
        ups++;
    }
}
