import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.HashMap;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand, selected;
    private ArrayList<String> removed;
    private Rectangle button;

    public DrawPanel() {
        button = new Rectangle(147, 300, 160, 26);
        this.addMouseListener(this);
        removed = new ArrayList<String>();
        hand = Card.buildHand(removed);
        selected = new ArrayList<Card>();

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 120;
        int y = 10;
        int count = 0;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;
            count++;
            if (count % 3 == 0) {
                y += 100;
                x = 120;
            }
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("GET NEW CARDS", 150, 320);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
        g.drawString("Cards Left: " + (52 - removed.size()), 147, 350);
        g.drawString("Possible Moves: " + possibleMovies(), 147, 375);
        if (hand.isEmpty()) {
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("YOU WON!", 120, 200);
        }
    }

    public void remove() {
        if (selected.size() == 2) {
            int val1 = 0;
            int val2 = 0;


            if (selected.get(0).getValue().equals("A")) {
                val1 = 1;
            }
            else {
                try {
                    val1 = Integer.parseInt(selected.get(0).getValue());
                } catch (NumberFormatException e) {}
            }


            if (selected.get(1).getValue().equals("A")) {
                val2 = 1;
            }
            else {
                try {
                    val2 = Integer.parseInt(selected.get(1).getValue());
                } catch (NumberFormatException e) {}
            }


            if (val1 + val2 == 11) {
                removed.add(selected.get(0).getSuit() + selected.get(0).getValue());
                removed.add(selected.get(1).getSuit() + selected.get(1).getValue());
                ArrayList<String> temp = new ArrayList<String>();
                temp = (ArrayList<String>) removed.clone();
                for (Card card: hand) {
                    temp.add(card.getSuit() + card.getValue());
                }
                Card.replaceCards(selected, hand, temp);
                selected.clear();
                System.out.println(removed);
            }
        }
        if (selected.size() == 3) {
            String valid = "KQJ";
            boolean works = true;
            for (Card card: selected) {
                if (!valid.contains(card.getValue())) {
                    works = false;
                }
            }
            if (works) {
                removed.add(selected.get(0).getSuit() + selected.get(0).getValue());
                removed.add(selected.get(1).getSuit() + selected.get(1).getValue());
                removed.add(selected.get(2).getSuit() + selected.get(2).getValue());
                ArrayList<String> temp = new ArrayList<String>();
                temp = (ArrayList<String>) removed.clone();
                for (Card card: hand) {
                    temp.add(card.getSuit() + card.getValue());
                }
                Card.replaceCards(selected, hand, temp);
                selected.clear();
                System.out.println(removed);
            }
        }
    }

    private int possibleMovies() {
        int moves = 0;
        int faces = 0;
        int val1 = 0;
        int val2 = 0;
        String validFace = "KQJ";
        for (int i = 0; i < hand.size(); i++) {
            if (validFace.contains(hand.get(i).getValue())) {
                faces++;
            }
            if (hand.get(i).getValue().equals("A")) {
                val1 = 1;
            }
            else {
                try {
                    val1 = Integer.parseInt(hand.get(i).getValue());
                } catch (NumberFormatException e) {}
            }
            for (int x = i + 1; x < hand.size(); x++) {
                try {
                    if (hand.get(x).getValue().equals("A")) {
                        val2 = 1;
                    }
                    else {
                        try {
                            val2 = Integer.parseInt(hand.get(x).getValue());
                        } catch (NumberFormatException e) {}
                    }
                } catch (NumberFormatException e) {}
                if (val1 + val2 == 11) {
                    moves++;
                }
            }
        }
        System.out.println(faces);
        faces -= 2;
        if (faces > 0) {
            moves += faces;
        }
        return moves;
    }
    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                hand = Card.buildHand(removed);
                System.out.println(hand);
                selected.clear();
            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                    if (selected.contains(hand.get(i))) {
                        selected.remove(hand.get(i));
                    }
                    else {
                        selected.add(hand.get(i));
                    }
                    System.out.println(selected);
                }
            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                    if (selected.contains(hand.get(i))) {
                        selected.remove(hand.get(i));
                    }
                    else {
                        selected.add(hand.get(i));
                    }
                    System.out.println(selected);
                }
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}