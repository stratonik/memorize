package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.client.place.ParameterNames;
import ru.abelitsky.memorize.client.ui.RepeatWordsButton;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;

public class ViewCourseViewImpl extends Composite implements ViewCourseView {

	interface ViewCourseViewImplUiBinder extends UiBinder<VerticalPanel, ViewCourseViewImpl> {
	}

	private static ViewCourseViewImplUiBinder uiBinder = GWT
			.create(ViewCourseViewImplUiBinder.class);

	private Presenter presenter;

	private CourseInfo courseInfo;

	@UiField
	Label title;
	@UiField
	Label description;
	@UiField
	Label wordsNumber;
	@UiField
	Button startCourse;
	@UiField
	Button stopCourse;
	@UiField
	Panel actions;
	@UiField
	RepeatWordsButton repeatWords;
	@UiField
	Button learnNewWords;
	@UiField
	VerticalPanel wordsPanel;

	CellTable<WordDTO> wordsList;

	public ViewCourseViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		wordsList = new CellTable<WordDTO>(WORDS_PER_PAGE);
		wordsList.setWidth("100%");
		wordsList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		wordsList.addColumn(new TextColumn<WordDTO>() {
			@Override
			public String getValue(WordDTO object) {
				return object.getKana();
			}
		}, "Слово");
		wordsList.addColumn(new TextColumn<WordDTO>() {
			@Override
			public String getValue(WordDTO object) {
				return object.getKanji();
			}
		}, "Иероглифы");
		TextColumn<WordDTO> translation = new TextColumn<WordDTO>() {
			@Override
			public String getValue(WordDTO object) {
				return object.getTranslation();
			}
		};
		translation.setCellStyleNames("translationColumn");
		wordsList.addColumn(translation, "Перевод");
		wordsList.addRangeChangeHandler(new RangeChangeEvent.Handler() {
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				Range range = event.getNewRange();
				int start = range.getStart();
				int length = range.getLength();
				presenter.getWords(start, length);
			}
		});
		wordsPanel.add(wordsList);

		SimplePager pager = new SimplePager();
		pager.setDisplay(wordsList);
		wordsPanel.add(pager);
	}

	@UiHandler("courses")
	void onClickCourses(ClickEvent event) {
		presenter.goTo(presenter.getBackPlace());
	}

	@UiHandler("learnNewWords")
	void onClickLearnNewWords(ClickEvent event) {
		presenter.startTraining(courseInfo, ParameterNames.ADD_NEW_WORDS);
	}

	@UiHandler("loadWords")
	void onClickLoadWords(ClickEvent event) {
		presenter.goTo(presenter.getLoadWordsPlace());
	}

	@UiHandler("repeatWords")
	void onClickRepeatWords(ClickEvent event) {
		presenter.startTraining(courseInfo, ParameterNames.REPEAT_WORDS);
	}

	@UiHandler("startCourse")
	void onClickStartCourse(ClickEvent event) {
		presenter.startCourse(courseInfo);
	}

	@UiHandler("stopCourse")
	void onClickStopCourse(ClickEvent event) {
		presenter.stopCourse(courseInfo);
	}

	@Override
	public void prepareView() {
		title.setText("Загрузка данных...");
		description.setText("");
		wordsNumber.setText("");
		startCourse.setVisible(false);
		stopCourse.setVisible(false);
		actions.setVisible(false);
		wordsPanel.setVisible(false);
		wordsList.setVisibleRangeAndClearData(new Range(0, WORDS_PER_PAGE), false);
	}

	@Override
	public void setData(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;

		title.setText(courseInfo.getCourse().getName());
		description.setText(courseInfo.getCourse().getDescription());
		if (courseInfo.getStatus() == null) {
			wordsNumber.setText("Слов: " + courseInfo.getCourse().getWordsNumber());
		} else {
			wordsNumber.setText("Слов: " + courseInfo.getCourse().getWordsNumber()
					+ " (из них изучено: " + courseInfo.getStatus().getKnownWordsNumber() + ")");
		}
		startCourse.setVisible(courseInfo.getStatus() == null);
		stopCourse.setVisible(courseInfo.getStatus() != null);
		actions.setVisible(courseInfo.getStatus() != null);
		if (courseInfo.getStatus() != null) {
			repeatWords.setWordsCount(courseInfo.getStatus().getReadyForTrainingWordsNumber());
			learnNewWords.setEnabled((courseInfo.getCourse().getWordsNumber() - courseInfo
					.getStatus().getKnownWordsNumber()) > 0);
		}
		wordsList.setRowCount(courseInfo.getCourse().getWordsNumber());
	}

	@Override
	public void setData(List<WordDTO> words) {
		if (!words.isEmpty()) {
			wordsList.setRowData(words.get(0).getIndex() - 1, words);
		} else {
			wordsList.setRowData(0, words);
		}
		if (!wordsPanel.isVisible()) {
			wordsPanel.setVisible(true);
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
