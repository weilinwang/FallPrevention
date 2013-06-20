package com.weilin.fallsurvey;

import android.app.Application;

/**
 * Provides global, static access to the current survey and the logged-in user's
 * ID.
 */
public class GlobalsApp extends Application {
	public static Survey survey; // the survey to be displayed and completed, read in SurveyDBHelper
	public static String userId; // the ID of the currently logged-in user
}
