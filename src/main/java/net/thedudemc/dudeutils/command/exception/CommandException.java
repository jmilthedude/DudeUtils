package net.thedudemc.dudeutils.command.exception;

public class CommandException extends Exception {

    private final String message;

    public CommandException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
