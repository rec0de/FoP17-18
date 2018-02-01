package campusManagement;

/**
 * Class represents a grade achieved in an examination
 * 
 * @author Lukas Roehrig
 */
public class ExaminationGrade {

	/**
	 * Student who achieved this grade
	 */
	private Student student;

	/**
	 * Examination in which the student achieved this grade
	 */
	private Examination examination;

	/**
	 * The actual grade
	 */
	private double grade;

	/**
	 * Construct a new ExaminationGrade given all parameters
	 * 
	 * @param grade
	 * @param student
	 * @param examination
	 */
	public ExaminationGrade(double grade, Student student, Examination examination) {
		setGrade(grade);
		this.student = student;
		this.examination = examination;
	}

	/**
	 * @return Student who achieved this grade
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @return Examination in which the student achieved this grade
	 */
	public Examination getExamination() {
		return examination;
	}

	/**
	 * @return The actual grade
	 */
	public double getGrade() {
		return grade;
	}

	/**
	 * Overrides the grade with the given value
	 * 
	 * @param grade
	 */
	public void setGrade(double grade) {
		this.grade = grade;
	}
}
