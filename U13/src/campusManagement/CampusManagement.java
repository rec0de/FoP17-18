package campusManagement;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import exceptions.ExaminationAlreadyExistsException;
import exceptions.GradeAlreadyExistsException;
import exceptions.InvalidValueException;
import exceptions.StudentRegistrationException;

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
	 * @throws InvalidValueException If the matriculation number is already in use
	 */
	public Student addStudent(String firstName, String lastName, int matriculationNumber, String courseOfStudies) throws InvalidValueException {
		
		if(this.getFilteredStudents(this.filterStudentsByMatriculationNumber(matriculationNumber)).size() > 0)
			throw new InvalidValueException("Matriculation number is used by another student");
		
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
	 * @throws ExaminationAlreadyExistsException If an exam of the same name exists in the current semester
	 * @throws InvalidValueException If the start/end times are invalid
	 */
	public Examination addExamination(String name, int creditPoints, LocalDateTime dateBegin, LocalDateTime dateEnd) throws InvalidValueException, ExaminationAlreadyExistsException {
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
	 * @throws InvalidValueException If start/end times are invalid or exam duration is longer than one day
	 * @throws ExaminationAlreadyExistsException If an exam with the same name exists in the same semester
	 */
	public Examination addPastExamination(String name, int creditPoints, Semester semester, LocalDateTime dateBegin,
			LocalDateTime dateEnd) throws InvalidValueException, ExaminationAlreadyExistsException{
		if(creditPoints < 1)
			throw new InvalidValueException("CP can't be < 1");
		
		if(dateBegin.getDayOfYear() != dateEnd.getDayOfYear() || dateBegin.getYear() != dateEnd.getYear())
			throw new InvalidValueException("Exam has to start and end at the same day");
		
		if(dateBegin.isAfter(dateEnd) || dateBegin.isEqual(dateEnd))
			throw new InvalidValueException("Exam can't end before it starts");
		
		Predicate<Examination> filter_name = this.filterExaminationsByName(name);
		Predicate<Examination> filter_sem = this.filterExaminationsBySemester(semester);
		Predicate<Examination> filter = exam -> filter_name.test(exam) && filter_sem.test(exam);
		
		if(this.getFilteredExaminations(filter).size() > 0)
			throw new ExaminationAlreadyExistsException("Exam exists");
		
		Examination newExamination = new Examination(name, creditPoints, semester, dateBegin, dateEnd);
		examinations.add(newExamination);
		return newExamination;
	}

	/**
	 * Registers a given student to a given examination
	 * 
	 * @param student
	 * @param examination
	 * @throws StudentRegistrationException If the student is already registered
	 */
	public void registerStudentForExamination(Student student, Examination examination) throws StudentRegistrationException {
		
		if(examination.getStudentsRegistered().contains(student))
			throw new StudentRegistrationException("Student is already registered for this exam");
		
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
	 * @throws StudentRegistrationException If the student is not registered for the exam
	 * @throws GradeAlreadyExistsException If the given student already received a grade
	 * @throws InvalidValueException On invalid grade
	 */
	public void addExaminationGradeForStudent(double grade, Student student, Examination examination) throws StudentRegistrationException, GradeAlreadyExistsException, InvalidValueException {
		
		if(!examination.getStudentsRegistered().contains(student))
			throw new StudentRegistrationException("Student is not registered for given exam");
		
		if(examination.getFilteredGrades(examination.filterGradesByStudent(student)).size() > 0)
			throw new GradeAlreadyExistsException("Student has already been graded");
		
		if(!(grade == 1 || grade == 1.3 || grade == 1.7 || grade == 2 || grade == 2.3 || grade == 2.7 || grade == 3 || grade == 3.3 || grade == 3.7 || grade == 4 || grade == 5))
			throw new InvalidValueException("Invalid grade value");
		
		ExaminationGrade examinationGrade = new ExaminationGrade(grade, student, examination);
		student.getGrades().add(examinationGrade);
		examination.getGrades().add(examinationGrade);
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
	 * Creates a predicate matching every exam in the given semester
	 * @param semester The semester to match
	 * @return Predicate matching all exams in the given semester
	 */
	public Predicate<Examination> filterExaminationsBySemester(Semester semester){
		return exam -> exam.getSemester().equals(semester);
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
	 * Filters all exams using the supplied predicate
	 * @param filter The predicate to use for matching
	 * @return A list containing all exams the predicate matches
	 */
	public List<Examination> getFilteredExaminations(Predicate<Examination> filter) {
		return this.examinations.stream().filter(filter).collect(Collectors.toCollection(LinkedList::new));
	}
	
	/**
	 * Filters all registered students using the supplied predicate
	 * @param filter The predicate to use for matching
	 * @return A list containing all students the predicate matches
	 */
	public List<Student> getFilteredStudents(Predicate<Student> filter){
		return this.students.stream().filter(filter).collect(Collectors.toCollection(LinkedList::new));
	}

}
