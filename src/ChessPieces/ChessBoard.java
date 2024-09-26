// Diego Gonzalez 2/29: created Chessboard class

package ChessPieces;

import Interfaces.IntChessBoard;
import Enums.LocationX;

public class ChessBoard implements IntChessBoard {
  public static final int MAX_ROW = 8;
  public static final int MIN_ROW = 1;

  public boolean verifyCoordinate(char x, int y) {
    try {
      LocationX col = LocationX.valueOf(String.valueOf(x));
    } catch (IllegalArgumentException e) {
      return false;
    }
    return y >= MIN_ROW && y <= MAX_ROW;
  }
}
