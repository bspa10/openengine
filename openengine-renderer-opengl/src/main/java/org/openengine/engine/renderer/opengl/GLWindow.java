package org.openengine.engine.renderer.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import org.openengine.EngineState;
import org.openengine.core.EngineSettings;
import org.openengine.core.logging.Logger;
import org.openengine.core.logging.LoggerFactory;
import org.openengine.engine.renderer.RendererSettings;
import org.openengine.engine.renderer.Window;

public final class GLWindow extends Window {
    private static final Logger LOGGER = LoggerFactory.getLogger(GLWindow.class);

    private GLWindow(){}

    @Override
    public void setBgColor(float red, float green, float blue, float alfa) {
        GL11.glClearColor(red, green, blue, alfa);
    }

    @Override
    protected void doInit() {
        final var callback = GLFWErrorCallback.createPrint(System.err);
        GLFW.glfwSetErrorCallback(callback);


        LOGGER.trace("Initializing GLFW");
        if ( !GLFW.glfwInit() )
            throw new GLException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        var maximized = false;
        final var videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        final var windowSize = EngineSettings.getWindowSize();
        if (windowSize.x == 0 || windowSize.y == 0) {
            windowSize.setComponent(0, videoMode.width());
            windowSize.setComponent(1, videoMode.height());
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximized = true;
        }

        LOGGER.trace("Creating window");
        window = GLFW.glfwCreateWindow(windowSize.x, windowSize.y, EngineSettings.getWindowTitle(), MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
            throw new GLException("Unable to create window");

        LOGGER.trace("Sizing and Positioning window");
        if (maximized)
            GLFW.glfwMaximizeWindow(window);
        else {
            GLFW.glfwSetWindowPos(
                    window,
                    (videoMode.width() - windowSize.x) / 2,
                    (videoMode.height() - windowSize.y) / 2
            );
        }
        GLFW.glfwMakeContextCurrent(window);

        LOGGER.trace("Creating OpenGL Capabilities");
        GL.createCapabilities();
        GL11.glClearColor(0.5f, 0.0f, 0.5f, 0.0f);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glEnable(GL11.GL_STENCIL_TEST);
//        GL11.glEnable(GL11.GL_CULL_FACE);
//        GL11.glCullFace(GL11.GL_BACK);

        LOGGER.trace("Updating Frustum");
        RendererSettings
                .getFrustum()
                .identity()
                .perspective(
                        EngineSettings.getFieldOfView(),
                        getAspectRatio(),
                        EngineSettings.getzNear(),
                        EngineSettings.getzFar()
                );


        LOGGER.trace("Configuring callbacks");
        setUpWindowCallbacks();
        setUpInputCallbacks();

        LOGGER.trace("Showing window");
        GLFW.glfwShowWindow(window);
    }

    private void setUpWindowCallbacks() {
        GLFW.glfwSetWindowCloseCallback(window, (window) -> EngineState.setShouldStop());

        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            EngineSettings.getWindowSize().set(width, height);

            RendererSettings
                    .getFrustum()
                    .identity()
                    .perspective(
                            EngineSettings.getFieldOfView(),
                            getAspectRatio(),
                            EngineSettings.getzNear(),
                            EngineSettings.getzFar()
                    );

            GL11.glViewport(0, 0, EngineSettings.getWindowSize().x, EngineSettings.getWindowSize().y);
        });
    }

    private void setUpInputCallbacks() {

    }

    @Override
    public void hide() {
        LOGGER.trace("Hiding window");
        GLFW.glfwHideWindow(window);
    }

    @Override
    protected void doRefresh() {
        GL11.glFlush();
        GLFW.glfwSwapBuffers(window);
    }

    @Override
    public void update() {
        GLFW.glfwPollEvents();
    }

    @Override
    protected void doCleanup() {
        GLFW.glfwDestroyWindow(window);
    }

    @Override
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(window, title);
    }
}
