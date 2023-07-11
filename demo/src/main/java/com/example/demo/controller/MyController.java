package com.example.demo.controller;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Handler.ErrorResponse;
import com.example.demo.config.LoginResultPublisher;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtTokenUtils;

@RestController
public class MyController {
    private final UserService userService;

    private final CacheManager cacheManager;

    private final JwtTokenUtils jwtTokenUtils;

    private final RedisTemplate<String, Object> redisTemplate;
    
    private final LoginResultPublisher loginResultPublisher;

    
    @Autowired
    public MyController(UserService userService, CacheManager cacheManager, JwtTokenUtils jwtTokenUtils, RedisTemplate<String, Object> redisTemplate
    		,LoginResultPublisher loginResultPublisher) {
        this.userService = userService;
        this.cacheManager = cacheManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.redisTemplate = redisTemplate;
        this.loginResultPublisher = loginResultPublisher;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getAccount(), loginRequest.getPassword());

        if (user != null) {
            // 檢查使用者是否已經使用存儲在 Redis 中的令牌登入
            if (redisTemplate.hasKey("token:" + user.getId())) {
                String existingToken = (String) redisTemplate.opsForValue().get("token:" + user.getId());

                // 如果沒有傳入令牌，刷新令牌並將其存儲在 Redis 中
                if (loginRequest.getToken() == null || loginRequest.getToken().isEmpty()) {
                    // 生成新的令牌並將其存儲在 Redis 中
                    String token = jwtTokenUtils.createToken(user.getAccount(), "ROLE_USER", true);

                    // 覆蓋 Redis 中的值
                    redisTemplate.opsForValue().set("token:" + user.getId(), token);

                    // 使用新的令牌更新使用者物件
                    user.setToken(token);

                    // 構建成功登入的回應
                    ErrorResponse successResponse = new ErrorResponse(HttpStatus.OK.value(), "登入成功", token);
                    loginResultPublisher.publishLoginResult(user);
                    return ResponseEntity.ok(successResponse);
                }

                // 檢查提供的令牌是否與現有令牌匹配
                if (!existingToken.equals(loginRequest.getToken())) {
                    // 返回錯誤消息的 JSON 響應
                    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "無效的令牌", "令牌不匹配");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
                }

                // 令牌有效，構建成功登入的回應
                ErrorResponse successResponse = new ErrorResponse(HttpStatus.OK.value(), "登入成功", existingToken);
                return ResponseEntity.ok(successResponse);
            } else {
                // 如果沒有傳入令牌，刷新令牌並將其存儲在 Redis 中
                if (loginRequest.getToken() == null || loginRequest.getToken().isEmpty()) {
                    // 生成新的令牌並將其存儲在 Redis 中
                    String token = jwtTokenUtils.createToken(user.getAccount(), "ROLE_USER", true);

                    // 覆蓋 Redis 中的值
                    redisTemplate.opsForValue().set("token:" + user.getAccount(), token);

                    // 使用新的令牌更新使用者物件
                    user.setToken(token);

                    // 構建成功登入的回應
                    ErrorResponse successResponse = new ErrorResponse(HttpStatus.OK.value(), "登入成功", token);
                    return ResponseEntity.ok(successResponse);
                } else {
                    // 返回錯誤消息的 JSON 響應，要求傳入有效的令牌
                    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "無效的令牌", "令牌錯誤或失效");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
                }
            }
        } else {
            // 使用者不存在或使用者名稱和密碼無效，返回未授權狀態
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "無效的使用者名稱或密碼", "登入失敗");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
    
    @GetMapping("/user/account/token")
    public ResponseEntity<?> getAccountFromToken(@RequestParam String token) {
        try {
            String account = jwtTokenUtils.getUsername(token);

            if (account != null) {
                // 構建成功回應，將帳戶資料放入 JSON 物件中
                Map<String, Object> response = new HashMap<>();
                response.put("account", account);

                // 構建成功訊息
                String successMessage = "成功獲取帳戶資料";

                // 構建成功回應
                response.put("message", successMessage);
                response.put("status", HttpStatus.OK.value());

                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "無效的令牌", "無法解析帳戶資料");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "無效的令牌", "解析令牌失敗");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/user/register")
    public int registerUser(@RequestBody User user) {
    	User userContainer = new User();
    	userContainer.setAccount(user.getAccount());
    	userContainer.setPassword(user.getPassword());
    	userContainer.setRole("ROLE_USER");
        return userService.registerUser(userContainer);
    }
    
    //登出功能
    @PostMapping("/user/logout")
    public ResponseEntity<?> logout(@RequestParam String token) {
        try {
            // 從令牌解析出帳戶名稱
            String account = jwtTokenUtils.getUsername(token);

            if (account != null) {
                // 構建存儲在 Redis 中的令牌鍵
                String redisKey = "token:"+account;
                
                System.out.println(redisKey);

                // 檢查令牌是否存在於 Redis 中
                Boolean hasKey = redisTemplate.hasKey(redisKey);
                if (hasKey != null && hasKey) {
                	System.out.println(123);
                    // 從 Redis 中刪除令牌
                    redisTemplate.delete(redisKey);
                }

                // 構建成功登出的回應
                String successMessage = "登出成功";
                Map<String, Object> response = new HashMap<>();
                response.put("message", successMessage);
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "無效的令牌", "無法解析帳戶資料");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "無效的令牌", "解析令牌失敗");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    //測試redis
    @GetMapping("/user/account")
    public String getAccountFromRedis(@RequestParam String account, @RequestParam String password) {
        Cache cache = cacheManager.getCache("users");
        User user = cache.get("user-" + account + "-" + password, User.class);
        System.out.println(user);
        if (user != null) {
            return user.getAccount();
        } else {
            return "User not found in Redis";
        }
    }
}
