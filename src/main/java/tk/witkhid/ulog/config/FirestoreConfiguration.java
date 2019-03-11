package tk.witkhid.ulog.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.witkhid.ulog.repository.FIrestoreRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class FirestoreConfiguration {

    final String keyPath = "C:\\Users\\전찬동\\Downloads\\test-fe856-21f716968770.json";

    @Bean
    public FIrestoreRepository FIrestoreRepository(){
        try {
            InputStream serviceAccount = null;
            serviceAccount = new FileInputStream(keyPath);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new FIrestoreRepository(FirestoreClient.getFirestore());
    }
}
