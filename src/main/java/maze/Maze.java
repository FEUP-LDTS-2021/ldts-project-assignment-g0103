package maze;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import com.googlecode.lanterna.input.KeyStroke;
import handler.BombsHandler;
import handler.CoinsHandler;
import handler.PortalHandler;
import element.Element;
import element.dynam.Hero;
import element.position.Position;
import element.position.PositionInterface;
import element.Static.*;
import game.GameInterface;
import handler.HeroHandler;

import java.util.*;

/**
 * Maze class which contains the elements that make up the game, this includes, but is not limited to, static elements such as hero, path, walls, other special
 * game objects and the actual maze in matrix form, generated from the MazeGenerator class. This class is also responsible for managing the directional inputs received by the
 * player and verifying the state of the game.
 *
 * @author Eduardo Correia
 * @author Alberto Serra
 * @author José Carvalho
 */
public class Maze implements MazeInterface {
    final private int xIncr = 5;
    final private int yIncr = 8;
    private int counter;
    private Position begin;
    private final Position ending;
    private final GameInterface game;
    private int[][] maze;
    private int dim;
    private Hero hero;
    private List<StaticElement> staticElems;
    private List<Position> emptyTiles;
    private List<Heart> hp;
    private List<StaticElement> coins;
    private Queue<StaticElement> path;
    private Queue<Bomb> bombs;
    private HeroHandler heroHandler;
    private PortalHandler portalHandler;
    private int currentHealth;


    private BombsHandler bombsHandler;


    private CoinsHandler coinsHandler;
    final private String backgroundcolor = "BLACK";

    //TODO change hero constructor to accept starting hp as a variable and the correspondent tests

    /**
     * Constructor for the maze class. Requires a game class in which the maze shall be used and an appropriate dimension.
     *
     * @param game game class in which the maze is to be used in.
     * @param dim  dimension integer which dictates the maze's size.
     */
    public Maze(GameInterface game, int dim) {
        //Initialize Variables
        this.game = game;
        this.dim = dim;
        this.begin = new Position(1 + xIncr, 1 + yIncr);
        this.ending = new Position(dim - 2 + xIncr, dim - 2 + yIncr);
        counter = 0;
        currentHealth = game.getHeroHp();
        hp = new ArrayList<>();
        staticElems = new LinkedList<>();
        emptyTiles = new LinkedList<>();
        path = new LinkedList<>();
        coins = new LinkedList<>();
        bombs = new LinkedList<>();

        hero = new Hero(begin, "GREEN", SGR.BORDERED, "@",currentHealth);
        heroHandler = new HeroHandler(hero, this);
        bombsHandler = new BombsHandler(this);
        getMaze(dim);
        maze = load_walls(maze, dim);
        createElements();
    }

    @Override
    public int getxIncr() {
        return xIncr;
    }

    @Override
    public int getyIncr() {
        return yIncr;
    }

    @Override
    public Position getBegin() {
        return begin;
    }

    public void getMaze(int dim) {
        MazeGenerator gen = null;
        do {
            gen = new MazeGenerator(dim - 2);
            gen.generateMaze();
            maze = gen.getIntMaze();
        } while (maze[dim - 3][dim - 3] == 0);
    }

    @Override
    public PositionInterface getEnding() {
        return ending;
    }

    @Override
    public List<StaticElement> getStaticElems() {
        return staticElems;
    }

    @Override
    public Queue<StaticElement> getPath() {
        return path;
    }

    @Override
    public GameInterface getGame() {
        return game;
    }

    /**
     * Function to encapsulate the raw integer maze matrix in the value 0 (which corresponds to a Wall).
     *
     * @param map raw integer maze generated by the maze generator.
     * @param dim integer dimension for the output maze.
     * @return returns a new raw integer maze in which the outer layers have the value of 0.
     */
    private static int[][] load_walls(int[][] map, int dim) {
        int[][] maze = new int[dim][dim];
        for (int i = 1; i < dim - 1; i++) {
            for (int j = 1; j < dim - 1; j++) {
                if (map[i - 1][j - 1] != 0) maze[i][j] = map[i - 1][j - 1];
            }
        }
        return maze;
    }

    @Override
    public void createElements() {
        createHpBar();
        loadHearts();
        createWalls();
        createTrophy();
        createPortals();
        createCoins();
    }



    @Override
    public int getDim() {
        return dim;
    }

    @Override
    public void nextFrame(KeyStroke key) {
        counter++;
        bombsHandler.tickAllBombs();
        if (key != null)
            heroHandler.checkKey(key);
        if (counter == 10) {
            if (path.size() != 0) {
                PositionInterface pathPosition = path.remove().getPosition();
                staticElems.add(new RedPath(pathPosition, "RED", SGR.BOLD, "{"));
            }
            counter = 0;
        }

    }

    /**
     * Creates a Trophy at the ending position of the maze.
     */
    private void createTrophy() {
        staticElems.add(new Trophy(ending, "#F3CA28", SGR.BOLD, "$"));
    }

    /**
     * Creates walls objects corresponding to where the value 0 exists in the raw integer maze.
     */
    private void createWalls() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if(!(i == 1 && j == 1) && !(i == dim-2 && j == dim-2)) {
                    if (maze[i][j] == 0) {
                        if(i == 0 || j == 0 || i == dim-1 || j == dim - 1){
                            staticElems.add(new Wall(new Position(i + xIncr, j + yIncr), "#FFFFFF", SGR.BOLD, "#",true));
                        }
                        staticElems.add(new Wall(new Position(i + xIncr, j + yIncr), "#FFFFFF", SGR.BOLD, "#"));
                    }
                    else if (maze[i][j] == 1)
                            emptyTiles.add(new Position(i + xIncr, j + yIncr));
                }
            }
        }
    }

    public int getActualHeroHp(){
        return hero.getHealth();
    }
    /**
     * Creates an Hpbar object at the upper-left corner of the terminal.
     */
    private void createHpBar() {
        int xsize = hero.getHealth() + 2;
        int ysize = 3;
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                if (i == 0 || i == xsize - 1 || j == 0 || j == ysize - 1) {

                }
                //staticElems.add(new HpBar(new Position(i + 1, j + 1), "#FFFFFF", SGR.BOLD, "-"));
            }
        }

    }


    private void createPortals() {
        portalHandler = new PortalHandler(this);
        staticElems.add(portalHandler.getPortalA());
        staticElems.add(portalHandler.getPortalB());
    }

    private void createCoins() {
        coinsHandler = new CoinsHandler(this);
        for(int i = 0; i < dim/10; i++){
            coinsHandler.generateCoin();
        }
    }

    //TODO change hearts to be stored to a stack instead.


    @Override
    public void loadHearts() {
        hp.clear();
        for (int i = 1; i <= hero.getHealth(); i++) {
            hp.add(new Heart(new Position(i + 15, 2), "#FF0000", SGR.BOLD, "*"));
        }
    }

    @Override
    public void draw(TextGraphics screen) {
        screen.setBackgroundColor(TextColor.Factory.fromString(backgroundcolor));
        screen.fillRectangle(new TerminalPosition(xIncr, yIncr), new TerminalSize(dim, dim), ' ');
        for (Element element : hp)
            element.draw(screen);
        for (Element element : staticElems)
            element.draw(screen);
        for (Element tile : path)
            tile.draw(screen);
        for (Element coin : coins)
            coin.draw(screen);
        for (Element bomb : bombs)
            bomb.draw(screen);
        bombsHandler.draw(screen);
        loadHearts();
        hero.draw(screen);
    }

    /**
     * Transforms the raw integer maze to string form.
     *
     * @return raw integer maze in string form.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : maze) {
            sb.append(Arrays.toString(row) + "\n");
        }
        return sb.toString();
    }




    @Override
    public List<Position> getEmptyTiles() {
        return emptyTiles;
    }

    @Override
    public PortalHandler getPortalHandler() {
        return portalHandler;
    }

    @Override
    public List<StaticElement> getCoins() {
        return coins;
    }

    @Override
    public CoinsHandler getCoinsHandler() {
        return coinsHandler;
    }

    @Override
    public Queue<Bomb> getBombs() {
        return bombs;
    }

    @Override
    public BombsHandler getBombsHandler() {
        return bombsHandler;
    }

}

