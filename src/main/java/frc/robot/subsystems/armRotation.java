package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;

public class armRotation extends SubsystemBase {
  private PIDController pid = new PIDController(0.1, 0.1, 0.1);
  private TalonFX falconOne;
  private TalonFX falconTwo;
  private Encoder encoder;
  private double motorValue;
  private boolean isSpinning = false;
  public armRotation() {
    falconOne = new TalonFX(0);
    falconTwo = new TalonFX(1);
      falconTwo.setInverted(true);
    encoder = new Encoder(0, 1, true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    motorValue = pid.calculate(encoder.getDistance(), 1);
    if (isSpinning){
      falconOne.set(motorValue);
      falconTwo.set(motorValue);
    }
  }

  public void startRotation() {
    isSpinning = true;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}