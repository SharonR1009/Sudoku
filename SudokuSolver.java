import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SudokuSolver{
	
	public static void main(String[] args) throws IOException  {
		//String inputPath = "/Users/sharon/Desktop/SudokuSolver/input1.txt";
		//String outputPath = "/Users/sharon/Desktop/SudokuSolver/output1.txt";
		String inputPath = args[0];
		String outputPath = args[1];
		
		int[][] Matrix = parseInput(inputPath);
		
		if(complete(0,0,Matrix))
		{
			for(int i=0; i<9; i++)
			{
				for(int j=0; j<9; j++)
				{
					System.out.print(Matrix[i][j]+" ");					
				}
				System.out.print("\n");
			}
			writeMatrix(Matrix, outputPath);
		}
		else
			System.out.println("No Result Found!");
		
	}
	//
	static boolean complete(int i, int j, int[][] Matrix){
		//not filled
		if(j == 9)
		{
			j = 0;
			i++;
			if(i == 9)
				return true;
		}
		if(Matrix[i][j] == 0)
		{
			for(int value=1; value<10; value++)
			{
				if(checkRow(i,j,value,Matrix) && checkColumn(i,j,value,Matrix) && checkBlock(i,j,value,Matrix))
				{
					Matrix[i][j] = value;
					if(complete(i,j+1,Matrix))
						return true;
				}
			}
			Matrix[i][j] = 0;
		}
		else//already filled
			return complete(i,j+1,Matrix);
		
		return false;
	}
	//check constraints
	static boolean checkRow(int i, int j, int value, int[][] Matrix){
		for(int k=0; k<9; k++)
		{
			if(Matrix[i][k] == value)
				return false;
		}
		return true;
	}
	static boolean checkColumn(int i, int j, int value, int[][] Matrix){
		for(int k=0; k<9; k++)
		{
			if(Matrix[k][j] == value)
				return false;
		}
		return true;
	}
	static boolean checkBlock(int i, int j, int value, int[][] Matrix){
		int temp_i = (i/3)*3;
		int temp_j = (j/3)*3;
		for(int m=0; m<3; m++)
		{
			for(int n=0; n<3; n++)
			{
				if(Matrix[temp_i+m][temp_j+n] == value)
					return false;
			}
		}
		//no conflict
		return true;
	}
	//
	static int[][] parseInput(String inputPath) throws IOException{
		File file = new File(inputPath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		String string = "";
		int n = 0;
		int[][] Matrix = new int[9][9];
		
		while((tempString = reader.readLine()) != null)
		{
			string = string + tempString;
			
		}
		string = string.replaceAll(" ", "");
		
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(string.charAt(n) == '-')
				{
					Matrix[i][j] = 0;
					n++;
				}
				else
				{
					Matrix[i][j] = Integer.parseInt(String.valueOf(string.charAt(n)));
					n++;
				}
			}
		}
		reader.close();
		
		return Matrix;
	}
	//
	static void writeMatrix(int[][] Matrix, String path) throws IOException{
		FileWriter fileWrite = new FileWriter(path);
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				fileWrite.write(Matrix[i][j] + " ");
			}
			fileWrite.write("\n");
		}
		fileWrite.close();
	}

}
