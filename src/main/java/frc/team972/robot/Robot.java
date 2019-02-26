package frc.team972.robot;

import frc.team972.drive.DriveTrain;
import frc.team972.sensors.Sensors;
import frc.team972.stateMachine.RobotState;
import frc.team972.stateMachine.RobotStateMachine;
import frc.team972.util.XMLReader;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

	DriveTrain driveTrain;
	Sensors sensors;
	RobotStateMachine stateMachine;
	RobotState driveForwards;
	ArrayList<RobotState> actions = new ArrayList<>();

	@Override
	public void robotInit() {
		XMLReader reader = new XMLReader("robotConfig");
		
		driveTrain = new DriveTrain(reader);
		
		driveForwards = new RobotState(RobotState.State.DRIVE_FORWARDS_TIME, 1000);
		
		actions.add(driveForwards);
	}

	@Override
	public void autonomousInit() {
		stateMachine = new RobotStateMachine(actions, 0.3);
	}
	
	@Override
	public void autonomousPeriodic() {
		stateMachine.update(driveTrain);
	}

}
