package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.Utility.ServiceUtility;
import com.codecool.enterprise.overcomplicated.Utility.TicTacToeGameService;
import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.State;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Controller
@SessionAttributes({"player", "game"})
public class GameController {

    @Autowired
    ServiceUtility serviceUtility;

    @Autowired
    TicTacToeGameService gameService;

    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
    }

    @ModelAttribute("game")
    public TictactoeGame getGame() {
        return new TictactoeGame();
    }

    @ModelAttribute("avatar_uri")
    public String getAvatarUri(@ModelAttribute("player") Player player) {
        if (player.getAvatarURI() == null) {
            player.setAvatarURI(serviceUtility.getAvatar());
        }
        return player.getAvatarURI();
    }

    @GetMapping(value = "/")
    public String welcomeView(@ModelAttribute Player player) {
        return "welcome";
    }

    @PostMapping(value="/changeplayerusername")
    public String changPlayerUserName(@ModelAttribute Player player) {
        System.out.println(player.getUserName());
        return "redirect:/game";
    }

    @PostMapping(value = "/changeavatar")
    public String changeAvatar(@ModelAttribute Player player) {
        player.setAvatarURI(serviceUtility.getAvatar());
        return "redirect:/game";
    }

    @GetMapping(value = "/game")
    public String gameView(@ModelAttribute("player") Player player, @ModelAttribute("game")TictactoeGame game, Model model) {
        serviceUtility.getChuckNorrisJoke(model);
        serviceUtility.getComic(model);
        return "game";
    }

    @RequestMapping(value = "/game-move", method = RequestMethod.GET)
    public String gameMove(@ModelAttribute("player") Player player, @ModelAttribute("move") int move, @ModelAttribute("game") TictactoeGame game, Model model) {
        return gameService.handleMoves(move, game, model);
    }

    @RequestMapping(value = "/newGame", method = RequestMethod.POST)
    public String generateNewGame(Model model){
        model.addAttribute("game", new TictactoeGame());
        return "redirect:/game";
    }

}
