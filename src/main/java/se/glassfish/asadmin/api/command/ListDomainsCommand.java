/**
 * Java API for management of GlassFish servers.
 * Copyright (C) 2010 Patrik Boström
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package se.glassfish.asadmin.api.command;

import se.glassfish.asadmin.api.GlassFishEnvironment;
import se.glassfish.asadmin.api.CommandException;
import se.glassfish.asadmin.api.Version;

import java.util.List;
import java.util.ArrayList;

public class ListDomainsCommand extends Command<List<DomainInfo>> {

    public ListDomainsCommand(GlassFishEnvironment environment) {
        super(environment);
    }

    public String getCommandName() {
        return "list-domains";
    }


    public List<DomainInfo> execute() throws CommandException {
        CommandResult commandResult = executeCommand();
        List<DomainInfo> result = new ArrayList<DomainInfo>();
        List<String> domains = commandResult.getOutput();
        for (String domain : domains) {
            if (!domain.startsWith("Command list-domains") && !domain.startsWith("CLI")) {

                if (environment.getVersion() == Version.V3) {
                    String domainName = domain.substring(6, domain.indexOf("Status: ") - 1);
                    String status = domain.substring(domain.indexOf("Status: ") + 8, domain.length());
                    boolean running = status.startsWith("Running");
                    boolean restart = status.equals("Running, restart required to apply configuration changes");
                    result.add(new DomainInfo(domainName, running, restart));
                }
                if (environment.getVersion() == Version.V3_1) {
                    //sunedomain running, restart required to apply configuration changes
                    int index = domain.indexOf(" ");
                    String domainName = domain.substring(0, index);
                    String status = domain.substring(index + 1, domain.length());
                    //TODO add this status
                    boolean starting = status.equals("starting");
                    boolean running = status.equals("running") || status.equals("running, restart required to apply configuration changes");
                    boolean restart = status.equals("running, restart required to apply configuration changes");
                    result.add(new DomainInfo(domainName, running, restart));
                }
                if (environment.getVersion() == Version.V2) {
                    //boolean running = true;
                    int index = domain.indexOf(" ");
                    String domainName = domain.substring(0, index);
                    String status = domain.substring(index + 1, domain.length());
                    //TODO add this status
                    boolean starting = status.equals("starting");
                    boolean running = status.equals("running") || status.equals("requires restart");
                    boolean restart = status.equals("requires restart");
                    result.add(new DomainInfo(domainName, running, restart));
                }
            }


        }

        return result;
    }
}
