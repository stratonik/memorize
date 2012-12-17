package ru.abelitsky.memorize.client.view;

import java.util.List;

import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.client.ui.Composite;

public class TrainingViewImpl extends Composite implements TrainingView {

	private Presenter presenter;

	@Override
	public void setData(List<TrainingTest> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
