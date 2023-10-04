package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Utils.validateNumArgs("init", args,1);
                Repository.initRepo();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                Utils.validateNumArgs("add", args, 2);
                Repository.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                Utils.validateNumArgs("commit", args, 2);
                Repository.commit(args[1]);
                break;
            case "rm":
                Utils.validateNumArgs("rm", args, 2);
                Repository.remove(args[1]);
                break;
            case "log":
                Utils.validateNumArgs("log", args, 1);
                Log.printLog();
                break;
            case "global-log":
                Utils.validateNumArgs("global-log", args, 1);
                Log.printGlobalLog();
                break;
            case "find":
                Utils.validateNumArgs("find", args, 2);
                Log.findMessage(args[1]);
                break;
            case "status":
                Utils.validateNumArgs("status", args, 1);
                Status.printStatus();
                break;
            case "checkout":
                Checkout.CheckOperands(args);
                break;
        }
    }
}
