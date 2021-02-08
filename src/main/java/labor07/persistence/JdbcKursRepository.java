package labor07.persistence;

import labor07.model.Dozent;
import labor07.model.Kunde;
import labor07.model.Kurs;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

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
		List<Kurs> kurse = new ArrayList<>();
		String sql = """
				select id, typ, doz_id, bezeichnung, beginndatum
				from kurs
				""";
		
		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(sql);
			while (resultSet.next()) {
				kurse.add(new Kurs(
						resultSet.getInt(1),
						resultSet.getString(2).charAt(0),
						resultSet.getInt(3),
						resultSet.getString(4),
						resultSet.getDate(5)
				));
			}
		}
		
		return kurse;
	}
	
	@Override
	public List<Kurs> findAllByDozent (Dozent dozent) throws SQLException {
		List<Kurs> kurse = new ArrayList<>();
		String sql = """
				select id, typ, doz_id, bezeichnung, beginndatum
				from kurs
				where doz_id = ?
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, dozent.getId());
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				kurse.add(new Kurs(
						resultSet.getInt(1),
						resultSet.getString(2).charAt(0),
						resultSet.getInt(3),
						resultSet.getString(4),
						resultSet.getDate(5)
				));
			}
		}
		
		return kurse;
	}
	
	@Override
	public Optional<Kurs> findById (Integer id) throws SQLException {
		String sql = """
				select id, typ, doz_id, bezeichnung, beginndatum
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
		if (kurs.getId() != null)
			throw new IllegalArgumentException("Kurs ID must be NULL!");
		
		Kurs ret;
		String sql = """
				insert into kurs(typ, doz_id, bezeichnung, beginndatum)
				values (?, ?, ?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			insertStatement.setString(1, Character.toString(kurs.getTyp()));
			insertStatement.setInt(2, kurs.getDozId());
			insertStatement.setString(3, kurs.getBezeichnung());
			insertStatement.setDate(4, kurs.getBeginn());
			insertStatement.executeUpdate();
			
			ResultSet resultSet = insertStatement.getGeneratedKeys();
			if (resultSet.next()) {
				ret = findById(resultSet.getInt(1)).orElse(null);
			} else
				throw new IllegalArgumentException("There was no key generated for the Kurs!");
			connection.commit();
		} finally {
			connection.setAutoCommit(true);
		}
		
		return ret;
	}
	
	@Override
	public List<Kurs> getKurseByKunde (Integer id) throws SQLException {
		List<Kurs> kurse = new ArrayList<>();
		String sql = """
				select kurs_id
				from kurs_kunde
				where kunde_id = ?
				""";
		
		try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, id);
			ResultSet resultSet = selectStatement.executeQuery();
			
			Kurs temp;
			while (resultSet.next()) {
				temp = findById(resultSet.getInt(1)).orElse(null);
				if (temp != null)
					kurse.add(temp);
			}
		}
		return kurse;
	}
	
	@Override
	public boolean bucheKurs (Kunde kunde, Kurs kurs) throws SQLException {
		boolean success;
		String sql = """
				insert into kurs_kunde
				values(?, ?)
				""";
		
		try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			insertStatement.setInt(1, kunde.getId());
			insertStatement.setInt(2, kurs.getId());
			success = insertStatement.executeUpdate() == 1;
			connection.commit();
		} catch (JdbcSQLIntegrityConstraintViolationException e) {
			return false;
		} finally {
			connection.setAutoCommit(true);
		}
		
		return success;
	}
	
	@Override
	public boolean storniereKurs (Kunde kunde, Kurs kurs) throws SQLException {
		boolean success;
		String sql = """
				delete from kurs_kunde
				where kunde_id = ? and kurs_id = ?
				""";
		
		try (PreparedStatement deleteStatement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			deleteStatement.setInt(1, kunde.getId());
			deleteStatement.setInt(2, kurs.getId());
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
