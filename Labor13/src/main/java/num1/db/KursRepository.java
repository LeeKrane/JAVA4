package num1.db;

import num1.model.Kurs;

import java.util.List;

public class KursRepository extends Repository implements AutoCloseable {
	private static final KursRepository INSTANCE = new KursRepository();
	
	public static KursRepository getINSTANCE () {
		return INSTANCE;
	}
	
	public List<Kurs> findAll () {
		return super.findAll(Kurs.class);
	}
	
	public Kurs findById (Integer id) {
		return super.findById(id, Kurs.class);
	}
	
	@Override
	public void close () {
		db.JPAUtil.close();
	}
}
