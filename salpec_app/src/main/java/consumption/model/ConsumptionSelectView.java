package consumption.model;

public class ConsumptionSelectView extends IConsumption{
	
	public ConsumptionSelectView() {
		super();
	}
	
	public ConsumptionSelectView(int id, String name) {
		super(id, name);
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", consumption name : ").append(getName());
		return strBuff.toString();
	}
}
