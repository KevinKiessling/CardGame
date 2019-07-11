package client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import util.cards.Card;
import util.cards.CardRank;
import util.cards.CardSuite;
import util.protocol.DataPacket;


public class GameController {

    private Client client;

    @FXML
    public HBox hBox;

    @FXML
    public ImageView spadesButton;
    @FXML
    public ImageView heartsButton;
    @FXML
    public ImageView clubsButton;
    @FXML
    public ImageView diamondsButton;

    @FXML
    public Label player2Cards;
    @FXML
    public Label player3Cards;
    @FXML
    public Label player4Cards;

    @FXML
    public ImageView currentCard;
    @FXML
    public ImageView deck;

    @FXML
    public void initialize() {
        System.out.println(this.toString());

        Card c = new Card(CardSuite.CLUBS, CardRank.KING);
        //Card[] cA = {new Card(CardSuite.CLUBS, CardRank.KING)};
        //setHand(cA);
        hBox.getChildren().add(new ImageView(c.getImage()));

        spadesButton.setCursor(Cursor.HAND);
        heartsButton.setCursor(Cursor.HAND);
        clubsButton.setCursor(Cursor.HAND);
        diamondsButton.setCursor(Cursor.HAND);

        Card ca = new Card(CardSuite.SPADES, CardRank.QUEEN);
        currentCard.setImage(ca.getImage());

        /*Platform.runLater(() -> {
            Card[] cards = ((GameState) client.receiveData()).getHand();
            System.out.println(cards[0].toString());
            setHand(cards);
        });*/


    }

    /**
     * Sets the view according to a packet sent from the server
     * @param packet A packet describing the game state
     */
    public void setGameState(DataPacket packet) {

    }

    /**
     * Sets the player's displayed hand.
     * @param playerHand Array of cards
     */
    public void setHand(Card[] playerHand) {
        ObservableList<Node> children = hBox.getChildren();
        children.clear();
        hBox.getChildren().add(new Label("Hello"));


        for (Card c : playerHand) {
            System.out.println(c.toString());
            hBox.getChildren().add(new ImageView(c.getImage()));
        }
    }

    // Buttonactions for the whish cardSuite buttons
    @FXML
    public void spadesClicked() {
        System.out.println("You've wished spades");

    }
    @FXML
    public void heartsClicked() {
        System.out.println("You've wished hearts");

    }
    @FXML
    public void clubsClicked() {
        System.out.println("You've wished clubs");

    }
    @FXML
    public void diamondsClicked() {
        System.out.println("You've wished diamonds");

    }

    public void setClient(Client client) {
        this.client = client;
        this.client.getHandUpdatedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (newValue) {
                System.out.println("New hand is ready to be printed...");
                setHand(client.getCurrentHand());
                //client.setHandUpdatedProperty(false);
            }
        });
    }
}
