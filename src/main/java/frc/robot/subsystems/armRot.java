package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class armRot extends SubsystemBase {
  private PIDController pidController = new PIDController(0.1, 0.1, 0.1);
  private final TalonFX leftMotorFx;
  private final TalonFX rightMotorFx;
  private final Encoder encoder;
  private double motorVal;
  private boolean spinning = false;
  public armRot() {
    leftMotorFx = new TalonFX(0);
    rightMotorFx = new TalonFX(1);
      rightMotorFx.setInverted(true);
    encoder = new Encoder(0, 1, true);
  }
  @Override
  public void periodic() {
    motorVal = pidController.calculate(encoder.getDistance(), 1);
    if (spinning == true) {
      leftMotorFx.set(motorVal);
      rightMotorFx.set(motorVal);
    }
  }
  public void startRot() {
    spinning = true;
  }
}
