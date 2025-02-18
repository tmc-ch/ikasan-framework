/*
 * $Id$
 * $URL$
 *
 * ====================================================================
 * Ikasan Enterprise Integration Platform
 *
 * Distributed under the Modified BSD License.
 * Copyright notice: The copyright for this software and a full listing
 * of individual contributors are as shown in the packaged copyright.txt
 * file.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.ikasan.component.endpoint.jms.producer;

import jakarta.jms.Message;
import org.ikasan.spec.event.ManagedRelatedEventIdentifierService;
import org.ikasan.spec.flow.FlowEvent;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for JmsPostProcessor.
 *
 * @author Ikasan Development Team
 */
public class JmsPostProcessorTest
{
    private Mockery mockery = new Mockery()
    {{
        setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
        setThreadingPolicy(new Synchroniser());
    }};

    private final FlowEvent<?, ?> flowEvent = mockery.mock(FlowEvent.class);
    private final Message message = mockery.mock(Message.class);
    private final ManagedRelatedEventIdentifierService<String, Message> managedRelatedEventIdentifierService = mockery.mock(ManagedRelatedEventIdentifierService.class);

    private JmsPostProcessor jmsPostProcessor = new JmsPostProcessor();

    @Before
    public void setup()
    {
        jmsPostProcessor.setManagedEventIdentifierService(managedRelatedEventIdentifierService);
    }

    @Test
    public void test_when_flowEvent()
    {
        mockery.checking(new Expectations()
        {{
            oneOf(flowEvent).getIdentifier();
            will(returnValue("id"));
            oneOf(flowEvent).getRelatedIdentifier();
            will(returnValue("relatedId"));
            oneOf(managedRelatedEventIdentifierService).setEventIdentifier("id", message);
            oneOf(managedRelatedEventIdentifierService).setRelatedEventIdentifier("relatedId", message);

        }});
        jmsPostProcessor.invoke(flowEvent, message);

        mockery.assertIsSatisfied();

    }


}
