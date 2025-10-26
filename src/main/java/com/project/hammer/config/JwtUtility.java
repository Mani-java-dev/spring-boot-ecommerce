package com.project.hammer.config;


import com.project.hammer.entity.Users;
import com.project.hammer.model.LoginModel;
import com.project.hammer.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.prefs.BackingStoreException;

@Component
public class JwtUtility{

    private static final String secret="this1is2secret3833638878988888888888888888888888888";

    @Autowired
    UserRepo repo;


    @Autowired
    private RequestedUserInfo requestedUserInfo;

    public Map<String,String> generateJWt(LoginModel value)
    {
        Map<String, Object> claims = new HashMap<>();
        Users users= repo.findByGmail(value.getGmail());
        claims.put("user",users.getName());
        claims.put("role",users.getRole());
        String key=Jwts.builder()
                .setClaims(claims)
                .setSubject(value.getGmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

        Map<String,String> res=new HashMap<String,String>();
        res.put("accessToken",key);
        return res;
    }


    public void verifyUser(String authorization)throws Exception
    {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
        }catch(Exception e){
            throw new BackingStoreException(e.getMessage());
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, String usernames) {
        Claims claims=extractAllClaims(token);
        requestedUserInfo.setUserName(claims.get("user").toString());
        requestedUserInfo.setGmailAddress(claims.getSubject());
        requestedUserInfo.setRole("ROLE_"+claims.get("role").toString().toUpperCase());
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
