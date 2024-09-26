// Diego Gonzalez 2/29: created class Queen that extends abstract class Figure and implements IntBishop
// Isaac Padilla 3/5: Implemented the logic for the queen move

package ChessPieces;

import Interfaces.IntBishop;
import Enums.*;

public class Queen extends Rook implements IntBishop {
  public Queen(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    super(pieceType, color, locationX, locationY);
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
  public boolean moveTo(LocationX x, int y) {
    // Implement logic for Queen using Rook's moveTo method and moveToBishop
    // implementation
    return super.moveTo(x, y) || moveToBishop(x, y);
  }

  @Override
  public String toString() {
      char rowChar = (char) ('8' - this.locationY); // Convert locationY to the corresponding row character
      return "Queen at " + locationX + rowChar;
  }
}

