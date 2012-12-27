package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.widget.training.TrainingWidget.TrainingWidgetImageBundle;

import com.google.gwt.core.shared.GWT;

public class Resources {

	private static final TrainingWidgetImageBundle trainingWidgetImageBundle = GWT
			.create(TrainingWidgetImageBundle.class);

	public static TrainingWidgetImageBundle getTrainingWidgetImageBundle() {
		return trainingWidgetImageBundle;
	}

}
