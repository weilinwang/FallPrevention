package com.weilin.fallsurvey.creator;


import com.weilin.fall.R;
import com.weilin.fall.R.drawable;
import com.weilin.fall.R.id;
import com.weilin.fall.R.layout;
import com.weilin.fall.R.string;
import com.weilin.fallsurvey.GlobalsApp;
import com.weilin.fallsurvey.Survey;
import com.weilin.fallsurvey.SurveyPagerAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SurveyEditorActivity extends FragmentActivity {

	private SurveyEditorPagerAdapter mSurveyPagerAdapter;
	private ViewPager mViewPager;
	private Survey mSurvey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int surveyId = Integer.MAX_VALUE;	// TODO check if this is ever actually needed

		setContentView(R.layout.survey_activity_viewpager);

		if(savedInstanceState != null)
			mSurvey = savedInstanceState.getParcelable("survey");
		else
			mSurvey = new Survey("New Survey");

		GlobalsApp.survey = mSurvey;

		mSurveyPagerAdapter = new SurveyEditorPagerAdapter(getSupportFragmentManager(), this);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSurveyPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);

				final RelativeLayout swipeNote = (RelativeLayout) findViewById(R.id.swipe_note);
				final TextView swipeNoteText = (TextView) findViewById(R.id.swipe_note_text);
				if(position == mSurveyPagerAdapter.getCount() - 1) {
					swipeNote.setVisibility(View.GONE);
				} else if(position == 0) {
					swipeNote.setVisibility(View.VISIBLE);
					swipeNoteText.setText(R.string.swipe_note_start);
				} else {
					swipeNote.setVisibility(View.VISIBLE);
					swipeNoteText.setText(R.string.swipe_note_continue);
				}
			}

			@Override
			public void onPageScrolled(int position, float offset, int offsetPixel) {}

			@Override
			public void onPageScrollStateChanged(int state) {}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("survey", GlobalsApp.survey);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("End survey?").setIcon(R.drawable.ic_dialog_alert_holo_light).setMessage("You will be logged out and any data you entered will be discarded.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).setNegativeButton("Cancel", null).show();
	}
}
