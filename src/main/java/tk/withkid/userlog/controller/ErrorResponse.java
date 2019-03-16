package tk.withkid.userlog.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
class ErrorResponse {
	private String name;
	private String msg;
	private HttpStatus status;
}
