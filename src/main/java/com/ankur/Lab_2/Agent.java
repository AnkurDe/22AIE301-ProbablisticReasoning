package com.ankur.Lab_2;

import java.util.*;

public class Agent {
    final int size;
    private final String[][] memory;
    private final HashSet<Pos> visitedCells;
    private final HashSet<Pos> suspectedPits;
    private final HashSet<Pos> suspectedWumpus;
    private final HashSet<Pos> safeCells;
    private boolean isUnsafeSolution;
    private List<Pos> solutionPath;
    private Pos goldLocation;

    public static record Pos(int x, int y) {
        public boolean isValid(int size) {
            return x >= 0 && y >= 0 && x < size && y < size;
        }

        public List<Pos> getAdjacentCells(int size) {
            List<Pos> adjacent = new ArrayList<>();
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                Pos newPos = new Pos(this.x + dir[0], this.y + dir[1]);
                if (newPos.isValid(size)) {
                    adjacent.add(newPos);
                }
            }
            return adjacent;
        }
    }

    public Agent(String[][] worldState) {
        this.size = worldState.length;
        this.memory = Driver.initGrid(size, "");
        this.visitedCells = new HashSet<>();
        this.suspectedPits = new HashSet<>();
        this.suspectedWumpus = new HashSet<>();
        this.safeCells = new HashSet<>();
        this.isUnsafeSolution = false;
        this.solutionPath = new ArrayList<>();
        this.goldLocation = null;

        // Initialize memory from world state
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (worldState[i][j] != null && !worldState[i][j].isEmpty()) {
                    memory[i][j] = worldState[i][j];
                }
            }
        }
    }

    /**
     * Handle stench - marks adjacent cells as suspected Wumpus locations
     */
    public void handleStench(Pos pos) {
        List<Pos> adjacent = pos.getAdjacentCells(size);
        for (Pos neighbor : adjacent) {
            suspectedWumpus.add(neighbor);
            memory[neighbor.x][neighbor.y] += "v";
        }
    }

    /**
     * Handle breeze - marks adjacent cells as suspected pit locations
     */
    public void handleBreeze(Pos pos) {
        List<Pos> adjacent = pos.getAdjacentCells(size);
        for (Pos neighbor : adjacent) {
            if (!suspectedPits.contains(neighbor)) {
                suspectedPits.add(neighbor);
                memory[neighbor.x][neighbor.y] += "p";
            }
        }
    }

    /**
     * Handle both stench and breeze
     */
    public void handleStenchAndBreeze(Pos pos) {
        handleStench(pos);
        handleBreeze(pos);
    }

    /**
     * Parse cell content and handle different scenarios
     */
    private void processCellContent(Pos pos, String content) {
        if (content.contains("G")) {
            goldLocation = pos;
        }
        if (content.contains("S")) {
            handleStench(pos);
        }
        if (content.contains("B")) {
            handleBreeze(pos);
        }
    }

    /**
     * Check if a cell is safe to move to
     */
    private boolean isSafe(Pos pos) {
        return !suspectedPits.contains(pos) && !suspectedWumpus.contains(pos);
    }

    /**
     * DFS-based pathfinding to reach gold with backtracking
     */
    public List<Pos> run(int startX, int startY) {
        Pos start = new Pos(startX, startY);

        // Parse initial cell
        String initialContent = memory[startX][startY];
        processCellContent(start, initialContent);
        safeCells.add(start);
        visitedCells.add(start);

        // If gold is at start, we're done
        if (goldLocation != null && goldLocation.equals(start)) {
            solutionPath.add(start);
            return solutionPath;
        }

        // Try to find safe path first
        boolean foundSafePath = dfs(start, new HashSet<>(), new ArrayList<>(), true);

        // If no safe path found, try unsafe path
        if (!foundSafePath && goldLocation != null) {
            System.out.println("UNSAFE SOLUTION: No safe path found. Attempting risky route...");
            isUnsafeSolution = true;
            dfs(start, new HashSet<>(), new ArrayList<>(), false);
        }

        return solutionPath;
    }

    /**
     * Depth-First Search with option for safe or risky exploration
     *
     * @param current Current position
     * @param visited Cells visited in current path
     * @param path Current path being explored
     * @param safeOnly If true, only explore safe cells
     * @return true if gold found
     */
    private boolean dfs(Pos current, HashSet<Pos> visited, List<Pos> path, boolean safeOnly) {
        // Found gold!
        if (goldLocation != null && current.equals(goldLocation)) {
            path.add(current);
            solutionPath = new ArrayList<>(path);
            return true;
        }

        visited.add(current);
        path.add(current);

        // Get adjacent cells
        List<Pos> adjacent = current.getAdjacentCells(size);

        // Sort adjacent cells by exploration strategy
        adjacent.sort(Comparator.comparingInt(p -> {
            // Prioritize unexplored cells
            if (!visitedCells.contains(p)) {
                return 0;
            }
            return 1;
        }));

        for (Pos next : adjacent) {
            // Skip if already in current path (avoid cycles)
            if (visited.contains(next)) {
                continue;
            }

            // Check if safe to move
            boolean nextIsSafe = isSafe(next);
            if (safeOnly && !nextIsSafe) {
                continue;
            }

            // Process the cell content when we explore it
            String nextContent = memory[next.x][next.y];
            if (nextContent != null && !nextContent.isEmpty()) {
                processCellContent(next, nextContent);
            }

            visitedCells.add(next);

            // Recursive DFS
            if (dfs(next, new HashSet<>(visited), new ArrayList<>(path), safeOnly)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Print solution analysis
     */
    public void printSolution() {
        if (solutionPath.isEmpty()) {
            System.out.println("NO SOLUTION: Cannot reach gold from starting position.");
            return;
        }

        if (isUnsafeSolution) {
            System.out.println("UNSAFE SOLUTION FOUND");
            System.out.println("Path requires passing through risky cells (suspected pits or Wumpus)");
        } else {
            System.out.println("SAFE SOLUTION FOUND");
            System.out.println("Path avoids all suspected dangers");
        }

        System.out.println("Solution path:");
        for (int i = 0; i < solutionPath.size(); i++) {
            Pos p = solutionPath.get(i);
            System.out.println((i + 1) + ". (" + p.x + ", " + p.y + ")");
        }
        System.out.println("Total moves: " + (solutionPath.size() - 1));
        System.out.println();
    }

    /**
     * Get the solution path
     */
    public List<Pos> getSolutionPath() {
        return solutionPath;
    }

    /**
     * Check if solution is unsafe
     */
    public boolean isUnsafe() {
        return isUnsafeSolution;
    }

    /**
     * Get memory state for analysis
     */
    public String[][] getMemory() {
        return memory;
    }

    /**
     * Print memory grid
     */
    public void printMemory() {
        System.out.println("Memory Grid:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("[" + memory[i][j] + "] ");
            }
            System.out.println();
        }
        System.out.println();
    }
}