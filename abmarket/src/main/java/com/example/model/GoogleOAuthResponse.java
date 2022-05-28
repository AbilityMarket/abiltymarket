package com.example.model;

import lombok.Data;

@Data
public class GoogleOAuthResponse {

    // 응답 받을 모델
	private String accessToken;
	private String expiresIn;
	private String refreshToken;
	private String scope;
	private String tokenType;
	private String idToken;

}
