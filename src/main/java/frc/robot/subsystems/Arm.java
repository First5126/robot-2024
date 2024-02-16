package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private PIDController pidController = new PIDController(1.2, 0.0, 0.02);
  private final TalonFX leftMotorFx;
  private final TalonFX rightMotorFx;
  private final Encoder encoder;
  private double motorVal;
  private boolean spinning = false;
  public Arm() {
    leftMotorFx = new TalonFX(11, "frc5126");
    rightMotorFx = new TalonFX(10, "frc5126");
      rightMotorFx.setInverted(true);
    encoder = new Encoder(0, 1, true);
  }
  @Override
  public void periodic() {
    motorVal = pidController.calculate(encoder.getDistance(), 90);
    if (spinning == true) {
      leftMotorFx.set(motorVal);
      rightMotorFx.set(motorVal);
    }
  }
  public boolean startRot() {
    if (spinning == false){
      spinning = true;
    }
    if (encoder.getDistance() == 90){
      return true;
    }
    else{
      return false;
    }
  }
  public void endRot(){
    spinning = false;
  }
}
