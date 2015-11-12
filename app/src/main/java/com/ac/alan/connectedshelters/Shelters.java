package com.ac.alan.connectedshelters;

import java.util.List;

/**
 * Created by Alan on 12/11/2015.
 */
public class Shelters {

    private int nhits;
    private List<Record> records;

    public int getNhits() {
        return nhits;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return "Shelters{" +
                "nhits=" + nhits +
                ", records=" + records +
                '}';
    }
}
