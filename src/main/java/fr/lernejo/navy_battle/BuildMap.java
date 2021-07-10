package fr.lernejo.navy_battle;

import java.util.*;

public class BuildMap {
    private final String[] boxesOnMap = new String[]{ "A1","A2","A3","A4","A5","A6","A7","A8","A9","A10","B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","C1","C2","C3","C4","C5","C6","C7","C8","C9","C10","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","E1","E2","E3","E4","E5","E6","E7","E8","E9","E10","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10", "G1","G2","G3","G4","G5","G6","G7","G8","G9","G10","H1","H2","H3","H4","H5","H6","H7","H8","H9","H10","I1","I2","I3","I4","I5","I6","I7","I8","I9","I10","J1","J2","J3","J4","J5","J6","J7","J8","J9","J10"};
    public final List<Map<String, String>> myMap = new ArrayList<Map<String, String>>();
    public final List<String> alreadyHit = new ArrayList<String>();

    public String getRandomBoxes() {
        String cell = "A1";
        while (alreadyHit.contains(cell)) {
            int rnd = new Random().nextInt(boxesOnMap.length);
            cell = boxesOnMap[rnd];
        }
        setAlreadyHit(cell);
        return cell;
    }

    public void makefirstboat(Map<String, String> myMap1) {
        myMap1.put("A1", "HIT");
        myMap1.put("B1", "HIT");
    }
    public void makeSecondBoat(Map<String, String> myMap1) {
        myMap1.put("H4", "HIT");
        myMap1.put("H5", "HIT");
        myMap1.put("H6", "HIT");
    }
    public void makeThirdBoat(Map<String, String> myMap1) {
        myMap1.put("J7", "HIT");
        myMap1.put("J8", "HIT");
        myMap1.put("J9", "HIT");
    }
    public void makeFourthBoat(Map<String, String> myMap1) {
        myMap1.put("A10", "HIT");
        myMap1.put("B10", "HIT");
        myMap1.put("C10", "HIT");
        myMap1.put("D10", "HIT");
    }

    public void makeFiftBoat(Map<String, String> myMap1) {
        myMap1.put("E3", "HIT");
        myMap1.put("E4", "HIT");
        myMap1.put("E5", "HIT");
        myMap1.put("E6", "HIT");
        myMap1.put("E7", "HIT");
    }

    public BuildMap() {
        for (int i = 0; i < 5; i++) {
            Map<String, String> myMap1 = new HashMap<String, String>();
            if (i == 0) { makefirstboat(myMap1); }
            if (i == 1) { makeSecondBoat(myMap1); }
            if (i == 2) { makeThirdBoat(myMap1); }
            if (i == 3) { makeFourthBoat(myMap1); }
            if (i == 4) { makeFiftBoat(myMap1); }
            myMap.add(i, myMap1);
        }
    }

    public boolean isAlive() {
        for (Map<String, String> map : myMap) {
            if (!map.isEmpty())
                return true;
        }
        return false;
    }

    public void setAlreadyHit(String cell) { alreadyHit.add(cell); };

    public String isTouched(String cell) {
        for (Map<String, String> map : myMap) {
            if (map.get(cell) != null) {
                map.remove(cell);
                return "HIT";
            }
        }
        return "MISS";
    }
}
