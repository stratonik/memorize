package ru.abelitsky.memorize.server;

import static ru.abelitsky.memorize.server.OfyService.ofy;

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
import ru.abelitsky.memorize.shared.dto.UserInfo;
import ru.abelitsky.memorize.shared.dto.WordDTO;
import au.com.bytecode.opencsv.CSVReader;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;

public class CoursesServiceImpl extends RemoteServiceServlet implements
		CoursesService {

	private static final long serialVersionUID = -3744122268386375444L;

	@Override
	public List<CourseDTO> deleteCourse(Long id) {
		final Key<Course> courseKey = Key.create(Course.class, id);

		List<Key<CourseStatus>> courseStatusKeys = new ArrayList<Key<CourseStatus>>(
				ofy().load().type(CourseStatus.class)
						.filter("course", courseKey).keys().list());
		for (final Key<CourseStatus> courseStatusKey : courseStatusKeys) {
			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {
					ofy().delete().key(courseStatusKey).now();
					ofy().delete().keys(
							ofy().load().type(WordStatus.class)
									.ancestor(courseStatusKey).keys());
				}
			});
		}

		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				ofy().delete().key(courseKey).now();
				ofy().delete().keys(
						ofy().load().type(Word.class).ancestor(courseKey)
								.keys());
			}
		});

		return getCourses();
	}

	@Override
	public CourseInfo getCourseInfo(Long id) {
		Key<Course> courseKey = Key.create(Course.class, id);
		CourseStatus status = ofy()
				.load()
				.type(CourseStatus.class)
				.filter("course", courseKey)
				.filter("user",
						UserServiceFactory.getUserService().getCurrentUser())
				.first().get();

		CourseInfo info;
		if (status != null) {
			info = new CourseInfo(status.getCourse().toDto());
			info.setStatus(status.toDto());
			info.getStatus().setReadyForTrainingWordsNumber(
					getReadyForTrainingWordsNumber(status));
		} else {
			info = new CourseInfo(ofy().load().key(courseKey).get().toDto());
		}
		return info;
	}

	@Override
	public List<CourseDTO> getCourses() {
		List<CourseDTO> courses = new ArrayList<CourseDTO>();
		for (Course course : ofy().load().type(Course.class).list()) {
			courses.add(course.toDto());
		}
		Collections.sort(courses);
		return courses;
	}

	@Override
	public List<CourseInfo> getStatuses() {
		List<CourseStatus> statuses = ofy()
				.load()
				.type(CourseStatus.class)
				.filter("user",
						UserServiceFactory.getUserService().getCurrentUser())
				.list();
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
		return ofy().load().type(WordStatus.class).ancestor(courseStatus)
				.filter("nextTrainingDate <", new Date()).count();
	}

	@Override
	public UserInfo getUserInfo() {
		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn()) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(userService.getCurrentUser().getNickname());
			userInfo.setAdmin(userService.isUserAdmin());
			userInfo.setLogoutUrl(userService.createLogoutURL("/Memorize.html"));
			return userInfo;
		} else {
			return null;
		}
	}

	@Override
	public List<WordDTO> getWords(Long courseId, int beginIndex, int count) {
		Key<Course> courseKey = Key.create(Course.class, courseId);
		List<Word> words = ofy().load().type(Word.class).ancestor(courseKey)
				.order("index").filter("index >=", beginIndex).limit(count)
				.list();
		List<WordDTO> result = new ArrayList<WordDTO>(words.size());
		for (Word word : words) {
			result.add(word.toDto());
		}
		return result;
	}

	@Override
	public void importWords(Long courseId, String data) {
		Ref<Course> courseRef = Ref.create(Key.create(Course.class, courseId));
		final Course course = ofy().load().ref(courseRef).get();
		if (course == null) {
			return;
		}

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
		course.setWordsNumber(words.size());

		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				ofy().delete()
						.keys(ofy().load().type(Word.class).ancestor(course)
								.keys()).now();
				ofy().save().entities(words).now();
				ofy().save().entity(course).now();
			}
		});

		removeObsoleteWordStatuses(courseRef);
	}

	private void removeObsoleteWordStatuses(Ref<Course> courseRef) {
		List<CourseStatus> courseStatuses = ofy().load()
				.type(CourseStatus.class).filter("course", courseRef).list();
		for (final CourseStatus courseStatus : courseStatuses) {
			final List<WordStatus> obsoleteWordStatuses = new LinkedList<WordStatus>();
			final List<WordStatus> wordStatuses = ofy().load()
					.type(WordStatus.class).ancestor(courseStatus).list();
			for (WordStatus wordStatus : wordStatuses) {
				if (wordStatus.getWord() == null) {
					obsoleteWordStatuses.add(wordStatus);
				}
			}

			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {
					ofy().delete().entities(obsoleteWordStatuses).now();
					courseStatus.setKnownWordsNumber(wordStatuses.size()
							- obsoleteWordStatuses.size());
					ofy().save().entity(courseStatus).now();
				}
			});
		}
	}

	@Override
	public void saveCourse(CourseDTO courseDto) {
		Course course = null;
		if (courseDto.getId() != null) {
			course = ofy().load()
					.key(Key.create(Course.class, courseDto.getId())).get();
		}
		if (course == null) {
			course = new Course();
		}
		course.fromDto(courseDto);
		ofy().save().entity(course).now();
	}

}
