package frc.team972.robot;

import frc.team972.drive.DriveTrain;
import frc.team972.sensors.Sensors;
import frc.team972.stateMachine.RobotStateMachine;
import frc.team972.util.XMLReader;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

	DriveTrain driveTrain;
	Sensors sensors;
	RobotStateMachine stateMachine;

	@Override
	public void robotInit() {
		XMLReader reader = new XMLReader("robotConfig");
		
		driveTrain = new DriveTrain(reader);
	}

	@Override
	public void autonomousInit() {
	}
	
	@Override
	public void autonomousPeriodic() {
	}

}
