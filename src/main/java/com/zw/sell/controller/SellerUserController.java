package com.zw.sell.controller;

import com.zw.sell.constant.CookieConstant;
import com.zw.sell.constant.RedisConstant;
import com.zw.sell.entity.SellerInfo;
import com.zw.sell.enums.ResultEnum;
import com.zw.sell.service.SellerService;
import com.zw.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                      HttpServletResponse response,
                      Map<String,Object> map){
        // valid the openid in database
        SellerInfo sellerInfo = sellerService.findOneByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }

        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;

        // set token to redis
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PEFIX, token),openid, expire, TimeUnit.SECONDS);

        // set token to cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token, CookieConstant.EXPIRE);

        return new ModelAndView("redirect:" + "/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map){
        // check from cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null){

            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PEFIX,cookie.getValue()));

            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
        // remove from redis
        // clear from cookie
        //TODO
    }
}
