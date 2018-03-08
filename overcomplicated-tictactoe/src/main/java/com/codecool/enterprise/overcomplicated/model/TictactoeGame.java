package com.codecool.enterprise.overcomplicated.model;

public class TictactoeGame {

    private State[][] board;
    private boolean isOver;

    private State winner;

    public TictactoeGame() {
        winner  = State.Empty;
        isOver = false;
        board = new State[3][3];
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                board[i][j] = State.Empty;
            }
        }
    }

    public boolean boardIsFull() {
        return !toStringState().contains("-");
    }

    public String toStringState() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                builder.append(board[i][j].getValue());
            }
        }
        return builder.toString();
    }

    public boolean playerMove(int move) {
        if (board[move/3][move%3] == State.Empty) {
            board[move/3][move%3] = State.Player;
            System.out.println(toStringState());
            return true;
        }
        return false;
    }

    public void aiMove(int move) {
        board[move/3][move%3] = State.AI;
    }

    public State[][] getBoard() {
        return board;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isOver() {
        return isOver;
    }

    public State getWinner() {
        return winner;
    }

    public void setWinner(State winner) {
        this.winner = winner;
    }
}
