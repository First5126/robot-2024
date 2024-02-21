package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private Slot0Configs slot0Configs;

  private final PositionVoltage positionVoltage;
  private final TalonFX leftMotorFx; // the motor on the left side of the arm
  private final TalonFX rightMotorFx; // the motor on the right side of the arm

  //private final Encoder encoder;
  public Arm() {
    slot0Configs = new Slot0Configs();
      slot0Configs.kP = Constants.armConstants.kP;
      slot0Configs.kI = Constants.armConstants.kI;
      slot0Configs.kD = Constants.armConstants.kD;
      slot0Configs.kA = Constants.armConstants.kA;
      slot0Configs.kG = Constants.armConstants.kG;
      slot0Configs.kS = Constants.armConstants.kS;
      slot0Configs.kV = Constants.armConstants.kV;
  
    positionVoltage = new PositionVoltage(0).withSlot(0);
    leftMotorFx = new TalonFX(11, "frc5126");
      leftMotorFx.getConfigurator().apply(slot0Configs);
    rightMotorFx = new TalonFX(10, "frc5126"); // changing to a device ID of 12

    //encoder = new Encoder(0, 1);
    rightMotorFx.setControl(new Follower(leftMotorFx.getDeviceID(), false));
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
  public void ResetEncoder(){
    //encoder.reset();
  }
}
