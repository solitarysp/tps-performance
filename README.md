# Tps Performance
## Việt Nam
### Kiến trúc
```
\---com
    \---lethanh98
        \---performance
            \---tps
                    \---functional
                        NextCounter
                    GroupTpsCounter.java # Khai báo 1 group counter
                    TpsCounter.java # Nơi khai báo tạo một tps counter
                    Utils.java
                    
```

### Nguyên lý hoạt động
    - Sử dụng schedule để lên lịch. Client sẽ add counter liên tục và khi đến thời gian reset thì sẽ star lại từ 0.
# Test
##### Test Group

```
TpsCounter tpsCounter = new TpsCounter("Tps/5s", 5, TimeUnit.SECONDS);

            GroupTpsCounter tpsCounters = new GroupTpsCounter("TPS MainTest", (name, counter) -> {
                log.info("New Start {} , ola counter {}", name, counter);
            }, tpsCounter, new TpsCounter("Tps/10s", 10, TimeUnit.SECONDS));

            while (true) {
                int number = new Random().nextInt(5000);
                for (int i = 0; i < number; i++) {
                    tpsCounters.addTps();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("TPS: {}", tpsCounters.toStringCounter());
            }
```

```
 TPS: {"counterList":[{"tps":3886,"name":"Tps/5s"},{"tps":3886,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
 TPS: {"counterList":[{"tps":8563,"name":"Tps/5s"},{"tps":8563,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
 TPS: {"counterList":[{"tps":10853,"name":"Tps/5s"},{"tps":10853,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
 TPS: {"counterList":[{"tps":15848,"name":"Tps/5s"},{"tps":15848,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
New Start Tps/5s , ola counter 17174
 TPS: {"counterList":[{"tps":0,"name":"Tps/5s"},{"tps":17174,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
 TPS: {"counterList":[{"tps":4007,"name":"Tps/5s"},{"tps":21181,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
 TPS: {"counterList":[{"tps":7922,"name":"Tps/5s"},{"tps":25096,"name":"Tps/10s"}],"groupName":"TPS MainTest"}
```

##### Test  singleton

```
 TpsCounter tpsCounter = new TpsCounter("Tps/5s", 5, TimeUnit.SECONDS);
            while (true) {
                int number = new Random().nextInt(5000);
                for (int i = 0; i < number; i++) {
                    tpsCounter.addTps();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("TPS: {}", tpsCounter.toString());
            }
```

```
{"tps":4595,"name":"Tps/5s"}
{"tps":5945,"name":"Tps/5s"}
{"tps":10092,"name":"Tps/5s"}
{"tps":14081,"name":"Tps/5s"}
{"tps":0,"name":"Tps/5s"}
{"tps":456,"name":"Tps/5s"}
{"tps":4666,"name":"Tps/5s"}
{"tps":9001,"name":"Tps/5s"}
```