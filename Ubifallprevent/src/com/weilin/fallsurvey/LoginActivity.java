package com.weilin.fallsurvey;



import com.weilin.fall.R;
import com.weilin.fallsurvey.creator.SurveyEditorActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Displays the interface for user login.
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		final EditText loginField = (EditText) findViewById(R.id.login_field);
		final Button loginButton = (Button) findViewById(R.id.login_button);
		final Spinner surveySelector = (Spinner) findViewById(R.id.survey_selector);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, SurveyDBHelper.getSurveyTitles(this));
		surveySelector.setAdapter(adapter);
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!loginField.getText().toString().trim().equals("")) {
					String userId = loginField.getText().toString().trim();

					final Intent startSurvey = new Intent(LoginActivity.this, SurveyActivity.class);
					int surveyId = SurveyDBHelper.getSurveyId(LoginActivity.this, surveySelector.getSelectedItem().toString());
					startSurvey.putExtra("userId", userId);
					startSurvey.putExtra("surveyId", surveyId);

					if(SurveyDBHelper.isAlreadyAnswered(LoginActivity.this, surveyId, userId)) {
						// @formatter:off
						new AlertDialog.Builder(LoginActivity.this)
						.setTitle("Re-take the survey?")
						.setIcon(R.drawable.ic_dialog_alert_holo_light)
						.setMessage("This survey has already been submitted with your User ID. If you take this survey, your previous set of answers will be overwritten. Do you want to take this survey anyway?")
						.setPositiveButton("Yes, re-take the survey", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								/*
								 * close the keyboard before we leave so the background gradient
								 * doesn't have to be resized when returning from SurveyActivity
								 */
								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(loginField.getWindowToken(), 0);
								startActivity(startSurvey);
							}
						}).setNegativeButton("Cancel", null).show();
						// @formatter:on
					} else {
						/*
						 * close the keyboard before we leave so the background gradient doesn't
						 * have to be resized when returning from SurveyActivity
						 */
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(loginField.getWindowToken(), 0);
						startActivity(startSurvey);
					}
				}
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		GlobalsApp.userId = null;
		GlobalsApp.survey = null;
		final EditText loginField = (EditText) findViewById(R.id.login_field);
		loginField.setText("");
		loginField.requestFocus();
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.login_activity, menu);
//	    return true;
//	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.create_survey:
			final Intent startSurveyCreator = new Intent(LoginActivity.this, SurveyEditorActivity.class);
			startActivity(startSurveyCreator);
			return true;
		default:
			return true;
		}

	}

}
