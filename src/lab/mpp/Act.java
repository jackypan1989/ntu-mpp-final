package lab.mpp;

public class Act {
	String name;
	String place;
	String time;
	int cheakId;

	Act(String inn, String inp, String intime, int inId) {
		name = inn;
		place = inp;
		time = intime;
		cheakId = inId;
	}
	int test(){
		return cheakId;
	}
}
