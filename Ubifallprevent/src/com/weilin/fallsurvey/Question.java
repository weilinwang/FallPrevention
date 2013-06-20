package com.weilin.fallsurvey;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Question implements Parcelable {
	private String prompt;
	private String answer;
	private String section;

	final public static int QUESTION = 0;
	final public static int QUESTION_CHECKBOX = 1;
	final public static int QUESTION_MC = 2;
	final public static int QUESTION_WRITING = 3;

	/**
	 * The base Question should (probably?) never be used - instead use
	 * QuestionMC for multiple choice, QuestionCheckbox for checkboxes, etc.
	 * Maybe this shouldn't be a regular class, but I don't know the right way
	 * to do it.
	 * 
	 * @param prompt
	 */
	public Question(String prompt) {
		setPrompt(prompt);
	}

	public Question(String prompt, String section) {
		setPrompt(prompt);
		setSection(section);
	}

	public Question(Parcel in) {
		readFromParcel(in);
	}

	public void setPrompt(String newPrompt) {
		prompt = newPrompt;
	}

	public void setAnswer(String anAnswer) {
		answer = anAnswer;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getPrompt() {
		return prompt;
	}

	public String getAnswer() {
		return answer;
	}

	public String getSection() {
		return section;
	}

	public View getQuestionView(Context context, ViewGroup container) {
		TextView tv = new TextView(context);
		tv.setText("this is the parent class and you shouldn't be seeing this");
		return tv;
	}

	@Override
	public int describeContents() {
		return Question.QUESTION;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.describeContents());		// read by the CREATOR
		dest.writeString(prompt);
		dest.writeString(answer);
		dest.writeString(section);
	}

	protected void readFromParcel(Parcel in) {
		prompt = in.readString();
		answer = in.readString();
		section = in.readString();
	}

	// Making this class Parcelable allows it to be passed in a bundle between
	// classes
	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {

		@Override
		public Question createFromParcel(Parcel source) {
			int description = source.readInt();
			Question q = null;

			switch(description) {
			case QUESTION:
				q = new Question(source);
				break;
			case QUESTION_CHECKBOX:
				q = new QuestionCheckbox(source);
				break;
			case QUESTION_MC:
				q = new QuestionMC(source);
				break;
			case QUESTION_WRITING:
				q = new QuestionWriting(source);
				break;
			default:
				break;
			}
			return q;
		}

		@Override
		public Question[] newArray(int size) {
			return new Question[size];
		}

	};

}
