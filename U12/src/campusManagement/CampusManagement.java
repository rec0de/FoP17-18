package campusManagement;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class represents a campus management system
 * 
 * @author Lukas Roehrig
 */
public class CampusManagement {

	/**
	 * name of the system
	 */
	private String name;

	/**
	 * current semester
	 */
	private Semester currentSemester;

	/**
	 * students registered in the system
	 */
	private LinkedList<Student> students;

	/**
	 * examinations registered in the system
	 */
	private LinkedList<Examination> examinations;

	/**
	 * Constructs a new management system given the parameters
	 * 
	 * @param name
	 * @param currentSemester
	 */
	public CampusManagement(String name, Semester currentSemester) {
		this.name = name;
		this.currentSemester = currentSemester;
		students = new LinkedList<Student>();
		examinations = new LinkedList<Examination>();
	}

	/**
	 * @return the name of the system
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the current semester
	 */
	public Semester getCurrentSemester() {
		return currentSemester;
	}

	/**
	 * @return the list of all students registered
	 */
	public LinkedList<Student> getStudents() {
		return students;
	}

	/**
	 * @return the list of all examinations registered
	 */
	public LinkedList<Examination> getExaminations() {
		return examinations;
	}

	/**
	 * Adds a new student to the list of students with the parameters given
	 * 
	 * @param firstName
	 * @param lastName
	 * @param matriculationNumber
	 * @param courseOfStudies
	 * @return
	 */
	public Student addStudent(String firstName, String lastName, int matriculationNumber, String courseOfStudies) {
		Student newStudent = new Student(firstName, lastName, matriculationNumber, courseOfStudies, currentSemester);
		students.add(newStudent);
		return newStudent;
	}

	/**
	 * Adds a new examination to the list of examinations which takes places in
	 * the current semester
	 * 
	 * @param name
	 * @param creditPoints
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public Examination addExamination(String name, int creditPoints, LocalDateTime dateBegin, LocalDateTime dateEnd) {
		return addPastExamination(name, creditPoints, currentSemester, dateBegin, dateEnd);
	}

	/**
	 * Adds a past examination to the list of examinations which took place in
	 * another semester
	 * 
	 * @param name
	 * @param creditPoints
	 * @param semester
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public Examination addPastExamination(String name, int creditPoints, Semester semester, LocalDateTime dateBegin,
			LocalDateTime dateEnd) {
		Examination newExamination = new Examination(name, creditPoints, semester, dateBegin, dateEnd);
		examinations.add(newExamination);
		return newExamination;
	}

	/**
	 * Registers a given student to a given examination
	 * 
	 * @param student
	 * @param examination
	 */
	public void registerStudentForExamination(Student student, Examination examination) {
		student.getExaminationsRegistered().add(examination);
		examination.getStudentsRegistered().add(student);
	}

	/**
	 * Adds a new grade achieved by a student given the student and the
	 * examination
	 * 
	 * @param grade
	 * @param student
	 * @param examination
	 */
	public void addExaminationGradeForStudent(double grade, Student student, Examination examination) {
		ExaminationGrade examinationGrade = new ExaminationGrade(grade, student, examination);
		student.getGrades().add(examinationGrade);
		examination.getGrades().add(examinationGrade);
	}
	
	/**
	 * Creates a predicate matching every student with equal first- and last name
	 * @param firstName First name to match
	 * @param lastName Last name to match
	 * @return Predicate matching all students with the given name
	 */
	public Predicate<Student> filterStudentsByName(String firstName, String lastName){
		return student -> {return student.getFirstName().toLowerCase().equals(firstName.toLowerCase()) && student.getLastName().toLowerCase().equals(lastName.toLowerCase());};
	}
	
	/**
	 * Creates a predicate matching every student with the given matriculation number
	 * @param matriculationNumber The matriculation number to match
	 * @return Predicate matching all students with the given mat. number
	 */
	public Predicate<Student> filterStudentsByMatriculationNumber(int matriculationNumber){
		return student -> {return student.getMatriculationNumber() == matriculationNumber;};
	}
	
	/**
	 * Creates a predicate matching every student in the given course of studies
	 * @param courseOfStudies The CoS to match
	 * @return Predicate matching all students in the given CoS
	 */
	public Predicate<Student> filterStudentsByCourseOfStudies(String courseOfStudies){
		return student -> student.getCourseOfStudies().toLowerCase().equals(courseOfStudies.toLowerCase());
	}
	
	/**
	 * Creates a predicate matching every exam with the given name
	 * @param name Exam name to match
	 * @return Predicate matching all exams with the given name
	 */
	public Predicate<Examination> filterExaminationsByName(String name){
		return exam -> exam.getName().toLowerCase().equals(name.toLowerCase());
	}
	
	/**
	 * Creates a predicate matching every exam with the given amount of CP
	 * @param cp The amount of CP to match
	 * @return Predicate matching all exams with given CP count
	 */
	public Predicate<Examination> filterExaminationsByCreditPoints(int cp){
		return exam -> {return exam.getCreditPoints() == cp;};
	}
	
	/**
	 * Creates a predicate matching every exam in the given semester
	 * @param semester The semester to match
	 * @return Predicate matching all exams in the given semester
	 */
	public Predicate<Examination> filterExaminationsBySemester(Semester semester){
		return exam -> exam.getSemester().equals(semester);
	}
	
	/**
	 * Filters all registered students using the supplied predicate
	 * @param filter The predicate to use for matching
	 * @return A list containing all students the predicate matches
	 */
	public List<Student> getFilteredStudents(Predicate<Student> filter){
		return this.students.stream().filter(filter).collect(Collectors.toCollection(LinkedList::new));
	}
	
	/**
	 * Filters all exams using the supplied predicate
	 * @param filter The predicate to use for matching
	 * @return A list containing all exams the predicate matches
	 */
	public List<Examination> getFilteredExaminations(Predicate<Examination> filter) {
		return this.examinations.stream().filter(filter).collect(Collectors.toCollection(LinkedList::new));
	}
}
