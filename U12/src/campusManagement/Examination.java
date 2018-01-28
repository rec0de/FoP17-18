package campusManagement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	
	/**
	 * Creates a filter that matches ExaminationGrades with equal grade
	 * @param grade The grade to match
	 * @return Predicate matching all equal grades
	 */
	public Predicate<ExaminationGrade> filterGradesByGrade(double grade){
		return examgrade -> examgrade.getGrade() == grade;
	}

	/**
	 * Creates a filter that matches ExaminationGrades with equal student
	 * @param student The student to match
	 * @return Predicate matching all ExaminationGrades by the given student
	 */
	public Predicate<ExaminationGrade> filterGradesByStudent(Student student){
		return examgrade -> examgrade.getStudent().equals(student);
	}

	/**
	 * Filters the exam's grades using the given predicate
	 * @param filter The predicate used to match grades to keep
	 * @return A list of all grades matching the filter
	 */
	public List<ExaminationGrade> getFilteredGrades(Predicate<ExaminationGrade> filter){
		return this.grades.stream().filter(filter).collect(Collectors.toCollection(LinkedList::new));
	}
	
	/**
	 *  Calculates the average of all grades in the exam
	 * @return Average grade of the exam
	 */
	public double getAverageGrade() {
		return this.grades.stream().mapToDouble(g -> g.getGrade()).average().orElse(0.0);
	}
	
	/**
	 * Counts all grades and groups them together
	 * @return Map containing grade count for every unique grade
	 */
	public Map<Double, Integer> getDistributionOfGrades(){
		Map<Double, Integer> res = new HashMap<Double, Integer>();
		DoubleConsumer consumer = g -> {res.put(g, res.containsKey(g) ? res.get(g) + 1 : 1);};
		this.grades.stream().mapToDouble(g -> g.getGrade()).forEach(consumer);
		return res;
	}
}
