import num2.db.Repository;
import num2.model.Approval;
import num2.model.Employee;
import num2.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Num2Test {
	private static Repository rep;
	
	@AfterEach
	void closeRepository () {
		rep.close();
	}
	
	@BeforeEach
	void insertTestData () {
		rep = Repository.getINSTANCE();
		
		Question[] questions = new Question[]{
				new Question("Q1", "Is Java a programming language?",
							 LocalDate.of(2035, 10, 22)),
				new Question("Q2", "Is dead person physically able to program?",
							 LocalDate.of(2000, 6, 17))
		};
		
		Employee[] employees = new Employee[]{
				new Employee(123456, "Kranabetter", "Christian"),
				new Employee(987654, "Stiefsohn", "Fabian"),
				new Employee(246802, "Heindl", "Matteo")
		};
		
		Arrays.stream(questions).forEach(rep::persist);
		Arrays.stream(employees).forEach(rep::persist);
		
		rep.answerQuestion(
				rep.findById(1, Question.class),
				rep.findById(987654, Employee.class),
				Approval.COMPLETE_APPROVAL);
	}
	
	@Test
	void answerQuestion_successfulAnswer () {
		rep.answerQuestion(
				rep.findById(1, Question.class),
				rep.findById(123456, Employee.class),
				Approval.COMPLETE_APPROVAL);
		assertEquals(2, rep.findById(1, Question.class).getAnswers().size());
	}
	
	@Test
	void answerQuestion_repeatedAnswer_throwsIAE () {
		assertThrows(IllegalArgumentException.class,
					() -> rep.answerQuestion(
							rep.findById(1, Question.class),
							rep.findById(987654, Employee.class),
							Approval.COMPLETE_APPROVAL));
	}
	
	@Test
	void answerQuestion_expiredAnswer_throwsIAE () {
		assertThrows(IllegalArgumentException.class,
					 () -> rep.answerQuestion(
							 rep.findById(2, Question.class),
							 rep.findById(123456, Employee.class),
							 Approval.COMPLETE_APPROVAL));
	}
}
