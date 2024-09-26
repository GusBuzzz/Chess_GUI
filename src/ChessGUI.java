import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ChessPieces.*;
import Enums.*;

public class ChessGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JPanel controlPanel;
    private Tile[][] boardCells; // 8x8 chessboard
    private JComboBox<String> pieceSelector;
    private Map<String, ImageIcon> pieceImages; // Maps piece names to their images
    private Object currentPiece; // Will hold the current chess piece object
    private int currentRow, currentCol; // Track the current position of the selected piece
    // Map chess piece names to their classes for instantiation
    private Map<String, Class<?>> pieceClasses;
    private JComboBox<String> colorSelector;
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
    String[] ranks = {"1", "2", "3", "4", "5", "6", "7", "8"};
    ButtonGroup letterGroup;
    ButtonGroup rankGroup;
    private Set<String> occupiedPositions = new HashSet<>();
    private List<Object> createdPieces = new ArrayList<>(); // Declaring createdPieces list


    public ChessGUI() {
        initializePieceClasses();
        initializePieceImages();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Lab 6 Chess Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        boardPanel = new JPanel(new GridLayout(8, 8));
        controlPanel = new JPanel(new GridLayout(3, 1));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        initializeBoard();
        initializeControls();

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.WEST);

        add(mainPanel);
        setVisible(true);
    }

    private void initializePieceClasses() {
        pieceClasses = new HashMap<>();
        pieceClasses.put("Pawn", Pawn.class);
        pieceClasses.put("Rook", Rook.class);
        pieceClasses.put("Knight", Knight.class);
        pieceClasses.put("Bishop", Bishop.class);
        pieceClasses.put("Queen", Queen.class);
        pieceClasses.put("King", King.class);
    }

    private void initializePieceImages() {
        pieceImages = new HashMap<>();
        pieceImages.put("White Pawn", new ImageIcon("art/WP.gif"));
        pieceImages.put("White Rook", new ImageIcon("art/WR.gif"));
        pieceImages.put("White Knight", new ImageIcon("art/WN.gif"));
        pieceImages.put("White Bishop", new ImageIcon("art/WB.gif"));
        pieceImages.put("White Queen", new ImageIcon("art/WQ.gif"));
        pieceImages.put("White King", new ImageIcon("art/WK.gif"));

        pieceImages.put("Black Pawn", new ImageIcon("art/BP.gif"));
        pieceImages.put("Black Rook", new ImageIcon("art/BR.gif"));
        pieceImages.put("Black Knight", new ImageIcon("art/BN.gif"));
        pieceImages.put("Black Bishop", new ImageIcon("art/BB.gif"));
        pieceImages.put("Black Queen", new ImageIcon("art/BQ.gif"));
        pieceImages.put("Black King", new ImageIcon("art/BK.gif"));
    }

    private void initializeBoard() {
        boardCells = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color backgroundColor = (row + col) % 2 == 0 ? Color.WHITE : Color.GRAY;
                Tile tile = new Tile(backgroundColor);
                boardCells[row][col] = tile;
                boardPanel.add(tile);
            }
        }
    }

    private void initializeControls() {
        String[] chessPieces = {"Pawn", "Rook", "Knight", "Bishop", "Queen", "King"};
        pieceSelector = new JComboBox<>(chessPieces);
        controlPanel.add(pieceSelector);

        String[] colors = {"White", "Black"};
        colorSelector = new JComboBox<>(colors);
        controlPanel.add(colorSelector);

        // Radio buttons for selecting file (A-H)
        letterGroup = new ButtonGroup();
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
        JLabel fileLabel = new JLabel("Letter: ");
        filePanel.add(fileLabel);


        for (String letter : letters) {
            JRadioButton radioButton = new JRadioButton(letter);
            radioButton.setActionCommand(letter);
            letterGroup.add(radioButton);
            filePanel.add(radioButton);
        }
        controlPanel.add(filePanel);

        // Radio buttons for selecting rank (1-8)
        rankGroup = new ButtonGroup();
        JPanel rankPanel = new JPanel();
        rankPanel.setLayout(new BoxLayout(rankPanel, BoxLayout.Y_AXIS));

        JLabel rankLabel = new JLabel("Rank: ");

        rankPanel.add(rankLabel);

        for (String rank : ranks) {
            JRadioButton radioButton = new JRadioButton(rank);
            radioButton.setActionCommand(rank);
            rankGroup.add(radioButton);
            rankPanel.add(radioButton);
        }
        controlPanel.add(rankPanel);

        JButton createPieceButton = new JButton("Create Piece    ");
        controlPanel.add(createPieceButton);
        createPieceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCreatePiece();
            }
        });

        JButton targetButton = new JButton("Target Position");
        controlPanel.add(targetButton);
        targetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processTargetPosition();
            }
        });

        JButton resetButton = new JButton("Reset Demo     ");
        controlPanel.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    private void processCreatePiece() {
        if (occupiedPositions.size() >= 6) {
            JOptionPane.showMessageDialog(this, "You have reached the maximum limit of 6 unique pieces.");
            return;
        }
    
        // Get selected piece type
        String selectedPiece = (String) pieceSelector.getSelectedItem();
        Class<?> clazz = pieceClasses.get(selectedPiece);
    
        // Check if a piece of the same type already exists
        for (Object piece : createdPieces) {
            if (piece.getClass() == clazz) {
                JOptionPane.showMessageDialog(this, "You already have a " + selectedPiece + ".");
                return;
            }
        }
    
        // Get selected color
        String selectedColor = (String) colorSelector.getSelectedItem();
        PieceColor color = selectedColor.equals("White") ? PieceColor.WHITE : PieceColor.BLACK;
    
        // Get selected file (column)
        String selectedFile = getFileSelection();
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a file.");
            return;
        }
    
        // Convert the file (column) to its corresponding index (0 to 7)
        int selectedFileIndex = convertRankLetterToNumber(selectedFile);
    
        // Get selected rank (row)
        String rankString = getRankSelection();
        if (rankString == null) {
            JOptionPane.showMessageDialog(this, "Please select a rank.");
            return;
        }
        int selectedRank = 8 - Integer.parseInt(rankString); // Convert rank to 0-based index
    
        // Check if position is already occupied
        String positionKey = selectedFile + selectedRank;
        if (occupiedPositions.contains(positionKey)) {
            JOptionPane.showMessageDialog(this, "Position " + selectedFile + rankString + " is already occupied.");
            return;
        }
    
        try {
            String pieceKey = selectedColor + " " + selectedPiece;
            ImageIcon icon = pieceImages.get(pieceKey);
    
            if (icon == null) {
                throw new IllegalArgumentException("Failed to load icon for piece: " + pieceKey);
            }
    
            // Create new instance of the selected piece
            Object newPiece = clazz.getConstructor(PieceType.class, PieceColor.class, LocationX.class, int.class)
                    .newInstance(getPieceType(selectedPiece), color, LocationX.valueOf(selectedFile.toUpperCase()), selectedRank);
    
            if (icon != null) {
                setPieceImageOnTile(selectedRank, selectedFileIndex, icon);
                // Update current position after piece creation
                currentRow = selectedRank;
                currentCol = selectedFileIndex;
    
                // Add the position to the occupied set
                occupiedPositions.add(positionKey);
                createdPieces.add(newPiece);  // Keep track of created pieces
            }
    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private PieceType getPieceType(String pieceName) {
        switch (pieceName) {
            case "Pawn":
                return PieceType.PAWN;
            case "Rook":
                return PieceType.ROOK;
            case "Knight":
                return PieceType.KNIGHT;
            case "Bishop":
                return PieceType.BISHOP;
            case "Queen":
                return PieceType.QUEEN;
            case "King":
                return PieceType.KING;
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceName);
        }
    }
    
    private String getRankSelection() {
        for (Enumeration<AbstractButton> buttons = rankGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();  // Use action command instead of getText()
            }
        }
        return null;
    }

    private String getFileSelection() {
        for (Component component : controlPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component subComponent : panel.getComponents()) {
                    if (subComponent instanceof JRadioButton) {
                        JRadioButton radioButton = (JRadioButton) subComponent;
                        if (radioButton.isSelected()) {
                            return radioButton.getText();  // Use getText() instead of getActionCommand()
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private void processTargetPosition() {
        String targetPos = JOptionPane.showInputDialog(this, "Enter Target Position (e.g., e4):");
        
        // Check if input is provided and is of correct length
        if (targetPos != null && targetPos.length() == 2) {
            // Extract column and row from the input
            char colChar = Character.toUpperCase(targetPos.charAt(0)); // Convert to uppercase
            char rowChar = targetPos.charAt(1);
            
            // Check if column is a valid letter (A-H) and row is a valid number (1-8)
            if (colChar >= 'A' && colChar <= 'H' && rowChar >= '1' && rowChar <= '8') {
                int targetCol = convertRankLetterToNumber(Character.toString(colChar));
                int targetRow = 8 - Character.getNumericValue(rowChar); // Reverse the board row mapping
                
                // Validate positions
                if (targetCol >= 0 && targetCol < 8 && targetRow >= 0 && targetRow < 8) {
                    try {
                        boolean moveSuccessful = false;
                        StringBuilder invalidMovesMessage = new StringBuilder();
        
                        for (Object piece : createdPieces) {
                            String pieceMessage = null;
                            if (piece instanceof Bishop) {
                                Bishop bishop = (Bishop) piece;
                                String pieceKey = getColorAndTypeKey(bishop, bishop.getColor(), bishop.getPieceType());
                                ImageIcon icon = pieceImages.get(pieceKey);
                                boolean isValidMove = bishop.moveToBishop(LocationX.valueOf(targetPos.substring(0, 1).toUpperCase()), targetRow);
        
                                if (!isValidMove) {
                                    pieceMessage = bishop + " cannot move to " + targetPos.substring(0, 1).toUpperCase() + targetPos.substring(1);
                                } else {
                                    // Check if the target position is occupied
                                    if (isPositionOccupied(targetCol, targetRow)) {
                                        pieceMessage = bishop + " can move to " + targetPos.substring(0, 1).toUpperCase() + targetPos.substring(1) +
                                        " but it is occupied!";
                                    }
                                    else{
                                        pieceMessage = bishop + " can move to " + targetPos.substring(0, 1).toUpperCase() + targetPos.substring(1);;
                                        int prevRow = getBishopRow(bishop);
                                        int prevCol = getBishopColumn(bishop).ordinal();
            
                                        boardCells[prevRow][prevCol].clearPieceIcon();
                                        setPieceImageOnTile(targetRow, targetCol, icon);
                                        updateBishopPosition(bishop, targetRow, targetCol);
                                        moveSuccessful = true;
                                    }
                                }
                            } else if (piece instanceof Figure) {
                                Figure chessPiece = (Figure) piece;
                                String pieceKey = getColorAndTypeKey(chessPiece, chessPiece.getColor(), chessPiece.getPieceType());
                                ImageIcon icon = pieceImages.get(pieceKey);
        
                                boolean isValidMove = chessPiece.moveTo(LocationX.valueOf(targetPos.substring(0, 1).toUpperCase()), targetRow);
        
                                if (!isValidMove) {
                                    pieceMessage = chessPiece + " cannot move to " + targetPos.substring(0, 1).toUpperCase() + targetPos.substring(1);
                                } else {
                                    if (isPositionOccupied(targetCol, targetRow)) {
                                        pieceMessage = chessPiece + " can move to " + targetPos.substring(0, 1).toUpperCase() + targetPos.substring(1) +
                                        " but it is occupied!";
                                    }
                                    else{
                                        pieceMessage = chessPiece + " can move to " + targetPos.substring(0, 1).toUpperCase() + targetPos.substring(1);;
                                        int prevRow = getPieceRow(chessPiece);
                                        int prevCol = getPieceColumn(chessPiece).ordinal();
            
                                        boardCells[prevRow][prevCol].clearPieceIcon();
                                        setPieceImageOnTile(targetRow, targetCol, icon);
                                        updatePiecePosition(chessPiece, targetRow, targetCol);
                                        moveSuccessful = true;
    
                                    }
                                }
                            }
        
                            if (pieceMessage != null) {
                                invalidMovesMessage.append(pieceMessage).append("\n");
                            }
                        }
        
                        if (invalidMovesMessage.length() > 0) {
                            JOptionPane.showMessageDialog(this, invalidMovesMessage.toString());
                            return; // Don't proceed if there are invalid moves
                        }
        
                        if (!moveSuccessful) {
                            JOptionPane.showMessageDialog(this, "No valid move found for the selected piece.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Inform the user about invalid position
                    JOptionPane.showMessageDialog(this, "Invalid position. Please enter a position within the chessboard.");
                }
            } else {
                // Inform the user about invalid format
                JOptionPane.showMessageDialog(this, "Invalid input format. Please enter a position in the format 'A1' to 'H8'.");
            }
        } else {
            // Inform the user about invalid input length
            JOptionPane.showMessageDialog(this, "Invalid input length. Please enter a position in the format 'A1' to 'H8'.");
        }
    }
    
    private void updateBishopPosition(Bishop bishop, int row, int col) {
        bishop.setLocation(LocationX.valueOf(convertNumberToRankLetter(col).toUpperCase()), row);
    }
    
    // Helper method to get the piece's column
    private LocationX getPieceColumn(Figure piece) {
        return piece.getLocationX();
    }

    private int getPieceRow(Figure piece) {
        return piece.getLocationY();
    }

    private void updatePiecePosition(Figure piece, int row, int col) {
        piece.setLocationX(LocationX.valueOf(convertNumberToRankLetter(col).toUpperCase()));
        piece.setLocationY(row);
    }

    private String convertNumberToRankLetter(int col) {
        if (col < 0 || col >= 8) {
            throw new IllegalArgumentException("Column index out of bounds: " + col);
        }
    
        char rankLetter = (char) ('A' + col);
        return String.valueOf(rankLetter);
    }    

    private void setPieceImageOnTile(int row, int col, ImageIcon icon) {
        // Set the new piece icon on the tile
        boardCells[row][col].setPieceIcon(icon);
    }

    private void resetGame() {
        // Clear the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardCells[row][col].clearPieceIcon();
            }
        }
    
        // Clear the occupied positions set
        occupiedPositions.clear();
    
        // Clear the created pieces list
        createdPieces.clear();
    }

    private int convertRankLetterToNumber(String letter) {
        // The rank letters are A-H
        // We want to convert A to 0, B to 1, ..., H to 7
        return letter.charAt(0) - 'A';
    }

    // Helper method to check if a position is already occupied
    private boolean isPositionOccupied(int col, int row) {
        for (Object piece : createdPieces) {
            if (piece instanceof Figure) {
                Figure figurePiece = (Figure) piece;
                int pieceCol = convertRankLetterToNumber(getPieceColumn(figurePiece).toString());
                int pieceRow = getPieceRow(figurePiece);
                if (pieceCol == col && pieceRow == row) {
                    return true;
                }
            } else if (piece instanceof Bishop) {
                Bishop bishopPiece = (Bishop) piece;
                if (getBishopColumn(bishopPiece).ordinal() == col && getBishopRow(bishopPiece) == row) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method to get the color and type key for a piece
    private String getColorAndTypeKey(Object piece, PieceColor color, PieceType pieceType) {
        String colorString = color == PieceColor.WHITE ? "White" : "Black";
        String typeString = pieceType.toString();
        String key = colorString + " " + typeString.charAt(0) + typeString.substring(1).toLowerCase();
        return key;
    }

    private int getBishopRow(Bishop bishop) {
        return bishop.getLocationY();
    }
    
    private LocationX getBishopColumn(Bishop bishop) {
        return bishop.getLocationX();
    }
}
