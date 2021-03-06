import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import jess.*;
import jess.awt.TextAreaWriter;

public class InvCon extends JFrame {
	TextArea t;
	JButton b1,b2;
	JTextField tf1, tf2, tf3,tf4;
	JLabel jl1, jl2, jl3,jl4;
	String fs;
	Rete engine;
	
	InvCon() {
		setTitle("재정상담");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = new TextArea(10, 50);
		JScrollPane js = new JScrollPane(t);
		js.setSize(400, 200);
		js.setLocation(20, 15);
		add(js);
		
		MyActionListener ml = new MyActionListener();
		
		b1 = new JButton("상담시작");
		b1.addActionListener(ml);
		b1.setSize(180, 30);
		b1.setLocation(450, 30);
		add(b1);
		
		b2 = new JButton("초기화");
		b2.addActionListener(ml);
		b2.setSize(180, 30);
		b2.setLocation(630, 30);
		add(b2);
		
		jl4 = new JLabel("일정치");
	    tf4 = new JTextField(20);
	    jl4.setSize(100, 30);
	    tf4.setSize(260, 30);   
	    jl4.setLocation(450, 80);
	    tf4.setLocation(550, 80);
	    add(jl4);
	    add(tf4);
		
	    jl1 = new JLabel("연소득");
		tf1 = new JTextField(20);
		jl1.setSize(100, 30);
		tf1.setSize(260, 30);	
		jl1.setLocation(450, 120);
	    tf1.setLocation(550, 120);
	    add(jl1);
	    add(tf1);
	    
	    jl2= new JLabel("부양가족");
	    tf2 = new JTextField(20);
	    jl2.setSize(100, 30);
	    tf2.setSize(260, 30);	
	    jl2.setLocation(450, 160);
	    tf2.setLocation(550, 160);
	    add(jl2);
	    add(tf2);
	    
	    jl3= new JLabel("저축액");
	    tf3 = new JTextField(20);
	    jl3.setSize(100, 30);
	    tf3.setSize(260, 30);	
	    jl3.setLocation(450, 200);
	    tf3.setLocation(550, 200);
	    add(jl3);
	    add(tf3);
	    
	    setSize(850,300);
	    setVisible(true);
	}
	private class MyActionListener implements ActionListener {
	     public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if(b == b1) {
			t.append("\n \n *** 상담 의뢰인의 현황 *** \n \n" );
			t.append("일정치 : " + tf4.getText() +"\n" );
			t.append("연수입 : " +  "$"+ tf1.getText() + "\n");
			t.append("부양가족 : " + tf2.getText() + "명" + "\n");
			t.append("저축액 : " + "$" + tf3.getText() + "\n");
	        fs = "";
	        fs = fs + "\n(assert (earnings " +  tf1.getText() + " " + tf4.getText()+"))";
			fs = fs + "\n(assert (dependents " +  tf2.getText() + "))";
			fs = fs + "\n(assert (amount_saved " +  tf3.getText() + "))";
			fs = fs + "\n (run)";
			t.append("\n \n *** 상담 결과 *** \n \n" );		
			btnRunActionPerformed(e);
		} else if (b == b2){
			t.setText(""); tf1.setText(""); tf2.setText("");
			tf3.setText(""); fs = "";} 
	    }
	}
	private void btnRunActionPerformed(ActionEvent e) {
		try {
			ReadJessFile readJessFile = new ReadJessFile("D:\\My Document\\Eclipse Workspace\\ConsultInvestment_201511102\\bin\\Consult.clp");
			String strTemp = readJessFile.getJessFileContent();		
			if (strTemp.substring(0, 1).equals("1")) {  // 0번째 첫 문자열 
				this.engine = new Rete();
				TextAreaWriter taw = new TextAreaWriter(t);
				engine.addOutputRouter("t", taw);
				strTemp = strTemp + fs;
				this.engine.executeCommand(strTemp.substring(1)); 
	            // 1번째부터 전체 문자열, 자바에서 Jess 실행 
			}		
	              	} catch (JessException ex) {
	                                  //	this.taTrace.append(ex.toString() + "\n");
	        }	
	}
	public static void main(String [] args) {
		new InvCon();
	}
}
