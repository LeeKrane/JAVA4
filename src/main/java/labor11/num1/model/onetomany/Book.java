package labor11.num1.model.onetomany;

import labor11.num1.model.manytomany.BookCategory;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book implements Serializable {
	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "book_isbn")
	private String isbn;
	
	@Column(name = "book_author")
	private String author;
	
	@Column(name = "book_publication_date")
	private LocalDate date;
	
	@Column(name = "book_title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "book_pub_id", nullable = false)
	private Publisher publisher;
	
	@ManyToMany
	@JoinTable(name = "book_cat",
			joinColumns = {@JoinColumn(name = "book_id")},
			inverseJoinColumns = {@JoinColumn(name = "cat_id")})
	private Set<BookCategory> categories = new HashSet<>();
	
	public Book (String isbn, String author, LocalDate date, String title, Publisher publisher) {
		this.isbn = isbn;
		this.author = author;
		this.date = date;
		this.title = title;
		this.publisher = publisher;
	}
	
	public Book (Integer id, String isbn, String author, LocalDate date, String title, Publisher publisher) {
		this.id = id;
		this.isbn = isbn;
		this.author = author;
		this.date = date;
		this.title = title;
		this.publisher = publisher;
	}
	
	public void addBookCategory (BookCategory bookCategory) {
		bookCategory.getBooks().add(this);
		categories.add(bookCategory);
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		return Objects.equals(id, book.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Book{" +
				"id=" + id +
				", isbn='" + isbn + '\'' +
				", author='" + author + '\'' +
				", date=" + date +
				", title='" + title + '\'' +
				", publisher=" + publisher +
				'}';
	}
}
