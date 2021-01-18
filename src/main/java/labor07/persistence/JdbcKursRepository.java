package labor07.persistence;

import labor07.domain.Dozent;
import labor07.domain.Kunde;
import labor07.domain.Kurs;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcKursRepository implements KursRepository {
	@Override
	public List<Kurs> findAll () throws SQLException {
		return null;
	}
	
	@Override
	public List<Kurs> findAllByDozent (Dozent dozent) throws SQLException {
		return null;
	}
	
	@Override
	public Optional<Kurs> findById (Integer id) throws SQLException {
		return Optional.empty();
	}
	
	@Override
	public Kurs save (Kurs kurs) throws SQLException {
		return null;
	}
	
	@Override
	public List<Kurs> getKurseByKunde (Integer id) throws SQLException {
		return null;
	}
	
	@Override
	public boolean bucheKurs (Kunde kunde, Kurs kurs) throws SQLException {
		return false;
	}
	
	@Override
	public boolean storniereKurs (Kunde kunde, Kurs kurs) throws SQLException {
		return false;
	}
}
