package handler;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.input.KeyStroke;
import element.Element;
import element.Static.*;
import element.dynam.Hero;
import element.position.Position;
import element.position.PositionInterface;
import game.GameInterface;
import maze.MazeInterface;


public class HeroHandler {
    private Hero hero;
    private MazeInterface maze;
    private GameInterface game;
    private final int heroHealth = 5;


    public HeroHandler(Hero hero, MazeInterface maze) {
        this.hero = hero;
        this.maze = maze;
        game = maze.getGame();
        hero.setHealth(heroHealth);
    }

    public void checkTile(PositionInterface position) {
        int index;
        if (position.equals(maze.getEnding())) {
            maze.getGame().winGame();
            return;
        } else {
            if (checkElement(position, RedPath.class, maze.getStaticElems()) != 0) {
                takeDamage();
            }
            else if (checkElement(position, Wall.class, maze.getStaticElems()) != 0) {
                return;
            }
            index = checkElement(position, Portal.class, maze.getStaticElems());
            if (index != 0) {
                teleportHero(index);
                return;
            }
        }
        moveHero(position);
    }

    public void takeDamage() {
        hero.heroTakesDamage();
        maze.getGame().getPointsHandler().incrementPoints(-130);
        maze.loadHearts();
        if (hero.isDead()) {
            hero.setHealth(heroHealth);
            maze.getGame().gameOver();
        }
    }

    private void teleportHero(int index){
        int counter = 0;
        for(StaticElement element : maze.getStaticElems()){
            if(element.getClass() == Portal.class && index != counter) {
                maze.getPath().add(new Path(hero.getPosition(), "YELLOW", SGR.BOLD, "{"));
                hero.setPosition(element.getPosition());
            }
        }
    }

    public void checkKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp -> checkTile(hero.moveUp());
            case ArrowDown -> checkTile(hero.moveDown());
            case ArrowLeft -> checkTile(hero.moveLeft());
            case ArrowRight -> checkTile(hero.moveRight());
            case Enter -> maze.generateBombs(hero.getPosition().getX(), hero.getPosition().getY());
            case Delete -> maze.generateCoin(hero.getPosition().getX(), hero.getPosition().getY());
        }
    }


    public void moveHero(PositionInterface position) {
        if (checkEmpty(hero.getPosition())){
            maze.getPath().add(new Path(hero.getPosition(), "YELLOW", SGR.BOLD, "{"));
            game.getPointsHandler().incrementPoints(2);
            maze.getEmptyTiles().remove(new Position((Position) hero.getPosition()));
        }
        if (checkElement(hero.getPosition(), Coin.class, maze.getStaticElems()) != 0)
            game.getPointsHandler().incrementPoints(2); // trying to gain points when he gets a coin
        hero.setPosition(position);
    }

    private boolean checkEmpty(PositionInterface position){
        if(position.equals(maze.getBegin())) return true;
        for (Position pos : maze.getEmptyTiles()){
            if (pos.equals(position)) return true;
        }
        return false;
    }

    /**
     * Check if there's an element of a given class at a certain position in a given list.
     *
     * @param position position to check.
     * @param cl       Class type to check
     * @param list     list to check objects.
     * @return corresponding to the existence of a cl object at the given position.
     */
    private int checkElement(PositionInterface position, Class cl, Iterable<StaticElement> list) {
        int counter = 0;
        for (Element tile : list) {
            if (cl.isInstance(tile) && tile.getPosition().equals(position)) return counter;
            counter++;
        }
        return 0;
    }

}
