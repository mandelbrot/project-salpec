package currency.dao;

import java.util.List;

import currency.model.Currency;
import currency.model.CurrencySelectView;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CurrencyDAO implements ICurrencyDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addCurrency(Currency currency) {
		getSessionFactory().getCurrentSession().save(currency);
	}

	public void deleteCurrency(Currency currency) {
		getSessionFactory().getCurrentSession().delete(currency);
	}

	public void updateCurrency(Currency currency) {
		getSessionFactory().getCurrentSession().update(currency);
	}

	public Currency getCurrencyById(int id) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.name, sum(t.value) as balance from Currency a, Transaction t" +
			" where a.id = t.idCurrency and id=?")
	        .setParameter(0, id).list();

		String sql_query = "select d.name,p.name,sum(p.price) as totalprice from Product p join p.dealer d group by p.name";
		
		return (Currency)list.get(0);
	}

	public List<Currency> getCurrencys(int id_user) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.name, sum(t.value) as balance from Currency a, Transaction t" +
			" where a.id = t.idCurrency and a.idUser=? group by t.idCurrency")
	        .setParameter(0, id_user).list();
		return list;
	}	

	public List<CurrencySelectView> getCurrencySelectView() {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("select c.id, c.name as name " +
					"from Currency c").list();
		return (List<CurrencySelectView>) list;
	}   
}

