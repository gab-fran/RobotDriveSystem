package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Configs {
    // Configurações para os motores
    /** Configuração para o motor de driving. */
    public static final SparkMaxConfig drivingConfig = new SparkMaxConfig();

    /** Configuração para o motor de turning. */
    public static final SparkFlexConfig turningConfig = new SparkFlexConfig();

    /** Configuração do motor Kraken X60 */
    //public static final TalonFXConfiguration krakenConfig = new TalonFXConfiguration();

    static {
        // Configuração do motor de driving (movimento)
        drivingConfig
                .idleMode(IdleMode.kCoast)// Modo de inatividade: Coast (Lembrando que o Coast para o motor suavemente e
                                          // o brake trava o motor)
                .smartCurrentLimit(40, 40, 0); // Limite de corrente inteligente: 40A
        drivingConfig
                .openLoopRampRate(10) // Rampa de aceleração: O motor leva 10 segundos para chegar a sua velocidade
                                      // máxima
                .closedLoopRampRate(10); // Rampa de aceleração para o pid: O motor leva 10 segundos para chegar a sua
                                         // velocidade máxima
        // Nescessario pesquisar para configurar corretamente
        /*
         * drivingConfig
         * .encoder
         * .positionConversionFactor(drivingFactor) // Fator de conversão de posição
         * para metros
         * .velocityConversionFactor(drivingFactor / 60.0); // Fator de conversão de
         * velocidade para metros por segundo
         * drivingConfig
         * .closedLoop
         * .velocityFF(drivingVelocityFeedForward) // Feed-forward para velocidade
         * .outputRange(-1, 1) // Faixa de saída do motor
         * .pid(0.04, 0, 0) // PID para controle de velocidade
         * .velocityFF(drivingVelocityFeedForward) // Feed-forward para velocidade
         * .outputRange(-1, 1); // Faixa de saída
         */

        // Configuração do motor de turning (viragem)
        turningConfig
                .idleMode(IdleMode.kBrake) // Modo de inatividade: Coast (Lembrando que o Coast para o motor suavemente
                                           // e o brake trava o motor)
                .smartCurrentLimit(40); // Limite de corrente inteligente: 40A
        turningConfig
                .openLoopRampRate(10) // Rampa de aceleração: O motor leva 10 segundos para chegar a sua velocidade
                                      // máxima
                .closedLoopRampRate(0.25); // Rampa de aceleração para o pid: O motor leva 10 segundos para chegar a sua
                                         // velocidade máxima
        turningConfig.closedLoop // Configuração dos valores do pid
                .pid(0.08, 0, 0)
                .outputRange(-1, 1)
                .positionWrappingEnabled(true)
                .positionWrappingInputRange(0, 1);
        /*
         * turningConfig
         * .absoluteEncoder
         * .inverted(true) // Inverte o encoder, pois o eixo de saída gira na direção
         * oposta do motor
         * .positionConversionFactor(turningFactor) // Conversão para radianos
         * .velocityConversionFactor(turningFactor / 60.0); // Conversão para radianos
         * por segundo
         * turningConfig
         * .closedLoop
         * .feedbackSensor(FeedbackSensor.kAbsoluteEncoder) // Sensor absoluto de
         * feedback
         * .pid(1, 0, 0) // PID para controle de posição
         * .outputRange(-1, 1) // Faixa de saída
         * .positionWrappingEnabled(true) // Habilita o "wrap" (envolvimento de posição)
         * .positionWrappingInputRange(0, turningFactor); // Faixa de entrada para
         * "wrap"
         */

         // Configuração do motor Kraken X60 para driving (Movimento)
    }
}
