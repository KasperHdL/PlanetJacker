package com.kasperhdl.planetjacker;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kasperhdl.planetjacker.states.GameplayState;
import com.kasperhdl.planetjacker.states.MainMenuState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 580;

	public Game(String gamename){
		super(gamename);
	}


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {

        GameState mainMenu = new MainMenuState();
        GameState gameRunning = new GameplayState();

        addState(mainMenu);
        addState(gameRunning);
    }

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Rouge Gauntlet"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}