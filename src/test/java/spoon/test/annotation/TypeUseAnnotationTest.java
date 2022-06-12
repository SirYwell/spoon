package spoon.test.annotation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtCatchVariable;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.testing.utils.ModelTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static spoon.test.SpoonTestHelpers.containsRegexMatch;

/**
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se18/html/jls-4.html#jls-4.11">JLS 4.11</a>
 */
@DisplayName("cover rules for type annotations in places mentioned in JLS 4.11")
class TypeUseAnnotationTest {
	private static final String BASE_PATH = "src/test/java/spoon/test/annotation/testclasses/typeannotations/";
	private static final String TYPE_USE_A_PATH = BASE_PATH + "TypeUseA.java";
	private static final String TYPE_USE_B_PATH = BASE_PATH + "TypeUseB.java";

	@DisplayName("Types are used in most kinds of declaration and in certain kinds of expression. Specifically, there are 17 type contexts where types are used")
	@Nested
	class TypeContexts {
		@DisplayName("In declarations")
		@Nested
		class InDeclarations {

			@DisplayName("1. A type in the extends or implements clause of a class declaration (§8.1.4, §8.1.5)")
			@ModelTest({TYPE_USE_A_PATH, TYPE_USE_B_PATH, BASE_PATH + "p01/"})
			void testTypeAnnotationOnExtendsOrImplements(Factory factory) {
				// contract: type annotations on extends and implements declarations of classes are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p01.ExtendsAndImplements");

				// first, check the annotation of the extends type
				assertThat(type.getSuperclass().getAnnotations().size(), equalTo(1));
				assertThat(type.getSuperclass().getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("extends java\\.lang\\.\\W*@.*TypeUseA\\W+Object"));

				// then, check the annotation of the implements type
				CtTypeReference<?> superInterface = type.getSuperInterfaces().iterator().next();
				assertThat(superInterface.getAnnotations().size(), equalTo(1));
				assertThat(superInterface.getAnnotations().get(0).getType(), equalTo(typeUseBRef(factory)));
				assertThat(type.toString(), containsRegexMatch("implements java\\.lang\\.\\W*@.*TypeUseB\\W+Cloneable"));
			}

			@DisplayName("2. A type in the extends clause of an interface declaration (§9.1.3)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p02/"})
			void testTypeAnnotationsOnInterfaceExtends(Factory factory) {
				// contract: type annotations on extends declarations of interfaces are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p02.InterfaceExtends");

				CtTypeReference<?> superInterface = type.getSuperInterfaces().iterator().next();
				assertThat(superInterface.getAnnotations().size(), equalTo(1));
				assertThat(superInterface.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("extends java\\.lang\\.\\W*@.*TypeUseA\\W+Cloneable"));
			}

			@DisplayName("3. The return type of a method (§8.4.5, §9.4), including the type of an element of an annotation interface (§9.6.1)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p03/"})
			void testTypeAnnotationOnReturnType(Factory factory) {
				// contract: type annotations on return type declarations of methods are part of the model
				for (String suffix : List.of("A", /*"C",*/ "I")) { // TODO print type annotations on type (after modifiers)
					CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p03.OnReturnType" + suffix);

					CtTypeReference<?> returnType = type.getMethods().iterator().next().getType();
					assertThat(returnType.getAnnotations().size(), equalTo(1));
					assertThat(returnType.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
					assertThat(type.toString(), containsRegexMatch("public java\\.lang\\.\\W*@.*TypeUseA\\W+String value"));
				}
			}

			@DisplayName("4. A type in the throws clause of a method or constructor (§8.4.6, §8.8.5, §9.4)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p04/"})
			void testTypeAnnotationOnThrowsType(Factory factory) {
				// contract: type annotations on throws declarations of methods are part of the model

				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p04.OnThrowsTypeC");

				// first, check the case for class methods
				CtTypeReference<?> throwsType = type.getMethods().iterator().next().getThrownTypes().iterator().next();
				assertThat(throwsType.getAnnotations().size(), equalTo(1));
				assertThat(throwsType.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("throws java\\.lang\\.\\W*@.*TypeUseA\\W+Exception"));

				// then, check the case for constructors
				throwsType = ((CtClass<?>) type).getConstructors().iterator().next().getThrownTypes().iterator().next();
				assertThat(throwsType.getAnnotations().size(), equalTo(1));
				assertThat(throwsType.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("throws java\\.lang\\.\\W*@.*TypeUseA\\W+Exception"));

				// then, check the case for interface methods
				type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p04.OnThrowsTypeI");
				throwsType = type.getMethods().iterator().next().getThrownTypes().iterator().next();
				assertThat(throwsType.getAnnotations().size(), equalTo(1));
				assertThat(throwsType.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("throws java\\.lang\\.\\W*@.*TypeUseA\\W+Exception"));
			}

			@DisplayName("5. A type in the extends clause of a type parameter declaration of a generic class, interface, " +
					"method, or constructor (§8.1.2, §9.1.2, §8.4.4, §8.8.4)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p05/"})
			void testTypeAnnotationOnGenericExtends(Factory factory) {
				// contract: type annotations on extends clauses of a type parameter declaration of a
				// generic class, interface, method, or constructor are part of the model
				CtClass<?> classType = (CtClass<?>) factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p05.GenericExtendsC");
				CtType<?> interfaceType = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p05.GenericExtendsI");

				// first, check the case for a generic class
				CtTypeReference<?> bound = classType.getFormalCtTypeParameters().get(0).getSuperclass();
				assertThat(bound.getAnnotations().size(), equalTo(1));
				assertThat(bound.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(classType.toString(), containsRegexMatch("<T extends java\\.lang\\.\\W*@.*TypeUseA\\W+CharSequence>"));

				// then, check the case for a generic interface
				bound = interfaceType.getFormalCtTypeParameters().get(0).getSuperclass();
				assertThat(bound.getAnnotations().size(), equalTo(1));
				assertThat(bound.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(interfaceType.toString(), containsRegexMatch("<T extends java\\.lang\\.\\W*@.*TypeUseA\\W+CharSequence>"));

				// then, check the case for generic methods in classes and interfaces and for constructors
				List<String> typeNames = List.of("M", "M", "C");
				List<String> bounds = List.of("Cloneable", "Cloneable", "Serializable");
				// use var to capture CtExecutable & CtFormalTypeDeclarer
				var executables = List.of(
						classType.getMethods().iterator().next(),
						interfaceType.getMethods().iterator().next(),
						classType.getConstructors().iterator().next());
				for (int j = 0; j < executables.size(); j++) {
					// use var to capture CtExecutable & CtFormalTypeDeclarer
					var executable = executables.get(j);
					bound = executable.getFormalCtTypeParameters().get(0).getSuperclass();
					assertThat(bound.getAnnotations().size(), equalTo(1));
					assertThat(bound.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
					String genericRegex = "<" + typeNames.get(j) + " extends java\\.lang\\.\\W*@.*TypeUseA\\W+" + bounds.get(j) + ">";
					assertThat(executable.getDeclaringType().toString(), containsRegexMatch(genericRegex));
				}
			}

			@DisplayName("6. The type in a field declaration of a class or interface (§8.3, §9.3), including an enum constant (§8.9.1)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p06/"})
			@Disabled
				// TODO annotations are not present on type, printed in the wrong spot
			void testTypeAnnotationOnFieldDeclarations(Factory factory) {
				// contract: type annotations on field declarations are part of the model
				for (String suffix : List.of("C", "E", "I")) {
					CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p06.FieldDecl" + suffix);
					for (CtField<?> field : type.getFields()) {
						assertThat(field.getType().getAnnotations().size(), equalTo(1));
						assertThat(field.getType().getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
						assertThat(type.toString(), containsRegexMatch("java\\.lang\\.\\W*@.*TypeUseA\\W+String " + field.getSimpleName()));
					}
				}
			}

			@DisplayName("7. The type in a formal parameter declaration of a method, constructor, or lambda " +
					"expression (§8.4.1, §8.8.1, §9.4, §15.27.1)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p07/"})
			void testTypeAnnotationsOnFormalParameters(Factory factory) {
				// contract: type annotations on formal parameters are part of the model
				CtClass<?> type = (CtClass<?>) factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p07.FormalParameters");
				List<CtParameter<?>> parameters = List.of(
						type.getMethods().iterator().next().getParameters().get(0),
						type.getConstructors().iterator().next().getParameters().get(0),
						type.getElements(new TypeFilter<CtLambda<?>>(CtLambda.class)).get(0).getParameters().get(0)
				);
				for (CtParameter<?> parameter : parameters) {
					assertThat(parameter.getType().getAnnotations().size(), equalTo(1));
					assertThat(parameter.getType().getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
					assertThat(type.toString(), containsRegexMatch("@.*TypeUseA\\W+int " + parameter.getSimpleName()));
				}
			}

			@DisplayName("8. The type of the receiver parameter of a method (§8.4)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p08/"})
			void testTypeAnnotationsOnReceiverParameter(Factory factory) {
				// contract: type annotations on receiver parameters are part of the model
				CtClass<?> type = (CtClass<?>) factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p08.ReceiverParameter");
				CtParameter<?> parameter = type.getMethods().iterator().next().getParameters().get(0);
				assertThat(parameter.getType().getAnnotations().size(), equalTo(1));
				assertThat(parameter.getType().getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("@.*TypeUseA\\W+ReceiverParameter this"));
			}

			@DisplayName("9. The type in a local variable declaration in either a statement (§14.4.2, §14.14.1, §14.14.2, §14.20.3) " +
					"or a pattern (§14.30.1)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p09/"})
			void testTypeAnnotationsOnLocalVariableDeclarations(Factory factory) {
				// contract: type annotations on local variable declarations are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p09.LocalVariables");
				CtMethod<?> method = type.getMethods().iterator().next();
				for (CtLocalVariable<?> localVariable : method.getElements(new TypeFilter<CtLocalVariable<?>>(CtLocalVariable.class))) {
					assertThat(localVariable.getType().getAnnotations().size(), equalTo(1));
					assertThat(localVariable.getType().getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
					assertThat(type.toString(), containsRegexMatch("final java\\.lang\\.\\W*@.*TypeUseA\\W+String" + localVariable.getSimpleName()));
				}
				// TODO patterns
			}

			@DisplayName("10. A type in an exception parameter declaration (§14.20)")
			@ModelTest({TYPE_USE_A_PATH, TYPE_USE_B_PATH, BASE_PATH + "p10/"})
			@Disabled
			void testTypeAnnotationsOnExceptionParameters(Factory factory) {
				// contract: type annotations on exception parameters are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p10.ExceptionParameters");
				CtMethod<?> multiCatchMethod = type.getMethodsByName("multiCatch").get(0);
				// TODO figure out what happens with the first annotation
				//  - is it part of the parameter? (but TYPE_USE)
				//  - is it part of the first type or of both types?
				CtCatch multiCatch = multiCatchMethod.getElements(new TypeFilter<>(CtCatch.class)).get(0);
				CtCatchVariable<?> multiCatchParameter = multiCatch.getParameter();
				assertThat(multiCatchParameter.getMultiTypes().size(), equalTo(2));
				for (CtTypeReference<?> multiType : multiCatchParameter.getMultiTypes()) {

				}
				CtMethod<?> uniCatchMethod = type.getMethodsByName("uniCatch").get(0);
				CtCatch uniCatch = uniCatchMethod.getElements(new TypeFilter<>(CtCatch.class)).get(0);
				CtCatchVariable<?> uniCatchParameter = uniCatch.getParameter();
				assertThat(uniCatchParameter.getAnnotations().size(), equalTo(1));
				assertThat(uniCatchParameter.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				// TODO fix
				//  assertThat(type.toString(), containsRegexMatch("final @TypeUseA\\W+Exception e"));
			}

			@DisplayName("11. The type in a record component declaration of a record class (§8.10.1)")
			@ModelTest({TYPE_USE_A_PATH, TYPE_USE_B_PATH, BASE_PATH + "p10/"})
			@Disabled("TODO")
			void testTypeAnnotationsOnRecordComponent(Factory factory) {
				// TODO
			}
		}


		@DisplayName("In expressions")
		@Nested
		class InExpressions {

			@DisplayName("12. A type in the explicit type argument list to an explicit constructor invocation statement, " +
					"class instance creation expression, method invocation expression, or method reference expression " +
					"(§8.8.7.1, §15.9, §15.12, §15.13)")
			@ModelTest({TYPE_USE_A_PATH, TYPE_USE_B_PATH, BASE_PATH + "p12/"})
			void testTypeAnnotationInTypeArgumentList(Factory factory) {
				// contract: type annotations in type argument lists are part of the model
				// first, check for explicit constructor invocations
				CtType<?> outer = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p12.ExplicitConstructorInvocation");
				CtType<?> i1 = outer.getNestedType("I1");
				CtType<?> i2 = outer.getNestedType("I2");
				for (CtType<?> type : List.of(i1/*, i2*/)) { // TODO actual type arguments are wrong
					for (CtExecutableReference<?> executable : type.getElements(new TypeFilter<CtExecutableReference<?>>(CtExecutableReference.class))) {
						assertThat(executable.getActualTypeArguments().size(), equalTo(1));
						CtTypeReference<?> typeReference = executable.getActualTypeArguments().get(0);
						assertThat(typeReference.getAnnotations().size(), equalTo(1));
						assertThat(typeReference.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
						String explicit = executable.getType().getSimpleName().equals(executable.getParent(CtClass.class).getSimpleName()) ? "this" : "super";
						assertThat(outer.toString(), containsRegexMatch("<@.*TypeUseA\\W+String>" + explicit));
					}
				}

				// then, check for class instance creations
				CtType<?> classInstanceCreation = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p12.ClassInstanceCreation");
				CtConstructorCall<?> call = classInstanceCreation.getElements(new TypeFilter<>(CtMethod.class)).get(0)
						.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class)).get(0);
				assertThat(call.getActualTypeArguments().size(), equalTo(1));
				assertThat(call.getActualTypeArguments().get(0).getAnnotations().size(), equalTo(1));
				assertThat(call.getActualTypeArguments().get(0).getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(classInstanceCreation.toString(), containsRegexMatch("new <@.*TypeUseA\\W+String>ClassInstanceCreation"));

				// then, check for method invocations
				CtType<?> methodInvocation = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p12.MethodInvocation");
				CtInvocation<?> invocation = methodInvocation.getElements(new TypeFilter<>(CtMethod.class)).get(0)
						.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class)).get(0);
				assertThat(invocation.getActualTypeArguments().size(), equalTo(1));
				assertThat(invocation.getActualTypeArguments().get(0).getAnnotations().size(), equalTo(1));
				assertThat(invocation.getActualTypeArguments().get(0).getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(methodInvocation.toString(), containsRegexMatch("this.<@.*TypeUseA\\W+T>m"));

				//then, check for method references
				CtType<?> methodReference = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p12.MethodReference");
				for (CtField<?> field : methodReference.getFields()) {
					CtExecutableReference<?> reference = field.getDefaultExpression().getElements(new TypeFilter<>(CtExecutableReference.class)).get(0);
					assertThat(reference.getActualTypeArguments().size(), equalTo(1));
					assertThat(reference.getActualTypeArguments().get(0).getAnnotations().size(), equalTo(1));
					assertThat(reference.getActualTypeArguments().get(0).getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				}
				// TODO figure out why String is fq here
				assertThat(methodReference.toString(), containsRegexMatch("MethodReference::<@.*TypeUseA\\W+(java\\.lang\\.)?String>new"));
				assertThat(methodReference.toString(), containsRegexMatch("new N2\\(\\)::<@.*TypeUseA\\W+(java\\.lang\\.)?String>value"));
				assertThat(methodReference.toString(), containsRegexMatch("super::<@.*TypeUseA\\W+(java\\.lang\\.)?String>value"));
			}

			@DisplayName("13. In an unqualified class instance creation expression, as the class type to be instantiated (§15.9) " +
					"or as the direct superclass type or direct superinterface type of an anonymous class to be instantiated (§15.9.5)")
			@ModelTest({TYPE_USE_A_PATH, TYPE_USE_B_PATH, BASE_PATH + "p13/"})
			void testTypeAnnotationOnClassInstanceCreation(Factory factory) {
				// contract: type annotations on class instance creations are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p13.InstanceCreation");
				for (CtField<?> field : type.getFields()) {
					CtConstructorCall<?> call = (CtConstructorCall<?>) field.getDefaultExpression();
					CtTypeReference<?> callType = call.getType();
					assertThat(callType.getAnnotations().size(), equalTo(1));
					CtTypeReference<?> annotationRef = field.getSimpleName().equals("a") ? typeUseARef(factory) : typeUseBRef(factory);
					assertThat(callType.getAnnotations().get(0).getType(), equalTo(annotationRef));
					assertThat(type.toString(), containsRegexMatch("new @.*TypeUse" + field.getSimpleName().toUpperCase() + "\\W+InstanceCreation"));
				}
			}

			@DisplayName("14. The element type in an array creation expression (§15.10.1)")
			@ModelTest({TYPE_USE_A_PATH, TYPE_USE_B_PATH, BASE_PATH + "p14/"})
			void testTypeAnnotationOnArrayCreation(Factory factory) {
				// contract: type annotations on class instance creations are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p14.ArrayCreation");
				for (CtField<?> field : type.getFields()) {
					CtNewArray<?> call = (CtNewArray<?>) field.getDefaultExpression();
					CtTypeReference<?> elementType = ((CtArrayTypeReference<?>) call.getType()).getComponentType();
					assertThat(elementType.getAnnotations().size(), equalTo(1));
					CtTypeReference<?> annotationRef = field.getSimpleName().equals("a") ? typeUseARef(factory) : typeUseBRef(factory);
					assertThat(elementType.getAnnotations().get(0).getType(), equalTo(annotationRef));
					assertThat(type.toString(), containsRegexMatch("new java\\.lang\\.\\W*@.*TypeUse" + field.getSimpleName().toUpperCase() + "\\W+String"));
				}
			}

			@DisplayName("15. The type in the cast operator of a cast expression (§15.16)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p15/"})
			void testTypeAnnotationOnCast(Factory factory) {
				// contract: type annotations on class instance creations are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p15.Cast");
				CtField<?> field = type.getFields().get(0);
				CtExpression<?> defaultExpression = field.getDefaultExpression();
				assertThat(defaultExpression.getTypeCasts().size(), equalTo(1));
				List<CtAnnotation<?>> annotations = defaultExpression.getTypeCasts().get(0).getAnnotations();
				assertThat(annotations.size(), equalTo(1));
				assertThat(annotations.get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("\\(java\\.lang\\.\\W*@.*TypeUseA\\W+String\\)"));
			}

			@DisplayName("16. The type that follows the instanceof type comparison operator (§15.20.2)")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p16/"})
			@Disabled
				// TODO annotation not in the model/type
			void testTypeAnnotationOnInstanceOfType(Factory factory) {
				// contract: type annotations on class instance creations are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p16.InstanceOf");
				CtBinaryOperator<?> operator = type.getElements(new TypeFilter<>(CtBinaryOperator.class)).get(0);
				CtTypeAccess<?> rightHand = (CtTypeAccess<?>) operator.getRightHandOperand();
				CtTypeReference<Void> rightHandType = rightHand.getType();
				assertThat(rightHandType.getAnnotations().size(), equalTo(1));
				assertThat(rightHandType.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
				assertThat(type.toString(), containsRegexMatch("instanceof java\\.lang\\.\\W*@.*TypeUseA\\W+String"));
			}

			@DisplayName("17. In a method reference expression (§15.13), as the reference type to search for a member method " +
					"or as the class type or array type to construct")
			@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "p17/"})
			@Disabled
				// TODO annotation not in the model/type
			void testTypeAnnotationOnMethodReferenceType(Factory factory) {
				// contract: type annotations on class instance creations are part of the model
				CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.p17.MethodReference");
				List<String> methodReferences = List.of("String::hashCode", "String::new", "int[]::new");
				List<CtField<?>> fields = type.getFields();
				for (int i = 0; i < fields.size(); i++) {
					CtTypeReference<?> reference = fields.get(i).getDefaultExpression().getType().getActualTypeArguments().get(0);
					assertThat(reference.getAnnotations().size(), equalTo(1));
					assertThat(reference.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
					assertThat(type.toString(), containsRegexMatch("@.*TypeUseA\\W+" + methodReferences.get(i)));
				}
			}
		}
	}

	@DisplayName("The element type of an array type in any of the above contexts")
	@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "parray/"})
	@Disabled
		// TODO annotation not in the model/dimension
	void testTypeAnnotationOnArrayDimension(Factory factory) {
		// contract: type annotations on array dimensions are part of the model
		CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.parray.Dimension");
		CtTypeReference<?> typeReference = type.getFields().get(0).getType();
		assertThat(typeReference.getAnnotations().size(), equalTo(1));
		assertThat(typeReference.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
		assertThat(type.toString(), containsRegexMatch("private .*String @.*TypeUseA\\W*\\[] a"));
	}

	@DisplayName("The ... in the type of a variable arity parameter (§8.4.1), to indicate an array type")
	@ModelTest({TYPE_USE_A_PATH, BASE_PATH + "pvarargs/"})
	@Disabled
		// TODO annotation not in the model/dimension
	void testTypeAnnotationOnArrayDimensionVarargs(Factory factory) {
		// contract: type annotations on array dimensions are part of the model
		CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.typeannotations.pvarargs.Varargs");
		CtTypeReference<?> typeReference = type.getMethods().iterator().next().getParameters().get(0).getType();
		assertThat(typeReference.getAnnotations().size(), equalTo(1));
		assertThat(typeReference.getAnnotations().get(0).getType(), equalTo(typeUseARef(factory)));
		assertThat(type.toString(), containsRegexMatch("final .*String @.*TypeUseA\\W*\\.\\.\\. a"));
	}

	// TODO parametrized types, bounds of wildcard types

	// TODO just on constructors?

	private CtTypeReference<?> typeUseARef(Factory factory) {
		return factory.Type().get("spoon.test.annotation.testclasses.typeannotations.TypeUseA").getReference();
	}

	private CtTypeReference<?> typeUseBRef(Factory factory) {
		return factory.Type().get("spoon.test.annotation.testclasses.typeannotations.TypeUseB").getReference();
	}
}
