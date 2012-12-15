package ru.abelitsky.memorize.server;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.abelitsky.memorize.client.service.CoursesService;
import ru.abelitsky.memorize.server.model.Course;
import ru.abelitsky.memorize.server.model.CourseStatus;
import ru.abelitsky.memorize.server.model.Word;
import ru.abelitsky.memorize.server.model.WordStatus;
import ru.abelitsky.memorize.shared.dto.CourseDTO;
import ru.abelitsky.memorize.shared.dto.CourseInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;
import au.com.bytecode.opencsv.CSVReader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.Saver;

public class CoursesServiceImpl extends RemoteServiceServlet implements
		CoursesService {

	private static final long serialVersionUID = -3744122268386375444L;

	@Override
	public CourseInfo createCourseStatus(Long courseId) {
		Objectify ofy = OfyService.ofy();
		Key<Course> courseKey = Key.create(Course.class, courseId);
		CourseStatus status = ofy.load().type(CourseStatus.class)
				.filter("course", courseKey).first().get();
		if (status == null) {
			status = new CourseStatus(courseKey);
			Key<CourseStatus> statusKey = ofy.save().entity(status).now();
			status = ofy.load().key(statusKey).get();
		}
		CourseInfo info = new CourseInfo(status.getCourse().toDto());
		info.setStatus(status.toDto());
		return info;
	}

	@Override
	public List<CourseDTO> deleteCourse(Long id) {
		Objectify ofy = OfyService.ofy();
		final Key<Course> courseKey = Key.create(Course.class, id);

		List<Key<CourseStatus>> courseStatusKeys = new ArrayList<Key<CourseStatus>>(
				ofy.load().type(CourseStatus.class).filter("course", courseKey)
						.keys().list());
		for (final Key<CourseStatus> courseStatusKey : courseStatusKeys) {
			ofy.transact(new VoidWork() {
				@Override
				public void vrun() {
					Objectify ofy = OfyService.ofy();
					ofy.delete().key(courseStatusKey).now();
					ofy.delete().keys(
							ofy.load().type(WordStatus.class)
									.ancestor(courseStatusKey).keys());
				}
			});
		}

		ofy.transact(new VoidWork() {
			@Override
			public void vrun() {
				Objectify ofy = OfyService.ofy();
				ofy.delete().key(courseKey).now();
				ofy.delete().keys(
						ofy.load().type(Word.class).ancestor(courseKey).keys());
			}
		});

		return getCourses();
	}

	@Override
	public CourseInfo deleteCourseStatus(Long statusId) {
		Objectify ofy = OfyService.ofy();
		final Key<CourseStatus> statusKey = Key.create(CourseStatus.class,
				statusId);
		CourseStatus status = ofy.load().key(statusKey).get();
		CourseInfo info = new CourseInfo(status.getCourse().toDto());
		ofy.transact(new VoidWork() {
			@Override
			public void vrun() {
				Objectify ofy = OfyService.ofy();
				ofy.delete().key(statusKey).now();
				ofy.delete().keys(
						ofy.load().type(WordStatus.class).ancestor(statusKey)
								.keys());
			}
		});
		return info;
	}

	@Override
	public CourseDTO getCourse(Long id) {
		Objectify ofy = OfyService.ofy();
		return ofy.load().key(Key.create(Course.class, id)).get().toDto();
	}

	@Override
	public CourseInfo getCourseInfo(Long id) {
		Objectify ofy = OfyService.ofy();
		Key<Course> courseKey = Key.create(Course.class, id);
		CourseStatus status = ofy.load().type(CourseStatus.class)
				.filter("course", courseKey).first().get();

		CourseInfo info;
		if (status != null) {
			info = new CourseInfo(status.getCourse().toDto());
			info.setStatus(status.toDto());
			info.getStatus().setReadyForTrainingWordsNumber(
					getReadyForTrainingWordsNumber(status));
		} else {
			info = new CourseInfo(ofy.load().key(courseKey).get().toDto());
		}
		return info;
	}

	@Override
	public List<CourseDTO> getCourses() {
		List<CourseDTO> courses = new ArrayList<CourseDTO>();
		for (Course course : OfyService.ofy().load().type(Course.class).list()) {
			courses.add(course.toDto());
		}
		Collections.sort(courses);
		return courses;
	}

	@Override
	public List<CourseInfo> getStatuses() {
		List<CourseStatus> statuses = OfyService.ofy().load()
				.type(CourseStatus.class).list();
		List<CourseInfo> infos = new ArrayList<CourseInfo>(statuses.size());
		for (CourseStatus status : statuses) {
			CourseInfo info = new CourseInfo(status.getCourse().toDto());
			info.setStatus(status.toDto());
			info.getStatus().setReadyForTrainingWordsNumber(
					getReadyForTrainingWordsNumber(status));
			infos.add(info);
		}
		Collections.sort(infos);
		return infos;
	}

	private int getReadyForTrainingWordsNumber(CourseStatus courseStatus) {
		return OfyService.ofy().load().type(WordStatus.class)
				.ancestor(courseStatus)
				.filter("nextTrainingDate <", new Date()).count();
	}

	@Override
	public List<WordDTO> getWords(Long courseId, int beginIndex, int count) {
		Key<Course> courseKey = Key.create(Course.class, courseId);
		List<Word> words = OfyService.ofy().load().type(Word.class)
				.ancestor(courseKey).order("index")
				.filter("index >=", beginIndex).limit(count).list();
		List<WordDTO> result = new ArrayList<WordDTO>(words.size());
		for (Word word : words) {
			result.add(word.toDto());
		}
		return result;
	}

	@Override
	public void loadWords(Long courseId, String data) {
		Objectify ofy = OfyService.ofy();
		Ref<Course> courseRef = Ref.create(Key.create(Course.class, courseId));
		final Course course = ofy.load().ref(courseRef).get();
		if (course == null) {
			return;
		}

		ofy.transact(new VoidWork() {
			@Override
			public void vrun() {
				Objectify ofy = OfyService.ofy();
				course.setWordsNumber(0);
				ofy.save().entity(course).now();
				ofy.delete()
						.keys(ofy.load().type(Word.class).ancestor(course)
								.keys()).now();
			}
		});

		StringReader reader = new StringReader(data);
		CSVReader csvReader = new CSVReader(reader);
		final List<Word> words = new LinkedList<Word>();
		for (String[] fields : csvReader) {
			Word word = new Word();
			words.add(word);
			word.setCourse(courseRef);
			word.setIndex(words.size());
			word.setKana(fields[0]);
			word.setKanji(fields[1]);
			word.setTranslation(fields[2]);
			word.setAdditionalInfo(fields[3]);
		}
		try {
			csvReader.close();
			reader.close();
		} catch (IOException e) {
		}

		ofy.transact(new VoidWork() {
			@Override
			public void vrun() {
				course.setWordsNumber(words.size());
				Saver saver = OfyService.ofy().save();
				saver.entity(course).now();
				saver.entities(words).now();
			}
		});

		removeObsoleteWordStatuses(courseRef);
	}

	private void removeObsoleteWordStatuses(Ref<Course> courseRef) {
		Objectify ofy = OfyService.ofy();
		List<CourseStatus> courseStatuses = ofy.load().type(CourseStatus.class)
				.filter("course", courseRef).list();
		for (final CourseStatus courseStatus : courseStatuses) {
			ofy.transact(new VoidWork() {
				@Override
				public void vrun() {
					Objectify ofy = OfyService.ofy();
					List<WordStatus> obsoleteWordStatuses = new LinkedList<WordStatus>();
					List<WordStatus> wordStatuses = ofy.load()
							.type(WordStatus.class).ancestor(courseStatus)
							.list();
					for (WordStatus wordStatus : wordStatuses) {
						if (wordStatus.getWord() == null) {
							obsoleteWordStatuses.add(wordStatus);
						}
					}
					ofy.delete().entities(obsoleteWordStatuses).now();
					courseStatus.setKnownWordsNumber(wordStatuses.size()
							- obsoleteWordStatuses.size());
					ofy.save().entity(courseStatus).now();
				}
			});
		}
	}

	@Override
	public void saveCourse(CourseDTO courseDto) {
		Objectify ofy = OfyService.ofy();
		Course course = null;
		if (courseDto.getId() != null) {
			course = ofy.load()
					.key(Key.create(Course.class, courseDto.getId())).get();
		}
		if (course == null) {
			course = new Course();
		}
		course.fromDto(courseDto);
		OfyService.ofy().save().entity(course).now();
	}

}
