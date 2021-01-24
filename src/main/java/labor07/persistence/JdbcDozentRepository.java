package labor07.persistence;

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
		
		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(sql);
			while (resultSet.next()) {
				dozentList.add(new Dozent(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				));
			}
		}
		
		return dozentList;
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
		if (dozent.getId() != 0)
			throw new IllegalArgumentException("Dozent ID must be 0!");
		
		String sql = """
				insert into dozent
				values (?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			insertStatement.setInt(1, dozent.getId());
			insertStatement.setString(2, dozent.getZuname());
			insertStatement.setString(3, dozent.getVorname());
			insertStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return dozent;
	}
	
	@Override
	public boolean deleteDozent (Integer id) throws SQLException {
		boolean ret;
		
		String sql = """
				delete from dozent
				where id = ?
				""";
		
		try (PreparedStatement deleteStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			deleteStatement.setInt(1, id);
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
