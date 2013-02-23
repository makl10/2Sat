//Student Name : Marc Klocke
//Student ID : 260399607

import java.io.*;
import java.util.*;

public class 2SatSolver {

    // Reads input from stdin or a specified file.  Accepts an optional command-line argument,
    // a flag to indicate whether to use a simple brute-force solution
    // or not.
    public static void main(String[] args) {
        Scanner intScan;
        boolean bruteForce = true;

        Scanner inp = new Scanner(System.in);

        for (int i=0;i<args.length;i++) {
            if (args[i].equals("-nobrute"))
                bruteForce = false;  // don't use brute force, use clever algorithm
            	
            	
            else {
                try {
                    inp = new Scanner(new File(args[i]));
                } catch(FileNotFoundException e) {
                    System.out.println("File not found: "+args[i]);
                    System.exit(1);
                }
            }
        }

        intScan = new Scanner(inp.nextLine());

        // first read in number of vars involved
        int numV = intScan.nextInt();
        boolean check = false;
        ArrayList<Disjunc> disjunctions = new ArrayList<Disjunc>();
        // then read in each disjunction
        while (inp.hasNextLine()) 
        {
            String s = inp.nextLine();
            intScan = new Scanner(s);

            // read in a disjunction: (a \/ b) represented by 2 integers separated by whitespace
            // each variable is represented by a number in the range 1..numV, or -numV..-1
            // (negative numbers represent logically negated values)
            int a = intScan.nextInt();
            int b = intScan.nextInt();     
            
            
            Disjunc d = new Disjunc(a,b);
            disjunctions.add(d);
                      
            //System.out.println("Read "+a+" and "+b);
        }
        
        //Call checking method
        if(bruteForce == true)
        {
        	check = bruteForce(disjunctions, numV);
        }
        
        else if(bruteForce == false)
        {
        	check = noBruteForce(disjunctions, numV);
        }

        
        //Return appropriate response
        if(check == true)
        {
        	System.out.println("There is a solution");
        }
        
        else
        {
        	System.out.println("There is no solution");
        }
        
        
    }
    
    public static boolean noBruteForce(ArrayList<Disjunc> disjunctions, int vars)
    {
    	return true;
    }
    
    public static boolean bruteForce(ArrayList<Disjunc> disjunctions, int vars)
    {
    	//Setting the boolean array to be tested along with the disjunctions
    	boolean[] flags = new boolean[vars];
    	int numMod = (int) Math.pow(2,vars);
    	
    	//Setting the array to all possible binary strings
    	for(int i = 0; i < numMod; i++)
    	{
    		for(int j = 0; j < flags.length; j++)
    		{
    			int val = flags.length * j + i;
    			int ret = (1 & (val >>> j));
    			flags[j] = ret != 0;
    		}
    	
    		//Testing each disjunction with each flag
    		if(!testDisjunctions(flags,disjunctions))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean testDisjunctions(boolean[] flags, ArrayList<Disjunc> disjunctions)
    {
    	boolean left;
    	boolean right;
    	
    	for(Disjunc disjunction : disjunctions)
    	{
    		//Testing individual cases, if one is false then break early if one disjunc is false
    		if(disjunction.a > 0)
    		{
    			left = flags[disjunction.a - 1];
    		}
    		
    		else
    		{
    			left = flags[-1 - disjunction.a];
    		}
    		
    		if(disjunction.b > 0)
    		{
    			right = flags[disjunction.b - 1];
    		}
    		
    		else
    		{
    			right = flags[-1 - disjunction.b];
    		}
    		
    		
    		if (!(left || right))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    //Helper class to define a disjunction
    public static class Disjunc
    {
    	int a;
    	int b;
    	
    	public Disjunc(int x, int y)
    	{
    		a = x;
    		b = y;
    	}
    }
    

    
    
}
