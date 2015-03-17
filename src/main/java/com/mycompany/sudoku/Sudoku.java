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
			setArgument(args);

			SudokuSolve solve = new SudokuSolve(); 

			start = System.currentTimeMillis();
			solve.readQuestion(input_filename);
			stop = System.currentTimeMillis();
			System.out.println("prcess time readQuestion: " + (stop - start));

			start = System.currentTimeMillis();
			solve.solve();
			stop = System.currentTimeMillis();
			System.out.println("prcess time solve: " + (stop - start));

			start = System.currentTimeMillis();
			solve.outputResult(output_filename);
			stop = System.currentTimeMillis();
			System.out.println("prcess time outputResult: " + (stop - start));

			solve.outputResultConsole();
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