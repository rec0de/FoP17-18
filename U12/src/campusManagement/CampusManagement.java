package campusManagement;

import java.time.LocalDateTime;
import java.util.LinkedList;

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
}
