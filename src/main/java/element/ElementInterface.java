package element;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import element.position.PositionInterface;
/**
 * The Element class is the base class for the objects present in the maze. All elements extend this class.
 *
 * @author Eduardo Correia
 * @author Alberto Serra
 * @author José Carvalho
 */
public interface ElementInterface {
    /**
     * Returns the color of the element.
     * @return string value of the element's color.
     */
    String getColor();
    /**
     * Returns the format of the element.
     * @return string value of the element's format.
     */
    SGR getFormat();
    /**
     * Returns the Character of the element.
     * @return string value of the element's character.
     */
    String getCharacter();
    /**
     * Returns the position of the element.
     * @return position object corresponding to the element's location on the screen.
     */
    PositionInterface getPosition();
    /**
     * Sets the position of the element.
     * @param position Position (x,y) to set the element on.
     */
    void setPosition(PositionInterface position);
    /**
     *  Draws the element in a given screen.
     * @param screen Screen to draw the element on.
     */
    void draw(TextGraphics screen);
}
