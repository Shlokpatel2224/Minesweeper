import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI extends JFrame
{
    private Board board;
    private JButton[][] buttons;
    private boolean firstClick;

    public MinesweeperGUI()
    {
        String mode = JOptionPane.showInputDialog("Choose difficulty: easy, medium, hard");

        int rows;
        int cols;
        int mines;

        if (mode != null && mode.equalsIgnoreCase("easy"))
        {
            rows = 8;
            cols = 8;
            mines = 10;
        }
        else if (mode != null && mode.equalsIgnoreCase("medium"))
        {
            rows = 12;
            cols = 12;
            mines = 25;
        }
        else
        {
            rows = 16;
            cols = 16;
            mines = 40;
        }

        board = new Board(rows, cols, mines);
        buttons = new JButton[rows][cols];
        firstClick = true;

        setTitle("Minesweeper");
        setLayout(new GridLayout(rows, cols, 1, 1)); // spacing for grid lines
        getContentPane().setBackground(Color.BLACK); // grid line color

        int i;
        int j;

        for (i = 0; i < rows; i++)
        {
            for (j = 0; j < cols; j++)
            {
                JButton btn = new JButton();

                // covered tile color
                btn.setBackground(Color.BLUE);
                btn.setOpaque(true);

                // thin black border around each block
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                // better number visibility
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                btn.setForeground(Color.BLACK);

                int r = i;
                int c = j;

                btn.addMouseListener(new MouseAdapter()
                {
                    public void mousePressed(MouseEvent e)
                    {
                        if (SwingUtilities.isLeftMouseButton(e))
                        {
                            handleLeftClick(r, c);
                        }
                        else if (SwingUtilities.isRightMouseButton(e))
                        {
                            handleRightClick(r, c);
                        }
                    }
                });

                buttons[i][j] = btn;
                add(btn);
            }
        }

        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void handleLeftClick(int r, int c)
    {
        if (firstClick)
        {
            board.placeMines(r, c); // first click safe
            firstClick = false;
        }

        if (board.isMine(r, c))
        {
            showMines();
            JOptionPane.showMessageDialog(this, "Game Over!");
            return;
        }

        board.reveal(r, c);
        updateBoard();
    }

    private void handleRightClick(int r, int c)
    {
        Cell cell = board.getCell(r, c);

        if (!cell.isRevealed())
        {
            cell.toggleFlag();

            if (cell.isFlagged())
            {
                buttons[r][c].setText("🚩");
            }
            else
            {
                buttons[r][c].setText("");
            }
        }
    }

    private void updateBoard()
    {
        int i;
        int j;

        for (i = 0; i < board.getRows(); i++)
        {
            for (j = 0; j < board.getCols(); j++)
            {
                Cell cell = board.getCell(i, j);
                JButton btn = buttons[i][j];

                if (cell.isRevealed())
                {
                    btn.setBackground(Color.GREEN); // revealed tile

                    int value = cell.getValue();

                    // show numbers if next to mines
                    if (value > 0)
                    {
                        btn.setText("" + value);

                        // color numbers like real Minesweeper
                        if (value == 1) btn.setForeground(Color.BLUE);
                        else if (value == 2) btn.setForeground(Color.GREEN.darker());
                        else if (value == 3) btn.setForeground(Color.RED);
                        else btn.setForeground(Color.BLACK);
                    }
                }
            }
        }
    }

    private void showMines()
    {
        int i;
        int j;

        for (i = 0; i < board.getRows(); i++)
        {
            for (j = 0; j < board.getCols(); j++)
            {
                if (board.isMine(i, j))
                {
                    buttons[i][j].setBackground(Color.RED); // mine color
                }
            }
        }
    }

    public static void main(String[] args)
    {
        new MinesweeperGUI();
    }
}
