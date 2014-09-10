package demo;

public class ChartContentBean
{
	private int year;
	private int passPercentage;
	private int failedPercentage;
	
	public ChartContentBean()
	{
	}
	
	public ChartContentBean(int year, int passPercentage, int failedPercentage)
	{
		this.year = year;
		this.passPercentage = passPercentage;
		this.failedPercentage = failedPercentage;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public int getPassPercentage()
	{
		return passPercentage;
	}
	
	public void setPassPercentage(int passPercentage)
	{
		this.passPercentage = passPercentage;
	}
	
	public int getFailedPercentage()
	{
		return failedPercentage;
	}
	
	public void setFailedPercentage(int failedPercentage)
	{
		this.failedPercentage = failedPercentage;
	}
}

