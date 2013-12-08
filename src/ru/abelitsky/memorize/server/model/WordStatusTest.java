package ru.abelitsky.memorize.server.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordStatusTest {

	@Test
	public void testPass() {
		final WordMock wordWithKanji = new WordMock(true);

		WordStatus wordStatus = new WordStatus() {
			@Override
			public Word getWord() {
				return wordWithKanji;
			}
		};

		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 0);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 2);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 3);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 0);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 2);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 3);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 2);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 2);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 3);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 3);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 4);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 4);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 5);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 5);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 6);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 6);
		assertEquals(wordStatus.getSubLevel(), 4);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 6);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 6);
		assertEquals(wordStatus.getSubLevel(), 4);

		final WordMock wordWithoutKanji = new WordMock(false);

		wordStatus = new WordStatus() {
			@Override
			public Word getWord() {
				return wordWithoutKanji;
			}
		};

		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 0);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 0);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 0);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 1);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 2);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 3);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 4);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 5);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 6);
		assertEquals(wordStatus.getSubLevel(), 1);

		wordStatus.pass();
		assertEquals(wordStatus.getLevel(), 6);
		assertEquals(wordStatus.getSubLevel(), 1);
	}

	private class WordMock extends Word {

		private boolean hasKanji;

		public WordMock(boolean hasKanji) {
			this.hasKanji = hasKanji;
		}

		@Override
		public boolean hasKanji() {
			return hasKanji;
		}
	}
}
