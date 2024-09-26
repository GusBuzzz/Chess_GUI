// Diego Gonzalez 2/29: Created abstract class Figure

package ChessPieces;

import Interfaces.IntFigure;
import Enums.*;

// Abstract class Figure partially implementing IntFigure interface
public abstract class Figure implements IntFigure {
  protected LocationX locationX;
  protected int locationY;
  protected PieceColor color;
  protected PieceType pieceType;

  // Constructor to initialize the figure's location
  public Figure(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    this.locationX = locationX;
    this.locationY = locationY;
    this.color = color;
    this.pieceType = pieceType;
  }

  public boolean samePosition(LocationX targetX, int targetY) {
    return this.locationX.equals(targetX) && this.locationY == targetY;
  }

  // Abstract method for moving the figure
  // method will be implemented by subclasses
  public abstract boolean moveTo(LocationX x, int y);
  public LocationX getLocationX() {
      return locationX;
  }

  // Get the Y coordinate of the figure
  public int getLocationY() {
      return locationY;
  }

  // Set the location of the figure
  public void setLocation(LocationX x, int y) {
      this.locationX = x;
      this.locationY = y;
  }

  public void setLocationX(LocationX locationX) {
    this.locationX = locationX;
  }

  public void setLocationY(int locationY) {
    this.locationY = locationY;
  }

  public PieceColor getColor() {
    return color;
  }

  public void setColor(PieceColor color) {
    this.color = color;
  }

  public PieceType getPieceType() {
    return pieceType;
  }

  public void setPieceType(PieceType pieceType) {
    this.pieceType = pieceType;
  }
}

