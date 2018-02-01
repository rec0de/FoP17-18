package campusManagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import exceptions.*;

public class TestStudentException {
	static CampusManagement management;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	@Before
	public void initTest() {
		management = new CampusManagement("Test", Semester.WiSe_17_18);
	}

	@Test(expected = InvalidValueException.class)
	public void testPastExamInvalidCP() throws ExaminationAlreadyExistsException, InvalidValueException{
		TestStudentException.management.addPastExamination("You might as well do nothing", 0, Semester.SoSe_17, LocalDateTime.parse("13.03.2017 13:37", formatter), LocalDateTime.parse("13.03.2017 22:22", formatter));
	}
	
	@Test(expected = InvalidValueException.class)
	public void testPastExamTiming() throws ExaminationAlreadyExistsException, InvalidValueException{
		TestStudentException.management.addPastExamination("Time Travel 101", 20, Semester.SoSe_17, LocalDateTime.parse("13.03.2017 13:37", formatter), LocalDateTime.parse("13.03.2017 12:12", formatter));
	}
	
	@Test(expected = ExaminationAlreadyExistsException.class)
	public void testPastExamDuplicates() throws ExaminationAlreadyExistsException, InvalidValueException{
		TestStudentException.management.addPastExamination("Advanced Gene Editing & Cloning", 20, Semester.SoSe_17, LocalDateTime.parse("05.05.2017 12:00", formatter), LocalDateTime.parse("05.05.2017 14:00", formatter));
		TestStudentException.management.addPastExamination("Advanced Gene Editing & Cloning", 20, Semester.SoSe_17, LocalDateTime.parse("05.05.2017 16:00", formatter), LocalDateTime.parse("05.05.2017 18:00", formatter));
	}
	
	@Test(expected = StudentRegistrationException.class)
	public void testAddStudentGradeRegistration() throws ExaminationAlreadyExistsException, InvalidValueException, StudentRegistrationException, GradeAlreadyExistsException{
		Examination css = management.addExamination("Computer Systems Security", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
						LocalDateTime.parse("01.03.2018 11:30", formatter));
		Student sam = management.addStudent("Sam", "Sepiol", 7991, "Computer Science B.Sc.");

		management.addExaminationGradeForStudent(1.0, sam, css);
	}
	
	@Test(expected = InvalidValueException.class)
	public void testAddStudentGradeInvalidGrade() throws ExaminationAlreadyExistsException, InvalidValueException, StudentRegistrationException, GradeAlreadyExistsException{
		Examination css = management.addExamination("Computer Systems Security", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
						LocalDateTime.parse("01.03.2018 11:30", formatter));
		Student sam = management.addStudent("Sam", "Sepiol", 7991, "Computer Science B.Sc.");
		management.registerStudentForExamination(sam, css);
		management.addExaminationGradeForStudent(2.5, sam, css);
	}
	
	@Test(expected = GradeAlreadyExistsException.class)
	public void testAddStudentGradeDuplicate() throws ExaminationAlreadyExistsException, InvalidValueException, StudentRegistrationException, GradeAlreadyExistsException{
		Examination css = management.addExamination("Computer Systems Security", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
						LocalDateTime.parse("01.03.2018 11:30", formatter));
		Student sam = management.addStudent("Sam", "Sepiol", 7991, "Computer Science B.Sc.");
		management.registerStudentForExamination(sam, css);
		management.addExaminationGradeForStudent(2.0, sam, css);
		management.addExaminationGradeForStudent(1.0, sam, css);
	}
	
	@Test(expected = InvalidValueException.class)
	public void testAddStudent() throws InvalidValueException {
		management.addStudent("Sam", "Sepiol", 7991, "Computer Science B.Sc.");
		management.addStudent("Elliot", "Alderson", 7991, "Psychology B.Sc.");
		
	}
	
	@Test(expected = StudentRegistrationException.class)
	public void testRegisterStudent() throws InvalidValueException, ExaminationAlreadyExistsException, StudentRegistrationException {
		Student elliot = management.addStudent("Elliot", "Alderson", 7991, "Psychology B.Sc.");
		Examination psych = management.addExamination("Mindreading 101", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
				LocalDateTime.parse("01.03.2018 11:30", formatter));
		management.registerStudentForExamination(elliot, psych);
		management.registerStudentForExamination(elliot, psych);
	}
	
	@Test
	public void testAddStudentWorking() throws InvalidValueException {
		Student elliot = management.addStudent("Elliot", "Alderson", 7991, "Psychology B.Sc.");
		assertTrue(management.getStudents().contains(elliot));
	}
	
	@Test
	public void testRegisterStudentWorking() throws InvalidValueException, ExaminationAlreadyExistsException, StudentRegistrationException {
		Student elliot = management.addStudent("Elliot", "Alderson", 7991, "Psychology B.Sc.");
		Examination psych = management.addExamination("Mindreading 101", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
				LocalDateTime.parse("01.03.2018 11:30", formatter));
		management.registerStudentForExamination(elliot, psych);
		assertTrue(psych.getStudentsRegistered().contains(elliot));
	}
	
	@Test
	public void testExamAddGradeWorking() throws InvalidValueException, ExaminationAlreadyExistsException, StudentRegistrationException, GradeAlreadyExistsException {
		Student elliot = management.addStudent("Elliot", "Alderson", 7991, "Psychology B.Sc.");
		Examination psych = management.addExamination("Mindreading 101", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
				LocalDateTime.parse("01.03.2018 11:30", formatter));
		management.registerStudentForExamination(elliot, psych);
		management.addExaminationGradeForStudent(5.0, elliot, psych);
		assertTrue(psych.getFilteredGrades(psych.filterGradesByStudent(elliot)).get(0).getGrade() == 5);
	}
	
	@Test
	public void testAddExaminationWorking() throws InvalidValueException, ExaminationAlreadyExistsException {
		Examination psych = management.addExamination("Split-second Decisionmaking", 1, LocalDateTime.parse("01.03.2018 09:00", formatter),
				LocalDateTime.parse("01.03.2018 09:01", formatter));
		
		assertTrue(management.getExaminations().contains(psych));
	}
}
