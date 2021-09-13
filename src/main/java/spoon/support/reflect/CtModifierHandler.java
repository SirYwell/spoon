/*
 * SPDX-License-Identifier: (MIT OR CECILL-C)
 *
 * Copyright (C) 2006-2019 INRIA and contributors
 *
 * Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) of the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 */
package spoon.support.reflect;

import spoon.SpoonException;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.support.util.internal.collection.CompositeValueSetMap;
import spoon.support.util.internal.collection.ValueSetMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static spoon.reflect.path.CtRole.MODIFIER;

public class CtModifierHandler implements Serializable {
	private static final long serialVersionUID = 2L;

	private ValueSetMap<ModifierKind, CtExtendedModifier> modifierMap = new CompositeValueSetMap<>(
			CtExtendedModifier.class,
			CtExtendedModifier::getKind,
			new EnumMap<>(ModifierKind.class));

	private CtElement element;

	public CtModifierHandler(CtElement element) {
		this.element = element;
	}

	public Factory getFactory() {
		return element.getFactory();
	}

	public Set<CtExtendedModifier> getExtendedModifiers() {
		return Collections.unmodifiableSet(modifierMap.values());
	}

	public CtModifierHandler setExtendedModifiers(Set<CtExtendedModifier> extendedModifiers) {
		if (extendedModifiers != null && !extendedModifiers.isEmpty()) {
			getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(element, MODIFIER, this.modifierMap.values(), new HashSet<>(this.modifierMap.values()));
			this.modifierMap.clear();
			for (CtExtendedModifier extendedModifier : extendedModifiers) {
				getFactory().getEnvironment().getModelChangeListener().onSetAdd(element, MODIFIER, this.modifierMap.values(), extendedModifier.getKind());
				this.modifierMap.add(extendedModifier);
			}
		}
		return this;
	}

	public Set<ModifierKind> getModifiers() {
		return Collections.unmodifiableSet(modifierMap.keySet());
	}

	public boolean hasModifier(ModifierKind modifier) {
		return getModifiers().contains(modifier);
	}

	public CtModifierHandler setModifiers(Set<ModifierKind> modifiers) {
		if (modifiers == null) {
			modifiers = Collections.emptySet();
		}
		getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(element, MODIFIER, this.modifierMap.values(), new HashSet<>(this.modifierMap.values()));
		this.modifierMap.clear();
		for (ModifierKind modifier : modifiers) {
			addModifier(modifier);
		}
		return this;
	}

	public CtModifierHandler addModifier(ModifierKind modifier) {
		return addModifier(CtExtendedModifier.explicit(modifier));
	}

	public CtModifierHandler addModifier(CtExtendedModifier modifier) {
		// TODO I'm tricking the modelchangelistener here
		getFactory().getEnvironment().getModelChangeListener().onSetAdd(element, MODIFIER, this.modifierMap.values(), modifier.getKind());
		modifierMap.add(modifier);
		return this;
	}

	public CtModifierHandler removeModifier(ModifierKind modifier) {
		getFactory().getEnvironment().getModelChangeListener().onSetDelete(element, MODIFIER, modifierMap.values(), modifier);
		// we want to remove implicit OR explicit modifier
		modifierMap.remove(modifier);
		return this;
	}

	// TODO maybe this should only remove the exact extended modifier?
	public CtModifierHandler removeModifier(CtExtendedModifier modifier) {
		return removeModifier(modifier.getKind());
	}

	public CtModifierHandler setVisibility(ModifierKind visibility) {
		if (visibility != ModifierKind.PUBLIC && visibility != ModifierKind.PROTECTED && visibility != ModifierKind.PRIVATE) {
			throw new SpoonException("setVisibility could only be called with a private, public or protected argument value. Given argument: " + visibility);
		}
		if (hasModifier(visibility)) {
			return this;
		}
		if (isPublic()) {
			removeModifier(ModifierKind.PUBLIC);
		}
		if (isProtected()) {
			removeModifier(ModifierKind.PROTECTED);
		}
		if (isPrivate()) {
			removeModifier(ModifierKind.PRIVATE);
		}
		addModifier(visibility);
		return this;
	}

	public ModifierKind getVisibility() {
		if (isPublic()) {
			return ModifierKind.PUBLIC;
		}
		if (isProtected()) {
			return ModifierKind.PROTECTED;
		}
		if (isPrivate()) {
			return ModifierKind.PRIVATE;
		}
		return null;
	}

	public boolean isPublic() {
		return getModifiers().contains(ModifierKind.PUBLIC);
	}

	public boolean isProtected() {
		return getModifiers().contains(ModifierKind.PROTECTED);
	}

	public boolean isPrivate() {
		return getModifiers().contains(ModifierKind.PRIVATE);
	}

	public boolean isAbstract() {
		return getModifiers().contains(ModifierKind.ABSTRACT);
	}

	public boolean isStatic() {
		return getModifiers().contains(ModifierKind.STATIC);
	}

	public boolean isFinal() {
		return getModifiers().contains(ModifierKind.FINAL);
	}

	public boolean isTransient() {
		return getModifiers().contains(ModifierKind.TRANSIENT);
	}

	public boolean isVolatile() {
		return getModifiers().contains(ModifierKind.VOLATILE);
	}

	public boolean isSynchronized() {
		return getModifiers().contains(ModifierKind.SYNCHRONIZED);
	}

	public boolean isNative() {
		return getModifiers().contains(ModifierKind.NATIVE);
	}

	public boolean isStrictfp() {
		return getModifiers().contains(ModifierKind.STRICTFP);
	}

	@Override
	public int hashCode() {
		return getModifiers().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CtModifierHandler)) {
			return false;
		}
		final CtModifierHandler other = (CtModifierHandler) obj;
		if (getVisibility() == null) {
			if (other.getVisibility() != null) {
				return false;
			}
		} else if (other.getVisibility() == null) {
			return false;
		} else if (!getVisibility().equals(other.getVisibility())) {
			return false;
		}
		if (getModifiers().size() != other.getModifiers().size()) {
			return false;
		}
		return getModifiers().containsAll(other.getModifiers());
	}

	// Custom serialisation is needed because serializing the map is even worse

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		element = (CtElement) aInputStream.readObject();
		modifierMap = new CompositeValueSetMap<>(
				CtExtendedModifier.class,
				CtExtendedModifier::getKind, new EnumMap<>(ModifierKind.class)
		);
		modifierMap.values().addAll((Collection<? extends CtExtendedModifier>) aInputStream.readObject());
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		aOutputStream.writeObject(element);
		aOutputStream.writeObject(new HashSet<>(modifierMap.values()));
	}
}
