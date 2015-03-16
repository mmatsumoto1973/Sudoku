package com.mycompany.sudoku;

/**
 *
 */
public class Sudoku {

	static String input_filename;
	static String output_filename;

    public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		setArgument(args);

		SudokuSolve solve = new SudokuSolve(); 
		solve.readQuestion(input_filename);
		solve.solve();
		solve.outputResult(output_filename);
		long stop = System.currentTimeMillis();

		System.out.println("prcess time : " + (stop - start));
		solve.outputResultConsole();
    }

    private static void setArgument(String[] args) throws Exception {
        if ((args.length < 1)  || (args.length > 2))
            throw new IllegalArgumentException("Parameter Error.");

        input_filename = args[0];
        output_filename = args[1];
    }

}