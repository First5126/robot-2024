package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LLBack extends SubsystemBase {
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tid = table.getEntry("tid");
  double limelightAngle;
  
  // reads values periodically
  double x = tx.getDouble(0.0);
  double y = ty.getDouble(0.0);
  double area = ta.getDouble(0.0);
  long id = tid.getInteger(0);

  // how many degrees back the Limelight is rotated from being perfectly horizontal
  double targetDegrees;
  double targetRadians;

  // distance from the center of the Limelight lens to the floor in inches
  double limelightHeight = 31.0; 

  // distance from the target to the floor in inches
  double targetHeight;

  double DistanceFromTarget = 0;

  public LLBack() {}

  @Override
  public void periodic() {
    //posts to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putNumber("TargetDistance", DistanceFromTarget);
    limelightAngle = SmartDashboard.getNumber("LimelightAngle", 9);
  }

  public void getDistance() {  
    if (id == 1 || id == 2 || id == 5 || id == 6 || id == 9 || id == 10) {
      targetHeight = 9.375;
    }
    else if (id == 3 || id == 4 || id == 7 || id == 8) {
      targetHeight = 57.125;
    }
    else if (id >= 11) {
      targetHeight = 52;
    }
    targetDegrees = limelightAngle + y;
    targetRadians = targetDegrees * (3.14159 / 180.0);

    // calculates distance
    DistanceFromTarget = (targetHeight - limelightHeight) / Math.tan(targetRadians);
  }
}