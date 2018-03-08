package com.codecool.enterprise.overcomplicated.Utility;

import com.codecool.enterprise.overcomplicated.model.State;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

@Service
public class TicTacToeGameService {

    @Autowired
    ServiceUtility serviceUtility;

    private State winCheck(State[][] board) {
        State x = checkRow(board);
        if (x != null) return x;
        State x1 = checkDiagonalRight(board);
        if (x1 != null) return x1;
        State x2 = checkDiagonalLeft(board);
        if (x2 != null) return x2;
        return null;
    }

    private State checkDiagonalLeft(State[][] board) {
        int countAi = 0;
        int countPlayer = 0;
        //check diagonal left
        for (int i = 0, j = 2; i < 3 ; i++, j--) {
            if (board[i][j] == State.Player) countPlayer++;
            else if(board[i][j] == State.AI) countAi++;
        }
        if (countAi == 3) return State.AI;
        else if (countPlayer == 3) return State.Player;
        return null;
    }

    private State checkDiagonalRight(State[][] board) {
        int countAi = 0;
        int countPlayer = 0;
        //check diagonal right
        for (int i = 0, j= 0; i < 3; i++, j++) {
            if (board[i][j] == State.AI) countPlayer++;
            else if(board[i][j] == State.Player) countAi++;
        }
        if (countAi == 3) return State.AI;
        else if (countPlayer == 3) return State.Player;
        return null;
    }

    private State checkRow(State[][] board) {
        for (int i = 0; i < 3; i++) {
            int countAi = 0;
            int countPlayer = 0;
            for (int j = 0; j < 3; j++) {

                if(board[i][j] == State.Player) countPlayer++;
                else if(board[i][j] == State.AI)countAi++;
            }
            if (countPlayer == 3) return State.Player;
            else if (countAi == 3) return State.AI;
        }
        return null;
    }

    private int getAIMove(String boardState) {
        try {
            URL obj = new URL("http://tttapi.herokuapp.com/api/v1/" + boardState + "/O");
            Map<String, Object> response = serviceUtility.getResponse(obj);
            Double recommendation = (Double) response.get("recommendation");
            return recommendation != null ? recommendation.intValue() : -1;
        } catch (IOException e) {
            return new Random().nextInt(9);
        }
    }

    public String handleMoves(@ModelAttribute("move") int move, @ModelAttribute("game") TictactoeGame game, Model model) {
        System.out.println("Before player: " + game.toStringState());
        if (game.playerMove(move)) {
            if (winCheck(game.getBoard()) == State.Player) {
                System.out.println("Player win");
                game.setOver(true);
                game.setWinner(State.Player);
            }
            else if (game.boardIsFull()) {
                System.out.println("Board is full");
                game.setOver(true);
            }
            System.out.println("After player and before AI: " + game.toStringState());
            int aiMove = getAIMove(game.toStringState());
            if (aiMove != -1) game.aiMove(aiMove);
            System.out.println("After round: " + game.toStringState());
            if (winCheck(game.getBoard()) == State.AI) {
                game.setOver(true);
                game.setWinner(State.AI);
            } else if (game.boardIsFull()) {
                game.setOver(true);
                System.out.println("Board is full");
            }
        }
        return "redirect:/game";
    }

}
