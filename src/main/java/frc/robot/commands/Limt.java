package frc.robot.commands;
import frc.robot.subsystems.robotArm;
import edu.wpi.first.wpilibj2.command.Command;

public class Limt extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final robotArm m_subsystem;
  private boolean isFinished = false;

  public Limt(robotArm subsystem) {
    m_subsystem = subsystem;
  }

  @Override
  public void initialize() {
    isFinished = false;
  }
  @Override
  public void execute() {
    m_subsystem.enableLimt();
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableLimt();
  }
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}