package frc.robot.commands;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;
public class RotateArm extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Arm m_subsystem;
  private final double position;
  private boolean IsFinished;

  public RotateArm(Arm subsystem, double position) {
    this.position = position;
    m_subsystem = subsystem;
  }
  @Override
  public void initialize() {
    m_subsystem.Reset();
    IsFinished = false;
  }
  @Override
  public void execute() {
    m_subsystem.ArmMoveOn(position);
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.ArmMoveOff();
    IsFinished = true;
  }
  @Override
  public boolean isFinished() {
    return IsFinished;
  }
}