import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HyperSudokuSolverHeuristic {

	public static void main(String[] args) throws IOException {
		String inputPath = args[0];
		String outputPath = args[1];
		
		int[][] Matrix = new int[9][9];
		Matrix = parseInput(inputPath);
		
		if(complete(heuristic(Matrix),Matrix,0))
		{
			for(int i=0; i<9; i++)
			{
				for(int j=0; j<9; j++)
				{
					System.out.print(Matrix[i][j]+" ");
				}
				System.out.print("\n");
			}
			writeMatrix(outputPath, Matrix);
		}
		else
			System.out.println("No Result Found!");
		
	}
	//
	static int[] heuristic(int[][] Matrix){
		int min = 10;
		int[] position = new int[2];
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(Matrix[i][j] == 0 && possibleValue(i,j,Matrix) < min)
				{
					min = possibleValue(i,j,Matrix);
					position[0] = i;
					position[1] = j;
				}
			}
		}
		return position;
	}
	//
	static int possibleValue(int i, int j, int[][] Matrix){
		int numb = 0;
		for(int value=1; value<10; value++)
		{
			if(checkRow(i,j,value,Matrix) && checkColumn(i,j,value,Matrix) && checkBlock(i,j,value,Matrix) && checkExtraBlock(i,j,value,Matrix))
				numb++;
		}
		return numb;
	}
	//
	static boolean isFulled(int[][] Matrix){
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(Matrix[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	//
	static boolean complete(int[] position, int[][] Matrix, int nodeNumb){
		int node = nodeNumb;
		
		if(isFulled(Matrix))
		{
			System.out.println("Total Searched Node : " + node);
			return true;
		}
		
		int i = position[0];
		int j = position[1];
		if(Matrix[i][j] == 0)
		{
			for(int value=1; value<10; value++)
			{
				if(checkRow(i,j,value,Matrix) && checkColumn(i,j,value,Matrix) && checkBlock(i,j,value,Matrix) && checkExtraBlock(i,j,value,Matrix))
				{
					Matrix[i][j] = value;
					if(complete(heuristic(Matrix),Matrix, ++node))
						return true;
				}
			}
			Matrix[i][j] = 0;
		}
		else//already filled
			return complete(heuristic(Matrix),Matrix, ++node);
		
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
	static boolean checkExtraBlock(int i, int j, int value, int[][] Matrix){
		int i2 = (i/4)*4+1;
		int j2 = (j/4)*4+1;
		if(i == 0 || i == 4 || i == 8 || j == 0 || j == 4 || j == 8);
		else
		{
			for(int m=0; m<3; m++)
			{
				for(int n=0; n<3; n++)
				{
					if(Matrix[i2+m][j2+n] == value)
						return false;
				}
			}
		}
		//no conflict
		return true;
	}
	//
	static int[][] parseInput(String path) throws IOException{
		int[][] Matrix = new int[9][9];
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		String string = "";
		int n = 0;
		
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
	static void writeMatrix(String path, int[][] Matrix) throws IOException{
		File file = new File(path);
		FileWriter fileWrite = new FileWriter(file);
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
