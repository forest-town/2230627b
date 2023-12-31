/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.model.transform;

import javax.xml.stream.XMLStreamReader;

import org.junit.jupiter.api.Test;

import static org.xmlunit.assertj.XmlAssert.assertThat;

class ModulesXMLFilterTest extends AbstractXMLFilterTests {

    @Override
    protected ModulesXMLFilter getFilter(XMLStreamReader parser) {
        return new ModulesXMLFilter(parser);
    }

    @Test
    void emptyModules() throws Exception {
        String input = "<project><modules/></project>";
        String expected = "<project/>";
        String actual = transform(input);
        assertThat(actual).and(expected).areIdentical();
    }

    @Test
    void setOfModules() throws Exception {
        String input = "<project><modules>" + "<module>ab</module>" + "<module>../cd</module>" + "</modules></project>";
        String expected = "<project/>";
        String actual = transform(input);
        assertThat(actual).and(expected).areIdentical();
    }

    @Test
    void noModules() throws Exception {
        String input = "<project><name>NAME</name></project>";
        String expected = input;
        String actual = transform(input);
        assertThat(actual).and(expected).areIdentical();
    }

    @Test
    void comment() throws Exception {

        String input = "<project><!--before--><modules>"
                + "<!--pre-in-->"
                + "<module><!--in-->ab</module>"
                + "<module>../cd</module>"
                + "<!--post-in-->"
                + "</modules>"
                + "<!--after--></project>";
        String expected = "<project><!--before--><!--after--></project>";
        String actual = transform(input);
        assertThat(actual).and(expected).areIdentical();
    }

    @Test
    void setOfModulesLF() throws Exception {
        String input = "<project>\n"
                + "\n"
                + "  <modules>\n"
                + "    <module>ab</module>\n"
                + "    <module>../cd</module>\n"
                + "  </modules>\n"
                + "\n"
                + "</project>\n";
        String expected = "<project>\n" + "\n" + "  \n" + "\n" + "</project>\n";
        String actual = transform(input);
        assertThat(actual).and(expected).areIdentical();
    }
}
