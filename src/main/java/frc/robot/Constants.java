package frc.robot;

public final class Constants {

  public static class OperatorConstants {
    public static final int DriverControllerPort = 0;
    public static final int ButtonsControllerPort = 1;
  }

  public static class SwerveConstants {
    public static final double MaxSpeed = 6; // 6 meters per second desired top speed
    public static final double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity
  }
  public static class ArmPID {
    public static final double kP = 4.6;
    public static final double kI = 0.0;
    public static final double kD = 0.25;
    public static final double kA = 0.0;
    public static final double kG = 0.0;
    public static final double kS = 0.25;
    public static final double kV = 0.05;
  }
}
