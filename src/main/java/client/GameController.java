package client;

import javafx.application.Platform;
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

        for (Card c : playerHand) {
            System.out.println(c.toString());
            hBox.getChildren().add(new ImageView(c.getImage()));
        }
    }

    // Buttonactions for the whish cardSuite buttons
    @FXML
    public void spadesClicked() {
        client.wishCard(CardSuite.SPADES);
    }
    @FXML
    public void heartsClicked() {
        client.wishCard(CardSuite.HEARTS);

    }
    @FXML
    public void clubsClicked() {
        client.wishCard(CardSuite.CLUBS);

    }
    public void diamondsClicked() {
        client.wishCard(CardSuite.DIAMONDS);

    }

    public void setClient(Client client) {
        this.client = client;
        this.client.getHandUpdatedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (newValue) {
                Platform.runLater(() -> {
                    System.out.println("New hand is ready to be printed...");
                    setHand(client.getCurrentHand());
                    client.setHandUpdatedProperty(false);
                });

            }
        });
    }
}
