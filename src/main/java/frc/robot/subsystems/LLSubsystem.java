package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LLSubsystem extends SubsystemBase {
  NetworkTable IntakeLL = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry IntakeTX;
  NetworkTableEntry IntakeTY;
  NetworkTableEntry IntakeTA; 
  NetworkTableEntry IntakeTID;

  NetworkTable BackLL = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry BackTX;
  NetworkTableEntry BackTY;
  NetworkTableEntry BackTA; 
  NetworkTableEntry BackTID;

  NetworkTable FrontLL = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry FrontTX;
  NetworkTableEntry FrontTY;
  NetworkTableEntry FrontTA; 
  NetworkTableEntry FrontTID;

  double intakeLimelightAngle;

  double IntakeX;
  double IntakeY;
  double IntakeArea;
  long IntakeId;

  double backLimelightAngle;

  double BackX;
  double BackY;
  double BackArea;
  long BackId;

  double FrontLimelightAngle;

  double FrontX;
  double FrontY;
  double FrontArea;
  long FrontId;

  // distance from the center of the Limelight lens to the floor in inches
  double intakeLimelightHeight = 31.0; 
  double backLimelightHeight = 7;
  double FrontLimelightHeight = 31.0;

  double IntakeDistanceFromTarget = 0;
  double BackDistanceFromTarget = 0;
  double FrontDistanceFromTarget = 0;

  public LLSubsystem() {
    SmartDashboard.putNumber("(Back) Limelight Angle", 0);
    SmartDashboard.putNumber("test AprilTag Hight", 0);
    SmartDashboard.putNumber("Limelight Hight", 0);
  }

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

    // Gets Front LimeLight reading data
    FrontTX = BackLL.getEntry("tx");
    FrontTY = BackLL.getEntry("ty");
    FrontTA = BackLL.getEntry("ta");
    FrontTID = BackLL.getEntry("tid");

    // reads Inttake Limelight values periodically
    IntakeX = IntakeTX.getDouble(0.0);
    IntakeY = IntakeTY.getDouble(0.0);
    IntakeArea = IntakeTA.getDouble(0.0);
    IntakeId = IntakeTID.getInteger(0);   

    // reads Back Limelight values periodically
    BackX = BackTX.getDouble(0.0);
    BackY = BackTY.getDouble(0.0);
    SmartDashboard.putNumber("backY", BackY);
    BackArea = BackTA.getDouble(0.0);
    BackId = BackTID.getInteger(0);  
    
    // reads Front Limelight values periodically
    FrontX = BackTX.getDouble(0.0);
    FrontY = BackTY.getDouble(0.0);
    FrontArea = BackTA.getDouble(0.0);
    FrontId = BackTID.getInteger(0);

    // Intake Limelight Values
    SmartDashboard.putNumber("(Intake) Limelight IntakeTX", IntakeX);
    SmartDashboard.putNumber("(Intake) Limelight IntakeTY", IntakeY);
    SmartDashboard.putNumber("(Intake) Limelight Area", IntakeArea);
    SmartDashboard.putNumber("(Intake) Target Distance", IntakeDistanceFromTarget);
    intakeLimelightAngle = SmartDashboard.getNumber("(Intake) Limelight Angle", 9);
    
    // Back Limelight Values
    SmartDashboard.putNumber("(Back) Limelight tx", BackX);
    SmartDashboard.putNumber("(Back) Limelight ty", BackY);
    SmartDashboard.putNumber("(Back) Limelight Area", BackArea);
    SmartDashboard.putNumber("(Back) Target Distance", BackDistanceFromTarget);
    backLimelightAngle = SmartDashboard.getNumber("(Back) Limelight Angle", 0);//85

    // Front Limelight Values
    SmartDashboard.putNumber("(Front) Limelight tx", FrontX);
    SmartDashboard.putNumber("(Front) Limelight ty", FrontY);
    SmartDashboard.putNumber("(Front) Limelight Area", FrontArea);
    SmartDashboard.putNumber("(Front) Target Distance", FrontDistanceFromTarget);
    FrontLimelightAngle = SmartDashboard.getNumber("(Front) Limelight Angle", 9);
  }

  public double getDistance(int LLId) { 
    double targetDegrees;
    double targetHeight = 0;
    if (LLId == 2){
      if (BackId == 1 || BackId == 2 || BackId == 5 || BackId == 6 || BackId == 9 || BackId == 10) {
        targetHeight = 9.375;
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
        targetHeight = 9.375;
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
