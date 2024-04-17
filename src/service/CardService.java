package service;

import model.Card;
import model.Product;

import java.util.ArrayList;

public class CardService {
    private ArrayList<Card> cards;

    public CardService(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
