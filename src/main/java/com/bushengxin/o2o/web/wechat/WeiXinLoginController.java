package com.bushengxin.o2o.web.wechat;

import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.dto.WechatAuthExecution;
import com.bushengxin.o2o.entity.PersonInfo;
import com.bushengxin.o2o.entity.WechatAuth;
import com.bushengxin.o2o.enums.WechatAuthStateEnum;
import com.bushengxin.o2o.service.PersonInfoService;
import com.bushengxin.o2o.service.ShopAuthMapService;
import com.bushengxin.o2o.service.ShopService;
import com.bushengxin.o2o.service.WechatAuthService;
import com.bushengxin.o2o.util.weixin.WeiXinUser;
import com.bushengxin.o2o.util.weixin.WeiXinUserUtil;
import com.bushengxin.o2o.util.weixin.message.pojo.UserAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * @author lixiang
 *
 */
@Controller
@RequestMapping("wechatlogin")

public class WeiXinLoginController {

    private static Logger log = LoggerFactory.getLogger(WeiXinLoginController.class);

    //Resource和Autowired类似，Resource是JDK提供的，而Autowired是Spring提供的
    @Resource
    private PersonInfoService personInfoService;
    @Resource
    private WechatAuthService wechatAuthService;

    @Resource
    private ShopService shopService;

    @Resource
    private ShopAuthMapService shopAuthMapService;

    private static final String FRONTEND = "1";
    private static final String SHOPEND = "2";

    /*
    * 关注着打开这个连接https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
    * 并同意授权后会请求上面参数中的redirect_uri，即下面这个路由
    * 如果scope=snsapi_base则为静默授权，scope=snsapi_userinfo则为手动授权需要认证才有手动授权功能
    * */
    @RequestMapping(value = "/logincheck", method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        //获取微信公众号传输过来的code，通过code可以获取access_token，进而获取用户信息
        String code = request.getParameter("code");
        //state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatAuth auth = null;
        WeiXinUser user = null;
        String openId = null;
        if (null != code) {
            UserAccessToken token;
            try {
            	//通过code获取access_token
                token = WeiXinUserUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                String accessToken = token.getAccessToken();
                openId = token.getOpenId();
                //通过access_token和openId获取用户昵称等信息
                user = WeiXinUserUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                //有了openId后，可以通过它去数据库判断该微信账号是否在我们网站里有对应账号了
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: "+ e.toString());
                e.printStackTrace();
            }
        }else {

        }
        log.debug("weixin login success.");
        log.debug("login role_type:" + roleType);
        if (FRONTEND.equals(roleType)) {
            PersonInfo personInfo = null;
            //没有在我们数据库里有对应账号则自动创建，实现微信和我们网站的无缝对接
            if (auth == null) {
                personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
                personInfo.setCustomerFlag(1);
                auth = new WechatAuth();
                auth.setOpenId(openId);
                auth.setPersonInfo(personInfo);
                WechatAuthExecution we = wechatAuthService.register(auth, null);
                if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                    return null;
                }
            }
            personInfo = personInfoService.getPersonInfoById(auth.getUserId());
            request.getSession().setAttribute("user", personInfo);
            return "frontend/index";
        }
        if (SHOPEND.equals(roleType)) {
            PersonInfo personInfo = null;
            WechatAuthExecution we = null;
            if (auth == null) {
                auth = new WechatAuth();
                auth.setOpenId(openId);
                personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
                personInfo.setShopOwnerFlag(1);
                auth.setPersonInfo(personInfo);
                we = wechatAuthService.register(auth, null);
                if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                    return null;
                }
            }
            personInfo = personInfoService.getPersonInfoById(auth.getUserId());
            request.getSession().setAttribute("user", personInfo);
            ShopExecution se = shopService.getShopByEmployeeId(personInfo.getUserId());
            request.getSession().setAttribute("user", personInfo);
            if (se.getShopList() == null || se.getShopList().size() <= 0) {
                return "shop/registershop";
            } else {
                request.getSession().setAttribute("shopList", se.getShopList());
                return "shop/shoplist";
            }
        }
        return null;
    }
}
