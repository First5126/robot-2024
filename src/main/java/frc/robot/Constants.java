package frc.robot;

import com.ctre.phoenix6.signals.GravityTypeValue;

public final class Constants {

  public static class OperatorConstants {
    public static final int DriverControllerPort = 0;
    public static final int ButtonsControllerPort = 1;
  }

  public static class SwerveConstants {
    public static final double MaxSpeed = 6; // 6 meters per second desired top speed
    public static final double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity
  }

  public static class ShooterConstants {
    public static final double kP = 0.11; //Output per unit of error in velocity (output/rps)
    public static final double kI = 0; //Output per unit of integrated error in velocity (output/rotation)
    public static final double kD = 0; //Output per unit of error derivative in velocity (output/(rps/s))
    public static final double kA = 0; //Target Acceleration
    public static final double kG = 0; //Gravity Feedforward gain
    public static final double kS = 0.05; //to over come static friction (output)
    public static final double kV = 0.12; //output per unit of requested velocity (output/rps)
  }

  public static class ArmConstants {
    public static final GravityTypeValue gType = GravityTypeValue.Arm_Cosine;
    public static final double kP = 2.6545; //Older : 3.7651 - Output per unit of error in velocity (output/rps)
    public static final double kI = 0.0; //Output per unit of integrated error in velocity (output/rotation)
    public static final double kD = 0.025089; //Older : 0.034524 - Output per unit of error derivative in velocity (output/(rps/s))
    public static final double kA = 0.0034147; // Older : 0.00032207 - Target Acceleration
    public static final double kG = 0.24575; //Older : 0.5066 - Gravity Feedforward gain
    public static final double kS = 0.18075; //Older : 0.079362 - to over come static friction (output)
    public static final double kV = 0.11551; //Older : 0.12209 - output per unit of requested velocity (output/rps)
  }
  public static class LLDrivingConstants {
    public static final double P = 0.1;
    public static final double I = 0;
    public static final double D = 0;
    public static final double TurnTolerance = 0.05;
    public static final double TurnRateTolerancePerS = 0.25;
  }
}
