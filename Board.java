import java.util.Random;

public class Board
{
    private Cell[][] grid;
    private int rows;
    private int cols;
    private int mines;

    public Board(int r, int c, int m)
    {
        rows = r;
        cols = c;
        mines = m;

        grid = new Cell[rows][cols];

        int i;
        int j;

        // create cells
        for (i = 0; i < rows; i++)
        {
            for (j = 0; j < cols; j++)
            {
                grid[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int r, int c)
    {
        return grid[r][c];
    }

    // place mines so first click is always zero
    public void placeMines(int safeR, int safeC)
    {
        Random rand = new Random();
        int count = 0;

        while (count < mines)
        {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);

            boolean nearFirstClick = false;

            int i;
            int j;

            // block mines in 3x3 area around first click
            for (i = safeR - 1; i <= safeR + 1; i++)
            {
                for (j = safeC - 1; j <= safeC + 1; j++)
                {
                    if (r == i && c == j)
                    {
                        nearFirstClick = true;
                    }
                }
            }

            if (!nearFirstClick && !grid[r][c].hasMine())
            {
                grid[r][c].setMine(true);
                count++;
            }
        }

        calculateValues();
    }

    // calculate numbers
    private void calculateValues()
    {
        int i;
        int j;

        for (i = 0; i < rows; i++)
        {
            for (j = 0; j < cols; j++)
            {
                if (!grid[i][j].hasMine())
                {
                    grid[i][j].setValue(countMines(i, j));
                }
            }
        }
    }

    // count mines around cell
    private int countMines(int r, int c)
    {
        int count = 0;
        int i;
        int j;

        for (i = r - 1; i <= r + 1; i++)
        {
            for (j = c - 1; j <= c + 1; j++)
            {
                if (i >= 0 && i < rows && j >= 0 && j < cols)
                {
                    if (grid[i][j].hasMine())
                    {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // reveal cells
    public void reveal(int r, int c)
    {
        if (r < 0 || r >= rows || c < 0 || c >= cols)
        {
            return;
        }

        Cell cell = grid[r][c];

        // stop if revealed or flagged
        if (cell.isRevealed() || cell.isFlagged())
        {
            return;
        }

        cell.reveal();

        // if value is not zero, stop spreading
        if (cell.getValue() != 0)
        {
            return;
        }

        int i;
        int j;

        // reveal all 8 neighbors
        for (i = r - 1; i <= r + 1; i++)
        {
            for (j = c - 1; j <= c + 1; j++)
            {
                if (!(i == r && j == c))
                {
                    reveal(i, j);
                }
            }
        }
    }

    public boolean isMine(int r, int c)
    {
        return grid[r][c].hasMine();
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }
}