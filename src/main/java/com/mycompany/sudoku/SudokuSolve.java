package com.mycompany.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class SudokuSolve {

	byte initData[][];
	boolean isFixData[][];

	SudokuSolve () {
		initData = new byte[9][9];
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
					if (!StringUtils.isEmpty(cell)) {
						value = Byte.parseByte(cell);
						isFixed = true; 
					}
					initData[row][col] = value;
					isFixData[row][col] = isFixed;
					col++;
				}
				row++;
			}
        } catch(IOException ex) {

        }
	}

	void outputResult() {
		System.out.println("[問題]");
		System.out.println("+------------------");
		for (byte[] rowData : initData) {
			System.out.printf("|%1$d %2$d %3$d %4$d %5$d %6$d %7$d %8$d %9$d%n", 
				rowData[0], rowData[1], rowData[2], rowData[3], rowData[4], rowData[5], rowData[6], rowData[7], rowData[8]);
		}
		System.out.println("+------------------");
	}

	void submit() {
	}
}
