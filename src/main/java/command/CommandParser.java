package command;

public class CommandParser {
    public Command readCommand(String userInput) {
        if (userInput.equals("bye")) {
            return Command.BYE;
        } else if (userInput.equals("list")) {
            return Command.LIST;
        } else if (userInput.startsWith("mark ")) {
            return Command.MARK;
        } else if (userInput.startsWith("unmark ")) {
            return Command.UNMARK;
        } else if (userInput.startsWith("todo")) {
            return Command.TODO;
        } else if (userInput.startsWith("deadline ")) {
            return Command.DEADLINE;
        } else if (userInput.startsWith("event ")) {
            return Command.EVENT;
        } else if (userInput.startsWith("delete ")) {
            return Command.DELETE;
        } else if (userInput.startsWith("check ")) {
            return Command.CHECK;
        } else {
            return Command.UNKNOWN;
        }
    }
}

