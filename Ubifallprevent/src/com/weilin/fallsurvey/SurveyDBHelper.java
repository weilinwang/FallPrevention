package com.weilin.fallsurvey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.weilin.fall.R;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;

public class SurveyDBHelper {
	public SurveyDBHelper() {}

	
	
	
	
/*	public static Cursor getContact(Context context) throws SQLException 
    {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
	 
       Cursor mCursor =db.query( "answers_0" , new String[] { "USER_ID", "Q1" ,"Q2" , "Q3", "Q4","Q5", "Q6"}, "USER_ID"  + "=" + "wwlin" , null, null, null , null );
                                       
                                   
      if (mCursor != null) {
          mCursor.moveToFirst();
      }
      return mCursor;
      

    }*/
	
	
	/**
	 * Get a list of all of the titles of surveys stored in the DB.
	 * 
	 * @param context
	 *        A context
	 * @return A list of survey titles
	 */
	public static ArrayList<String> getSurveyTitles(Context context) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		Cursor c = db.query("surveys", new String[] { "title" }, null, null, null, null, "survey_id");
		c.moveToFirst();
		ArrayList<String> surveyNames = new ArrayList<String>();
		while(!c.isAfterLast()) {
			surveyNames.add(c.getString(0));
			c.moveToNext();
		}

		db.close();
		return surveyNames;
	}

	public static int getSurveyId(Context context, String surveyTitle) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		Cursor c = db.query("surveys", new String[] { "survey_id" }, "title=?", new String[] { surveyTitle }, null, null, null);
		if(c.getCount() > 0) {
			c.moveToFirst();
			int id = c.getInt(0);
			c.close();
			db.close();
			return id;
		} else {
			c.close();
			db.close();
			return -1;
		}
	}

	public static int getSurveyCount(Context context) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		Cursor c = db.rawQuery("select count(*) from surveys", null);
		c.moveToFirst();
		int surveyCount = c.getInt(0);

		db.close();
		return surveyCount;
	}
	
	

	

	public static int getMaxSurveyIndex(Context context) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		Cursor c = db.rawQuery("select survey_id from surveys order by survey_id desc", null);
		c.moveToFirst();
		int maxSurveyIndex = c.getInt(0);

		db.close();
		return maxSurveyIndex;
	}

	/**
	 * Check whether a user has already submitted a set of answers for a survey.
	 * 
	 * @param context
	 *        A context
	 * @param surveyId
	 *        The survey to check
	 * @param userId
	 *        The user to check
	 * @return True if the user has already answered the survey, or false otherwise
	 */
	public static boolean isAlreadyAnswered(Context context, int surveyId, String userId) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		Cursor c = db.query("answers_" + surveyId, new String[] { "user_id" }, "user_id=?", new String[] { userId }, null, null, null);
		int count = c.getCount();

		if(count > 0)
			return true;
		else
			return false;
	}

	/**
	 * Read one survey from the database and return it as a Survey object.
	 * 
	 * @param context
	 *        For retrieving the database
	 * @param id
	 *        The ID of the survey to be read
	 * @return a Survey object containing data from the database
	 */
	public static Survey readSurvey(Context context, int id) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		return readSurvey(db, id);
	}

	/**
	 * Read one survey from the database and return it as a Survey object.
	 * 
	 * @param db
	 *        The database to read from
	 * @param suveyId
	 *        The ID of the survey to be read, as specified in the database
	 * @return a Survey object containing the specified survey from the database
	 */
	public static Survey readSurvey(SQLiteDatabase db, int suveyId) {
		Cursor c;
		Cursor c2;

		c = db.query("surveys", new String[] { "title", "intro_text" }, "survey_id=?", new String[] { Integer.toString(suveyId) }, null, null, null);

		if(c.getCount() == 0)
			return null;

		c.moveToFirst();
		String title = c.getString(0);
		String introText = c.getString(1);

		Survey survey = new Survey();
		survey.setTitle(title);
		survey.setIntroText(introText);

		// get the question_id, type, and prompt of all questions in the specified survey
		c = db.query("questions", new String[] { "type", "prompt", "question_id", "section" }, "survey_id=?", new String[] { Integer.toString(suveyId) }, null, null, "question_order");
		c.moveToFirst();

		// grab a question, find its text, and add it to the Question object
		while(!c.isAfterLast()) {
			int type = c.getInt(0);
			String prompt = c.getString(1);
			int questionId = c.getInt(2);
			String section = c.getString(3);

			Question q = null;

			// figure out the question type and create the corresponding object
			if(type == Question.QUESTION_MC)
				q = new QuestionMC(prompt, section);
			else if(type == Question.QUESTION_CHECKBOX)
				q = new QuestionCheckbox(prompt, section);
			else if(type == Question.QUESTION_WRITING)
				q = new QuestionWriting(prompt, section);

			// if it's a multiple choice or checkbox question, get its set of options
			if(type == Question.QUESTION_MC || type == Question.QUESTION_CHECKBOX) {
				c2 = db.query("question_options", new String[] { "answer_text", "text_field_label" }, "question_id=?", new String[] { Integer.toString(questionId) }, null, null, "option_order");
				c2.moveToFirst();

				while(!c2.isAfterLast()) {
					String choice = c2.getString(0);
					String textFieldLabel = null;

					if(!c2.isNull(1))
						textFieldLabel = c2.getString(1);

					if(type == Question.QUESTION_MC) {
						if(textFieldLabel != null)
							((QuestionMC) q).addChoice(choice, textFieldLabel);
						else
							((QuestionMC) q).addChoice(choice);
					} else if(type == Question.QUESTION_CHECKBOX) {
						if(textFieldLabel != null)
							((QuestionCheckbox) q).addChoice(choice, textFieldLabel);
						else
							((QuestionCheckbox) q).addChoice(choice);
					}
					c2.moveToNext();
				}
				c2.close();
			}
			survey.addQuestion(q);
			c.moveToNext();
		}

		c.close();
		// db.close(); // don't close the db because initializeAnswerTables still needs it

		return survey;
	}

	/**
	 * Write the survey structure to the DB with a unique survey ID
	 * 
	 * @param context
	 *        a context
	 * @param survey
	 *        a survey
	 */
	public static void writeSurvey(Context context, Survey survey) {
		writeSurvey(context, survey, getMaxSurveyIndex(context) + 1);
	}

	/**
	 * Write the survey structure to the database. Wipes out and overwrites any existing survey at
	 * the specified index.
	 * 
	 * @param context
	 *        a context
	 * @param survey
	 *        a survey
	 * @param surveyIndex
	 *        a survey index as specified in the DB
	 */
	public static void writeSurvey(Context context, Survey survey, int surveyIndex) {
		ContentValues cv = new ContentValues();
		ContentValues cv2 = new ContentValues();
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

		String surveyTitle = survey.getTitle();
		String introText = survey.getIntroText();

		// TODO wipe out survey if it exists
		db.delete("surveys", "survey_id=?", new String[] { Integer.toString(surveyIndex) });
		db.delete("questions", "survey_id=?", new String[] { Integer.toString(surveyIndex) });
		db.delete("question_options", "question_id > ? AND question_id < ?", new String[] { Integer.toString(surveyIndex * 1000), Integer.toString((surveyIndex + 1) * 1000) });

		// survey table
		cv.clear();
		cv.put("survey_id", surveyIndex);
		cv.put("title", surveyTitle + " copy"); // TODO remove this
		cv.put("intro_text", introText);
		db.insert("surveys", null, cv);

		// questions table
		for(int i = 0; i < survey.getSize(); i++) {
			Question currentQuestion = survey.getQuestion(i);
			String questionType = currentQuestion.getClass().getSimpleName();
			int questionId = (surveyIndex * 1000) + i + 1; // question id should be unique, so let's
															// make it (surveyIndex * 1000) + i + 1
			// TODO limit survey to 998 questions (ha)

			cv.clear();
			cv.put("survey_id", surveyIndex);
			cv.put("question_id", questionId);
			cv.put("question_order", (i + 1));
			cv.put("prompt", currentQuestion.getPrompt());
			cv.put("section", currentQuestion.getSection());

			if(questionType.equals("QuestionCheckbox")) {
				cv.put("type", Question.QUESTION_CHECKBOX);

				for(int j = 0; j < ((QuestionCheckbox) currentQuestion).getChoiceCount(); j++) {
					// question_options table
					cv2.clear();
					cv2.put("question_id", questionId);
					cv2.put("option_order", (j + 1));
					cv2.put("answer_text", ((QuestionCheckbox) currentQuestion).getOptionLabel(j));
					if(((QuestionCheckbox) currentQuestion).textFieldAtIndex(j)) {
						cv2.put("text_field_label", ((QuestionCheckbox) currentQuestion).getTextFieldLabel(j));
					}
					db.insert("question_options", null, cv2);
				}
			} else if(questionType.equals("QuestionMC")) {
				cv.put("type", Question.QUESTION_MC);

				for(int j = 0; j < ((QuestionMC) currentQuestion).getChoiceCount(); j++) {
					// question_options table
					cv2.clear();
					cv2.put("question_id", questionId);
					cv2.put("option_order", (j + 1));
					cv2.put("answer_text", ((QuestionMC) currentQuestion).getOptionLabel(j));
					if(((QuestionMC) currentQuestion).textFieldAtIndex(j)) {
						cv2.put("text_field_label", ((QuestionMC) currentQuestion).getTextFieldLabel(j));
					}
					db.insert("question_options", null, cv2);
				}
			} else if(questionType.equals("QuestionWriting")) {
				cv.put("type", Question.QUESTION_WRITING);
				// writing questions have no question_options
			}
			db.insert("questions", null, cv);
		}
		DatabaseHelper.initializeAnswerTable(db, surveyIndex);	// TODO make sure it doesn't exist already
	}

	/**
	 * Write the answers to the survey currently stored in GlobalsApp to the answers table in the
	 * database.
	 * 
	 * @param context
	 *        For retrieving the database
	 */
	public static void writeSurveyAnswers(Context context) {
		writeSurveyAnswers(context, GlobalsApp.survey);
	}

	/**
	 * Write the specified survey's answers to the answers table in the database.
	 * 
	 * @param context
	 *        For retrieving the database
	 * @param survey
	 *        The survey to read from
	 */
	public static void writeSurveyAnswers(Context context, Survey survey) {
		/**
		 * AsyncTask to handle writing the answer set to the database. Call it as: new
		 * InsertAnswersTask().execute(db, cv, surveyId, context);
		 */
		class InsertAnswersTask extends AsyncTask<Object, Object, Boolean> {
			SQLiteDatabase db;
			Context context;

			@Override
			protected Boolean doInBackground(Object... params) {
				db = (SQLiteDatabase) params[0];
				ContentValues cv = (ContentValues) params[1];
				int surveyId = (Integer) params[2];
				context = (Context) params[3];
				boolean runPostExecute = false;

				try {
					db.insertOrThrow("answers_" + surveyId, null, cv);
					runPostExecute = true;
				} catch (SQLiteConstraintException e) {
					publishProgress(db, cv, surveyId, context);
				}

				return runPostExecute;
			}

			@Override
			protected void onProgressUpdate(Object... params) {
				final SQLiteDatabase db = (SQLiteDatabase) params[0];
				final ContentValues cv = (ContentValues) params[1];
				final int surveyId = (Integer) params[2];
				final Context context = (Context) params[3];

				// @formatter:off
				new AlertDialog.Builder(context)
				.setTitle("Overwrite existing answers?")
				.setIcon(R.drawable.ic_dialog_alert_holo_light)
				.setMessage("This survey has already been submitted with your User ID. Do you want to overwrite the existing set of answers?")
				.setPositiveButton("Overwrite existing answers with mine", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new UpdateAnswersTask().execute(db, cv, surveyId);
					}
				}).setNegativeButton("Keep existing answers", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {}
				}).show();
				// @formatter:on
			}

			@Override
			protected void onPostExecute(Boolean run) {
				if(run) {
					db.close();
					// @formatter:off
					new AlertDialog.Builder(context)
					.setTitle("Survey complete")
					.setMessage("Congradualation! Low risk,   Your answers have been saved and you will be logged out.")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							((SurveyActivity)context).finish();							
						}
					})
					.setCancelable(false)
					.show();
					// @formatter:on
				}
			}

			/**
			 * Called from inside InsertAnswersTask if a key collision occurs during insertion.
			 */
			class UpdateAnswersTask extends AsyncTask<Object, Void, Integer> {
				@Override
				protected Integer doInBackground(Object... params) {
					SQLiteDatabase db = (SQLiteDatabase) params[0];
					ContentValues cv = (ContentValues) params[1];
					int surveyId = (Integer) params[2];

					db.update("answers_" + surveyId, cv, "user_id=?", new String[] { GlobalsApp.userId });

					return 1;
				}

				@Override
				protected void onPostExecute(Integer result) {
					db.close();
					// @formatter:off
					new AlertDialog.Builder(context)
					.setTitle("Survey complete")
					.setMessage("Thanks! Your answers have been saved and you will be logged out.")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							((SurveyActivity)context).finish();							
						}
					})
					.setCancelable(false)
					.show();
					// @formatter:on
				}
			}
		}

		final SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
		final ContentValues cv = new ContentValues();
		cv.put("user_id", GlobalsApp.userId);

		/*
		 * Based on the class of the question, determine the name of the appropriate column in the
		 * database and prepare a row for insertion
		 */
		for(int questionIterator = 0; questionIterator < survey.getSize(); questionIterator++) {
			String className = survey.getQuestion(questionIterator).getClass().getName();

			if(className.endsWith(".QuestionWriting")) {
				cv.put("q" + (questionIterator + 1), survey.getQuestion(questionIterator).getAnswer());
			}

			else if(className.endsWith(".QuestionMC")) {
				cv.put("q" + (questionIterator + 1), survey.getQuestion(questionIterator).getAnswer());
				for(int choiceIterator = 0; choiceIterator < ((QuestionMC) survey.getQuestion(questionIterator)).getChoiceCount(); choiceIterator++) {
					if(((QuestionMC) survey.getQuestion(questionIterator)).textFieldAtIndex(choiceIterator)) {
						cv.put("q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + "_text_field", ((QuestionMC) survey.getQuestion(questionIterator)).getTextFieldAnswer(choiceIterator));
					}
				}
			}

			else if(className.endsWith(".QuestionCheckbox")) {
				for(int choiceIterator = 0; choiceIterator < ((QuestionCheckbox) survey.getQuestion(questionIterator)).getChoiceCount(); choiceIterator++) {
					if(((QuestionCheckbox) survey.getQuestion(questionIterator)).isChecked(choiceIterator))
						cv.put("q" + (questionIterator + 1) + "_" + (choiceIterator + 1), "1");
					else
						cv.put("q" + (questionIterator + 1) + "_" + (choiceIterator + 1), "0");
					if(((QuestionCheckbox) survey.getQuestion(questionIterator)).textFieldAtIndex(choiceIterator)) {
						cv.put("q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + "_text_field", ((QuestionCheckbox) survey.getQuestion(questionIterator)).getTextFieldAnswer(choiceIterator));
					}
				}
			}
		}

		// get the ID of the survey by finding its name in the databse
		Cursor c = db.query("surveys", new String[] { "survey_id" }, "title=?", new String[] { survey.getTitle() }, null, null, null);
		c.moveToFirst();
		final int surveyId = c.getInt(0);
		c.close();

		// new InsertAnswersTask().execute(db, cv, surveyId, context).get(3, TimeUnit.SECONDS);
		new InsertAnswersTask().execute(db, cv, surveyId, context);
	}

	/**
	 * Writes a set of survey answers to a csv file. The csv file is manually constructed from a
	 * bunch of strings and is completely rewritten every time this function is called. The output
	 * format is currently hard-coded to /[sdcard]/Survey/[Survey Title].csv
	 * 
	 * @param context
	 *        The context
	 * @param surveyIndex
	 *        Survey index, as defined in the surveys table in the database
	 */
	public static void answersToCsv(Context context, int surveyIndex) {
		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
		String tableName = "answers_" + surveyIndex;
		Cursor c = db.query(tableName, null, null, null, null, null, null);

		String[] columnNames = c.getColumnNames();

		// read the answers table and start building the CSV file
		String csv = "";
		// first line: column names
		for(int i = 0; i < columnNames.length; i++) {
			csv += columnNames[i] + ",";
		}
		csv = csv.substring(0, csv.length() - 1) + "\r\n";

		// subsequent lines: data
		c.moveToFirst();
		// Log.d("count", c.getCount() + "");
		while(!c.isAfterLast()) {
			for(int j = 0; j < c.getColumnCount(); j++) {
				String item = c.getString(j);
				if((item != null) && (item.length() > 1))
					item = "\"" + item + "\"";
				if(item == null)
					item = "";
				csv += item + ",";
			}
			csv = csv.substring(0, csv.length() - 1) + "\r\n";
			c.moveToNext();
		}
		c.close();

		c = db.query("surveys", new String[] { "title" }, "survey_id=?", new String[] { Integer.toString(surveyIndex) }, null, null, null);
		c.moveToFirst();
		String surveyName = c.getString(0);
		c.close();
		db.close();

		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/Survey");
		dir.mkdirs();
		File outputFile = new File(dir, surveyName + ".csv");
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
			writer.write(csv);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
