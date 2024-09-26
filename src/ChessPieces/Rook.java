// Diego Gonzalez 2/29: Created class Rook that extends abstract class Figure
// Isaac Padilla 3/6: Implemented the logic for the rook move

package ChessPieces;

import Enums.*;

public class Rook extends Figure {
  public Rook(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    super(pieceType, color, locationX, locationY);
  }

  @Override
  public boolean moveTo(LocationX x, int y) {
    // Implement movement logic for Rook
    if (samePosition(x, y))
      return false;
    return (this.locationX == x) ^ (this.locationY == y);
  }

  @Override
  public String toString() {
      char rowChar = (char) ('8' - this.locationY); // Convert locationY to the corresponding row character
      return "Rook at " + locationX + rowChar;
  }
}
