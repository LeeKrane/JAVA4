package labor07.persistence;

import labor07.model.Dozent;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Create POSTGRESQL-Database from .sql file

public class JdbcDozentRepository implements DozentRepository {
	private final Connection connection;
	
	public JdbcDozentRepository (Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public List<Dozent> findAll () throws SQLException {
		List<Dozent> dozenten = new ArrayList<>();
		String sql = """
				select id, zuname, vorname
				from dozent
				""";
		
		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(sql);
			while (resultSet.next()) {
				dozenten.add(new Dozent(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				));
			}
		}
		
		return dozenten;
	}
	
	@Override
	public Optional<Dozent> findById (Integer id) throws SQLException {
		String sql = """
				select id, zuname, vorname
				from dozent
				where id = ?
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, id);
			ResultSet resultSet = selectStatement.executeQuery();
			if (!resultSet.next())
				return Optional.empty();
			return Optional.of(new Dozent(
					resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3)
			));
		}
	}
	
	@Override
	public Dozent save (Dozent dozent) throws SQLException {
		if (dozent.getId() != null)
			throw new IllegalArgumentException("Dozent ID must be NULL!");
		
		Dozent ret;
		String sql = """
				insert into dozent(zuname, vorname)
				values (?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			insertStatement.setString(1, dozent.getZuname());
			insertStatement.setString(2, dozent.getVorname());
			insertStatement.executeUpdate();
			
			ResultSet resultSet = insertStatement.getGeneratedKeys();
			if (resultSet.next()) {
				ret = findById(resultSet.getInt(1)).orElse(null);
			} else
				throw new IllegalArgumentException("There was no key generated for the Dozent!");
			connection.commit();
		} finally {
			connection.setAutoCommit(true);
		}
		
		return ret;
	}
	
	@Override
	public boolean deleteDozent (Integer id) throws SQLException {
		boolean success;
		String sql = """
				delete from dozent
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
