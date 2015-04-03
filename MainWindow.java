import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel first_panel, second_panel, third_panel, fourth_panel, fifth_panel,
			sixth_panel;
	JLabel current_file_name, file_number, current_file_size, total_file_size,
			remaining_time, current_speed;
	JButton skip, pause, close, start;
	JProgressBar total_progress, current_progress;

	MainWindow(String title) {

		this.setSize(650, 150);
		GridLayout gridlayout = new GridLayout(6, 1);
		GridLayout gridlayout2 = new GridLayout(1, 4, 20, 20);
		GridLayout gridlayout3 = new GridLayout(1, 3, 10, 10);
		GridLayout gridlayout1 = new GridLayout(1, 1);
		BorderLayout borderlayout = new BorderLayout();
		this.setLayout(gridlayout);
		first_panel = new JPanel(gridlayout3);
		second_panel = new JPanel(gridlayout1);
		third_panel = new JPanel(gridlayout3);
		fourth_panel = new JPanel(gridlayout1);
		fifth_panel = new JPanel(gridlayout2);
		sixth_panel = new JPanel(borderlayout);
		current_file_name = new JLabel("No File Selected");
		file_number = new JLabel("Copying 0 Out Of 0 ");
		current_file_size = new JLabel(" Currently : 00 MB of 00 MB");
		total_file_size = new JLabel(" Total: 00 MB of 00 MB");
		remaining_time = new JLabel("00 Min 00 Sec  ");
		current_speed = new JLabel("Speed : 00 MB/Sec  ");
		start = new JButton("Start");
		pause = new JButton("Pause");
		skip = new JButton("Skip");
		close = new JButton("Close");
		total_progress = new JProgressBar();
		current_progress = new JProgressBar();
		//first_panel.setBackground(Color.RED);
		this.add(first_panel);
		first_panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		second_panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		third_panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		fourth_panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		fifth_panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		fifth_panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		first_panel.add(current_file_name);
		first_panel.add(file_number);
		first_panel.add(total_file_size);
		//second_panel.setBackground(Color.BLUE);
		second_panel.add(total_progress);
		this.add(second_panel);
		//third_panel.setBackground(Color.GREEN);
		this.add(third_panel);
		third_panel.add(remaining_time);
		third_panel.add(current_speed);
		third_panel.add(current_file_size);
		//fourth_panel.setBackground(Color.CYAN);
		fourth_panel.add(current_progress);
		this.add(fourth_panel);
		//fifth_panel.setBackground(Color.CYAN);
		// sixth_panel.add(current_speed,BorderLayout.LINE_START);
		this.add(sixth_panel);
		fifth_panel.add(start);
		fifth_panel.add(pause);
		fifth_panel.add(skip);
		fifth_panel.add(close);
		this.add(fifth_panel);

		this.setResizable(false);
		this.setTitle(title);

		this.setVisible(true);
	}
}
