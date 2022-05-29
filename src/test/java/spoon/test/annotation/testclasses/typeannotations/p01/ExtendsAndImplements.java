package spoon.test.annotation.testclasses.typeannotations.p01;

import jdk.jfr.Event;
import spoon.test.annotation.testclasses.typeannotations.TypeUseA;
import spoon.test.annotation.testclasses.typeannotations.TypeUseB;

import java.io.Serializable;

public class ExtendsAndImplements extends @TypeUseA Event implements @TypeUseB Serializable {
}
