package ru.abelitsky.memorize.server;

import static ru.abelitsky.memorize.server.OfyService.ofy;

import java.util.ArrayList;
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
			wordStatus.setNextTrainingDateInFourHours();
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
	public void fail(String wordStatusKey) {
		WordStatus status = ofy().load()
				.key(Key.<WordStatus> create(wordStatusKey)).get();
		status.fail();
		ofy().save().entity(status);
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
	public void pass(String wordStatusKey) {
		WordStatus status = ofy().load()
				.key(Key.<WordStatus> create(wordStatusKey)).get();
		status.pass();
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
