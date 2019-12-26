import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/*This program is written to solve cnf problems. It will read an input.cnf file and
 * create an output.cnf file to write a single solution */


public class s150116020 {
	//this is an updateArray function, takes two array one is 2D other is 1D
	//updates 2D arrays rows with checking second array
	//if a row will not solves the problem updates 1.column of this row to -1
	private static int[][] updateArray(int mat[][],int update[]) {
		
		int[] arr = new int[mat[0].length];
		for(int i=0;i<arr.length;i++) {
			arr[i]=-1;
		}
		
		for(int i=0;update[i]!=0;i++) {
			if(update[i]<0) {
				int num=Math.abs(update[i]);
				arr[num-1]=1;
			}else {
				int num=Math.abs(update[i]);
				arr[num-1]=0;
			}
		}
		
		int control=0;
		
		for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat[i].length;j++) {
				 if(mat[i][0]==-1 ) {
					 control = 0;
					 break;
				 }else {
					 if(arr[j]==-1) {
						 continue;
					 }else if(mat[i][j]==arr[j]) {
						 control = 1;
					 }else {
						 control=0;
						 break;
					 }
				 }
			}
			if(control==0) {
				continue;
			}else if(control==1) {
				mat[i][0]=-1;
				control=0;
				continue;
			}
		}
		return mat;
    }
	
	//creates a truth table with 1 and 0's
    private static int[][] createTruthTable(int f) {
        int rows = (int) Math.pow(2,f);
        int[][] table = new int[(int) Math.pow(2, f)][f];

        for (int i=0; i<rows; i++) {
            for (int j=f-1; j>=0; j--) {
                table[i][j]=(i/(int) Math.pow(2, j))%2;
            }
        }
		return table;
    }
    
    //checks the array and creates a cnf file "output.cnf"
    //if there is a solution of cnf problem writes SAT to file and writes single solution
    //else writes just UNSAT
    public static void print2D(int mat[][]) throws IOException{ 
    	FileWriter fileWriter = new FileWriter("output.cnf");
    	PrintWriter printWriter = new PrintWriter(fileWriter);
        // Loop through all rows 
    	int control=0;
    	int count =1;
        for (int i = 0; i < mat.length; i++) {
        	if(mat[i][0]==-1) {
        		control++;
        		continue;
        	}else {
        		printWriter.println("SAT");
        		for (int j = 0; j < mat[i].length; j++) {
        			if(mat[i][j]==1) {
        				printWriter.print(count++ + " ");
        			}else if(mat[i][j]==0) {
        				printWriter.print((-1)*count++ + " ");
        			}
        			
            		
            	}
        		printWriter.close();
        		return;
        	}
        }
        if(control==mat.length) {
        	printWriter.print("UNSAT");
        	printWriter.close();
        }
    } 
    
    
    public static void main(String[] args) throws IOException {
    	
    	String st;
		try {
			int num1=0, num2=0;
			Scanner sc = new Scanner(new File("input.cnf"));
			
			//st is the first line of cnf file
			st=sc.nextLine(); 
			
			//splits the line by spaces
			String[] msg = st.split(" ");
			
			//for first four word third is number of variables fourth is number of proposition
			//cast them to integer and updates num1 and num2
			for(int i=0; i<4; i++) {
	    		if(i==2) {
	    			num1=Integer.parseInt(msg[i]);
	    		}else if(i==3) {
	    			num2=Integer.parseInt(msg[i]);
	    		}
	    	}
			
			//creates a truth table by number of variables
			int[][] table =createTruthTable(num1);
			
			//this will check propositions num2 time
			for(int i=0;i<num2;i++) {
				//checks line by line and split them
				st=sc.nextLine();
				String[] line = st.split(" ");
				int[] intArray = new int[line.length];
				
				//for each numbers in type string convert them integer and add intArray
				for(int j=0;Integer.parseInt(line[j])!=0;j++) {
					intArray[j]=Integer.parseInt(line[j]);
				}
				
				//calls update array function and update it with checking integers
				table=updateArray(table,intArray);
			}
			//close the file
			sc.close();
			
			//print SAT or UNSAT and single possibility if SAT 
			print2D(table);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
