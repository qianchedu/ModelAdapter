package com.dsz.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dsz.db.MySQLiteHelper;
import com.dsz.db.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soham on 17-Mar-15.
 */
public class AddNameSql {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private static final String DATABASE_NAME = "addname.db";
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME};

    public AddNameSql(Context context) {
        dbHelper = new MySQLiteHelper(context,DATABASE_NAME);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * 创建数据
     * @param name
     * @return
     */
    public AddNameBean createRestaurant(String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        long insertId = database.insert(MySQLiteHelper.TABLE_RESTAURANTS_ADD, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANTS_ADD,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        AddNameBean newRestaurant = cursorToRestaurant(cursor);
        cursor.close();
        return newRestaurant;
    }

    /**
     * 删除数据
     * @param restaurant
     */
    public void deleteRestaurant(AddNameBean restaurant) {
        long id = restaurant.getId();
        System.out.println("Restaurant deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_RESTAURANTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * 更新数据
     * @param restaurant
     */
    public void updateRestaurant(AddNameBean restaurant) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, restaurant.getName());
        long id = restaurant.getId();
        System.out.println("Restaurant updated with id: " + id);
        database.update(MySQLiteHelper.TABLE_RESTAURANTS, values,
                MySQLiteHelper.COLUMN_ID + " = ?", new String[]
                        { String.valueOf(restaurant.getId()) });
    }

    /**
     * 获取数据
     * @return
     */
    public List<AddNameBean> getAllRestaurants() {
        List<AddNameBean> restaurants = new ArrayList<AddNameBean>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANTS_ADD,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AddNameBean restaurant =  cursorToRestaurant(cursor);
            restaurants.add(restaurant);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return restaurants;
    }

    /**
     *
     * @param cursor
     * @return
     */
    private AddNameBean cursorToRestaurant(Cursor cursor) {
        AddNameBean restaurant = new AddNameBean();
        restaurant.setId(cursor.getLong(0));
        restaurant.setName(cursor.getString(1));
        return restaurant;
    }
}
