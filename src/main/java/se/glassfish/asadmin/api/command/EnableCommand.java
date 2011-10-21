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

public class EnableCommand extends RemoteCommand<Integer> {

    private String name;
    private String target;


    public EnableCommand(GlassFishEnvironment environment, String name, String target) {
        super(environment);
        this.name = name;
        this.target = target;
    }


    public String getCommandName() {
        return "enable";
    }

    public Integer execute() throws CommandException {
        if (target != null) {
            addParam("target", target);
        }

        addArg(name);
        return executeCommand().getReturnCode();
    }
}
