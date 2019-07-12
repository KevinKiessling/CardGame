package server;

import util.cards.Deck;
import util.game.GameState;
import util.protocol.DataPacket;
import util.protocol.DataType;
import util.protocol.Packer;
import util.protocol.messages.NewGame;

public class GameThread implements Runnable {

    private int ID;
    private Player[] players;
    private String[] playerNames;
    private Deck deck = new Deck();
    private IOHandler gameIOHandler;
    private int closed = 0;

    /**
     * Creates a game
     *
     * @param players       all players in the game
     * @param gameIOHandler IOHandler for player inputs
     */
    GameThread(int ID, Player[] players, IOHandler gameIOHandler) {
        this.ID = ID;
        this.players = players;
        this.playerNames = new String[players.length];
        this.gameIOHandler = gameIOHandler;
    }

    private boolean isOver() {
        // TODO: implement game logic
        return false;
    }

    @Override
    public void run() {
        // initializing
        Thread thisThread = Thread.currentThread();
        thisThread.setName("Game-" + this.ID + "\t\t\t>> ");
        System.out.println(thisThread.getName() + "\t\t\t>> Starting new game");

        // prints out all players in the current game
        StringBuilder pString = new StringBuilder();
        for (int i = 0; i < playerNames.length; i++) {
            pString.append("'").append(players[i].getName()).append("'");
            playerNames[i] = players[i].getName();
        }
        System.out.println(thisThread.getName() + "\t\t\t>> [" + pString + "]");

        // send each player the NewGame message
        for (Player p : players) {
            // share player names and their hand
            System.out.println("Sending new game to id: " + p.getID());
            String s = Packer.packData(DataType.NEWGAME, new NewGame(playerNames, deck.deal(5), p.getID()));
            p.send(s);
            //System.out.println(s);
        }

        //String receivedMessage;
        while (!isOver() && players.length != closed) {
            System.out.println(closed);
            System.out.println("Receive");
            String receivedMessage = gameIOHandler.receive();
            DataPacket packet = Packer.getDataPacket(receivedMessage);
            if(packet.getDataType() == DataType.DISCONNECT){
                Player p = players[packet.getPlayerID()];
                //System.out.println(thisThread + " disconnecting ID: " + players[packet.getPlayerID()].getID());
                p.send(Packer.packData(DataType.CONFIRM, true));
                p.disconnect();
                ++closed;
            }
            //System.out.println(thisThread.getName() + "\t\t\t>> " + receivedMessage);


            for (Player p : players) {
                // share player names and their hand
                String s = Packer.packData(DataType.GAMESTATE, new GameState(0, deck.deal(1)));
                p.send(s);
                System.out.println(s);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // TODO: Each player will have their turn here

        }
        System.out.println(thisThread.getName() + "\t\t\t>> Stopping game");
    }
}
