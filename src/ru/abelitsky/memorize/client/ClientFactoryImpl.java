package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.service.CoursesService;
import ru.abelitsky.memorize.client.service.CoursesServiceAsync;
import ru.abelitsky.memorize.client.view.AllCoursesView;
import ru.abelitsky.memorize.client.view.AllCoursesViewImpl;
import ru.abelitsky.memorize.client.view.CurrentCoursesView;
import ru.abelitsky.memorize.client.view.CurrentCoursesViewImpl;
import ru.abelitsky.memorize.client.view.EditCourseView;
import ru.abelitsky.memorize.client.view.EditCourseViewImpl;
import ru.abelitsky.memorize.client.view.LoadWordsView;
import ru.abelitsky.memorize.client.view.LoadWordsViewImpl;
import ru.abelitsky.memorize.client.view.ViewCourseView;
import ru.abelitsky.memorize.client.view.ViewCourseViewImpl;
import ru.abelitsky.memorize.client.widget.LoadingDialog;
import ru.abelitsky.memorize.client.widget.RPCFaultDialog;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(
			eventBus);

	private final CoursesServiceAsync coursesService = GWT
			.create(CoursesService.class);

	private final LoadingDialog loadingDialog = new LoadingDialog();
	private final RPCFaultDialog rpcFaultDialog = new RPCFaultDialog();

	private final AllCoursesView allCoursesView = new AllCoursesViewImpl();
	private final CurrentCoursesView currentCoursesView = new CurrentCoursesViewImpl();
	private final EditCourseView editCourseView = new EditCourseViewImpl();
	private final LoadWordsView loadWordsView = new LoadWordsViewImpl();
	private final ViewCourseView viewCourseView = new ViewCourseViewImpl();

	@Override
	public AllCoursesView getAllCoursesView() {
		return allCoursesView;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public LoadingDialog getLoadingDialog() {
		return loadingDialog;
	}

	@Override
	public LoadWordsView getLoadWordsView() {
		return loadWordsView;
	}

	@Override
	public EditCourseView getEditCourseView() {
		return editCourseView;
	}

	@Override
	public CurrentCoursesView getCurrentCoursesView() {
		return currentCoursesView;
	}

	@Override
	public CoursesServiceAsync getCoursesService() {
		return coursesService;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public RPCFaultDialog getRPCFaultDialog() {
		return rpcFaultDialog;
	}

	@Override
	public ViewCourseView getViewCourseView() {
		return viewCourseView;
	}

}