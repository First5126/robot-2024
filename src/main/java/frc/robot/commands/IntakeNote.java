package frc.robot.commands;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
public class IntakeNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private boolean IsFinished;
  private boolean Slowed;

  public IntakeNote(Shooter subsystem) {
    m_subsystem = subsystem;
  }
  @Override
  public void initialize() {
    IsFinished = false;
    Slowed = false;
  }
  @Override
  public void execute() {
    if (!m_subsystem.SlowSensor.get()){
      Slowed = true;
    }
    if (!m_subsystem.StopSensor.get()){
      IsFinished = true;
    }
    else if (Slowed){
      m_subsystem.SlowIntake();
    }
    else{
      m_subsystem.IntakeOn();
    }
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.IntakeOff();
  }
  @Override
  public boolean isFinished() {
    return IsFinished;
  }
}