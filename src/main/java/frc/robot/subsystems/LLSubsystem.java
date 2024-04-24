package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Swerve.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.math.controller.PIDController;

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
  double backLimelightHeight = 13.75;
  double FrontLimelightHeight = 31.0;  

  double BackDistanceFromTarget = 0;
  double FrontDistanceFromTarget = 0;

  final CommandSwerveDrivetrain drivetrain;
  final SwerveRequest.FieldCentric drive;
  final PIDController LLDriveController = new PIDController(Constants.LLDrivingConstants.P, Constants.LLDrivingConstants.I, Constants.LLDrivingConstants.D);

  public LLSubsystem(CommandSwerveDrivetrain drivetrain, SwerveRequest.FieldCentric drive) {
    this.drivetrain = drivetrain;
    this.drive = drive;
    SmartDashboard.putNumber("LLRotRate", 1);
    SmartDashboard.putNumber("Rotation Deadzone", 1);
  }

  @Override
  public void periodic() {
    

    // Gets Back LimeLight reading data
    BackTX = BackLL.getEntry("tx");
    BackTY = BackLL.getEntry("ty");
    BackTA = BackLL.getEntry("ta");
    BackTID = BackLL.getEntry("tid");
    SmartDashboard.putNumber("TID", BackTID.getDouble(0d));

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
   
    if (Math.signum(BackX) == -1){
      SmartDashboard.putBoolean("Offset-LEFT", true);
      SmartDashboard.putBoolean("Offset-RIGHT", false);
      SmartDashboard.putBoolean("Offset-CENTER", false);
    }
    else if (Math.signum(BackX) == 1){
      SmartDashboard.putBoolean("Offset-RIGHT", true);
      SmartDashboard.putBoolean("Offset-LEFT", false);
      SmartDashboard.putBoolean("Offset-CENTER", false);
    }
    else if (BackId != 0){
      SmartDashboard.putBoolean("Offset-CENTER", true);
      SmartDashboard.putBoolean("Offset-LEFT", false);
      SmartDashboard.putBoolean("Offset-RIGHT", false);
    }
    else{
      SmartDashboard.putBoolean("Offset-CENTER", false);
      SmartDashboard.putBoolean("Offset-LEFT", false);
      SmartDashboard.putBoolean("Offset-RIGHT", false);
    }
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
  public double GetRotation(){
    return Math.round(BackX) * (3.14159 / 180);
  }
  public void LLDrive(){
    if(!LLDriveController.atSetpoint()){
      double setpoint = BackX + drivetrain.getState().Pose.getRotation().getDegrees();
      drivetrain.setControl(drive.withVelocityX(0).withVelocityY(0).withRotationalRate(LLDriveController.calculate(drivetrain.getState().Pose.getRotation().getDegrees() + FrontX, setpoint)));
    }
  }
}
