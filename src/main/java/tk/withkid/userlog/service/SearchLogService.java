package tk.withkid.userlog.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.repository.FIrestoreRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SearchLogService {
    private FIrestoreRepository fIrestoreRepository;
    private AuthService authService;

    @Autowired
    public SearchLogService(FIrestoreRepository fIrestoreRepository, AuthService authService) {
        this.fIrestoreRepository = fIrestoreRepository;
        this.authService = authService;
    }

    public static String getDocId(LocalDateTime now){
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;
        return docId;
    }

    public String saveSearchLog(String accessToken, SearchLog searchLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Long userId = this.authService.getUserId(accessToken);
        searchLog.setStorableLog(userId, timestamp);

        String docId = getDocId(now);
        String updateTIme = fIrestoreRepository.saveSearchLog(docId, searchLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }

    public Map<String, String> getMaxSearchLogKeys(String accessToken) throws ExecutionException, InterruptedException {
        HashMap<String, String> maxKeyBunch = new HashMap<>();
        Long userId = this.authService.getUserId(accessToken);
        try {
            List<SearchLog> searchLogs = fIrestoreRepository.findRecentSearchLog(userId);
            Map<String, Long> groupByKindOf = searchLogs.stream().map(SearchLog::getKindOf).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            maxKeyBunch.put("kindOf", getMaxKey(groupByKindOf));
            Map<String, Long> groupByRegion = searchLogs.stream().map(SearchLog::getRegion).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            maxKeyBunch.put("region", getMaxKey(groupByRegion));
        } catch (NullPointerException e) {
            if(!maxKeyBunch.containsKey("kindOf")){
                maxKeyBunch.put("kindOf", "전체");
            }

            if(!maxKeyBunch.containsKey("region")){
                maxKeyBunch.put("region", "전체");
            }
        }
            return maxKeyBunch;
    }

    private String getMaxKey(Map<String, Long> map) {
        return Collections.max(map.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }

}
