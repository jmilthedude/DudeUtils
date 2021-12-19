package net.thedudemc.dudeutils.command.exception;

public class InvalidUsageCommandException extends CommandException {
    public InvalidUsageCommandException(String command) {
        super("Invalid Usage. See \"/" + command + " help\" for how to use this command.");
    }
}
