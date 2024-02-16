package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
public class RobotUpDown extends SubsystemBase {

    private PIDController PIDController = new PIDController(0.1, 0.1, 0.1);
    private TalonFX talonRotOne;
    private TalonFX talonRotTwo;
    private final Encoder encoder;
    private double MotorValue;

  public RobotUpDown() {
    talonRotOne = new TalonFX(0);
    talonRotTwo = new TalonFX(0);
        talonRotTwo.setInverted(true);
    encoder = new Encoder(0, 0, true);
  }

  @Override
  public void periodic(){
    MotorValue = PIDController.calculate(encoder.getDistance(), 1);
  }

   public void RobotTestOn(){
    talonRotOne.set(MotorValue);
    talonRotTwo.set(MotorValue);
   }

   public void RobotTestOff() {
    talonRotOne.set(0);
    talonRotTwo.set(0);
   }
 
}
