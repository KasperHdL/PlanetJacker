package com.kasperhdl.planetjacker.states;

import com.kasperhdl.planetjacker.World;
import com.kasperhdl.planetjacker.entities.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by @Kasper on 14/03/2015
 * <p/>
 * Description:
 * Game State when the game run this will be updating
 * <p/>
 * Usage:
 * ---
 */

public class GameplayState implements GameState {

    public static final float FPS = 60;

    GameContainer container;
    StateBasedGame sbg;

    World world;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.sbg = stateBasedGame;

        world = new World();

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        world.render(g);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int deltaInMs) throws SlickException {
        float deltaTime = (float)deltaInMs/FPS;
        world.update(deltaTime);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        System.out.println("entering game state");
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void controllerLeftPressed(int i) {

    }

    @Override
    public void controllerLeftReleased(int i) {

    }

    @Override
    public void controllerRightPressed(int i) {

    }

    @Override
    public void controllerRightReleased(int i) {

    }

    @Override
    public void controllerUpPressed(int i) {

    }

    @Override
    public void controllerUpReleased(int i) {

    }

    @Override
    public void controllerDownPressed(int i) {

    }

    @Override
    public void controllerDownReleased(int i) {

    }

    @Override
    public void controllerButtonPressed(int i, int i1) {

    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int key, char c) {
        if(key == Input.KEY_SPACE) {
            try {
                init(container, sbg);

            } catch (SlickException e) {

            }
        }

        world.player.keyPressed(key);
    }

    @Override
    public void keyReleased(int key, char c) {
        world.player.keyReleased(key);
    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
