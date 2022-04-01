package ru.aberezhnoy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aberezhnoy.service.PictureService;

import java.util.function.Function;

@Controller
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

//    @GetMapping("/{pictureId}")
//    public void downLoadPicture(@PathVariable("pictureId") long pictureId,
//                                HttpServletResponse response) throws ServletException, IOException {
//        Optional<String> opt = pictureService.getPictureContentType(pictureId);
//        if (opt.isPresent()) {
//            response.setContentType(opt.get());
//            response.getOutputStream().write(pictureService.getPictureDataById(pictureId).get());
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }

    @GetMapping("/{pictureId}")
    public ResponseEntity<byte[]> downLoadPicture(@PathVariable("pictureId") long pictureId) {
        return pictureService.getPictureDataById(pictureId)
                .map(pic -> ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_TYPE, pic.getContentType())
                        .body(pic.getData())
                ).orElse(ResponseEntity
                .notFound()
                .build());
    }
}
