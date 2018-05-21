package com.handen.handenview.database;

/**
 * Created by Vanya on 21.05.2018.
 */

public class DbSchema {
    public static final class BaseTable {
        public static final String NAME = "base";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String MIN = "min";
            public static final String MAX = "max";
            public static final String UNITS = "units";
        }
    }
}
