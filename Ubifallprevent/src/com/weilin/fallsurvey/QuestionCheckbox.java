/**
 * This and QuestionMC should probably both subclasses of something that includes the textField
 */
package com.weilin.fallsurvey;

import java.util.ArrayList;

import com.weilin.fall.R;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

public class QuestionCheckbox extends Question implements Parcelable {
	private ArrayList<String> choices;
	private ArrayList<Boolean> checked;
	private ArrayList<String> textField;
	private ArrayList<String> textFieldAnswer;

	/**
	 * A question with possible answers displayed as a list of checkboxes. Any combination and any
	 * number of answers can be chosen.
	 * 
	 * @param prompt
	 *        The question prompt
	 */
	public QuestionCheckbox(String prompt) {
		super(prompt);
		choices = new ArrayList<String>();
		checked = new ArrayList<Boolean>();
		textField = new ArrayList<String>();
		textFieldAnswer = new ArrayList<String>();
	}

	/**
	 * A question with possible answers displayed as a list of checkboxes. Any combination and any
	 * number of answers can be chosen.
	 * 
	 * @param prompt
	 *        The question prompt
	 * @param section
	 *        The "section name" of the question, displayed at the top of the page
	 */
	public QuestionCheckbox(String prompt, String section) {
		super(prompt, section);
		choices = new ArrayList<String>();
		checked = new ArrayList<Boolean>();
		textField = new ArrayList<String>();
		textFieldAnswer = new ArrayList<String>();
	}

	/**
	 * Used automatically during parcelization.
	 */
	public QuestionCheckbox(Parcel in) {
		super(in);
	}

	/**
	 * Add a single choice to the question.
	 * 
	 * @param newChoice
	 *        The text displayed next to the new choice.
	 */
	public void addChoice(String newChoice) {
		choices.add(newChoice);
		checked.add(false);
		textField.add(null);
		textFieldAnswer.add(null);
	}

	/**
	 * Add a single choice with a supplemental text field to the question. The text field will
	 * appear when the choice is selected.
	 * 
	 * @param newChoice
	 *        The text displayed next to the new choice.
	 * @param textFieldLabel
	 *        The text displayed next to the supplemental text field.
	 */
	public void addChoice(String newChoice, String textFieldLabel) {
		addChoice(newChoice);
		setTextField(choices.lastIndexOf(newChoice), textFieldLabel);
	}

	/**
	 * Mark a question's answer as checked or not.
	 * 
	 * @param index
	 *        The index of a choice.
	 * @param bool
	 *        True if checked, false if not checked.
	 */
	public void setChecked(int index, boolean bool) {
		if(index < checked.size())
			checked.set(index, bool);
	}

	/**
	 * Add a supplemental text field associated with the choice at the specified index. The text
	 * field will appear when the choice is selected.
	 * 
	 * @param index
	 *        The index of a choice.
	 * @param label
	 *        The text displayed next to the supplemental text field
	 */
	public void setTextField(int index, String label) {
		if(choices.get(index) != null)
			textField.set(index, label);
	}

	/**
	 * Set the contents of the supplemental text field at the specified index.
	 * 
	 * @param index
	 *        The index of a choice.
	 * @param answer
	 *        The contents of the supplemental text field.
	 */
	public void setTextFieldAnswer(int index, String answer) {
		textFieldAnswer.set(index, answer);
	}

	/**
	 * Get the number of choices associated with this question.
	 * 
	 * @return The number of choices associated with this question.
	 */
	public int getChoiceCount() {
		return choices.size();
	}

	/**
	 * Get the contents of the supplemental text field at the specified index.
	 * 
	 * @param index
	 *        The index of the choice.
	 * @return The contents of the supplemental text field.
	 */
	public String getTextFieldAnswer(int index) {
		return textFieldAnswer.get(index);
	}

	/**
	 * Build a comma-separated string of the checked answers for this question.
	 * 
	 * @return A comma-separated string of the checked answers for this question.
	 */
	public String getAnswerString() {
		String answerString = "";
		char tag = 'a';

		for(int i = 0; i < choices.size(); i++) {
			if(checked.get(i) == true) {
				answerString += tag + ", ";
			}
			tag++;
		}

		if(answerString.length() > 2)
			return answerString.substring(0, answerString.length() - 2);
		else if(answerString.length() == 1)
			return answerString;
		else
			return null;
	}

	/**
	 * Test whether a supplemental text field exists at the specified index.
	 * 
	 * @param index
	 *        The index of a choice.
	 * @return The truth value indicating whether a supplemental text field is associated with the
	 *         choice at the specified index.
	 */
	public boolean textFieldAtIndex(int index) {
		if(textField.get(index) != null)
			return true;
		else
			return false;
	}

	/**
	 * Test whether a choice is checked (selected).
	 * 
	 * @param index
	 *        The index of a choice.
	 * @return The truth value indicating whether the choice is checked.
	 */
	public boolean isChecked(int index) {
		return checked.get(index);
	}

	/**
	 * Remove the supplemental text field associated with the choice at the specified index.
	 * 
	 * @param index
	 *        The index of a choice.
	 */
	public void removeTextField(int index) {
		if(choices.get(index) != null)
			textField.set(index, null);
	}

	/**
	 * Build a View of this Question to be displayed to the user. The view includes a prompt and a
	 * list of options with associated checkboxes.
	 */
	public View getQuestionView(final Context context, final ViewGroup container) {
		// inflate the basic page layout
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.survey_page, container, false);
		LinearLayout pollPage = (LinearLayout) view.findViewById(R.id.poll_page_container);

		// start adding answer options to the layout
		if(choices.size() > 0) {
			for(int i = 0; i < choices.size(); i++) {
				// build the button
				String choiceLabel = choices.get(i);
				final CheckBox cb = (CheckBox) inflater.inflate(R.layout.checkbox_style, pollPage, false);
				cb.setText(choiceLabel);

				// set the alphabetic tag for the button
				cb.setTag(i);

				// restore the checked status
				if(checked.get(i) != null & checked.get(i) == true) {
					cb.setChecked(true);
				}

				/*
				 * Make an EditText field for each choice in case there should be an associated
				 * textField. Hide it if there is no textField for that choice. This... could
				 * probably be done better.
				 */
				final EditText et = new EditText(context);

				if(getTextFieldAnswer(i) != null)
					et.setText(getTextFieldAnswer(i));
				else
					et.setVisibility(View.GONE);

				if(textFieldAtIndex(i)) {
					String textFieldLabel = textField.get(i);
					et.setHint(textFieldLabel);
					et.setTag(i);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(50, 0, 0, 15);
					et.setLayoutParams(layoutParams);
					et.addTextChangedListener(new TextWatcher() {
						@Override
						public void afterTextChanged(Editable s) {}

						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							setTextFieldAnswer((Integer) et.getTag(), s.toString());
						}
					});
				}

				/*
				 * When the answer is changed, save it to the Question object's "answer" field.
				 */
				cb.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						/*
						 * The QuestionCheckbox class handles this slightly differently than the
						 * other subclasses of Question. The tag is used to store the index, which
						 * is retrieved from the callback method and used to set values in the
						 * "checked" ArrayList. The list of answers are compiled into a string.
						 */
						setChecked((Integer) cb.getTag(), isChecked);
						setAnswer(getAnswerString());

						if(isChecked && et.getTag() != null) {
							et.setVisibility(View.VISIBLE);
							setTextFieldAnswer((Integer) cb.getTag(), et.getText().toString());
							et.requestFocus();
						} else {
							et.setVisibility(View.GONE);
							setTextFieldAnswer((Integer) cb.getTag(), null);
							final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
						}
					}
				});

				pollPage.addView(cb);
				pollPage.addView(et);
			}

		}

		return view;
	}

	/**
	 * Used automatically during parcelization.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.describeContents());
		dest.writeString(getPrompt());
		dest.writeString(getAnswer());
		dest.writeString(getSection());
		dest.writeStringList(choices);
		dest.writeList(checked);
		dest.writeStringList(textField);
		dest.writeStringList(textFieldAnswer);
	}

	/**
	 * Used automatically during parcelization.
	 */
	@Override
	protected void readFromParcel(Parcel in) {
		choices = new ArrayList<String>();
		checked = new ArrayList<Boolean>();
		textField = new ArrayList<String>();
		textFieldAnswer = new ArrayList<String>();
		super.readFromParcel(in);
		in.readStringList(choices);
		in.readList(checked, null);
		in.readStringList(textField);
		in.readStringList(textFieldAnswer);
	}

	/**
	 * Each subclass of {@link Question} returns a unique integer. Refer to the constants in
	 * {@link Question} for more detail.
	 */
	@Override
	public int describeContents() {
		return Question.QUESTION_CHECKBOX;
	}

	public String getOptionLabel(int optionIndex) {
		if(choices.get(optionIndex) != null)
			return choices.get(optionIndex);
		else
			return null;
	}

	public String getTextFieldLabel(int optionIndex) {
		if(textFieldAtIndex(optionIndex))
			return textField.get(optionIndex);
		else
			return null;
	}
}