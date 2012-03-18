package se.glassfish.asadmin.api.command;

import se.glassfish.asadmin.api.CommandException;
import se.glassfish.asadmin.api.GlassFishEnvironment;

public class EnableSecureAdminCommand extends RemoteCommand<Integer> {

/*    --adminalias
    The alias that refers to the SSL/TLS certificate on the
    DAS.  This  alias is used by the DAS to identify itself
    to instances. The default value is s1as.

    --instancealias
*/
    
    private String adminAlias;
    private String instanceAlias;

    public EnableSecureAdminCommand(GlassFishEnvironment environment, String adminAlias, String instanceAlias) {
        super(environment);
        this.adminAlias = adminAlias;
        this.instanceAlias = instanceAlias;
    }

    public EnableSecureAdminCommand(GlassFishEnvironment environment) {
        super(environment);
    }

    @Override
    public Integer execute() throws CommandException {
        if (adminAlias != null) {
            addParam("adminalias", adminAlias);
        }
        if (instanceAlias != null) {
            addParam("instancealias", instanceAlias);
        }
        return executeCommand().getReturnCode();
    }

    @Override
    public String getCommandName() {
        return "enable-secure-admin";
    }
}
