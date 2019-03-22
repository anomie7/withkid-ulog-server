package tk.withkid.userlog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.repository.FIrestoreRepository;
import tk.withkid.userlog.util.DateTimeUtill;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public String saveSearchLog(String accessToken, SearchLog searchLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = DateTimeUtill.nowOfUTC();
        Long userId = this.authService.getUserId(accessToken);
        searchLog.setStorableLog(userId, now);
        String docId = getDocId(now);
        String updateTIme = fIrestoreRepository.saveSearchLog(docId, searchLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }

    public String getDocId(LocalDateTime now){
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;
        return docId;
    }

    public Map<String, String> getMaxSearchLogKeys(String accessToken) {
        HashMap<String, String> maxKeyBunch = new HashMap<>();
        try {
            Long userId = this.authService.getUserId(accessToken);
            List<SearchLog> searchLogs = fIrestoreRepository.findRecentSearchLog(userId);
            Map<String, Long> groupByKindOf = searchLogs.stream().map(SearchLog::getKindOf).filter(Objects::nonNull).filter(s -> !s.equals("전체")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            maxKeyBunch.put("kindOf", getMaxKey(groupByKindOf));
            Map<String, Long> groupByRegion = searchLogs.stream().map(SearchLog::getRegion).filter(Objects::nonNull).filter(s -> !s.equals("전체")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            maxKeyBunch.put("region", getMaxKey(groupByRegion));
        } catch (Exception e) {
            log.error(e.getMessage());
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
        Optional<String> key = Optional.ofNullable(Collections.max(map.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey());
        return key.orElse("전체");
    }

}
