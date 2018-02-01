package campusManagement;

import java.time.format.DateTimeFormatter;
import org.junit.Before;

public class TestStudentException {
	static CampusManagement management;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	@Before
	public void initTest() {
		management = new CampusManagement("Test", Semester.WiSe_17_18);
	}


}
