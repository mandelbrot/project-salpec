package consumption.service;

import java.util.List;

import transaction.model.TransactionView;

import consumption.model.Consumption;
import consumption.model.ConsumptionGroup;
import consumption.model.ConsumptionGroupSelectView;
import consumption.model.ConsumptionSelectView;

public interface IConsumptionService {
	public void addConsumption(Consumption consumption);
	public void updateConsumption(Consumption consumption);
	public void saveOrUpdateConsumptionGroup(ConsumptionGroup cg);
	public void saveOrUpdateConsumption(Consumption c);
	public void deleteConsumptionGroup(ConsumptionGroup cg);
	public void deleteConsumption(Consumption consumption);
	public Consumption getConsumptionById(int id);
	public List<Consumption> getConsumptions(int id_user);
	public List<ConsumptionSelectView> getConsumptionSelectView(int id_user);
	public List<ConsumptionGroupSelectView> getConsumptionGroupSelectView(int id_user);
	public List<ConsumptionGroup> getConsumptionGroups(int id_user);
	public List<Consumption> getConsumptionsByGroup(int id_group);
	public List<TransactionView> getTransactionViews(int id_consumption, int id_user, boolean consumptionGroup);
	public String getConsumptionName(int id_consumption, int id_user, boolean consumptionGroup);
	public List<String[]> getConsumptionsCount(int id, String ie);
	public List<String[]> getConsumptionsValue(int id, String ie);
}
