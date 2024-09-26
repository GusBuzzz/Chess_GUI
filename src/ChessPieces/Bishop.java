// Diego Gonzalez 2/29: Created class Bishop that implements interface
// Isaac Padilla: Removed MoveMethod so we use the default method from IntBishop

// Implementing the IntBishop interface

package ChessPieces;

import Interfaces.IntBishop;
import Enums.*;

public class Bishop implements IntBishop {
  private LocationX locationX;
  private int locationY;
  private PieceColor color;
  private PieceType pieceType;

  // Constructor to initialize the figure's location
  public Bishop(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    this.locationX = locationX;
    this.locationY = locationY;
    this.color = color;
    this.pieceType = pieceType;
  }

  public boolean moveToBishop(LocationX x, int y) {
    if (this.locationX.equals(x) && this.locationY == y)
      return false;
    int columnDiff = Math.abs(x.ordinal() - locationX.ordinal());
    int rowDiff = Math.abs(y - locationY);
    // Check if the bishop is moving along a diagonal line
    return (columnDiff == rowDiff);
  }

  @Override
  public String toString() {
      char rowChar = (char) ('8' - this.locationY); // Convert locationY to the corresponding row character
      return "Bishop at " + locationX + rowChar;
  }

  public LocationX getLocationX() {
    return locationX;
  }

  public void setLocationX(LocationX locationX) {
    this.locationX = locationX;
  }

  public int getLocationY() {
    return locationY;
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

  public void setLocation(LocationX valueOf, int row) {
    this.locationX = valueOf;
    this.locationY = row;
  }
}

// you cannot add the bishop in the array because they are not the same
// the queen inherits from the rook, but get the information from the bishop (no
// double inheritance), do inheritance and extend an interface
// cannot re-use code in the class for bishop, all you are getting is the name
// of the method
