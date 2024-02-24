package frc.robot.commands;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
public class Shoot extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private boolean IsFinished;

  public Shoot(Shooter subsystem) {
    m_subsystem = subsystem;
  }
  @Override
  public void initialize() {
    IsFinished = false;
  }
  @Override
  public void execute() {
    m_subsystem.OuttakeOn();
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.OuttakeOff();
    IsFinished = true;
  }
  @Override
  public boolean isFinished() {
    return IsFinished;
  }
}