package labor07.persistence;

import labor06.model.Schueler;
import labor07.domain.Dozent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDozentRepository implements DozentRepository {
	private final Connection connection;
	
	public JdbcDozentRepository (Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public List<Dozent> findAll () throws SQLException {
		List<Dozent> dozentList = new ArrayList<>();
		
		String sql = """
				select id, zuname, vorname
				from dozent
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				dozentList.add(new Dozent(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				));
			}
		} catch (SQLException e) {
			return dozentList;
		}
		
		return dozentList;
	}
	
	@Override
	public Optional<Dozent> findById (Integer id) throws SQLException {
		Optional<Dozent> dozent = Optional.empty();
		
		String sql = String.format("""
				select id, zuname, vorname
				from dozent
				where id = %d
				""", id);
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				dozent = Optional.of(new Dozent(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				));
			}
		} catch (SQLException e) {
			return dozent;
		}
		
		return dozent;
	}
	
	@Override
	public Dozent save (Dozent dozent) throws SQLException {
		return null;
	}
	
	@Override
	public boolean deleteDozent (Integer id) throws SQLException {
		boolean ret;
		
		try (Statement deleteStatement = connection.createStatement()) {
			connection.setAutoCommit(false);
			String sql = String.format("""
					delete from dozent
					where id = %d
					""", id);
			ret = deleteStatement.executeUpdate(sql) == 1;
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return ret;
	}
}
