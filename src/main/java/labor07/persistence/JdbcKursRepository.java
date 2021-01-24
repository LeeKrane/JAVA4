package labor07.persistence;

import labor07.domain.Dozent;
import labor07.domain.Kunde;
import labor07.domain.Kurs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcKursRepository implements KursRepository {
	private final Connection connection;
	
	public JdbcKursRepository (Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public List<Kurs> findAll () throws SQLException {
		List<Kurs> kursList = new ArrayList<>();
		
		String sql = """
				select id, typ, doz_id, bezeichnung, beginn
				from kurs
				""";
		
		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(sql);
			while (resultSet.next()) {
				kursList.add(new Kurs(
						resultSet.getInt(1),
						resultSet.getString(2).charAt(0),
						resultSet.getInt(3),
						resultSet.getString(4),
						resultSet.getDate(5)
				));
			}
		}
		
		return kursList;
	}
	
	@Override
	public List<Kurs> findAllByDozent (Dozent dozent) throws SQLException {
		List<Kurs> kursList = new ArrayList<>();
		
		String sql = """
				select id, typ, doz_id, bezeichnung, beginn
				from kurs
				where dozId = ?
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, dozent.getId());
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				kursList.add(new Kurs(
						resultSet.getInt(1),
						resultSet.getString(2).charAt(0),
						resultSet.getInt(3),
						resultSet.getString(4),
						resultSet.getDate(5)
				));
			}
		}
		
		return kursList;
	}
	
	@Override
	public Optional<Kurs> findById (Integer id) throws SQLException {
		String sql = """
				select id, typ, doz_id, bezeichnung, beginn
				from kurs
				where id = ?
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, id);
			ResultSet resultSet = selectStatement.executeQuery();
			if (!resultSet.next())
				return Optional.empty();
			return Optional.of(new Kurs(
					resultSet.getInt(1),
					resultSet.getString(2).charAt(0),
					resultSet.getInt(3),
					resultSet.getString(4),
					resultSet.getDate(5)
			));
		}
	}
	
	@Override
	public Kurs save (Kurs kurs) throws SQLException {
		if (kurs.getId() != 0)
			throw new IllegalArgumentException("Kurs ID must be 0!");
		
		String sql = """
				insert into kurs
				values (?, ?, ?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			insertStatement.setInt(1, kurs.getId());
			insertStatement.setString(2, Character.toString(kurs.getTyp()));
			insertStatement.setInt(3, kurs.getDozId());
			insertStatement.setString(4, kurs.getBezeichnung());
			insertStatement.setDate(5, kurs.getBeginn());
			insertStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return kurs;
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
