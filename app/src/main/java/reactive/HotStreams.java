package reactive;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class HotStreams {
  public static void main(String... s) {
    ConnectableFlux<Object> publisher =
        Flux.create(objectFluxSink -> {
          while(true) {
            objectFluxSink.next(System.currentTimeMillis());
          }
        })
            .sample(Duration.ofSeconds(2))
            .publish();

    /*publisher.subscribe(new Subscriber<Object>() {
      Subscription s;

      @Override
      public void onSubscribe(Subscription s) {
        this.s = s;
        s.request(1);
      }

      @Override
      public void onNext(Object o) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long)o), ZoneId.systemDefault());
        System.out.println(dateTime);
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        s.request(1);
      }

      @Override
      public void onError(Throwable t) {

      }

      @Override
      public void onComplete() {

      }
    });*/
    publisher.subscribe(System.out::println);

    publisher.connect();
  }
}
