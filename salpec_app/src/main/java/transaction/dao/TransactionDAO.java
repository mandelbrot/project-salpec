package transaction.dao;

import java.util.List;
import transaction.model.Transaction;
import org.hibernate.SessionFactory;

public class TransactionDAO implements ITransactionDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addTransaction(Transaction transaction) {
		getSessionFactory().getCurrentSession().save(transaction);
	}

	public void deleteTransaction(Transaction transaction) {
		getSessionFactory().getCurrentSession().delete(transaction);
	}

	public void updateTransaction(Transaction transaction) {
		getSessionFactory().getCurrentSession().update(transaction);
	}

	public void saveOrUpdateTransaction(Transaction transaction) {
		getSessionFactory().getCurrentSession().saveOrUpdate(transaction);
	}
	
	public Transaction getTransactionById(int id) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select t.id, t.id_account, t.id_currency, t.id_consumption, t.value, t.date, t.ie from Transaction t " +
					" where t.id=?")
	        .setParameter(0, id).list();
		
/*		String hql = "select e.firstname,e.lastname,e.cellphone,a.city,a.state,a.country from Employee e inner join e.address as a";
		String sql_query = "select d.name,p.name,sum(p.price) as totalprice from Product p join p.dealer d group by p.name";*/
		
		return (Transaction)list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactions(int id_user) {
		List<Transaction> list = (List<Transaction>) getSessionFactory().getCurrentSession()
				.createQuery("select t.id, a.name, c.name, t.value, cu.code, t.date, t.ie, c.id, t.description from Transaction t" +
						", Account a, Consumption c, Currency cu where t.idAccount = a.id and t.idConsumption = c.id and " +
						" t.idCurrency = cu. id and a.idUser=? order by t.dateTransaction desc")
		        .setParameter(0, id_user).list();
		
		return list;
	}
}

