// Diego Gonzalez 2/29: Created class Pawn that extends abstract class Figure
// Isaac Padilla 3/6: Implemented the logic for the pawn move

package ChessPieces;

import Enums.*;

public class Pawn extends Figure {
  public Pawn(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    super(pieceType, color, locationX, locationY);
  }

  @Override
  public boolean moveTo(LocationX x, int y) {
    if (samePosition(x, y)) // If the target position is the same as the current position, return false
        return false;

    int rowDiff = y - this.locationY; // Calculate the difference in rows
    int columnDiff = this.locationX.ordinal() - x.ordinal(); // Calculate the difference in columns

    // Determine the direction of pawn movement based on its color
    // For a white pawn, it should move towards higher row numbers (y values decrease)
    // For a black pawn, it should move towards lower row numbers (y values increase)
    if ((this.color == PieceColor.WHITE && rowDiff == -1 && columnDiff == 0) || // White pawn can move one square up
        (this.color == PieceColor.BLACK && rowDiff == 1 && columnDiff == 0)) { // Black pawn can move one square down
        return true;
    }

    return false; // If the conditions are not met, return false
  }

  @Override
  public String toString() {
      char rowChar = (char) ('8' - this.locationY); // Convert locationY to the corresponding row character
      return "Pawn at " + locationX + rowChar;
  }

}