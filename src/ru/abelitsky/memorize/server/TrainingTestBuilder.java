package ru.abelitsky.memorize.server;

import java.util.Random;

import ru.abelitsky.memorize.server.model.WordStatus;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestAction;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestType;

import com.googlecode.objectify.Key;

public class TrainingTestBuilder {

	private static final String KATAKANA = "アイウエオ" + "カガキギクグケゲコゴ"
			+ "サザシジスズセゼソゾ" + "タダチヂツヅテデトド" + "ナニヌネノ" + "ハバパヒビピフブプヘベペホボポ"
			+ "マミムメモ" + "ヤユヨ" + "ラリルレロ" + "ワ";
	private static final String HIRAGANA = "あいうえお" + "かがきぎくぐけげこご"
			+ "さざしじすずせぜそぞ" + "ただちぢつづてでとど" + "なにぬねの" + "はばぱひびぴふぶぷへべぺほぼぽ"
			+ "まみむめも" + "やゆよ" + "らりるれろ" + "わ";

	public static TrainingTest createSelectKanaTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setType(TrainingTestType.kana);
		test.setAction(TrainingTestAction.selectVariant);

		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKana());

		test.setVariants(new String[6]);
		Random random = new Random();
		for (int i = 0; i < test.getVariants().length; i++) {
			int index = 0;
			if (test.getAnswer().length() > 2) {
				index = random.nextInt(test.getAnswer().length() - 1);
			}
			char symbol = test.getAnswer().charAt(index);
			String replacement;
			if ((symbol >= 0x3040) && (symbol <= 0x309f)) {
				replacement = HIRAGANA;
			} else {
				replacement = KATAKANA;
			}
			char newSymbol;
			do {
				newSymbol = replacement.charAt(random.nextInt(replacement
						.length()));
			} while (symbol == newSymbol);
			test.getVariants()[i] = test.getAnswer().substring(0, index)
					+ newSymbol
					+ ((test.getAnswer().length() > index + 1) ? test
							.getAnswer().substring(index + 1) : "");
		}
		test.getVariants()[random.nextInt(test.getVariants().length)] = test
				.getAnswer();

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
			return createSelectKanaTest(wordStatus);
		case 1:
			return createWriteKanaTest(wordStatus);
		case 2:
			return createWriteKanjiTest(wordStatus);
		}
		return null;
	}

	private static TrainingTest createTrainingTestObject(WordStatus wordStatus) {
		TrainingTest test = new TrainingTest();
		if (wordStatus.getId() != null) {
			test.setWordStatusKey(Key.create(wordStatus).getString());
		}
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
