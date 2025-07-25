package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Configs {
        // Configurações para os motores
        /** Configuração para o motor de driving. */
        public static final SparkMaxConfig drivingConfig = new SparkMaxConfig(); // No momento está sendo usado apenas
                                                                                 // para testes
        public static final TalonFXConfiguration drivingConfigKraken = new TalonFXConfiguration();

        /** Configuração para o motor de turning. */
        public static final SparkFlexConfig turningConfig = new SparkFlexConfig();

        static {
                // Configuração do motor de driving (movimento)
                drivingConfig
                                .idleMode(IdleMode.kCoast)// Modo de inatividade: Coast (Lembrando que o Coast para o
                                                          // motor suavemente e
                                                          // o brake trava o motor)
                                .smartCurrentLimit(40, 40, 0); // Limite de corrente inteligente: 40A
                drivingConfig
                                .openLoopRampRate(10) // Rampa de aceleração: O motor leva 10 segundos para chegar a sua
                                                      // velocidade
                                                      // máxima
                                .closedLoopRampRate(10); // Rampa de aceleração para o pid: O motor leva 10 segundos
                                                         // para chegar a sua
                                                         // velocidade máxima

                // Configuração do motor de driving Kraken X60 para o TalonFX
                drivingConfigKraken.CurrentLimits.StatorCurrentLimit = 120; // Limite de corrente do estator: 120A
                drivingConfigKraken.CurrentLimits.StatorCurrentLimitEnable = true; // Habilita o limite de corrente do
                                                                                   // estator
                drivingConfigKraken.Feedback.SensorToMechanismRatio = 1; // Relação entre o sensor e o mecanismo: 1
                drivingConfigKraken.MotorOutput.NeutralMode = NeutralModeValue.Coast; // Modo neutro: Coast (motor
                                                                                      // desacelera suavemente)
                drivingConfigKraken.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.25; // Período de rampa de
                                                                                        // aceleração para controle
                                                                                        // fechado: 0.25 segundos
                drivingConfigKraken.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 20; // Período de rampa de aceleração
                                                                                    // para controle aberto: 20 segundos
                drivingConfigKraken.MotorOutput.Inverted = InvertedValue.Clockwise_Positive; // Inversão do motor:
                                                                                             // sentido horário positivo

                // Configuração do motor de turning (viragem)
                turningConfig
                                .idleMode(IdleMode.kBrake) // Modo de inatividade: Coast (Lembrando que o Coast para o
                                                           // motor suavemente
                                                           // e o brake trava o motor)
                                .smartCurrentLimit(40); // Limite de corrente inteligente: 40A
                turningConfig
                                .openLoopRampRate(10) // Rampa de aceleração: O motor leva 10 segundos para chegar a sua
                                                      // velocidade
                                                      // máxima
                                .closedLoopRampRate(0); // Rampa de aceleração para o pid: O motor leva 10 segundos para
                                                        // chegar a sua
                                                        // velocidade máxima
                turningConfig.closedLoop // Configuração dos valores do pid
                                .pid(0.94, 0, 0)
                                .outputRange(-1, 1)
                                .positionWrappingEnabled(true)
                                .positionWrappingInputRange(0, 1);

        }
}
