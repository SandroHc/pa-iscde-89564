/*
 * Copyright (c) 2019.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Authors:
 *  - Sandro Marques <https://sandrohc.net>
 */

package pa.iscde.minimap.internal.extension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IExtension;

public class Extension {

	public final String id;
	public final String name;
	public final Collection<ExtensionRule> rules;

	public Extension(String id, String name, Collection<ExtensionRule> rules) {
		this.id = id;
		this.name = name;
		this.rules = Collections.unmodifiableCollection(rules);
	}

	public Extension(IExtension ext) {
		this.id = ext.getSimpleIdentifier();
		this.name = ext.getLabel();
		this.rules = Collections.unmodifiableCollection(loadRules(this, ext));
	}

	private static Collection<ExtensionRule> loadRules(Extension self, IExtension ext) {
		return Arrays.stream(ext.getConfigurationElements())
				.map(conf -> new ExtensionRule(self, conf))
				.collect(Collectors.toList());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Extension extension = (Extension) o;

		return Objects.equals(id, extension.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Extension[id='" + id + "',rules=" + rules + ']';
	}
}
