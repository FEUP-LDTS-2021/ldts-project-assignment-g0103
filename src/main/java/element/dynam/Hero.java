package element.dynam;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import element.position.Position;
import element.position.PositionInterface;

public class Hero extends DynamicElement {
    private int health;
    private int maxhealth; //todo ver se isto é necessario implementar

    public Hero(PositionInterface position) {
        super(position);
    }

    public void draw(TextGraphics screen) {
        screen.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        screen.enableModifiers(SGR.BORDERED);
        screen.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "@");
    }
    public int getHealth(){return this.health;}

    public void heroTakesDamage(){this.health--;}

    public void heroHeals(){this.health++;}

    public void setHealth(int newHealth){this.health = newHealth;}

    public PositionInterface moveUp() {
        return new Position(getPosition().getX(), getPosition().getY() - 1);
    }

    public PositionInterface moveDown() {
        return new Position(getPosition().getX(), getPosition().getY() + 1);
    }

    public PositionInterface moveLeft() {
        return new Position(getPosition().getX() - 1, getPosition().getY());
    }

    public PositionInterface moveRight() {
        return new Position(getPosition().getX() + 1, getPosition().getY());
    }
}
