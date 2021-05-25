import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Puzzle {
    int size;
    String puzzle[][];




    //
    // constructor: fn is the filename where the puzzle is stored
    //
    public Puzzle(String fn) throws IOException {

        File file = new File(fn);
        Scanner sc = new Scanner(file);
        if (sc.hasNext()) {
            size = Integer.valueOf(sc.nextLine());
        }
        puzzle = new String[size][size];
        for (int i = 0; i < size; i++) {
            String c = sc.nextLine();
            for (int j = 0; j < size; j++) {
                puzzle[i][j] = c.charAt(j) + "";
            }
        }
    }

    //
    // search the puzzle for the given word
    // return {a, b, x, y} where (a, b) is
    // the starting location and (x, y) is
    // the ending location
    // return null if the word can't be found
    // in the puzzle
    //

    public int[] search(String word) {

        ArrayList<String> letters = new ArrayList<>();

        int indexes[] = new int[4];

        for (int i = 0; i < word.length(); i++) {
            letters.add(word.charAt(i) + "");
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzle[i][j].equals(letters.get(0))) {
                    indexes = dLeft(i, j, letters);
                    if (indexes[0] != -1 ) {
                        return indexes;
                    }
                    indexes = dLeftUp(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                    indexes = dRight(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                    indexes = dRightUp(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                    indexes = right(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                    indexes = left(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                    indexes = up(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                    indexes = down(i, j, letters);
                    if (indexes[0] != -1) {
                        return indexes;
                    }
                }
            }
        }
        return null;
    }



    public int [] dLeft(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (y < list.size() - 1 || x > size - list.size()) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x + 1][y - 1].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
                x++;
                y--;

        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;
    }

    public int [] dLeftUp(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (y < list.size() - 1 || x < list.size() - 1) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x - 1][y - 1].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
            x--;
            y--;
        }

        indexes = new int[]{indexX, indexY, x, y};
        return indexes;

    }

    public int [] dRight(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (y > size - list.size() || x > size - list.size()) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x + 1][y + 1].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
            x++;
            y++;
        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;
    }

    public int [] dRightUp(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (y > size - list.size() || x < list.size() - 1) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x - 1][y + 1].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
            x--;
            y++;
        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;

    }

    public int [] right(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (y > size - 1 - list.size()) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x][y + 1].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
            y++;
        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;
    }

    public int [] left(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (y < list.size() - 1) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x][y - 1].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
            y--;
        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;
    }


    public int [] up(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;
        if (x < list.size() - 1) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }
        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x - 1][y].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;

            }
            x--;
        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;
    }

    public int [] down(int x, int y, ArrayList<String> list) {
        int indexes[] = new int[4];
        int indexX = x;
        int indexY = y;


        if (x > size - list.size()) {
            indexes = new int[]{-1, -1, -1, -1};
            return indexes;
        }

        for (int i = 1; i < list.size(); i++) {
            if (!puzzle[x + 1][y].equals(list.get(i))) {
                indexes = new int[]{-1, -1, -1, -1};
                return indexes;
            }
            x++;
        }
        indexes = new int[]{indexX, indexY, x, y};
        return indexes;
    }

}

