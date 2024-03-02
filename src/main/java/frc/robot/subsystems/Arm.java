package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.units.Dimensionless;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    leftMotorFx = new TalonFX(10);
      leftMotorFx.getConfigurator().apply(slot0Configs);
    rightMotorFx = new TalonFX(11); // changing to a device ID of 12

    rightMotorFx.setControl(new Follower(leftMotorFx.getDeviceID(), true));
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Arm Velocity Left", leftMotorFx.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Arm Position", leftMotorFx.getPosition().getValueAsDouble());
  }
  public boolean startRot(double position){
    leftMotorFx.setControl(positionVoltage.withPosition(position));
    if (rightMotorFx.getFault_ForwardHardLimit().getValueAsDouble() == 0){
      // The lower limit switch
      return true;
    }
    else if (leftMotorFx.getForwardLimit().getValueAsDouble() == 0)
    {
      startRot(63); 
    }
    return false;
  }
  public boolean manualRot(double speed){
    leftMotorFx.set(speed);
    if (rightMotorFx.getForwardLimit().getValueAsDouble() == 0){ 
      // The lower limit switch
      return true;
    }
    else if (leftMotorFx.getReverseLimit().getValueAsDouble() == 0){ 
      // The upper limit switch. When this gets hit, it will set the arm back to 90 degrees using the PID loop function.
      startRot(64);
    }
    return false;
  }
  public void endRot(){
    leftMotorFx.set(0);
  }
}
