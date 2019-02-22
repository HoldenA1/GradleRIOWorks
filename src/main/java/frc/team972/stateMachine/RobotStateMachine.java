package frc.team972.stateMachine;

import java.util.ArrayList;

import frc.team972.drive.DriveTrain;

public class RobotStateMachine {
	
	ArrayList<RobotState> actions;
	RobotState currentAction;
	double robotSpeed;
	long actionStartTime;
	
	public RobotStateMachine(ArrayList<RobotState> actions, double robotSpeed) {
		this.actions = actions;
		this.robotSpeed = robotSpeed;
		actionStartTime = System.currentTimeMillis();
	}
	
	public void update(DriveTrain drive) {
		// Checks if there are any actions to be completed
		if (actions.size() == 0) {
			System.out.println("[Done with actions]");
			sleep(100);
			return;
		}
		
		currentAction = actions.get(0);
		
		switch(currentAction.getState()) {
		case DRIVE_BACKWARDS_TIME:
			driveTime(drive, -robotSpeed, -robotSpeed);
			break;
		case DRIVE_FORWARDS_TIME:
			driveTime(drive, robotSpeed, robotSpeed);
			break;
		case TURN_LEFT_TIME:
			driveTime(drive, -robotSpeed, robotSpeed);
			break;
		case TURN_RIGHT_TIME:
			driveTime(drive, robotSpeed, -robotSpeed);
			break;
		case DO_NOTHING:
			sleep((long) currentAction.getValue());
			actions.remove(0);
			actionStartTime = System.currentTimeMillis();
			break;
		default:
			break;
		
		}
	}
	
	private void driveTime(DriveTrain drive, double leftSpeed, double rightSpeed) {
		long now = System.currentTimeMillis();
		if (now + currentAction.getValue() > actionStartTime)
			drive.driveSides(leftSpeed, rightSpeed);
		else {
			drive.stop();
			actions.remove(0);
			actionStartTime = now;
		}
	}
	
	private void sleep(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
