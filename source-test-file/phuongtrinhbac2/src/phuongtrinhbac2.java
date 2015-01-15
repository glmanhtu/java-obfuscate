/**
 * 
 */
import javax.swing.*;
/**
 * @author shine
 *
 */
public class phuongtrinhbac2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JTextField aField = new JTextField(5);
	      JTextField bField = new JTextField(5);
	      JTextField cField = new JTextField(5);

	      JPanel myPanel = new JPanel();
	      myPanel.add(aField);
	      myPanel.add(new JLabel("x² + "));
	      myPanel.add(Box.createHorizontalStrut(5)); // a spacer
	      myPanel.add(bField);
	      myPanel.add(new JLabel("x + "));
	      myPanel.add(Box.createHorizontalStrut(5)); // a spacer
	      myPanel.add(cField);
	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter A, B and C Values", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  
	    	  double[] kq = solved(Double.parseDouble(aField.getText()),Double.parseDouble(bField.getText()),Double.parseDouble(cField.getText()));
	    	  if(kq[0] == 1){
	    		  JOptionPane.showMessageDialog(null, "Phương trình có nghiệm kép x = "+kq[1]);
	    	  }else if(kq[0] == 2){
	    		  JOptionPane.showMessageDialog(null, "Phương trình có 2 nghiệm x1 = "+kq[1]+" và x2 = "+kq[2]);
	    	  }else{
	    		  JOptionPane.showMessageDialog(null, "Phương trình vô nghiệm ");
	    	  }
	    		  
	      }
	}
	public static double[] solved(double a, double b, double c){
		
		double delta = (b * b) - (4 * a * c);
		double x1,x2;
		double result[] = new double[3];
        if (delta >= 0) {
            if (delta > 0) {
                x1 = ((-b) - Math.sqrt(delta)) / (2 * a);
                x2 = ((-b) + Math.sqrt(delta)) / (2 * a);
                result[0] = 2;
                result[1] = x1;
                result[2] = x2;
            } else {
                x1 = (-b) / (2 * a);
                result[0] = 1;
                result[1] = x1;
            }
        }else{
        	result[0] = 0;
        }
        return result;
	}

}
