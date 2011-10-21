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

import se.glassfish.asadmin.api.CommandException;
import se.glassfish.asadmin.api.GlassFishEnvironment;

import java.util.ArrayList;
import java.util.List;

public class ListJdbcConnectionPoolsCommand extends RemoteCommand<List<String>> {

    public ListJdbcConnectionPoolsCommand(GlassFishEnvironment environment) {
        super(environment);
    }

    @Override
    public String getCommandName() {
        return "list-jdbc-connection-pools";
    }


    @Override
    public List<String> execute() throws CommandException {
        CommandResult commandResult = executeCommand();
        List<String> output = commandResult.getOutput();
        List<String> result = new ArrayList<String>();
        for (String name : output) {
            if (!name.trim().equals("")) {
                result.add(name);
            }
        }
        return result;
    }

}
