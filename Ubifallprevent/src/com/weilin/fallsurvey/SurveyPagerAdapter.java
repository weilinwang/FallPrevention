package com.weilin.fallsurvey;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the primary sections of the app.
 */
public class SurveyPagerAdapter extends FragmentPagerAdapter {
	private Survey survey;

	public SurveyPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		survey = GlobalsApp.survey;
	}

	/**
	 * Get a question from the associated survey (the one that we're paging
	 * through) and turn it into a Fragment to be shown by the ViewPager in
	 * MainPoll. (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int i) {
		Fragment fragment;

		// shift the offset if the survey has a title
		int offset = 0;
		if(survey.hasTitle())
			offset = 1;

		Bundle args = new Bundle();

		// index 0 is the title page, if it exists
		if(i == 0 && survey.hasTitle()) {
			fragment = new SurveyTitlePage();
		}
		// final index is the finish page
		else if(i == getCount() - 1) {
			fragment = new SurveyFinishPage();
		}
		// all other indices are regular poll pages
		else {
			args.putInt("qIndex", (i - offset));

			fragment = new SurveyPage();
			fragment.setArguments(args);
		}

		return fragment;

	}

	/**
	 * Get the number of questions in this survey, including the title page and
	 * finish page.
	 */
	@Override
	public int getCount() {
		if(survey.hasTitle())
			return survey.getSize() + 2;
		else
			return survey.getSize() + 1;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(position == 0)
			return "START";
		else if(position == getCount() - 1)
			return "END";
		else
			return "QUESTION " + (position);
	}

}