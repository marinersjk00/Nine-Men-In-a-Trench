import java.io.*;
import java.util.*;
import java.time.*;



class main
{
static Scanner in = new Scanner(System.in);
static class State{
	ArrayList<ArrayList<Character>> state; //2 rows to hold the recesses
	int depth;
	
	State() {//building start state
		this.state = new ArrayList<ArrayList<Character>>();
		this.depth = 0; //depth 0 for start
		state.add(new ArrayList<Character>());
		
		for(int i = 0; i < 1; i++) { 
			if(i == 3 || i == 5 || i == 7) {
				state.get(0).add('-');
			}else {
				state.get(0).add('x');
			}
		}
		state.add(new ArrayList<Character>());
		
		for(int i = 0; i < 10; i++) {
			if(i == 0) state.get(1).add('-');
			else if(i != 9) state.get(1).add((char)(i + 49)); //= i + 1 but in ASCII
			else if(i == 9) state.get(1).add('1');
		}
		
		}
}

static class StateCompareDepth implements Comparator<State>{ //Comparator for priority queue. Prioritizes min depth
	public int compare(State s, State p) {
		if(s.depth < p.depth) {
			return -1;
		}else if(s.depth > p.depth) {
			return 1;
		}else {
			return 0;
		}
	}
}

static ArrayList<ArrayList<Character>> swap(ArrayList<ArrayList<Character>> arr, char a, char b, int ai, int aj, int bi, int bj){ //simple swapping function for swapping two elements in a 2D vector
	

	arr.get(ai).set(aj, b);
	arr.get(bi).set(bj, a);
	
	return arr;
	
	
}

static boolean isGoal(ArrayList<ArrayList<Character>> curr, ArrayList<ArrayList<Character>> goal){ //function used to check if current state is the goal. Called on every state
	for(int i = 0; i < 2; i++){
		for(int j = 0; j < 10; j++){
			if(curr.get(i).get(j) != goal.get(i).get(j)) return false;
		}
	}
	
	
	System.out.print("\n\nSolved puzzle: \n"); //if it makes it out of the for loop then it will return true.
	//printing the current state to visually verify that it is the goal state
	for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 10; j++) {
			System.out.print((char)curr.get(i).get(j));
		}
		System.out.println();
	}
	
	return true;
}
	
	public static void main(String[] args) {
		PriorityQueue<State> next = null;
		ArrayList<ArrayList<Character>> goal = new ArrayList<ArrayList<Character>>();
		State startState = new State();
		
		
		for(int i = 0; i < 2; i++) {
			goal.add(new ArrayList<Character>());
			for(int j = 0; j < 10; j++) {
				if(i == 0) {
					if(j == 3 || j == 5 || j == 7) {
						goal.get(i).add('-');
					}else {
						goal.get(i).add('x');
					}
				}else {
					if(j != 9) {
						goal.get(i).add((char)(j + 49));
					}else {
						goal.get(i).add('-');
					}
				}
			}
		}
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print((char)goal.get(i).get(j));
			}
			System.out.println();
		}
		
		
		System.out.println("Thank you for utilizing Jordan Kuschner's 8-puzzle solver!");
		

	}
}
