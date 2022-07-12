package Level_2_0;

public class Solution {
	
	public static class Elevator{    	
    	int elevatorMajor, elevatorMinor, elevatorRevision;
        String elevatorNumber;

        public Elevator(String elevator) {
        	this.elevatorNumber = elevator;
        	
            String[] division = elevator.split("\\.");            
            elevatorMajor = Integer.parseInt(division[0]);
            elevatorMinor = division.length > 1 ? Integer.parseInt(division[1]) : -1;
            elevatorRevision = division.length > 2 ? Integer.parseInt(division[2]) : -1;
        }
    }
	
    public static String[] solution(String[] l) {
    	java.util.List<Elevator> elevatorList = new java.util.ArrayList<Elevator>();
    	for(int i = 0 ; i < l.length ; i++) {
    		elevatorList.add(new Elevator(l[i]));
    	}
    	elevatorList.sort(new java.util.Comparator<Elevator>() {
			@Override
			public int compare(Elevator o1, Elevator o2) {
				if (o1.elevatorMajor < o2.elevatorMajor) return -1;
	            if (o1.elevatorMajor > o2.elevatorMajor) return 1;
	            if (o1.elevatorMinor < o2.elevatorMinor) return -1;
	            if (o1.elevatorMinor > o2.elevatorMinor) return 1;
	            if (o1.elevatorRevision < o2.elevatorRevision) return -1;
	            if (o1.elevatorRevision > o2.elevatorRevision) return 1;
				return 0;
			}
    	});		
    	java.util.ArrayList<String> result = new java.util.ArrayList<String>();
    	for(int i = 0; i < l.length ; i++) {
    		result.add(elevatorList.get(i).elevatorNumber);
    	}
    	
    	return result.toArray(new String[result.size()]);
    }    
}