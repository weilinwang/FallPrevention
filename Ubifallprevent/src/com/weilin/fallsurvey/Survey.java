package com.weilin.fallsurvey;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Survey implements Parcelable {

	private ArrayList<Question> questions;
	private String title;
	private String introText;

	/**
	 * A Survey is a series of Questions.
	 */
	public Survey() {
		setQuestions(new ArrayList<Question>());
		setTitle(null);
		setIntroText(null);
	}
	
	public Survey(String title) {
		setTitle(title);
		setIntroText(null);
	}
	
	public Survey(String title, String introText) {
		setTitle(title);
		setIntroText(introText);
	}

	public void addQuestion(Question newQuestion) {
		questions.add(newQuestion);
	}

	public void setQuestions(ArrayList<Question> newQuestions) {
		questions = newQuestions;
	}

	public Question getQuestion(int index) {
		return questions.get(index);
	}

	public boolean hasTitle() {
		if(title != null)
			return true;
		else
			return false;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public void setIntroText(String anIntroText) {
		introText = anIntroText;
	}

	public int getSize() {
		return questions.size();
	}

	public String getTitle() {
		return title;
	}
	
	public String getIntroText() {
		return introText;
	}

	public Survey(Parcel in) {
		this();
		setTitle(in.readString());
		setIntroText(in.readString());
		in.readTypedList(questions, Question.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(introText);
		dest.writeTypedList(questions);
	}

	public static final Parcelable.Creator<Survey> CREATOR = new Parcelable.Creator<Survey>() {

		@Override
		public Survey createFromParcel(Parcel source) {
			return new Survey(source);
		}

		@Override
		public Survey[] newArray(int size) {
			return new Survey[size];
		}
	};

}
