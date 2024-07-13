package telran.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

record AppArgs (String filePath, String codFileNme, String commentsFileName) {
}

public class CodeCommentsSeparation {

	public static void main(String[] args) {
		
		//args[0] - file path for file containing both Java class code and comments
		//args[1] - result file with only code
		//args[2] -result file with only comments
		// example of args[0] "src/telran/io/test/InputOutputTest.java" 
		//TODO 
		//from one file containing code and comments to create two files
		//one with only comments and second with only code
		
		try {
			AppArgs appArgs = getAppArgs(args);
			separation(appArgs);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void separation(AppArgs appArgs) throws Exception {
		try (BufferedReader reader = new BufferedReader(new FileReader(appArgs.filePath()));
				PrintWriter codWriter = new PrintWriter(appArgs.codFileNme());
				PrintWriter commentsWriter = new PrintWriter(appArgs.commentsFileName());
				){
			reader.lines().forEach(line -> {
                if (line.trim().startsWith("//")) commentsWriter.println(line);
                else codWriter.println(line);
            });
		}
				
				
	}

	private static AppArgs getAppArgs(String[] args) throws Exception {
		if(args.length != 3) throw new Exception ("incorrect number of arguments");
		if(!Files.exists(Path.of(args[0]))) throw new Exception("the file was not found at the specified path");
		return new AppArgs(args[0], args[1], args[2]);
	}
}
