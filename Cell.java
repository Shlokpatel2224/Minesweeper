public class Cell
{
    private boolean mine;
    private boolean revealed;
    private boolean flagged;
    private int value;

    public Cell()
    {
        mine = false;
        revealed = false;
        flagged = false;
        value = 0;
    }

    public boolean hasMine()
    {
        return mine;
    }

    public void setMine(boolean m)
    {
        mine = m;
    }

    public boolean isRevealed()
    {
        return revealed;
    }

    public void reveal()
    {
        revealed = true;
    }

    public boolean isFlagged()
    {
        return flagged;
    }

    public void toggleFlag()
    {
        flagged = !flagged;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int v)
    {
        value = v;
    }
}
