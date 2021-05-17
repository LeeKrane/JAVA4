package num1.db;

import lombok.NoArgsConstructor;
import num1.model.Dozent;
import num1.model.Kunde;
import num1.model.Kurs;
import num1.model.Kurstyp;

import java.util.List;

@NoArgsConstructor
public class Kurssystemservice implements IKurssystemService {
	private static final Kurssystemservice INSTANCE = new Kurssystemservice();
	
	public static Kurssystemservice getINSTANCE () {
		return INSTANCE;
	}
	
	@Override
	public List<Kunde> getKunden () throws KursDBException {
		return KundeRepository.getINSTANCE().findAll();
	}
	
	@Override
	public void insertKunde (Kunde k) throws KursDBException {
		KundeRepository.getINSTANCE().persist(k);
	}
	
	@Override
	public void deleteKunde (Kunde k) throws KursDBException {
		// if (k.getKundeId() != null)
		//	throw new IllegalArgumentException("The Kunde ID must not be null!");
		KundeRepository.getINSTANCE().delete(k);
	}
	
	@Override
	public List<Dozent> getDozenten () throws KursDBException {
		return DozentRepository.getINSTANCE().findAll();
	}
	
	@Override
	public List<Kurstyp> getKurstypen () throws KursDBException {
		return KurstypRepository.getINSTANCE().findAll();
	}
	
	@Override
	public List<Kurs> getKurse () throws KursDBException {
		return KursRepository.getINSTANCE().findAll();
	}
	
	@Override
	public void insertKurstyp (Kurstyp kt) throws KursDBException {
		KurstypRepository.getINSTANCE().persist(kt);
	}
	
	@Override
	public void deleteKurstyp (Kurstyp kt) throws KursDBException {
		KurstypRepository.getINSTANCE().delete(kt);
	}
	
	@Override
	public void insertKurs (Kurs kurs) throws KursDBException {
		KursRepository.getINSTANCE().persist(kurs);
	}
	
	@Override
	public List<Kunde> getKundenFromKurs (Kurs kurs) throws KursDBException {
		// if (kurs.getKursId() != null)
		//	throw new IllegalArgumentException("The Kurs ID must not be null!");
		return KundeRepository.getINSTANCE().getKundenByKurs(kurs);
	}
	
	@Override
	public void bucheKurs (Kunde kunde, Kurs kurs) throws KursDBException {
		KundeRepository.getINSTANCE().bucheKurs(kunde, kurs);
	}
	
	@Override
	public void storniereKurs (Kunde kunde, Kurs kurs) throws KursDBException {
		KundeRepository.getINSTANCE().storniereKurs(kunde, kurs);
	}
	
	public void close () {
		db.JPAUtil.close();
	}
}
