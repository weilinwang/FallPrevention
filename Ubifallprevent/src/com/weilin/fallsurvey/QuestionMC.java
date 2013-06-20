/**
 * This and QuestionCheckbox should probably both subclasses of something that includes the textField
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QuestionMC extends Question implements Parcelable {
	private ArrayList<String> choices;
	private ArrayList<String> textField;
	private ArrayList<String> textFieldAnswer;

	/**
	 * A question with possible answers displayed as a list of radio buttons.
	 * Only one choice can be selected.
	 * 
	 * @param prompt
	 *        The question prompt
	 */
	public QuestionMC(String prompt) {
		super(prompt);
		choices = new ArrayList<String>();
		textField = new ArrayList<String>();
		textFieldAnswer = new ArrayList<String>();
	}

	/**
	 * A question with possible answers displayed as a list of checkboxes. Any
	 * combination and any number of answers can be chosen.
	 * 
	 * @param prompt
	 *        The question prompt
	 * @param section
	 *        The "section name" of the question, displayed at the top of the
	 *        page
	 */
	public QuestionMC(String prompt, String section) {
		super(prompt, section);
		choices = new ArrayList<String>();
		textField = new ArrayList<String>();
		textFieldAnswer = new ArrayList<String>();
	}

	/**
	 * Used automatically during parcelization.
	 */
	public QuestionMC(Parcel in) {
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
		textField.add(null);
		textFieldAnswer.add(null);
	}

	/**
	 * Add a single choice with a supplemental text field to the question. The
	 * text field will appear when the choice is selected.
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
	 * Add a supplemental text field associated with the choice at the specified
	 * index. The text field will appear when the choice is selected.
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
	 * Test whether a supplemental text field exists at the specified index.
	 * 
	 * @param index
	 *        The index of a choice.
	 * @return The truth value indicating whether a supplemental text field is
	 *         associated with the choice at the specified index.
	 */
	public boolean textFieldAtIndex(int index) {
		if(textField.get(index) != null)
			return true;
		else
			return false;
	}

	/**
	 * Remove the supplemental text field associated with the choice at the
	 * specified index.
	 * 
	 * @param index
	 *        The index of a choice.
	 */
	public void removeTextField(int index) {
		if(choices.get(index) != null)
			textField.set(index, null);
	}

	/**
	 * Build a View of this Question to be displayed to the user. The view
	 * includes a prompt and a list of options with associated checkboxes.
	 */
	public View getQuestionView(final Context context, final ViewGroup container) {
		// build the basic page layout
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.survey_page, container, false);
		LinearLayout pollPage = (LinearLayout) view.findViewById(R.id.poll_page_container);

		// start adding answer buttons to the layout
		if(choices.size() > 0) {
			// add a RadioGroup to hold the RadioButtons
			RadioGroup rg = new RadioGroup(context);
			rg.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
			pollPage.addView(rg);

			// assign an alphabetic tag to each button
			char tag = 'a';
			int checkIndex = -1;
			Character checkedTag = null;

			for(int i = 0; i < choices.size(); i++) {
				// build the button
				String choiceLabel = choices.get(i);
				final RadioButton rb = (RadioButton) inflater.inflate(R.layout.radio_button_style, rg, false);
				rb.setText(choiceLabel);

				// set the alphabetic tag for the button
				rb.setTag(tag);

				// check whether the button should be checked (selected)
				if(getAnswer() != null && getAnswer().equals(Character.toString(tag)))
					checkedTag = tag;
				tag++;

				/*
				 * make an EditText field for each button in case there should
				 * be an associated textField
				 */
				final EditText et = new EditText(context);

				if(getTextFieldAnswer(i) != null)
					et.setText(getTextFieldAnswer(i));
				else
					et.setVisibility(View.GONE);

				/*
				 * If a text field exists at the specified index, build it and
				 * style it so it's ready to be displayed
				 */
				if(textFieldAtIndex(i)) {
					String textFieldLabel = textField.get(i);
					et.setHint(textFieldLabel);
					et.setTag(tag);
					RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(50, 0, 0, 15);
					et.setLayoutParams(layoutParams);
					et.addTextChangedListener(new TextWatcher() {
						@Override
						public void afterTextChanged(Editable s) {}

						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							int tag = (Character) rb.getTag();
							tag = tag - 'a';
							setTextFieldAnswer(tag, s.toString());
						}
					});
				}

				/*
				 * When the radio button is checked, test whether there's an
				 * associated textField and show/hide it
				 */
				rb.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked && et.getTag() != null) {
							et.setVisibility(View.VISIBLE);
							int etTag = (Character) rb.getTag();
							etTag = etTag - 'a';
							setTextFieldAnswer(etTag, et.getText().toString());
							et.requestFocus();
						} else {
							et.setVisibility(View.GONE);
							int tag = (Character) rb.getTag();
							tag = tag - 'a';
							setTextFieldAnswer(tag, null);
							final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
						}

					}
				});
				// add the button to the RadioGroup
				rg.addView(rb);
				rg.addView(et);
			}

			// mark the checked button as checked
			if(checkedTag != null) {
				checkIndex = rg.findViewWithTag(checkedTag).getId();
			}
			rg.check(checkIndex);

			/*
			 * when the answer is changed, save it to the Question object's
			 * "answer" field
			 */
			rg.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
					RadioButton checked = (RadioButton) container.findViewById(checkedId);
					setAnswer(checked.getTag().toString());
				}
			});
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
		dest.writeStringList(textField);
		dest.writeStringList(textFieldAnswer);
	}

	/**
	 * Used automatically during parcelization.
	 */
	protected void readFromParcel(Parcel in) {
		choices = new ArrayList<String>();
		textField = new ArrayList<String>();
		textFieldAnswer = new ArrayList<String>();
		super.readFromParcel(in);
		in.readStringList(choices);
		in.readStringList(textField);
		in.readStringList(textFieldAnswer);
	}

	/**
	 * Each subclass of {@link Question} returns a unique integer. Refer to the
	 * constants in {@link Question} for more detail.
	 */
	@Override
	public int describeContents() {
		return Question.QUESTION_MC;
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