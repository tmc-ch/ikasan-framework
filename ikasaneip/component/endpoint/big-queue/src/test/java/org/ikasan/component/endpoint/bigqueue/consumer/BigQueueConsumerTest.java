package org.ikasan.component.endpoint.bigqueue.consumer;

import com.google.common.util.concurrent.ListenableFuture;
import org.ikasan.bigqueue.BigQueueImpl;
import org.ikasan.component.endpoint.bigqueue.builder.BigQueueMessageBuilder;
import org.ikasan.component.endpoint.bigqueue.consumer.configuration.BigQueueConsumerConfiguration;
import org.ikasan.component.endpoint.bigqueue.message.BigQueueMessageImpl;
import org.ikasan.component.endpoint.bigqueue.serialiser.BigQueueMessageJsonSerialiser;
import org.ikasan.spec.bigqueue.message.BigQueueMessage;
import org.ikasan.spec.event.EventFactory;
import org.ikasan.spec.event.EventListener;
import org.ikasan.spec.event.Resubmission;
import org.ikasan.spec.flow.FlowEvent;
import org.ikasan.spec.resubmission.ResubmissionEventFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.with;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BigQueueConsumerTest {

    @Mock
    private EventFactory<FlowEvent<?,?>> flowEventFactory;
    @Mock
    private ResubmissionEventFactory<Resubmission<?>> resubmissionEventFactory;
    @Mock
    private EventListener eventListener;
    @Mock
    private FlowEvent flowEvent;
    @Mock
    private Resubmission resubmission;

    @Mock
    private TransactionManager transactionManager;

    @Mock
    private Transaction transaction;

    @Mock
    private Xid xid;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_message_consumed_successfully() throws IOException, InterruptedException, XAException, SystemException {
        Mockito.doNothing().when(eventListener).invoke(any(FlowEvent.class));
        when(flowEventFactory.newEvent(anyString(), anyString(), any())).thenReturn(flowEvent);
        when(transactionManager.getTransaction()).thenReturn(transaction);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);
        consumer.setListener(this.eventListener);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        BigQueueMessage bigQueueMessage = new BigQueueMessageBuilder<>().withMessage("test message").build();
        BigQueueMessageJsonSerialiser bigQueueMessageJsonSerialiser = new BigQueueMessageJsonSerialiser();
        bigQueue.enqueue(bigQueueMessageJsonSerialiser.serialise(bigQueueMessage));

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(()
                -> verify(eventListener, times(1)).invoke(any(FlowEvent.class))
            );

        consumer.commit(xid, true);

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> Assert.assertEquals(0, bigQueue.size()));

        consumer.stop();
        Assert.assertFalse(consumer.isRunning());
        Assert.assertEquals(0, bigQueue.size());
    }

    @Test
    public void test_null_message_consumed_successfully() throws IOException, InterruptedException, XAException, SystemException {
        Mockito.doNothing().when(eventListener).invoke(any(FlowEvent.class));
        when(flowEventFactory.newEvent(anyString(), anyString(), any())).thenReturn(flowEvent);
        when(transactionManager.getTransaction()).thenReturn(transaction);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);
        consumer.setListener(this.eventListener);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> Assert.assertEquals(0, bigQueue.size()));

        ListenableFuture future = (ListenableFuture)ReflectionTestUtils.getField(consumer, "listenableFuture");
        Assert.assertNotNull(future);
        Assert.assertFalse(future.isDone());

        consumer.stop();
        Assert.assertFalse(consumer.isRunning());
        Assert.assertEquals(0, bigQueue.size());
    }

    @Test
    public void test_exception_invoke() throws IOException, InterruptedException, XAException, SystemException {
        doThrow(new RuntimeException("test exception")).when(eventListener).invoke(any(FlowEvent.class));
        when(flowEventFactory.newEvent(anyString(), anyString(), any())).thenReturn(flowEvent);
        when(transactionManager.getTransaction()).thenReturn(transaction);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        BigQueueConsumerConfiguration configuration = new BigQueueConsumerConfiguration();
        configuration.setPutErrorsToBackOfQueue(true);
        consumer.setConfiguration(configuration);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);
        consumer.setListener(this.eventListener);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        BigQueueMessage bigQueueMessage = new BigQueueMessageBuilder<>().withMessage("test message").build();
        BigQueueMessageJsonSerialiser bigQueueMessageJsonSerialiser = new BigQueueMessageJsonSerialiser();
        bigQueue.enqueue(bigQueueMessageJsonSerialiser.serialise(bigQueueMessage));

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(()
                -> verify(eventListener, times(1)).invoke(any(FlowEvent.class))
            );

        consumer.rollback(xid);

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> Assert.assertEquals(1, bigQueue.size()));

        consumer.stop();
        Assert.assertFalse(consumer.isRunning());

        verify(eventListener, times(2)).invoke(any(FlowEvent.class));
    }

    @Test
    public void test_exception_invoke_message_moved_to_back_of_queue() throws IOException, InterruptedException, XAException, SystemException {
        doThrow(new RuntimeException("test exception")).when(eventListener).invoke(any(FlowEvent.class));
        when(flowEventFactory.newEvent(anyString(), anyString(), any())).thenReturn(flowEvent);
        when(transactionManager.getTransaction()).thenReturn(transaction);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        BigQueueConsumerConfiguration configuration = new BigQueueConsumerConfiguration();
        configuration.setPutErrorsToBackOfQueue(true);
        consumer.setConfiguration(configuration);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);
        consumer.setListener(this.eventListener);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        BigQueueMessage bigQueueMessage1 = new BigQueueMessageBuilder<>().withMessage("test message 1").build();
        BigQueueMessage bigQueueMessage2 = new BigQueueMessageBuilder<>().withMessage("test message 2").build();
        BigQueueMessageJsonSerialiser bigQueueMessageJsonSerialiser = new BigQueueMessageJsonSerialiser();
        bigQueue.enqueue(bigQueueMessageJsonSerialiser.serialise(bigQueueMessage1));
        bigQueue.enqueue(bigQueueMessageJsonSerialiser.serialise(bigQueueMessage2));

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(()
                -> verify(eventListener, times(1)).invoke(any(FlowEvent.class))
            );

        consumer.rollback(xid);

        with().pollInterval(1, TimeUnit.SECONDS).and().with().pollDelay(1, TimeUnit.SECONDS).await()
            .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> Assert.assertEquals(2, bigQueue.size()));


        consumer.stop();
        Assert.assertFalse(consumer.isRunning());

        byte[] dequeue1 = bigQueue.dequeue();
        byte[] dequeue2 = bigQueue.dequeue();
        BigQueueMessage deserialised1 = bigQueueMessageJsonSerialiser.deserialise(dequeue1);
        Assert.assertEquals("\"test message 2\"", deserialised1.getMessage());
        BigQueueMessage deserialised2 = bigQueueMessageJsonSerialiser.deserialise(dequeue2);
        Assert.assertEquals("\"test message 1\"", deserialised2.getMessage());
    }

    @Test
    public void test_exception_invoke_null_event_listener() throws IOException, InterruptedException {
        doThrow(new RuntimeException("test exception")).when(eventListener).invoke(any(FlowEvent.class));
        when(flowEventFactory.newEvent(anyString(), anyString(), anyString())).thenReturn(flowEvent);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        bigQueue.enqueue("test message".getBytes());

        Thread.sleep(1000);

        consumer.stop();
        Assert.assertFalse(consumer.isRunning());

        Assert.assertEquals(1, bigQueue.size());

        verify(eventListener, times(0)).invoke(any(FlowEvent.class));
        verify(eventListener, times(0)).invoke(any(RuntimeException.class));
    }

    @Test
    public void test_message_resubmitted_successfully() throws IOException, InterruptedException, SystemException {
        Mockito.doNothing().when(eventListener).invoke(any(Resubmission.class));
        when(resubmissionEventFactory.newResubmissionEvent(any())).thenReturn(resubmission);
        when(resubmission.getEvent()).thenReturn(new BigQueueMessageImpl<>());
        when(transactionManager.getTransaction()).thenReturn(transaction);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);
        consumer.setListener(this.eventListener);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        consumer.onResubmission("test message");


        Thread.sleep(1000);

        consumer.stop();
        Assert.assertFalse(consumer.isRunning());

        Assert.assertEquals(0, bigQueue.size());

        verify(eventListener, times(1)).invoke(any(Resubmission.class));
    }

    @Test(expected = RuntimeException.class)
    public void test_exception_resubmitted_invoke_exception() throws IOException, InterruptedException {
        doThrow(new RuntimeException("test exception")).when(eventListener).invoke(any(Resubmission.class));
        when(resubmissionEventFactory.newResubmissionEvent(any())).thenReturn(resubmission);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);
        consumer.setListener(this.eventListener);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        consumer.onResubmission("test message");
    }

    @Test(expected = RuntimeException.class)
    public void test_exception_resubmitted_null_event_listener() throws IOException, InterruptedException {
        doThrow(new RuntimeException("test exception")).when(eventListener).invoke(any(Resubmission.class));
        when(resubmissionEventFactory.newResubmissionEvent(anyString())).thenReturn(resubmission);

        BigQueueImpl bigQueue = new BigQueueImpl("./target", "test");
        bigQueue.removeAll();

        InboundQueueMessageRunner runner = new InboundQueueMessageRunner(bigQueue, new BigQueueMessageJsonSerialiser());
        BigQueueConsumer consumer = new BigQueueConsumer(bigQueue, runner, this.transactionManager);
        runner.setMessageListener(consumer);
        runner.setEndpointListener(consumer);
        consumer.setEventFactory(this.flowEventFactory);
        consumer.setResubmissionEventFactory(this.resubmissionEventFactory);

        consumer.start();
        Assert.assertTrue(consumer.isRunning());

        consumer.onResubmission("test message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_exception_null_big_queue_constructor() {
        new BigQueueConsumer(null
            , null, null);
    }

}
