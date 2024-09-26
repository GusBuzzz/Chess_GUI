// Diego Gonzalez 2/29: Created class IntBishop

// Define the interface for the Bishop chess piece
package Interfaces;

import Enums.LocationX;

public interface IntBishop {
  // Method for moving the Bishop
  boolean moveToBishop(LocationX targetX, int targetY);
}
