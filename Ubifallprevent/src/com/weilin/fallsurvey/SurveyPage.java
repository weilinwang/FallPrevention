package com.weilin.fallsurvey;

import com.weilin.fall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SurveyPage extends Fragment {
	View pageView;

	/**
	 * To create a survey page, pass a bundle containing an integer "qIndex"
	 */
	public SurveyPage() {
	}

	/**
	 * Creates a fragment by turning a survey question into a View.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Bundle args = getArguments();
		int index = (int) args.getInt("qIndex");
		Question question = GlobalsApp.survey.getQuestion(index);

		// keep input alive even when you swipe to a faraway page
		if(pageView == null) {
			// normally inflate the view hierarchy
			pageView = question.getQuestionView(getActivity(), container);
		} else {
			// view is still attached to the previous view hierarchy
			// we need to remove it and re-attach it to the current one
			ViewGroup parent = (ViewGroup) pageView.getParent();
			parent.removeView(pageView);
		}

		TextView promptTextView = (TextView) pageView.findViewById(R.id.prompt);
		promptTextView.setText(question.getPrompt());

		TextView sectionTextView = (TextView) pageView.findViewById(R.id.section);
		sectionTextView.setVisibility(View.GONE);

		if(question.getSection() != null) {
			sectionTextView.setText(question.getSection());
			sectionTextView.setVisibility(View.VISIBLE);
		}

		return pageView;
	}
}
