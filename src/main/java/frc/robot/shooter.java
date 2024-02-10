package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooter extends SubsystemBase {
  private TalonFX talonFXOne;
  private TalonFX talonFXTwo;
  private double outtakeSpeed;
  private double intakeSpeed;
  private double slowIntakeSpeed;
  public DigitalInput stopSensor;
  public DigitalInput slowSensor;

  private CANSparkMax sparkMax;
  /* Creates a new shooter. */
  public shooter() {
    stopSensor = new DigitalInput(0);
    slowSensor = new DigitalInput(1);

    talonFXOne = new TalonFX(5);
    talonFXTwo = new TalonFX(6);

    sparkMax = new CANSparkMax(5, MotorType.kBrushless);
  }

  public void runOuttake() {
    talonFXOne.set(outtakeSpeed);
    talonFXTwo.set(outtakeSpeed);
    talonFXTwo.setInverted(true);
  }
  public void stopOuttake() {
    talonFXOne.set(0);
    talonFXTwo.set(0);
  }
  public void runIntake() {
    sparkMax.set(intakeSpeed);
    //return !stopSensor.get();
  }
  public void slowIntake() {
    sparkMax.set(slowIntakeSpeed);
    //return !slowSensor.get();
  }
  public void stopIntake() {
    sparkMax.set(0);
  }
  public void clearJam() {
    sparkMax.setInverted(true);
    talonFXOne.setInverted(true);
    talonFXTwo.setInverted(true);

    sparkMax.set(0.2);
    talonFXOne.set(0.2);
    talonFXTwo.set(0.2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    outtakeSpeed = SmartDashboard.getNumber("OuttakeSpeed", 0.75);
    intakeSpeed = SmartDashboard.getNumber("IntakeSpeed", 0.6);
    slowIntakeSpeed = SmartDashboard.getNumber("SlowIntakeSpeed", 0.15);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
