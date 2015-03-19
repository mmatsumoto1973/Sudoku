import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * 
 */
public class Sudoku {

	static class SudokuSolver {
		static final int SIDE_LENGTH = 9;
		static final int MIN_VALUE = 1;
		static final int MAX_VALUE = 9;

		byte[][] questionData;
		byte[][] resultData;
		boolean[][] isFixData;

		SudokuSolver () {
			questionData = new byte[SIDE_LENGTH][SIDE_LENGTH];
			isFixData = new boolean[SIDE_LENGTH][SIDE_LENGTH];
		}

		void readQuestion(String inputFile) {

			try (FileReader fr = new FileReader(inputFile);
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
						if (!cell.isEmpty()) {
							value = Byte.parseByte(cell);
							isFixed = true; 
						}
						questionData[row][col] = value;
						isFixData[row][col] = isFixed;
						col++;
					}
					row++;
				}
				
				resultData = new byte[questionData.length][];
				for (int i = 0; i < questionData.length; i++) {
					resultData[i] = Arrays.copyOf(questionData[i], row);
				}
			} catch(IOException ex) {
				System.out.println("parse error.");
			}
		}

		void outputResult(String outputFile) {

			try (FileWriter fw = new FileWriter(outputFile);
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
				System.out.println("output error.");
			}
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
				byte value = MIN_VALUE ;
				while (value <= MAX_VALUE) {
					if (isValidValue(row, col, value)) {
						resultData[row][col] = value;
						if (isLastRow(row) && isLastCol(col)) {
							return true;	
						} else if (isLastCol(col)){
							if (solve(row+1, 0)) {
								return true;	
							}
						} else {
							if (solve(row, col+1)) {
								return true;	
							}
						}
					}
					value++;
				}
				resultData[row][col] = 0;
			}

			return false;
		}

		boolean isValidValue(int row, int col, byte value) {
			for (int i = 0; i < SIDE_LENGTH; i++) {
				// 行
				if (i != col) {
					if (resultData[row][i] == value) {
						return false;
					}
				}
				// 列
				if (i != row) {
					if (resultData[i][col] == value) {
						return false;
					}
				}
			}
			//  ボックス
			int start_row = row/3*3;
			int start_col = col/3*3;
			for (int i = start_row; i < start_row + 3; i++) {
				for (int j = start_col; j < start_col + 3; j++) {
					if (i != row && j != col) {
						if (resultData[i][j] == value) {
							return false;
						}
					}
				}
			}
			return true;
		}

		boolean isLastRow(int row) {
			return row == (SIDE_LENGTH - 1);
		}

		boolean isLastCol(int col) {
			return col == (SIDE_LENGTH - 1);
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

	}

	static String input_filename;
	static String output_filename;

    public static void main(String[] args) {

		long start = 0;
		long stop = 0;

		try {
			start = System.currentTimeMillis();
			setArgument(args);

			SudokuSolver solver = new SudokuSolver(); 
			solver.readQuestion(input_filename);
			solver.solve();
			solver.outputResult(output_filename);

			stop = System.currentTimeMillis();
			System.out.println("prcess time : " + (stop - start));
			solver.outputResultConsole();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

    }

    private static void setArgument(String[] args) throws Exception {
        if ((args.length < 1)  || (args.length > 2))
            throw new IllegalArgumentException("Usage : \n   Sudoku inputfile outputfile");

        input_filename = args[0];
        output_filename = args[1];
    }
}