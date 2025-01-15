package com.example.jsonparser.util;

public enum TopLadder {
    
    MARTSENIUK("Maksim Martseniuk"),
    RAPTUNOVICH("Aleksei Raptunovich"),
    NICK_LATKOVICH("NickLatkovich"),
    KPPY("K1ppy"),
    ROMAN_KOHAN("Роман Кохан"),
    ROSTISLAV_BROZHAT("Rostislav Brozhat"),
    OMG_PRO_FUNNY("OMGProfunny"),
    OMG_SUMMIE("[OMG]Summie"),
    HOGAR("Hogar"),
    VALENTINE_NOVIKOV("Valentine Novikov"),
    VALERY_ZUBOVICH("Valery Zubovich"),
    SLAVA_BELEZEKO("Slava Belezeko"),
    MAXIM_SHAGOIKO("Maxim Shagoiko"),
    DZIANIS_CHUYANAU("Dzianis Chuyanau"),
    ILYA_BOGDAN("Ilya Bogdan"),
    BU_RIAL("BuRial63"),
    AKEL("Akel"),
    Ilya_LASHKIN("Илья Лашкин"),
    EGOR_SNYTKO("Egor Snytko");

    String displayName;

    TopLadder(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
