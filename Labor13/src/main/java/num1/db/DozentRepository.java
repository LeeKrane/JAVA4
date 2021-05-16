package num1.db;

import num1.model.Dozent;

import java.util.List;

public class DozentRepository extends Repository implements AutoCloseable {
	private static final DozentRepository INSTANCE = new DozentRepository();
	
	public static DozentRepository getINSTANCE () {
		return INSTANCE;
	}
	
	public List<Dozent> findAll () {
		return super.findAll(Dozent.class);
	}
	
	public Dozent findById (Integer id) {
		return super.findById(id, Dozent.class);
	}
	
	@Override
	public void close () {
		db.JPAUtil.close();
	}
}
