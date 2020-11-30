package de.beachboys;

import org.junit.jupiter.api.Assertions;

import java.util.List;

public class IOHelperForTests extends IOHelper {
    private final List<String> inputs;
    private final List<Object> outputs;
    private int inputIndex = 0;
    private int outputIndex = 0;

    public IOHelperForTests(List<String> inputs, List<Object> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public String getInput(String textToDisplay) {
        if (inputs == null || inputs.size() <= inputIndex) {
            Assertions.fail("Input needed but not provided for index " + inputIndex);
        }
        inputIndex++;
        return inputs.get(inputIndex - 1);
    }

    @Override
    public void logDebug(Object debugText) {
        super.logDebug(debugText);
    }

    @Override
    public void logInfo(Object infoText) {
        super.logInfo(infoText);
        if (outputs == null || outputs.size() <= outputIndex) {
            Assertions.fail("Output provided, but not tested for index " + outputIndex);
        }
        outputIndex++;
        Assertions.assertEquals(outputs.get(outputIndex - 1).toString(), infoText.toString());
    }
}
