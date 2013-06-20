package com.weilin.fallsurvey;


import com.weilin.fall.R;

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

/**
 * The Activity that allows the user to fill out the survey. Pages in the survey are navigated by
 * mViewPager, which receives fragments from mSurveyPagerAdapter. The Survey is accessed statically
 * from GlobalsApp.
 */
public class SurveyActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
	 * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best to
	 * switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SurveyPagerAdapter mSurveyPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private Survey mSurvey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GlobalsApp.userId = getIntent().getStringExtra("userId");
		int surveyId = getIntent().getIntExtra("surveyId", 0);

		setContentView(R.layout.survey_activity_viewpager);

		if(savedInstanceState != null)
			mSurvey = savedInstanceState.getParcelable("survey");
		else
			mSurvey = SurveyDBHelper.readSurvey(this, surveyId);

		GlobalsApp.survey = mSurvey;

		/*
		 * Create the adapter that will return a fragment for each of the primary sections of the
		 * app.
		 */
		mSurveyPagerAdapter = new SurveyPagerAdapter(getSupportFragmentManager(), this);

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

	/**
	 * If the user leaves the program, save the state of the global survey. The survey gets restored
	 * in SurveyActivity.onCreate().
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("survey", GlobalsApp.survey);
	}


	/**
	 * Intercept the back button so the user has to confirm exit from the survey
	 */
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
