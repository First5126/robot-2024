package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.CoastOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Arm extends SubsystemBase {
  private Slot0Configs slot0Configs;

  private final PositionVoltage positionVoltage;
  public final TalonFX leftMotorFx; // the motor on the left side of the arm
  public final TalonFX rightMotorFx; // the motor on the right side of the arm
  public double speed; 
  public final AnalogPotentiometer potentiometer;
  public DigitalInput ReverseMagLimitSwitch;
  public DigitalInput ForwardMagLimitSwitch;
  private Encoder revThroughBore;
  private double ClimberSpeed;

  public Arm() {
    ReverseMagLimitSwitch = new DigitalInput(5);
    ForwardMagLimitSwitch = new DigitalInput(6);

    potentiometer = new AnalogPotentiometer(0);

    revThroughBore = new Encoder(1, 2);


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

    SmartDashboard.putNumber("Climber Speed", 0);
  }
  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Magnetic Limit Switch", ReverseMagLimitSwitch.get());
    SmartDashboard.putNumber("arm speed", speed);
    SmartDashboard.putNumber("reverse limit switch", leftMotorFx.getReverseLimit().getValueAsDouble());
    SmartDashboard.putNumber("forward limit switch", leftMotorFx.getForwardLimit().getValueAsDouble());
    SmartDashboard.putNumber("Arm Velocity", leftMotorFx.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Potentiometer Position", potentiometer.get());
    SmartDashboard.putNumber("Arm Position", leftMotorFx.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Arm Encoder", revThroughBore.getDistance());
    SmartDashboard.putBoolean("Arm Swtich", RobotContainer.getSwitchPosition());
    ClimberSpeed = SmartDashboard.getNumber("Climber Speed", .1);


    /*if(leftMotorFx.getReverseLimit().getValueAsDouble() == 0){
      revThroughBore.reset();
    }*/
  }
  public void startRot(double position){
    leftMotorFx.setControl(positionVoltage.withPosition(position));
  }

  public double getPosition(){
    return leftMotorFx.getPosition().getValueAsDouble();
  }

  public boolean isAtPosition(double position){
    if (position - Math.abs(leftMotorFx.getPosition().getValueAsDouble()) <= 4){
      return true;
    }
    else{
      return false;
    }
  }
  public void manualRot(double speed){
    this.speed = speed;
    leftMotorFx.set(speed);
  }

  public void endRot(){
    leftMotorFx.set(0);
  }

  public boolean getReverseMagneticLimit(){
    if (ReverseMagLimitSwitch.get()){
      return false;
    }
    else{
      return true;
    }
  }

  public void setArmCoast(){
    leftMotorFx.setNeutralMode(NeutralModeValue.Coast);
    rightMotorFx.setNeutralMode(NeutralModeValue.Coast);
    //leftMotorFx.setSafetyEnabled(false);
    //rightMotorFx.setSafetyEnabled(false);
    //leftMotorFx.feed();
    //rightMotorFx.feed();
    //leftMotorFx.setControl(new CoastOut());
    //rightMotorFx.setControl(new CoastOut());
  }

  public void setArmBrake(){
    leftMotorFx.setNeutralMode(NeutralModeValue.Brake);
    rightMotorFx.setNeutralMode(NeutralModeValue.Brake);
    //leftMotorFx.setSafetyEnabled(false);
    //rightMotorFx.setSafetyEnabled(false);
    //leftMotorFx.feed();
    //rightMotorFx.feed();
  }

    public boolean getForwardMagneticLimit(){
    if (ForwardMagLimitSwitch.get()){
      return false;
    }
    else{
      return true;
    }
  }

  public void setClimberSpeed(){
    leftMotorFx.set(ClimberSpeed);
  }
}
