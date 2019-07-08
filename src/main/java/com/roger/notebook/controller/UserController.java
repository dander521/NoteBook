package com.roger.notebook.controller;

import com.alibaba.druid.util.StringUtils;
import com.roger.notebook.Service.UserService;
import com.roger.notebook.Service.model.UserModel;
import com.roger.notebook.controller.viewmodel.UserVO;
import com.roger.notebook.dao.UserDOMapper;
import com.roger.notebook.dataObject.UserDO;
import com.roger.notebook.error.BusinessException;
import com.roger.notebook.response.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserDOMapper userDOMapper;

    @Value("${imagepath}")
    private String imagePath;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseVO login(@RequestParam(name = "phone") String phone, @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return ResponseVO.serviceFail("手机号和密码不能为空");
        }

        UserDO userDO = userDOMapper.selectByPhone(phone);
        if (userDO == null) {
            return ResponseVO.serviceFail("登录失败");
        }

        if (!StringUtils.equals(EncodeByMD5(password), userDO.getPassword())) {
            return ResponseVO.serviceFail("密码不正确");
        }

        UserModel userModel = userService.login(phone, EncodeByMD5(password));
        if (userModel == null) {
            return ResponseVO.serviceFail("登录失败");
        }

        String uuidToken = UUID.randomUUID().toString();
        System.out.println("uuidToken =" + uuidToken);
        uuidToken = uuidToken.replace("-","");
        redisTemplate.opsForValue().set(uuidToken, userModel);
        redisTemplate.expire(uuidToken,2, TimeUnit.HOURS);

        Map<String, String> map = new HashMap<>();
        map.put("token", uuidToken);
        map.put("uuid", ""+userModel.getUuid());

        return ResponseVO.success(map);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseVO register(@RequestParam(name = "phone") String phone, @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return ResponseVO.serviceFail("手机号和密码不能为空");
        }

        // 手机号是否存在
        UserDO userDO = userDOMapper.selectByPhone(phone);
        if (userDO!=null) {
            return ResponseVO.serviceFail("手机号是否存在");
        }

        boolean isSuccess = userService.register(phone, EncodeByMD5(password));
        if (!isSuccess) {
            return ResponseVO.serviceFail("注册失败");
        }

        return ResponseVO.success("注册成功");
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public ResponseVO updateUserInfo(@RequestParam(name = "avatar", required = false) MultipartFile avatar,
                                           @RequestParam(name = "nickname", required = false) String nickname,
                                           @RequestParam(name = "sex", required = false) String sex,
                                           @RequestParam(name = "address", required = false) String address) throws BusinessException {

        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResponseVO.serviceFail("用户还未登录");
        }

        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if (userModel == null) {
            return ResponseVO.serviceFail("登录已失效，请重新登录");
        }

        if (avatar.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = avatar.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = imagePath;
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            avatar.transferTo(dest);
            userModel.setAvatar(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!StringUtils.isEmpty(nickname)) {
            userModel.setNickname(nickname);
        }
        if (!StringUtils.isEmpty(sex)) {
            userModel.setSex(Integer.parseInt(sex));
        }
        if (!StringUtils.isEmpty(address)) {
            userModel.setAddress(address);
        }

        boolean isSuccess = userService.updateUserInfo(userModel);
        if (!isSuccess) {
            return ResponseVO.serviceFail("更新失败");
        }

        return ResponseVO.success("更新用户信息成功");
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public ResponseVO getUserInfo() throws BusinessException {

        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResponseVO.serviceFail("用户还未登录");
        }

        UserModel checkModel = (UserModel) redisTemplate.opsForValue().get(token);
        if (checkModel == null) {
            return ResponseVO.serviceFail("登录已失效，请重新登录");
        }

        UserModel userModel = userService.getUserInfo(checkModel.getUuid());
        if (userModel == null) {
            return ResponseVO.serviceFail("获取用户信息失败");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);

        return ResponseVO.success(userVO);
    }

    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String resultStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return resultStr;
    }
}
