package com.weilin.fallsurvey;

import com.weilin.fall.DBAdapter;
import com.weilin.fall.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SurveyFinishPage extends Fragment {
	
	
	
	public SurveyFinishPage() {
	}
	
//	
//	DatabaseHelper.getContact(); 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.survey_finish_page, null);

		Button finishButton = (Button) view.findViewById(R.id.finish_button);

		finishButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// @formatter:off
				new AlertDialog.Builder(getActivity())
//				.setTitle("Finish the survey?")
				.setMessage("Are you finished answering this survey?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SurveyDBHelper.writeSurveyAnswers(getActivity());
						SurveyDBHelper.answersToCsv(getActivity(), SurveyDBHelper.getSurveyId(getActivity(), GlobalsApp.survey.getTitle()));
					
						
					/*	SurveyDBHelper.getContact(getActivity());
						SQLiteDatabase db = new DatabaseHelper(getActivity()).getReadableDatabase();
						db.open();*/
						
					
						
				/*		SQLiteDatabase db = new DatabaseHelper(getActivity()).getWritableDatabase(); 
						Cursor mCursor =db.query( "answers_0" , new String[] { "USER_ID"}, "Q1=?", null, null, null , null );
						//String select = " select all from answers_0"; 
						//Cursor c = db.rawQuery(select, null);
						//String adgfd= mCursor.getString(1);
						 mCursor.moveToFirst();
						 mCursor.getString(1);
						db.close();
					     //  
					           
						
				         if (mCursor.moveToFirst())
				        {
				            do {
				            	Log.d(tag, mCursor.getString(0));
			            } while (mCursor.moveToNext());
			        }
				        db.close();*/
					}

					
				}).setNegativeButton("No", null)
				.show();
				// @formatter:on
			}
		});

		return view;
	}
//	private void getresult() {
//		// TODO Auto-generated method stub
////		   db.query(answers_0, new String[] {USER_ID, Q1,Q2, 
////				   Q3,Q4,Q5,Q6}, null, null, null, null, null);
//		SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
//		Cursor mCursor =
//				db.query(answers_0, new String[] {USER_ID, Q1,Q2, 
////						   Q3,Q4,Q5,Q6}, null, null, null, null, null)
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
        

}
