# Tps Performance
## Việt Nam
### Kiến trúc
```
\---com
    \---lethanh98
        \---performance
            \---tps
                    GroupTpsCounter.java # Khai báo 1 group counter
                    TpsCounter.java # Nơi khai báo tạo một tps counter
                    Utils.java
```

### Nguyên lý hoạt động
    - Sử dụng schedule để lên lịch. Client sẽ add counter liên tục và khi đến thời gian reset thì sẽ star lại từ 0.
