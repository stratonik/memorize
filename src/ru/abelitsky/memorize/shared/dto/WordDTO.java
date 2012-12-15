package ru.abelitsky.memorize.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WordDTO implements IsSerializable {

	private String id;
	private Integer index;
	private String kana;
	private String kanji;
	private String translation;
	private String additionalInfo;

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public String getId() {
		return id;
	}

	public Integer getIndex() {
		return index;
	}

	public String getKana() {
		return kana;
	}

	public String getKanji() {
		return kanji;
	}

	public String getTranslation() {
		return translation;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setKana(String kana) {
		this.kana = kana;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

}
