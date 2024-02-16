package frc.robot.commands;

import frc.robot.subsystems.robotArm;
import edu.wpi.first.wpilibj2.command.Command;

public class Limt extends Command {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final robotArm m_subsystem;
  private boolean isFinished = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */

  public Limt(robotArm subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;.
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.enableLimt();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableLimt();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
