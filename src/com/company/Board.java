package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Board class simulates a board for Pentago game. It holds a list of the board
 * cells which itself holds a stone for player 1, player 2 or is empty.
 * The first player plays white and the second player plays black.
 * Also this class is a child class of JFrame.
 * @author Negar Movaghatian
 * @since 2020-03-30
 */
public class Board extends JFrame{

    // The list of the board's cells
    protected Cell[][] cells;
    private JLabel player;
    private int moves;
    private boolean[] isEmpty;

    /**
     * Creat a new board with adding 36 cells to it.
     */
    public Board() {

        cells = new Cell[6][6];
        moves = 0;
        isEmpty = new boolean[]{true, true, true, true};

        setLayout(new FlowLayout());

        for (int i=0; i<6; i++) {
            for (int j = 0; j < 6; j++) {
                Cell c = new Cell();
                if (j == 3)
                    add(new JLabel(" "));
                add(c.getButton());
                cells[i][j] = c;
            }
            if (i == 2) {
                JLabel space = new JLabel();
                for (int k=0; k<50; k++)
                    space.setText(space.getText() + "       ");
                space.setFont(new Font(space.getName(), Font.PLAIN, 4));
                add(space);
            }
        }
        player = new JLabel("[Player 1]");
        add(player);
    }

    /**
     * Swap the given section 90 degrees clockwise or anticlockwise.
     * @param section The section of the board to rotate.
     * @param direction The direction of the rotation. [CW: clockwise, ACW: anticlockwise]
     */
    private void rotate(int section, String direction) {
        int[] dSection = {0, 3, 18, 21}, edge, corner;
        if (direction=="ACW") {
            edge = new int[] {1, 8, 13, 6};
            corner = new int[] {0, 2, 14, 12};
        }
        else {
            edge = new int[] {6, 13, 8, 1};
            corner = new int []{12, 14, 2, 0};
        }
        for (int i=0; i<3; i++) {
            int x = (edge[i] + dSection[section])/6, y = (edge[i] + dSection[section])%6;
            int xp = (edge[(i + 1)] + dSection[section])/6, yp = (edge[(i + 1)] + dSection[section])%6;
            cells[x][y].swap(cells[xp][yp]);
            x = (corner[i] + dSection[section])/6; y = (corner[i] + dSection[section])%6;
            xp = (corner[(i + 1)] + dSection[section])/6; yp = (corner[(i + 1)] + dSection[section])%6;
            cells[x][y].swap(cells[xp][yp]);
        }
    }

    /**
     * Check if all the cells in the given sequence have the same color.
     * @param sequence The number of cells to check.
     * @return 1 if all the cells of the 'sequence' are black, 0 if white and if they do
     *         not have the same color -1.
     */
    private int sameColor(int[] sequence) {
        // Same color and black: 1, Same color and white: 0, Not the same color: -1
        int firstColor = cells[sequence[0]/6][sequence[0]%6].getStat();
        if (firstColor==0) return -1;
        for (int i=1; i<5; i++)
            if (cells[sequence[i]/6][sequence[i]%6].getStat()!=firstColor)
                return -1;
        return (firstColor==-1)? 1 : 0;
    }

    /**
     * Check if any of the players has 5 stones in a row.
     * @return false for each player if the player doesn't have 5 stones in
     *         a row and true otherwise.
     */
    private boolean[] checkBoard() {
        boolean[] ans = {false, false}; // [0: first player, 1: second player]
        int[][] diagonalWays = {{1, 8, 15, 22, 29}, {6, 13, 20, 27, 34}, {0, 7, 14, 21, 28}, {7, 14, 21, 28, 35}
                                , {11, 16, 21, 26, 31}, {4, 9, 14, 19, 24}, {5, 10, 15, 20, 25}, {10, 15, 20, 25, 30}};
        int[][] verticalWays = {{-1, 5, 11, 17, 23}, {5, 11, 17, 23, 29}};
        int[][] horizontalWays = {{-6, -5, -4, -3, -2}, {-5, -4, -3, -2, -1}};

        for (int i=0; i<8; i++) {
            int[] sequence = diagonalWays[i];
            if (sameColor(sequence)!=-1)
                ans[sameColor(sequence)] = true;
        }
        for (int i=0; i<6; i++)
            for (int j=0; j<2; j++) {
                int[] sequence = horizontalWays[j];
                for (int k=0; k<5; k++)
                    sequence[k] += 6;
                if (sameColor(sequence)!=-1)
                    ans[sameColor(sequence)] = true;
            }
        for (int i=0; i<6; i++)
            for (int j=0; j<2; j++) {
                int[] sequence = verticalWays[j];
                for (int k=0; k<5; k++)
                    sequence[k] += 1;
                if (sameColor(sequence)!=-1)
                ans[sameColor(sequence)] = true;
            }
        return ans;
    }

    /**
     * Run a multi-player Pentago game until one or both of the players get
     * five stones in a row (vertically, horizontally or diagonal).
     * White begins the game.
     */
    public void runGame() {
        boolean[] flag = {false, false};
        while (!checkBoard()[0] && !checkBoard()[1] && moves<36) {
            while (!Cell.isSelected());
            //isEmpty[] = false;

            if (checkBoard()[0])
                flag[0] = true;
            else if (checkBoard()[1])
                flag[1] = true;

            boolean pass = isEmpty[0] || isEmpty[1] || isEmpty[2] || isEmpty[3];
            Integer[] sectionOptions;
            if (pass)
                sectionOptions = new Integer[]{1, 2, 3, 4, 5};
            else
                sectionOptions = new Integer[]{1, 2, 3, 4};
            int section = JOptionPane.showOptionDialog(null, "Which section of the board you want to rotate? (Press 5 to pass)",
                    "Section to Rotate", 0, 3, null, sectionOptions, sectionOptions[0]);
            if (section!=4) {
                String[] directionOptions = {"Clockwise", "Anticlockwise"};
                int direction = JOptionPane.showOptionDialog(null, "In which direction you want to rotate the block?",
                        "CW or ACW", 0, 3, null, directionOptions, directionOptions[0]);
                rotate(section, (direction == 0) ? "CW" : "ACW");
            }

            moves++;
            Cell.roundFinished();
            remove(player);
            player = new JLabel((Cell.getTurn()==0)? "[Player 1]" : "[Player 2]");
            add(player);
            revalidate();
        }
        String result = (flag[0])? "<<White Wins!>>" : (flag[1])? "<<Black Wins!>>" :
                (checkBoard()[0] && checkBoard()[1])? "<<Draw!>>" :
                        (checkBoard()[0])? "<<White Wins!>>" :
                                (checkBoard()[1])? "<<Black Wins!>>" : "<<Draw!>>";
        JOptionPane.showMessageDialog(null, result);
    }
}