package com.mycompany.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class SudokuSolve {

	byte[][] questionData;
	byte[][] resultData;
	boolean[][] isFixData;

	SudokuSolve () {
		questionData = new byte[9][9];
		isFixData = new boolean[9][9];
	}

	void readQuestion(String inputFile) {
		try(FileReader fr = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(fr)) {

			String str;
			int row = 0;

			while((str = br.readLine()) != null){
				int col = 0;
				String[] rowData = str.split(",", 0);
				for (String cell : rowData) {
					byte value = 0; 
					boolean isFixed = false; 
					cell = cell.trim();
					if (!StringUtils.isEmpty(cell) && cell.compareTo("0") !=0 ) {
						value = Byte.parseByte(cell);
						isFixed = true; 
					}
					questionData[row][col] = value;
					isFixData[row][col] = isFixed;
					col++;
				}
				row++;
			}
			
			// 
			resultData = new byte[questionData.length][];
			for (int i = 0; i < questionData.length; i++) {
				resultData[i] = Arrays.copyOf(questionData[i], row);
			}
        } catch(IOException ex) {
			System.out.println("parse error.");
        }
	}

	void outputResult() {
		// 問題出力
		System.out.println("[問題]");
		System.out.println("+------------------");
		for (byte[] rowData : questionData) {
			System.out.printf("|%1$d %2$d %3$d %4$d %5$d %6$d %7$d %8$d %9$d%n", 
				rowData[0], rowData[1], rowData[2], rowData[3], rowData[4], rowData[5], rowData[6], rowData[7], rowData[8]);
		}
		System.out.println("+------------------");
		System.out.println("");

		// 回答出力
		System.out.println("[回答]");
		System.out.println("+------------------");
		for (byte[] rowData : resultData) {
			System.out.printf("|%1$d %2$d %3$d %4$d %5$d %6$d %7$d %8$d %9$d%n", 
				rowData[0], rowData[1], rowData[2], rowData[3], rowData[4], rowData[5], rowData[6], rowData[7], rowData[8]);
		}
		System.out.println("+------------------");
	}

	void submit() {
	}
}
