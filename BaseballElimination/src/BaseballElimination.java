import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

class Team
{
	int wins;
	int loss;
	int left;
	String name;
	int teamNo;
	public Team(String name, int wins, int loss, int left,int teamNo)
	{
		this.name=name;
		this.wins=wins;
		this.loss=loss;
		this.left=left;
		this.teamNo = teamNo;
	}
}
public class BaseballElimination
{
	private int teamNumber = 0;
	private Map<String,Team> map;
	private Map<Integer,String> map1;
	private int[][] matches;
	private int source;
	private int tail;
	
	private int calculateVertices(int vertices)
	{
		int mult= (vertices-1)*(vertices-2);
		mult = mult/2;
		//allow for total teams + combinations of 2 + 2
		return mult+vertices+2;
	}

	private boolean isTrivial(String team)
	{
		Team team1 = map.get(team);
		Team topTeam = map.get(map1.get(0));
		if(team1.wins+team1.left<topTeam.wins)
			return true;
		else
			return false;
	}
	public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
	{	
		In input = new In(filename);
		teamNumber = Integer.parseInt(input.readLine());
		int count = 0;
		map = new HashMap<>();
		map1 = new HashMap<>();
		matches = new int[teamNumber][teamNumber];
		
		for(String string : input.readAllLines())
		{
			string = string.trim().replaceAll(" +", " ");
			String teamResult[] = string.split(" ");
			String teamName = teamResult[0];
			int wins = Integer.parseInt(teamResult[1]);
			
			int loss = Integer.parseInt(teamResult[2]);
			int left = Integer.parseInt(teamResult[3]);
			Team team = new Team(teamName,wins,loss,left,count);
			map.put(teamName,team);
			map1.put(count,teamName);
			int count1=0;
			for(int i=4;i<teamResult.length;i++)
			{
				//check for the same team result and remove it
				matches[count][count1]= Integer.parseInt(teamResult[i]);
				count1++;
			}
			count = count+1;
		}	
	}
	public	int numberOfTeams()                        // number of teams
	{
		return teamNumber;
	}
	public Iterable<String> teams()                                // all teams
	{	
		Set<String> set = map.keySet();
		Iterable<String> iterable = set;
		return iterable;
	}
	public	int wins(String team)                      // number of wins for given team
	{
		if(!map.containsKey(team))
		{
			throw new IllegalArgumentException();
		}
		Team team1 = map.get(team);
		return team1.wins;
	}
	public int losses(String team)                    // number of losses for given team
	{
		if(!map.containsKey(team))
		{
			throw new IllegalArgumentException();
		}
		Team team1 = map.get(team);
		return team1.loss;
	}
	public int remaining(String team)                 // number of remaining games for given team
	{
		if(!map.containsKey(team))
		{
			throw new IllegalArgumentException();
		}
		Team team1 = map.get(team);
		return team1.left;
	}
	public int against(String team1, String team2)    // number of remaining games between team1 and team2
	{
		if(!map.containsKey(team1)|| !map.containsKey(team2))
		{
			throw new IllegalArgumentException();
		}
		Team team = map.get(team1);
		int teamNo1=team.teamNo;
		team = map.get(team2);
		int teamNo2=team.teamNo;	
		return matches[teamNo1][teamNo2];
	}

	private FlowNetwork createNetwork(String team)
	{
		Team team1 = map.get(team);
		int teamNo = team1.teamNo;
		
		int totalVertex = calculateVertices(teamNumber);
		FlowNetwork network = new FlowNetwork(totalVertex);
		source =totalVertex-2;
		tail = totalVertex-1;
		int countCombined = teamNumber;
		
		//make it to fetch according to the team number			
		for(int i=0;i<teamNumber;i++)
		{
			if(i==teamNo)
				continue;
			for(int j=i+1;j<teamNumber;j++)
			{
				if(j==teamNo)
					continue;
				int capacity = matches[i][j];
				FlowEdge edge1 = new FlowEdge(countCombined,i,Double.POSITIVE_INFINITY);
				network.addEdge(edge1);	
				FlowEdge edge3 = new FlowEdge(countCombined,j,Double.POSITIVE_INFINITY);
				network.addEdge(edge3);
				FlowEdge edge2 = new FlowEdge(source,countCombined,capacity);
				network.addEdge(edge2);
				countCombined++;						
			}
			int capacity1 = team1.wins+team1.left-map.get(map1.get(i)).wins;
			if(capacity1<0)
				capacity1=0;
			FlowEdge edge4 = new FlowEdge(i,tail,capacity1);
			network.addEdge(edge4);
		}
		new FordFulkerson(network,source,tail);
		return network;
	}
	public boolean isEliminated(String team)              // is given team eliminated?
	{
		if(!map.containsKey(team))
		{
			throw new IllegalArgumentException();
		}
		if(isTrivial(team))
		{
			return true;
		}
		FlowNetwork solution = createNetwork(team);
		for(FlowEdge edge : solution.adj(source))
		{
			if(edge.capacity()!=edge.flow())
				return true;
		}
		return false;
	}
	public Iterable<String> certificateOfElimination(String team) 
	{
		if(!map.containsKey(team))
		{
			throw new IllegalArgumentException();
		}
		if(!isEliminated(team))
		{
			return null;
		}
		Team team1 = map.get(team);
		int score = team1.left+team1.wins;
		List<String> resultList = new ArrayList<>();
		int sum = 0;
		int average = 0;
		int count = 1;
		for(int i=0;i<teamNumber;i++)
		{
			if(i==team1.teamNo)
				continue;
			for(int j=0;j<=i;j++)
			{
				if(j==team1.teamNo)
					continue;
				sum = sum + matches[i][j];
			}
			Team currentTeam = map.get(map1.get(i));
			sum = sum + currentTeam.wins;
			average = (sum)/count;
			if(average>=score-1)
			{
				resultList.add(currentTeam.name);
			}
			count++;
		}
		Iterable<String> result = resultList;
		return result;
	}
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination("teams4.txt");  
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}