package buttonTest;

import element.position.Position;
import element.position.PositionInterface;
import game.Game;
import game.GameInterface;
import menu.ButtonInterface;
import menu.button.MainMenuButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MainMenuButtonTest {
    private ButtonInterface mainMenuButton;
    private GameInterface game;
    private PositionInterface position;

    @BeforeEach
    public void helper() {
        game = new Game();
        position = new Position(3, 5);
        mainMenuButton = new MainMenuButton(game, position);
    }

    @Test
    public void constructorTest() {
        ButtonInterface MainMenuButtonTemp = new MainMenuButton(game, position);
        assertTrue(MainMenuButtonTemp != null);
        assertTrue(mainMenuButton != null);
    }

    @Test
    public void executeTest() {
        //set game state to the value of 0
        assertEquals(game.getState(), 0);
        game.setState(5);
        assertEquals(game.getState(), 5);
        mainMenuButton.execute();
        assertEquals(game.getState(), 0);
    }

}
