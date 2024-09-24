package codriversubscription.infra;

import codriversubscription.config.kafka.KafkaProcessor;
import codriversubscription.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MypageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRequested_then_CREATE_1(@Payload Requested requested) {
        try {
            if (!requested.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setUserId(requested.getUserId());
            mypage.setSubId(requested.getSubId());
            mypage.setSubStatus(requested.getSubStatus());
            mypage.setDate(Date.valueOf(requested.getDate()));
            mypage.setSubProcessed(requested.getSubProcessed());
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCancelled_then_CREATE_2(@Payload Cancelled cancelled) {
        try {
            if (!cancelled.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setUserId(cancelled.getUserId());
            mypage.setSubId(cancelled.getSubId());
            mypage.setSubStatus(cancelled.getSubStatus());
            mypage.setDate(Date.valueOf(cancelled.getDate()));
            mypage.setSubProcessed(cancelled.getSubProcessed());
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRegistered_then_UPDATE_1(@Payload Registered registered) {
        try {
            if (!registered.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findBySubId(
                registered.getSubId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setSubProcessed(registered.getSubProcessed());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUnregistered_then_UPDATE_2(
        @Payload Unregistered unregistered
    ) {
        try {
            if (!unregistered.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findBySubId(
                unregistered.getSubId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setSubProcessed(unregistered.getSubProcessed());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
