package ru.abelitsky.memorize.server;

import ru.abelitsky.memorize.server.model.WordStatus;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestAction;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;

public class TrainingTestBuilder {

	public static TrainingTest createSelectKanaTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kana);
		test.setAction(TrainingTestAction.selectVariant);

		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKana());
		// TODO: set variants

		return test;
	}

	public static TrainingTest createSelectKanjiTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kanji);
		test.setAction(TrainingTestAction.selectVariant);

		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKanji());
		// TODO: set variants

		return test;
	}

	public static TrainingTest createSelectTranslationByKanaTest(
			WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kana);
		test.setAction(TrainingTestAction.selectTranslation);

		test.setQuestion(wordStatus.getWord().getKana());
		test.setAnswer(wordStatus.getWord().getTranslation());
		// TODO: set variants

		return test;
	}

	public static TrainingTest createSelectTranslationByKanjiTest(
			WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kanji);
		test.setAction(TrainingTestAction.selectTranslation);

		test.setQuestion(wordStatus.getWord().getKanji());
		test.setAnswer(wordStatus.getWord().getTranslation());
		// TODO: set variants

		return test;
	}

	public static TrainingTest createShowKanaTest(WordStatus wordStatus) {
		TrainingTest test = new TrainingTest();
		test.setWord(wordStatus.getWord().toDto());

		test.setType(TrainingTestType.kana);
		test.setAction(TrainingTestAction.showInfo);

		test.setQuestion(wordStatus.getWord().getKana());

		return test;
	}

	public static TrainingTest createShowKanjiTest(WordStatus wordStatus) {
		TrainingTest test = new TrainingTest();
		test.setWord(wordStatus.getWord().toDto());

		test.setType(TrainingTestType.kanji);
		test.setAction(TrainingTestAction.showInfo);

		test.setQuestion(wordStatus.getWord().getKanji());

		return test;
	}

	public static TrainingTest createTest(WordStatus wordStatus) {
		switch (wordStatus.getSubLevel()) {
		case 0:
			return createWriteKanaTest(wordStatus);
		case 1:
			return createWriteKanjiTest(wordStatus);
		}
		return null;
	}

	private static TrainingTest createTrainingTestObject(WordStatus wordStatus) {
		TrainingTest test = new TrainingTest();
		test.setWordStatusId(wordStatus.getId());
		test.setWord(wordStatus.getWord().toDto());
		return test;
	}

	public static TrainingTest createWriteKanaTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kana);
		test.setAction(TrainingTestAction.writeAnswer);

		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKana());

		return test;
	}

	public static TrainingTest createWriteKanjiTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kanji);
		test.setAction(TrainingTestAction.writeAnswer);

		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKanji());

		return test;
	}

}
