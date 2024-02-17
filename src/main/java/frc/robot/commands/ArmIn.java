package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.robotArm;

public class ArmIn extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final robotArm m_subsystem;
  private boolean isFinished = false;
  private boolean Slowed;

  public ArmIn(robotArm subsystem) {
    m_subsystem = subsystem;
  }

  @Override
  public void initialize() {
    isFinished = false;
    Slowed = false;
  }
  @Override
  public void execute() {
    if (!m_subsystem.SlowSensor.get()){
      Slowed = true;
    }
    if (!m_subsystem.StopSensor.get()){
      isFinished = true;
    }
    else if (Slowed){
      m_subsystem.enableIntakeslow();
    }
    else{
      m_subsystem.enableIntake();
    }
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableIntake();
  }
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
