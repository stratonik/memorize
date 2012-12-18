package ru.abelitsky.memorize.server;

import static ru.abelitsky.memorize.server.OfyService.ofy;

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
import com.googlecode.objectify.VoidWork;

public class TrainingServiceImpl extends RemoteServiceServlet implements
		TrainingService {

	private static final long serialVersionUID = -8892629311987799151L;

	@Override
	public List<TrainingTest> addNewWordsToTraining(Long courseStatusId) {
		Key<CourseStatus> statusKey = Key.create(CourseStatus.class,
				courseStatusId);
		final CourseStatus status = ofy().load().key(statusKey).get();
		List<Word> words = selectNewWordsForTraining(status);

		final List<WordStatus> wordStatuses = new ArrayList<WordStatus>(
				words.size());
		List<TrainingTest> tests = new ArrayList<TrainingTest>(words.size() * 4);
		for (Word word : words) {
			WordStatus wordStatus = new WordStatus(status, word);
			wordStatus
					.setNextTrainingDate(getNextSublevelTrainingDate(wordStatus));
			wordStatuses.add(wordStatus);

			tests.add(TrainingTestBuilder.createShowKanaTest(wordStatus));
			tests.add(TrainingTestBuilder.createWriteKanaTest(wordStatus));
			if (word.hasKanji()) {
				tests.add(TrainingTestBuilder.createShowKanjiTest(wordStatus));
				tests.add(TrainingTestBuilder.createWriteKanjiTest(wordStatus));
			}
		}

		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				status.setKnownWordsNumber(ofy().load().type(WordStatus.class)
						.ancestor(status).count()
						+ wordStatuses.size());
				ofy().save().entity(status);
				ofy().save().entities(wordStatuses);
			}
		});
		return tests;
	}

	@Override
	public void fail(Long wordStatusId) {
		WordStatus status = ofy().load()
				.key(Key.create(WordStatus.class, wordStatusId)).get();
		status.setLevel(0);
		status.setNextTrainingDate(getNextSublevelTrainingDate(status));
		ofy().save().entity(status);
	}

	private Date getNextLevelTrainingDate(WordStatus wordStatus) {
		Calendar cal = Calendar.getInstance();
		switch (wordStatus.getLevel()) {
		case 1:
			cal.add(Calendar.DAY_OF_MONTH, 3);
			break;
		case 2:
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			break;
		case 3:
			cal.add(Calendar.WEEK_OF_YEAR, 2);
			break;
		case 4:
			cal.add(Calendar.MONTH, 1);
			break;
		case 5:
			cal.add(Calendar.MONTH, 2);
			break;
		case 6:
			cal.add(Calendar.MONTH, 3);
			break;
		}
		return cal.getTime();
	}

	private Date getNextSublevelTrainingDate(WordStatus wordStatus) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 4);
		return cal.getTime();
	}

	@Override
	public List<TrainingTest> getWordsForTraining(Long courseStatusId) {
		Key<CourseStatus> statusKey = Key.create(CourseStatus.class,
				courseStatusId);
		List<WordStatus> wordStatuses = ofy().load().type(WordStatus.class)
				.ancestor(statusKey).filter("nextTrainingDate <", new Date())
				.list();
		List<TrainingTest> tests = new ArrayList<TrainingTest>(
				wordStatuses.size());
		for (WordStatus wordStatus : wordStatuses) {
			TrainingTest test = TrainingTestBuilder.createTest(wordStatus);
			if (test != null) {
				tests.add(test);
			}
		}
		return tests;
	}

	@Override
	public void pass(Long wordStatusId) {
		WordStatus status = ofy().load()
				.key(Key.create(WordStatus.class, wordStatusId)).get();
		status.setSubLevel(status.getSubLevel() + 1);
		if ((!status.getWord().hasKanji() && (status.getSubLevel() >= 1))
				|| (status.getSubLevel() >= 2)) {
			status.setSubLevel(0);
			status.setLevel(status.getLevel() + 1);
			status.setNextTrainingDate(getNextLevelTrainingDate(status));
		} else {
			status.setNextTrainingDate(getNextSublevelTrainingDate(status));
		}
		ofy().save().entity(status);
	}

	private List<Word> selectNewWordsForTraining(CourseStatus status) {
		List<WordStatus> wordStatuses = ofy().load().type(WordStatus.class)
				.ancestor(status).list();
		Set<Integer> wordIndexes = new HashSet<Integer>(wordStatuses.size());
		for (WordStatus wordStatus : wordStatuses) {
			wordIndexes.add(wordStatus.getWord().getIndex());
		}
		List<Word> words = new ArrayList<Word>();
		for (Word word : ofy().load().type(Word.class)
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
