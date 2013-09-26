package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.service.CoursesServiceAsync;
import ru.abelitsky.memorize.client.service.TrainingServiceAsync;
import ru.abelitsky.memorize.client.view.AllCoursesView;
import ru.abelitsky.memorize.client.view.CurrentCoursesView;
import ru.abelitsky.memorize.client.view.EditCourseView;
import ru.abelitsky.memorize.client.view.LoadWordsView;
import ru.abelitsky.memorize.client.view.TrainingView;
import ru.abelitsky.memorize.client.view.ViewCourseView;
import ru.abelitsky.memorize.client.widget.LoadingDialog;
import ru.abelitsky.memorize.client.widget.RPCFaultDialog;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {

	public static final ClientFactory INSTANCE = GWT.create(ClientFactory.class);
	
	AllCoursesView getAllCoursesView();

	CurrentCoursesView getCurrentCoursesView();

	CoursesServiceAsync getCoursesService();

	EditCourseView getEditCourseView();

	EventBus getEventBus();

	LoadingDialog getLoadingDialog();

	LoadWordsView getLoadWordsView();

	PlaceController getPlaceController();
	
	PlaceHistoryMapper getPlaceHistoryMapper();

	RPCFaultDialog getRPCFaultDialog();

	TrainingServiceAsync getTrainingService();

	TrainingView getTrainingView();

	ViewCourseView getViewCourseView();

}
