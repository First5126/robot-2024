package frc.robot.commands;

import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class RotateArm extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Arm m_subsystem;
  private final double position;

  private boolean isFinished;

  public RotateArm(Arm subsystem, double position) {
    this.position = position;
    m_subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if ((m_subsystem.getMagneticLimitSwitch() && Math.signum(position) == -1) || (m_subsystem.leftMotorFx.getReverseLimit().getValueAsDouble() == 0 && Math.signum(position) == -1)){
      isFinished = true;
    }
    else if (m_subsystem.leftMotorFx.getForwardLimit().getValueAsDouble() == 0 && Math.signum(position) == 1){
      isFinished = true;
    }
    else{
      m_subsystem.startRot(position);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isFinished = true;
    m_subsystem.endRot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
