package com.example.admin.pagination.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.admin.pagination.Serializables.Consulate;
import com.example.admin.pagination.Serializables.Embassy;
import com.example.admin.pagination.Serializables.Hotline;
import com.example.admin.pagination.Serializables.Istories;
import com.example.admin.pagination.Serializables.RulesOfIncoming;
import com.example.admin.pagination.Serializables.User;
import com.example.admin.pagination.Serializables.VseAndUzery;

/**
 * Created by Admin on 12.07.2016.
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String TABLE_USER="User";
    public static final String USER_ID_COLUMN="user_id";



    public static final String TABLE_NEWS="News";
    public static final String NEWS_ZAGOLOVOK_COLUMN="news_zagolovok";
    public static final String NEWS_TEXT_COLUMN="news_text";


    public static final String TABLE_RULES_OF_INCOMING="Rules_of_incoming";
    public static final String RULES_OF_INCOMING_ZAGOLOVOK_COLUMN="rules_of_zagolovok";
    public static final String RULES_OF_INCOMING_TEXT_COLUMN="rules_of_text";
    public static final String RULES_OF_INCOMING_IMAGE_COLUMN="rules_of_image";


    public static final String TABLE_RULES_OF_MIGRATION="Rules_of_migration";
    public static final String RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN="rules_of_zagolovok";
    public static final String RULES_OF_MIGRATION_TEXT_COLUMN="rules_of_migration_text";
    public static final String RULES_OF_MIGRATION_IMAGE_COLUMN="rules_of_migration_image";


    public static final String  NEWS_JSON_ID_COLUMN="news_id_json";
    public static final String TABLE_LANGUAGE="News";
    public static final String LANGUAGE_COLUMN="news_zagolovok";



    public static final String TABLE_EMBASSY="Embassy";
    public static final String EMBASSY_COUNTRY_COLUMN="embassy_country";
    public static final String EMBASSY_ADDRESS_COLUMN="em_address";
    public static final String EMBASSY_PHONE_COLUMN="em_phone";
    public static final String EMBASSY_SITE_COLUMN="em_site";
    public static final String EMBASSY_JSON_ID_COLUMN="em_id";
    public static final String EMBASSY_EMAIL_COLUMN="em_email";
    public static final String EMBASSY_FAX_COLUMN="em_fax";

    public static final String TABLE_HOT_LINE="Hot_line";
    public static final String HOT_TITLE_COLUMN="hot_zagolovok";
    public static final String HOT_PHONE_COLUMN="hot_phone";
    public static final String HOT_DESCRIPTION_COLUMN="hot_description";

    public static final String TABLE_CONSULATE="Consulate";
    public static final String CONSULATE_REGION_COLUMN="consulate_region";
    public static final String CONSULATE_PHONE_COLUMN="consulate_phone";
    public static final String CONSULATE_ADDRESS_COLUMN="consulate_address";

    public static final String TABLE_FAQ="Faq";
    public static final String FAQ_ANSWER_COLUMN="faq_answer";
    public static final String FAQ_QUESTION_COLUMN="faq_question";

    public static final String TABLE_FORUM="Forum";
    public static final String FORUM_TITLE_COLUMN="forum_title";
    public static final String FORUM_USERNAME_COLUMN="forum_username";
    public static final String FORUM_TEXT_COLUMN="forum_text";

    public static final String TABLE_QUESTION_ANSWER="Qa";
    public static final String QA_USERNAME_COLUMN="qa_username";
    public static final String QA_QUESTION_COLUMN="qa_question";
    public static final String QA_ID_COLUMN="a_id";

    public static final String TABLE_ANSWER="Answer";
    public static final String A_USERNAME_COLUMN="a_username";
    public static final String A_ANSWER_COLUMN="a_question";
    public static final String A_ID_COLUMN="a_id";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NEWS + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                NEWS_ZAGOLOVOK_COLUMN + " text," +
                NEWS_TEXT_COLUMN + " text);");

        db.execSQL("create table " + TABLE_FAQ + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                FAQ_ANSWER_COLUMN + " text," +
                FAQ_QUESTION_COLUMN + " text);");

        db.execSQL("create table " + TABLE_RULES_OF_INCOMING + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                RULES_OF_INCOMING_IMAGE_COLUMN + " text," +
                RULES_OF_INCOMING_ZAGOLOVOK_COLUMN + " text," +
                RULES_OF_INCOMING_TEXT_COLUMN + " text);");

        db.execSQL("create table " + TABLE_HOT_LINE + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                HOT_DESCRIPTION_COLUMN + " text," +
                HOT_PHONE_COLUMN + " text," +
                HOT_TITLE_COLUMN + " text);");


        db.execSQL("create table " + TABLE_RULES_OF_MIGRATION + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                RULES_OF_MIGRATION_IMAGE_COLUMN + " text," +
                RULES_OF_MIGRATION_TEXT_COLUMN + " text," +
                RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN + " text);");

        db.execSQL("create table " + TABLE_CONSULATE + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                CONSULATE_ADDRESS_COLUMN + " text," +
                CONSULATE_PHONE_COLUMN + " text," +
                CONSULATE_REGION_COLUMN + " text);");

        db.execSQL("create table " + TABLE_EMBASSY + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                EMBASSY_ADDRESS_COLUMN + " text," +
                EMBASSY_COUNTRY_COLUMN + " text," +
                EMBASSY_EMAIL_COLUMN + " text," +
                EMBASSY_FAX_COLUMN + " text," +
                EMBASSY_JSON_ID_COLUMN + " text," +
                EMBASSY_PHONE_COLUMN + " text," +
                EMBASSY_SITE_COLUMN + " text);");

        db.execSQL("create table " + TABLE_FORUM + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                FORUM_TITLE_COLUMN + " text," +
                FORUM_TEXT_COLUMN + " text," +
                FORUM_USERNAME_COLUMN + " text);");

        db.execSQL("create table " + TABLE_QUESTION_ANSWER + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                QA_QUESTION_COLUMN + " text," +
                QA_ID_COLUMN + " text," +
                QA_USERNAME_COLUMN + " text);");

        db.execSQL("create table " + TABLE_ANSWER + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                A_ANSWER_COLUMN + " text," +
                A_ID_COLUMN + " text," +
                A_USERNAME_COLUMN + " text);");
        db.execSQL("create table " + TABLE_USER + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +

                USER_ID_COLUMN + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //User>---------------------------------------------------------------------------


    public Cursor getDataUser() {
        return getReadableDatabase().query(TABLE_USER,
                null, null, null,
                null, null, null);
    }
    public void insertUser(String id) {
        ContentValues values = new ContentValues();

        values.put(USER_ID_COLUMN, id);

        getWritableDatabase().insert(TABLE_USER, null, values);

    }
    public void deleteUser() {
        getWritableDatabase().delete(TABLE_USER, null, null);
    }

    public void readDataUser(){
        Cursor cursor=getDataUser();
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Log.e("TAG_DATAHELPER_S",cursor.getString(cursor.getColumnIndex(USER_ID_COLUMN)));
            }
        }
    }




    //News>----------------------------------------------------------------------
    public Cursor getData() {
        return getReadableDatabase().query(TABLE_NEWS,
                null, null, null,
                null, null, null);
    }
    public void insertNews(Istories istories) {
        ContentValues values = new ContentValues();

        values.put(NEWS_ZAGOLOVOK_COLUMN, istories.getNickName());
        values.put(NEWS_TEXT_COLUMN,istories.getText());

        getWritableDatabase().insert(TABLE_NEWS, null, values);

    }
    public void delete() {
        getWritableDatabase().delete(TABLE_NEWS, null, null);
    }
    public void readData(){
        Cursor cursor=getData();
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Log.e("TAG_DATAHELPER",cursor.getString(cursor.getColumnIndex(NEWS_TEXT_COLUMN)));
            }
        }
    }

    //Rules of incoming>------------------------------------------------------------------


    public Cursor getDataROI() {
        return getReadableDatabase().query(TABLE_RULES_OF_INCOMING,
                null, null, null,
                null, null, null);
    }
    public void insertROI(RulesOfIncoming istories) {
        ContentValues values = new ContentValues();

        values.put(RULES_OF_INCOMING_ZAGOLOVOK_COLUMN, istories.getTitle());
        values.put(RULES_OF_INCOMING_TEXT_COLUMN,istories.getText());
        values.put(RULES_OF_INCOMING_IMAGE_COLUMN,istories.getImage());

        getWritableDatabase().insert(TABLE_RULES_OF_INCOMING, null, values);

    }
    public void deleteROI() {
        getWritableDatabase().delete(TABLE_RULES_OF_INCOMING, null, null);
    }


    //Rules of Migration>-----------------------------------------------------------------------


    public Cursor getDataROM() {
        return getReadableDatabase().query(TABLE_RULES_OF_MIGRATION,
                null, null, null,
                null, null, null);
    }
    public void insertROM(RulesOfIncoming ofIncoming) {
        ContentValues values = new ContentValues();

        values.put(RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN, ofIncoming.getTitle());
        values.put(RULES_OF_MIGRATION_TEXT_COLUMN,ofIncoming.getText());
        values.put(RULES_OF_MIGRATION_IMAGE_COLUMN,ofIncoming.getImage());

        getWritableDatabase().insert(TABLE_RULES_OF_MIGRATION, null, values);

    }
    public void deleteROM() {
        getWritableDatabase().delete(TABLE_RULES_OF_MIGRATION, null, null);
    }


    //Embassy>--------------------------------------------------------------------------------------


    public Cursor getDataEmbassy() {
        return getReadableDatabase().query(TABLE_EMBASSY,
                null, null, null,
                null, null, null);
    }
    public void insertEmbassy(Embassy embassy) {
        ContentValues values = new ContentValues();

        values.put(EMBASSY_FAX_COLUMN, embassy.getFax());
        values.put(EMBASSY_ADDRESS_COLUMN,embassy.getRegion());
        values.put(EMBASSY_COUNTRY_COLUMN,embassy.getCountry());
        values.put(EMBASSY_SITE_COLUMN, embassy.getSite());
        values.put(EMBASSY_EMAIL_COLUMN,embassy.getEmail());
        values.put(EMBASSY_JSON_ID_COLUMN,embassy.getId());
        values.put(EMBASSY_PHONE_COLUMN, embassy.getPhoneNumber());

        getWritableDatabase().insert(TABLE_EMBASSY, null, values);

    }
    public void deleteEmbassy() {
        getWritableDatabase().delete(TABLE_EMBASSY, null, null);
    }

    //HOTLINE>-------------------------------------------------------------------------------------
    public Cursor getDataHot() {
        return getReadableDatabase().query(TABLE_HOT_LINE,
                null, null, null,
                null, null, null);
    }
    public void insertHot(Hotline hotline) {
        ContentValues values = new ContentValues();

        values.put(HOT_TITLE_COLUMN, hotline.getTitle());
        values.put(HOT_DESCRIPTION_COLUMN,hotline.getDescription());
        values.put(HOT_PHONE_COLUMN,hotline.getPhoneNumber());

        getWritableDatabase().insert(TABLE_HOT_LINE, null, values);

    }
    public void deleteHot() {
        getWritableDatabase().delete(TABLE_HOT_LINE, null, null);
    }

    //FAQ>------------------------------------------------------------------------------------------

    public Cursor getDataFaq() {
        return getReadableDatabase().query(TABLE_FAQ,
                null, null, null,
                null, null, null);
    }
    public void insertFAQ(Istories istories) {
        ContentValues values = new ContentValues();

        values.put(FAQ_QUESTION_COLUMN, istories.getNickName());
        values.put(FAQ_ANSWER_COLUMN,istories.getText());

        getWritableDatabase().insert(TABLE_FAQ, null, values);

    }
    public void deleteFAQ() {
        getWritableDatabase().delete(TABLE_FAQ, null, null);
    }

    //FORUM>----------------------------------------------------------------------------------------



    public Cursor getDataForum() {
        return getReadableDatabase().query(TABLE_FORUM,
                null, null, null,
                null, null, null);
    }
    public void insertForum(VseAndUzery vseAndUzery) {
        ContentValues values = new ContentValues();

        values.put(FORUM_TITLE_COLUMN, vseAndUzery.getTitle());
        values.put(FORUM_TEXT_COLUMN,vseAndUzery.getText());
        values.put(FORUM_USERNAME_COLUMN,vseAndUzery.getUsername());

        getWritableDatabase().insert(TABLE_FORUM, null, values);

    }
    public void deleteForum() {
        getWritableDatabase().delete(TABLE_FORUM, null, null);
    }


    //QA>--------------------------------------------------------------------------------------------
    public Cursor getDataQAById() {
        return getReadableDatabase().query(TABLE_QUESTION_ANSWER, null,null, null, null, null, null);
    }

    public Cursor getDataQA() {
        return getReadableDatabase().query(TABLE_QUESTION_ANSWER,
                null, null, null,
                null, null, null);
    }
    public void insertQA(VseAndUzery vseAndUzery) {
        ContentValues values = new ContentValues();

        values.put(QA_ID_COLUMN,vseAndUzery.getTitle());
        values.put(QA_QUESTION_COLUMN,vseAndUzery.getText());
        values.put(QA_USERNAME_COLUMN,vseAndUzery.getUsername());

        getWritableDatabase().insert(TABLE_QUESTION_ANSWER, null, values);

    }
    public void deleteQA() {
        getWritableDatabase().delete(TABLE_QUESTION_ANSWER, null, null);
    }


    //Answer>__________________________________________________________________________________


    public Cursor getDataAById(String id) {
        return getReadableDatabase().query(TABLE_ANSWER, null,A_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)}, null, null, null);
    }

    public Cursor getDataA() {
        return getReadableDatabase().query(TABLE_ANSWER,
                null, null, null,
                null, null, null);
    }
    public void insertA(VseAndUzery vseAndUzery,String id) {
        ContentValues values = new ContentValues();

        values.put(A_ID_COLUMN,id);
        values.put(A_ANSWER_COLUMN,vseAndUzery.getText());
        values.put(A_USERNAME_COLUMN,vseAndUzery.getUsername());

        getWritableDatabase().insert(TABLE_ANSWER, null, values);

    }
    public void deleteA() {
        getWritableDatabase().delete(TABLE_ANSWER, null, null);
    }
    public void deleteAById(String id){
        getWritableDatabase().delete(TABLE_ANSWER,A_ID_COLUMN + " = " + id, null);
    }





    //CONSULATE>------------------------------------------------------------------------------------

    public Cursor getDataConsulate() {
        return getReadableDatabase().query(TABLE_CONSULATE,
                null, null, null,
                null, null, null);
    }
    public void insertConsulate(Consulate consulate) {
        ContentValues values = new ContentValues();

        values.put(CONSULATE_ADDRESS_COLUMN, consulate.getAddress());
        values.put(CONSULATE_PHONE_COLUMN,consulate.getPhoneNumber());
        values.put(CONSULATE_REGION_COLUMN,consulate.getRegion());

        getWritableDatabase().insert(TABLE_CONSULATE, null, values);

    }
    public void deleteConsulate() {
        getWritableDatabase().delete(TABLE_CONSULATE, null, null);
    }

}
