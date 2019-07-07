package com.roger.notebook.controller;

import com.alibaba.druid.util.StringUtils;
import com.roger.notebook.Service.BookService;
import com.roger.notebook.Service.model.BookModel;
import com.roger.notebook.Service.model.UserModel;
import com.roger.notebook.error.BusinessException;
import com.roger.notebook.response.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/book/")
public class BookController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private BookService bookService;

    @Value("${imagepath}")
    private String imagePath;

    @RequestMapping(value = "createBook", method = RequestMethod.POST)
    public ResponseVO createBook(@RequestParam(name = "photo", required = true) MultipartFile photo,
                                     @RequestParam(name = "title", required = true) String title,
                                     @RequestParam(name = "subtitle", required = true) String subtitle,
                                     @RequestParam(name = "createtime", required = true) String createtime) throws BusinessException {

        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResponseVO.serviceFail("用户还未登录");
        }

        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if (userModel == null) {
            return ResponseVO.serviceFail("登录已失效，请重新登录");
        }

        BookModel bookModel = new BookModel();

        if (photo.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = photo.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = imagePath;
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            photo.transferTo(dest);
            bookModel.setPhoto(filePath+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bookModel.setTitle(title);
        bookModel.setSubtitle(subtitle);
        bookModel.setCreatetime(createtime);

        boolean isSuccess = bookService.createBookCategory(bookModel);

        if (!isSuccess) {
            return ResponseVO.serviceFail("创建失败");
        }

        return ResponseVO.success("创建成功");
    }

    @RequestMapping(value = "getBookList", method = RequestMethod.POST)
    public ResponseVO getBookList(@RequestParam(name = "keyword", required = false) String keyword,
                                  @RequestParam(name = "page", required = false) String page,
                                  @RequestParam(name = "pageSize", required = false) String pageSize) {
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResponseVO.serviceFail("用户还未登录");
        }

        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if (userModel == null) {
            return ResponseVO.serviceFail("登录已失效，请重新登录");
        }

        if (page==null) {
            page = "1";
        }

        if (pageSize==null) {
            pageSize = "10";
        }

        List<BookModel> bookList = bookService.getBookList(userModel.getUuid(),keyword,Integer.parseInt(page),Integer.parseInt(pageSize));

        if (bookList!=null) {
            Map<String, Object> map = new HashMap<>();
            map.put("bookList", bookList);

            return ResponseVO.success(map);
        }
        return ResponseVO.serviceFail("请求失败");
    }
}
