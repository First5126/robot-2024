package frc.robot.subsystems;
import java.beans.Encoder;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotUpDown extends SubsystemBase {

    private PIDController PIDController = new PIDController(0, 0, 0)
    private TalonFX talonRotOne;
    private TalonFX talonRotTwo;
    private double TurnSpeed;
    private double TurnTime;
    private final Encoder Encoder;
    private double MotorValue;

  public RobotUpDown() {
    talonRotOne = new TalonFX(0);
    talonRotTwo = new TalonFX(0);
        talonRotTwo.setInverted(true);
    Encoder = new Encoder
  }

  @Override
  public void periodic(){
    TurnSpeed = SmartDashboard.getNumber("Speed", 0);
    TurnTime = SmartDashboard.getNumber("degrees", 0);
  }

   public void RobotTest(){
    talonRotOne.set(TurnSpeed);
    talonRotTwo.set(TurnSpeed);
   }

 
}
