package com.company;

import javax.swing.*;
import java.awt.*;

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

    /**
     * Creat a new board with adding 36 cells to it.
     */
    public Board() {

        cells = new Cell[6][6];

        setLayout(new FlowLayout());

        for (int i=0; i<6; i++) {
            for (int j = 0; j < 6; j++) {
                Cell c = new Cell(i, j);
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
        add(new JLabel("Player 1]"));
    }

    public void rotateCW(int section) {

    }

    public void rotateACW(int section) {

    }
}