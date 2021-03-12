package implementations;

import java.util.ArrayDeque;
import java.util.Deque;

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
//        solveWithRecursion();
        solveWithBFS();
    }

    private void solveWithBFS() {
        Deque<int[]> queue = new ArrayDeque<>();

        queue.offer(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            this.matrix[current[0]][current[1]] = fillChar;


            if (isValidCoordinates(current[0] + 1 , current[1])
                    && this.matrix[current[0] + 1][current[1]] == this.toBeReplaced) {
                queue.offer(new int[]{current[0] + 1, current[1] });
            }

            if (isValidCoordinates(current[0] , current[1] + 1)
                    && this.matrix[current[0]][current[1] + 1] == this.toBeReplaced) {
                queue.offer(new int[]{current[0], current[1] + 1});
            }

            if (isValidCoordinates(current[0] - 1 , current[1])
                    && this.matrix[current[0] - 1 ][current[1]] == this.toBeReplaced) {
                queue.offer(new int[]{current[0] - 1, current[1]});
            }

            if (isValidCoordinates(current[0] , current[1] - 1)
                    && this.matrix[current[0]][current[1] - 1] == this.toBeReplaced) {
                queue.offer(new int[]{current[0], current[1]  - 1});
            }
        }
    }

    private void solveWithRecursion() {
        fillMatrix(startRow, startCol);
    }

    private void fillMatrix(int row, int col) {
        if (!isValidCoordinates(row, col)
                || this.matrix[row][col] != this.toBeReplaced) {
            return;
        }

        this.matrix[row][col] = fillChar;

        fillMatrix(row + 1, col);
        fillMatrix(row, col + 1);
        fillMatrix(row - 1, col);
        fillMatrix(row, col - 1);
    }

    private boolean isValidCoordinates(int startRow, int startCol) {
        return 0 <= startRow && startRow < this.matrix.length
                && 0 <= startCol && startCol < this.matrix[0].length;
    }

    public String toOutputString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : this.matrix) {
            for (char aChar : chars) {
                sb.append(aChar);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
