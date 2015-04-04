/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author RAHUL JHA
 */
public class Copy {
	int file_no = 0;
	int current_file_no=0;
	long copiedfileSize = 0;
	long all_copiedfileSize = 0;
	double copiedfileSize_percentage = 0;
	double all_copiedfileSize_percentage = 0;
	long totalfileSize = 0;
	String currentfileSize = "";
	String current_write_speed = "";
	long all_file_size = 0;
	static boolean isPaused = false;

	Copy() {
	}

	String calculate_Speed(int length, double sec) {
		double length_in_MB = length / (1024 * 1024);
		double speed = length_in_MB / sec;
		speed = Math.round(speed);
		return speed + " MB/SEC";
	}

	String get_actual_time(double nano_seconds) {
		int seconds = (int) (nano_seconds / 1000000000);
		if (seconds >= 60) {
			return (seconds / 60) + " Min " + (seconds % 60) + " Sec";
		} else {
			return "" + seconds;
		}
	}

	void copyFileChannel(String target_file, String target_directory)
			throws IOException {
		FileChannel input_channel = null;
		FileChannel output_channel = null;
		try {
			long start = System.nanoTime();
			input_channel = new FileInputStream(target_file).getChannel();
			output_channel = new FileOutputStream(target_directory)
					.getChannel();
			output_channel.transferFrom(input_channel, 0, input_channel.size());
			long end = System.nanoTime();
			System.out.println("Total time to copy " + (end - start)
					/ 1000000000);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Copy.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Copy.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			input_channel.close();
			output_channel.close();
		}
	}

	int i = 0;

	void copyBufferedStream(String target_file, String target_directory)
			throws FileNotFoundException, IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				target_file), 24 * 1024);
		BufferedOutputStream fos = new BufferedOutputStream(
				new FileOutputStream(target_directory), 24 * 1024);
		long start = System.nanoTime();
		while ((i = bis.read()) != -1) {
			System.out.println(i);
			fos.write(i);
		}
		long end = System.nanoTime();
		System.out.println("Total time:- " + (end - start) / 1000000000
				+ " Seconds.");
	}

	String getFileSize(long total_size) {
		double size = total_size;
		String unit = "";
		if (size > 1 && size < 1024) {
			unit = " Byte";
		} else if (size >= 1025 && size < 1024 * 1024) {
			size = size / 1024;
			unit = " KB";
		} else if (size >= 1048576 && size < 1073741824) {
			unit = " MB";
			size = size / (1024 * 1024);
		} else if (size >= 1073741824) {
			size = size / (1024 * 1024 * 1024);
			unit = " GB";
		} else {
			size = 00;
		}

		return (Math.round(size * 100.0) / 100.0) + unit;
	}

	String getFileSize(String path_of_file) {
		File file = new File(path_of_file);
		double size = file.length();
		String unit = "";
		if (file.exists()) {
			if (size > 1 && size < 1024) {
				unit = " Byte";
			} else if (size >= 1025 && size < 1024 * 1024) {
				size = size / 1024;
				unit = " KB";
			} else if (size >= 1048576 && size < 1073741824) {
				unit = " MB";
				size = size / (1024 * 1024);
			} else if (size >= 1073741824) {
				size = size / (1024 * 1024 * 1024);
				unit = " GB";
			} else {
				size = 00;
			}
		}
		return (int) size + unit;
	}

	void streamCopy(String source_file, String target_file,
			MainWindow mainwindow) throws FileNotFoundException, IOException {
		InputStream is = null;
		OutputStream os = null;
		File get_source_file, get_target_file;
		get_source_file = new File(source_file);
		get_target_file = new File(target_file);
		long source_file_size = get_source_file.length();
		long target_file_size;
		is = new FileInputStream(source_file);
		os = new FileOutputStream(target_file);
		byte[] buffer = new byte[24 * 1024];
		int length;
		long start = System.nanoTime();
		current_file_no=current_file_no+1;
		mainwindow.current_file_name.setText(get_source_file.getName());
		mainwindow.file_number.setText("Copying "+current_file_no+" Out Of "+file_no+" ");
		while ((length = is.read(buffer)) > 0) {
			long old_target_file_size = get_target_file.length();
			os.write(buffer, 0, length);
			long new_target_file_size = get_target_file.length();
			double ins_end = System.nanoTime();
			target_file_size = get_target_file.length();
			copiedfileSize = target_file_size;
			System.out.println("All Copied file" + all_copiedfileSize);
			all_copiedfileSize = all_copiedfileSize
					+ (new_target_file_size - old_target_file_size);
			mainwindow.current_file_size.setText((getFileSize(target_file))
					+ " of " + getFileSize(source_file));
			mainwindow.total_file_size.setText(getFileSize(all_copiedfileSize)+" of "+getFileSize(all_file_size));
			copiedfileSize_percentage = (copiedfileSize * 100)
					/ source_file_size;
			System.out.println("All Copied file" + all_copiedfileSize);
			System.out.println("All file size " + all_file_size);
			all_copiedfileSize_percentage = (all_copiedfileSize * 100)
					/ all_file_size;
			mainwindow.total_progress
					.setValue((int) all_copiedfileSize_percentage);
			mainwindow.total_progress.setStringPainted(true);
			System.out.println(all_copiedfileSize_percentage + " %");

			mainwindow.current_progress
					.setValue((int) copiedfileSize_percentage);
			mainwindow.current_progress.setStringPainted(true);
			while (isPaused) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("here");
			}
		}
		mainwindow.current_progress.setValue(0);
		os.flush();
		os.close();
		is.close();
		long end = System.nanoTime();
		System.out.println("Total time taken :- " + (end - start) / 1000000000
				+ " Seconds.");
	}
}
