
// Diego Gonzalez 2/29: Created class Knight that extends abstract class Figure
// Isaac Padilla 3/6: Implemented the logic for the knight move

package ChessPieces;
import Enums.*;

public class Knight extends Figure {
  public Knight(PieceType pieceType, PieceColor color, LocationX locationX, int locationY) {
    super(pieceType, color, locationX, locationY);
  }

  @Override
  public boolean moveTo(LocationX x, int y) {
    // Implement movement logic for Knight
    if(samePosition(x, y)) return false;
    int columnDiff = Math.abs(this.locationX.ordinal() - x.ordinal());
    int rowDiff = Math.abs(this.locationY - y);
    return (columnDiff == 1 && rowDiff == 2) || (columnDiff == 2 && rowDiff == 1);
  }

  @Override
  public String toString() {
      char rowChar = (char) ('8' - this.locationY); // Convert locationY to the corresponding row character
      return "Knight at " + locationX + rowChar;
  }
}
