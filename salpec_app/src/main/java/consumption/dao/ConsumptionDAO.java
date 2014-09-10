package consumption.dao;

import java.util.Iterator;
import java.util.List;

import consumption.model.Consumption;
import consumption.model.ConsumptionGroup;
import consumption.model.ConsumptionGroupSelectView;
import consumption.model.ConsumptionSelectView;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import transaction.model.Transaction;
import transaction.model.TransactionView;

public class ConsumptionDAO implements IConsumptionDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addConsumption(Consumption consumption) {
		getSessionFactory().getCurrentSession().save(consumption);
	}

	public void deleteConsumption(Consumption consumption) {
		getSessionFactory().getCurrentSession().delete(consumption);
	}

	public void updateConsumption(Consumption consumption) {
		getSessionFactory().getCurrentSession().update(consumption);
	}

	public void saveOrUpdateConsumptionGroup(ConsumptionGroup cg) {
		getSessionFactory().getCurrentSession().saveOrUpdate(cg);
	}

	public void saveOrUpdateConsumption(Consumption c) {
		getSessionFactory().getCurrentSession().saveOrUpdate(c);
	}

	public void deleteConsumptionGroup(ConsumptionGroup consumption) {
		getSessionFactory().getCurrentSession().delete(consumption);
	}

	public Consumption getConsumptionById(int id) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.name, sum(t.value) as balance from Consumption a, Transaction t" +
			" where a.id = t.idConsumption and id=?")
	        .setParameter(0, id).list();

		String sql_query = "select d.name,p.name,sum(p.price) as totalprice from Product p join p.dealer d group by p.name";
		
		return (Consumption)list.get(0);
	}

	public List<Consumption> getConsumptions(int id_user) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.idUser, a.idConsumptionGroup, a.name, a.ieDefault, a.description from Consumption a" +
			" where a.idUser=?")
	        .setParameter(0, id_user).list();
		return list;
	}

	public List<String[]> getConsumptionsCount(int id_user, String ie) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("SELECT c.name, c.id, count(*) FROM Transaction t, Consumption c" +
				" where t.idConsumption in (select id from Consumption where idUser=?)" +
				" and t.idConsumption=c.id and c.ieDefault=?" +
				" group by c.name,c.id" + 
				" order by count(*) desc limit 10")
	        .setParameter(0, id_user).setParameter(1, ie).list();
		return list;
	}

	public List<String[]> getConsumptionsValue(int id_user, String ie) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("SELECT c.name, c.id, " +
				" sum(case when t.ie='1' then t.value else (t.value*(-1)) end) " +
				" FROM Transaction t, Consumption c" +
				" where t.idConsumption in (select id from Consumption where idUser=?)" +
				" and t.idConsumption=c.id and c.ieDefault=?" +
				" group by c.name,c.id" +
				" order by sum(case when t.ie='1' then t.value else (t.value*(-1)) end)")
	        .setParameter(0, id_user).setParameter(1, ie).list();
		return list;
	}
		
	public List<TransactionView> getTransactionViews(int id_consumption, int id_user, boolean consumptionGroup) {
		String query;
		
		if(consumptionGroup)
			query = "select t.id, t.idAccount, t.idCurrency, t.idConsumption, t.value, t.ie, t.e, " +
					"t.active, t.description, a.name as accountName, c.name as consumptionName, " +
					"cu.code, t.date, t.dateTransaction from Transaction t, " +
					"Account a, Consumption c, Currency cu, ConsumptionGroup cg " +
					" where t.idAccount = a.id and t.idConsumption = c.id and " +
					" t.idCurrency = cu.id and cg.id=:id and cg.id=c.idConsumptionGroup and " +
					" cg.idUser=:idUser and t.active ='Y' order by t.dateTransaction desc";
		else
			query = "select t.id, t.idAccount, t.idCurrency, t.idConsumption, t.value, t.ie, t.e, " +
					"t.active, t.description, a.name as accountName, c.name as consumptionName, " +
					"cu.code, t.date, t.dateTransaction from Transaction t, " +
					"Account a, Consumption c, Currency cu, ConsumptionGroup cg " +
					" where t.idAccount = a.id and t.idConsumption = c.id and " +
					" t.idCurrency = cu.id and c.id=:id and cg.id=c.idConsumptionGroup and t.active ='Y' and " +
					" cg.idUser=:idUser order by t.dateTransaction desc";
		Query q = getSessionFactory().getCurrentSession().createQuery(query);
		q.setParameter("id", id_consumption);
		q.setParameter("idUser", id_user);
		List list = q.list();
		return list;
	}	

	public String getConsumptionName(int id_consumption, int id_user, boolean consumptionGroup) {
		String query, value = "";
		
		if(consumptionGroup)
			query = "select cg.name from ConsumptionGroup cg " +
					" where cg.id=:id and cg.idUser=:idUser";
		else
			query = "select c.name from Consumption c, ConsumptionGroup cg " +
					" where c.id=:id and cg.id=c.idConsumptionGroup and cg.idUser=:idUser";
		Query q = getSessionFactory().getCurrentSession().createQuery(query);
		q.setParameter("id", id_consumption);
		q.setParameter("idUser", id_user);
		List list = q.list();
        Iterator it=list.iterator();

        while(it.hasNext())
        {
        	value=(String)it.next();
        }
		return value;
	}	
	
	public List<Consumption> getConsumptionsByGroup(int id_group) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.idUser, a.idConsumptionGroup, a.name, a.ieDefault from Consumption a" +
			" where a.idConsumptionGroup=?")
	        .setParameter(0, id_group).list();
		return list;
	}	
	
	public List<ConsumptionGroup> getConsumptionGroups(int id_user) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.idUser, a.name, a.ieDefault, a.description from ConsumptionGroup a" +
			" where a.idUser=?")
	        .setParameter(0, id_user).list();
		return list;
	}	
	
	public List<ConsumptionSelectView> getConsumptionSelectView(int id_user) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("select c.id, c.name as name " +
					"from Consumption c where c.idUser=?")
		        .setParameter(0, id_user).list();
		return (List<ConsumptionSelectView>) list;
	}    

	public List<ConsumptionGroupSelectView> getConsumptionGroupSelectView(int id_user) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("select c.id, c.name as name " +
					"from ConsumptionGroup c where c.idUser=?")
		        .setParameter(0, id_user).list();
		return (List<ConsumptionGroupSelectView>) list;
	}  
}

