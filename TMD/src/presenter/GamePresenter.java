package presenter;

import model.Pemain;
import model.Bola;
import model.BolaManager;
import model.Keranjang;
import view.MainGameView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.random.RandomGenerator;

public class GamePresenter {
    private Pemain pemain;
    private BolaManager managerBola;
    private Keranjang keranjang;
    private MainGameView panel;

    private int currentFrame = 0;
    private int frameCounter = 0;
    private int spawnCountdown;
    private RandomGenerator random = RandomGenerator.getDefault();
    private int mousePositionX;
    private int mousePositionY;

    private GameOverListener gameOverListener;
    private final Set<Integer> pressedKeys = new HashSet<>();

    public GamePresenter(Pemain pemain, MainGameView panel, BolaManager managerBola, Keranjang keranjang){
        this.pemain = pemain;
        this.managerBola = managerBola;
        this.keranjang = keranjang;
        this.panel = panel;
        this.panel.addKeyListener(new KeyHandler());
    }

    public void setGameOverListener(GameOverListener gameOverListener) { this.gameOverListener = gameOverListener; }

    public void startGame(){
        panel.setKeranjang(this.keranjang);
        panel.startGameLoop();
        spawnCountdown = random.nextInt(30, 60);
        managerBola.spawnBola(10, panel.getWidth(), panel.getHeight());
    }

    public void updateGame(){
        int dx = 0;
        int dy = 0;
        spawnCountdown--;

        if (spawnCountdown <= 0) {
            managerBola.spawnSatuBola(panel.getWidth(), panel.getHeight());
            spawnCountdown = random.nextInt(30, 120);
        }

        if (pressedKeys.contains(KeyEvent.VK_UP) || pressedKeys.contains(KeyEvent.VK_W)) { dy = -1; }
        if (pressedKeys.contains(KeyEvent.VK_DOWN) || pressedKeys.contains(KeyEvent.VK_S)) { dy = 1; }
        if (pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A)) { dx = -1; }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D)) { dx = 1; }

        if (dx != 0 || dy != 0) {
            pemain.gerak(dx, dy, panel.getWidth(), panel.getHeight());
        }

        managerBola.updateAllBolas(panel.getWidth());

        // --- Logic for the Hook State Machine ---
        int playerCenterX = pemain.getPosX() + (pemain.getSize() / 2);
        int playerCenterY = pemain.getPosY() + (pemain.getSize() / 2);

        // Pancingan berada di posisi pemain terus ketika IDLE
        if (pemain.getPancingan().getState().equals("IDLE")) {
            pemain.getPancingan().setPosition(playerCenterX, playerCenterY);
        }

        String hookStateBefore = pemain.getPancingan().getState();
        pemain.getPancingan().updateState(mousePositionX, mousePositionY, managerBola.getBola(), playerCenterX, playerCenterY);
        String hookStateAfter = pemain.getPancingan().getState();

        // Jika terjadi sebuah "catch", maka remove bola dari list bola liar
        if (hookStateBefore.equals("SHOOTING") && hookStateAfter.equals("RETRACTING_CAUGHT")) {
            Bola caughtBola = pemain.getPancingan().getCaughtBola();
            if (caughtBola != null) {
                managerBola.getBola().remove(caughtBola);
            }
        }
        
        if (pemain.getPancingan().getState().equals("IDLE") && pemain.getPancingan().getCaughtBola() != null) {
            Bola heldBall = pemain.getPancingan().getCaughtBola();
            
            // Update posisi bola yang ditangkap mengikuti player
            pemain.getPancingan().updateCaughtBallPosition();
            
            // Cek apakah bola yang dipegang berada dalam keranjang
            if (keranjang.isBolaInside(heldBall)) {
                pemain.incrementBolaCollected();
                pemain.tambahScore(heldBall.getValue());
                pemain.getPancingan().releaseHook();
            }
        }

        if (managerBola.getBola().isEmpty()) {
            managerBola.spawnBola(5, panel.getWidth(), panel.getHeight());
        }

        updateAnimation();
        panel.setBola(managerBola.getBola());
        panel.setCurrentFrame(this.currentFrame);
        panel.setScore(pemain.getScore());
        panel.setBolaCollectedCount(pemain.getBolaCollected());
        panel.refreshView();
    }

    public void resetGame(){
        this.pressedKeys.clear();
        this.currentFrame = 0;
        this.frameCounter = 0;
        this.spawnCountdown = random.nextInt(30, 60);

        if (pemain != null) {
            pemain.reset();
            pemain.setPosition((panel.getWidth()/2) - (pemain.getSize() / 2), (panel.getHeight() / 2) - (pemain.getSize() / 2));
        }
        if(keranjang != null) {
            keranjang.setPosition((panel.getWidth() * 3 / 4) - (keranjang.getWidth() / 2), (panel.getHeight() / 2) - (keranjang.getHeight() / 2));
        }
        managerBola.spawnBola(10, panel.getWidth(), panel.getHeight());
    }

    private void updateAnimation() {
        frameCounter++;
        if (frameCounter > 10) {
            currentFrame = (currentFrame + 1) % 2;
            frameCounter = 0;
        }
    }

    public void handleMouseClicked(int x, int y){
        // Only fire the hook if it is idle and NOT holding a ball.
        if (pemain.getPancingan().getState().equals("IDLE") && pemain.getPancingan().getCaughtBola() == null) {
            mousePositionX = x;
            mousePositionY = y;
            pemain.getPancingan().setState("SHOOTING");
        }
    }

    public void updateHookPosition(int x, int y){
        if (pemain.getPancingan().getState().equals("IDLE")) {
            pemain.getPancingan().setPosition(x,y);
        }
    }

    private class KeyHandler extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                panel.stopGameLoop();
                if (gameOverListener != null) {
                    gameOverListener.gameOver();
                }
            } else {
                pressedKeys.add(e.getKeyCode());
            }
        }
        @Override
        public void keyReleased(KeyEvent e){
            pressedKeys.remove(e.getKeyCode());
        }
    }
}