package campusManagement;

import java.util.LinkedList;

/**
 * Class represents a student in our management system
 * 
 * @author Lukas Roehrig
 */
public class Student {

	/**
	 * first name of the student
	 */
	private String firstName;

	/**
	 * last name of the student
	 */
	private String lastName;

	/**
	 * matriculation number of the student
	 */
	private int matriculationNumber;

	/**
	 * semester in which the student got enrolled
	 */
	private Semester enrolmentSemester;

	/**
	 * course of studies of the student
	 */
	private String courseOfStudies;

	/**
	 * list of examinations in which the student is registered
	 */
	private LinkedList<Examination> examinationsRegistered;

	/**
	 * list of achieved grades of the student
	 */
	private LinkedList<ExaminationGrade> grades;

	/**
	 * Constructs a new student with the given parameters
	 * 
	 * @param firstName
	 * @param lastName
	 * @param matriculationNumber
	 * @param courseOfStudies
	 * @param enrolmentSemester
	 */
	public Student(String firstName, String lastName, int matriculationNumber, String courseOfStudies,
			Semester enrolmentSemester) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.matriculationNumber = matriculationNumber;
		this.courseOfStudies = courseOfStudies;
		this.enrolmentSemester = enrolmentSemester;
		examinationsRegistered = new LinkedList<Examination>();
		grades = new LinkedList<ExaminationGrade>();
	}

	/**
	 * @return the first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the matriculation number of the student
	 */
	public int getMatriculationNumber() {
		return matriculationNumber;
	}

	/**
	 * @return the course of studies of the student
	 */
	public String getCourseOfStudies() {
		return courseOfStudies;
	}

	/**
	 * @return list of examinations in which the student is registered
	 */
	public LinkedList<Examination> getExaminationsRegistered() {
		return examinationsRegistered;
	}

	/**
	 * @return list of achieved grades of the student
	 */
	public LinkedList<ExaminationGrade> getGrades() {
		return grades;
	}

	/**
	 * @return the semester in which the student got enrolled
	 */
	public Semester getEnrolmentSemester() {
		return enrolmentSemester;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Student: ");
		sb.append(getMatriculationNumber());
		sb.append(" - ");
		sb.append(getFirstName());
		sb.append(" ");
		sb.append(getLastName());
		return sb.toString();
	}

}
