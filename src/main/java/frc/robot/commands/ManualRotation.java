
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class ManualRotation extends Command {
  private final Arm m_subsystem;
  private final double speed;

  private boolean isFinished;

  public ManualRotation(Arm subsystem, double speed) {
    this.speed = speed;
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
    if (m_subsystem.MagLimitSwitch.get() == false){
      isFinished = true;
    }
    else if (m_subsystem.leftMotorFx.getReverseLimit().getValueAsDouble() == 0 && Math.signum(speed) == 1){
      m_subsystem.manualRot(speed);
    }
    else{
      m_subsystem.manualRot(speed);
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
