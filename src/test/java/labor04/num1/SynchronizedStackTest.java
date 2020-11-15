package labor04.num1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SynchronizedStackTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void mustHavePositiveCapacity(int capacity) {
        assertThatThrownBy(() -> new SynchronizedStack<>(capacity));
    }

    @Test
    void popsTheLastElement() throws InterruptedException {
        SynchronizedStack<Integer> stack = new SynchronizedStack<>(2);
        stack.push(0);
        stack.push(1);
        System.out.println(stack);
        assertThat(stack.pop()).isOne();
        assertThat(stack.size()).isOne();
    }

    @Test
    void withoutElementWaitsBeforePopping() throws InterruptedException {
        SynchronizedStack<Integer> stack = new SynchronizedStack<>(1);
        int[] actual = new int[1];
        Thread popper = new Thread(() -> {
            try {
                actual[0] = stack.pop();
            } catch (InterruptedException unexpected) {
            }
        });

        popper.start();
        await().atMost(1, TimeUnit.SECONDS)
                .until(() -> popper.getState() == Thread.State.WAITING);
        stack.push(1);

        await().atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(actual[0]).isOne());
    }

    @Test
    void beingFullWaitsBeforePushing() throws InterruptedException {
        SynchronizedStack<Integer> stack = new SynchronizedStack<>(1);
        stack.push(0);
        Thread pusher = new Thread(() -> {
            try {
                stack.push(1);
            } catch (InterruptedException unexpected) {
            }
        });

        pusher.start();
        await().atMost(ofSeconds(1))
                .until(() -> pusher.getState() == Thread.State.WAITING);
        stack.pop();

        await().atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(stack.pop()).isOne());
    }

    @Test
    void testToString() throws InterruptedException{
        SynchronizedStack<String> stack = new SynchronizedStack<>(3);
        stack.push("one");
        stack.push("two");
        assertEquals("[one, two]", stack.toString());


    }
}