package labor07.persistence;

import labor07.domain.Kunde;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcKundeRepository implements KundeRepository {
	@Override
	public List<Kunde> findAll () throws SQLException {
		return null;
	}
	
	@Override
	public Optional<Kunde> findById (Integer id) throws SQLException {
		return Optional.empty();
	}
	
	@Override
	public Kunde save (Kunde kunde) throws SQLException {
		return null;
	}
	
	@Override
	public boolean deleteKunde (Integer id) throws SQLException {
		return false;
	}
}
