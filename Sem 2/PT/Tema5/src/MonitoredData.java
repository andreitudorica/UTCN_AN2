import org.joda.time.DateTime;
public class MonitoredData {
	private DateTime startTime,endTime;
	private String activityLable;
	public MonitoredData() {
	}
	public DateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	public DateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	public String getActivityLable() {
		return activityLable;
	}
	public void setActivityLable(String activityLable) {
		this.activityLable = activityLable;
	}
	public String toString()
	{
		return this.startTime.toString()+" "+this.endTime.toString()+" "+this.activityLable;
	}
	
	public Long getLengthOfActivity()
	{
		return this.getEndTime().getMillis()-this.getStartTime().getMillis();
	}
	
}