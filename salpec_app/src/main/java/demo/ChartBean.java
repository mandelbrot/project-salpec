package demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name="chartBean")
@ViewScoped 
public class ChartBean implements Serializable {
	private static final long serialVersionUID = 2L;
    private PieChartModel pieModel;
    private BarChartModel spendingYearsModel;
    private BarChartModel expenseIncomeYearsModel;
    private BarChartModel profitYearsModel;
    private MeterGaugeChartModel meterGaugeModel;

    @PostConstruct
    public void init() {
        createModels();
    }

    public void itemSelect(ItemSelectEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",  
                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        
    	System.out.println("----------ChartBean.itemSelect: Selektiran: "+ event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
    }  
    
    public PieChartModel getPieModel() {  
        return pieModel;  
    }  

    public BarChartModel getSpendingYearsModel() {
        return spendingYearsModel;
    }
    
    public BarChartModel getExpenseIncomeYearsModel() {
        return expenseIncomeYearsModel;
    }

    public BarChartModel getProfitYearsModel() {
        return profitYearsModel;
    }

    public MeterGaugeChartModel getMeterGaugeModel() {
        return meterGaugeModel;
    }

    private void createModels() {
        pieModel = new PieChartModel();  
        pieModel.set("Gas", 540);  
        pieModel.set("Goingout", 325);  
        pieModel.set("Birthday", 702);  
        pieModel.set("Vacation", 1421);
        pieModel.set("Phone", 121);  
        pieModel.set("Utilites", 1721);  
        pieModel.set("Rent", 2421);  
        
        createBarModels();
    }
    
    private BarChartModel initSpendingYearsModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries gas = new ChartSeries("Gas");
        gas.setLabel("Gas");
        
        gas.set("2009", 70);
        gas.set("2010", 115);
        gas.set("2011", 98);
        gas.set("2012", 95);
        gas.set("2013", 110);
 
        ChartSeries goingout = new ChartSeries("Goingout");
        goingout.setLabel("Goingout");
        goingout.set("2009", 52);
        goingout.set("2010", 60);
        goingout.set("2011", 82);
        goingout.set("2012", 35);
        goingout.set("2013", 78);
        
        ChartSeries birthday = new ChartSeries("Birthday");
        birthday.setLabel("Birthday");
        birthday.set("2009", 32);
        birthday.set("2010", 40);
        birthday.set("2011", 32);
        birthday.set("2012", 25);
        birthday.set("2013", 29);

        ChartSeries vacation = new ChartSeries("Vacation");
        vacation.setLabel("Vacation");
        vacation.set("2009", 252);
        vacation.set("2010", 260);
        vacation.set("2011", 382);
        vacation.set("2012", 435);
        vacation.set("2013", 320);

        ChartSeries phone = new ChartSeries("Phone");
        phone.setLabel("Phone");
        phone.set("2009", 12);
        phone.set("2010", 10);
        phone.set("2011", 12);
        phone.set("2012", 15);
        phone.set("2013", 12);

        ChartSeries utilites = new ChartSeries("Utilites");
        utilites.setLabel("Utilites");
        utilites.set("2009", 1612);
        utilites.set("2010", 1510);
        utilites.set("2011", 1812);
        utilites.set("2012", 2015);
        utilites.set("2013", 1912);

        ChartSeries rent = new ChartSeries("Rent");
        rent.setLabel("Rent");
        rent.set("2009", 2700);
        rent.set("2010", 2700);
        rent.set("2011", 2800);
        rent.set("2012", 3000);
        rent.set("2013", 3000);
        
        model.addSeries(gas);
        model.addSeries(goingout);
        model.addSeries(birthday);
        model.addSeries(vacation);
        model.addSeries(phone);
        model.addSeries(utilites);
        model.addSeries(rent);

        return model;
    }

    private BarChartModel initExpenseIncomeYearsModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries expenses = new ChartSeries();
        expenses.setLabel("Expenses");
        expenses.set("2009", 4770);
        expenses.set("2010", 4871);
        expenses.set("2011", 4913);
        expenses.set("2012", 5298);
        expenses.set("2013", 5076);
        	
        ChartSeries income = new ChartSeries();
        income.setLabel("Income");
        income.set("2009", 4650);
        income.set("2010", 4850);
        income.set("2011", 5450);
        income.set("2012", 5650);
        income.set("2013", 5650);
        
        model.addSeries(expenses);
        model.addSeries(income);

        return model;
    }

    private BarChartModel initProfitYearsModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries income = new ChartSeries();
        income.setLabel("Income");
        income.set("2009", -120);
        income.set("2010", -21);
        income.set("2011", 537);
        income.set("2012", 352);
        income.set("2013", 576);
        
        model.addSeries(income);

        return model;
    }

    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){{
            add(0);
            add(500);
            add(1000);
            add(1500);
            add(2000);
        }};
         
        return new MeterGaugeChartModel(1140, intervals);
    }
    
    private void createBarModels() {
        createSpendingYearsModel();
        createExpenseIncomeYearsModel();
        createProfitYearsModel();
        createMeterGaugeModel();
    }
     
    private void createSpendingYearsModel() {
    	spendingYearsModel = initSpendingYearsModel();
         
    	spendingYearsModel.setTitle("Track your spending history!");
    	spendingYearsModel.setLegendPosition("ne");
         
        Axis xAxis = spendingYearsModel.getAxis(AxisType.X);
        xAxis.setLabel("Year");
         
        Axis yAxis = spendingYearsModel.getAxis(AxisType.Y);
        yAxis.setLabel("$$$");
        yAxis.setMin(0);
        yAxis.setMax(3500);
    }
    
    private void createExpenseIncomeYearsModel() {
    	expenseIncomeYearsModel = initExpenseIncomeYearsModel();
         
    	//expenseIncomeYearsModel.setTitle("Cashflow through years. Because you are your best investment! (We know it's a cliche, but it's true!)");
    	expenseIncomeYearsModel.setLegendPosition("ne");
         
        Axis xAxis = expenseIncomeYearsModel.getAxis(AxisType.X);
        xAxis.setLabel("Year");
         
        Axis yAxis = expenseIncomeYearsModel.getAxis(AxisType.Y);
        yAxis.setLabel("$$$");
        yAxis.setMin(0);
        yAxis.setMax(6000);
    }
    
    private void createProfitYearsModel() {
    	profitYearsModel = initProfitYearsModel();
    	profitYearsModel.setSeriesColors("00ff00,ff3333");
    	profitYearsModel.setTitle("Up/down year history");
    	profitYearsModel.setLegendPosition("ne");
         
        Axis xAxis = profitYearsModel.getAxis(AxisType.X);
        xAxis.setLabel("Year");
         
        Axis yAxis = profitYearsModel.getAxis(AxisType.Y);
        yAxis.setLabel("$$$");
        yAxis.setMin(-500);
        yAxis.setMax(2000);
    }

    private void createMeterGaugeModel() {
    	meterGaugeModel = initMeterGaugeModel();
        meterGaugeModel.setTitle("Know if you are making or losing money. (Or if you are above or below your benchmark)");
        meterGaugeModel.setSeriesColors("ff6666,ff0000,ff3333,66ff66,00ff00");
        meterGaugeModel.setGaugeLabel("$$$");
        meterGaugeModel.setGaugeLabelPosition("bottom");
        //meterGaugeModel.setShowTickLabels(false);
        //meterGaugeModel.setLabelHeightAdjust(150);
        //meterGaugeModel.setIntervalOuterRadius(80);
    }
}