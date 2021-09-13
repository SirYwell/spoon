/*
 * SPDX-License-Identifier: (MIT OR CECILL-C)
 *
 * Copyright (C) 2006-2019 INRIA and contributors
 *
 * Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) of the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 */
package spoon.support.reflect;

import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.cu.SourcePositionHolder;
import spoon.reflect.declaration.ModifierKind;
import spoon.support.sniper.internal.ElementSourceFragment;

import java.io.Serializable;

/** Represents a modifier (eg "public").
 * When a modifier is "implicit", it does not appear in the source code (eg public for interface methods)
 * ModifierKind in kept for sake of full backward-compatibility.
 */
public class CtExtendedModifier implements SourcePositionHolder, Serializable {
	private static final long serialVersionUID = 1L;
	private boolean implicit;
	private ModifierKind kind;
	private SourcePosition position;

	/**
	 * Creates a new extended modifier, marked as not implicit.
	 *
	 * @param kind the kind of this extended modifier.
	 * @deprecated use {@link #explicit(ModifierKind)} instead.
	 */
	@Deprecated
	public CtExtendedModifier(ModifierKind kind) {
		this.kind = kind;
	}

	/**
	 * Creates a new extended modifier.
	 *
	 * @param kind the kind of this extended modifier.
	 * @param implicit whether this modifier is implicit or not.
	 */
	public CtExtendedModifier(ModifierKind kind, boolean implicit) {
		this(kind);
		this.implicit = implicit;
	}

	/**
	 * Creates a new implicit extended modifier.
	 *
	 * @param kind the kind of the created extended modifier.
	 * @return a new modifier, with the given kind and marked as implicit.
	 */
	public static CtExtendedModifier implicit(ModifierKind kind) {
		return new CtExtendedModifier(kind, true);
	}

	/**
	 * Creates a new explicit extended modifier.
	 *
	 * @param kind the kind of the created extended modifier.
	 * @return a new modifier, with the given kind and marked as explicit.
	 */
	public static CtExtendedModifier explicit(ModifierKind kind) {
		return new CtExtendedModifier(kind, false);
	}

	public boolean isImplicit() {
		return implicit;
	}

	public void setImplicit(boolean implicit) {
		this.implicit = implicit;
	}

	public ModifierKind getKind() {
		return kind;
	}

	public void setKind(ModifierKind kind) {
		this.kind = kind;
	}

	@Override
	public SourcePosition getPosition() {
		if (position == null) {
			return SourcePosition.NOPOSITION;
		}
		return position;
	}

	public void setPosition(SourcePosition position) {
		this.position = position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CtExtendedModifier that = (CtExtendedModifier) o;
		return (implicit == that.implicit && kind == that.kind);
	}

	@Override
	public int hashCode() {
		int result = (implicit ? 1 : 0);
		result = 31 * result + (kind != null ? kind.hashCode() : 0);
		return result;
	}

	@Override
	public ElementSourceFragment getOriginalSourceFragment() {
		SourcePosition sp = this.getPosition();
		CompilationUnit compilationUnit = sp.getCompilationUnit();
		if (compilationUnit != null) {
			ElementSourceFragment rootFragment = compilationUnit.getOriginalSourceFragment();
			return rootFragment.getSourceFragmentOf(this, sp.getSourceStart(), sp.getSourceEnd() + 1);
		} else {
			return ElementSourceFragment.NO_SOURCE_FRAGMENT;
		}
	}

	@Override
	public String toString() {
		return "CtExtendedModifier{" +
				"kind=" + kind +
				", implicit=" + implicit +
				'}';
	}
}
