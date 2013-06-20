package com.weilin.fallsurvey;

import com.weilin.fall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class SurveyTitlePage extends Fragment {

	/**
	 * Create a title page for the currently selected survey.
	 */
	public SurveyTitlePage() {}

	/**
	 * Draw the title page. Called automatically by SurveyPagerAdapter.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.survey_title_page, null);

		TextView title = (TextView) scrollView.findViewById(R.id.survey_title);
		title.setText(GlobalsApp.survey.getTitle());

		String introText = GlobalsApp.survey.getIntroText();
		TextView introTextView = (TextView) scrollView.findViewById(R.id.intro_text);
		if(introText != null) {
			introTextView.setText(introText);
			introTextView.setVisibility(View.VISIBLE);
		}
		else {
			introTextView.setVisibility(View.GONE);
		}

		return scrollView;
	}
}
