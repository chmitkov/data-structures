package implementations;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public class TheMatrix {

    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        //dfsSolve(startRow, startCol);
        bfsSolve(new int[]{startRow, startCol});
    }

    private int[] findSearchedCharIndex(char toBeReplaced) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == toBeReplaced) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    private void bfsSolve(int[] searchedCharIndexes) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(searchedCharIndexes);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            int row = current[0];
            int col = current[1];

            matrix[row][col] = fillChar;

            if (isValidIndex(row, col + 1) && matrix[row][col + 1] == toBeReplaced) {
                queue.offer(new int[]{row, col + 1});
            }

            if (isValidIndex(row + 1, col) && matrix[row + 1][col] == toBeReplaced) {
                queue.offer(new int[]{row + 1 , col});
            }

            if (isValidIndex(row, col - 1) && matrix[row][col - 1] == toBeReplaced) {
                queue.offer(new int[]{row, col - 1});
            }

            if (isValidIndex(row - 1, col) && matrix[row - 1][col] == toBeReplaced) {
                queue.offer(new int[]{row - 1, col});
            }
        }

    }

    private void dfsSolve(int row, int col) {
        matrix[row][col] = fillChar;

        if (isValidIndex(row, col + 1) && matrix[row][col + 1] == toBeReplaced) {
            dfsSolve(row, col + 1);
        }

        if (isValidIndex(row + 1, col) && matrix[row + 1][col] == toBeReplaced) {
            dfsSolve(row + 1, col);
        }

        if (isValidIndex(row, col - 1) && matrix[row][col - 1] == toBeReplaced) {
            dfsSolve(row, col - 1);
        }

        if (isValidIndex(row - 1, col) && matrix[row - 1][col] == toBeReplaced) {
            dfsSolve(row - 1, col);
        }
    }

    private boolean isValidIndex(int row, int col) {
        return row >= 0 && col >= 0 && row < matrix.length && col < matrix[0].length;
    }

    public String toOutputString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : this.matrix) {
            for (int col = 0; col < this.matrix[0].length; col++) {
                sb.append(chars[col]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
