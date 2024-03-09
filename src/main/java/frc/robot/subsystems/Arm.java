package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.Dimensionless;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private Slot0Configs slot0Configs;

  private final PositionVoltage positionVoltage;
  public final TalonFX leftMotorFx; // the motor on the left side of the arm
  public final TalonFX rightMotorFx; // the motor on the right side of the arm
  public double speed; 
  public final AnalogPotentiometer potentiometer;
  public DigitalInput MagLimitSwitch;

  public Arm() {
    MagLimitSwitch = new DigitalInput(5);

    potentiometer = new AnalogPotentiometer(0);

    slot0Configs = new Slot0Configs();
      slot0Configs.kP = Constants.ArmConstants.kP;
      slot0Configs.kI = Constants.ArmConstants.kI;
      slot0Configs.kD = Constants.ArmConstants.kD;
      slot0Configs.kA = Constants.ArmConstants.kA;
      slot0Configs.kG = Constants.ArmConstants.kG;
      slot0Configs.kS = Constants.ArmConstants.kS;
      slot0Configs.kV = Constants.ArmConstants.kV;
  
    positionVoltage = new PositionVoltage(0).withSlot(0);
    leftMotorFx = new TalonFX(10, "frc5126");
      leftMotorFx.getConfigurator().apply(slot0Configs);
    rightMotorFx = new TalonFX(11, "frc5126"); // changing to a device ID of 12
      rightMotorFx.setControl(new Follower(leftMotorFx.getDeviceID(), false));
  }
  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Magnetic Limit Switch", MagLimitSwitch.get());
    SmartDashboard.putNumber("arm speed", speed);
    SmartDashboard.putNumber("reverse limit switch", leftMotorFx.getReverseLimit().getValueAsDouble());
    SmartDashboard.putNumber("forward limit switch", leftMotorFx.getForwardLimit().getValueAsDouble());
    SmartDashboard.putNumber("Arm Velocity", leftMotorFx.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Potentiometer Position", potentiometer.get());
    SmartDashboard.putNumber("Arm Position", leftMotorFx.getPosition().getValueAsDouble());
  }
  public void startRot(double position){
    leftMotorFx.setControl(positionVoltage.withPosition(position));
  }
  public void manualRot(double speed){
    this.speed = speed;
    leftMotorFx.set(speed);
  }

  public void endRot(){
    leftMotorFx.set(0);
  }

  public boolean getMagneticLimitSwitch(){
    if (MagLimitSwitch.get()){
      return false;
    }
    else{
      return true;
    }
  }
}
