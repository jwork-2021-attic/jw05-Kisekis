package rougelike.world.mapgen;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Arrays;

public class MazeGenerator {
    
    private Stack<Node> stack = new Stack<>();
    private Random rand = new Random();
    private int[][] maze;
    private int dimension;

    private int[] endPos;
    public int[] getEndPos() {
        return endPos;
    }

    public MazeGenerator(int dim) {
        maze = new int[dim][dim];
        dimension = dim;
        endPos = new int[2];
    }

    public void generateMaze() {
        stack.push(new Node(0,0));
        while (!stack.empty()) {
            Node next = stack.pop();
            if (validNextNode(next)) {
                if(isEnd(next)) {endPos[0] = next.y;endPos[1] = next.x;};
                maze[next.y][next.x] = 1;
                ArrayList<Node> neighbors = findNeighbors(next);
                randomlyAddNodesToStack(neighbors);
            }
        }
    }
    public int getMazeBlock(int i, int j) {
        return maze[i][j];
    }

    public String getRawMaze() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : maze) {
            sb.append(Arrays.toString(row) + "\n");
        }
        return sb.toString();
    }

    public String getSymbolicMaze() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(maze[i][j] == 1 ? "*" : " ");
                sb.append("  "); 
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean validNextNode(Node node) {
        int numNeighboringOnes = 0;
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && maze[y][x] == 1) {
                    numNeighboringOnes++;
                }
            }
        }
        return (numNeighboringOnes < 3) && maze[node.y][node.x] != 1;
    }

    private void randomlyAddNodesToStack(ArrayList<Node> nodes) {
        int targetIndex;
        while (!nodes.isEmpty()) {
            targetIndex = rand.nextInt(nodes.size());
            stack.push(nodes.remove(targetIndex));
        }
    }

    private ArrayList<Node> findNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotCorner(node, x, y)
                    && pointNotNode(node, x, y)) {
                    neighbors.add(new Node(x, y));
                }
            }
        }
        return neighbors;
    }

    private Boolean pointOnGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < dimension && y < dimension;
    }

    private Boolean pointNotCorner(Node node, int x, int y) {
        return (x == node.x || y == node.y);
    }
    
    private Boolean pointNotNode(Node node, int x, int y) {
        return !(x == node.x && y == node.y);
    }

    private Boolean isCorner(Node node) {
        return (node.x == dimension - 1 && node.y == dimension - 1)
                || (node.x == dimension - 1 && node.y == 0)
                || (node.x == 0 && node.y == dimension - 1);
    }

    public boolean[] CountNeighbor(int x,int y) {
        boolean[] neighbor = new boolean[4];
        if(x-1>=0) {
            neighbor[0] = maze[x-1][y]==1? true:false;
        }
        if(x+1<dimension) {
            neighbor[1] = maze[x+1][y]==1? true:false;
        }
        if(y-1>=0) {
            neighbor[2] = maze[x][y-1]==1? true:false;
        }
        if(y+1<dimension) {
            neighbor[3] = maze[x][y+1]==1? true:false;
        }

        return neighbor;
    }
    private Boolean isEnd(Node node) {
        boolean[] neighbor = CountNeighbor(node.y,node.x);
        int count = 0;
        for(boolean x: neighbor) {
            if(x) count++;
        }
        return (!(node.x==0 && node.y == 0)) && (count == 1);
    }
}
