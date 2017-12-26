
public class CenterStar {
	
	public static int starIndex;
	public static String centerString;
	public static int direction;
	public static String globalAlign[]=new String [2];
	public static String multipleAlign[];
	public static String sequence[] = new String[10000];  
	public static int totalScore = 0;

	
	//Main function called in a static way by another program
	public static String cumpute(char[][] matrix,int n) {				
		
		findStarIndex(n,sequence);
		centerString = sequence[starIndex];
		multipleAlign = new String[n];
		multipleAlignment(n,sequence);
		System.out.println("total cost: "+calculateTotalCost(matrix,n));
		
		String result="";
		for(int i=0 ; i< n; i++)			
			result +=multipleAlign[i]+"\n";		
		return result;
	}
	
	
	//this Function calculate the total cost
	public static int calculateTotalCost(char[][] matrix,int n){			
		int length = multipleAlign[0].length();
		for(int i = 0; i< n; i++){
			for( int j =0; j< n; j++){
				 if(j > i){
					 for(int k = 0; k < length; k++){
						 for(int c=0;c<24;c++){
							 if(multipleAlign[i].charAt(k) == matrix[c][0] && multipleAlign[j].charAt(k)==matrix[c][1])						
							 totalScore +=  Character.getNumericValue(matrix[c][2]);							 
						 }
					 }
				 }
			}
		}
		return totalScore;
	}
	
	
	//The Function do the multiple alignment according to the center string 
	public static void multipleAlignment(int n, String sequence[]){
		String centerString2 = centerString;
		
		for(int i=0 ; i< n; i++){
			if(i == starIndex){
				multipleAlign[i] = centerString2;
				continue;
			}
		
			calculateEditDistance(centerString, sequence[i]);
			multipleAlign[i] = globalAlign[1];
			
			if(globalAlign[0].length() > centerString2.length()){	
				for(int j1 = 0,j2 = 0; j1 < globalAlign[0].length(); j1++){					
					if(centerString2.charAt(j2) != globalAlign[0].charAt(j1)){
						StringBuilder a;
						for(int k = 0; k < i; k++){
							a = new StringBuilder(multipleAlign[k]);
							a.insert(j1, '-');
							multipleAlign[k] = a.toString();
						}
					}
					else
						j2++;
				}
				centerString2 = globalAlign[0];
			}
			if(globalAlign[0].length() < centerString2.length()){			
				for(int j1 = 0,j2 = 0; j1 < centerString2.length(); j1++){					
					if(centerString2.charAt(j1) != globalAlign[0].charAt(j2)){
						StringBuilder a;
						a = new StringBuilder(multipleAlign[i]);
						a.insert(j1, '-');
						multipleAlign[i] = a.toString();
					}
					else
						j2++;
				}
			}
		}
	}

	
	//This Function finds the minimum star cost from all sequences
	public static void findStarIndex(int n, String s[]){		
		int editDist = 0;
		int minEditDist = Integer.MAX_VALUE;
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++)
				editDist = editDist + calculateEditDistance(s[i],s[j]);				
			if(editDist < minEditDist){
				minEditDist = editDist;
				starIndex = i;
			}
			editDist = 0;
		}		
	}
	
	
	//Function to calculate the edit distances
	/*   0   diagonal
	 *   1   left
	 *   2   up
	 */
	public static int calculateEditDistance(String seq1, String seq2){		
		if(seq1.equals(seq2))
			return 0;
		int l1 = seq1.length();
		int l2 = seq2.length();
		int match = 0;
		int i,j,k;		
		int score[][] = new int [l1+1][l2+1];
		int trace[][] = new int [l1+1][l2+1];		
		score[0][0] = 0;
		trace[0][0] = 0;
		
		for(i=1; i<=l2; i++){
			score[0][i] = i;
			trace[0][i] = 1;
		}		
		for(j=1; j<=l1; j++){
			score[j][0] = j;
			trace[j][0] = 2;
		}				
		//Filling the remaining cells in the matrix
		for(i=1; i<=l1; i++){
			for(j=1; j<=l2; j++){
				if(seq1.charAt(i-1)==seq2.charAt(j-1))
					match = 0;
				else
					match = 1;		  
				score[i][j] = calculateMinimum(score[i-1][j-1]+match,score[i][j-1]+1,score[i-1][j]+1);
				trace[i][j] = direction;
			}
		}
		
		//Creating the global alignment by the trace found
		i = l1;
		j = l2;
		k=0;
	    char [][] pairAlignment = new char [2][l1+l2];
				
		do{
		if(trace[i][j]==0){
			pairAlignment[0][k] = seq1.charAt(i-1);
			pairAlignment[1][k] = seq2.charAt(j-1);
			i--;
			j--;
			k++;
		}
		else if(trace[i][j]==1){
			pairAlignment[0][k] = '-';
			pairAlignment[1][k] = seq2.charAt(j-1);
			j--;
			k++;
		}
		else{
			pairAlignment[0][k] = seq1.charAt(i-1);
			pairAlignment[1][k] = '-';
			i--;
			k++;
		}
		}while(i!=0 || j!=0);
	
		String input;
		char [][] stringReverse = new char [2][k];
		i=0;
		while(k > 0){
			stringReverse[0][i] = pairAlignment[0][k-1];
			stringReverse[1][i] = pairAlignment[1][k-1];
			i++;
			k--;
		}
		input = String.valueOf(stringReverse[0]);
		globalAlign[0] = input;
		input = String.valueOf(stringReverse[1]);
		globalAlign[1] = input;
		
		return score[l1][l2];
	}
	
	
	//This Function calculates the minimum choice of three choices in the next move
	public static int calculateMinimum(int diagonal, int left, int up){
		int temp = diagonal;
		direction = 0;
		if(temp > left){
			temp = left;
			direction = 1;
		}
		if (temp > up){
			temp = up;
			direction = 2;
		}
		return temp;
	}

}