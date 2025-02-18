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
package org.ikasan.component.endpoint.consumer;

import org.ikasan.component.endpoint.consumer.api.spec.Endpoint;
import org.ikasan.spec.event.*;
import org.ikasan.spec.management.ManagedIdentifierService;

import java.util.concurrent.ExecutorService;

/**
 * This consumer implementation provides a simple event generator to provision testing of flows quickly and easily.
 * 
 * @author Ikasan Development Team
 */
public class EventGeneratingConsumer<T> extends RunnableThreadConsumer<T>
        implements MessageListener<T>, ExceptionListener<Throwable>, ManagedIdentifierService<ManagedEventIdentifierService>
{
    ManagedEventIdentifierService<?,T> managedEventIdentifierService = new SimpleManagedEventIdentifierService();

    /**
     * Constructor
     * @param techEndpoint
     */
    public EventGeneratingConsumer(ExecutorService executorService, Endpoint techEndpoint)
    {
        super(executorService, techEndpoint);
    }

    @Override
    public void onResubmission(T message)
    {
        Resubmission resubmission = this.resubmissionEventFactory.newResubmissionEvent(
                flowEventFactory.newEvent(managedEventIdentifierService.getEventIdentifier(message), message) );
        this.eventListener.invoke(resubmission);
    }

    @Override
    public void onMessage(T message)
    {
        eventListener.invoke( flowEventFactory.newEvent(managedEventIdentifierService.getEventIdentifier(message), message) );
    }

    @Override
    public void setManagedIdentifierService(ManagedEventIdentifierService managedEventIdentifierService)
    {
        this.managedEventIdentifierService = managedEventIdentifierService;
    }

    class SimpleManagedEventIdentifierService implements ManagedEventIdentifierService<String,T>
    {

        @Override
        public void setEventIdentifier(String string, T t) throws ManagedEventIdentifierException
        {
            // nothing to do here
        }

        @Override
        public String getEventIdentifier(T t) throws ManagedEventIdentifierException
        {
            return String.valueOf(t.hashCode());
        }
    }
}
