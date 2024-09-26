import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    private JLabel pieceLabel; // Label to display the chess piece image

    public Tile(Color backgroundColor) {
        this.setLayout(new BorderLayout()); // Use BorderLayout for flexibility
        this.setBackground(backgroundColor);
        this.pieceLabel = new JLabel("",SwingConstants.CENTER);
        this.add(pieceLabel, BorderLayout.CENTER); // Add the label to the center of the tile
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional: adds a border to each tile
    }

    public void setPieceIcon(Icon icon) {
        pieceLabel.setIcon(icon); // Set the chess piece image
    }

    public void clearPieceIcon() {
        pieceLabel.setIcon(null);
    }

    public Object getPieceIcon() {
        return pieceLabel.getIcon();
    }

}
