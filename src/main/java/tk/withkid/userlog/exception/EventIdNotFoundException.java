package tk.withkid.userlog.exception;

public class EventIdNotFoundException extends Exception {
    public EventIdNotFoundException() {
        super("이벤트 아이디를 발견하지 못했습니다.");
    }
}
