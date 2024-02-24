package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
public class UnJam extends Command {
  private final Shooter m_subsystem;
  private boolean IsFinished;
  public UnJam(Shooter subsystem) {
    m_subsystem = subsystem;
  }
  @Override
  public void initialize() {
    IsFinished = false;
  }
  @Override
  public void execute() {
    m_subsystem.UnJamOn();
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.UnJamOff();
    IsFinished = true;
  }
  @Override
  public boolean isFinished() {
    return IsFinished;
  }
}