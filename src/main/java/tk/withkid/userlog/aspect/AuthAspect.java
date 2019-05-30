package tk.withkid.userlog.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import tk.withkid.userlog.exception.AuthorizationUnavailableException;

@Aspect
@Component
public class AuthAspect {

    @Around("execution(public Long tk.withkid.userlog.service.AuthService.getUserId(*))")
    public Object throwAuthException(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        if (result.equals(-1L)) {
            throw new AuthorizationUnavailableException("인증에 실패했습니다.");
        }
        return result;
    }
}
