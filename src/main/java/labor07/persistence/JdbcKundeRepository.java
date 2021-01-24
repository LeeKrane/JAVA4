package labor07.persistence;

import labor07.domain.Kunde;

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
		List<Kunde> kundeList = new ArrayList<>();
		
		String sql = """
				select id, zuname, vorname
				from kunde
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				kundeList.add(new Kunde(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				));
			}
		}
		
		return kundeList;
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
				Optional.empty();
			return Optional.of(new Kunde(
					resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3)
			));
		}
	}
	
	@Override
	public Kunde save (Kunde kunde) throws SQLException {
		if (kunde.getId() != 0)
			throw new IllegalArgumentException("Kunde ID must be 0!");
		
		String sql = """
				insert into kunde
				values (?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			insertStatement.setInt(1, kunde.getId());
			insertStatement.setString(2, kunde.getZuname());
			insertStatement.setString(3, kunde.getVorname());
			insertStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return kunde;
	}
	
	@Override
	public boolean deleteKunde (Integer id) throws SQLException {
		boolean ret;
		
		String sql = """
				delete from kunde
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
