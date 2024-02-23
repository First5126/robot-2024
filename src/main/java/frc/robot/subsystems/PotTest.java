package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PotTest extends SubsystemBase {
  private AnalogInput pot;
  public PotTest() {
    pot = new AnalogInput(1);
    pot.setAverageBits(16);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Pot voltage", pot.getVoltage());
    SmartDashboard.putNumber("Pot Voltage Average", pot.getAverageVoltage());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
