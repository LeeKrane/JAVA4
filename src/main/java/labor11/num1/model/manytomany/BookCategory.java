package labor11.num1.model.manytomany;

import labor11.num1.model.onetomany.Book;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_category")
public class BookCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cat_id")
	private Integer id;
	
	@Column(name = "cat_description")
	private String description;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Book> books = new HashSet<>();
	
	public BookCategory (String description) {
		this.description = description;
	}
	
	public BookCategory (Integer id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public void addBook (Book book) {
		book.getCategories().add(this);
		books.add(book);
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookCategory that = (BookCategory) o;
		return Objects.equals(id, that.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "BookCategory{" +
				"id=" + id +
				", description='" + description + '\'' +
				'}';
	}
}
