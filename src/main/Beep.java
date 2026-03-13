package main;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;

public class Beep {

    private static final int SAMPLE_RATE = 48000;

    // Refined ADSR Envelope Parameters
    private static final int ATTACK_TIME_MS = 15;
    private static final int DECAY_TIME_MS = 60;
    private static final int SUSTAIN_LEVEL = 50; // Slightly lower sustain level
    private static final int RELEASE_TIME_MS = 150;

    public static void play(int v1, int v2) {
        // Calculate frequency with enhanced differentiation
        int frequency = calculateFrequency(v1, v2);

        // Generate smoother triangular wave with refined ADSR envelope
        byte[] audioData = generateTriangularWave(frequency, 150); // Increased duration for smoother playback

        // Play the generated audio
        playAudio(audioData);
    }

    private static int calculateFrequency(int v1, int v2) {
        int minFrequency = 120;
        int maxFrequency = 1212;

        // Normalized values for v1 and v2 (scale between 0 and 1)
        double normalizedV1 = v1 / 255.0;
        double normalizedV2 = v2 / 255.0;

        // Weighted combination of magnitude and difference
        double magnitudeComponent = (normalizedV1 + normalizedV2) / 2.0; // Average magnitude
        double differenceComponent = Math.abs(normalizedV1 - normalizedV2); // Absolute difference

        // Apply weights to emphasize the difference more
        double weightedValue = 0.6 * magnitudeComponent + 0.4 * differenceComponent;

        // Map the weighted value to the frequency range (logarithmic scaling for better
        // differentiation)
        return (int) (minFrequency + (maxFrequency - minFrequency) * Math.pow(weightedValue, 2));
    }

    private static byte[] generateTriangularWave(int frequency, int durationMs) {
        int samples = (int) (SAMPLE_RATE * durationMs / 1000.0);
        byte[] audioData = new byte[samples];

        int period = SAMPLE_RATE / frequency;
        int halfPeriod = period / 2;

        // Generate a smoother triangular wave
        for (int i = 0; i < samples; i++) {
            int positionInWave = i % period;
            float fraction = positionInWave < halfPeriod
                    ? (float) positionInWave / halfPeriod // Rising edge
                    : (float) (period - positionInWave) / halfPeriod; // Falling edge
            audioData[i] = (byte) (fraction * 100 - 50); // Scale for smoother sound
        }

        // Apply refined ADSR envelope
        applyADSR(audioData);

        return audioData;
    }

    private static void applyADSR(byte[] audioData) {
        int totalSamples = audioData.length;
        int attackSamples = (int) (SAMPLE_RATE * ATTACK_TIME_MS / 1000.0);
        int decaySamples = (int) (SAMPLE_RATE * DECAY_TIME_MS / 1000.0);
        int releaseSamples = (int) (SAMPLE_RATE * RELEASE_TIME_MS / 1000.0);

        for (int i = 0; i < totalSamples; i++) {
            float multiplier;
            if (i < attackSamples) {
                multiplier = i / (float) attackSamples; // Attack phase
            } else if (i < attackSamples + decaySamples) {
                float decayProgress = (i - attackSamples) / (float) decaySamples;
                multiplier = 1.0f - decayProgress * (1.0f - SUSTAIN_LEVEL / 128.0f); // Decay phase
            } else if (i < totalSamples - releaseSamples) {
                multiplier = SUSTAIN_LEVEL / 128.0f; // Sustain phase
            } else {
                float releaseProgress = (i - (totalSamples - releaseSamples)) / (float) releaseSamples;
                multiplier = SUSTAIN_LEVEL / 128.0f * (1.0f - releaseProgress); // Release phase
            }
            audioData[i] = (byte) (audioData[i] * multiplier);
        }
    }

    private static void playAudio(byte[] audioData) {
        try {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            AudioInputStream ais = new AudioInputStream(bais, format, audioData.length);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error playing audio: " + e.getMessage());
        }
    }
}
