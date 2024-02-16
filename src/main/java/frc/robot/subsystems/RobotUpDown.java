package frc.robot.subsystems;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotUpDown extends SubsystemBase {
    private Slot0Configs slot0Configs;
    private final PositionVoltage positionVoltage;
    private final TalonFX talonRotOne;
    private final TalonFX talonRotTwo;
    private final Encoder encoder;

  public RobotUpDown() {
    slot0Configs = new Slot0Configs();
      slot0Configs.kP = Constants.ArmConstants.kP;
      slot0Configs.kI = Constants.ArmConstants.kI;
      slot0Configs.kD = Constants.ArmConstants.kD;
      slot0Configs.kA = Constants.ArmConstants.kA;
      slot0Configs.kS = Constants.ArmConstants.kS;
      slot0Configs.kV = Constants.ArmConstants.kV;
      slot0Configs.kG = Constants.ArmConstants.kG;
    positionVoltage = new PositionVoltage(0).withSlot(0);
    talonRotOne = new TalonFX(11, "frc5126");
    talonRotTwo = new TalonFX(12, "frc5126");
        talonRotTwo.setInverted(true);
    talonRotOne.getConfigurator().apply(slot0Configs);
    talonRotTwo.getConfigurator().apply(slot0Configs);
    encoder = new Encoder(0, 0);
  }

  @Override
  public void periodic(){
    SmartDashboard.putNumber("Rev Encoder", encoder.getDistance());
  }
   public void RobotTestOn(double position){
    talonRotOne.setControl(positionVoltage.withPosition(position));
    talonRotTwo.setControl(positionVoltage.withPosition(position));
   }
   public void RobotTestOff() {
    talonRotOne.set(0);
    talonRotTwo.set(0);
   }
   public void ResetEncoder(){
    encoder.reset();
  }
}