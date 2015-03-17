package com.mycompany.sudoku;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class Sudoku {

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
			Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
		}

    }

    private static void setArgument(String[] args) throws Exception {
        if ((args.length < 1)  || (args.length > 2))
            throw new IllegalArgumentException("Parameter Error.");

        input_filename = args[0];
        output_filename = args[1];
    }

}