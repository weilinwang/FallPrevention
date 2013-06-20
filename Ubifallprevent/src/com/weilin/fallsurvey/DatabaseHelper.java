package com.weilin.fallsurvey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles creation of and access to the database, which is stored in surveys.db.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "surveys.db";

	private static final String CREATE_TABLE_SURVEYS = "CREATE TABLE surveys (survey_id INTEGER PRIMARY KEY, title TEXT UNIQUE NOT NULL, intro_text TEXT)";
	private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE questions (question_id INTEGER PRIMARY KEY, survey_id INTEGER, section TEXT, question_order INTEGER, type TEXT, prompt TEXT)";
	private static final String CREATE_TABLE_QUESTION_OPTIONS = "CREATE TABLE question_options (question_id INTEGER, option_order INTEGER, answer_text TEXT, text_field_label TEXT)";
	SQLiteDatabase db;
	/**
	 * Create a helper object to create, open, and/or manage a database. This method always returns
	 * very quickly. The database is not actually created or opened until one of
	 * getWritableDatabase() or getReadableDatabase() is called.
	 * 
	 * @param context
	 *        To open or create the database
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(CREATE_TABLE_SURVEYS);
			db.execSQL(CREATE_TABLE_QUESTIONS);
			db.execSQL(CREATE_TABLE_QUESTION_OPTIONS);

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		initializeSurveys(db);
		initializeAnswerTables(db);
//		db.close();	// crashes on startup
	}

	/**
	 * Writes the default set of surveys to the database. Refer to the CREATE_TABLE_* strings in
	 * this class for the structure of the tables. Survey titles must be unique.
	 * 
	 * @param db
	 *        The database to which the surveys will be written.
	 */
	private void initializeSurveys(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();

		// test
		
		cv.put("survey_id", 1);
		cv.put("title", "2ed Fall Risk Assessment");
		cv.put("intro_text", "Morse Fall Risk Assessment:This is one of the most widely used fall risk assessment scales available. It is a reliable and valid measure of fall risk.");
		db.insert("surveys", null, cv);
		
		
				cv.put("survey_id", 0);
				cv.put("title", "Morse Fall Risk Assessment");
				cv.put("intro_text", "Morse Fall Risk Assessment:This is one of the most widely used fall risk assessment scales available. It is a reliable and valid measure of fall risk.");
				db.insert("surveys", null, cv);
				
				cv.clear();
				cv.put("survey_id", 0);
				cv.put("question_id", 1);
				cv.put("question_order", 1);
				cv.put("type", Question.QUESTION_MC);
				cv.put("prompt", "History of Falls:");
				db.insert("questions", null, cv);

				cv.clear();
				cv.put("survey_id", 0);
				cv.put("question_id", 2);
				cv.put("question_order", 2);
				cv.put("type", Question.QUESTION_MC);
				cv.put("prompt", "Secondary Diagnosis:");
				db.insert("questions", null, cv);

				
				cv.clear();
				cv.put("survey_id", 0);
				cv.put("question_id", 3);
				cv.put("question_order", 3);
				cv.put("type", Question.QUESTION_MC);
				cv.put("prompt", "IV / Heparin Lock:");
				db.insert("questions", null, cv);
				
				
				cv.clear();
				cv.put("survey_id", 0);
				cv.put("question_id", 4);
				cv.put("question_order", 4);
				cv.put("type", Question.QUESTION_MC);
				cv.put("prompt", "Mental Status:");
				db.insert("questions", null, cv);

				
				cv.clear();
				cv.put("survey_id", 0);
				cv.put("question_id", 5);
				cv.put("question_order", 5);
				cv.put("type", Question.QUESTION_MC);
				cv.put("prompt", "Ambulatory Aid:");
				db.insert("questions", null, cv);
				
				cv.clear();
				cv.put("survey_id", 0);
				cv.put("question_id", 6);
				cv.put("question_order", 6);
				cv.put("type", Question.QUESTION_MC);
				cv.put("prompt", "Gait / Transferring:");
				db.insert("questions", null, cv);
				
				
				
				cv.clear();
				
				cv.put("question_id", 1);
				cv.put("option_order", 1);
				cv.put("answer_text", "Yes");
				db.insert("question_options", null, cv);

				cv.clear();
				
				cv.put("question_id", 1);
				cv.put("option_order", 2);
				cv.put("answer_text", "No");
				db.insert("question_options", null, cv);

				
				cv.clear();
				
				cv.put("question_id", 2);
				cv.put("option_order", 1);
				cv.put("answer_text", "Yes");
				db.insert("question_options", null, cv);

				cv.clear();
				
				cv.put("question_id", 2);
				cv.put("option_order", 2);
				cv.put("answer_text", "No");
				db.insert("question_options", null, cv);

				
				
				cv.clear();
				
				cv.put("question_id", 3);
				cv.put("option_order", 1);
				cv.put("answer_text", "Yes");
				db.insert("question_options", null, cv);

				cv.clear();
			
				cv.put("question_id", 3);
				cv.put("option_order", 2);
				cv.put("answer_text", "No");
				db.insert("question_options", null, cv);

				
				
				cv.clear();
				
				cv.put("question_id", 4);
				cv.put("option_order", 1);
				cv.put("answer_text", "Forgets Limitations");
				db.insert("question_options", null, cv);

				cv.clear();
				
				cv.put("question_id",4);
				cv.put("option_order", 2);
				cv.put("answer_text", "Knows own limits or Overestimates");
				db.insert("question_options", null, cv);
				
				
                 cv.clear();
				
				cv.put("question_id", 5);
				cv.put("option_order", 1);
				cv.put("answer_text", "Furniture");
				db.insert("question_options", null, cv);

				cv.clear();
				
				cv.put("question_id",5);
				cv.put("option_order", 2);
				cv.put("answer_text", "Crutches/ Cane/Walker");
				db.insert("question_options", null, cv);
				
				
				cv.clear();
				cv.put("question_id",5);
				cv.put("option_order", 3);
				cv.put("answer_text", "None/Bed Rest/Nurse assist");
				db.insert("question_options", null, cv);
				
				
				
				cv.put("question_id", 6);
				cv.put("option_order", 1);
				cv.put("answer_text", "Normal/bed rest/wheelchair");
				db.insert("question_options", null, cv);

				cv.clear();
				
				cv.put("question_id",6);
				cv.put("option_order", 2);
				cv.put("answer_text", "Weakr");
				db.insert("question_options", null, cv);
				
				
				cv.clear();
				cv.put("question_id",6);
				cv.put("option_order", 3);
				cv.put("answer_text", "Impaired");
				db.insert("question_options", null, cv);

	}
		

	/**
	 * Creates tables to hold the results of each survey.
	 * 
	 * @param db
	 *        The database to which the empty answer tables will be written.
	 */
	private void initializeAnswerTables(SQLiteDatabase db) {
		Cursor c = db.query("surveys", new String[] { "count(*)" }, null, null, null, null, null);
		c.moveToFirst();
		int numSurveys = c.getInt(0);
		c.close();

		for(int surveyIterator = 0; surveyIterator < numSurveys; surveyIterator++) {
			initializeAnswerTable(db, surveyIterator);
		}

			
//		db.close();		// can't close DB here because it is still needed during startup sequence
	}
		
	public static void initializeAnswerTable(SQLiteDatabase db, int surveyIndex) {
		Survey survey = SurveyDBHelper.readSurvey(db, surveyIndex);

		/*
		 * Build a table to hold survey answers. Each writing question should have one column to
		 * store its answer. Each multiple choice should have one column per answer, plus one
		 * for each textField associated with an answer option. Checkbox questions should have
		 * one column per check, plus one column for each textField associated with an answer
		 * option. THIS IS SO UGLY
		 */

		String tableName = "answers_" + (surveyIndex);
		String createTable = "CREATE TABLE " + tableName + " (user_id TEXT UNIQUE NOT NULL COLLATE NOCASE, ";

		for(int questionIterator = 0; questionIterator < survey.getSize(); questionIterator++) {
			String className = survey.getQuestion(questionIterator).getClass().getName();

			if(className.endsWith(".QuestionWriting")) {
				// example: "CREATE TABLE answers1 (q1 TEXT,
				createTable += "q" + (questionIterator + 1) + " TEXT, ";
			}

			else if(className.endsWith(".QuestionMC")) {
				// example: "CREATE TABLE answers1 (q1 TEXT, q1_1_text_field TEXT,
				createTable += "q" + (questionIterator + 1) + " TEXT, ";
				for(int choiceIterator = 0; choiceIterator < ((QuestionMC) survey.getQuestion(questionIterator)).getChoiceCount(); choiceIterator++) {
					if(((QuestionMC) survey.getQuestion(questionIterator)).textFieldAtIndex(choiceIterator)) {
						createTable += "q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + "_text_field TEXT, ";
					}
				}
			}

			else if(className.endsWith(".QuestionCheckbox")) {
				// example: "CREATE TABLE answers1 (q1_1 TEXT, q1_1_text_field TEXT
				for(int choiceIterator = 0; choiceIterator < ((QuestionCheckbox) survey.getQuestion(questionIterator)).getChoiceCount(); choiceIterator++) {
					createTable += "q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + " TEXT, ";
					if(((QuestionCheckbox) survey.getQuestion(questionIterator)).textFieldAtIndex(choiceIterator)) {
						createTable += "q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + "_text_field TEXT, ";
					}
				}
			}
		}
		createTable = createTable.substring(0, createTable.length() - 2) + ")";

		// TODO make sure this actually does anything/works
		try {
			db.execSQL(createTable);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	  
	

}
