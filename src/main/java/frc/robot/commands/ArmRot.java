package frc.robot.commands;
import frc.robot.subsystems.RobotUpDown;
import edu.wpi.first.wpilibj2.command.Command;

public class ArmRot extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final RobotUpDown m_subsystem;
  private final double position;
  private boolean isFinished;

  public ArmRot(RobotUpDown subsystem, double position) {
    this.position = position;
    m_subsystem = subsystem;
  }

  @Override
  public void initialize() {
    m_subsystem.ResetEncoder();
    isFinished = false;
  }
  @Override
  public void execute() {
    m_subsystem.RobotTestOn(position);
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.RobotTestOff();
    isFinished = true;
  }
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}