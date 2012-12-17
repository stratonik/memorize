package ru.abelitsky.memorize.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.abelitsky.memorize.client.service.TrainingService;
import ru.abelitsky.memorize.server.model.CourseStatus;
import ru.abelitsky.memorize.server.model.Word;
import ru.abelitsky.memorize.server.model.WordStatus;
import ru.abelitsky.memorize.shared.dto.TrainingTest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.VoidWork;

public class TrainingServiceImpl extends RemoteServiceServlet implements
		TrainingService {

	private static final long serialVersionUID = -8892629311987799151L;

	@Override
	public List<TrainingTest> addNewWordsForTraining(Long courseStatusId) {
		Objectify ofy = OfyService.ofy();
		Key<CourseStatus> statusKey = Key.create(CourseStatus.class,
				courseStatusId);
		final CourseStatus status = ofy.load().key(statusKey).get();
		List<Word> words = selectNewWordsForTraining(status);

		final List<WordStatus> wordStatuses = new ArrayList<WordStatus>(
				words.size());
		List<TrainingTest> tests = new ArrayList<TrainingTest>(words.size() * 4);
		for (Word word : words) {
			WordStatus wordStatus = new WordStatus(status, word);
			wordStatus.setNextTrainingDate(getNextTrainingDate(wordStatus));
			wordStatuses.add(wordStatus);

			tests.add(TrainingTestBuilder.createShowKanaTest(wordStatus));
			tests.add(TrainingTestBuilder.createWriteKanaTest(wordStatus));
			tests.add(TrainingTestBuilder.createShowKanjiTest(wordStatus));
			tests.add(TrainingTestBuilder.createWriteKanjiTest(wordStatus));
		}

		ofy.transact(new VoidWork() {
			@Override
			public void vrun() {
				Objectify ofy = OfyService.ofy();
				status.setKnownWordsNumber(ofy.load().type(WordStatus.class)
						.ancestor(status).count()
						+ wordStatuses.size());
				ofy.save().entity(status);
				ofy.save().entities(wordStatuses);
			}
		});
		return tests;
	}

	private Date getNextTrainingDate(WordStatus wordStatus) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 4);
		return cal.getTime();
	}

	private List<Word> selectNewWordsForTraining(CourseStatus status) {
		Objectify ofy = OfyService.ofy();
		List<WordStatus> wordStatuses = ofy.load().type(WordStatus.class)
				.ancestor(status).list();
		Set<Integer> wordIndexes = new HashSet<Integer>(wordStatuses.size());
		for (WordStatus wordStatus : wordStatuses) {
			wordIndexes.add(wordStatus.getWord().getIndex());
		}
		List<Word> words = new ArrayList<Word>();
		for (Word word : ofy.load().type(Word.class)
				.ancestor(status.getCourse()).order("index")) {
			if (!wordIndexes.contains(word.getIndex())) {
				words.add(word);
				if (words.size() >= 3) {
					break;
				}
			}
		}
		return words;
	}

}
