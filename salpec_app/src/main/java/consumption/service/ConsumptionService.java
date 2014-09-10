package consumption.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import transaction.model.TransactionView;

import consumption.model.Consumption;
import consumption.model.ConsumptionGroup;
import consumption.model.ConsumptionGroupSelectView;
import consumption.model.ConsumptionSelectView;
import consumption.dao.IConsumptionDAO;

@Transactional(readOnly = true)
public class ConsumptionService implements IConsumptionService {

	// ConsumptionDAO is injected...
	IConsumptionDAO consumptionDAO;
	
	@Transactional(readOnly = false)
	public void addConsumption(Consumption consumption) {
		getConsumptionDAO().addConsumption(consumption);
	}
	
	@Transactional(readOnly = false)
	public void deleteConsumption(Consumption consumption) {
		getConsumptionDAO().deleteConsumption(consumption);
	}
	
	@Transactional(readOnly = false)
	public void updateConsumption(Consumption consumption) {
		getConsumptionDAO().updateConsumption(consumption);
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdateConsumptionGroup(ConsumptionGroup cg) {
		getConsumptionDAO().saveOrUpdateConsumptionGroup(cg);
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdateConsumption(Consumption c) {
		getConsumptionDAO().saveOrUpdateConsumption(c);
	}
	
	@Transactional(readOnly = false)
	public void deleteConsumptionGroup(ConsumptionGroup consumption) {
		getConsumptionDAO().deleteConsumptionGroup(consumption);
	}
	
	public Consumption getConsumptionById(int id) {
		return getConsumptionDAO().getConsumptionById(id);
	}

	public List<Consumption> getConsumptions(int id) {	
		return getConsumptionDAO().getConsumptions(id);
	}

	public List<ConsumptionSelectView> getConsumptionSelectView(int id) {	
		return getConsumptionDAO().getConsumptionSelectView(id);
	}

	public List<ConsumptionGroupSelectView> getConsumptionGroupSelectView(int id) {	
		return getConsumptionDAO().getConsumptionGroupSelectView(id);
	}

	public List<ConsumptionGroup> getConsumptionGroups(int id) {	
		return getConsumptionDAO().getConsumptionGroups(id);
	}

	public List<String[]> getConsumptionsCount(int id, String ie) {	
		return getConsumptionDAO().getConsumptionsCount(id, ie);
	}

	public List<String[]> getConsumptionsValue(int id, String ie) {	
		return getConsumptionDAO().getConsumptionsValue(id, ie);
	}
	
	public List<Consumption> getConsumptionsByGroup(int id_group) {	
		return getConsumptionDAO().getConsumptionsByGroup(id_group);
	}

	public IConsumptionDAO getConsumptionDAO() {
		return consumptionDAO;
	}

	public List<TransactionView> getTransactionViews(int id, int id_user, boolean consumptionGroup) {	
		return getConsumptionDAO().getTransactionViews(id, id_user, consumptionGroup);
	}

	public String getConsumptionName(int id, int id_user, boolean consumptionGroup) {	
		return getConsumptionDAO().getConsumptionName(id, id_user, consumptionGroup);
	}
	
	public void setConsumptionDAO(IConsumptionDAO consumptionDAO) {
		this.consumptionDAO = consumptionDAO;
	}
}
