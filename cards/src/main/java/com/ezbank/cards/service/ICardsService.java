package com.ezbank.cards.service;


import com.ezbank.cards.dto.CardsDTO;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDTO fetchCard(String mobileNumber);

    boolean updateCard(CardsDTO cardsDto);
    
    boolean deleteCard(String mobileNumber);

}
