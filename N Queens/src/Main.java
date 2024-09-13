import java.util.Scanner;

public class Main
{
    private static int numberOfStateChanges = 0;
    private static int numberOfRestarts = 0;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter board size (n > 4): ");
        int n = scanner.nextInt();
        eightQueens(n);
    }

    public static void eightQueens(int n)
    {
        State state = new State(n);
        setNeighborInfo(state);
        print(state);

        while (state.hasBetterNeighbor)
        {
            System.out.println("Setting new current state");
            state = new State(n, state.getBestNeighbor().getBoard());
            numberOfStateChanges++;
            setNeighborInfo(state);
            System.out.println();
            print(state);
        }

        if (state.getHeuristic() != 0)
        {
            System.out.println("RESTART");
            numberOfRestarts++;
            System.out.println();
            eightQueens(n);
        }
        else
        {
            System.out.println("Solution Found!");
            System.out.println("State changes: " + numberOfStateChanges);
            System.out.println("Restarts: " + numberOfRestarts);
        }
    }

    private static void setNeighborInfo(State state)
    {
        state.setNumberOfBetterNeighbors();
        state.setBestNeighbor();
    }

    private static void print(State state)
    {
        System.out.println("Current h: " + state.getHeuristic());
        System.out.println("Current State");
        state.printBoard();
        System.out.println("Neighbors found with lower h: " + state.getNumberOfBetterNeighbors());
    }
}
