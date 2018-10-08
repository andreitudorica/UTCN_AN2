import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import javax.swing.*;

public class Main {
	
	public static void GUI() throws IOException
	{
		JFrame frame;
		JTabbedPane pnlMain;
		JPanel pnl1,pnl2,pnl3,pnl4,pnl5;
		JTextArea ta1,ta2,ta3,ta4,ta5;
		frame = new JFrame("Depot Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		pnlMain = new JTabbedPane();
		pnl1 = new JPanel();
		pnl2 = new JPanel();
		pnl3 = new JPanel();
		pnl4 = new JPanel();
		pnl5 = new JPanel();
		pnlMain.addTab("Assignment 1", pnl1);
		pnlMain.addTab("Assignment 2", pnl2);
		pnlMain.addTab("Assignment 3", pnl3);
		pnlMain.addTab("Assignment 4", pnl4);
		pnlMain.addTab("Assignment 5", pnl5);
		ArrayList<String> lines1 = (ArrayList<String>) Files.readAllLines(Paths.get("ex1.txt"));
		ArrayList<String> lines2 = (ArrayList<String>) Files.readAllLines(Paths.get("ex2.txt"));
		ArrayList<String> lines3 = (ArrayList<String>) Files.readAllLines(Paths.get("ex3.txt"));
		ArrayList<String> lines4 = (ArrayList<String>) Files.readAllLines(Paths.get("ex4.txt"));
		ArrayList<String> lines5 = (ArrayList<String>) Files.readAllLines(Paths.get("ex5.txt"));
		ta1= new JTextArea();
		ta2= new JTextArea();
		ta3= new JTextArea();
		ta4= new JTextArea();
		ta5= new JTextArea();
		lines1.forEach(l->ta1.append(l+"\n"));
		lines2.forEach(l->ta2.append(l+"\n"));
		lines3.forEach(l->ta3.append(l+"\n"));
		lines4.forEach(l->ta4.append(l+"\n"));
		lines5.forEach(l->ta5.append(l+"\n"));
		pnl1.add(ta1);
		pnl2.add(ta2);
		pnl3.add(ta3);
		pnl4.add(ta4);
		pnl5.add(ta5);
		frame.add(pnlMain);
		frame.setVisible(true);
	}
	
	public static void main(String args[]) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fileName = "D://Andrei/Scoala/PT2017_30421_Tudorica_Andrei/Tema5/Activities.txt";
		ArrayList<MonitoredData> monitoredData=new ArrayList<MonitoredData>();
		ArrayList<String> list=new ArrayList<>();
		try {
			list=(ArrayList<String>) Files.lines(Paths.get(fileName)).collect(Collectors.toList());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(String s:list)
		{
			MonitoredData m=new MonitoredData();
			String[] splitted=s.split(" |\\t");
			Date d;
			try {
				d = formatter.parse(splitted[0]+" "+splitted[1]);
				m.setStartTime(new DateTime(d));
				d = formatter.parse(splitted[3]+" "+splitted[4]);
				m.setEndTime(new DateTime(d));
				m.setActivityLable(splitted[6]);
				monitoredData.add(m);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Integer> Days=(ArrayList<Integer>) monitoredData.stream().map(m-> new Integer(m.getStartTime().getDayOfYear())).collect(Collectors.toList());//get start times
		monitoredData.stream().map(m->m.getEndTime().getDayOfYear()).collect(Collectors.toCollection(() -> Days));//add End times
		Map<String, Long> ex2 = monitoredData.stream().collect(Collectors.groupingBy(m -> m.getActivityLable(),  Collectors.counting())); //ex 2
		Map<Object,Map<Object,Long>> ex3=monitoredData.stream().collect(Collectors.groupingBy(m->new Integer( Days.stream().distinct().filter(t->t.equals(m.getStartTime().getDayOfYear())).collect(Collectors.toList()).get(0)),Collectors.groupingBy(m->m.getActivityLable(),  Collectors.counting())));
		Map<String, LongSummaryStatistics> ex4=monitoredData.stream().collect(Collectors.groupingBy(m->m.getActivityLable(),Collectors.summarizingLong(t->t.getLengthOfActivity())));
		Map<String, Long> ex5_1=monitoredData.stream().collect(Collectors.groupingBy(m->m.getActivityLable(),Collectors.counting()));
		Map<String, Long> ex5_2=monitoredData.stream().filter(t->t.getLengthOfActivity() < 5*60000).collect(Collectors.groupingBy(m->m.getActivityLable(),Collectors.counting()));
		ArrayList<String> ex5 = (ArrayList<String>) ex5_2.entrySet().stream().filter(e->ex5_1.get(e.getKey())*0.9<=e.getValue()).map(e->e.getKey()).collect(Collectors.toList());
		try{
		    PrintWriter writer1 = new PrintWriter("ex1.txt", "UTF-8");
		    writer1.println( Days.stream().distinct().count());//Ex 1
		    writer1.close();

		    PrintWriter writer2 = new PrintWriter("ex2.txt", "UTF-8");
		    ex2.forEach((e1,e2)->writer2.println(e1.toString() + ", " + e2));
		    writer2.close();
		    
		    PrintWriter writer3 = new PrintWriter("ex3.txt", "UTF-8");
		    ex3.forEach((e1,e2)->writer3.println(e1.toString() + ", " + e2));
		    writer3.close();
		
		    PrintWriter writer4 = new PrintWriter("ex4.txt", "UTF-8");
		    for (Map.Entry entry : ex4.entrySet()) {
				if(((LongSummaryStatistics)entry.getValue()).getSum()>36000000)
					writer4.println(entry.getKey().toString() + ", " + ((LongSummaryStatistics)entry.getValue()).getSum()/60000+" minutes"); 
			}//Ex4
		    writer4.close();
		    
			    PrintWriter writer5 = new PrintWriter("ex5.txt", "UTF-8");
			    ex5.forEach(e->writer5.println(e));
			    writer5.close();
		} catch (IOException e) {
		}
		try {
			GUI();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
