package labor06.persistence;

import labor06.model.Schueler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSchuelerRepository implements SchuelerRepository {
	private final Connection connection;
	
	public JdbcSchuelerRepository (Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public int persistSchueler (List<Schueler> lst) throws SQLException {
		int edited = 0;
		
		try (Statement createTableStatement = connection.createStatement()) {
			connection.setAutoCommit(false);
			String sql = """
					drop table if exists schueler;
					create table schueler(
						familienname varchar(128),
						vorname varchar(128),
						geschlecht varchar(1),
						katNr int,
						klasse varchar(32),
						primary key(katNr, klasse)
					)
					""";
			createTableStatement.executeUpdate(sql);
			
			sql = """
					insert into schueler
					values(?, ?, ?, ?, ?)
					""";
			PreparedStatement insertStatement = connection.prepareStatement(sql);
			
			for (Schueler s : lst) {
				insertStatement.setString(1, s.getFamilienname());
				insertStatement.setString(2, s.getVorname());
				insertStatement.setString(3, String.valueOf(s.getGeschlecht()));
				insertStatement.setInt(4, s.getKatNr());
				insertStatement.setString(5, s.getKlasse());
				edited += insertStatement.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return edited;
	}
	
	@Override
	public int deleteAll () throws SQLException {
		int deleted = 0;
		
		try (Statement deleteStatement = connection.createStatement()) {
			connection.setAutoCommit(false);
			deleted = deleteStatement.executeUpdate("delete from schueler");
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return deleted;
	}
	
	@Override
	public List<Schueler> findSchuelerByKlasse (String klasse) throws SQLException {
		List<Schueler> schuelerList = new ArrayList<>();
		
		String sql = String.format("""
				select familienname, vorname, geschlecht, katNr, klasse
				from schueler
				where klasse like '%s'
				order by katNr
				""", klasse);
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				schuelerList.add(new Schueler(
						resultSet.getString(1),
						resultSet.getString(2),
						resultSet.getString(3).charAt(0),
						resultSet.getInt(4),
						resultSet.getString(5)
				));
			}
		} catch (SQLException e) {
			return schuelerList;
		}
		
		return schuelerList;
	}
	
	@Override
	public List<Schueler> findSchuelerByGeschlecht (char geschlecht) throws SQLException {
		List<Schueler> schuelerList = new ArrayList<>();
		
		if (geschlecht != 'm' && geschlecht != 'w')
			throw new IllegalArgumentException(geschlecht + " is neither w nor m!");
		
		String sql = """
				select familienname, vorname, geschlecht, katNr, klasse
				from schueler
				where geschlecht like '%c'
				order by klasse, katNr
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(String.format(sql, geschlecht))) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				schuelerList.add(new Schueler(
						resultSet.getString(1),
						resultSet.getString(2),
						resultSet.getString(3).charAt(0),
						resultSet.getInt(4),
						resultSet.getString(5)
				));
			}
		} catch (SQLException e) {
			return schuelerList;
		}
		
		return schuelerList;
	}
	
	@Override
	public Map<String, Integer> getKlassen () throws SQLException {
		Map<String, Integer> klassen = new HashMap<>();
		
		String sql = """
				select distinct klasse, count(klasse)
				from schueler
				group by klasse
				order by klasse
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next())
				klassen.put(resultSet.getString(1), resultSet.getInt(2));
		} catch (SQLException e) {
			return klassen;
		}
		
		return klassen;
	}
}