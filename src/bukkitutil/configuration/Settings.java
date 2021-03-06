/*
	BukkitUtil
	Copyright (C) 2011 Klezst

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package bukkitutil.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Contains settings. Automatically validates settings during the constructor.
 * 
 * @author Klezst
 */
public class Settings {
    private Map<Validatable, Object> settings = new HashMap<Validatable, Object>();

    /**
     * Validates and stores configuration settings, invalid settings will not be stored.
     * 
     * @param enums
     *            The settings to validate and store.
     * @param config
     *            The FileConfiguration that contains the settings.
     * 
     * @throws InvalidSettingsException
     *             Thrown, if any settings fail validation.
     * 
     * @author Klezst
     */
    public Settings(FileConfiguration config, Validatable[] enums)
	    throws InvalidSettingsException {
	ArrayList<InvalidSettingException> exceptions = new ArrayList<InvalidSettingException>();

	for (Validatable setting : enums) {
	    String key = setting.getKey();
	    Object value = config.get(key);

	    // Validate
	    if (value == null) {
		exceptions.add(new InvalidSettingException(key,
			"Must specify a value"));
	    } else {
		Class<?> type = setting.getType();
		if (value.getClass().equals(type)) {
		    try {
			this.settings.put(setting, setting.validate(value));
		    } catch (InvalidSettingException e) {
			exceptions.add(e);
		    } catch (InvalidSettingsException e) {
			for (InvalidSettingException ex : e.getExceptions()) {
			    exceptions.add(ex);
			}
		    } catch (ClassCastException e) {
			throw new IllegalArgumentException(
				"ClassCastException in validation(Object) method for "
					+ setting
					+ ". You probably tried casting the argument to a class other than "
					+ type.getSimpleName());
		    }
		} else {
		    exceptions.add(new InvalidSettingException(key,
			    "Must be a " + type.getSimpleName()));
		}
	    }
	}

	// Print invalid keys.
	if (exceptions.size() > 0) {
	    throw new InvalidSettingsException(exceptions);
	}
    }

    /**
     * Returns a value corresponding to setting.
     * 
     * @param setting
     *            The setting to get the value of.
     * @param type
     *            The class of the value to be returned. NOTE: Do not user primitive data types.
     * 
     * @return The value corresponding to setting, if the setting exists and is of the Class type; otherwise null.
     * 
     * @throws IllegalArgumentException
     *             If setting is not of Class type or an InvalidSettingsException was thrown during the constructor.
     * 
     * @author Klezst
     */
    @SuppressWarnings("unchecked")
    public <T> T getSetting(Validatable setting, Class<T> type)
	    throws IllegalArgumentException {
	Object value = this.settings.get(setting);
	if (value != null) {
	    if (value.getClass().equals(type)) {
		return (T) value;
	    }
	    throw new IllegalArgumentException("The setting " + setting
		    + " is not a " + type.getSimpleName() + ".");
	}
	throw new IllegalArgumentException(
		"You ignored an InvalidSettingsException.");
    }
}
