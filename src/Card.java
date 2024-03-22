import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.HashMap;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png";
        this.show = true;
        this.backImageFileName = "images/card_back.png";
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public String getSuit() {
        return suit;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    public void flipCard() {
        show = !show;
        this.image = readImage();
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<Card> buildDeck(ArrayList<String> removed) {
        ArrayList<Card> deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                if (!removed.contains(s + v)) {
                    Card c = new Card(s, v);
                    deck.add(c);
                }
                else {
                    System.out.println("caught");
                }
            }
        }
        return deck;
    }

    public static ArrayList<Card> buildHand(ArrayList<String> removed) {
        ArrayList<Card> deck = Card.buildDeck(removed);
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 9 && !deck.isEmpty(); i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        return hand;
    }

    public static void replaceCards(ArrayList<Card> replacing, ArrayList<Card> currentHand, ArrayList<String> removed) {
        ArrayList<Card> deck = Card.buildDeck(removed);
        int counter = 0;
        for (int i = 0; i < currentHand.size() && !deck.isEmpty(); i++) {
            int r = (int)(Math.random()*deck.size());
            if (replacing.contains(currentHand.get(i))) {
                currentHand.set(i, deck.remove(r));
                counter++;
            }
        }
        while (counter < replacing.size()) {
            currentHand.remove(replacing.get(counter));
            counter++;
        }
    }
}
