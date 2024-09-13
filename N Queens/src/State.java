import java.util.ArrayList;

public class State
{
    private final int n;
    private int[][] board;
    private final int heuristic;
    private int numberOfBetterNeighbors;
    private State bestNeighbor;
    ArrayList<State> betterNeighbors;
    boolean hasBetterNeighbor = false;

    public State(int n)
    {
        this.n = n;
        board = generateBoard();
        heuristic = evaluateHeuristic();
    }

    public State(int n, int[][] board)
    {
        this.n = n;
        this.board = board;
        heuristic = evaluateHeuristic();
    }

    private int[][] generateBoard()
    {
        board = new int[n][n];

        for (int column = 0; column < n; column++)
        {
            board[(int) (Math.random() * n)][column] = 1;
        }

        return board;
    }

    public int evaluateHeuristic()
    {
        int temporaryHeuristic = 0;

        // checking for row conflicts

        boolean rowHasQueen = false;

        for (int[] row : board)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (rowHasQueen && row[j] == 1)
                {
                    temporaryHeuristic++;
                }
                else if (row[j] == 1)
                {
                    rowHasQueen = true;
                }
            }

            rowHasQueen = false;
        }

        // checking for up diagonal conflicts

        boolean upDiagonalHasQueen = false;

        for (int i = 1; i < board.length; i++)
        {
            for (int j = 0, currentRow = i; j < board[0].length && currentRow > -1; j++, currentRow--)
            {
                if (upDiagonalHasQueen && board[currentRow][j] == 1)
                {
                    temporaryHeuristic++;
                }
                else if (board[currentRow][j] == 1)
                {
                    upDiagonalHasQueen = true;
                }
            }
            upDiagonalHasQueen = false;
        }

        for (int j = 1; j < board[0].length; j++)
        {
            for (int i = n - 1, currentColumn = j; i > -1 && currentColumn < board[0].length; i--, currentColumn++)
            {
                if (upDiagonalHasQueen && board[i][currentColumn] == 1)
                {
                    temporaryHeuristic++;
                }
                else if (board[i][currentColumn] == 1)
                {
                    upDiagonalHasQueen = true;
                }
            }
            upDiagonalHasQueen = false;
        }


        // checking for down diagonal conflicts

        boolean downDiagonalHasQueen = false;

        for (int j = 6; j > -1; j--)
        {
            for (int i = 0, currentColumn = j; i < board.length && currentColumn < board[0].length; i++, currentColumn++)
            {
                if (downDiagonalHasQueen && board[i][currentColumn] == 1)
                {
                    temporaryHeuristic++;
                }
                else if (board[i][currentColumn] == 1)
                {
                    downDiagonalHasQueen = true;
                }
            }
            downDiagonalHasQueen = false;
        }

        for (int i = 1; i < board.length; i++)
        {
            for (int j = 0, currentRow = i; j < board[0].length && currentRow < board.length; j++, currentRow++)
            {
                if (downDiagonalHasQueen && board[currentRow][j] == 1)
                {
                    temporaryHeuristic++;
                }
                else if (board[currentRow][j] == 1)
                {
                    downDiagonalHasQueen = true;
                }
            }
            downDiagonalHasQueen = false;
        }

        return temporaryHeuristic;
    }

    public void setNumberOfBetterNeighbors()
    {
        betterNeighbors = new ArrayList<>();

        for (int j = 0; j < board[0].length; j++)
        {
            int queenRow = -1;

            for (int i = 0; i < board.length; i++)
            {
                if (board[i][j] == 1)
                {
                    queenRow = i;
                    break;
                }
            }

            for (int i = 0; i < board.length; i++)
            {
                if (queenRow != i)
                {
                    int[][] temporaryBoard = problemSolver(board);
                    temporaryBoard[queenRow][j] = 0;
                    temporaryBoard[i][j] = 1;

                    if ((new State(n, temporaryBoard)).evaluateHeuristic() < heuristic)
                    {
                        betterNeighbors.add(new State(n, temporaryBoard));
                        numberOfBetterNeighbors++;
                    }
                }
            }
        }
    }

    public void setBestNeighbor()
    {
        if (!betterNeighbors.isEmpty())
        {
            hasBetterNeighbor = true;
            int bestHeuristic = betterNeighbors.get(0).getHeuristic();
            bestNeighbor = betterNeighbors.get(0);

            for (State betterNeighbor : betterNeighbors)
            {
                if (betterNeighbor.getHeuristic() < bestHeuristic)
                {
                    bestHeuristic = betterNeighbor.getHeuristic();
                    bestNeighbor = betterNeighbor;
                }
            }
        }
        else
        {
            bestNeighbor = null;
        }
    }

    public int[][] getBoard()
    {
        return board;
    }

    public int getHeuristic()
    {
        return heuristic;
    }

    public int getNumberOfBetterNeighbors()
    {
        return numberOfBetterNeighbors;
    }

    public State getBestNeighbor()
    {
        return bestNeighbor;
    }

    public void printBoard()
    {
        for (int[] row : board)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                System.out.print(row[j]);

                if (j == 0 || j % n  != 0)
                {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }

    public static int[][] problemSolver(int[][] board)
    {
        int length = board.length;
        int[][] copy  = new int[length][length];

        for (int i = 0; i < length; i++)
        {
            System.arraycopy(board[i], 0, copy[i], 0, length);
        }

        return copy;
    }
}
