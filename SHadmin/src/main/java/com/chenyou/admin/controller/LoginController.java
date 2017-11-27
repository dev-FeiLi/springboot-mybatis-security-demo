package com.chenyou.admin.controller;


import com.chenyou.admin.Utils.ApplicationConstant;
import com.chenyou.admin.service.SysManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.jws.WebParam;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@Controller
public class LoginController {

    final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysManageService sysManageService;

    @RequestMapping(value = {"/", "/login"}, method = {RequestMethod.GET})
    public ModelAndView loginPage(HttpServletRequest request, HttpSession session, Model model) {
        ModelAndView modelAndView = new ModelAndView("login");
        try {
            String errMsg = "";
            Object message = session.getAttribute(ApplicationConstant.SESSION_ERROR_MSG);
            if (null == message) {
                errMsg = "";
            } else {
                errMsg = String.valueOf(message);
            }
            model.addAttribute("errMsg", errMsg);
            model.addAttribute("tstamp", System.currentTimeMillis());
            model.addAttribute("checkPath", ApplicationConstant.LOGIN_CHECK_PATH);
            model.addAttribute("captchaPath", ApplicationConstant.LOGIN_CAPTCHA_PATH);
            model.addAttribute("loginPath", ApplicationConstant.LOGIN_PAGE_PATH);
            modelAndView.addObject(model);
        } catch (Exception e) {
            LOG.error("login page error: ", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "getcaptcha", method = {RequestMethod.GET})
    public void buildCaptcha(HttpServletResponse response, HttpSession session) {
        // 验证码图片的宽度。
        int width = 75;
        // 验证码图片的高度。
        int height = 25;
        // 验证码字符个数
        int codeCount = 4;
        int x = 10;
        // 字体高度
        int fontHeight;
        int codeY;
        char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        x = width / (codeCount + 1);
        fontHeight = height - 2;
        codeY = height - 4;
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类
        Random random = new Random();

        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 2, height - 1);

        // 随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            int w = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(w, y, w + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(150);
            green = random.nextInt(255);
            blue = random.nextInt(150);

            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        try {
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();
            session.setAttribute(ApplicationConstant.SESSION_VALIDATE_CODE, randomCode.toString());
        } catch (IOException e) {
            LOG.error("getcaptcha error: ", e);
        }
    }


    @RequestMapping(value = "/error", method = {RequestMethod.GET})
    public ModelAndView errorPage(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        String page = "error";
        int status = response.getStatus();
        page = page + "/" + status;
        mav.setViewName(page);
        return mav;
    }

    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
