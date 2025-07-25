package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  private final SparkMax drivingMotor;
  private final SparkFlex turningMotor;
  private final TalonFX motorKraken;

  private final RelativeEncoder turningEncoder;

  private final SparkClosedLoopController pid;

  public DriveSubsystem() {
    drivingMotor = new SparkMax(22, MotorType.kBrushless);
    turningMotor = new SparkFlex(18, MotorType.kBrushless);
    motorKraken = new TalonFX(DriveConstants.frontLeftDrivingKraken, "rio");

    turningEncoder = turningMotor.getEncoder();

    pid = turningMotor.getClosedLoopController();

    drivingMotor.configure(Configs.drivingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    turningMotor.configure(Configs.turningConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    inicializeMotor();

    //stopMotors();
    turningEncoder.setPosition(0);
    
  }

  public void inicializeMotor() {
TalonFXConfiguration globalConfig = new TalonFXConfiguration();
    motorKraken.clearStickyFaults();
    // Factory Default
    motorKraken.getConfigurator().apply(new TalonFXConfiguration());      
      
    /* Configure a stator limit of 20 amps */
    CurrentLimitsConfigs currentLimitConfigs = globalConfig.CurrentLimits;
    currentLimitConfigs.StatorCurrentLimit = DriveConstants.kCurrentThreshold;
    currentLimitConfigs.StatorCurrentLimitEnable = DriveConstants.kEnableCurrentLimit; 
    
    /* Gear Ratio Config */
    globalConfig.Feedback.SensorToMechanismRatio = 1;
    globalConfig.MotorOutput.NeutralMode = DriveConstants.kNeutralMode;
    // globalConfig.ClosedLoopGeneral.ContinuousWrap = false;
    
    /* Closed Loop Ramping */
    globalConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = DriveConstants.kClosedLoopRamp;
    globalConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 20;
    /* User can optionally change the configs or leave it alone to perform a factory default */
    globalConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    
    /* Retry config apply up to 5 times, report if failure */
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = motorKraken.getConfigurator().apply(globalConfig);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }
          
    // Enable safety
    // escaladorMotor.setSafetyEnabled(true);
    /* And initialize encoder position to 0 */
    motorKraken.getConfigurator().setPosition(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void drive(double xSpeed, double ySpeed) {

    if (Math.abs(xSpeed) < 0.05 && Math.abs(ySpeed) < 0.05)
      return;

    double angleDeg = Math.toDegrees(Math.atan2(ySpeed, xSpeed));

    if (angleDeg < 0)
      angleDeg += 360;

    double setpoint = (angleDeg / 360.0) * 1 ;

    pid.setReference(setpoint, ControlType.kPosition);
    double speed = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
    speed *= Math.signum(ySpeed);
    
    SmartDashboard.putNumber("SetPoint", setpoint);
    SmartDashboard.putNumber("Encoder", turningEncoder.getPosition());
    
    // if (speed > 0.1) speed = 0.2;
    // if (speed < -0.1) speed = -0.2;
    //motorKraken.set(speed);
    //turningMotor.set(speed);
    
  }

  public void stopMotors() {
    motorKraken.set(0);
    turningMotor.set(0);
  }

  public void resetEncoder(){
    turningEncoder.setPosition(0);
  }
}
