package fr.lernejo.navy_battle.model;

import org.jetbrains.annotations.NotNull;

public class Fire {
    @NotNull
    private final boolean shipLeft;
    @NotNull
    private final String consequence;

    public Fire(boolean shipLeft, String consequence) {
        this.shipLeft = shipLeft;
        this.consequence = consequence;
    }

    public boolean getShipLeft() {
        return shipLeft;
    }

    public String getConsequence() {
        return consequence;
    }
}
