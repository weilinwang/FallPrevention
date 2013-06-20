package com.weilin.fallsurvey;


import com.weilin.fall.R;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class QuestionWriting extends Question implements Parcelable {

	/**
	 * A question that asks the user to write some text. Displays the question
	 * prompt and a text box.
	 * 
	 * @param prompt
	 *        The question prompt
	 */
	public QuestionWriting(String prompt) {
		super(prompt);
	}

	/**
	 * A question that asks the user to write some text. Displays the question
	 * prompt and a text box.
	 * 
	 * @param prompt
	 *        The question prompt
	 * @param section
	 *        The "section name" of the question, displayed at the top of the
	 *        page
	 */
	public QuestionWriting(String prompt, String section) {
		super(prompt, section);
	}

	/**
	 * Used automatically during parcelization.
	 */
	public QuestionWriting(Parcel in) {
		super(in);
	}

	/**
	 * Build a View of this Question to be displayed to the user. The view
	 * includes a prompt and a list of options with associated checkboxes.
	 */
	public View getQuestionView(Context context, final ViewGroup container) {
		// build the basic page layout
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.survey_page, container, false);
		LinearLayout pollPage = (LinearLayout) view.findViewById(R.id.poll_page_container);

		// make a text box and style it
		EditText inputBox = new EditText(context);
		inputBox.setHint("Write your response here");
		if(getAnswer() != null && getAnswer().length() > 0)
			inputBox.setText(getAnswer());
		inputBox.setMinLines(5);
		inputBox.setGravity(Gravity.TOP | Gravity.LEFT);

		/*
		 * when the answer is changed, save it to the Question object's "answer"
		 * field
		 */
		inputBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				setAnswer(s.toString());
			}
		});

		// add the text box to the page
		pollPage.addView(inputBox);

		return view;
	}

	/**
	 * Each subclass of {@link Question} returns a unique integer. Refer to the
	 * constants in {@link Question} for more detail.
	 */
	@Override
	public int describeContents() {
		return Question.QUESTION_WRITING;
	}
}