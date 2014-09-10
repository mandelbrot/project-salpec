package consumption.model;

public class ConsumptionGroupSelectView extends IConsumption{

	public ConsumptionGroupSelectView() {
		super();
	}
	
	public ConsumptionGroupSelectView(int id, String name) {
		super(id, name);
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", consumption group name : ").append(getName());
		return strBuff.toString();
	}
}
