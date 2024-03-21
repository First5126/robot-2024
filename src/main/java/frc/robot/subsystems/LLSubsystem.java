package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LLSubsystem extends SubsystemBase {

  NetworkTable BackLL = NetworkTableInstance.getDefault().getTable("limelight-upper");
  NetworkTableEntry BackTX;
  NetworkTableEntry BackTY;
  NetworkTableEntry BackTA; 
  NetworkTableEntry BackTID;

  NetworkTable FrontLL = NetworkTableInstance.getDefault().getTable("limelight-front");
  NetworkTableEntry FrontTX;
  NetworkTableEntry FrontTY;
  NetworkTableEntry FrontTA; 
  NetworkTableEntry FrontTID;

  double FrontX;
  double FrontY;
  double FrontArea;
  long FrontId;

  double backLimelightAngle = 40.5;

  double BackX;
  double BackY;
  double BackArea;
  long BackId;

  double FrontLimelightAngle = 35;

  // distance from the center of the Limelight lens to the floor in inches 
  double backLimelightHeight = 19.5;
  double FrontLimelightHeight = 31.0;  

  double BackDistanceFromTarget = 0;
  double FrontDistanceFromTarget = 0;

  public LLSubsystem() {}

  @Override
  public void periodic() {
    
    // Gets Back LimeLight reading data
    BackTX = BackLL.getEntry("tx");
    BackTY = BackLL.getEntry("ty");
    BackTA = BackLL.getEntry("ta");
    BackTID = BackLL.getEntry("tid");

    // Gets Front LimeLight reading data
    FrontTX = BackLL.getEntry("tx");
    FrontTY = BackLL.getEntry("ty");
    FrontTA = BackLL.getEntry("ta");
    FrontTID = BackLL.getEntry("tid");

    // reads Back Limelight values periodically
    BackX = BackTX.getDouble(0.0);
    BackY = BackTY.getDouble(0.0);
    BackArea = BackTA.getDouble(0.0);
    BackId = BackTID.getInteger(0);  
    
    // reads Front Limelight values periodically
    FrontX = BackTX.getDouble(0.0);
    FrontY = BackTY.getDouble(0.0);
    FrontArea = BackTA.getDouble(0.0);
    FrontId = BackTID.getInteger(0);
    
    // Back Limelight Values
    SmartDashboard.putNumber("(Back) Limelight tx", BackX);
    SmartDashboard.putNumber("(Back) Limelight ty", BackY);
    SmartDashboard.putNumber("(Back) Limelight Area", BackArea);
    SmartDashboard.putNumber("(Back) Target Distance", BackDistanceFromTarget);

    // Front Limelight Values
    SmartDashboard.putNumber("(Front) Limelight tx", FrontX);
    SmartDashboard.putNumber("(Front) Limelight ty", FrontY);
    SmartDashboard.putNumber("(Front) Limelight Area", FrontArea);
    SmartDashboard.putNumber("(Front) Target Distance", FrontDistanceFromTarget);
  }

  public double getDistance(int LLId) { 
    double targetDegrees;
    double targetHeight = 0;
    if (LLId == 2){
      if (BackId == 1 || BackId == 2 || BackId == 5 || BackId == 6 || BackId == 9 || BackId == 10) {
        targetHeight = 48.125;
      }
      else if (BackId == 3 || BackId == 4 || BackId == 7 || BackId == 8) {
        targetHeight = 57.125;
      }
      else if (BackId >= 11) {
        targetHeight = 52;
      }
      targetDegrees = backLimelightAngle + BackY;
      double targetRadians = targetDegrees * (3.14159 / 180.0);

      // calculates distance
      BackDistanceFromTarget = (targetHeight - backLimelightHeight) / Math.tan(targetRadians);
      return BackDistanceFromTarget;
    }
    else if (LLId == 3){
      if (FrontId == 1 || FrontId == 2 || FrontId == 5 || FrontId == 6 || FrontId == 9 || FrontId == 10) {
        targetHeight = 48.125;
      }
      else if (FrontId == 3 || FrontId == 4 || FrontId == 7 || FrontId == 8) {
        targetHeight = 57.125;
      }
      else if (FrontId >= 11) {
        targetHeight = 52;
      }
      targetDegrees = FrontLimelightAngle + BackY;
      double targetRadians = targetDegrees * (3.14159 / 180.0);

      // calculates distance
      FrontDistanceFromTarget = (targetHeight - FrontLimelightHeight) / Math.tan(targetRadians);
      return FrontDistanceFromTarget;
    }
    else{
      return -1;
    }
  }
}
