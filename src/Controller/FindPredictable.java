package Controller;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindPredictable extends JOptionPane{
	private static final long serialVersionUID = 1L;
	private String str;
	
	public FindPredictable(JTextField[] text) {
		String[] str = new String[text.length];
		for(int i = 0; i < text.length; i++) {
			str[i] = text[i].getText();
		}
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Select the feature that the problem can predict"));
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox combo = new JComboBox(str);
		panel.add(combo);
		
		showOptionDialog(null, panel, "Select Predictable", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Enter"}, "Enter");
		this.str = str[combo.getSelectedIndex()];
	}
	
	public String getStr() {
		return str;
	}
}
