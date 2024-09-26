

// Diego Gonzalez 2/29: created King class that extends Queen
// Isaac Padilla 3/6: Implemented the logic for the king move
package ChessPieces;
import Enums.*;

public class King extends Queen {
  public King(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    super(pieceType, color, locationX, locationY);
  }

  @Override
  public boolean moveTo(LocationX column, int row) {
    return super.moveTo(column, row)
        && ((Math.abs(column.ordinal() - this.locationX.ordinal()) == 1 || Math.abs(row - this.locationY) == 1));
  }

  @Override
  public String toString() {
      char rowChar = (char) ('8' - this.locationY); // Convert locationY to the corresponding row character
      return "King at " + locationX + rowChar;
  }
}
