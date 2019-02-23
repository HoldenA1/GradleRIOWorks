package frc.team972.sensors;

import frc.team972.drive.DriveTrain;
import frc.team972.util.*;
import edu.wpi.first.wpilibj.Encoder;

public class Sensors {
	// Left is the first and right is the second
	Encoder[] leftSideDriveEncoders, rightSideDriveEncoders;
	Motor[] leftSide, rightSide;
	int driveEncoderCount;
	EncoderType driveEncoderType;
	
	public Sensors(XMLReader reader, DriveTrain drive) {
		try {
			setUpDriveEncoders(reader, drive);
		} catch(Exception e) {
			Logger.logError("Something went wrong with the sensor config. Check to see if the config file on the roborio is correct.");
		}
	}
	
	private void setUpDriveEncoders(XMLReader reader, DriveTrain drive) {
		driveEncoderType = EncoderType.getEncoderType(reader.parseXML(RobotSettings.DRIVE_ENCODER_TYPE));
		driveEncoderCount = Integer.parseInt(reader.parseXML(RobotSettings.DRIVE_ENCODER_COUNT));
		
		switch (driveEncoderType) {
		case INTEGRATED:
			leftSide = drive.getLeftDriveMotors();
			rightSide = drive.getRightDriveMotors();
			
			// Sets up the left and right sides
			for (int i = 0; i < driveEncoderCount/2; i++) {
				leftSide[i].setFeedbackSensor();
				rightSide[i + (driveEncoderCount/2)].setFeedbackSensor();
			}
			break;
		case NORMAL:
			String encoderChannels = reader.parseXML(RobotSettings.DRIVE_ENCODER_CHANNELS);
			leftSideDriveEncoders = new Encoder[driveEncoderCount/2];
			rightSideDriveEncoders = new Encoder[driveEncoderCount/2];
			
			// Sets up the left and right sides
			for (int i = 0; i < driveEncoderCount/2; i++) {
				int channelA = Integer.parseInt(encoderChannels.substring(i*2, i*2+1));
				int channelB = Integer.parseInt(encoderChannels.substring((i+1)*2, (i+1)*2+1));
				leftSideDriveEncoders[i] = new Encoder(channelA, channelB);
				
				int x = i + (driveEncoderCount/2);
				int channelC = Integer.parseInt(encoderChannels.substring(x*2, x*2+1));
				int channelD = Integer.parseInt(encoderChannels.substring((x+1)*2, (x+1)*2+1));
				rightSideDriveEncoders[i] = new Encoder(channelC, channelD);
			}
			break;
		}
	}
	
	/**
	 * @return the left drive encoder objects
	 */
	public double getLeftDriveEncoders() {
		double sum = 0;
		for (int i = 0; i < driveEncoderCount/2; i++) {
			switch (driveEncoderType) {
			case INTEGRATED:
				sum += leftSide[i].getIntegratedEncoderPos();
				break;
			case NORMAL:
				sum += leftSideDriveEncoders[i].getDistance();
				break;
			}
		}
		return sum / (driveEncoderCount/2);
	}
	
	/**
	 * @return the right drive encoder objects
	 */
	public double getRightDriveEncoders() {
		double sum = 0;
		for (int i = 0; i < driveEncoderCount/2; i++) {
			switch (driveEncoderType) {
			case INTEGRATED:
				sum += rightSide[i].getIntegratedEncoderPos();
				break;
			case NORMAL:
				sum += rightSideDriveEncoders[i].getDistance();
				break;
			}
		}
		return sum / (driveEncoderCount/2);
	}
	
	public void resetDriveEncoders() {
		switch (driveEncoderType) {
		case INTEGRATED:
			for (Motor l: leftSide)
				l.resetIntegratedEncoder();
			for (Motor r: rightSide)
				r.resetIntegratedEncoder();
			break;
		case NORMAL:
			for (Encoder l: leftSideDriveEncoders)
				l.reset();
			for (Encoder r: rightSideDriveEncoders)
				r.reset();
			break;
		}
	}
	
	public enum EncoderType {
		INTEGRATED, NORMAL;
		
		public static EncoderType getEncoderType(String readerOutput) {
			switch (readerOutput) {
			case "INTEGRATED":
				return EncoderType.INTEGRATED;
			case "NORMAL":
				return EncoderType.NORMAL;
			default:
				Logger.logError("Invalid encoder type");
				return null;
			}
		}
	}

}