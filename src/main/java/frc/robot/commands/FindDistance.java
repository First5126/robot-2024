package frc.robot.commands;

import frc.robot.subsystems.LLIntake;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class FindDistance extends Command {
  private final LLIntake m_LLSubsystem;
  private final boolean IntakeLL;

  private boolean isFinished;
  public FindDistance(LLIntake LL_subsystem, boolean Front_LL) {
    m_LLSubsystem = LL_subsystem;
    IntakeLL = Front_LL;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
