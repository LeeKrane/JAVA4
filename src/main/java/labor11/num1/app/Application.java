package labor11.num1.app;

import labor11.db.Repository;
import labor11.num1.model.manytomany.BookCategory;
import labor11.num1.model.onetomany.Book;
import labor11.num1.model.onetomany.Publisher;
import labor11.num1.model.onetoone.Address;
import labor11.num1.model.onetoone.Customer;

import java.time.LocalDate;
import java.util.Arrays;

public class Application {
	public static void main (String[] args) {
		//oneToOne();
		//oneToMany();
		manyToMany();
	}
	
	private static void oneToOne () {
		try (Repository rep = Repository.getINSTANCE()) {
			persist_oneToOne(rep);
			
			System.out.println("\n\n\nOne to One\n");
			rep.findAll(Customer.class).forEach(System.out::println);
			rep.findAll(Address.class).forEach(System.out::println);
			System.out.println("\n\n");
		}
	}
	
	private static void oneToMany () {
		try (Repository rep = Repository.getINSTANCE()) {
			persist_oneToOne(rep);
			
			Publisher p1 = new Publisher("entwickler.press", "Fachverlag für Softwareentwicklung");
			Publisher p2 = new Publisher("Michael Müller Verlag", "Reiseführer und Romane");
			
			LocalDate d1 = LocalDate.of(2012, 3, 20);
			LocalDate d2 = LocalDate.of(2008, 1, 17);
			
			Book b1 = new Book("9783868020144", "Daniel Röder", d1, "JPA mit Hibernate", p1);
			Book b2 = new Book("9783777020144", "John Smith", d2, "Das Java Persistance API", p1);
			Book b3 = new Book("3831713502", "Michael Schuh", d2, "Pyrenäen", p2);
			Book b4 = new Book("9783899533388", "Michael Müller", d1, "Toskana", p2);
			
			rep.persist(p1);
			rep.persist(p2);
			rep.persist(b1);
			rep.persist(b2);
			rep.persist(b3);
			rep.persist(b4);
			
			System.out.println("\n\n\nOne to Many\n");
			rep.findAll(Book.class).forEach(System.out::println);
			rep.findAll(Publisher.class).forEach(System.out::println);
			System.out.println("\n\n");
		}
	}
	
	private static void manyToMany () {
		try (Repository rep = Repository.getINSTANCE()) {
			Publisher p1 = new Publisher("entwickler.press", "Fachverlag für Softwareentwicklung");
			Publisher p2 = new Publisher("Michael Müller Verlag", "Reiseführer und Romane");
			
			BookCategory bc1 = new BookCategory("EDV-Fachbuch");
			BookCategory bc2 = new BookCategory("Datenbanken");
			BookCategory bc3 = new BookCategory("Reiseführer");
			LocalDate d1 = LocalDate.of(2012, 3, 20);
			LocalDate d2 = LocalDate.of(2008, 1, 17);
			
			Book b1 = new Book("9783868020144", "Daniel Röder", d1, "JPA mit Hibernate", p1);
			b1.addBookCategory(bc1);
			b1.addBookCategory(bc2);
			Book b2 = new Book("9783777020144", "John Smith", d2, "Das Java Persistance API", p1);
			b2.addBookCategory(bc1);
			b2.addBookCategory(bc2);
			Book b3 = new Book("3831713502", "Michael Schuh", d2, "Pyrenäen", p2);
			b3.addBookCategory(bc3);
			Book b4 = new Book("9783899533388", "Michael Müller", d1, "Toskana", p2);
			b4.addBookCategory(bc3);
			
			p1.getAddresses().add(new Address(1010, "Herrengasse 17", "Wien"));
			p1.getAddresses().add(new Address(3100, "Linzerstraße 37", "St.Pölten"));
			p2.getAddresses().add(new Address(2473, "Obere Hauptstraße 32", "Prellenkirchen"));
			p1.getAddresses().add(new Address(3200, "Hauptstraße 20", "Ober Grafendorf"));
			
			Book[] books = {b1, b2, b3, b4};
			BookCategory[] categories = {bc1, bc2, bc3};
			
			rep.persist(p1);
			rep.persist(p2);
			Arrays.stream(categories).forEach(rep::persist);
			Arrays.stream(books).forEach(rep::persist);
			
			System.out.println("\n\n\nMany to Many\n");
			rep.findAll(Customer.class).forEach(System.out::println);
			rep.findAll(Address.class).forEach(System.out::println);
			rep.findAll(Book.class).forEach(System.out::println);
			rep.findAll(Publisher.class).forEach(System.out::println);
			rep.findAll(BookCategory.class).forEach(System.out::println);
			System.out.println("\n\n");
		}
	}
	
	private static void persist_oneToOne (Repository rep) {
		Customer c1 = new Customer("Bauer", "Franz", "franz.bauer@gmx.at");
		Customer c2 = new Customer("Hörndle", "Eva", "eva.hoerndle@gmx.at");
		
		Address a1 = new Address(3100, "St. Pölten", "Waldstraße 3", c1);
		Address a2 = new Address(3500, "Krems/Donau", "Ringstraße 33", c2);
		
		c1.setAddress(a1);
		c2.setAddress(a2);
		
		rep.persist(c1);
		rep.persist(c2);
		
		rep.persist(a1);
		rep.persist(a2);
	}
}