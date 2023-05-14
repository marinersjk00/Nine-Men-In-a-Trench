import java.io.*;
import java.util.*;
import java.time.*;



class main
{
static Scanner in = new Scanner(System.in);
static class State{
	ArrayList<ArrayList<Character>> state; //2 rows to hold the recesses
	int depth;
	int manhattan;
	
	State() {//building start state

		this.state = new ArrayList<ArrayList<Character>>();
		this.depth = 0; //depth 0 for start
		state.add(new ArrayList<Character>());
		
		for(int i = 0; i < 10; i++) { 
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
		
		

		;
		
		}
	
	State(ArrayList<ArrayList<Character>> curr, int dep){
		this.state = curr;
		this.depth = dep;
		
	}
	
	State(ArrayList<ArrayList<Character>> curr, int dep, int man){
		this.state = curr;
		this.depth = dep;
		this.manhattan = man;
		
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

static class StateCompareManhattan implements Comparator<State>{ //Comparator for priority queue. Prioritizes min(depth + numMisplaced)
	public int compare(State s, State p) {
		if((s.depth + s.manhattan) < (p.depth + p.manhattan)) {
			return -1;
		}else if((s.depth + s.manhattan) > (p.depth + p.manhattan)) {
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
			if(curr.get(i).get(j) == goal.get(i).get(j))continue;
			else return false;
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

static int countManhattan(ArrayList<ArrayList<Character>> curr) { //Manhattan Distance is calculated by finding the distance
	//from each soldier's current position to their proper position in the goal state
	int manhattanDis = 0;
	
	for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 10; j++) {
			switch(curr.get(i).get(j)) {
				
				case '1':
					manhattanDis += j;
					if (i == 0) manhattanDis++;
					break;
					
				case '2':
					manhattanDis += Math.abs(j-1);
					if(i == 0) manhattanDis++;
					break;
					
				case '3':
					manhattanDis += Math.abs(j-2);
					if(i == 0) manhattanDis++;
					break;
				case '4':
					manhattanDis += Math.abs(j-3);
					if(i == 0) manhattanDis++;
					break;
					
				case '5':
					manhattanDis += Math.abs(j-4);
					if(i == 0) manhattanDis++;
					break;
					
				case '6':
						manhattanDis += Math.abs(j-5);
						if(i == 0) manhattanDis++;
						break;
				case '7':
					manhattanDis += Math.abs(j-6);
					if(i == 0) manhattanDis++;
					break;
				case '8':
					manhattanDis += Math.abs(j-7);
					if(i == 0) manhattanDis++;
					break;
				case '9':
					manhattanDis += Math.abs(j-8);
					if(i == 0) manhattanDis++;
					break;
					
				default:
					break;
			}
			
		}
	}
	
	return manhattanDis;
	
}

static void uniformSearch(State curr, HashMap<ArrayList<ArrayList<Character>>, Integer> seen, Queue<State> next) {
		
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 10; j++) {
				if(curr.state.get(i).get(j) != 'x') { //x's don't move
					 
						//checking down
					if(i == 0) {
						ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
						for(int k = 0; k < 2; k++) {
							copy.add(new ArrayList<Character>());
							for(int m = 0; m < 10; m++) {
								copy.get(k).add(curr.state.get(k).get(m));
							}
							
						}
						
						if(curr.state.get(i + 1).get(j) == '-') { //open space to swap to
							swap(copy, copy.get(i).get(j), copy.get(i + 1).get(j), i, j, i + 1, j);
							State nextState = new State(copy, curr.depth + 1);
							if(!seen.containsKey(nextState.state)) {
								seen.put(nextState.state,  curr.depth + 1);
								next.add(nextState);
							}
						}
						continue;
					}
					
					if(j > 0) {
						ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
						for(int k = 0; k < 2; k++) {
							copy.add(new ArrayList<Character>());
							for(int m = 0; m < 10; m++) {
								copy.get(k).add(curr.state.get(k).get(m));
							}
							
						}
						
						if(curr.state.get(i).get(j-1) == '-') { //open space to swap to
							swap(copy, copy.get(i).get(j), copy.get(i).get(j-1), i, j, i, j - 1);
							State nextState = new State(copy, curr.depth + 1);
							if(!seen.containsKey(nextState.state)) {
								seen.put(nextState.state,  curr.depth + 1);
								next.add(nextState);
							}
						}
						
					}
					
					if(j < 9) {
						ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
						for(int k = 0; k < 2; k++) {
							copy.add(new ArrayList<Character>());
							for(int m = 0; m < 10; m++) {
								copy.get(k).add(curr.state.get(k).get(m));
							}
							
						}
						
						if(curr.state.get(i).get(j+1) == '-') { //open space to swap to
							swap(copy, copy.get(i).get(j), copy.get(i).get(j+1), i, j, i, j + 1);
							State nextState = new State(copy, curr.depth + 1);
							if(!seen.containsKey(nextState.state)) {
								seen.put(nextState.state,  curr.depth + 1);
								next.add(nextState);
							}
						}
						
					}
					
					if(i == 1) {
						ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
						for(int k = 0; k < 2; k++) {
							copy.add(new ArrayList<Character>());
							for(int m = 0; m < 10; m++) {
								copy.get(k).add(curr.state.get(k).get(m));
							}
							
						}
						
						if(curr.state.get(i - 1).get(j) == '-') { //open space to swap to
							 swap(copy, copy.get(i).get(j), copy.get(i - 1).get(j), i, j, i - 1, j);
							State nextState = new State(copy, curr.depth + 1);
							if(!seen.containsKey(nextState.state)) {
								seen.put(nextState.state,  curr.depth + 1);
								next.add(nextState);
							}
						}
					}
					
				}
			}
		}
		
		
	
	}

static void manhattanSearch(State curr, HashMap<ArrayList<ArrayList<Character>>, Integer> seen, Queue<State> next) {
	
	
	for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 10; j++) {
			if(curr.state.get(i).get(j) != 'x') { //x's don't move
				 
					//checking down
				if(i == 0) {
					ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
					for(int k = 0; k < 2; k++) {
						copy.add(new ArrayList<Character>());
						for(int m = 0; m < 10; m++) {
							copy.get(k).add(curr.state.get(k).get(m));
						}
						
					}
					
					if(curr.state.get(i + 1).get(j) == '-') { //open space to swap to
						int manhattanDis = countManhattan(swap(copy, copy.get(i).get(j), copy.get(i + 1).get(j), i, j, i + 1, j));
						State nextState = new State(copy, curr.depth + 1, manhattanDis);
						if(!seen.containsKey(nextState.state)) {
							seen.put(nextState.state,  curr.depth + 1);
							next.add(nextState);
						}
					}
					continue;
				}
				
				if(j > 0) {
					ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
					for(int k = 0; k < 2; k++) {
						copy.add(new ArrayList<Character>());
						for(int m = 0; m < 10; m++) {
							copy.get(k).add(curr.state.get(k).get(m));
						}
						
					}
					
					if(curr.state.get(i).get(j-1) == '-') { //open space to swap to
						int manhattanDis = countManhattan(swap(copy, copy.get(i).get(j), copy.get(i).get(j-1), i, j, i, j-1));
						State nextState = new State(copy, curr.depth + 1, manhattanDis);
						if(!seen.containsKey(nextState.state)) {
							seen.put(nextState.state,  curr.depth + 1);
							next.add(nextState);
						}
					}
					
				}
				
				if(j < 9) {
					ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
					for(int k = 0; k < 2; k++) {
						copy.add(new ArrayList<Character>());
						for(int m = 0; m < 10; m++) {
							copy.get(k).add(curr.state.get(k).get(m));
						}
						
					}
					
					if(curr.state.get(i).get(j+1) == '-') { //open space to swap to
						int manhattanDis = countManhattan(swap(copy, copy.get(i).get(j), copy.get(i).get(j+1), i, j, i, j+1));
						State nextState = new State(copy, curr.depth + 1, manhattanDis);
						if(!seen.containsKey(nextState.state)) {
							seen.put(nextState.state,  curr.depth + 1);
							next.add(nextState);
						}
					}
					
				}
				
				if(i == 1) {
					ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>();
					for(int k = 0; k < 2; k++) {
						copy.add(new ArrayList<Character>());
						for(int m = 0; m < 10; m++) {
							copy.get(k).add(curr.state.get(k).get(m));
						}
						
					}
					
					if(curr.state.get(i - 1).get(j) == '-') { //open space to swap to
						int manhattanDis = countManhattan(swap(copy, copy.get(i).get(j), copy.get(i - 1).get(j), i, j, i - 1, j));
						State nextState = new State(copy, curr.depth + 1, manhattanDis);
						if(!seen.containsKey(nextState.state)) {
							seen.put(nextState.state,  curr.depth + 1);
							next.add(nextState);
						}
					}
				}
				
			}
		}
	}
	
	

}
	
public static void UniformCostSearch(ArrayList<ArrayList<Character>> goalState, HashMap<ArrayList<ArrayList<Character>>, Integer> seenStates, PriorityQueue<State> next) {

	boolean isSolvable = false;
	int maxDepth = 0;
	State finished = null;
	int numExpansions = 0; //each while loop iteration is an expanded node
	//loop displays each state and current depth on each iteration
	Instant insta = Instant.now();
	long startTime = insta.toEpochMilli();
	State start = next.peek();
	int maxQueue = 0;
	
	
	while(!next.isEmpty() && !isSolvable) { //loops until queue is empty (unsolvable) or goal state is achieved (solvable)
		maxQueue = Math.max(maxQueue, next.size());
		State curr = next.remove();
		maxDepth = Math.max(maxDepth,  curr.depth);
		
		System.out.print("\nExpanding State with g(n) =  " + curr.depth + " and h(n) = " + curr.manhattan + "\n\n");
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print((char)curr.state.get(i).get(j));
			}
			System.out.println();
		}
		isSolvable = isGoal(curr.state, goalState);
		if(isSolvable) finished = curr;
		manhattanSearch(curr, seenStates, next);	
		numExpansions++;
	
		
	}
	insta = Instant.now();
	long endTime = insta.toEpochMilli();
	if(isSolvable) { //if we can solve the puzzle we will print some stats for it
		
		
		System.out.print("\nSolved!\nDepth of Solution: " + finished.depth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000 + " sec\nNumber of Nodes Expanded = " + numExpansions + "\nMax"
				+ " Queue Size: " + maxQueue + "\n\n");
		System.out.println("Starting puzzle: ");
		for(int i = 0; i < 2; i++){ //printing the initial state as additional info
			for(int j = 0; j < 10; j++){
				System.out.print(start.state.get(i).get(j) + "  ");
			}
			System.out.println();
		}
	
	}else System.out.print("\nNo solution for given input.\nMax Depth Reached: " + maxDepth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000 + "\nMax Queue Size: " + maxQueue);
	
	
}
	
	public static void main(String[] args) {
		PriorityQueue<State> next = null; //to pick next state
		ArrayList<ArrayList<Character>> goal = new ArrayList<ArrayList<Character>>(); //the goal state of the problem
		HashMap<ArrayList<ArrayList<Character>>, Integer> seen = new HashMap<>(); //tracking seen states
	//	State startState = new State(); //the start state
	//	seen.put(startState.state,  0);
		ArrayList<ArrayList<Character>> test = new ArrayList<ArrayList<Character>>();
		test.add(new ArrayList<Character>());
		
		test.get(0).add('x');
		test.get(0).add('x');
		test.get(0).add('x');
		test.get(0).add('-');
		test.get(0).add('x');
		test.get(0).add('-');
		test.get(0).add('x');
		test.get(0).add('-');
		test.get(0).add('x');
		test.get(0).add('x');
	
		test.add(new ArrayList<Character>());
		test.get(1).add('-');
		test.get(1).add('2');
		test.get(1).add('3');
		test.get(1).add('4');
		test.get(1).add('5');
		test.get(1).add('6');
		test.get(1).add('7');
		test.get(1).add('8');
		test.get(1).add('9');
		test.get(1).add('1');
		
		State testStart = new State(test, 0);


		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print((char)testStart.state.get(i).get(j));
			}
			System.out.println();
		}
		
		
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
		
	
		
	
		
		
		next = new PriorityQueue<State>(new StateCompareManhattan());
		next.add(testStart);
		UniformCostSearch(goal, seen, next ); 
		
		
		System.out.println("Thank you for utilizing Jordan Kuschner's Nine Men in a Trench solver!");
		

	}
}
