package frc.team972.stateMachine;

public class RobotState {
	
	private State state;
	private double value;
	
	enum State {
		DO_NOTHING,
		DRIVE_FORWARDS_TIME,
		DRIVE_BACKWARDS_TIME,
		TURN_RIGHT_TIME,
		TURN_LEFT_TIME;
	}
	
	public RobotState(State state, double value) {
		this.state = state;
		this.value = value;
	}
	
	public State getState() {
		return state;
	}
	
	public double getValue() {
		return value;
	}

}
