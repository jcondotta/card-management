package com.blitzar.cards.web.controller;

public interface CardAPIConstants {

    String BASE_PATH_API_V1_MAPPING = "/api/v1/cards/";

    String CARD_V1_MAPPING = BASE_PATH_API_V1_MAPPING + "card-id/{card-id}";
    String GET_CARDS_V1_MAPPING = BASE_PATH_API_V1_MAPPING + "bank-account-id/{bank-account-id}";

}
