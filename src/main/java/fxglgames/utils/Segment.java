package fxglgames.utils;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Segment {
  private Point from;
  private Point to;
  private Line asLine;
  
  public Segment( Point a, Point b ) {
    this.from = a;
    this.to = new Point( b.getX() - a.getX(), b.getY() - a.getY() );
  }
  
  public double magnitude() {
    return Math.sqrt( to.getX() * to.getX() + to.getY() * to.getY() );
  }
  
  public Point getFrom() {
    return from;
  }
  
  public Point getTo() {
    return to;
  }
  
  public Line getAsLine() {
    if (asLine == null) {
      asLine = new Line(from.x, from.y, from.x + to.x, from.y + to.y);
      asLine.setFill(Color.CYAN);
      asLine.setStroke(Color.CYAN);
    }
    return asLine;
  }
}
