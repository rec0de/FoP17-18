package campusManagement;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Class represents a examination in our management system
 * 
 * @author Lukas Roehrig
 */
public class Examination {

	/**
	 * name of the examinations
	 */
	private String name;

	/**
	 * credit points achieved if this examination is passed
	 */
	private int creditPoints;

	/**
	 * semester this examination takes place
	 */
	private Semester semester;

	/**
	 * date and start time of the examination
	 */
	LocalDateTime dateBegin;

	/**
	 * date and end time of the examination
	 */
	LocalDateTime dateEnd;

	/**
	 * list of students who registered themselves for this examination
	 */
	private LinkedList<Student> studentsRegistered;

	/**
	 * list of grades which where achieved in this examination
	 */
	private LinkedList<ExaminationGrade> grades;

	/**
	 * Constructs a new Examination with the parameters given
	 * 
	 * @param name
	 * @param creditPoints
	 * @param semester
	 * @param dateBegin
	 * @param dateEnd
	 */
	public Examination(String name, int creditPoints, Semester semester, LocalDateTime dateBegin,
			LocalDateTime dateEnd) {
		this.name = name;
		this.creditPoints = creditPoints;
		this.semester = semester;
		setDateBegin(dateBegin);
		setDateEnd(dateEnd);
		studentsRegistered = new LinkedList<Student>();
		grades = new LinkedList<ExaminationGrade>();
	}

	/**
	 * @return the name of the examination
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return credit points achieved if this examination is passed
	 */
	public int getCreditPoints() {
		return creditPoints;
	}

	/**
	 * @return date and start time of the examination
	 */
	public LocalDateTime getDateBegin() {
		return dateBegin;
	}

	/**
	 * @return date and end time of the examination
	 */
	public LocalDateTime getDateEnd() {
		return dateEnd;
	}

	/**
	 * @return list of students who registered themselves for this examination
	 */
	public LinkedList<Student> getStudentsRegistered() {
		return studentsRegistered;
	}

	/**
	 * @return list of grades which where achieved in this examination
	 */
	public LinkedList<ExaminationGrade> getGrades() {
		return grades;
	}

	/**
	 * @return semester this examination takes place
	 */
	public Semester getSemester() {
		return semester;
	}

	/**
	 * overrides the date and start time of this examination with the date and
	 * time given
	 * 
	 * @param dateBegin
	 */
	public void setDateBegin(LocalDateTime dateBegin) {
		this.dateBegin = dateBegin;
	}

	/**
	 * overrides the date and end time of this examination with the date and
	 * time given
	 * 
	 * @param dateEnd
	 */
	public void setDateEnd(LocalDateTime dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Examination: ");
		sb.append(getName());
		sb.append(" - CP: ");
		sb.append(getCreditPoints());
		sb.append(" - ");
		sb.append("Students registered: ");
		sb.append(getStudentsRegistered().size());
		sb.append(" - ");
		sb.append("Grades given: ");
		sb.append(grades.size());
		return sb.toString();
	}


}
