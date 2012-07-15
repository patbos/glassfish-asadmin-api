package se.glassfish.asadmin.api;

import java.util.List;

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
public class ExecutorResult {

    private List<String> output;
    private List<String> errors;
    private int exitCode;

    public ExecutorResult(List<String> output, List<String> errors, int exitCode) {
        this.output = output;
        this.errors = errors;
        this.exitCode = exitCode;
    }

    public List<String> getOutput() {
        return output;
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getExitCode() {
        return exitCode;
    }
}