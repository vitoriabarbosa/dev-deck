package devdeck.model.base;

import devdeck.model.home.Home;

import javax.swing.*;
import java.awt.*;

public abstract class Base extends JLabel {
    protected int x = 0;
    protected int y = 0;
    
    abstract public Home getHome();

    public void setBaseX(int x) {
        this.x = x;
    }
    
    public int getBaseX() {
        return this.x;
    }
    
    public void setBaseY(int y) {
        this.y = y;
    }
    
    public int getBaseY() {
        return this.y;
    }
    
    public abstract Point getNextCardPoint();
}