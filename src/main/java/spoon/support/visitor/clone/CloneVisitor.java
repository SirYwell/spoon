/* SPDX-License-Identifier: (MIT OR CECILL-C)

Copyright (C) 2006-2019 INRIA and contributors

Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) of the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 */
package spoon.support.visitor.clone;
/**
 * Used to clone a given element.
 *
 * This class is generated automatically by the processor spoon.generating.CloneVisitorGenerator.
 */
public class CloneVisitor extends spoon.reflect.visitor.CtScanner {
	private final spoon.support.visitor.equals.CloneHelper cloneHelper;

	private final spoon.support.visitor.clone.CloneBuilder builder = new spoon.support.visitor.clone.CloneBuilder();

	private spoon.reflect.declaration.CtElement other;

	public CloneVisitor(spoon.support.visitor.equals.CloneHelper cloneHelper) {
		this.cloneHelper = cloneHelper;
	}

	public <T extends spoon.reflect.declaration.CtElement> T getClone() {
		return ((T) (other));
	}

	public <T extends spoon.reflect.declaration.CtElement> T clone(T element) {
		scan(element);
		T clone = getClone();
		other = null;
		return clone;
	}

	public <T extends spoon.reflect.declaration.CtElement> java.util.Collection<T> clone(java.util.Collection<T> elements) {
		if ((elements == null) || elements.isEmpty()) {
			return new java.util.ArrayList<>();
		}
		java.util.Collection<T> others = new java.util.ArrayList<>();
		for (T element : elements) {
			addClone(others, element);
		}
		return others;
	}

	public <T extends spoon.reflect.declaration.CtElement> java.util.List<T> clone(java.util.List<T> elements) {
		if (elements instanceof spoon.support.util.EmptyClearableList) {
			return elements;
		}
		if ((elements == null) || elements.isEmpty()) {
			return new java.util.ArrayList<>();
		}
		java.util.List<T> others = new java.util.ArrayList<>();
		for (T element : elements) {
			addClone(others, element);
		}
		return others;
	}

	/**
	 * clones a Set of elements
	 *
	 * @param <T>
	 * 		the Set of elements to be cloned
	 * @return others Set of cloned elements
	 */
	public <T extends spoon.reflect.declaration.CtElement> java.util.Set<T> clone(java.util.Set<T> elements) {
		if (elements instanceof spoon.support.util.EmptyClearableSet) {
			return elements;
		}
		if ((elements == null) || elements.isEmpty()) {
			return spoon.support.util.EmptyClearableSet.instance();
		}
		java.util.Set<T> others = new java.util.HashSet<>(elements.size());
		for (T element : elements) {
			addClone(others, element);
		}
		return others;
	}

	public <T extends spoon.reflect.declaration.CtElement> java.util.Map<java.lang.String, T> clone(java.util.Map<java.lang.String, T> elements) {
		if ((elements == null) || elements.isEmpty()) {
			return new java.util.HashMap<>();
		}
		java.util.Map<java.lang.String, T> others = new java.util.HashMap<>();
		for (java.util.Map.Entry<java.lang.String, T> tEntry : elements.entrySet()) {
			addClone(others, tEntry.getKey(), tEntry.getValue());
		}
		return others;
	}

	/**
	 * clones an element and adds it's clone as value into targetCollection
	 *
	 * @param targetCollection
	 * 		- the collection which will receive a clone of element
	 * @param element
	 * 		to be cloned element
	 */
	protected <T extends spoon.reflect.declaration.CtElement> void addClone(java.util.Collection<T> targetCollection, T element) {
		targetCollection.add(clone(element));
	}

	/**
	 * clones a value and adds it's clone as value into targetMap under key
	 *
	 * @param targetMap
	 * 		- the Map which will receive a clone of value
	 * @param key
	 * 		the target key, which has to be used to add cloned value into targetMap
	 * @param value
	 * 		to be cloned element
	 */
	protected <T extends spoon.reflect.declaration.CtElement> void addClone(java.util.Map<java.lang.String, T> targetMap, java.lang.String key, T value) {
		targetMap.put(key, clone(value));
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <A extends java.lang.annotation.Annotation> void visitCtAnnotation(final spoon.reflect.declaration.CtAnnotation<A> annotation) {
		spoon.reflect.declaration.CtAnnotation<A> aCtAnnotation = annotation.getFactory().Core().createAnnotation();
		this.builder.copy(annotation, aCtAnnotation);
		aCtAnnotation.setType(clone(annotation.getType()));
		aCtAnnotation.setComments(clone(annotation.getComments()));
		aCtAnnotation.setAnnotationType(clone(annotation.getAnnotationType()));
		aCtAnnotation.setAnnotations(clone(annotation.getAnnotations()));
		aCtAnnotation.setValues(clone(annotation.getValues()));
		this.cloneHelper.tailor(annotation, aCtAnnotation);
		this.other = aCtAnnotation;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(final spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
		spoon.reflect.declaration.CtAnnotationType<A> aCtAnnotationType = annotationType.getFactory().Core().createAnnotationType();
		this.builder.copy(annotationType, aCtAnnotationType);
		aCtAnnotationType.setAnnotations(clone(annotationType.getAnnotations()));
		aCtAnnotationType.setTypeMembers(clone(annotationType.getTypeMembers()));
		aCtAnnotationType.setComments(clone(annotationType.getComments()));
		this.cloneHelper.tailor(annotationType, aCtAnnotationType);
		this.other = aCtAnnotationType;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtAnonymousExecutable(final spoon.reflect.declaration.CtAnonymousExecutable anonymousExec) {
		spoon.reflect.declaration.CtAnonymousExecutable aCtAnonymousExecutable = anonymousExec.getFactory().Core().createAnonymousExecutable();
		this.builder.copy(anonymousExec, aCtAnonymousExecutable);
		aCtAnonymousExecutable.setAnnotations(clone(anonymousExec.getAnnotations()));
		aCtAnonymousExecutable.setBody(clone(anonymousExec.getBody()));
		aCtAnonymousExecutable.setComments(clone(anonymousExec.getComments()));
		this.cloneHelper.tailor(anonymousExec, aCtAnonymousExecutable);
		this.other = aCtAnonymousExecutable;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtArrayRead(final spoon.reflect.code.CtArrayRead<T> arrayRead) {
		spoon.reflect.code.CtArrayRead<T> aCtArrayRead = arrayRead.getFactory().Core().createArrayRead();
		this.builder.copy(arrayRead, aCtArrayRead);
		aCtArrayRead.setAnnotations(clone(arrayRead.getAnnotations()));
		aCtArrayRead.setType(clone(arrayRead.getType()));
		aCtArrayRead.setTypeCasts(clone(arrayRead.getTypeCasts()));
		aCtArrayRead.setTarget(clone(arrayRead.getTarget()));
		aCtArrayRead.setIndexExpression(clone(arrayRead.getIndexExpression()));
		aCtArrayRead.setComments(clone(arrayRead.getComments()));
		this.cloneHelper.tailor(arrayRead, aCtArrayRead);
		this.other = aCtArrayRead;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtArrayWrite(final spoon.reflect.code.CtArrayWrite<T> arrayWrite) {
		spoon.reflect.code.CtArrayWrite<T> aCtArrayWrite = arrayWrite.getFactory().Core().createArrayWrite();
		this.builder.copy(arrayWrite, aCtArrayWrite);
		aCtArrayWrite.setAnnotations(clone(arrayWrite.getAnnotations()));
		aCtArrayWrite.setType(clone(arrayWrite.getType()));
		aCtArrayWrite.setTypeCasts(clone(arrayWrite.getTypeCasts()));
		aCtArrayWrite.setTarget(clone(arrayWrite.getTarget()));
		aCtArrayWrite.setIndexExpression(clone(arrayWrite.getIndexExpression()));
		aCtArrayWrite.setComments(clone(arrayWrite.getComments()));
		this.cloneHelper.tailor(arrayWrite, aCtArrayWrite);
		this.other = aCtArrayWrite;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtArrayTypeReference(final spoon.reflect.reference.CtArrayTypeReference<T> reference) {
		spoon.reflect.reference.CtArrayTypeReference<T> aCtArrayTypeReference = reference.getFactory().Core().createArrayTypeReference();
		this.builder.copy(reference, aCtArrayTypeReference);
		aCtArrayTypeReference.setPackage(clone(reference.getPackage()));
		aCtArrayTypeReference.setDeclaringType(clone(reference.getDeclaringType()));
		aCtArrayTypeReference.setComponentType(clone(reference.getComponentType()));
		aCtArrayTypeReference.setActualTypeArguments(clone(reference.getActualTypeArguments()));
		aCtArrayTypeReference.setAnnotations(clone(reference.getAnnotations()));
		this.cloneHelper.tailor(reference, aCtArrayTypeReference);
		this.other = aCtArrayTypeReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtAssert(final spoon.reflect.code.CtAssert<T> asserted) {
		spoon.reflect.code.CtAssert<T> aCtAssert = asserted.getFactory().Core().createAssert();
		this.builder.copy(asserted, aCtAssert);
		aCtAssert.setAnnotations(clone(asserted.getAnnotations()));
		aCtAssert.setAssertExpression(clone(asserted.getAssertExpression()));
		aCtAssert.setExpression(clone(asserted.getExpression()));
		aCtAssert.setComments(clone(asserted.getComments()));
		this.cloneHelper.tailor(asserted, aCtAssert);
		this.other = aCtAssert;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T, A extends T> void visitCtAssignment(final spoon.reflect.code.CtAssignment<T, A> assignement) {
		spoon.reflect.code.CtAssignment<T, A> aCtAssignment = assignement.getFactory().Core().createAssignment();
		this.builder.copy(assignement, aCtAssignment);
		aCtAssignment.setAnnotations(clone(assignement.getAnnotations()));
		aCtAssignment.setType(clone(assignement.getType()));
		aCtAssignment.setTypeCasts(clone(assignement.getTypeCasts()));
		aCtAssignment.setAssigned(clone(assignement.getAssigned()));
		aCtAssignment.setAssignment(clone(assignement.getAssignment()));
		aCtAssignment.setComments(clone(assignement.getComments()));
		this.cloneHelper.tailor(assignement, aCtAssignment);
		this.other = aCtAssignment;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtBinaryOperator(final spoon.reflect.code.CtBinaryOperator<T> operator) {
		spoon.reflect.code.CtBinaryOperator<T> aCtBinaryOperator = operator.getFactory().Core().createBinaryOperator();
		this.builder.copy(operator, aCtBinaryOperator);
		aCtBinaryOperator.setAnnotations(clone(operator.getAnnotations()));
		aCtBinaryOperator.setType(clone(operator.getType()));
		aCtBinaryOperator.setTypeCasts(clone(operator.getTypeCasts()));
		aCtBinaryOperator.setLeftHandOperand(clone(operator.getLeftHandOperand()));
		aCtBinaryOperator.setRightHandOperand(clone(operator.getRightHandOperand()));
		aCtBinaryOperator.setComments(clone(operator.getComments()));
		this.cloneHelper.tailor(operator, aCtBinaryOperator);
		this.other = aCtBinaryOperator;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <R> void visitCtBlock(final spoon.reflect.code.CtBlock<R> block) {
		spoon.reflect.code.CtBlock<R> aCtBlock = block.getFactory().Core().createBlock();
		this.builder.copy(block, aCtBlock);
		aCtBlock.setAnnotations(clone(block.getAnnotations()));
		aCtBlock.setStatements(clone(block.getStatements()));
		aCtBlock.setComments(clone(block.getComments()));
		this.cloneHelper.tailor(block, aCtBlock);
		this.other = aCtBlock;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtBreak(final spoon.reflect.code.CtBreak breakStatement) {
		spoon.reflect.code.CtBreak aCtBreak = breakStatement.getFactory().Core().createBreak();
		this.builder.copy(breakStatement, aCtBreak);
		aCtBreak.setAnnotations(clone(breakStatement.getAnnotations()));
		aCtBreak.setComments(clone(breakStatement.getComments()));
		this.cloneHelper.tailor(breakStatement, aCtBreak);
		this.other = aCtBreak;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <S> void visitCtCase(final spoon.reflect.code.CtCase<S> caseStatement) {
		spoon.reflect.code.CtCase<S> aCtCase = caseStatement.getFactory().Core().createCase();
		this.builder.copy(caseStatement, aCtCase);
		aCtCase.setAnnotations(clone(caseStatement.getAnnotations()));
		aCtCase.setCaseExpressions(clone(caseStatement.getCaseExpressions()));
		aCtCase.setStatements(clone(caseStatement.getStatements()));
		aCtCase.setComments(clone(caseStatement.getComments()));
		this.cloneHelper.tailor(caseStatement, aCtCase);
		this.other = aCtCase;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtCatch(final spoon.reflect.code.CtCatch catchBlock) {
		spoon.reflect.code.CtCatch aCtCatch = catchBlock.getFactory().Core().createCatch();
		this.builder.copy(catchBlock, aCtCatch);
		aCtCatch.setAnnotations(clone(catchBlock.getAnnotations()));
		aCtCatch.setParameter(clone(catchBlock.getParameter()));
		aCtCatch.setBody(clone(catchBlock.getBody()));
		aCtCatch.setComments(clone(catchBlock.getComments()));
		this.cloneHelper.tailor(catchBlock, aCtCatch);
		this.other = aCtCatch;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtClass(final spoon.reflect.declaration.CtClass<T> ctClass) {
		spoon.reflect.declaration.CtClass<T> aCtClass = ctClass.getFactory().Core().createClass();
		this.builder.copy(ctClass, aCtClass);
		aCtClass.setAnnotations(clone(ctClass.getAnnotations()));
		aCtClass.setSuperclass(clone(ctClass.getSuperclass()));
		aCtClass.setSuperInterfaces(clone(ctClass.getSuperInterfaces()));
		aCtClass.setFormalCtTypeParameters(clone(ctClass.getFormalCtTypeParameters()));
		aCtClass.setTypeMembers(clone(ctClass.getTypeMembers()));
		aCtClass.setComments(clone(ctClass.getComments()));
		this.cloneHelper.tailor(ctClass, aCtClass);
		this.other = aCtClass;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter) {
		spoon.reflect.declaration.CtTypeParameter aCtTypeParameter = typeParameter.getFactory().Core().createTypeParameter();
		this.builder.copy(typeParameter, aCtTypeParameter);
		aCtTypeParameter.setAnnotations(clone(typeParameter.getAnnotations()));
		aCtTypeParameter.setSuperclass(clone(typeParameter.getSuperclass()));
		aCtTypeParameter.setComments(clone(typeParameter.getComments()));
		this.cloneHelper.tailor(typeParameter, aCtTypeParameter);
		this.other = aCtTypeParameter;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtConditional(final spoon.reflect.code.CtConditional<T> conditional) {
		spoon.reflect.code.CtConditional<T> aCtConditional = conditional.getFactory().Core().createConditional();
		this.builder.copy(conditional, aCtConditional);
		aCtConditional.setType(clone(conditional.getType()));
		aCtConditional.setAnnotations(clone(conditional.getAnnotations()));
		aCtConditional.setCondition(clone(conditional.getCondition()));
		aCtConditional.setThenExpression(clone(conditional.getThenExpression()));
		aCtConditional.setElseExpression(clone(conditional.getElseExpression()));
		aCtConditional.setComments(clone(conditional.getComments()));
		aCtConditional.setTypeCasts(clone(conditional.getTypeCasts()));
		this.cloneHelper.tailor(conditional, aCtConditional);
		this.other = aCtConditional;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtConstructor(final spoon.reflect.declaration.CtConstructor<T> c) {
		spoon.reflect.declaration.CtConstructor<T> aCtConstructor = c.getFactory().Core().createConstructor();
		this.builder.copy(c, aCtConstructor);
		aCtConstructor.setAnnotations(clone(c.getAnnotations()));
		aCtConstructor.setParameters(clone(c.getParameters()));
		aCtConstructor.setThrownTypes(clone(c.getThrownTypes()));
		aCtConstructor.setFormalCtTypeParameters(clone(c.getFormalCtTypeParameters()));
		aCtConstructor.setBody(clone(c.getBody()));
		aCtConstructor.setComments(clone(c.getComments()));
		this.cloneHelper.tailor(c, aCtConstructor);
		this.other = aCtConstructor;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtContinue(final spoon.reflect.code.CtContinue continueStatement) {
		spoon.reflect.code.CtContinue aCtContinue = continueStatement.getFactory().Core().createContinue();
		this.builder.copy(continueStatement, aCtContinue);
		aCtContinue.setAnnotations(clone(continueStatement.getAnnotations()));
		aCtContinue.setComments(clone(continueStatement.getComments()));
		this.cloneHelper.tailor(continueStatement, aCtContinue);
		this.other = aCtContinue;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtDo(final spoon.reflect.code.CtDo doLoop) {
		spoon.reflect.code.CtDo aCtDo = doLoop.getFactory().Core().createDo();
		this.builder.copy(doLoop, aCtDo);
		aCtDo.setAnnotations(clone(doLoop.getAnnotations()));
		aCtDo.setLoopingExpression(clone(doLoop.getLoopingExpression()));
		aCtDo.setBody(clone(doLoop.getBody()));
		aCtDo.setComments(clone(doLoop.getComments()));
		this.cloneHelper.tailor(doLoop, aCtDo);
		this.other = aCtDo;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T extends java.lang.Enum<?>> void visitCtEnum(final spoon.reflect.declaration.CtEnum<T> ctEnum) {
		spoon.reflect.declaration.CtEnum<T> aCtEnum = ctEnum.getFactory().Core().createEnum();
		this.builder.copy(ctEnum, aCtEnum);
		aCtEnum.setAnnotations(clone(ctEnum.getAnnotations()));
		aCtEnum.setSuperInterfaces(clone(ctEnum.getSuperInterfaces()));
		aCtEnum.setTypeMembers(clone(ctEnum.getTypeMembers()));
		aCtEnum.setEnumValues(clone(ctEnum.getEnumValues()));
		aCtEnum.setComments(clone(ctEnum.getComments()));
		this.cloneHelper.tailor(ctEnum, aCtEnum);
		this.other = aCtEnum;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtExecutableReference(final spoon.reflect.reference.CtExecutableReference<T> reference) {
		spoon.reflect.reference.CtExecutableReference<T> aCtExecutableReference = reference.getFactory().Core().createExecutableReference();
		this.builder.copy(reference, aCtExecutableReference);
		aCtExecutableReference.setDeclaringType(clone(reference.getDeclaringType()));
		aCtExecutableReference.setType(clone(reference.getType()));
		aCtExecutableReference.setParameters(clone(reference.getParameters()));
		aCtExecutableReference.setActualTypeArguments(clone(reference.getActualTypeArguments()));
		aCtExecutableReference.setAnnotations(clone(reference.getAnnotations()));
		aCtExecutableReference.setComments(clone(reference.getComments()));
		this.cloneHelper.tailor(reference, aCtExecutableReference);
		this.other = aCtExecutableReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtField(final spoon.reflect.declaration.CtField<T> f) {
		spoon.reflect.declaration.CtField<T> aCtField = f.getFactory().Core().createField();
		this.builder.copy(f, aCtField);
		aCtField.setAnnotations(clone(f.getAnnotations()));
		aCtField.setType(clone(f.getType()));
		aCtField.setDefaultExpression(clone(f.getDefaultExpression()));
		aCtField.setComments(clone(f.getComments()));
		this.cloneHelper.tailor(f, aCtField);
		this.other = aCtField;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtEnumValue(final spoon.reflect.declaration.CtEnumValue<T> enumValue) {
		spoon.reflect.declaration.CtEnumValue<T> aCtEnumValue = enumValue.getFactory().Core().createEnumValue();
		this.builder.copy(enumValue, aCtEnumValue);
		aCtEnumValue.setAnnotations(clone(enumValue.getAnnotations()));
		aCtEnumValue.setType(clone(enumValue.getType()));
		aCtEnumValue.setDefaultExpression(clone(enumValue.getDefaultExpression()));
		aCtEnumValue.setComments(clone(enumValue.getComments()));
		this.cloneHelper.tailor(enumValue, aCtEnumValue);
		this.other = aCtEnumValue;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtThisAccess(final spoon.reflect.code.CtThisAccess<T> thisAccess) {
		spoon.reflect.code.CtThisAccess<T> aCtThisAccess = thisAccess.getFactory().Core().createThisAccess();
		this.builder.copy(thisAccess, aCtThisAccess);
		aCtThisAccess.setComments(clone(thisAccess.getComments()));
		aCtThisAccess.setAnnotations(clone(thisAccess.getAnnotations()));
		aCtThisAccess.setType(clone(thisAccess.getType()));
		aCtThisAccess.setTypeCasts(clone(thisAccess.getTypeCasts()));
		aCtThisAccess.setTarget(clone(thisAccess.getTarget()));
		this.cloneHelper.tailor(thisAccess, aCtThisAccess);
		this.other = aCtThisAccess;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtAnnotationFieldAccess(final spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess) {
		spoon.reflect.code.CtAnnotationFieldAccess<T> aCtAnnotationFieldAccess = annotationFieldAccess.getFactory().Core().createAnnotationFieldAccess();
		this.builder.copy(annotationFieldAccess, aCtAnnotationFieldAccess);
		aCtAnnotationFieldAccess.setComments(clone(annotationFieldAccess.getComments()));
		aCtAnnotationFieldAccess.setAnnotations(clone(annotationFieldAccess.getAnnotations()));
		aCtAnnotationFieldAccess.setTypeCasts(clone(annotationFieldAccess.getTypeCasts()));
		aCtAnnotationFieldAccess.setTarget(clone(annotationFieldAccess.getTarget()));
		aCtAnnotationFieldAccess.setVariable(clone(annotationFieldAccess.getVariable()));
		this.cloneHelper.tailor(annotationFieldAccess, aCtAnnotationFieldAccess);
		this.other = aCtAnnotationFieldAccess;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtFieldReference(final spoon.reflect.reference.CtFieldReference<T> reference) {
		spoon.reflect.reference.CtFieldReference<T> aCtFieldReference = reference.getFactory().Core().createFieldReference();
		this.builder.copy(reference, aCtFieldReference);
		aCtFieldReference.setDeclaringType(clone(reference.getDeclaringType()));
		aCtFieldReference.setType(clone(reference.getType()));
		aCtFieldReference.setAnnotations(clone(reference.getAnnotations()));
		this.cloneHelper.tailor(reference, aCtFieldReference);
		this.other = aCtFieldReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtFor(final spoon.reflect.code.CtFor forLoop) {
		spoon.reflect.code.CtFor aCtFor = forLoop.getFactory().Core().createFor();
		this.builder.copy(forLoop, aCtFor);
		aCtFor.setAnnotations(clone(forLoop.getAnnotations()));
		aCtFor.setForInit(clone(forLoop.getForInit()));
		aCtFor.setExpression(clone(forLoop.getExpression()));
		aCtFor.setForUpdate(clone(forLoop.getForUpdate()));
		aCtFor.setBody(clone(forLoop.getBody()));
		aCtFor.setComments(clone(forLoop.getComments()));
		this.cloneHelper.tailor(forLoop, aCtFor);
		this.other = aCtFor;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtForEach(final spoon.reflect.code.CtForEach foreach) {
		spoon.reflect.code.CtForEach aCtForEach = foreach.getFactory().Core().createForEach();
		this.builder.copy(foreach, aCtForEach);
		aCtForEach.setAnnotations(clone(foreach.getAnnotations()));
		aCtForEach.setVariable(clone(foreach.getVariable()));
		aCtForEach.setExpression(clone(foreach.getExpression()));
		aCtForEach.setBody(clone(foreach.getBody()));
		aCtForEach.setComments(clone(foreach.getComments()));
		this.cloneHelper.tailor(foreach, aCtForEach);
		this.other = aCtForEach;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtIf(final spoon.reflect.code.CtIf ifElement) {
		spoon.reflect.code.CtIf aCtIf = ifElement.getFactory().Core().createIf();
		this.builder.copy(ifElement, aCtIf);
		aCtIf.setAnnotations(clone(ifElement.getAnnotations()));
		aCtIf.setCondition(clone(ifElement.getCondition()));
		aCtIf.setThenStatement(clone(((spoon.reflect.code.CtStatement) (ifElement.getThenStatement()))));
		aCtIf.setElseStatement(clone(((spoon.reflect.code.CtStatement) (ifElement.getElseStatement()))));
		aCtIf.setComments(clone(ifElement.getComments()));
		this.cloneHelper.tailor(ifElement, aCtIf);
		this.other = aCtIf;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtInterface(final spoon.reflect.declaration.CtInterface<T> intrface) {
		spoon.reflect.declaration.CtInterface<T> aCtInterface = intrface.getFactory().Core().createInterface();
		this.builder.copy(intrface, aCtInterface);
		aCtInterface.setAnnotations(clone(intrface.getAnnotations()));
		aCtInterface.setSuperInterfaces(clone(intrface.getSuperInterfaces()));
		aCtInterface.setFormalCtTypeParameters(clone(intrface.getFormalCtTypeParameters()));
		aCtInterface.setTypeMembers(clone(intrface.getTypeMembers()));
		aCtInterface.setComments(clone(intrface.getComments()));
		this.cloneHelper.tailor(intrface, aCtInterface);
		this.other = aCtInterface;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtInvocation(final spoon.reflect.code.CtInvocation<T> invocation) {
		spoon.reflect.code.CtInvocation<T> aCtInvocation = invocation.getFactory().Core().createInvocation();
		this.builder.copy(invocation, aCtInvocation);
		aCtInvocation.setAnnotations(clone(invocation.getAnnotations()));
		aCtInvocation.setTypeCasts(clone(invocation.getTypeCasts()));
		aCtInvocation.setTarget(clone(invocation.getTarget()));
		aCtInvocation.setExecutable(clone(invocation.getExecutable()));
		aCtInvocation.setArguments(clone(invocation.getArguments()));
		aCtInvocation.setComments(clone(invocation.getComments()));
		this.cloneHelper.tailor(invocation, aCtInvocation);
		this.other = aCtInvocation;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtLiteral(final spoon.reflect.code.CtLiteral<T> literal) {
		spoon.reflect.code.CtLiteral<T> aCtLiteral = literal.getFactory().Core().createLiteral();
		this.builder.copy(literal, aCtLiteral);
		aCtLiteral.setAnnotations(clone(literal.getAnnotations()));
		aCtLiteral.setType(clone(literal.getType()));
		aCtLiteral.setTypeCasts(clone(literal.getTypeCasts()));
		aCtLiteral.setComments(clone(literal.getComments()));
		this.cloneHelper.tailor(literal, aCtLiteral);
		this.other = aCtLiteral;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtTextBlock(final spoon.reflect.code.CtTextBlock literal) {
		spoon.reflect.code.CtTextBlock aCtTextBlock = literal.getFactory().Core().createTextBlock();
		this.builder.copy(literal, aCtTextBlock);
		aCtTextBlock.setAnnotations(clone(literal.getAnnotations()));
		aCtTextBlock.setType(clone(literal.getType()));
		aCtTextBlock.setTypeCasts(clone(literal.getTypeCasts()));
		aCtTextBlock.setComments(clone(literal.getComments()));
		this.cloneHelper.tailor(literal, aCtTextBlock);
		this.other = aCtTextBlock;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtLocalVariable(final spoon.reflect.code.CtLocalVariable<T> localVariable) {
		spoon.reflect.code.CtLocalVariable<T> aCtLocalVariable = localVariable.getFactory().Core().createLocalVariable();
		this.builder.copy(localVariable, aCtLocalVariable);
		aCtLocalVariable.setAnnotations(clone(localVariable.getAnnotations()));
		aCtLocalVariable.setType(clone(localVariable.getType()));
		aCtLocalVariable.setDefaultExpression(clone(localVariable.getDefaultExpression()));
		aCtLocalVariable.setComments(clone(localVariable.getComments()));
		this.cloneHelper.tailor(localVariable, aCtLocalVariable);
		this.other = aCtLocalVariable;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtLocalVariableReference(final spoon.reflect.reference.CtLocalVariableReference<T> reference) {
		spoon.reflect.reference.CtLocalVariableReference<T> aCtLocalVariableReference = reference.getFactory().Core().createLocalVariableReference();
		this.builder.copy(reference, aCtLocalVariableReference);
		aCtLocalVariableReference.setType(clone(reference.getType()));
		aCtLocalVariableReference.setAnnotations(clone(reference.getAnnotations()));
		this.cloneHelper.tailor(reference, aCtLocalVariableReference);
		this.other = aCtLocalVariableReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtCatchVariable(final spoon.reflect.code.CtCatchVariable<T> catchVariable) {
		spoon.reflect.code.CtCatchVariable<T> aCtCatchVariable = catchVariable.getFactory().Core().createCatchVariable();
		this.builder.copy(catchVariable, aCtCatchVariable);
		aCtCatchVariable.setComments(clone(catchVariable.getComments()));
		aCtCatchVariable.setAnnotations(clone(catchVariable.getAnnotations()));
		aCtCatchVariable.setMultiTypes(clone(catchVariable.getMultiTypes()));
		this.cloneHelper.tailor(catchVariable, aCtCatchVariable);
		this.other = aCtCatchVariable;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtCatchVariableReference(final spoon.reflect.reference.CtCatchVariableReference<T> reference) {
		spoon.reflect.reference.CtCatchVariableReference<T> aCtCatchVariableReference = reference.getFactory().Core().createCatchVariableReference();
		this.builder.copy(reference, aCtCatchVariableReference);
		aCtCatchVariableReference.setType(clone(reference.getType()));
		aCtCatchVariableReference.setAnnotations(clone(reference.getAnnotations()));
		this.cloneHelper.tailor(reference, aCtCatchVariableReference);
		this.other = aCtCatchVariableReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtMethod(final spoon.reflect.declaration.CtMethod<T> m) {
		spoon.reflect.declaration.CtMethod<T> aCtMethod = m.getFactory().Core().createMethod();
		this.builder.copy(m, aCtMethod);
		aCtMethod.setAnnotations(clone(m.getAnnotations()));
		aCtMethod.setFormalCtTypeParameters(clone(m.getFormalCtTypeParameters()));
		aCtMethod.setType(clone(m.getType()));
		aCtMethod.setParameters(clone(m.getParameters()));
		aCtMethod.setThrownTypes(clone(m.getThrownTypes()));
		aCtMethod.setBody(clone(m.getBody()));
		aCtMethod.setComments(clone(m.getComments()));
		this.cloneHelper.tailor(m, aCtMethod);
		this.other = aCtMethod;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
		spoon.reflect.declaration.CtAnnotationMethod<T> aCtAnnotationMethod = annotationMethod.getFactory().Core().createAnnotationMethod();
		this.builder.copy(annotationMethod, aCtAnnotationMethod);
		aCtAnnotationMethod.setAnnotations(clone(annotationMethod.getAnnotations()));
		aCtAnnotationMethod.setType(clone(annotationMethod.getType()));
		aCtAnnotationMethod.setDefaultExpression(clone(annotationMethod.getDefaultExpression()));
		aCtAnnotationMethod.setComments(clone(annotationMethod.getComments()));
		this.cloneHelper.tailor(annotationMethod, aCtAnnotationMethod);
		this.other = aCtAnnotationMethod;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtNewArray(final spoon.reflect.code.CtNewArray<T> newArray) {
		spoon.reflect.code.CtNewArray<T> aCtNewArray = newArray.getFactory().Core().createNewArray();
		this.builder.copy(newArray, aCtNewArray);
		aCtNewArray.setAnnotations(clone(newArray.getAnnotations()));
		aCtNewArray.setType(clone(newArray.getType()));
		aCtNewArray.setTypeCasts(clone(newArray.getTypeCasts()));
		aCtNewArray.setElements(clone(newArray.getElements()));
		aCtNewArray.setDimensionExpressions(clone(newArray.getDimensionExpressions()));
		aCtNewArray.setComments(clone(newArray.getComments()));
		this.cloneHelper.tailor(newArray, aCtNewArray);
		this.other = aCtNewArray;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtConstructorCall(final spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
		spoon.reflect.code.CtConstructorCall<T> aCtConstructorCall = ctConstructorCall.getFactory().Core().createConstructorCall();
		this.builder.copy(ctConstructorCall, aCtConstructorCall);
		aCtConstructorCall.setAnnotations(clone(ctConstructorCall.getAnnotations()));
		aCtConstructorCall.setTypeCasts(clone(ctConstructorCall.getTypeCasts()));
		aCtConstructorCall.setExecutable(clone(ctConstructorCall.getExecutable()));
		aCtConstructorCall.setTarget(clone(ctConstructorCall.getTarget()));
		aCtConstructorCall.setArguments(clone(ctConstructorCall.getArguments()));
		aCtConstructorCall.setComments(clone(ctConstructorCall.getComments()));
		this.cloneHelper.tailor(ctConstructorCall, aCtConstructorCall);
		this.other = aCtConstructorCall;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtNewClass(final spoon.reflect.code.CtNewClass<T> newClass) {
		spoon.reflect.code.CtNewClass<T> aCtNewClass = newClass.getFactory().Core().createNewClass();
		this.builder.copy(newClass, aCtNewClass);
		aCtNewClass.setAnnotations(clone(newClass.getAnnotations()));
		aCtNewClass.setTypeCasts(clone(newClass.getTypeCasts()));
		aCtNewClass.setExecutable(clone(newClass.getExecutable()));
		aCtNewClass.setTarget(clone(newClass.getTarget()));
		aCtNewClass.setArguments(clone(newClass.getArguments()));
		aCtNewClass.setAnonymousClass(clone(newClass.getAnonymousClass()));
		aCtNewClass.setComments(clone(newClass.getComments()));
		this.cloneHelper.tailor(newClass, aCtNewClass);
		this.other = aCtNewClass;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtLambda(final spoon.reflect.code.CtLambda<T> lambda) {
		spoon.reflect.code.CtLambda<T> aCtLambda = lambda.getFactory().Core().createLambda();
		this.builder.copy(lambda, aCtLambda);
		aCtLambda.setAnnotations(clone(lambda.getAnnotations()));
		aCtLambda.setType(clone(lambda.getType()));
		aCtLambda.setTypeCasts(clone(lambda.getTypeCasts()));
		aCtLambda.setParameters(clone(lambda.getParameters()));
		aCtLambda.setBody(clone(lambda.getBody()));
		aCtLambda.setExpression(clone(lambda.getExpression()));
		aCtLambda.setComments(clone(lambda.getComments()));
		this.cloneHelper.tailor(lambda, aCtLambda);
		this.other = aCtLambda;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(final spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression) {
		spoon.reflect.code.CtExecutableReferenceExpression<T, E> aCtExecutableReferenceExpression = expression.getFactory().Core().createExecutableReferenceExpression();
		this.builder.copy(expression, aCtExecutableReferenceExpression);
		aCtExecutableReferenceExpression.setComments(clone(expression.getComments()));
		aCtExecutableReferenceExpression.setAnnotations(clone(expression.getAnnotations()));
		aCtExecutableReferenceExpression.setType(clone(expression.getType()));
		aCtExecutableReferenceExpression.setTypeCasts(clone(expression.getTypeCasts()));
		aCtExecutableReferenceExpression.setExecutable(clone(expression.getExecutable()));
		aCtExecutableReferenceExpression.setTarget(clone(expression.getTarget()));
		this.cloneHelper.tailor(expression, aCtExecutableReferenceExpression);
		this.other = aCtExecutableReferenceExpression;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T, A extends T> void visitCtOperatorAssignment(final spoon.reflect.code.CtOperatorAssignment<T, A> assignment) {
		spoon.reflect.code.CtOperatorAssignment<T, A> aCtOperatorAssignment = assignment.getFactory().Core().createOperatorAssignment();
		this.builder.copy(assignment, aCtOperatorAssignment);
		aCtOperatorAssignment.setAnnotations(clone(assignment.getAnnotations()));
		aCtOperatorAssignment.setType(clone(assignment.getType()));
		aCtOperatorAssignment.setTypeCasts(clone(assignment.getTypeCasts()));
		aCtOperatorAssignment.setAssigned(clone(assignment.getAssigned()));
		aCtOperatorAssignment.setAssignment(clone(assignment.getAssignment()));
		aCtOperatorAssignment.setComments(clone(assignment.getComments()));
		this.cloneHelper.tailor(assignment, aCtOperatorAssignment);
		this.other = aCtOperatorAssignment;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtPackage(final spoon.reflect.declaration.CtPackage ctPackage) {
		spoon.reflect.declaration.CtPackage aCtPackage = ctPackage.getFactory().Core().createPackage();
		this.builder.copy(ctPackage, aCtPackage);
		aCtPackage.setAnnotations(clone(ctPackage.getAnnotations()));
		aCtPackage.setPackages(clone(ctPackage.getPackages()));
		aCtPackage.setTypes(clone(ctPackage.getTypes()));
		aCtPackage.setComments(clone(ctPackage.getComments()));
		this.cloneHelper.tailor(ctPackage, aCtPackage);
		this.other = aCtPackage;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtPackageReference(final spoon.reflect.reference.CtPackageReference reference) {
		spoon.reflect.reference.CtPackageReference aCtPackageReference = reference.getFactory().Core().createPackageReference();
		this.builder.copy(reference, aCtPackageReference);
		aCtPackageReference.setAnnotations(clone(reference.getAnnotations()));
		this.cloneHelper.tailor(reference, aCtPackageReference);
		this.other = aCtPackageReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtParameter(final spoon.reflect.declaration.CtParameter<T> parameter) {
		spoon.reflect.declaration.CtParameter<T> aCtParameter = parameter.getFactory().Core().createParameter();
		this.builder.copy(parameter, aCtParameter);
		aCtParameter.setAnnotations(clone(parameter.getAnnotations()));
		aCtParameter.setType(clone(parameter.getType()));
		aCtParameter.setComments(clone(parameter.getComments()));
		this.cloneHelper.tailor(parameter, aCtParameter);
		this.other = aCtParameter;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtParameterReference(final spoon.reflect.reference.CtParameterReference<T> reference) {
		spoon.reflect.reference.CtParameterReference<T> aCtParameterReference = reference.getFactory().Core().createParameterReference();
		this.builder.copy(reference, aCtParameterReference);
		aCtParameterReference.setType(clone(reference.getType()));
		aCtParameterReference.setAnnotations(clone(reference.getAnnotations()));
		this.cloneHelper.tailor(reference, aCtParameterReference);
		this.other = aCtParameterReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <R> void visitCtReturn(final spoon.reflect.code.CtReturn<R> returnStatement) {
		spoon.reflect.code.CtReturn<R> aCtReturn = returnStatement.getFactory().Core().createReturn();
		this.builder.copy(returnStatement, aCtReturn);
		aCtReturn.setAnnotations(clone(returnStatement.getAnnotations()));
		aCtReturn.setReturnedExpression(clone(returnStatement.getReturnedExpression()));
		aCtReturn.setComments(clone(returnStatement.getComments()));
		this.cloneHelper.tailor(returnStatement, aCtReturn);
		this.other = aCtReturn;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <R> void visitCtStatementList(final spoon.reflect.code.CtStatementList statements) {
		spoon.reflect.code.CtStatementList aCtStatementList = statements.getFactory().Core().createStatementList();
		this.builder.copy(statements, aCtStatementList);
		aCtStatementList.setAnnotations(clone(statements.getAnnotations()));
		aCtStatementList.setStatements(clone(statements.getStatements()));
		aCtStatementList.setComments(clone(statements.getComments()));
		this.cloneHelper.tailor(statements, aCtStatementList);
		this.other = aCtStatementList;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <S> void visitCtSwitch(final spoon.reflect.code.CtSwitch<S> switchStatement) {
		spoon.reflect.code.CtSwitch<S> aCtSwitch = switchStatement.getFactory().Core().createSwitch();
		this.builder.copy(switchStatement, aCtSwitch);
		aCtSwitch.setAnnotations(clone(switchStatement.getAnnotations()));
		aCtSwitch.setSelector(clone(switchStatement.getSelector()));
		aCtSwitch.setCases(clone(switchStatement.getCases()));
		aCtSwitch.setComments(clone(switchStatement.getComments()));
		this.cloneHelper.tailor(switchStatement, aCtSwitch);
		this.other = aCtSwitch;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T, S> void visitCtSwitchExpression(final spoon.reflect.code.CtSwitchExpression<T, S> switchExpression) {
		spoon.reflect.code.CtSwitchExpression<T, S> aCtSwitchExpression = switchExpression.getFactory().Core().createSwitchExpression();
		this.builder.copy(switchExpression, aCtSwitchExpression);
		aCtSwitchExpression.setAnnotations(clone(switchExpression.getAnnotations()));
		aCtSwitchExpression.setSelector(clone(switchExpression.getSelector()));
		aCtSwitchExpression.setCases(clone(switchExpression.getCases()));
		aCtSwitchExpression.setComments(clone(switchExpression.getComments()));
		aCtSwitchExpression.setType(clone(switchExpression.getType()));
		aCtSwitchExpression.setTypeCasts(clone(switchExpression.getTypeCasts()));
		this.cloneHelper.tailor(switchExpression, aCtSwitchExpression);
		this.other = aCtSwitchExpression;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtSynchronized(final spoon.reflect.code.CtSynchronized synchro) {
		spoon.reflect.code.CtSynchronized aCtSynchronized = synchro.getFactory().Core().createSynchronized();
		this.builder.copy(synchro, aCtSynchronized);
		aCtSynchronized.setAnnotations(clone(synchro.getAnnotations()));
		aCtSynchronized.setExpression(clone(synchro.getExpression()));
		aCtSynchronized.setBlock(clone(synchro.getBlock()));
		aCtSynchronized.setComments(clone(synchro.getComments()));
		this.cloneHelper.tailor(synchro, aCtSynchronized);
		this.other = aCtSynchronized;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtThrow(final spoon.reflect.code.CtThrow throwStatement) {
		spoon.reflect.code.CtThrow aCtThrow = throwStatement.getFactory().Core().createThrow();
		this.builder.copy(throwStatement, aCtThrow);
		aCtThrow.setAnnotations(clone(throwStatement.getAnnotations()));
		aCtThrow.setThrownExpression(clone(throwStatement.getThrownExpression()));
		aCtThrow.setComments(clone(throwStatement.getComments()));
		this.cloneHelper.tailor(throwStatement, aCtThrow);
		this.other = aCtThrow;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtTry(final spoon.reflect.code.CtTry tryBlock) {
		spoon.reflect.code.CtTry aCtTry = tryBlock.getFactory().Core().createTry();
		this.builder.copy(tryBlock, aCtTry);
		aCtTry.setAnnotations(clone(tryBlock.getAnnotations()));
		aCtTry.setBody(clone(tryBlock.getBody()));
		aCtTry.setCatchers(clone(tryBlock.getCatchers()));
		aCtTry.setFinalizer(clone(tryBlock.getFinalizer()));
		aCtTry.setComments(clone(tryBlock.getComments()));
		this.cloneHelper.tailor(tryBlock, aCtTry);
		this.other = aCtTry;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtTryWithResource(final spoon.reflect.code.CtTryWithResource tryWithResource) {
		spoon.reflect.code.CtTryWithResource aCtTryWithResource = tryWithResource.getFactory().Core().createTryWithResource();
		this.builder.copy(tryWithResource, aCtTryWithResource);
		aCtTryWithResource.setAnnotations(clone(tryWithResource.getAnnotations()));
		aCtTryWithResource.setResources(clone(tryWithResource.getResources()));
		aCtTryWithResource.setBody(clone(tryWithResource.getBody()));
		aCtTryWithResource.setCatchers(clone(tryWithResource.getCatchers()));
		aCtTryWithResource.setFinalizer(clone(tryWithResource.getFinalizer()));
		aCtTryWithResource.setComments(clone(tryWithResource.getComments()));
		this.cloneHelper.tailor(tryWithResource, aCtTryWithResource);
		this.other = aCtTryWithResource;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtTypeParameterReference(final spoon.reflect.reference.CtTypeParameterReference ref) {
		spoon.reflect.reference.CtTypeParameterReference aCtTypeParameterReference = ref.getFactory().Core().createTypeParameterReference();
		this.builder.copy(ref, aCtTypeParameterReference);
		aCtTypeParameterReference.setPackage(clone(ref.getPackage()));
		aCtTypeParameterReference.setDeclaringType(clone(ref.getDeclaringType()));
		aCtTypeParameterReference.setAnnotations(clone(ref.getAnnotations()));
		this.cloneHelper.tailor(ref, aCtTypeParameterReference);
		this.other = aCtTypeParameterReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference) {
		spoon.reflect.reference.CtWildcardReference aCtWildcardReference = wildcardReference.getFactory().Core().createWildcardReference();
		this.builder.copy(wildcardReference, aCtWildcardReference);
		aCtWildcardReference.setPackage(clone(wildcardReference.getPackage()));
		aCtWildcardReference.setDeclaringType(clone(wildcardReference.getDeclaringType()));
		aCtWildcardReference.setAnnotations(clone(wildcardReference.getAnnotations()));
		aCtWildcardReference.setBoundingType(clone(wildcardReference.getBoundingType()));
		this.cloneHelper.tailor(wildcardReference, aCtWildcardReference);
		this.other = aCtWildcardReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtIntersectionTypeReference(final spoon.reflect.reference.CtIntersectionTypeReference<T> reference) {
		spoon.reflect.reference.CtIntersectionTypeReference<T> aCtIntersectionTypeReference = reference.getFactory().Core().createIntersectionTypeReference();
		this.builder.copy(reference, aCtIntersectionTypeReference);
		aCtIntersectionTypeReference.setPackage(clone(reference.getPackage()));
		aCtIntersectionTypeReference.setDeclaringType(clone(reference.getDeclaringType()));
		aCtIntersectionTypeReference.setActualTypeArguments(clone(reference.getActualTypeArguments()));
		aCtIntersectionTypeReference.setAnnotations(clone(reference.getAnnotations()));
		aCtIntersectionTypeReference.setBounds(clone(reference.getBounds()));
		this.cloneHelper.tailor(reference, aCtIntersectionTypeReference);
		this.other = aCtIntersectionTypeReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtTypeReference(final spoon.reflect.reference.CtTypeReference<T> reference) {
		spoon.reflect.reference.CtTypeReference<T> aCtTypeReference = reference.getFactory().Core().createTypeReference();
		this.builder.copy(reference, aCtTypeReference);
		aCtTypeReference.setPackage(clone(reference.getPackage()));
		aCtTypeReference.setDeclaringType(clone(reference.getDeclaringType()));
		aCtTypeReference.setActualTypeArguments(clone(reference.getActualTypeArguments()));
		aCtTypeReference.setAnnotations(clone(reference.getAnnotations()));
		aCtTypeReference.setComments(clone(reference.getComments()));
		this.cloneHelper.tailor(reference, aCtTypeReference);
		this.other = aCtTypeReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtTypeAccess(final spoon.reflect.code.CtTypeAccess<T> typeAccess) {
		spoon.reflect.code.CtTypeAccess<T> aCtTypeAccess = typeAccess.getFactory().Core().createTypeAccess();
		this.builder.copy(typeAccess, aCtTypeAccess);
		aCtTypeAccess.setAnnotations(clone(typeAccess.getAnnotations()));
		aCtTypeAccess.setTypeCasts(clone(typeAccess.getTypeCasts()));
		aCtTypeAccess.setAccessedType(clone(typeAccess.getAccessedType()));
		aCtTypeAccess.setComments(clone(typeAccess.getComments()));
		this.cloneHelper.tailor(typeAccess, aCtTypeAccess);
		this.other = aCtTypeAccess;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtUnaryOperator(final spoon.reflect.code.CtUnaryOperator<T> operator) {
		spoon.reflect.code.CtUnaryOperator<T> aCtUnaryOperator = operator.getFactory().Core().createUnaryOperator();
		this.builder.copy(operator, aCtUnaryOperator);
		aCtUnaryOperator.setAnnotations(clone(operator.getAnnotations()));
		aCtUnaryOperator.setType(clone(operator.getType()));
		aCtUnaryOperator.setTypeCasts(clone(operator.getTypeCasts()));
		aCtUnaryOperator.setOperand(clone(operator.getOperand()));
		aCtUnaryOperator.setComments(clone(operator.getComments()));
		this.cloneHelper.tailor(operator, aCtUnaryOperator);
		this.other = aCtUnaryOperator;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtVariableRead(final spoon.reflect.code.CtVariableRead<T> variableRead) {
		spoon.reflect.code.CtVariableRead<T> aCtVariableRead = variableRead.getFactory().Core().createVariableRead();
		this.builder.copy(variableRead, aCtVariableRead);
		aCtVariableRead.setAnnotations(clone(variableRead.getAnnotations()));
		aCtVariableRead.setTypeCasts(clone(variableRead.getTypeCasts()));
		aCtVariableRead.setVariable(clone(variableRead.getVariable()));
		aCtVariableRead.setComments(clone(variableRead.getComments()));
		this.cloneHelper.tailor(variableRead, aCtVariableRead);
		this.other = aCtVariableRead;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtVariableWrite(final spoon.reflect.code.CtVariableWrite<T> variableWrite) {
		spoon.reflect.code.CtVariableWrite<T> aCtVariableWrite = variableWrite.getFactory().Core().createVariableWrite();
		this.builder.copy(variableWrite, aCtVariableWrite);
		aCtVariableWrite.setAnnotations(clone(variableWrite.getAnnotations()));
		aCtVariableWrite.setTypeCasts(clone(variableWrite.getTypeCasts()));
		aCtVariableWrite.setVariable(clone(variableWrite.getVariable()));
		aCtVariableWrite.setComments(clone(variableWrite.getComments()));
		this.cloneHelper.tailor(variableWrite, aCtVariableWrite);
		this.other = aCtVariableWrite;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtWhile(final spoon.reflect.code.CtWhile whileLoop) {
		spoon.reflect.code.CtWhile aCtWhile = whileLoop.getFactory().Core().createWhile();
		this.builder.copy(whileLoop, aCtWhile);
		aCtWhile.setAnnotations(clone(whileLoop.getAnnotations()));
		aCtWhile.setLoopingExpression(clone(whileLoop.getLoopingExpression()));
		aCtWhile.setBody(clone(whileLoop.getBody()));
		aCtWhile.setComments(clone(whileLoop.getComments()));
		this.cloneHelper.tailor(whileLoop, aCtWhile);
		this.other = aCtWhile;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtCodeSnippetExpression(final spoon.reflect.code.CtCodeSnippetExpression<T> expression) {
		spoon.reflect.code.CtCodeSnippetExpression<T> aCtCodeSnippetExpression = expression.getFactory().Core().createCodeSnippetExpression();
		this.builder.copy(expression, aCtCodeSnippetExpression);
		aCtCodeSnippetExpression.setType(clone(expression.getType()));
		aCtCodeSnippetExpression.setComments(clone(expression.getComments()));
		aCtCodeSnippetExpression.setAnnotations(clone(expression.getAnnotations()));
		aCtCodeSnippetExpression.setTypeCasts(clone(expression.getTypeCasts()));
		this.cloneHelper.tailor(expression, aCtCodeSnippetExpression);
		this.other = aCtCodeSnippetExpression;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public void visitCtCodeSnippetStatement(final spoon.reflect.code.CtCodeSnippetStatement statement) {
		spoon.reflect.code.CtCodeSnippetStatement aCtCodeSnippetStatement = statement.getFactory().Core().createCodeSnippetStatement();
		this.builder.copy(statement, aCtCodeSnippetStatement);
		aCtCodeSnippetStatement.setComments(clone(statement.getComments()));
		aCtCodeSnippetStatement.setAnnotations(clone(statement.getAnnotations()));
		this.cloneHelper.tailor(statement, aCtCodeSnippetStatement);
		this.other = aCtCodeSnippetStatement;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	public <T> void visitCtUnboundVariableReference(final spoon.reflect.reference.CtUnboundVariableReference<T> reference) {
		spoon.reflect.reference.CtUnboundVariableReference<T> aCtUnboundVariableReference = reference.getFactory().Core().createUnboundVariableReference();
		this.builder.copy(reference, aCtUnboundVariableReference);
		aCtUnboundVariableReference.setType(clone(reference.getType()));
		this.cloneHelper.tailor(reference, aCtUnboundVariableReference);
		this.other = aCtUnboundVariableReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtFieldRead(final spoon.reflect.code.CtFieldRead<T> fieldRead) {
		spoon.reflect.code.CtFieldRead<T> aCtFieldRead = fieldRead.getFactory().Core().createFieldRead();
		this.builder.copy(fieldRead, aCtFieldRead);
		aCtFieldRead.setAnnotations(clone(fieldRead.getAnnotations()));
		aCtFieldRead.setTypeCasts(clone(fieldRead.getTypeCasts()));
		aCtFieldRead.setTarget(clone(fieldRead.getTarget()));
		aCtFieldRead.setVariable(clone(fieldRead.getVariable()));
		aCtFieldRead.setComments(clone(fieldRead.getComments()));
		this.cloneHelper.tailor(fieldRead, aCtFieldRead);
		this.other = aCtFieldRead;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtFieldWrite(final spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
		spoon.reflect.code.CtFieldWrite<T> aCtFieldWrite = fieldWrite.getFactory().Core().createFieldWrite();
		this.builder.copy(fieldWrite, aCtFieldWrite);
		aCtFieldWrite.setAnnotations(clone(fieldWrite.getAnnotations()));
		aCtFieldWrite.setTypeCasts(clone(fieldWrite.getTypeCasts()));
		aCtFieldWrite.setTarget(clone(fieldWrite.getTarget()));
		aCtFieldWrite.setVariable(clone(fieldWrite.getVariable()));
		aCtFieldWrite.setComments(clone(fieldWrite.getComments()));
		this.cloneHelper.tailor(fieldWrite, aCtFieldWrite);
		this.other = aCtFieldWrite;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public <T> void visitCtSuperAccess(final spoon.reflect.code.CtSuperAccess<T> f) {
		spoon.reflect.code.CtSuperAccess<T> aCtSuperAccess = f.getFactory().Core().createSuperAccess();
		this.builder.copy(f, aCtSuperAccess);
		aCtSuperAccess.setComments(clone(f.getComments()));
		aCtSuperAccess.setAnnotations(clone(f.getAnnotations()));
		aCtSuperAccess.setTypeCasts(clone(f.getTypeCasts()));
		aCtSuperAccess.setTarget(clone(f.getTarget()));
		aCtSuperAccess.setVariable(clone(f.getVariable()));
		this.cloneHelper.tailor(f, aCtSuperAccess);
		this.other = aCtSuperAccess;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtComment(final spoon.reflect.code.CtComment comment) {
		spoon.reflect.code.CtComment aCtComment = comment.getFactory().Core().createComment();
		this.builder.copy(comment, aCtComment);
		aCtComment.setComments(clone(comment.getComments()));
		aCtComment.setAnnotations(clone(comment.getAnnotations()));
		this.cloneHelper.tailor(comment, aCtComment);
		this.other = aCtComment;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtJavaDoc(final spoon.reflect.code.CtJavaDoc javaDoc) {
		spoon.reflect.code.CtJavaDoc aCtJavaDoc = javaDoc.getFactory().Core().createJavaDoc();
		this.builder.copy(javaDoc, aCtJavaDoc);
		aCtJavaDoc.setComments(clone(javaDoc.getComments()));
		aCtJavaDoc.setAnnotations(clone(javaDoc.getAnnotations()));
		aCtJavaDoc.setTags(clone(javaDoc.getTags()));
		this.cloneHelper.tailor(javaDoc, aCtJavaDoc);
		this.other = aCtJavaDoc;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtJavaDocTag(final spoon.reflect.code.CtJavaDocTag docTag) {
		spoon.reflect.code.CtJavaDocTag aCtJavaDocTag = docTag.getFactory().Core().createJavaDocTag();
		this.builder.copy(docTag, aCtJavaDocTag);
		aCtJavaDocTag.setComments(clone(docTag.getComments()));
		aCtJavaDocTag.setAnnotations(clone(docTag.getAnnotations()));
		this.cloneHelper.tailor(docTag, aCtJavaDocTag);
		this.other = aCtJavaDocTag;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtImport(final spoon.reflect.declaration.CtImport ctImport) {
		spoon.reflect.declaration.CtImport aCtImport = ctImport.getFactory().Core().createImport();
		this.builder.copy(ctImport, aCtImport);
		aCtImport.setReference(clone(ctImport.getReference()));
		aCtImport.setAnnotations(clone(ctImport.getAnnotations()));
		aCtImport.setComments(clone(ctImport.getComments()));
		this.cloneHelper.tailor(ctImport, aCtImport);
		this.other = aCtImport;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtModule(spoon.reflect.declaration.CtModule module) {
		spoon.reflect.declaration.CtModule aCtModule = module.getFactory().Core().createModule();
		this.builder.copy(module, aCtModule);
		aCtModule.setComments(clone(module.getComments()));
		aCtModule.setAnnotations(clone(module.getAnnotations()));
		aCtModule.setModuleDirectives(clone(module.getModuleDirectives()));
		aCtModule.setRootPackage(clone(module.getRootPackage()));
		this.cloneHelper.tailor(module, aCtModule);
		this.other = aCtModule;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference) {
		spoon.reflect.reference.CtModuleReference aCtModuleReference = moduleReference.getFactory().Core().createModuleReference();
		this.builder.copy(moduleReference, aCtModuleReference);
		aCtModuleReference.setAnnotations(clone(moduleReference.getAnnotations()));
		this.cloneHelper.tailor(moduleReference, aCtModuleReference);
		this.other = aCtModuleReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport) {
		spoon.reflect.declaration.CtPackageExport aCtPackageExport = moduleExport.getFactory().Core().createPackageExport();
		this.builder.copy(moduleExport, aCtPackageExport);
		aCtPackageExport.setComments(clone(moduleExport.getComments()));
		aCtPackageExport.setPackageReference(clone(moduleExport.getPackageReference()));
		aCtPackageExport.setTargetExport(clone(moduleExport.getTargetExport()));
		aCtPackageExport.setAnnotations(clone(moduleExport.getAnnotations()));
		this.cloneHelper.tailor(moduleExport, aCtPackageExport);
		this.other = aCtPackageExport;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement) {
		spoon.reflect.declaration.CtModuleRequirement aCtModuleRequirement = moduleRequirement.getFactory().Core().createModuleRequirement();
		this.builder.copy(moduleRequirement, aCtModuleRequirement);
		aCtModuleRequirement.setComments(clone(moduleRequirement.getComments()));
		aCtModuleRequirement.setModuleReference(clone(moduleRequirement.getModuleReference()));
		aCtModuleRequirement.setAnnotations(clone(moduleRequirement.getAnnotations()));
		this.cloneHelper.tailor(moduleRequirement, aCtModuleRequirement);
		this.other = aCtModuleRequirement;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService) {
		spoon.reflect.declaration.CtProvidedService aCtProvidedService = moduleProvidedService.getFactory().Core().createProvidedService();
		this.builder.copy(moduleProvidedService, aCtProvidedService);
		aCtProvidedService.setComments(clone(moduleProvidedService.getComments()));
		aCtProvidedService.setServiceType(clone(moduleProvidedService.getServiceType()));
		aCtProvidedService.setImplementationTypes(clone(moduleProvidedService.getImplementationTypes()));
		aCtProvidedService.setAnnotations(clone(moduleProvidedService.getAnnotations()));
		this.cloneHelper.tailor(moduleProvidedService, aCtProvidedService);
		this.other = aCtProvidedService;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService) {
		spoon.reflect.declaration.CtUsedService aCtUsedService = usedService.getFactory().Core().createUsedService();
		this.builder.copy(usedService, aCtUsedService);
		aCtUsedService.setComments(clone(usedService.getComments()));
		aCtUsedService.setServiceType(clone(usedService.getServiceType()));
		aCtUsedService.setAnnotations(clone(usedService.getAnnotations()));
		this.cloneHelper.tailor(usedService, aCtUsedService);
		this.other = aCtUsedService;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtCompilationUnit(spoon.reflect.declaration.CtCompilationUnit compilationUnit) {
		spoon.reflect.declaration.CtCompilationUnit aCtCompilationUnit = compilationUnit.getFactory().Core().createCompilationUnit();
		this.builder.copy(compilationUnit, aCtCompilationUnit);
		aCtCompilationUnit.setComments(clone(compilationUnit.getComments()));
		aCtCompilationUnit.setAnnotations(clone(compilationUnit.getAnnotations()));
		aCtCompilationUnit.setPackageDeclaration(clone(compilationUnit.getPackageDeclaration()));
		aCtCompilationUnit.setImports(clone(compilationUnit.getImports()));
		aCtCompilationUnit.setDeclaredModuleReference(clone(compilationUnit.getDeclaredModuleReference()));
		aCtCompilationUnit.setDeclaredTypeReferences(clone(compilationUnit.getDeclaredTypeReferences()));
		this.cloneHelper.tailor(compilationUnit, aCtCompilationUnit);
		this.other = aCtCompilationUnit;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtPackageDeclaration(spoon.reflect.declaration.CtPackageDeclaration packageDeclaration) {
		spoon.reflect.declaration.CtPackageDeclaration aCtPackageDeclaration = packageDeclaration.getFactory().Core().createPackageDeclaration();
		this.builder.copy(packageDeclaration, aCtPackageDeclaration);
		aCtPackageDeclaration.setComments(clone(packageDeclaration.getComments()));
		aCtPackageDeclaration.setAnnotations(clone(packageDeclaration.getAnnotations()));
		aCtPackageDeclaration.setReference(clone(packageDeclaration.getReference()));
		this.cloneHelper.tailor(packageDeclaration, aCtPackageDeclaration);
		this.other = aCtPackageDeclaration;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtTypeMemberWildcardImportReference(spoon.reflect.reference.CtTypeMemberWildcardImportReference wildcardReference) {
		spoon.reflect.reference.CtTypeMemberWildcardImportReference aCtTypeMemberWildcardImportReference = wildcardReference.getFactory().Core().createTypeMemberWildcardImportReference();
		this.builder.copy(wildcardReference, aCtTypeMemberWildcardImportReference);
		aCtTypeMemberWildcardImportReference.setTypeReference(clone(wildcardReference.getTypeReference()));
		this.cloneHelper.tailor(wildcardReference, aCtTypeMemberWildcardImportReference);
		this.other = aCtTypeMemberWildcardImportReference;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtYieldStatement(spoon.reflect.code.CtYieldStatement statement) {
		spoon.reflect.code.CtYieldStatement aCtYieldStatement = statement.getFactory().Core().createYieldStatement();
		this.builder.copy(statement, aCtYieldStatement);
		aCtYieldStatement.setAnnotations(clone(statement.getAnnotations()));
		aCtYieldStatement.setExpression(clone(statement.getExpression()));
		aCtYieldStatement.setComments(clone(statement.getComments()));
		this.cloneHelper.tailor(statement, aCtYieldStatement);
		this.other = aCtYieldStatement;
	}

	// auto-generated, see spoon.generating.CloneVisitorGenerator
	@java.lang.Override
	public void visitCtTypePattern(spoon.reflect.code.CtTypePattern pattern) {
		spoon.reflect.code.CtTypePattern aCtTypePattern = pattern.getFactory().Core().createTypePattern();
		this.builder.copy(pattern, aCtTypePattern);
		aCtTypePattern.setVariable(clone(pattern.getVariable()));
		aCtTypePattern.setAnnotations(clone(pattern.getAnnotations()));
		aCtTypePattern.setType(clone(pattern.getType()));
		aCtTypePattern.setComments(clone(pattern.getComments()));
		this.cloneHelper.tailor(pattern, aCtTypePattern);
		this.other = aCtTypePattern;
	}
}