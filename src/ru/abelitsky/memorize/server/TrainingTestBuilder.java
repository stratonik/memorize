package ru.abelitsky.memorize.server;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.abelitsky.memorize.server.model.WordStatus;
import ru.abelitsky.memorize.shared.dto.TrainingTest;
import ru.abelitsky.memorize.shared.dto.TrainingTest.TrainingTestAction;

import com.googlecode.objectify.Key;

public class TrainingTestBuilder {

	private static final String KATAKANA = "アイウエオ" + "カガキギクグケゲコゴ" + "サザシジスズセゼソゾ" + "タダチヂツヅテデトド"
			+ "ナニヌネノ" + "ハバパヒビピフブプヘベペホボポ" + "マミムメモ" + "ヤユヨ" + "ラリルレロ" + "ワ";
	private static final String HIRAGANA = "あいうえお" + "かがきぎくぐけげこご" + "さざしじすずせぜそぞ" + "ただちぢつづてでとど"
			+ "なにぬねの" + "はばぱひびぴふぶぷへべぺほぼぽ" + "まみむめも" + "やゆよ" + "らりるれろ" + "わ";

	public static TrainingTest createSelectKanaTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setAction(TrainingTestAction.selectKana);
		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKana());

		String[] variants = new String[6];
		Random random = new Random();
		for (int i = 0; i < variants.length; i++) {
			int index = 0;
			if (test.getAnswer().length() > 2) {
				index = random.nextInt(test.getAnswer().length() - 1);
			}
			char symbol = test.getAnswer().charAt(index);
			String replacement;
			if (UnicodeBlock.of(symbol) == UnicodeBlock.HIRAGANA) {
				replacement = HIRAGANA;
			} else {
				replacement = KATAKANA;
			}
			char newSymbol;
			do {
				newSymbol = replacement.charAt(random.nextInt(replacement.length()));
			} while (symbol == newSymbol);
			variants[i] = test.getAnswer().substring(0, index)
					+ newSymbol
					+ ((test.getAnswer().length() > index + 1) ? test.getAnswer().substring(
							index + 1) : "");
		}
		variants[random.nextInt(variants.length)] = test.getAnswer();
		test.setVariants(variants);

		return test;
	}

	public static TrainingTest createSelectKanjiTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setAction(TrainingTestAction.selectKanji);
		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKanji());

		List<Integer> kanjiIndexes = new ArrayList<Integer>();
		for (int i = 0; i < test.getAnswer().length(); i++) {
			if (UnicodeBlock.of(test.getAnswer().charAt(i)) == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
				kanjiIndexes.add(i);
			}
		}

		String[] variants = new String[6];
		Random random = new Random();
		for (int i = 0; i < variants.length; i++) {
			int index = kanjiIndexes.get(random.nextInt(kanjiIndexes.size()));
			char symbol = test.getAnswer().charAt(index);
			char newSymbol;
			do {
				newSymbol = (char) (0x4e00 + random.nextInt(0x9faf - 0x4e00 + 1));
			} while (symbol == newSymbol);
			variants[i] = test.getAnswer().substring(0, index)
					+ newSymbol
					+ ((test.getAnswer().length() > index + 1) ? test.getAnswer().substring(
							index + 1) : "");
		}
		variants[random.nextInt(variants.length)] = test.getAnswer();
		test.setVariants(variants);

		return test;
	}

	public static TrainingTest createShowKanaTest(WordStatus wordStatus) {
		TrainingTest test = new TrainingTest();
		test.setWord(wordStatus.getWord().toDto());

		test.setAction(TrainingTestAction.showKanaInfo);
		test.setQuestion(wordStatus.getWord().getKana());

		return test;
	}

	public static TrainingTest createShowKanjiTest(WordStatus wordStatus) {
		TrainingTest test = new TrainingTest();
		test.setWord(wordStatus.getWord().toDto());

		test.setAction(TrainingTestAction.showKanjiInfo);
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
			return createSelectKanjiTest(wordStatus);
		case 3:
			return createWriteKanjiTest(wordStatus);
		case 4:
			return createWriteKanaByKanjiTest(wordStatus);
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

		test.setAction(TrainingTestAction.writeKana);
		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKana());

		return test;
	}

	public static TrainingTest createWriteKanaByKanjiTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setAction(TrainingTestAction.writeKanaByKanji);
		test.setQuestion(wordStatus.getWord().getKanji());
		test.setAnswer(wordStatus.getWord().getKana());

		return test;
	}

	public static TrainingTest createWriteKanjiTest(WordStatus wordStatus) {
		TrainingTest test = createTrainingTestObject(wordStatus);

		test.setAction(TrainingTestAction.writeKanji);
		test.setQuestion(wordStatus.getWord().getTranslation());
		test.setAnswer(wordStatus.getWord().getKanji());

		return test;
	}

}
