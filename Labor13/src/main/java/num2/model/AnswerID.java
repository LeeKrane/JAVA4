package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AnswerID implements Serializable {
	@ManyToOne
	@JoinColumn(name = "q_id")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "e_id")
	private Employee employee;
	
	public AnswerID (Question question, Employee employee) {
		this.question = question;
		this.employee = employee;
	}
}
