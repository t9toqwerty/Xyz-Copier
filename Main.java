/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * 
 * @author RAHUL JHA
 */
public class Main {

	Stack<String> source_files = new Stack<String>();

	void displayDirectoryContents(File dir) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					// System.out.println("directory:" +
					// file.getCanonicalPath());
					displayDirectoryContents(file);
				} else {
					// System.out.println(file.getCanonicalPath());
					source_files.push(file.getCanonicalPath().toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String target_directory;

	public static void main(String args[]) throws IOException,
			InterruptedException {
		Main m = new Main();
		MainWindow main_mw = null;

		Copy c = new Copy();

		if (m.target_directory == null) {

			JFileChooser targetDir = new JFileChooser();
			targetDir.setDialogTitle("Choose Target Directory.");
			targetDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (targetDir.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				m.target_directory = targetDir.getSelectedFile().toString();
				main_mw = new MainWindow("XYZ Copier");
				main_mw.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			} else {
				System.exit(0);
			}
		} else {
		}
		Queue<String> source_file = new LinkedList<String>();
		Queue<String> target_file = new LinkedList<String>();
		for (int i = 0; i < args.length; i++) {
			if (new File(args[i]).isDirectory()) {
				String temp1[] = args[i].toString().split("/");
				String base_directory = temp1[temp1.length - 1];
				m.displayDirectoryContents(new File(args[i]));
				Iterator<String> sIterator = m.source_files.iterator();
				while (sIterator.hasNext()) {
					String temp = sIterator.next();
					source_file.add(temp);
					String temp2 = temp.substring(args[i].length(),
							temp.length());
					String temp3 = m.target_directory + "/" + base_directory
							+ temp2;
					String temp4[] = temp3.split("/");
					String temp5 = temp3.replace(temp4[temp4.length - 1], "");
					// System.out.println(temp5);
					if (!new File(temp5).exists()) {
						if (new File(temp5).mkdirs()) {
							// System.out.println("Directory doesn't exist but created successfully.");
						} else {

							// System.out.println("Directory doesn't exist and not created.");
						}
					} else {
						// System.out.println("Directory exists.");
					}
					System.out.println(new File(temp).length());
					c.all_file_size = c.all_file_size + new File(temp).length();
					target_file.add(temp3);

				}
			} else {
				File file = new File(args[i]);
				String temp[] = file.getAbsolutePath().split("/");
				/*
				 * System.out.println("Ading file.....");
				 * System.out.println(file);
				 * System.out.println(m.target_directory + "/" +
				 * temp[temp.length - 1]);
				 */
				source_file.add(file.toString());
				System.out.println(file.length());
				c.all_file_size = c.all_file_size + file.length();
				System.out.println(c.all_file_size);
				target_file.add(m.target_directory + "/"
						+ temp[temp.length - 1]);
			}
		}

		System.out.println(source_file.size());
		System.out.println(target_file.size());
		System.out.println("peek  value of source queue " + source_file.peek());
		System.out.println("peek  value of target queue " + target_file.peek());
		System.out.println("Total file size = "
				+ c.getFileSize(c.all_file_size));
		c.file_no = source_file.size();
		Iterator<String> sf = source_file.iterator();
		Iterator<String> tf = target_file.iterator();
		while (sf.hasNext() && tf.hasNext()) {
			/*
			 * System.out.println(sf.next()); System.out.println(tf.next());
			 */
			c.streamCopy(sf.next(), tf.next(), main_mw);
		}
		if (c.all_copiedfileSize_percentage == 100) {
			main_mw.total_progress.setValue(0);
			JOptionPane.showMessageDialog(main_mw,
					"All Filed Copied Succesfully.");
			System.exit(0);
		}
	}
}
