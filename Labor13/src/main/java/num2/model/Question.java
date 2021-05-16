package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "q_id")
	private Integer id;
	
	@Size(max = 20, message = "The title must not exceed 20 characters.")
	@Column(name = "q_title")
	private String title;
	
	@Size(max = 200, message = "The message must not exceed 200 characters.")
	@Column(name = "q_message")
	private String message;
	
	@OneToMany(mappedBy = "id.question")
	private List<Answer> answers = new ArrayList<>();
	
	@Column(name = "q_expiration_date")
	private LocalDate expirationDate;
	
	public Question (String title, String message, LocalDate expirationDate) {
		this.title = title;
		this.message = message;
		this.expirationDate = expirationDate;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Question question = (Question) o;
		return Objects.equals(id, question.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Question{" +
				"id=" + id +
				", title='" + title + '\'' +
				", message='" + message + '\'' +
				", expirationDate=" + expirationDate +
				'}';
	}
	
	public void addAnswer (Answer answer) {
		answers.add(answer);
	}
}
