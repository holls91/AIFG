package gui.util;

/******************************************************************************
 *  Compilation:  javac DrawListener.java
 *  Execution:    none
 *  Dependencies: none
 *
 *  Interface that accompanies Draw.java.
 ******************************************************************************/

public interface DrawListener {

    /**
     * Invoked when the mouse has been pressed.
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    default void mousePressed(double x, double y) {}

    /**
     * Invoked when the mouse has been dragged.
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    default void mouseDragged(double x, double y) {}

    /**
     * Invoked when the mouse has been released.
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    default void mouseReleased(double x, double y) {}

    /**
     * Invoked when a key has been typed.
     *
     * @param c the character typed
     */
    default void keyTyped(char c) {}

    /**
     * Invoked when a key has been pressed.
     *
     * @param keycode the key combination pressed
     */
    default void keyPressed(int keycode) {}

    /**
     * Invoked when a key has been released.
     *
     * @param keycode the key combination released
     */
    default void keyReleased(int keycode) {}

}