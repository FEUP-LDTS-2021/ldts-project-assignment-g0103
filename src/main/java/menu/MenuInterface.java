package menu;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import game.GameInterface;

import java.io.IOException;

/**
 * Menu interface generate various types of menus.
 *
 * @author Eduardo Correia
 * @author Alberto Serra
 * @author José Carvalho
 */

public interface MenuInterface {


    /**
     * Returns the string representing the background color of the menu.
     *
     * @return Menu background color.
     */
    String getBackGroundColor();

    /**
     * Returns the current game object interface.
     *
     * @return game object interface.
     */
    GameInterface getGame();

    /**
     * Returns the used screen object in the menu.
     *
     * @return Screen object.
     */
    Screen getScreen();


    /**
     * Return the menu text shown in gui of the application.
     *
     * @return the menu text,
     */
    String getText();

    /**
     * Loads the Menu visual walls used for design proposes.
     */
    void loadWalls();

    /**
     * Return the middle of the text String displayed in the Menu screen.
     *
     * @param screenWidth
     * @param text
     * @return the middle of the text String.
     */
    int getMiddle(int screenWidth, String text);

    /**
     * Iterates the selection of buttons to use.
     *
     * @param iterator
     */
    void iterateSelection(int iterator);

    /**
     * Selects the usage of a certain selected button.
     */
    void select();

    /**
     * Splits the text into words and add them to the list of texts.
     *
     * @param separator
     * @param xIncr
     * @param yIncr
     */
    void splitText(String separator, int xIncr, int yIncr);

    /**
     * Draws the menu object into the screen.
     */
    void draw();
}
