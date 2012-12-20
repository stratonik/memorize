package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.client.place.CurrentCoursesPlace;
import ru.abelitsky.memorize.client.service.CoursesService;
import ru.abelitsky.memorize.client.service.CoursesServiceAsync;
import ru.abelitsky.memorize.shared.dto.UserInfo;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Memorize implements EntryPoint {

	private Place defaultPlace = new CurrentCoursesPlace();

	private SimplePanel appWidget = new SimplePanel();

	private Widget createLogoutWidget() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		Anchor logout = new Anchor("Выход");
		logout.setHref(UserService.getCurrentUserInfo().getLogoutUrl());

		panel.add(new Label(UserService.getCurrentUserInfo().getUserName()));
		panel.add(logout);
		return panel;
	}

	public void onModuleLoad() {
		CoursesServiceAsync coursesService = GWT.create(CoursesService.class);
		coursesService.getUserInfo(new AsyncCallback<UserInfo>() {
			@Override
			public void onSuccess(UserInfo result) {
				UserService.setCurrentUserInfo(result);
				startApplication();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ошибка при загрузке приложения: "
						+ caught.getMessage());
			}
		});
	}

	private void startApplication() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);

		RootPanel.get("appContainer").add(appWidget);
		RootPanel.get("logout").add(createLogoutWidget());
		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();
	}

}
