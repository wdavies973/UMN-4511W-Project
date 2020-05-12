package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class BarasonaStrategy implements Strategy {

    private Strategy strategy;

    private int turn = 0;
    private final Random rnd = new Random();

    public BarasonaStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {
        int id = root.getPlayer();

        turn++;
        ArrayList<SimulatedNode> actions = root.expand();

        SimulatedNode chosen_Child = actions.get(rnd.nextInt(actions.size()));

            if (turn == 1) {
                for (int i = 0; i < actions.size(); i++) {
                    if (actions.get(i).getAction().getKind() == 8) {
                        Action otherAction;
                        if (id == 0) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    2, false, 18, 18);
                        } else if (id == 1) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    1, false, 18, 1);
                        } else if (id == 2) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 1, 1);
                        } else {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    3, false, 1, 18);
                        }
                        actions.get(i).setAction(otherAction);
                        chosen_Child = actions.get(i);
                        break;
                    }
                }

                submit.add(chosen_Child.getAction());
            } else if (turn == 2) {
                for (int i = 0; i < actions.size(); i++) {
                    if (actions.get(i).getAction().getKind() == 1) {
                        Action otherAction;
                        if (id == 0) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 16, 16);
                        } else if (id == 1) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 16, 3);
                        } else if (id == 2) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 3, 3);
                        } else {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 3, 16);
                        }
                        actions.get(i).setAction(otherAction);
                        chosen_Child = actions.get(i);
                        break;
                    }
                }

                submit.add(chosen_Child.getAction());
            } else if (turn == 3) {
                for (int i = 0; i < actions.size(); i++) {
                    if (actions.get(i).getAction().getKind() == 9) {
                        Action otherAction;
                        if (id == 0) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 14, 14);
                        } else if (id == 1) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    3, false, 14, 5);
                        } else if (id == 2) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    2, false, 5, 5);
                        } else {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    1, false, 5, 14);
                        }
                        actions.get(i).setAction(otherAction);
                        chosen_Child = actions.get(i);
                        break;
                    }
                }

                submit.add(chosen_Child.getAction());
            } else if (turn == 4) {
                for (int i = 0; i < actions.size(); i++) {
                    if (actions.get(i).getAction().getKind() == 3) {
                        Action otherAction;
                        if (id == 0) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    0, false, 13, 12);
                        } else if (id == 1) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    3, false, 12, 6);
                        } else if (id == 2) {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    2, false, 6, 7);
                        } else {
                            otherAction = new Action(actions.get(i).getAction().getPiece(),
                                    1, false, 7, 13);
                        }
                            actions.get(i).setAction(otherAction);
                            chosen_Child = actions.get(i);
                            break;
                    }
                }

                submit.add(chosen_Child.getAction());
            } else {
                strategy.turnStarted(submit, root);
            }
        }

    @Override
    public String getName() {
        return "Barasona";
    }
}

