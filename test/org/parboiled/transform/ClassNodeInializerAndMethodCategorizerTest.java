/*
 * Copyright (C) 2009 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parboiled.transform;

import static org.parboiled.test.TestUtils.assertEqualsMultiline;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import java.util.List;

public class ClassNodeInializerAndMethodCategorizerTest {

    @Test
    public void testClassNodeSetup() throws Exception {
        ParserClassNode classNode = new ParserClassNode(TestParser.class);
        new ClassNodeInitializer(
                new MethodCategorizer(null)
        ).transform(classNode);

        assertEquals(classNode.name, "org/parboiled/transform/TestParser$$parboiled");
        assertEquals(classNode.superName, "org/parboiled/transform/TestParser");

        assertEqualsMultiline(join(classNode.constructors), "<init>");
        assertEqualsMultiline(join(classNode.ruleMethods),
                "noActionRule,simpleActionRule,upSetActionRule,booleanExpressionActionRule,complexActionsRule,any,empty");
        assertEqualsMultiline(join(classNode.cachedMethods),
                "ch,charIgnoreCase,charRange,charSet,charSet,string,stringIgnoreCase,firstOf,oneOrMore,optional,sequence,test,testNot,zeroOrMore");
        assertEqualsMultiline(join(classNode.labelMethods),
                "firstOf,oneOrMore,optional,sequence,zeroOrMore");
        assertEqualsMultiline(join(classNode.leafMethods),
                "string,stringIgnoreCase");
    }

    private String join(List<ParserMethod> methods) {
        StringBuilder sb = new StringBuilder();
        for (ParserMethod method : methods) {
            if (sb.length() > 0) sb.append(',');
            sb.append(method.name);
        }
        return sb.toString();
    }

}