/*
 * ActivityDiary
 *
 * Copyright (C) 2017-2017 Raphael Mack http://www.raphael-mack.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.rampro.activitydiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LocalDBHelper extends SQLiteOpenHelper {
    public static final String ACTIVITY_DB_TABLE = "activity";
    public static final String ACTIVITY_ALIAS_DB_TABLE = "activity_alias";
    public static final String CONDITION_DB_TABLE = "condition";
    public static final String CONDITIONS_DB_TABLE = "conditions";
    public static final String DIARY_DB_TABLE = "diary";

    LocalDBHelper(Context context) {
        super(context, "de.rampro.activitydiary", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* version 1 */
        /* TODO: should we add a constraint to forbid name reuse accross tables (even no alias to an existing name) */
        db.execSQL("CREATE TABLE " +
                ACTIVITY_DB_TABLE +
                "(" +
                " _ID INTEGER PRIMARY KEY ASC, " +
                " NAME TEXT NOT NULL UNIQUE," +
                " PARENT INTEGER " +
                ");");

        db.execSQL("CREATE TABLE " +
                ACTIVITY_ALIAS_DB_TABLE +
                "(" +
                " ACT_ID INTEGER NOT NULL, " +
                " NAME TEXT NOT NULL UNIQUE," +
                " FOREIGN KEY(ACT_ID) REFERENCES activity(_ID) " +
                ");");

        db.execSQL("CREATE TABLE " +
                CONDITION_DB_TABLE +
                "(" +
                " _ID INTEGER PRIMARY KEY ASC, " +
                " NAME TEXT NOT NULL UNIQUE, " +
                " TYPE TEXT, " +
                " PARAMETER TEXT " +
                ");");

        db.execSQL("CREATE TABLE " +
                CONDITIONS_DB_TABLE +
                "(" +
                " ACT_ID INTEGER NOT NULL, " +
                " COND_ID INTEGER NOT NULL, " +
                " FOREIGN KEY(ACT_ID) REFERENCES activity(_ID), " +
                " FOREIGN KEY(COND_ID) REFERENCES condition(_ID) " +
                ");");
        /* TODO: add table diary DIARY_DB_TABLE */

        /* TODO: remove dummy entry */
        db.execSQL("INSERT INTO " +
                ACTIVITY_DB_TABLE +
                "(NAME)" +
                " VALUES " +
                " ('Gardening'), ('Sleeping');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
         * you can use ALTER TABLE to rename the old table, then create the new table and then
         * populate the new table with the contents of the old table.
         */
        if(newVersion > 1){
            /* upgrade from 1 to 2 */
        }
        if(newVersion > 2){
            /* upgrade from 2 to 3 */
        }
        if(newVersion > 1){
            throw new RuntimeException("Database upgrade to version " + newVersion + " nyi.");
        }
    }
}
