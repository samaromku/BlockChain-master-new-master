package ru.savchenko.andrey.blockchain.di;

/**
 * Created by Andrey on 25.09.2017.
 */

public class ComponentManager {
    private static BuyOrSellComponent buyOrSellComponent;

    public static BuyOrSellComponent getBuyOrSellComponent() {
        return buyOrSellComponent;
    }

    public static void init(){
        buyOrSellComponent = DaggerBuyOrSellComponent
                .builder()
                .build();
    }
}
