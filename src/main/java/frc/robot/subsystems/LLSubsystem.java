package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LLSubsystem extends SubsystemBase {
  NetworkTable BackLL = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry BackTX;
  NetworkTableEntry BackTY;
  NetworkTableEntry BackTA; 
  NetworkTableEntry BackTID;

  NetworkTable IntakeLL = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry IntakeTX;
  NetworkTableEntry IntakeTY;
  NetworkTableEntry IntakeTA; 
  NetworkTableEntry IntakeTID;

  double backLimelightAngle;

  double BackX;
  double BackY;
  double BackArea;
  long BackId;

  double IntakeLimelightAngle = 35;

  double IntakeX;
  double IntakeY;
  double IntakeArea;
  long IntakeId;

  // distance from the center of the Limelight lens to the floor in inches
  double backLimelightHeight = 19.5;
  double IntakeLimelightHeight = 31.0;

  double BackDistanceFromTarget = 0;
  double IntakeDistanceFromTarget = 0;

  public LLSubsystem() {}

  @Override
  public void periodic() {
    backLimelightHeight = SmartDashboard.getNumber("Limelight Hight", 0);
    // Gets Intake LimeLight reading data
    IntakeTX = IntakeLL.getEntry("tx");
    IntakeTY = IntakeLL.getEntry("ty");
    IntakeTA = IntakeLL.getEntry("ta");
    IntakeTID = IntakeLL.getEntry("tid");
    
    // Gets Back LimeLight reading data
    BackTX = BackLL.getEntry("tx");
    BackTY = BackLL.getEntry("ty");
    BackTA = BackLL.getEntry("ta");
    BackTID = BackLL.getEntry("tid");

    // Gets Intake LimeLight reading data
    IntakeTX = BackLL.getEntry("tx");
    IntakeTY = BackLL.getEntry("ty");
    IntakeTA = BackLL.getEntry("ta");
    IntakeTID = BackLL.getEntry("tid");
  
    // reads Back Limelight values periodically
    BackX = BackTX.getDouble(0.0);
    BackY = BackTY.getDouble(0.0);
    BackArea = BackTA.getDouble(0.0);
    BackId = BackTID.getInteger(0);  
    
    // reads Intake Limelight values periodically
    IntakeX = BackTX.getDouble(0.0);
    IntakeY = BackTY.getDouble(0.0);
    IntakeArea = BackTA.getDouble(0.0);
    IntakeId = BackTID.getInteger(0);

    // Back Limelight Values
    SmartDashboard.putNumber("(Back) Limelight tx", BackX);
    SmartDashboard.putNumber("(Back) Limelight ty", BackY);
    SmartDashboard.putNumber("(Back) Limelight Area", BackArea);
    SmartDashboard.putNumber("(Back) Target Distance", BackDistanceFromTarget);

    // Intake Limelight Values
    SmartDashboard.putNumber("(Intake) Limelight tx", IntakeX);
    SmartDashboard.putNumber("(Intake) Limelight ty", IntakeY);
    SmartDashboard.putNumber("(Intake) Limelight Area", IntakeArea);
    SmartDashboard.putNumber("(Intake) Target Distance", IntakeDistanceFromTarget);
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
      if (IntakeId == 1 || IntakeId == 2 || IntakeId == 5 || IntakeId == 6 || IntakeId == 9 || IntakeId == 10) {
        targetHeight = 48.125;
      }
      else if (IntakeId == 3 || IntakeId == 4 || IntakeId == 7 || IntakeId == 8) {
        targetHeight = 57.125;
      }
      else if (IntakeId >= 11) {
        targetHeight = 52;
      }
      targetDegrees = IntakeLimelightAngle + BackY;
      double targetRadians = targetDegrees * (3.14159 / 180.0);

      // calculates distance
      IntakeDistanceFromTarget = (targetHeight - IntakeLimelightHeight) / Math.tan(targetRadians);
      return IntakeDistanceFromTarget;
    }
    else{
      return -1;
    }
  }
}
