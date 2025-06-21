package game.engine;

import game.engine.core.WindowManager;

import static org.lwjgl.opengl.GL11.*;

public class Main {

    private WindowManager windowManager;

    public void run() {
        init();
        loop();

        cleanup();
    }

    private void init() {
        windowManager = new WindowManager("My LWJGL + OpenGL Game", 800, 600, true);
        windowManager.init();
    }

    private void loop() {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

        while (!windowManager.windowShouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            windowManager.update();
        }
    }

    private void cleanup() {
        windowManager.cleanup();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}