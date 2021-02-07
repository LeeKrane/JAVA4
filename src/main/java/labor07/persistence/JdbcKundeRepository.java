package labor07.persistence;

import labor07.model.Kunde;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcKundeRepository implements KundeRepository {
	private final Connection connection;
	
	public JdbcKundeRepository (Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public List<Kunde> findAll () throws SQLException {
		List<Kunde> kunden = new ArrayList<>();
		String sql = """
				select id, zuname, vorname
				from kunde
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				kunden.add(new Kunde(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				));
			}
		}
		
		return kunden;
	}
	
	@Override
	public Optional<Kunde> findById (Integer id) throws SQLException {
		String sql = """
				select id, zuname, vorname
				from kunde
				where id = ?
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, id);
			ResultSet resultSet = selectStatement.executeQuery();
			if (!resultSet.next())
				return Optional.empty();
			return Optional.of(new Kunde(
					resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3)
			));
		}
	}
	
	@Override
	public Kunde save (Kunde kunde) throws SQLException {
		if (kunde.getId() != null)
			throw new IllegalArgumentException("Kunde ID must be NULL!");
		
		Kunde ret;
		String sql = """
				insert into kunde(zuname, vorname)
				values (?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			insertStatement.setString(1, kunde.getZuname());
			insertStatement.setString(2, kunde.getVorname());
			insertStatement.executeUpdate();
			
			ResultSet resultSet = insertStatement.getGeneratedKeys();
			if (resultSet.next()) {
				ret = findById(resultSet.getInt(1)).orElse(null);
			} else
				throw new IllegalArgumentException("There was no key generated for the Kunde!");
			connection.commit();
		} finally {
			connection.setAutoCommit(true);
		}
		
		return ret;
	}
	
	@Override
	public boolean deleteKunde (Integer id) throws SQLException {
		boolean success;
		String sql = """
				delete from kunde
				where id = ?
				""";
		
		try (PreparedStatement deleteStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			deleteStatement.setInt(1, id);
			success = deleteStatement.executeUpdate() == 1;
		} catch (JdbcSQLIntegrityConstraintViolationException e) {
			return false;
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return success;
	}
}
