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
import se.glassfish.asadmin.api.PasswordFile;
import java.io.IOException;

public class CreateSystemPropertiesCommand extends RemoteCommand {

    private String propertyName;
    private String value;

    public CreateSystemPropertiesCommand(GlassFishEnvironment environment, String propertyName, String value) {
        super(environment);
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getCommandName() {
        return "create-system-properties";
    }
	
    public Integer execute() throws CommandException {
        addArg(propertyName + "=" + value);
        return executeCommand().getReturnCode();
    }

}
