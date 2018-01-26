package campusManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;

public class TestStudentStream {
	static double epsilon = 0.0001;

	private static CampusManagement management;

	private static Examination vc;
	private static Examination fop;
	private static Examination cs;
	private static Examination math1;
	private static Examination se;
	private static Examination math2;

	private static Student johnDoe;
	private static Student peterClark;
	private static Student taylorSmith;
	private static Student annaWilliams;

	@BeforeClass
	public static void initDataBeforeClass() {
		management = new CampusManagement("Test System", Semester.WiSe_17_18);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

		// example data
		vc = management.addExamination("Visual Computing", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
				LocalDateTime.parse("01.03.2018 11:30", formatter));
		fop = management.addExamination("Functional and Object-oriented Programming Concepts ", 10,
				LocalDateTime.parse("02.04.2018 13:30", formatter), LocalDateTime.parse("02.04.2018 15:00", formatter));
		cs = management.addExamination("Computer Security", 5, LocalDateTime.parse("12.02.2018 08:00", formatter),
				LocalDateTime.parse("12.02.2018 09:30", formatter));

		math1 = management.addPastExamination("Mathematics I", 9, Semester.SoSe_17,
				LocalDateTime.parse("05.10.2017 09:00", formatter), LocalDateTime.parse("05.10.2017 11:30", formatter));
		se = management.addPastExamination("Software Engineering", 5, Semester.SoSe_17,
				LocalDateTime.parse("25.09.2017 13:30", formatter), LocalDateTime.parse("25.09.2017 15:00", formatter));
		math2 = management.addPastExamination("Mathematics II", 9, Semester.WiSe_16_17,
				LocalDateTime.parse("03.04.2017 08:00", formatter), LocalDateTime.parse("03.04.2017 09:30", formatter));

		johnDoe = management.addStudent("John", "Doe", 11111, "Computer Science B.Sc.");
		peterClark = management.addStudent("Peter", "Clark", 22222, "Computer Science B.Ed.");
		taylorSmith = management.addStudent("Taylor", "Smith", 33333, "Computer Science B.Sc.");
		annaWilliams = management.addStudent("Anna", "Williams", 44444, "Computer Science B.Sc.");

		management.registerStudentForExamination(johnDoe, math1);
		management.registerStudentForExamination(johnDoe, se);
		management.registerStudentForExamination(johnDoe, math2);
		management.addExaminationGradeForStudent(2.0, johnDoe, math1);
		management.addExaminationGradeForStudent(2.0, johnDoe, se);
		management.addExaminationGradeForStudent(2.0, johnDoe, math2);

		management.registerStudentForExamination(peterClark, math1);
		management.registerStudentForExamination(peterClark, se);
		management.registerStudentForExamination(peterClark, math2);
		management.addExaminationGradeForStudent(4.0, peterClark, math1);
		management.addExaminationGradeForStudent(1.7, peterClark, se);
		management.addExaminationGradeForStudent(3.7, peterClark, math2);

		management.registerStudentForExamination(taylorSmith, math1);
		management.registerStudentForExamination(taylorSmith, se);
		management.registerStudentForExamination(taylorSmith, math2);
		management.addExaminationGradeForStudent(1.3, taylorSmith, math1);
		management.addExaminationGradeForStudent(2.0, taylorSmith, se);
		management.addExaminationGradeForStudent(1.7, taylorSmith, math2);

		management.registerStudentForExamination(annaWilliams, math1);
		management.addExaminationGradeForStudent(1.0, annaWilliams, math1);
	}

}
