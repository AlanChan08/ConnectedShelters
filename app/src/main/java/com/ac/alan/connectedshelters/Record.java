package com.ac.alan.connectedshelters;

import java.util.Arrays;

/**
 * Created by Alan on 12/11/2015.
 */
public class Record {

    private String recordid;

    private Fields fields;

    public String getRecordid() {
        return recordid;
    }

    public Fields getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordid='" + recordid + '\'' +
                ", fields=" + fields +
                '}';
    }

    private class Fields {

        private String adresse;
        private String code_postal;
        private double[] xy;

        public Fields() {

        }

        public String getAdresse() {
            return adresse;
        }

        public String getCode_postal() {
            return code_postal;
        }


        public double[] getXy() {
            return xy;
        }

        @Override
        public String toString() {
            return "Fields{" +
                    "adresse='" + adresse + '\'' +
                    ", code_postal='" + code_postal + '\'' +
                    ", xy=" + Arrays.toString(xy) +
                    '}';
        }
    }
}
