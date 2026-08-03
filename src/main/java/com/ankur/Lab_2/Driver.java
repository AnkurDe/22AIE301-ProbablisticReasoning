package com.ankur.Lab_2;

import java.util.List;

public class Driver {

    static String[][] initGrid(final int n, String state) {
        String[][] states = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                states[i][j] = state;
            }
        }
        return states;
    }

    /**
     * Grid 1: Straightforward path to gold
     * Layout:
     *   [0][0]  [0][1]  [0][2]  [0][3]
     * S   [1][0]  [1][1]  [1][2]  [1][3]
     * P B [2][0]  [2][1]  [2][2]  [2][3]
     * B B [3][0]  [3][1]  [3][2]  [3][3]
     */
    static String[][] createGrid1() {
        String[][] grid = initGrid(4, "");
        // Row 0
        grid[0][0] = "S";
        grid[0][1] = "B";
        grid[0][2] = "";
        grid[0][3] = "";

        // Row 1
        grid[1][0] = "S";
        grid[1][1] = "G";
        grid[1][2] = "B";
        grid[1][3] = "B";

        // Row 2
        grid[2][0] = "B";
        grid[2][1] = "B";
        grid[2][2] = "";
        grid[2][3] = "";

        // Row 3 (Start position)
        grid[3][0] = "P";
        grid[3][1] = "B";
        grid[3][2] = "";
        grid[3][3] = "";

        return grid;
    }

    /**
     * Grid 2: Mixed safe and unsafe paths
     * Layout with combined indicators (B,S = breeze and stench)
     */
    static String[][] createGrid2() {
        String[][] grid = initGrid(4, "");
        // Row 0
        grid[0][0] = "B";
        grid[0][1] = "G";
        grid[0][2] = "";
        grid[0][3] = "";

        // Row 1
        grid[1][0] = "B";
        grid[1][1] = "S";
        grid[1][2] = "B";
        grid[1][3] = "S";

        // Row 2
        grid[2][0] = "";
        grid[2][1] = "B";
        grid[2][2] = "S";
        grid[2][3] = "";

        // Row 3 (Start position)
        grid[3][0] = "P";
        grid[3][1] = "B";
        grid[3][2] = "S";
        grid[3][3] = "";

        return grid;
    }

    /**
     * Grid 3: Complex scenario with gold in dangerous area
     */
    static String[][] createGrid3() {
        String[][] grid = initGrid(4, "");
        // Row 0
        grid[0][0] = "B";
        grid[0][1] = "B";
        grid[0][2] = "S";
        grid[0][3] = "B";

        // Row 1
        grid[1][0] = "B";
        grid[1][1] = "S";
        grid[1][2] = "B";
        grid[1][3] = "S";

        // Row 2
        grid[2][0] = "B";
        grid[2][1] = "B";
        grid[2][2] = "S";
        grid[2][3] = "G";

        // Row 3 (Start position)
        grid[3][0] = "P";
        grid[3][1] = "B";
        grid[3][2] = "B";
        grid[3][3] = "";

        return grid;
    }

    /**
     * Main method to test all three grids
     */
    public static void main(String[] args) {
        System.out.println("===== WUMPUS WORLD SOLVER =====\n");

        // Test Grid 1
        System.out.println("========== GRID 1 ==========");
        testGrid(createGrid1(), 3, 0, 1);

        System.out.println("\n========== GRID 2 ==========");
        testGrid(createGrid2(), 3, 0, 1);

        System.out.println("\n========== GRID 3 ==========");
        testGrid(createGrid3(), 3, 0, 1);
    }

    /**
     * Helper method to test a grid
     */
    static void testGrid(String[][] worldState, int startX, int startY, int gridNum) {
        System.out.println("Initial World State:");
        printGrid(worldState);

        Agent agent = new Agent(worldState);
        List<Agent.Pos> solution = agent.run(startX, startY);

        // Print analysis
        System.out.println("\nAgent Memory After Exploration:");
        agent.printMemory();

        if (solution.isEmpty()) {
            System.out.println("Result: NO SOLUTION");
            System.out.println("The gold cannot be reached from the starting position.");
            System.out.println("Status: UNSOLVABLE");
        } else {
            agent.printSolution();
            if (agent.isUnsafe()) {
                System.out.println("Status: UNSAFE SOLUTION ⚠️");
                System.out.println("Requires risk-taking through dangerous cells!");
            } else {
                System.out.println("Status: SAFE SOLUTION ✓");
                System.out.println("Successfully reached gold without taking risks!");
            }
        }
        System.out.println();
    }

    /**
     * Print grid in readable format
     */
    static void printGrid(String[][] grid) {
        System.out.println("Grid Layout (4x4):");
        String[] labels = {"[0,0]", "[0,1]", "[0,2]", "[0,3]"};
        System.out.println("     " + String.join("    ", labels));

        for (int i = 0; i < grid.length; i++) {
            System.out.print("Row" + i + ": ");
            for (int j = 0; j < grid[i].length; j++) {
                String cell = grid[i][j].isEmpty() ? "." : grid[i][j];
                System.out.print(String.format("%-6s", cell));
            }
            System.out.println();
        }
        System.out.println("\nLegend: P=Start, G=Gold, B=Breeze, S=Stench, .=Empty");
    }
}