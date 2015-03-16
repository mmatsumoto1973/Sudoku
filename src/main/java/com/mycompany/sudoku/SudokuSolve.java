package com.mycompany.sudoku;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class SudokuSolve {
	static final int MATRIX_NUM = 9;
	static final int MIN_VALUE = 1;
	static final int MAX_VALUE = 9;

	byte[][] questionData;
	byte[][] resultData;
	boolean[][] isFixData;

	SudokuSolve () {
		questionData = new byte[MATRIX_NUM][MATRIX_NUM];
		isFixData = new boolean[MATRIX_NUM][MATRIX_NUM];
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

	void outputResult(String outputFile) {
		try(FileWriter fw = new FileWriter(outputFile);
           BufferedWriter br = new BufferedWriter(fw)) {

			for (byte[] line : resultData) {
				StringBuilder s = new StringBuilder();
				for (int i = 0; i <  line.length; i++) {
					if (i != 0) {
						s.append(",");
					}
					s.append(line[i]);
				}
				br.write(s.toString());
				br.newLine();
			}
        } catch(IOException ex) {
			System.out.println("parse error.");
        }
	}
	void outputResultConsole() {
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

	void solve() {
		solve(0, 0);
	}
	
	boolean solve(int row, int col) {
		if (isFixData[row][col]) {
			if (isLastRow(row) && isLastCol(col)) {
				return true;	
			} else if (isLastCol(col)){
				return solve(row+1, 0);
			} else {
				return solve(row, col+1);
			}
	 	} else {
			boolean isSolved = false;
			byte value = MIN_VALUE ;
			while (value <= MAX_VALUE) {
				if (isValidValue(row, col, value)) {
					resultData[row][col] = value;
					if (isLastRow(row) && isLastCol(col)) {
						return true;	
					} else if (isLastCol(col)){
						if (solve(row+1, 0)) {
							isSolved = true;
							break;	
						}
					} else {
						if (solve(row, col+1)) {
							isSolved = true;
							break;	
						}
					}
				}
				value++;
			}
			if (isSolved) {
				return true;
			}
			resultData[row][col] = 0;
		}

		return false;
	}

	boolean isValidValue(int row, int col, byte value) {
		// 行、列、ボックスで同じ値が使用されていない事を評価する
		return isValidRowValue(row, col, value) 
			&& isValidColValue(row, col, value) 
			&& isValidBoxValue(row, col, value);
	}

	// 行
	boolean isValidRowValue(int row, int col, byte value) {
		for (int i = 0; i < MATRIX_NUM; i++) {
			if (i != col) {
				if (resultData[row][i] == value) {
					return false;
				}
			}
		}
		return true;
	}

	// 列
	boolean isValidColValue(int row, int col, byte value) {
		for (int i = 0; i < MATRIX_NUM; i++) {
			if (i != row) {
				if (resultData[i][col] == value) {
					return false;
				}
			}
		}
		return true;
	}

	// ボックス
	boolean isValidBoxValue(int row, int col, byte value) {
		int start_row = row/3*3;
		int start_col = col/3*3;

		for (int i = start_row; i < start_row + 3; i++) {
			for (int j = start_col; j < start_col + 3; j++) {
				if (i != row || j != col) {
					if (resultData[i][j] == value) {
						return false;
					}
				}
			}
		}
		return true;
	}

	boolean isLastRow(int row) {
		return row == (MATRIX_NUM - 1);
	}

	boolean isLastCol(int cell) {
		return cell == (MATRIX_NUM - 1);
	}

}
