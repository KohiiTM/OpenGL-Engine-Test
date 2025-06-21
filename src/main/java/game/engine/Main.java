package game.engine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main {

    private long window;

    public void run() {
        init();
        loop();

        // Free window callbacks and destroy window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create window
        window = glfwCreateWindow(800, 600, "Hello LWJGL + OpenGL!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Key callback
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
           if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
               glfwSetWindowShouldClose(window, true);
        });

        // Get thread stack -> push new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            // Primary monitor resolution
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

           glfwSetWindowPos(
                   window,
                   (vidmode.width() - pWidth.get(0)) / 2,
                   (vidmode.height() - pHeight.get(0)) / 2
           ) ;
        }

        glfwMakeContextCurrent(window);
        // v-sync
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }
    private void loop() {
        createCapabilities();
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
