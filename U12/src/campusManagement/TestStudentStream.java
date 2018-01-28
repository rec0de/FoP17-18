package campusManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

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
	
	@Test
	public void testStudentNameFilter() {
		List<Student> result = management.getFilteredStudents(management.filterStudentsByName("Peter", "Clark"));
		List<Student> expected = new LinkedList<Student>();
		expected.add(peterClark);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = management.getFilteredStudents(management.filterStudentsByName("Sam", "Sepiol"));
		expected = new LinkedList<Student>();
		
		assertEquals(result, expected);
		assertEquals(result.size(), 0);
	}


	@Test
	public void testStudentMatriculationFilter() {
		List<Student> result = management.getFilteredStudents(management.filterStudentsByMatriculationNumber(44444));
		List<Student> expected = new LinkedList<Student>();
		expected.add(annaWilliams);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = management.getFilteredStudents(management.filterStudentsByMatriculationNumber(1337));
		expected = new LinkedList<Student>();
		
		assertEquals(result, expected);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testStudentCOSFilter() {
		List<Student> result = management.getFilteredStudents(management.filterStudentsByCourseOfStudies("Computer Science B.Sc."));
		List<Student> expected = new LinkedList<Student>();
		expected.add(johnDoe);
		expected.add(taylorSmith);
		expected.add(annaWilliams);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 3);
		
		result = management.getFilteredStudents(management.filterStudentsByCourseOfStudies("Computer Science B.Ed."));
		expected = new LinkedList<Student>();
		expected.add(peterClark);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = management.getFilteredStudents(management.filterStudentsByCourseOfStudies("Psychology B.Sc."));
		expected = new LinkedList<Student>();
		
		assertEquals(result, expected);
		assertEquals(result.size(), 0);
	}

	@Test
	public void testExamNameFilter() {
		List<Examination> result = management.getFilteredExaminations(management.filterExaminationsByName("Computer Security"));
		List<Examination> expected = new LinkedList<Examination>();
		expected.add(cs);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = management.getFilteredExaminations(management.filterExaminationsByName("Mathematics II"));
		expected = new LinkedList<Examination>();
		expected.add(math2);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = management.getFilteredExaminations(management.filterExaminationsByName("Computional Engineering"));
		expected = new LinkedList<Examination>();
		
		assertEquals(result, expected);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testExamCPFilter() {
		List<Examination> result = management.getFilteredExaminations(management.filterExaminationsByCreditPoints(5));
		List<Examination> expected = new LinkedList<Examination>();
		expected.add(vc);
		expected.add(cs);
		expected.add(se);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 3);
		
		result = management.getFilteredExaminations(management.filterExaminationsByCreditPoints(9));
		expected = new LinkedList<Examination>();
		expected.add(math1);
		expected.add(math2);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 2);
		
		result = management.getFilteredExaminations(management.filterExaminationsByCreditPoints(15));
		expected = new LinkedList<Examination>();
		
		assertEquals(result, expected);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testExamSemesterFilter() {
		List<Examination> result = management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.WiSe_17_18));
		List<Examination> expected = new LinkedList<Examination>();
		expected.add(vc);
		expected.add(fop);
		expected.add(cs);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 3);
		
		result = management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.SoSe_17));
		expected = new LinkedList<Examination>();
		expected.add(math1);
		expected.add(se);
		
		assertEquals(result, expected);
		assertEquals(result.size(), 2);
		
		result = management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.WiSe_15_16));
		expected = new LinkedList<Examination>();
		
		assertEquals(result, expected);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testGradeGradeFilter() {
		List<ExaminationGrade> result = se.getFilteredGrades(se.filterGradesByGrade(2.0));
		List<ExaminationGrade> expected = new LinkedList<ExaminationGrade>();
		expected.add(se.getGrades().get(0));
		expected.add(se.getGrades().get(2));
		
		assertEquals(result, expected);
		assertEquals(result.size(), 2);
		
		result = se.getFilteredGrades(se.filterGradesByGrade(1.7));
		expected = new LinkedList<ExaminationGrade>();
		expected.add(se.getGrades().get(1));

		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = se.getFilteredGrades(se.filterGradesByGrade(1.0));
		expected = new LinkedList<ExaminationGrade>();

		assertEquals(result, expected);
		assertEquals(result.size(), 0);
		
		result = math1.getFilteredGrades(math1.filterGradesByGrade(1.0));
		expected = new LinkedList<ExaminationGrade>();
		expected.add(math1.getGrades().get(3));

		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = math1.getFilteredGrades(math1.filterGradesByGrade(4.0));
		expected = new LinkedList<ExaminationGrade>();
		expected.add(math1.getGrades().get(1));

		assertEquals(result, expected);
		assertEquals(result.size(), 1);
	}
	
	@Test
	public void testGradeStudentFilter() {
		List<ExaminationGrade> result = math1.getFilteredGrades(math1.filterGradesByStudent(annaWilliams));
		List<ExaminationGrade> expected = new LinkedList<ExaminationGrade>();
		expected.add(math1.getGrades().get(3));
		
		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getGrade(), 1.0);
		
		result = se.getFilteredGrades(se.filterGradesByStudent(johnDoe));
		expected = new LinkedList<ExaminationGrade>();
		expected.add(se.getGrades().get(0));

		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		
		result = math2.getFilteredGrades(math2.filterGradesByStudent(annaWilliams));
		expected = new LinkedList<ExaminationGrade>();

		assertEquals(result, expected);
		assertEquals(result.size(), 0);
		
		result = math2.getFilteredGrades(math2.filterGradesByStudent(johnDoe));
		expected = new LinkedList<ExaminationGrade>();
		expected.add(math2.getGrades().get(0));

		assertEquals(result, expected);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getGrade(), 2.0);
		
		result = math1.getFilteredGrades(math1.filterGradesByGrade(4.0));
		expected = new LinkedList<ExaminationGrade>();
		expected.add(math1.getGrades().get(1));

		assertEquals(result, expected);
		assertEquals(result.size(), 1);
	}
	
	@Test
	public void testAverageGrade() {
		double average = math1.getAverageGrade();
		double expected = 2.075;
		
		assertEquals(average, expected, 0.00001);
		
		average = math2.getAverageGrade();
		expected = 7.4/3;
		
		assertEquals(average, expected, 0.00001);
		
		average = se.getAverageGrade();
		expected = 1.9;
		
		assertEquals(average, expected, 0.00001);
		
		average = fop.getAverageGrade();
		expected = 0;
		
		assertEquals(average, expected);
	}
	
	@Test
	public void testDistribution() {
		Map<Double, Integer> dist = math1.getDistributionOfGrades();
		
		assertEquals(dist.get(1.0), new Integer(1));
		assertEquals(dist.get(2.0), new Integer(1));
		assertEquals(dist.get(1.3), new Integer(1));
		assertEquals(dist.get(4.0), new Integer(1));
		assertEquals(dist.size(), 4);
		
		dist = se.getDistributionOfGrades();
		
		assertEquals(dist.get(2.0), new Integer(2));
		assertEquals(dist.get(1.7), new Integer(1));
		assertEquals(dist.size(), 2);
		
		dist = cs.getDistributionOfGrades();
		assertEquals(dist.size(), 0);
	}
	
}
