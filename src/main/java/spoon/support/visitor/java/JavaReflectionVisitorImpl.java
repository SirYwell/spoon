/*
 * SPDX-License-Identifier: (MIT OR CECILL-C)
 *
 * Copyright (C) 2006-2019 INRIA and contributors
 *
 * Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) of the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 */
package spoon.support.visitor.java;

import spoon.SpoonException;
import spoon.reflect.path.CtRole;
import spoon.support.visitor.java.reflect.RtMethod;
import spoon.support.visitor.java.reflect.RtParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class JavaReflectionVisitorImpl implements JavaReflectionVisitor {
	private static Class<?> recordClass = getRecordClass();

	@Override
	public void visitPackage(Package aPackage) {
		visitAllSafe(aPackage.getDeclaredAnnotations(), this::visitAnnotation);
	}

	@Override
	public <T> void visitClass(Class<T> clazz) {
		if (clazz.getPackage() != null) {
			visitPackage(clazz.getPackage());
		}
		visitAllSafe(clazz.getTypeParameters(), this::visitTypeParameter);
		try {
			if (clazz.getGenericSuperclass() != null && clazz.getGenericSuperclass() != Object.class) {
				visitTypeReference(CtRole.SUPER_TYPE, clazz.getGenericSuperclass());
			}
		} catch (NoClassDefFoundError ignore) {
			// partial classpath
		}
		visitAllSafe(clazz.getGenericInterfaces(), anInterface -> visitTypeReference(CtRole.INTERFACE, anInterface));
		visitAllSafe(clazz.getDeclaredAnnotations(), this::visitAnnotation);
		visitAllSafe(clazz.getDeclaredConstructors(), filterSynthetic(this::visitConstructor));
		visitAllSafe(getDeclaredMethods(clazz), this::visitMethod);
		visitAllSafe(clazz.getDeclaredFields(), filterSynthetic(this::visitField));
		visitAllSafe(clazz.getDeclaredClasses(), this::visitType);
	}

	protected final <T> void visitType(Class<T> aClass) {
		if (aClass.isAnnotation()) {
			visitAnnotationClass((Class<Annotation>) aClass);
		} else if (aClass.isInterface()) {
			visitInterface(aClass);
		} else if (aClass.isEnum()) {
			visitEnum(aClass);
		} else {
			visitClass(aClass);
		}
	}

	@Override
	public <T> void visitInterface(Class<T> clazz) {
		assert clazz.isInterface();
		if (clazz.getPackage() != null) {
			visitPackage(clazz.getPackage());
		}
		visitAllSafe(clazz.getGenericInterfaces(), anInterface -> visitTypeReference(CtRole.INTERFACE, anInterface));
		visitAllSafe(clazz.getDeclaredAnnotations(), this::visitAnnotation);
		visitAllSafe(getDeclaredMethods(clazz), this::visitMethod); // synthetic methods are already filtered
		visitAllSafe(clazz.getDeclaredFields(), filterSynthetic(this::visitField));
		visitAllSafe(clazz.getDeclaredClasses(), this::visitType);
		visitAllSafe(clazz.getTypeParameters(), this::visitTypeParameter);
	}

	@Override
	public <T> void visitEnum(Class<T> clazz) {
		assert clazz.isEnum();
		if (clazz.getPackage() != null) {
			visitPackage(clazz.getPackage());
		}
		visitAllSafe(clazz.getGenericInterfaces(), anInterface -> visitTypeReference(CtRole.INTERFACE, anInterface));
		visitAllSafe(clazz.getDeclaredAnnotations(), this::visitAnnotation);
		try {
			for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
				if (Modifier.isPrivate(constructor.getModifiers())) {
					Class<?>[] paramTypes = constructor.getParameterTypes();
					if (paramTypes.length == 2 && paramTypes[0] == String.class && paramTypes[1] == int.class) {
						//ignore implicit enum constructor
						continue;
					}
				}
				if (constructor.isSynthetic()) {
					continue;
				}
				visitConstructor(constructor);
			}
		} catch (NoClassDefFoundError ignore) {
			// partial classpath
		}
		try {
			for (RtMethod method : getDeclaredMethods(clazz)) {
				if (("valueOf".equals(method.getName()) && method.getParameterTypes().length == 1 && String.class.equals(method.getParameterTypes()[0])) || "values".equals(method.getName())) {
					continue;
				}
				if (method.getMethod().isSynthetic()) {
					continue;
				}
				visitMethod(method);
			}
		} catch (NoClassDefFoundError ignore) {
			// partial classpath
		}
		visitAllSafe(clazz.getDeclaredFields(), filterSynthetic(field -> {
			if (field.isEnumConstant()) {
				visitEnumValue(field);
			} else {
				visitField(field);
			}
		}));
		visitAllSafe(clazz.getDeclaredClasses(), this::visitType);
	}

	@Override
	public <T extends Annotation> void visitAnnotationClass(Class<T> clazz) {
		assert clazz.isAnnotation();
		if (clazz.getPackage() != null) {
			visitPackage(clazz.getPackage());
		}
		visitAllSafe(clazz.getDeclaredAnnotations(), this::visitAnnotation);
		visitAllSafe(getDeclaredMethods(clazz), this::visitMethod);
		visitAllSafe(clazz.getDeclaredFields(), filterSynthetic(this::visitField));
		visitAllSafe(clazz.getDeclaredClasses(), this::visitType);
	}

	@Override
	public void visitAnnotation(Annotation annotation) {
		if (annotation.annotationType() != null) {
			visitTypeReference(CtRole.ANNOTATION_TYPE, annotation.annotationType());
			visitAllSafe(getDeclaredMethods(annotation.annotationType()), method -> visitMethod(method, annotation));
		}
	}

	@Override
	public <T> void visitConstructor(Constructor<T> constructor) {
		visitAllSafe(constructor.getDeclaredAnnotations(), this::visitAnnotation);
		int nrEnclosingClasses = getNumberOfEnclosingClasses(constructor.getDeclaringClass());
		for (RtParameter parameter : RtParameter.parametersOf(constructor)) {
			//ignore implicit parameters of enclosing classes
			if (nrEnclosingClasses > 0) {
				nrEnclosingClasses--;
				continue;
			}
			visitParameter(parameter);
		}
		visitAllSafe(constructor.getTypeParameters(), this::visitTypeParameter);
		visitAllSafe(constructor.getExceptionTypes(), exceptionType -> visitTypeReference(CtRole.THROWN, exceptionType));
	}

	private int getNumberOfEnclosingClasses(Class<?> clazz) {
		int depth = 0;
		while (Modifier.isStatic(clazz.getModifiers()) == false && (clazz = clazz.getEnclosingClass()) != null) {
			depth++;
		}
		return depth;
	}

	@Override
	public final void visitMethod(RtMethod method) {
		this.visitMethod(method, null);
	}

	protected void visitMethod(RtMethod method, Annotation parent) {
		visitAllSafe(method.getDeclaredAnnotations(), annotation -> {
			if (parent == null || !annotation.annotationType().equals(parent.annotationType())) {
				visitAnnotation(annotation);
			}
		});
		visitAllSafe(method.getTypeParameters(), this::visitTypeParameter);
		visitAllSafe(RtParameter.parametersOf(method), this::visitParameter);
		if (method.getReturnType() != null) {
			visitTypeReference(CtRole.TYPE, method.getGenericReturnType());
		}
		visitAllSafe(method.getExceptionTypes(), exceptionType -> visitTypeReference(CtRole.THROWN, exceptionType));
	}

	@Override
	public void visitField(Field field) {
		visitAllSafe(field.getDeclaredAnnotations(), this::visitAnnotation);
		if (field.getGenericType() != null) {
			visitTypeReference(CtRole.TYPE, field.getGenericType());
		}
	}

	@Override
	public void visitEnumValue(Field field) {
		visitAllSafe(field.getDeclaredAnnotations(), this::visitAnnotation);
		if (field.getType() != null) {
			visitTypeReference(CtRole.TYPE, field.getType());
		}
	}

	@Override
	public void visitParameter(RtParameter parameter) {
		visitAllSafe(parameter.getDeclaredAnnotations(), this::visitAnnotation);
		if (parameter.getGenericType() != null) {
			visitTypeReference(CtRole.TYPE, parameter.getGenericType());
		}
	}

	@Override
	public <T extends GenericDeclaration> void visitTypeParameter(TypeVariable<T> parameter) {
		for (Type type : parameter.getBounds()) {
			if (type == Object.class) {
				// we want to ignore Object to avoid <T extends Object>
				continue;
			}
			visitTypeReference(CtRole.SUPER_TYPE, type);
		}
	}

	@Override
	public <T extends GenericDeclaration> void visitTypeParameterReference(CtRole role, TypeVariable<T> parameter) {
		for (Type type : parameter.getBounds()) {
			if (type == Object.class) {
				// we bypass Object.class: if a generic type extends Object we don't put it in the model, it's implicit
				// we do the same thing in ReferenceBuilder
				continue;
			}
			visitTypeReference(CtRole.SUPER_TYPE, type);
		}
	}

	@Override
	public final void visitTypeReference(CtRole role, Type type) {
		if (type instanceof TypeVariable) {
			this.visitTypeParameterReference(role, (TypeVariable<?>) type);
			return;
		}
		if (type instanceof ParameterizedType) {
			this.visitTypeReference(role, (ParameterizedType) type);
			return;
		}
		if (type instanceof WildcardType) {
			this.visitTypeReference(role, (WildcardType) type);
			return;
		}
		if (type instanceof GenericArrayType) {
			visitArrayReference(role, ((GenericArrayType) type).getGenericComponentType());
			return;
		}
		if (type instanceof Class) {
			Class<?> clazz = (Class<?>) type;
			if (clazz.isArray()) {
				visitArrayReference(role, clazz.getComponentType());
				return;
			}
			this.visitTypeReference(role, clazz);
			return;
		}
		throw new SpoonException("Unexpected java reflection type: " + type.getClass().getName());
	}

	@Override
	public void visitTypeReference(CtRole role, ParameterizedType type) {
		Type rawType = type.getRawType();

		if (!(rawType instanceof Class)) {
			throw new UnsupportedOperationException("Rawtype of the parameterized type should be a class.");
		}

		Class<?> classRaw = (Class<?>) rawType;

		if (classRaw.getPackage() != null) {
			visitPackage(classRaw.getPackage());
		}
		if (classRaw.getEnclosingClass() != null) {
			visitTypeReference(CtRole.DECLARING_TYPE, classRaw.getEnclosingClass());
		}

		for (Type generic : type.getActualTypeArguments()) {
			visitTypeReference(CtRole.TYPE_ARGUMENT, generic);
		}
	}

	@Override
	public void visitTypeReference(CtRole role, WildcardType type) {
		if (!type.getUpperBounds()[0].equals(Object.class)) {
			for (Type upper : type.getUpperBounds()) {
				visitTypeReference(CtRole.BOUNDING_TYPE, upper);
			}
		}
		for (Type lower : type.getLowerBounds()) {
			visitTypeReference(CtRole.BOUNDING_TYPE, lower);
		}
	}

	@Override
	public <T> void visitArrayReference(CtRole role, Type typeArray) {
		visitTypeReference(role, typeArray);
	}

	@Override
	public <T> void visitTypeReference(CtRole role, Class<T> clazz) {
		if (clazz.getPackage() != null && clazz.getEnclosingClass() == null) {
			visitPackage(clazz.getPackage());
		}
		if (clazz.getEnclosingClass() != null) {
			visitTypeReference(CtRole.DECLARING_TYPE, clazz.getEnclosingClass());
		}
	}

	private <T> List<RtMethod> getDeclaredMethods(Class<T> clazz) {
		Method[] javaMethods = clazz.getDeclaredMethods();
		List<RtMethod> rtMethods = new ArrayList<>();
		for (Method method : javaMethods) {
			if (method.isSynthetic()) {
				//ignore synthetic methods.
				continue;
			}
			rtMethods.add(RtMethod.create(method));
		}
		return rtMethods;
	}

	@Override
	public <T> void visitRecord(Class<T> clazz) {
		if (recordClass == null) {
			// the record class is missing we cant create any shadow element for it.
			return;
		}
		visitAllSafe(clazz.getTypeParameters(), this::visitTypeParameter);
		// TODO can this even throw an exception?
		//  filtering needed?
		try {
			if (clazz.getGenericSuperclass() != null && clazz.getGenericSuperclass() != Object.class) {
				visitTypeReference(CtRole.SUPER_TYPE, clazz.getGenericSuperclass());
			}
		} catch (NoClassDefFoundError ignore) {
			// partial classpath
		}
		visitAllSafe(clazz.getGenericInterfaces(), anInterface -> visitTypeReference(CtRole.INTERFACE, anInterface));
		visitAllSafe(clazz.getDeclaredAnnotations(), this::visitAnnotation);
		visitAllSafe(clazz.getDeclaredConstructors(), filterSynthetic(this::visitConstructor));
		visitAllSafe(getDeclaredMethods(clazz), this::visitMethod); // synthetic already filtered
		visitAllSafe(clazz.getDeclaredFields(), filterSynthetic(this::visitField));
		visitAllSafe(clazz.getDeclaredClasses(), this::visitType);
		visitAllSafe(MethodHandleUtils.getRecordComponents(clazz), this::visitRecordComponent);
	}


	private static Class<?> getRecordClass() {
		try {
			return Class.forName("java.lang.Record");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void visitRecordComponent(AnnotatedElement recordComponent) {

	}

	private <T> void visitAllSafe(T[] elements, Consumer<T> action) {
		for (T element : elements) {
			visitSafe(element, action);
		}
	}

	private <T> void visitAllSafe(Iterable<T> elements, Consumer<T> action) {
		for (T element : elements) {
			visitSafe(element, action);
		}
	}

	private <T> void visitSafe(T element, Consumer<T> action) {
		try {
			action.accept(element);
		} catch (NoClassDefFoundError ignore) {
			// partial classpath
		}
	}

	private <T extends Member> Consumer<T> filterSynthetic(Consumer<T> action) {
		return element -> {
			if (!element.isSynthetic()) {
				action.accept(element);
			}
		};
	}

}
