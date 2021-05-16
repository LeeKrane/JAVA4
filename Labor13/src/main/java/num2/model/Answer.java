package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer implements Serializable {
	@EmbeddedId
	@Column(name = "a_id")
	private AnswerID id;
	
	@Column(name = "a_approval")
	@Enumerated(EnumType.STRING)
	private Approval approval;
	
	@Column(name = "a_timestamp")
	private LocalDateTime timestamp;
	
	public Answer (Question question, Employee employee, Approval approval) {
		if (question.getExpirationDate().isBefore(LocalDate.now()))
			throw new IllegalArgumentException("The Question has already expired.");
		id = new AnswerID(question, employee);
		this.approval = approval;
		timestamp = LocalDateTime.now();
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Answer answer = (Answer) o;
		return Objects.equals(id, answer.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Answer{" +
				"id=" + id +
				", approval=" + approval +
				", timestamp=" + timestamp +
				'}';
	}
}
