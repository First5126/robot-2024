package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private Slot0Configs slot0Configs;

  private final PositionVoltage positionVoltage;
  private final TalonFX leftMotorFx; // the motor on the left side of the arm
  private final TalonFX rightMotorFx; // the motor on the right side of the arm

  public Arm() {
    slot0Configs = new Slot0Configs();
      slot0Configs.kP = Constants.ArmPID.kP;
      slot0Configs.kI = Constants.ArmPID.kI;
      slot0Configs.kD = Constants.ArmPID.kD;
      slot0Configs.kA = Constants.ArmPID.kA;
      slot0Configs.kG = Constants.ArmPID.kG;
      slot0Configs.kS = Constants.ArmPID.kS;
      slot0Configs.kV = Constants.ArmPID.kV;
  
    positionVoltage = new PositionVoltage(0).withSlot(0);
    leftMotorFx = new TalonFX(11);
      leftMotorFx.getConfigurator().apply(slot0Configs);
    rightMotorFx = new TalonFX(10); // changing to a device ID of 12

    rightMotorFx.setControl(new Follower(leftMotorFx.getDeviceID(), true));
  }
  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Rev Encoder", encoder.getDistance());
  }
  public void startRot(double position){
    leftMotorFx.setControl(positionVoltage.withPosition(position));
  }
  public void manualRot(double speed){
    leftMotorFx.set(speed);
  }
  public void endRot(){
    leftMotorFx.set(0);
  }
}
